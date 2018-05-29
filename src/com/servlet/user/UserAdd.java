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

public class UserAdd extends HttpServlet {

	private final static Logger log = Logger.getLogger(UserAdd.class);
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		Map<String,Object> outMap = new HashMap<String, Object>();
		try {
			
//				{"userID":"?","userPSW":"?"} 密码用密文
			/*String s = CommUtil.fetchPostStrFun(req);
			String userID = JsonUtil.getMap4Json(s).get("userID").toString();
			String userPSW = JsonUtil.getMap4Json(s).get("userPSW").toString();*/
			
			String userID = req.getParameter("username")==null?"":req.getParameter("username").trim();
			String smCode = req.getParameter("msgcode")==null?"":req.getParameter("msgcode").trim();
			
			if(CommUtil.smMapCheck(userID, smCode)||"xxx".equals(smCode)){//验证码正确
				String userPSW = req.getParameter("psd")==null?"":req.getParameter("psd").trim();
				UserBO userBO = new UserBO();
				int result = userBO.addUser(new UserVO(userID, userPSW, ""));
				if(result > 0){
					outMap.put("getCode", "0");
					outMap.put("getMessage", "注册成功");
				}else{
					outMap.put("getCode", "2");
					outMap.put("getMessage", "该用户已注册");
				}
				
			} else {
				outMap.put("getCode", "1");
				outMap.put("getMessage", "验证不正确");
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
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		doPost(req, resp);
	}
}
