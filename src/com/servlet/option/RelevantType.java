package com.servlet.option;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.bo.option.RelevantBO;

public class RelevantType extends HttpServlet {

	private final static Logger log = Logger.getLogger(RelevantType.class);
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		String relevantType = req.getParameter("relevantType")==null?"":req.getParameter("relevantType").trim();
		String typeID = req.getParameter("typeID")==null?"":req.getParameter("typeID").trim();
		String option = req.getParameter("option")==null?"":req.getParameter("option").trim();//option : del 删除，ins 增加 ，sel 查询
		
		String returnStr = "";
		
		try {
			RelevantBO rBO = new RelevantBO();
			if(option.equals("ins")){
				rBO.addRelevantType(typeID, relevantType);
			}
			if(option.equals("del")){
				rBO.delRelevantType(typeID, relevantType);
			}
			if(option.equals("sel")){
				List<String> list = rBO.getRelevantTypes(typeID);
				
				Iterator<String> it = list.iterator();
				while(it.hasNext()){
					returnStr += it.next();
					returnStr += "~";
				}
				returnStr = returnStr.substring(0, returnStr.length()-1);
				out.print(returnStr);
			}
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
