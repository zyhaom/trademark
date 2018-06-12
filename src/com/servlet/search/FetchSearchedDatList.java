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
import com.util.prop.CommParameter;

// 3.9  根据

public class FetchSearchedDatList extends HttpServlet {

	private final static Logger log = Logger.getLogger(FetchSearchedDatList.class);
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
//				String userID = req.getParameter("username")==null?"":req.getParameter("username").trim();
				String typeid = req.getParameter("clsid")==null?"":req.getParameter("clsid").trim();
				String recordId = req.getParameter("fid")==null?"":req.getParameter("fid").trim();
				String totalpage = req.getParameter("totalpage")==null?"1":req.getParameter("totalpage").trim();//第几页
				ImgBO imgBO = new ImgBO();
				
				l = imgBO.getSearchedDatList(recordId, typeid,Integer.parseInt(CommParameter.getCommParameterByKey("pageSize")),Integer.parseInt(totalpage));
				
				outMap.put("getCode", "0");
				outMap.put("getMessage", "获取成功");
				
				Map<String,String> m = imgBO.getClassName(typeid);
				m.put("totalpage", totalpage);
				m.put("pagenum", ""+CommUtil.getTotalPage(imgBO.getSearchedDatListToTalnum(recordId, typeid), Integer.parseInt(CommParameter.getCommParameterByKey("pageSize"))));
				outMap.putAll(m );
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
