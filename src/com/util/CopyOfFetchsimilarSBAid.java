package com.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.bo.img.ImgBO;
import com.bo.option.PreSearchBO;
import com.bo.option.TableSBPropBO;
import com.dll.tm.TradeMarkDll;
import com.util.prop.CommParameter;

public class CopyOfFetchsimilarSBAid {

//	private final static Logger log = Logger.getLogger(FetchsimilarSBAid.class);

	static int threadNum = 30;
	public static ExecutorService pool = Executors.newFixedThreadPool(threadNum);
	
	public static void ThreadPoolFunc(){
		PreSearchBO preSearchBO = new PreSearchBO();
		Map<String, String> m = preSearchBO.getSinglePreSearch();
		
		while(CommUtil.hasPreSearchImg){
			System.out.println(new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date()));
			if(m.size() == 0){
				CommUtil.hasPreSearchImg = false;
				return;
			}
			
			try {
				String userID = m.get("username");
				String picPath = m.get("pic");
				String stat = m.get("stat");//0
				String bigclass = m.get("bigclass");//01
				String smallclass = m.get("smallclass");//0101$0102
				
				String datPathID = "";
				//temp
				String goalPicDirFullPath = CommParameter.getCommParameterByKey("tempDir")+File.separator+"img"+File.separator+picPath;
				String datDirFullPath = null;
				float precision = 0.3f;
				
				//1.临时的dat文件全路径
				String tempDatFullPath = TradeMarkDll.callBuildFeature(goalPicDirFullPath, CommParameter.getCommParameterByKey("tempDir")+File.separator+"dat", userID+System.currentTimeMillis()+".dat");
				//2.商标检索块
				if(tempDatFullPath != null){
					ImgBO imgBO = new ImgBO();
					TableSBPropBO bo = new TableSBPropBO();
					Map<String,String> map = imgBO.getDatPathByTypeID(bigclass, smallclass);
					Object[] datDirs = map.values().toArray();
					//持久化检索结果
					Set<String> sqlSet = new HashSet<String>();
					for (int i = 0; i < datDirs.length; i++) {
						datDirFullPath = datDirs[i].toString();
						List<Entry<Object, Object>> l = searchFunc1(datDirFullPath, precision, tempDatFullPath);
						if(l == null){
							continue;
						}
						Iterator<?> it = l.iterator();
						long timeGLWZ = System.currentTimeMillis();
						while(it.hasNext()){
							Entry<Object, Object> e = (Entry<Object, Object>)it.next();
							datPathID = (String)e.getKey();
							datPathID = datPathID.replaceAll("\\\\", "\\\\\\\\");
							sqlSet.add("INSERT INTO t_searchhistory (userID,picPath,datPathID,stat,timeGLWZ) " +
									"VALUES ('"+userID+"','"+picPath+"','"+datPathID+"','"+stat+"',"+timeGLWZ+");");
						}
					}
					if(sqlSet.size()==0){// 检索结果--无相似商标
						sqlSet.add("INSERT INTO t_searchhistory (userID,picPath,datPathID,stat,timeGLWZ) " +
								"VALUES ('"+userID+"','"+picPath+"','"+datPathID+"','"+stat+"',"+0+");");
					}
					bo.initSql(sqlSet);
				
				//3.删除临时的dat文件
				if(tempDatFullPath != null){
//					new File(tempDatFullPath).deleteOnExit();
					new File(tempDatFullPath).delete();
				}
				
				//4.检索过的记录更改状态
//				preSearchBO.updatePreSearch(recordId);
			}
				
			//last
			m = preSearchBO.getSinglePreSearch();
			//推送
			
			System.out.println(new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date()));
			System.out.println("tuisong...."+picPath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @Author:HaoMing(郝明)
	 * @Project_name:trademark
	 * @Full_path:com.servlet.search.FetchsimilarSBAid.java
	 * @Date:@2017 2017-3-27 下午5:41:25
	 * @Return_type:List<Entry<Object,Object>>
	 * @Desc :商标检索方法
	 */
	private static List<Entry<Object, Object>> searchFunc(String datDirFullPath, final float precision, final String tempDatFullPath) {
		int topSearchNum = 20000; //检索上限
		
		Map<Object, Object> m = new TreeMap<Object, Object>();
		float tempFloat = 0f;//临时相似距离
//		float precision = 0.3f;//阀值精度
		if(datDirFullPath==null){
			return null;
		}
		File[] fs = new File(datDirFullPath).listFiles();
		int index = 1;
		Iterator<File> it = Arrays.asList(fs).iterator();
		while(it.hasNext()){
				System.out.println(index++);
				try {
					final String datFullPath = String.valueOf((File)it.next());
					Future<Float> future = pool.submit(new Callable<Float>() {
						public Float call() throws Exception {
							return Float.parseFloat(TradeMarkDll.callSearchSyn(tempDatFullPath,datFullPath,precision));
						}
					});
	
					if(future.get()<=precision){
						String datPathID = datFullPath;
						datPathID = datPathID.replace("E:\\trademark\\dat\\", "");
						datPathID = datPathID.substring(0,datPathID.lastIndexOf("."));
						m.put(datPathID, String.valueOf(future.get()));
						topSearchNum--;
					}
					if(topSearchNum<=0){
//						break;
						return CommUtil.orderMap(m);
					}
					
				} catch (NoSuchElementException e) {
				}catch (Exception e) {
					e.printStackTrace();
				}
		}
		
//		pool.shutdown();
		
		List<Entry<Object,Object>> l = CommUtil.orderMap(m);//排序
		return l;
	}
	
	private static List<Entry<Object, Object>> searchFunc1(String datDirFullPath, final float precision, final String tempDatFullPath) {
		int topSearchNum = 20000; //检索上限
		
//		final Map<Object, Object> m = new TreeMap<Object, Object>();
		float tempFloat = 0f;//临时相似距离
//		float precision = 0.3f;//阀值精度
		if(datDirFullPath==null){
			return null;
		}
		final File[] fs = new File(datDirFullPath).listFiles();
		
		
		Map<Object, Object> m = new HashMap<Object, Object>();
		
		for (int i = 0; i < fs.length; i++) {
			final String datFullPath = fs[i].toString();
			Future<Map<Object, Object>> future = pool.submit(new Callable<Map<Object, Object>>() {
				public Map<Object, Object> call() throws Exception {
					Map<Object, Object> m = new TreeMap<Object, Object>();
						float f = Float.parseFloat(TradeMarkDll.callSearchSyn(tempDatFullPath,datFullPath,precision));
						if(f<=precision){
							String datPathID = datFullPath;
							datPathID = datPathID.replace("E:\\trademark\\dat\\", "");
							datPathID = datPathID.substring(0,datPathID.lastIndexOf("."));
							m.put(datPathID, String.valueOf(f));
						}
						return m;
					}
			});
			try {
				m.putAll(future.get());
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		try {
			
		} catch (Exception e) {
		}
		
		List<Entry<Object,Object>> l = CommUtil.orderMap(m);//排序
		return l;
	}
	
	
	public static void main(String[] args) {
//		int max = 100;
		int index = 1;
		System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()));
		
		final ExecutorService pool = Executors.newFixedThreadPool(10);
		final List<Future<Float>> resultList = new ArrayList<Future<Float>>();
		
		final float precision = 0.3f;
		final String tempDatFullPath = TradeMarkDll.callBuildFeature("E:\\trademark\\temp\\img\\pn_1491547213893.png", "E:\\trademark\\temp\\dat","1.dat");
		
		final File[] fs = new File("E:\\trademark\\dat\\01").listFiles();
		for (int i = 0; i < fs.length; i++) {
			try {
				Future<Float> future = pool.submit(new TaskWithResult(tempDatFullPath,fs[i].toString(), precision));
				if(future.get() <= precision){
					resultList.add(future);
//					max--;
				}
//				if(max<=0){
//					break;
//				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/*try {
			Thread.sleep(3000);
		} catch (Exception e) {
			// TODO: handle exception
		}*/
				
		pool.shutdown();
		
		if(tempDatFullPath != null){
			new File(tempDatFullPath).delete();
		}
		
		System.out.println(resultList.size());
		for (Future<Float> fu : resultList) {
			try {
				float f = fu.get();
				if(f <= precision && f!=-1){
					System.out.println(index+++"\t"+fu.get());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()));

	}
}

class TaskWithResult implements Callable<Float>{
	private String tempDatFullPath;
	private String datFullPath;
	private float precision;
	
	public TaskWithResult(String tempDatFullPath, String datFullPath, float precision) {
		super();
		this.tempDatFullPath = tempDatFullPath;
		this.datFullPath = datFullPath;
		this.precision = precision;
	}

	public Float call() throws Exception {
		float f = Float.parseFloat(TradeMarkDll.callSearchSyn(tempDatFullPath,datFullPath,precision));
//		if(f!=-1&&f<=0.3f){
//			System.out.println(Thread.currentThread().getName()+"\t"+f);
//		}
		return f;
		
		/*try {
			Thread.sleep(1);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 1f;*/
	}
	
}