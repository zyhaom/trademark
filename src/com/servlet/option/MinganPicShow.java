package com.servlet.option;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class MinganPicShow extends HttpServlet {

	private final static Logger log = Logger.getLogger(MinganPicShow.class);
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		
		String returnStr = "E:\\trademark\\img\\00\\01";
		
		try {
			List<String> list = new ArrayList<String>();
			
			File[] lf = new File("E:\\trademark\\img\\00\\01").listFiles();
			for (int i = 0; i < lf.length; i++) {
				list.add(lf[i].getName());
			}
			
			Collections.reverse(list);
			Iterator<String> it = list.iterator();
			String rs = "";
			while(it.hasNext()){
//				System.out.println(it.next());
				rs+=it.next()+"~";
			}
			rs = rs.substring(0, rs.length()-1);
			out.print(rs);
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
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		
		String path = "E:\\trademark\\img\\00\\01";
		File f = new File(path);
		File[] lf = f.listFiles();
		for (int i = 0; i < lf.length; i++) {
			System.out.println(lf[i].getName());
			list.add(lf[i].getName());
		}
		
		
		Collections.sort(list);  
		System.out.println(list);
		
		Collections.reverse(list);
		System.out.println(list);
		System.out.println(list.toString());
		Iterator<String> it = list.iterator();
		String rs = "";
		while(it.hasNext()){
//			System.out.println(it.next());
			rs+=it.next()+"~";
		}
		rs = rs.substring(0, rs.length()-1);
		
		System.out.println(rs);
	}
}
