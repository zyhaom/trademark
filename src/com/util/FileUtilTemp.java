package com.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;

/**
 * 
 * @desc 递归读取指定目录下的所有文件，获取文件的全路径列表
 * 
 */
public class FileUtilTemp {

	public static List<String> fileList = new ArrayList<String>();
	
	/**
	 * @Author:HaoMing(郝明)
	 * @Project_name:trademark
	 * @Full_path:com.util.FileUtil.java
	 * @Date:@2017 2017-1-6 上午10:08:27
	 * @Return_type:void
	 * @Desc :
	 */
	public static void main(String[] args) throws Exception{
		
		OutputStream out = new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\tempsql.sql"));
		
		for (int i = 2; i <= 11918; i++) {
			String sql = "INSERT INTO t_sbprop(datPathID,typeID,sbName,reqID, reqStartDate,reqMan,reqFinishDate,stat)"+
					"VALUES ('02\\\\02\\\\"+i+"', '0202','商标名称', '101011918','2017-10-10', 'trader', '2017-11-11', 2);\n";
			
			out.write(sql.getBytes());
			if(i%100 == 100){
				out.flush();
			}
		}
		
		
		out.close();
		
		
		/*long startTime = System.currentTimeMillis();
		
		List<String> list = listFiles("C:\\Users\\Administrator\\Desktop\\datajpgNS");
		int num = 1;
		Iterator<?> it = list.iterator();
		while(it.hasNext()){
			String s = (String)it.next();
			System.out.println(num+++"\t"+s);
			copyFile(s, "C:\\Users\\Administrator\\Desktop\\02\\", ""+num);
		}
		long endTime = System.currentTimeMillis();
		System.out.println(endTime-startTime);*/
		
//		copyFile("C:\\Users\\Administrator\\Desktop\\datajpgNS\\第45\\image035.jpg","C:\\Users\\Administrator\\Desktop\\02\\","2");
	}
	
	public static List<String> listFiles(String path){
		File file = new File(path);
		
		if(file.exists()&&file.isDirectory()){
			File[] listFiles = file.listFiles();
			for (int i = 0; i < listFiles.length; i++) {
//				System.out.println(listFiles[i].getName());
				if(listFiles[i].isFile()){
//					System.out.println(listFiles[i].toString());
					fileList.add(listFiles[i].toString());
				}else {
					listFiles(listFiles[i].toString());
				}
			}
		}
		return fileList;
	}
	
	
	
	private static void copyFile(String fromPath,String toPath,String newName){
		try {
			FileImageInputStream in = new FileImageInputStream(new File(fromPath));
//			FileInputStream in = new FileInputStream(new File(fromPath));
			FileImageOutputStream out = new FileImageOutputStream(new File(toPath+newName+fromPath.substring(fromPath.lastIndexOf("."))));
//			OutputStream out = new FileOutputStream(new File(toPath+newName));
			
			byte[] read = new byte[1024];
			int len = 0;
			while((len = in.read(read))!=-1){
				out.write(read,0,len);
			}
			in.close();
			out.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
