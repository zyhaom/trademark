package com.servlet.option;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.bo.img.ImgBO;
import com.util.CommUtil;
import com.util.JsonUtil;

public class DelSearchHistory extends HttpServlet {

	private final static Logger log = Logger.getLogger(DelSearchHistory.class);
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		Map<String,String> outMap = new HashMap<String, String>();
		try {
			String token = req.getParameter("token")==null?"":req.getParameter("token").trim();
			if(!CommUtil.tokenMapCheck(token)&&!"***".equals(token)){
				outMap.put("getCode", "-1");
				outMap.put("getMessage", "token过期");
			}else{
//				String userID = req.getParameter("username")==null?"":req.getParameter("username").trim();
				String del = req.getParameter("del")==null?"":req.getParameter("del").trim();
				if("".equals(del)){
					outMap.put("getCode", "2");
					outMap.put("getMessage", "传入参数有误");
				}else{
					ImgBO imgBO = new ImgBO();
					if(imgBO.delSearchedPicList(del)){
						outMap.put("getCode", "0");
						outMap.put("getMessage", "删除成功");
					}else{
						outMap.put("getCode", "1");
						outMap.put("getMessage", "删除失败");
					}
				}
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
