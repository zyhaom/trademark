package com.servlet.option;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.bo.option.TableSBPropBO;
import com.dll.tm.TradeMarkDll;

public class SensitivePic extends HttpServlet {

	private final static Logger log = Logger.getLogger(SensitivePic.class);
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html;charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		req.setCharacterEncoding("utf-8");
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		if (isMultipart) {
			try {
				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setHeaderEncoding("UTF-8");
				List<FileItem> items = upload.parseRequest(req);
				String newName = System.currentTimeMillis()+".jpg";
				String dir = "E:\\trademark\\img\\00\\01"+File.separator+newName;
				
				String returnName = null;
				for (FileItem item : items) {
					if (item.isFormField()) {// 如果文本类型参数
						String name = item.getFieldName();
						String value = item.getString("UTF-8");
						System.out.println(name + "=" + value);
					} else {// 如果文件类型参数
						File saveFile = new File(dir);
						returnName = item.getName();
						item.write(saveFile);
					}
				}
				if(returnName!=null){
					out.print(returnName);
				}
				out.print("上传成功");
			} catch (Exception e) {
				e.printStackTrace();
				out.print("error");
			}finally{
				out.close();
			}
		} 
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		doPost(req, resp);
	}
	
	private void upfile(HttpServletRequest request,String imgFullPathStr) throws FileNotFoundException, IOException {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
          
        if (isMultipart) {
             
            // 创建工厂（这里用的是工厂模式）
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //获取汽车零件清单与组装说明书（从ServletContext中得到上传来的数据）
            ServletContext servletContext = this.getServletConfig().getServletContext();
            File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
            //工厂把将要组装的汽车的参数录入工厂自己的系统，因为要根据这些参数开设一条生产线（上传来的文件的各种属性）
            factory.setRepository(repository);
            //此时工厂中已经有了汽车的组装工艺、颜色等属性参数（上传来的文件的大小、文件名等）
            //执行下面的这一行代码意味着根据组装工艺等开设了一条组装生产线
            ServletFileUpload upload = new ServletFileUpload(factory);
  
            //解析请求
            try {
                //把零件送给生产线，出来的就是一辆组装好的汽车（把request转成FileItem的实例）
                List<FileItem> items = upload.parseRequest(request);
                Iterator<FileItem> iter = items.iterator();
                while (iter.hasNext()) {
                    FileItem item = iter.next();
                    //创建文件输出流
                    File file=new File(imgFullPathStr);
                    if(!file.exists()){
                        try {   
                            file.createNewFile();   
                        } catch (IOException e) {   
                            e.printStackTrace();   
                        }   
                    }
                    FileOutputStream fos=new FileOutputStream(file);                 
                    //创建输入流
                    InputStream fis=(InputStream) item.getInputStream();
                    //从输入流获取字节数组
                    byte b[]=new byte[1];
                    //读取一个输入流的字节到b[0]中
                    int read=fis.read(b);
                    while(read!=-1) { 
                        fos.write(b,0,1); 
                        read=fis.read(b); 
                    } 
                    fis.close();
                    fos.flush();
                    fos.close();
                    //打印List中的内容（每一个FileItem的实例代表一个文件，执行这行代码会打印该文件的一些基本属性，文件名，大小等）
//                    System.out.println(item);  
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
        }
	}
	
	public static void main(String[] args) {
		String p = "D:\\html5\\code\\Folder.jpg";
		System.out.println(p);
		File f = new File(p);
		System.out.println(f.getName());
		System.out.println(f.getParent());
		p = p.substring(0, p.lastIndexOf("\\"));
		System.out.println(p);
	}
}
