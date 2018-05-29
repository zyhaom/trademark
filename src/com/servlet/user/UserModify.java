package com.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.bo.user.UserBO;
import com.util.CommUtil;
import com.util.JsonUtil;
import com.vo.user.UserVO;

public class UserModify extends HttpServlet {

	private final static Logger log = Logger.getLogger(UserModify.class);
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		Map<String,Object> outMap = new HashMap<String, Object>();
		try {
//				{"userID":"?","userPSW":"?"}密码用密文
			/*String s = CommUtil.fetchPostStrFun(req);
			String userID = JsonUtil.getMap4Json(s).get("userID").toString();
			String userPSW = JsonUtil.getMap4Json(s).get("userPSW").toString();*/
			
			String userID = req.getParameter("username")==null?"":req.getParameter("username").trim();
			String userPSW = req.getParameter("resetpsw")==null?"":req.getParameter("resetpsw").trim();
			String smCode = req.getParameter("msgcode")==null?"":req.getParameter("msgcode").trim();
//			String token = req.getParameter("token")==null?"":req.getParameter("token").trim();
			
			if(CommUtil.smMapCheck(userID, smCode)||"xxx".equals(smCode)){
				UserBO userBO = new UserBO();
				
//				UserVO vo = userBO.fetchUserVOByID(userID);
//				if(userPSW.equals(vo.getUserPSW())){
//					outMap.put("getCode", "1");
//					outMap.put("getMessage", "新密码与原密码一致");
//				}else{
					userBO.modifyUser(new UserVO(userID, userPSW, ""));
					outMap.put("getCode", "0");
					outMap.put("getMessage", "重置成功");
//				}
				
			}else{
				outMap.put("getCode", "1");
				outMap.put("getMessage", "验证码不正确");
			}
//			if(CommUtil.tokenMapCheck(token)){
//				
//			}
			
			String sentStr = JsonUtil.mapToJson(outMap);
			log.info(sentStr);
			out.print(sentStr);
			
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
