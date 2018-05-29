package com.dll.tm;

import java.io.File;

import org.apache.log4j.Logger;

/**
 * 
 * @author Administrator 执行前类名必须是 ： TradeMarkDll
 * 
第一个函数【由图片得到特征文件：init】
我给你传入两个参数，第一个表示图片的目录，第二个表示存放生成的特征文件的目录【里面存放的是每张图片对应的特征文件，文件个数和图片张数相同。文件名称和 图片名称也相同，只是后缀不同】
你给我返回的一个值的状态：全部正常执行【0】，执行过程中出现异常【1】
第二个函数【给订两张图片得到相似距离：search】
我给你传入三个参数：待检索图片的特征文件的全路径，某一个特征文件的全路径，相似距离
你给我返回的一个值：相似距离【0.x】或不相似【1】,异常【-1】

另外需要多加一个函数，【对待检索图片生成特征文件 buildFeature】
我给你传入三个个参数，第一个表示待检索图片的全路径你，第二个表示存放生成的特征文件的目录，第三个表示生成的特征文件的文件名
你给我返回的一个值的状态：全部正常执行【0】，执行过程中出现异常【1】


新增 SearchMultiThread函数： tn  表示调用的线程数
 */
public class TradeMarkDll {
	private final static Logger log = Logger.getLogger(TradeMarkDll.class);
	private native static String init(String picDirFullPath,String featureDirFullPath);
	private native static String search(String goalFeatureDirFullPath,String destFeatureDirFullPath,float similarDistance);
	private native static String searchMultiThread(String goalFeatureDirFullPath,String destFeatureDirFullPath,float similarDistance,int tn);
	private native static String buildFeature(String goalPicDirFullPath,String tempFeatureDirFullPath,String featureName);

	public static void main(String[] args) {

		System.out.println(System.getProperty("user.dir"));
		//10000张2分钟
//		System.out.println(callInit("D:\\ftp_folder\\trademark\\img\\01\\01","D:\\ftp_folder\\trademark\\dat\\01"));
//		System.out.println(callInit("E:\\trademark\\img\\01","E:\\trademark\\dat\\01"));
		
//		System.out.println(callInit("E:\\trademark\\img\\02\\01\\2_1_1.jpg","E:\\trademark\\dat\\02\\01",false));
//		System.out.println(callInit("E:\\trademark\\img\\01","E:\\trademark\\dat\\01",true));
//		System.out.println(callBuildFeature("E:\\trademark\\temp\\img\\2_1_1.jpg", "E:\\trademark\\temp\\dat", "1.dat"));
//		System.out.println(callSearch("E:\\trademark\\temp\\dat\\1.dat","E:\\trademark\\dat\\02\\01\\2_1_2.dat",0.9f));
		
		System.out.println(callSearchMultiThread("E:\\trademark\\temp\\dat\\1.dat","E:\\trademark\\dat\\01",0.3f,5));//multi_thread
		
//		System.out.println(callBuildFeature("D:\\ftp_folder\\trademark\\img\\01\\5_17.jpg", "D:\\ftp_folder\\trademark\\temp", "1.dat"));
//		System.out.println(callSearch("D:\\ftp_folder\\pic_search\\3\\3-1.dat","D:\\ftp_folder\\pic_search\\3\\3-2.dat",0.3f));
		
		/*File[] fs = new File("E:\\trademark\\dat\\02\\01").listFiles();
		float f = 0f;
		for (int i = 0; i < fs.length; i++) {
				f = Float.parseFloat(callSearch("E:\\trademark\\temp\\dat\\1.dat",fs[i].toString(),0.3f));
			if(f<=0.3f) {
				System.out.println(f+"\t"+fs[i]);
			}
			if(i%1000==0) {
				System.out.println("==== : "+i);
			}
		}*/
	}
	
	static {
//		 System.out.println("user.dir : "+System.getProperty("user.dir"));
		 log.info("user.dir : "+System.getProperty("user.dir"));
		 System.load(System.getProperty("user.dir")+"\\testdll.dll");
	}

	/**
	 * 
	 * @Author:HaoMing(郝明)
	 * @Project_name:trademark
	 * @Full_path:com.dll.tm.TradeMarkDll.java
	 * @Date:@2017 2017-3-10 下午4:06:50
	 * @param picDirFullPath 表示图片的目录
	 * @param featureDirFullPath 表示存放生成的特征文件的目录【里面存放的是每张图片对应的特征文件，文件个数和图片张数相同。文件名称和 图片名称也相同，只是后缀不同】
	 * @param isclean 是否将原文件夹清空
	 * @Return_type:String 正常【0】，异常【1】
	 * @Desc :初始化方法：生成图片对应的特征文件
	 */
	public static String callInit(String picDirFullPath,String featureDirFullPath,boolean isclean) {
		
		File f = new File(featureDirFullPath);
		if(!f.exists()){
			if(!f.mkdirs()){
				return "mkdirs faild";
			}
		}
		//1.将picDirFullPath下的所有特征文件删除
		if(isclean){
			File[] fs = new File(featureDirFullPath).listFiles();
			for (int i = 0; i < fs.length; i++) {
				fs[i].delete();
			}
		}
		//2.生成特征文件
		return init(picDirFullPath, featureDirFullPath);
	}

	/**
	 * 
	 * @Author:HaoMing(郝明)
	 * @Project_name:trademark
	 * @Full_path:com.dll.tm.TradeMarkDll.java
	 * @Date:@2017 2017-3-10 下午4:11:45
	 * @param goalFeatureDirFullPath   待检索的特征文件全路径
	 * @param destFeatureDirFullPath   目标特征文件全路径
	 * @param similarDistance  相似度【0.30-0.70】默认0.5
	 * @Return_type:String 相似距离【0.x】，不相似【1】;异常【-1】
	 * @Desc :待检方法
	 */
	public static String callSearch(String goalFeatureDirFullPath,String destFeatureDirFullPath,float similarDistance){
		return search(goalFeatureDirFullPath,destFeatureDirFullPath,similarDistance);
	}
	
	public static String callSearchSyn(String goalFeatureDirFullPath,String destFeatureDirFullPath,float similarDistance){
		synchronized (TradeMarkDll.class) {
			return search(goalFeatureDirFullPath,destFeatureDirFullPath,similarDistance);
		}
	}
	
	//multiThread
	public static String callSearchMultiThread(String goalFeatureDirFullPath,String destFeatureDirFullPath,float similarDistance,int tn){
		return searchMultiThread(goalFeatureDirFullPath,destFeatureDirFullPath,similarDistance,tn);
	}
	
	/**
	 * 
	 * @Author:HaoMing(郝明)
	 * @Project_name:trademark
	 * @Full_path:com.dll.tm.TradeMarkDll.java
	 * @Date:@2017 2017-3-10 下午4:18:45
	 * @param goalPicDirFullPath   待检索的图片全路径
	 * @param tempFeatureDirFullPath   待检索的图片生成的临时特征文件夹路径
	 * @param featureName   待检索的图片生成的临时特征文件名称  [*.dat]
	 * @Return_type:String 正常【0】，异常【1】
	 * @Desc :
	 */
	public static String callBuildFeature(String goalPicDirFullPath,String tempFeatureDirFullPath,String featureName){
		if("0".equals(buildFeature(goalPicDirFullPath,tempFeatureDirFullPath,featureName))){
			return tempFeatureDirFullPath+File.separator+featureName;
		}else{
			return null;
		}
	}

}
