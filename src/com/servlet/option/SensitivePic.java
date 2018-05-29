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
		resp.setContentType("text/html;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		// String relevantType = req.getParameter("relevantType")==null?"":req.getParameter("relevantType").trim();
		// String typeID = req.getParameter("typeID")==null?"":req.getParameter("typeID").trim();
		// String option = req.getParameter("option")==null?"":req.getParameter("option").trim();//option : del 删除，ins 增加 ，sel 查询

		try {
			// 0.收到文件
			String newName = System.currentTimeMillis()+".jpg";
			String senPicPath = "E:\\trademark\\img\\00\\01"+File.separator+newName;
			upfile(req, senPicPath);
			log.info("新增的[敏感]图片全路径 : "+senPicPath);
			
			// 1.生成特征文件
			String picDirFullPath = senPicPath;// [敏感]图片全路径
			String featureDirFullPath = picDirFullPath.substring(0, picDirFullPath.lastIndexOf("\\")).replaceAll("img", "dat");
			TradeMarkDll.callInit(picDirFullPath, featureDirFullPath, false);

			// 2.初始化sbprop表
			TableSBPropBO tableSBPropBO = new TableSBPropBO();

			Set<String> l = new HashSet<String>();
//			String datDirFullPath = featureDirFullPath + ".dat";
			String id = new File(picDirFullPath).getName();
			// id = id.replace("E:\\trademark\\dat\\", "");
			id = id.replaceAll("\\\\", "\\\\\\\\");
			l.add("INSERT INTO t_sbprop (datPathID,typeID,sbName,reqID,reqStartDate,reqMan,reqFinishDate,stat) " + "VALUES ('" + id + "','"
					+ id.substring(0, id.lastIndexOf("\\\\")).replaceAll("\\\\", "") + "','敏感图片','tradNO_2','2017-07-31','trader','2017-08-01',2);");

			tableSBPropBO.initSql(l);

			out.print("ok:"+senPicPath);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
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
          
//        if (isMultipart) {
             
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
                @SuppressWarnings("unchecked")
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
//        }
	}
	private static void sql(Set<String> l,String path){
		File f = new File(path);
		File[] fs = f.listFiles();
		for (int i = 0; i < fs.length; i++) {
			if(fs[i].isDirectory()){
				sql(l,fs[i].toString());
			}else{
				String id = fs[i].toString().substring(0, fs[i].toString().lastIndexOf(".dat"));
				id = id.replace("E:\\trademark\\dat\\", "");
				id = id.replaceAll("\\\\", "\\\\\\\\");
//					System.out.println(index+++"\t"+fs[i]+"\t"+id.replace(root, ""));
				l.add("INSERT INTO t_sbprop (datPathID,typeID,sbName,reqID,reqStartDate,reqMan,reqFinishDate,stat) " +
//							"VALUES ('"+id+"','"+id.substring(0, id.lastIndexOf("\\\\")).replaceAll("\\\\", "")+"','tradeName','tradNO_1','2016-03-20','trader','2017-03-20',2);");
						"VALUES ('"+id+"','"+id.substring(0, id.lastIndexOf("\\\\")).replaceAll("\\\\", "")+"','tradeNameT','tradNO_2','2017-07-31','trader','2017-08-01',2);");
			}
		}
	}
	
}
