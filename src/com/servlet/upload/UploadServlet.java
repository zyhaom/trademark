package com.servlet.upload;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;

import com.util.CommUtil;
import com.util.JsonUtil;
import com.util.prop.CommParameter;

//import com.util.UnicodeUtil;

public class UploadServlet extends HttpServlet {
	private final static Logger log = Logger.getLogger(UploadServlet.class);
	private static final long serialVersionUID = 290053520185043817L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		doPost(req, resp);
	}
	
	//设置保存上传文件的目录 
//	String uploadDir = "";
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
//		response.setHeader("content-type","text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		Map<String,Object> outMap = new HashMap<String, Object>();
	
		try {
			String token = request.getParameter("token")==null?"":request.getParameter("token").trim();
			if(!CommUtil.tokenMapCheck(token)&&false){
				outMap.put("getCode", "2");
				outMap.put("getMessage", "token过期");
				outMap.put("geturl", "");
			}else{
//				String uploadDir = getServletContext().getRealPath("/uploadDir");
				String uploadDir = CommParameter.getCommParameterByKey("tempDir")+File.separator+"img";
				
				if (uploadDir == null) {
					out.println("无法访问存储目录！");
					return;
				}
				File fUploadDir = new File(uploadDir);
				if (!fUploadDir.exists()) {
					if (!fUploadDir.mkdirs()) {
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
				List<?> fileItems = null;
				try {
					fileItems = fu.parseRequest(request);
				} catch (FileUploadException e) {
					out.println("解析数据时出现如下问题：");
					e.printStackTrace(out);
					return;
				}

				String tempImg="";
				//处理每个表单字段 
				Iterator<?> i = fileItems.iterator();
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
//						
//							tempImg = "pn_"+System.currentTimeMillis()+pathSrc.substring(pathSrc.lastIndexOf("."));
							String postFIx =".jpg";
							int dotIndex = pathSrc.lastIndexOf(".");
//							dotIndex=-1;
							if(dotIndex>-1){
								postFIx = pathSrc.substring(dotIndex);
							}
							String userID = request.getAttribute("username")==null?"pn":request.getAttribute("username").toString().trim();
							tempImg = userID.toString()+"_"+System.currentTimeMillis()+postFIx;
							
							File pathDest = new File(uploadDir, tempImg);
							fi.write(pathDest);
							
//							tempImg = "http://101.200.46.114:8088/show/img/temp/img/"+tempImg;
							tempImg = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/show/img/"+"temp/img/"+tempImg;
							
						} catch (Exception e) {
							out.println("存储文件时出现如下问题：");
							e.printStackTrace(out);
							return;
						} finally {//总是立即删除保存表单字段内容的临时文件 
							fi.delete();
						}
					}
				}
				
//				System.out.println(request.getAttribute("username"));//ok
//				System.out.println(UnicodeUtil.unicodeToStr_za(request.getAttribute("text_name1").toString()));//ok ?传参
				
				outMap.put("getCode", "0");
				outMap.put("getMessage", "上传成功");
				outMap.put("geturl", tempImg);
			}
			
			String sentStr = JsonUtil.mapToJson(outMap);
			log.info(sentStr);
			out.print(sentStr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}
	
//	public static void main(String[] args){
//		  System.out.println("默认的临时文件路径: " + System.getProperty("java.io.tmpdir"));
//	}
}



