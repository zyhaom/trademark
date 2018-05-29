package com.servlet.upload;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

import com.util.UnicodeUtil;

/**
 * 
 * @author Administrator
 * @desc 此类中没有判断已经上过的重名文件的方法
 */
public class CopyOfUploadServlet extends HttpServlet {
	
	private static final long serialVersionUID = 290053520185043817L;

	//设置保存上传文件的目录 
//	String uploadDir = "";
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=gb2312");
		PrintWriter out = response.getWriter();

		String uploadDir = getServletContext().getRealPath("/uploadDir");
		
		if (uploadDir == null) {
			out.println("无法访问存储目录！");
			return;
		}
		File fUploadDir = new File(uploadDir);
		if (!fUploadDir.exists()) {
			if (!fUploadDir.mkdir()) {
				out.println("无法创建存储目录!");
				return;
			}
		}

		if (!DiskFileUpload.isMultipartContent(request)) {
			out.println("只能处理multipart/form-data类型的数据!");
			return;
		}

		DiskFileUpload fu = new DiskFileUpload();
		
		fu.setSizeMax(1024 * 1024 * 2000);//最多上传*M数据 
		
		fu.setSizeThreshold(1024 * 1024);//超过1M的字段数据采用临时文件缓存 
		
		fu.setRepositoryPath(System.getProperty("java.io.tmpdir")); //采用默认的临时文件存储位置 
		
		fu.setHeaderEncoding("gb2312");//设置上传的普通字段的名称和文件字段的文件名所采用的字符集编码 

		//得到所有表单字段对象的集合 
		List fileItems = null;
		try {
			fileItems = fu.parseRequest(request);
		} catch (FileUploadException e) {
			out.println("解析数据时出现如下问题：");
			e.printStackTrace(out);
			return;
		}

		//处理每个表单字段 
		Iterator i = fileItems.iterator();
		while (i.hasNext()) {
			FileItem fi = (FileItem) i.next();
			if (fi.isFormField()) {
				String content = fi.getString("GB2312");  //获得普通表单域中的内容
				String fieldName = fi.getFieldName();     //获得普通表单域的控件名称
				request.setAttribute(fieldName, content);
			} else {
				try {
					String pathSrc = fi.getName();       //获得file表单域中的内容
					/*如果用户没有在FORM表单的文件字段中选择任何文件， 
					那么忽略对该字段项的处理*/
					if (pathSrc.trim().equals("")) {
						continue;
					}
					int start = pathSrc.lastIndexOf('\\');
					String fileName = pathSrc.substring(start + 1);
					
					File pathDest = new File(uploadDir, fileName);

//					String fieldName = fi.getFieldName();//获得file表单域的控件名称
//					request.setAttribute(fieldName, fileName);
					//if("file1".equals(fieldName)){
//						if(!isThisFileExit(uploadDir,(String) request.getAttribute("file")))
							fi.write(pathDest);
					//}
//					if("file2".equals(fieldName)){
//						if(!isThisFileExit(uploadDir,(String) request.getAttribute("file2")))
//							fi.write(pathDest);
//					}
				} catch (Exception e) {
					out.println("存储文件时出现如下问题：");
					e.printStackTrace(out);
					return;
				} finally {//总是立即删除保存表单字段内容的临时文件 
					fi.delete();
				}
			}
		}

		out.println("成功上传的文件：");
		
		System.out.println(request.getParameter("text_name1"));//null
		System.out.println(request.getAttribute("text_name1"));//ok
		System.out.println(request.getAttribute("text_name2"));//ok
		System.out.println(request.getAttribute("text_name3"));//null
		System.out.println(request.getParameter("text_name3"));//ok ?传参
//		System.out.println(UnicodeUtil.unicodeToStr_za(request.getParameter("text_name4")));//ok ?传参
	}

	
	/**
	 * @此方法判断已经上过的重名文件
	 * @param args
	 */
	private static boolean isThisFileExit(String destPath,String fileName){
		File f = new File(destPath);
		String[] files = f.list();
		for(int i = 0;i<files.length;i++){
			if(fileName.equals(files[i])){
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args){
		  System.out.println("默认的临时文件路径:: " + System.getProperty("java.io.tmpdir"));
	}
}



