package com.listener;

import java.io.File;
import java.util.Calendar;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.bo.CommParameterBO;
import com.bo.img.ImgBO;
import com.util.CommUtil;
import com.util.FetchsimilarSBAid;
import com.util.prop.CommParameter;

public class MyListener implements ServletContextListener {
	private final static Logger log = Logger.getLogger(MyListener.class);
	public static int dll_init = 0;

	/*public static void main(String[] args) {
		
		// System.out.println(PicSearchDll.dll_init);
		System.out.println(PicSearchDll.callInit("D:\\ftp_folder\\pic_search\\1\\"));
		System.out.println(PicSearchDll.callSearch("D:\\ftp_folder\\pic_search\\001.jpg"));
	}*/

	public void contextDestroyed(ServletContextEvent arg0) {
		//关闭线程池
		FetchsimilarSBAid.pool.shutdown();
		System.out.println("contextDestroyed...###");
	}

	public void contextInitialized(ServletContextEvent arg0) {
		File file = new File(CommParameter.getCommParameterByKey("maxMemPath"));
		if(!file.exists()){
			file.mkdirs();
		}
		new MyThread().start();
		taskPerFixSeconds(86400);
	}

	//初始化时间
	private void taskPerFixSeconds(long seconds){
		final CommParameterBO bo = new CommParameterBO();
		CommUtil.dayArray = bo.fetchDayArra();
		long l = Calendar.getInstance().getTimeInMillis();
		long fixTimeSpan = seconds*1000;//FixSecond的毫秒数
		long nextFixtimeSpan = fixTimeSpan-l%fixTimeSpan;//距离下FixSecond的毫秒数
		
		new Timer().scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				CommUtil.dayArray = null;
				CommUtil.dayArray = bo.fetchDayArra();
				
				//定期删除没用的上传图片
				delUploadImg(CommParameter.getCommParameterByKey("tempDir")+"\\img");
			}
		},nextFixtimeSpan, fixTimeSpan);
	}  
	
	private static void delUploadImg(String path){
		File[] files = new File(path).listFiles();//
		Set<String> set = new ImgBO().getSearchedImg();
//		Set<String> set = new HashSet<String>();
//		set.add("pn_1491547213893.png");
//		set.add("18911005534_1492494002099.jpg");
		for (int i = 0; i < files.length; i++) {
			File tempFile = files[i];
			if(!set.contains(tempFile.getName())){
				String s = tempFile.getName();
//				System.out.print(s+"\t");
//				System.out.println(s.substring(s.indexOf("_")+1, s.indexOf(".")));
				if(System.currentTimeMillis()-Long.parseLong(s.substring(s.indexOf("_")+1, s.indexOf(".")))>86000000l){
					tempFile.delete();
				}
			}
		}
	}
	
//	public static void main(String[] args) {
//		delUploadImg("E:\\trademark\\temp\\img");
//	}
	
	class MyThread extends Thread {
		public void run() {
			try {
				//1.删除临时dat目录
//				log.info(CommParameter.getCommParameterByKey("tempDir")+File.separator+"dat");
				File[] fs = new File(CommParameter.getCommParameterByKey("tempDir")+File.separator+"dat").listFiles();
				for (int i = 0; i < fs.length; i++) {
					fs[i].delete();
				}
				
				//2.判断是否启动检索任务
				/*if(new PreSearchBO().getPreSearchImgNum()>=1){
					CommUtil.hasPreSearchImg = true;
					FetchsimilarSBAid.ThreadPoolFunc();
				}else{
					CommUtil.hasPreSearchImg = false;
				}*/
				new Thread(new Runnable() {
					public void run() {
						try {
							Thread.sleep(10000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						FetchsimilarSBAid.ThreadPoolFunc();
					}
				}).start();
				
			} catch (Exception e) {
				e.printStackTrace();
				log.error("检索线程异常:"+e.getMessage());
			}
		}
	}
	
	
}
