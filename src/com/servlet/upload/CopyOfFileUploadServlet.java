package com.servlet.upload;

import java.io.File;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.util.prop.CommParameter;

public class CopyOfFileUploadServlet extends HttpServlet {

//	public static void main(String[] args) {
//		System.out.println(java.nio.charset.Charset.defaultCharset());
//	}
	
	private static final long serialVersionUID = 1L;
	private boolean isMultipart;
	private String filePath = CommParameter.getCommParameterByKey("filePath");
	private int maxFileSize = Integer.parseInt(CommParameter.getCommParameterByKey("maxFileSize"));//1024 * 1024;//文件最大尺寸：1M
	private int maxMemSize = Integer.parseInt(CommParameter.getCommParameterByKey("maxMemSize"));//100 * 1024;//内存最大的缓冲 100K
	private File file;

	public void init() {
		// filePath = getServletContext().getInitParameter("file-upload");
//		filePath = "d:\\";
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html; charset=UTF-8");
		
		response.setContentType("text/html");
		
		isMultipart = ServletFileUpload.isMultipartContent(request);
		PrintWriter out = response.getWriter();
		if (!isMultipart) {
			out.println("<html>");
			out.println("<head>");
//			out.println("<title>Servlet upload</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<p>No file uploaded</p>");
			out.println("</body>");
			out.println("</html>");
			return;
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// maximum size that will be stored in memory
		factory.setSizeThreshold(maxMemSize);
		// Location to save data that is larger than maxMemSize.
//		factory.setRepository(new File("c:\\temp"));
		factory.setRepository(new File(CommParameter.getCommParameterByKey("maxMemPath")));

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// maximum file size to be uploaded.
		upload.setSizeMax(maxFileSize);

		try {
			// Parse the request to get file items.
			
			List<?> fileItems = upload.parseRequest(request);

			// Process the uploaded file items
			Iterator<?> i = fileItems.iterator();

			out.println("<html>");
			out.println("<head>");
//			out.println("<title>Servlet upload</title>");
			out.println("</head>");
			out.println("<body>");
			while (i.hasNext()) {
				FileItem fi = (FileItem) i.next();
				String fieldName = fi.getFieldName();//name属性
//				System.out.println(fieldName + fi.isFormField());
				if("userID".equals(fieldName)){
					System.out.println(fi.getString());
					System.out.println(new String(fi.getString().getBytes("ISO-8859-1"),"UTF-8"));
//					System.out.println(URLDecoder.decode(fi.getString(), "utf-8"));
//					System.out.println(URLDecoder.decode(fi.getString(), "gbk"));
//					System.out.println(URLDecoder.decode(fi.getString(), "gb2312"));
//					System.out.println(URLDecoder.decode(fi.getString(), "ISO8859-1"));
					continue;
				}
				if (!fi.isFormField()) {
					// Get the uploaded file parameters
					if(!"file".equals(fieldName)){
						System.out.println("not file");
						continue;
					}
					String fileName = fi.getName();
					String contentType = fi.getContentType();
					if(!contentType.startsWith("image/")){
						System.out.println("not image");
						continue;
					}
//					boolean isInMemory = fi.isInMemory();
//					long sizeInBytes = fi.getSize();
					// Write the file
					if (fileName.lastIndexOf("\\") >= 0) {
						file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\")));
					} else {
						file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\") + 1));
					}
					fi.write(file);
					out.println("Uploaded Filename的: " + fileName + "<br>");
				}
			}
			out.println("</body>");
			out.println("</html>");
			
			//调用 dll操作....
			
		} catch (Exception ex) {
			System.out.println(ex);
		}finally {
			// dll操作完后删除上传的文件【单线程】
//			if(file!=null&&file.isFile()&&file.exists()){
//				file.delete();
//			}
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
		throw new ServletException("GET method used with " + getClass().getName() + ": POST method required.");
	}
}
