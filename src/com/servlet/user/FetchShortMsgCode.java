package com.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.log4j.Logger;

import com.util.CommUtil;

public class FetchShortMsgCode extends HttpServlet {

//	private final static Logger log = Logger.getLogger(FetchShortMsgCode.class);
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		try {
			
//				{"userID":"?","userPSW":"?"}密码用密文
//			String s = CommUtil.fetchPostStrFun(req);
//			String userID = JsonUtil.getMap4Json(s).get("userID").toString();
//			String userPSW = JsonUtil.getMap4Json(s).get("userPSW").toString();
			
			String userID = req.getParameter("username")==null?"":req.getParameter("username").trim();
			
			String smCode = CommUtil.sendShortMessage(userID, null);
			if(smCode.length()==6){
				CommUtil.smMapAdd(userID, smCode);
			}else{
				out.print(smCode);
			}
			
			/*Map<String,Object> outMap = new HashMap<String, Object>();
			if(userPSW.equals(vo.getUserPSW())){
				String token = UUID.randomUUID().toString();
				CommUtil.tokenMapAdd(token, System.currentTimeMillis());
				
				outMap.put("getCode", "0");
				outMap.put("getMessage", "登录成功");
				outMap.put("token", token);
			}else{
				outMap.put("getCode", "1");
				outMap.put("getMessage", "用户名或密码不正确");
			}
			
			String sentStr = JsonUtil.mapToJson(outMap);
			log.info(sentStr);
			out.print(sentStr);*/
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
}
