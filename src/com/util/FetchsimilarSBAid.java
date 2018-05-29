package com.util;

import java.io.File;
import java.util.Arrays;
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

import org.apache.log4j.Logger;

import com.bo.img.ImgBO;
import com.bo.option.PreSearchBO;
import com.bo.option.TableSBPropBO;
import com.dll.tm.TradeMarkDll;
import com.util.prop.CommParameter;

public class FetchsimilarSBAid {

	private final static Logger log = Logger.getLogger(FetchsimilarSBAid.class);

	static int threadNum = 3;
	public static ExecutorService pool = Executors.newFixedThreadPool(threadNum);
	
	public static void ThreadPoolFunc(){
		PreSearchBO preSearchBO = new PreSearchBO();
		Map<String, String> m = preSearchBO.getSinglePreSearch();
		ImgBO imgBO = new ImgBO();
		while(true){
//			System.out.println(new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date()));
			
			try {
				if(m.size() == 0){
//					CommUtil.hasPreSearchImg = false;
					Thread.sleep(1500);
					m = preSearchBO.getSinglePreSearch();
					continue;
				}
				String recordId = m.get("recordId");
				String userID = m.get("username");
				String picPath = m.get("pic");
				String stat = m.get("stat");//0
				String bigclass = m.get("bigclass");//01
				String smallclass = m.get("smallclass");//0101$0102
				String timeGLWZ_preSearch = m.get("timeGLWZ");
				String precisionNum = m.get("precisionNum");
//				log.info(userID+":"+picPath);
				
				String datPathID = "";
				String similarDistance = "";
				//temp
				String goalPicDirFullPath = CommParameter.getCommParameterByKey("tempDir")+File.separator+"img"+File.separator+picPath;
				if(!new File(goalPicDirFullPath).exists()){
					//上传的图片文件不存在即删除该记录
					imgBO.delSearchedPicList(recordId);
					m = preSearchBO.getSinglePreSearch();
					continue;
				}
				String datDirFullPath = null;
				float precision = 0.3f;
				if(!"".equals(precisionNum)){
					precision = Float.parseFloat(precisionNum);
				}
				
				//1.临时的dat文件全路径
				String tempDatFullPath = TradeMarkDll.callBuildFeature(goalPicDirFullPath, CommParameter.getCommParameterByKey("tempDir")+File.separator+"dat", userID+System.currentTimeMillis()+".dat");
				//2.商标检索块
				log.info(tempDatFullPath);
				if(tempDatFullPath != null){
					
					TableSBPropBO bo = new TableSBPropBO();
					Map<String,String> map = imgBO.getDatPathByTypeID(bigclass, smallclass);
					Object[] datDirs = map.values().toArray();
					//持久化检索结果
					Set<String> sqlSet = new HashSet<String>();
					for (int i = 0; i < datDirs.length; i++) {
						datDirFullPath = datDirs[i].toString();
//						log.info(datDirFullPath);
						List<Entry<Object, Object>> l = searchFunc2(datDirFullPath, precision, tempDatFullPath);
						if(l == null){
							continue;
						}
						Iterator<?> it = l.iterator();
						long timeGLWZ = System.currentTimeMillis();
						while(it.hasNext()){
							Entry<Object, Object> e = (Entry<Object, Object>)it.next();
							datPathID = (String)e.getKey();
							similarDistance = (String)e.getValue();
							datPathID = datPathID.replaceAll("\\\\", "\\\\\\\\");
							sqlSet.add("INSERT INTO t_searchhistory (recordId,picPath,datPathID,stat,timeGLWZ,similarDistance) " +
									"VALUES ('"+recordId+"','"+picPath+"','"+datPathID+"','"+stat+"',"+timeGLWZ+",'"+similarDistance+"');");
						}
					}
//					if(sqlSet.size()==0){// 检索结果--无相似商标
//						sqlSet.add("INSERT INTO t_searchhistory (userID,picPath,datPathID,stat,timeGLWZ) " +
//								"VALUES ('"+userID+"','"+picPath+"','"+datPathID+"','"+stat+"',"+0+");");
//					}
					bo.initSql(sqlSet);
				
				//3.删除临时的dat文件
				if(tempDatFullPath != null){
//					new File(tempDatFullPath).deleteOnExit();
					new File(tempDatFullPath).delete();
				}
				
//				4.检索过的记录更改状态
				preSearchBO.updatePreSearch(recordId);
				
				//推送
				String s = CommUtil.JPushComm(userID, "0", "dddddd", userID, recordId, timeGLWZ_preSearch);
				log.info(s);
			}
			//last
			m = preSearchBO.getSinglePreSearch();

			//System.out.println(new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date()));
//			System.out.println("tuisong...."+picPath);
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
		int topSearchNum = 10000; //检索上限
		
		Map<Object, Object> m = new TreeMap<Object, Object>();
		float tempFloat = 0f;//临时相似距离
//		float precision = 0.3f;//阀值精度
		if(datDirFullPath==null){
			return null;
		}
		File[] fs = new File(datDirFullPath).listFiles();
		Iterator<File> it = Arrays.asList(fs).iterator();
		while(it.hasNext()){
			for (int i = 0; i < threadNum; i++) {
				try {
					final String datFullPath = String.valueOf((File)it.next());
					Future<Float> future = pool.submit(new Callable<Float>() {
						public Float call() throws Exception {
							return Float.parseFloat(TradeMarkDll.callSearchSyn(tempDatFullPath,datFullPath,precision));
//							return Float.parseFloat(TradeMarkDll.callSearch(tempDatFullPath,datFullPath,precision));
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
		}
		
//		pool.shutdown();
		
		List<Entry<Object,Object>> l = CommUtil.orderMap(m);//排序
		return l;
	}
	private static List<Entry<Object, Object>> searchFunc2(String datDirFullPath, final float precision, final String tempDatFullPath) {
		int topSearchNum = 10000; //检索上限
		
		Map<Object, Object> m = new TreeMap<Object, Object>();
		float tempFloat = 0f;//临时相似距离
//		float precision = 0.3f;//阀值精度
		if(datDirFullPath==null){
			return null;
		}
		File[] fs = new File(datDirFullPath).listFiles();
		Iterator<File> it = Arrays.asList(fs).iterator();
		while(it.hasNext()){
			try {
				final String datFullPath = String.valueOf((File)it.next());
				
				tempFloat = Float.parseFloat(TradeMarkDll.callSearchSyn(tempDatFullPath,datFullPath,precision));
				if(tempFloat<=precision){
					String datPathID = datFullPath;
					datPathID = datPathID.replace("E:\\trademark\\dat\\", "");
					datPathID = datPathID.substring(0,datPathID.lastIndexOf("."));
					m.put(datPathID, String.valueOf(tempFloat));
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
		/*for (int i = 0; i < 3000; i++) {
			final String datFullPath = fs[i].toString();
			Future<Map<Object, Object>> future1 = pool.submit(new Callable<Map<Object, Object>>() {
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
				m.putAll(future1.get());
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		for (int i = 3000; i < 6000; i++) {
			final String datFullPath = fs[i].toString();
			Future<Map<Object, Object>> future2 = pool.submit(new Callable<Map<Object, Object>>() {
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
				m.putAll(future2.get());
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		for (int i = 6000; i < fs.length; i++) {
			final String datFullPath = fs[i].toString();
			Future<Map<Object, Object>> future3 = pool.submit(new Callable<Map<Object, Object>>() {
				public Map<Object, Object> call() throws Exception {
					Map<Object, Object> m = new TreeMap<Object, Object>();
	//					System.out.println(((ThreadPoolExecutor)pool).getActiveCount());
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
				m.putAll(future3.get());
			} catch (Exception e) {
				// TODO: handle exception
			}
		}*/
		
		try {
//			Map<Object, Object> m_result1 = future1.get();
//			Map<Object, Object> m_result2 = future2.get();
//			Map<Object, Object> m_result3 = future3.get();
//			m.putAll(m_result1);
//			m.putAll(m_result2);
//			m.putAll(m_result3);
//			Map<Object, Object> m_result = future.get();
//			m.putAll(m_result);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		List<Entry<Object,Object>> l = CommUtil.orderMap(m);//排序
		return l;
	}
	
	
	public static void main(String[] args) {
		/*for (int i = 0; i < 3; i++) {
			System.out.println("i= "+i);
			for (int j = 0; j < 3; j++) {
				if(j==1){
					break;
				}
				System.out.println("j= "+j);
			}
		}*/
		
		/*File[] fs = new File("E:\\Tomcat7.0\\bin").listFiles();
		for (int i = 0; i < fs.length; i++) {
//			System.out.println(fs[i]);
		}*/	
		
		/*Iterator<File> it = Arrays.asList(fs).iterator();
		while(it.hasNext()){
			System.out.println((File)it.next());
		}*/
		
		
	}
	
}
