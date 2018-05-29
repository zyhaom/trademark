package com.servlet.option;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.bo.option.TableSBPropBO;
import com.util.CommUtil;
import com.util.JsonUtil;

//自动生成属性记录在t_sbprop表中
public class TableSBPropInit extends HttpServlet {

	private final static Logger log = Logger.getLogger(TableSBPropInit.class);
	private static final long serialVersionUID = 1L;
//		String root = "D:\\ftp_folder\\trademark\\dat";
	static String root = "E:\\trademark\\dat\\01\\01";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		
		try {
			
//				{"datDirFullPath":"?","featureDirFullPath":"?"}
			String s = CommUtil.fetchPostStrFun(req);
			String datDirFullPath = JsonUtil.getMap4Json(s).get("datDirFullPath").toString();
			
			TableSBPropBO tableSBPropBO = new TableSBPropBO();
			//1.清空  t_sbprop
//			tableSBPropBO.trancateTable("t_sbprop");
			
			//2.初始化 t_sbprop 
//			insert into `t_sbprop` (`datPathID`, `typeID`, `sbName`, `reqID`, `reqStartDate`, `reqMan`, `reqFinishDate`, `stat`) values('###','00','无结果','',NULL,NULL,NULL,'0');
//			insert into `t_sbprop` (`datPathID`, `typeID`, `sbName`, `reqID`, `reqStartDate`, `reqMan`, `reqFinishDate`, `stat`) values('01\\1','01','完达山','1000100','2017-03-20','某某某','2017-03-24','1');
			
//			INSERT INTO t_sbprop (datPathID,typeID,sbName,reqID,reqStartDate,reqMan,reqFinishDate,stat) 
//			VALUES ('key','typeID','商标名称','商标编号100001','2016-03-20','商标声请人','2017-03-20',2),
			
			Set<String> l = new HashSet<String>();
//			l.add("INSERT INTO t_sbprop (datPathID,typeID,sbName,reqID,reqStartDate,reqMan,reqFinishDate,stat) VALUES ('###','00','无结果','','2016-03-20','','2017-03-20',0);");
			sql(l,datDirFullPath);
			tableSBPropBO.initSql(l);
			
			out.print(l.size()+"\n"+l.toArray()[1].toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}
	
	private static void sql(Set<String> l,String path){
		File f = new File(path);
		if(f.isFile()){
			String id = f.toString().substring(0, f.toString().lastIndexOf(".dat"));
			id = id.replace("E:\\trademark\\dat\\", "");
			id = id.replaceAll("\\\\", "\\\\\\\\");
//			System.out.println(index+++"\t"+fs[i]+"\t"+id.replace(root, ""));
			l.add("INSERT INTO t_sbprop (datPathID,typeID,sbName,reqID,reqStartDate,reqMan,reqFinishDate,stat) " +
//					"VALUES ('"+id+"','"+id.substring(0, id.lastIndexOf("\\\\")).replaceAll("\\\\", "")+"','tradeName','tradNO_1','2016-03-20','trader','2017-03-20',2);");
					"VALUES ('"+id+"','"+id.substring(0, id.lastIndexOf("\\\\")).replaceAll("\\\\", "")+"','tradeNameT','tradNO_2','2017-07-31','trader','2017-08-01',2);");
			
		}else{
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
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		doPost(req, resp);
	}
	
	public static void main(String[] args) {
		//E:\trademark\dat
//		fileFunc(root);
		
//		int index = 1;
		Set<String> l = new HashSet<String>();
		l.add("INSERT INTO t_sbprop (datPathID,typeID,sbName,reqID,reqStartDate,reqMan,reqFinishDate,stat) VALUES ('###','00','无结果','','','','',0);");
		sql(l,root);
		
		try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter("e:\\del.sql", false);
            writer.write("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		Iterator<?> it = l.iterator();
		while(it.hasNext()){
//			System.out.println(index+++"\t"+it.next());
			
			try {
	            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
	            FileWriter writer = new FileWriter("e:\\del.sql", true);
	            writer.write((String)it.next()+"\n");
	            writer.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		
	}
	
	private static void fileFunc(String path){
		int index = 1;
		File f = new File(path);
		File[] fs = f.listFiles();
		for (int i = 0; i < fs.length; i++) {
			if(fs[i].isDirectory()){
				System.out.println(fs[i]);
				fileFunc(fs[i].toString());
			}else{
				String id = fs[i].toString().substring(0, fs[i].toString().lastIndexOf(".dat"));
				System.out.println(index+++"\t"+fs[i]+"\t"+id.replace(root, ""));
			}
		}
	}
}
