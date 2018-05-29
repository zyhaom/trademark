package com.servlet.option;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.dll.tm.TradeMarkDll;
import com.util.CommUtil;
import com.util.JsonUtil;

//给定图片的路径，重新生成相应的dat文件
public class BatchDat extends HttpServlet {

	private final static Logger log = Logger.getLogger(BatchDat.class);
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		try {
			
//			{"picDirFullPath":"?"}
			String s = CommUtil.fetchPostStrFun(req);
			String picDirFullPath = JsonUtil.getMap4Json(s).get("picDirFullPath").toString();
//			String r = TradeMarkDll.callInit("E:\\trademark\\img\\01\\01","E:\\trademark\\dat\\01\\01");
			
			generateBat(out,picDirFullPath);
//			log.info(picDirFullPath);
//			out.print("aaa "+r);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * 
	 * @Author:HaoMing(郝明)
	 * @Project_name:trademark
	 * @Full_path:com.servlet.option.BatchDat.java
	 * @Date:@2017 2017-3-24 下午2:10:46
	 * @Return_type:void
	 * @Desc :批量生成dat文件，对应文件夹。
	 */
	private void generateBat(PrintWriter out,String picDirFullPath){
		
		String featureDirFullPath = picDirFullPath.replaceAll("img", "dat");
		String result = TradeMarkDll.callInit(picDirFullPath,featureDirFullPath,true);
		log.info(result);
		out.print(result);
		
		/*
		File f = new File(picDirFullPath);
		File[] fs = f.listFiles();
		for (int i = 0; i < fs.length; i++) {
			if(fs[i].isDirectory()&&!fs[i].toString().contains("WEB-INF")){
				String featureDirFullPath = fs[i].toString().replaceAll("img", "dat");
				String result = TradeMarkDll.callInit(fs[i].toString(),featureDirFullPath);
				generateBat(out, fs[i].toString());
				log.info(result);
				out.print(result);
			}
			
			
			if(fs[i].isDirectory()){
				generateBat(out, fs[i].toString());
			}else if(fs[i].isFile()){
				String featureDirFullPath = fs[i].toString().replaceAll("img", "dat");
				String result = TradeMarkDll.callInit(fs[i].toString(),featureDirFullPath);
				log.info(result);
				out.print(result);
			}else{
				
			}
		}*/
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		doPost(req, resp);
	}
	
	public static void main(String[] args) {
		generateBat1("E:\\trademark\\img\\01");
	}
	
	private static void generateBat1(String picDirFullPath){
		File f = new File(picDirFullPath);
		File[] fs = f.listFiles();
		for (int i = 0; i < fs.length; i++) {
			if(fs[i].isDirectory()&&!fs[i].toString().contains("WEB-INF")){
				String featureDirFullPath = fs[i].toString().replaceAll("img", "dat");
				System.out.println(fs[i].toString()+":"+featureDirFullPath);
				generateBat1(fs[i].toString());
			}
		}
	}
}
