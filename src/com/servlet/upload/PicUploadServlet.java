package com.servlet.upload;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.util.prop.CommParameter;

//import org.apache.log4j.Logger;

//import com.sun.org.apache.xalan.internal.xsltc.trax.SmartTransformerFactoryImpl;
//import com.util.prop.CommParameter;


public class PicUploadServlet extends HttpServlet {

	private final static Logger log = Logger.getLogger(PicUploadServlet.class);
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		   log.info("parameter : "+req.getParameter("p"));
	       ServletInputStream sis = req.getInputStream();
//	       String savePath = this.getServletContext().getRealPath("/123.jpg");
	       String savePath = CommParameter.getCommParameterByKey("filePath");
//	       String savePath = "D:\\ftp_folder\\trademark\\00\\2.jpg";
	       System.out.println(savePath);
	       FileOutputStream fos = new FileOutputStream(savePath);
	       byte[] media = new byte[1024];
	       int length = sis.read(media, 0, 1024);
	       while(length  != -1) {
	           fos.write(media, 0, length);
	           length = sis.read(media, 0, 1024);       
	       }
	       fos.close();
	       sis.close();
	    }
	
	/*@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();

		log.info("parameter : "+req.getParameter("p"));
		
//		String filePath = req.getParameter("fileStr");
//		System.out.println(filePath);
		
		String outStr = "";
		// 输入流
		InputStream is = null;
		OutputStream os = null;
		File f = null;
		try {
			is = req.getInputStream();
			byte[] bs = new byte[1024];// 1K的数据缓冲
			int len;// 读取到的数据长度
			// 输出的文件流
//			String uploadDir = getServletContext().getRealPath("/uploadDir");
//			f = new File(uploadDir);
			f = new File("D:\\ftp_folder\\trademark\\00\\1.jpg");
//			f = new File("D:\\111.txt");
			os = new FileOutputStream(f);
			// 开始读取
			while ((len = is.read(bs)) != -1) {
				System.out.println(bs.length);
				os.write(bs, 0, len);
				os.flush();
			}
			
//			FileCacheImageInputStream file = new FileCacheImageInputStream(is, null);
//			System.out.println(file.);
			System.out.println(f.getName());
			
			//调用 dll操作....
//			outStr  = "";
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 完毕，关闭所有链接
			if(is!=null){
				is.close();
			}
			if(os!=null){
				os.close();
			}
			// dll操作完后删除上传的文件【单线程】
//			if(f!=null&&f.isFile()&&f.exists()){
//				f.delete();
//			}
		}
		out.print(outStr);
	}*/
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		doPost(req, resp);
	}
	
}
