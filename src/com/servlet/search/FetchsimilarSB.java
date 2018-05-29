package com.servlet.search;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.bo.option.PreSearchBO;
import com.util.CommUtil;
import com.util.JsonUtil;

public class FetchsimilarSB extends HttpServlet {

	private final static Logger log = Logger.getLogger(FetchsimilarSB.class);
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		Map<String,Object> outMap = new HashMap<String, Object>();
		
		try {
			
			String token = req.getParameter("token")==null?"":req.getParameter("token").trim();
			if(!CommUtil.tokenMapCheck(token)&&!"***".equals(token)){
				outMap.put("getCode", "-1");
				outMap.put("getMessage", "token过期");
			}else{
				String userID = req.getParameter("username")==null?"":req.getParameter("username").trim();//13300000000
//				String inputtxt = req.getParameter("inputtxt")==null?"":req.getParameter("inputtxt").trim();
				String picPath = req.getParameter("pic")==null?"":req.getParameter("pic").trim();//1.png
				String stat = req.getParameter("status")==null?"0":req.getParameter("status").trim();//0
				String bigclass = req.getParameter("bigclass")==null?"":req.getParameter("bigclass").trim();//01
				String smallclass = req.getParameter("smallclass")==null?"":req.getParameter("smallclass").trim();//0101$0102
				String precisionNum = req.getParameter("precision")==null?"0.7":req.getParameter("precision").trim();
				
				//0.增加预检索记录
				PreSearchBO preSearchBO = new PreSearchBO();
				String timeGLWZ = preSearchBO.IsThisImgSearched(userID, picPath, bigclass, smallclass,precisionNum);
				if(timeGLWZ!=null){//重复检索
					outMap.put("getCode", "1");
					outMap.put("getMessage", "您在"+CommUtil.timeFormat(Long.parseLong(timeGLWZ))+"已经提交过该图片,请勿重复提交。");
					String sentStr = JsonUtil.mapToJson(outMap);
					log.info(sentStr);
					out.print(sentStr);
					return ;
				}
				
				if(preSearchBO.addPreSearch(userID, picPath, stat, bigclass, smallclass,precisionNum)>=1){
					outMap.put("getCode", "0");
					outMap.put("getMessage", "已经提交,等待执行。");
				}else{
					outMap.put("getCode", "1");
					outMap.put("getMessage", "提交异常。");
					String sentStr = JsonUtil.mapToJson(outMap);
					log.info(sentStr);
					out.print(sentStr);
					return;
				}
				
				//当前若没有检索任务,则启动检索。
				/*if(!CommUtil.hasPreSearchImg){
					CommUtil.hasPreSearchImg = true;
					new Thread(new Runnable() {
						public void run() {
							FetchsimilarSBAid.ThreadPoolFunc();
						}
					}).start();
				}*/
			}
			
			String sentStr = JsonUtil.mapToJson(outMap);
			log.info(sentStr);
			out.print(sentStr);
			
		} catch (Exception e) {
			log.info(e.getMessage());
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

//		System.out.println(TradeMarkDll.callInit("D:\\ftp_folder\\pic_search\\1","D:\\ftp_folder\\pic_search\\2"));
//		System.out.println(TradeMarkDll.callBuildFeature("D:\\ftp_folder\\pic_search\\1\\0.jpg", "D:\\ftp_folder\\pic_search\\3", "1.dat"));
//		System.out.println(TradeMarkDll.callSearch("D:\\ftp_folder\\pic_search\\3\\3-1.dat","D:\\ftp_folder\\pic_search\\3\\3-2.dat",0.25f));
	}
	
}
