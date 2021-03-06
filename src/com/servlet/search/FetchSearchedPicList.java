package com.servlet.search;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.bo.img.ImgBO;
import com.util.CommUtil;
import com.util.JsonUtil;

// 3.7  根据userid查找已经检索完的图片列表

public class FetchSearchedPicList extends HttpServlet {

	private final static Logger log = Logger.getLogger(FetchSearchedPicList.class);
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		Map<String,String> outMap = new HashMap<String, String>();
		List<Map<String,String>> l = null;
		try {
			
			String token = req.getParameter("token")==null?"":req.getParameter("token").trim();
			if(!CommUtil.tokenMapCheck(token)&&!"***".equals(token)){
				outMap.put("getCode", "-1");
				outMap.put("getMessage", "token过期");
			}else{
				String userID = req.getParameter("username")==null?"":req.getParameter("username").trim();//13300000000
				ImgBO imgBO = new ImgBO();
				
				l = imgBO.getSearchedPicList(userID);
				
				outMap.put("getCode", "0");
				outMap.put("getMessage", "获取成功");
			}
			
			String sentStr = JsonUtil.generalMixJson(l, outMap, "getList");
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
