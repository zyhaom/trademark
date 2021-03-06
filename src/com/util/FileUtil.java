package com.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;

import com.util.prop.CommParameter;

/**
 * 
 * @desc 递归读取指定目录下的所有文件，获取文件的全路径列表
 * 
 */
public class FileUtil {

	private static int totalNum = 0;
	public static List<String> fileList = new ArrayList<String>();
	
	/**
	 * @throws Exception 
	 * @Author:HaoMing(郝明)
	 * @Project_name:trademark
	 * @Full_path:com.util.FileUtil.java
	 * @Date:@2017 2017-1-6 上午10:08:27
	 * @Return_type:void
	 * @Desc :
	 */
	public static void main(String[] args) throws Exception {
		int[] r = getWidthAndHeight("E:\\trademark\\img\\01\\01\\1.jpg");
		System.out.println(r[0]+":"+r[1]);
		
		/*long startTime = System.currentTimeMillis();
//		System.out.println(CommParameter.getCommParameterByKey("filePath"));
		
		List<String> list = listFiles(CommParameter.getCommParameterByKey("filePath"));
//		Iterator<?> it = list.iterator();
//		while(it.hasNext()){
//			String s = (String)it.next();
//			System.out.println(s);
//		}
		
		Set<String> set = new TreeSet<String>(list);
		Object[] array = set.toArray();
		int num = 0;
		Iterator<?> it = set.iterator();
		while(it.hasNext()){
			String s = (String)it.next();
			if("D:\\ftp_folder\\trademark\\0\\20150204\\商标37类300个\\88.gif".equals(s)){
				break;
			}else{
				num++;
			}
//			System.out.println(s);
		}
		
		for (int i = num; i < array.length; i++) {
			System.out.println(array[i]);
		}
		System.out.println("totalNum:\t"+totalNum);
		
//		test();
		long endTime = System.currentTimeMillis();
		System.out.println(endTime-startTime);*/
	}
	
	public static List<String> listFiles(String path){
//		path = CommParameter.getCommParameterByKey("filePath");
		File file = new File(path);
		
		if(file.exists()&&file.isDirectory()){
			File[] listFiles = file.listFiles();
			for (int i = 0; i < listFiles.length; i++) {
//				System.out.println(listFiles[i].getName());
				if(listFiles[i].isFile()){
//					System.out.println(listFiles[i].toString());
					fileList.add(listFiles[i].toString());
					totalNum++;
				}else {
					listFiles(listFiles[i].toString());
				}
			}
		}
		return fileList;
	}
	
	/**
	 * 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @Author:HaoMing(郝明)
	 * @Project_name:trademark
	 * @Full_path:com.util.FileUtil.java
	 * @Date:@2018 2018-6-12 下午1:55:30
	 * @Return_type:int[]
	 * @Desc : 根据图片的全路径得到图片的实际宽高数组
	 */
	public static int[] getWidthAndHeight(String imgFullPath) throws Exception{
		int[] returnInt = {0,0};
		if(imgFullPath.length()==0){
			return returnInt;
		}
		BufferedImage sourceImg = ImageIO.read(new FileInputStream(new File(imgFullPath))); 
		returnInt[0] = sourceImg.getWidth();
		returnInt[1] = sourceImg.getHeight();
		return returnInt;
	} 
	
	
	
	
	
	private static void test(){
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add("10");
		arrayList.add("30");
		arrayList.add("11");
		arrayList.add("2");
		arrayList.add("1");
		TreeSet<String> treeSet = new TreeSet<String>(arrayList);
		System.out.println(treeSet);
		
		Set<String> set = new TreeSet<String>();
		set.add("10");
		set.add("30");
		set.add("11");
		set.add("2");
		set.add("1");
		System.out.println(set);
		
		
		Set<String> set2 = new TreeSet<String>();
		set2.add("c");
		set2.add("10");
		set2.add("30");
		set2.add("11");
		set2.add("2");
		set2.add("1");
		System.out.println(set2);
//		Iterator<?> it = set.iterator();
//		while(it.hasNext()){
//			String s = (String)it.next();
//			System.out.println(s);
//		}
	}

}
