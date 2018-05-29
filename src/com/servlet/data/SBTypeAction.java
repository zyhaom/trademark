package com.servlet.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.bo.SBTypeBO;
import com.util.JsonUtil;
import com.util.UnicodeUtil;
import com.util.prop.CommParameter;
import com.vo.SBTypeVO;

public class SBTypeAction extends HttpServlet {

	private final static Logger log = Logger.getLogger(SBTypeAction.class);
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		try {
			if(CommParameter.SBTYPEJSON.length()==0){
		//		接收客户端的消息  查询一级类型什么也不发；查询二级类型将依赖类型的ID发来
		//		{"dependTypeID":"?"}
				BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
				String line = "";
				String dependTypeID = null;
				StringBuffer sb = new StringBuffer();
				while ((line = br.readLine()) != null){
					sb.append(line);
				}
				
				if(sb.length()!=0){
					String s = sb.toString();
					if(s.contains("\\u")){
						s = UnicodeUtil.unicodeToStr_za(s);
					}
		//			log.info(s);
					dependTypeID = JsonUtil.getMap4Json(s).get("dependTypeID").toString();
				}
				
				
				SBTypeBO sbtypeBO = new SBTypeBO();
				
				Map<String,Map<String, String>> map_m = new TreeMap<String,Map<String, String>>();
				Map<String,List<Map<String,String>>> map_l = new TreeMap<String,List<Map<String,String>>>();
				
				List<SBTypeVO> list = sbtypeBO.fetchSBTypeVOList(dependTypeID);
				Iterator<?> it = list.iterator();
				while(it.hasNext()) {
					SBTypeVO vo = (SBTypeVO)it.next();
					if("-1".equals(vo.getDependTypeID())) {
						Map<String, String> mValue = new HashMap<String, String>();
						mValue.put("typeID", vo.getTypeID());
						mValue.put("typeName", vo.getTypeName());
						map_m.put(vo.getTypeID(),mValue);
						
						map_l.put(vo.getTypeID(), new ArrayList<Map<String,String>>());
					}else{
						List<Map<String,String>> l = map_l.get(vo.getDependTypeID());
						Map<String, String> mValue = new HashMap<String, String>();
						mValue.put("typeID", vo.getTypeID());
						mValue.put("typeName", vo.getTypeName());
						l.add(mValue);
						map_l.put(vo.getDependTypeID(), l);
					}
				}
				
	//			String sentStr = JsonUtil.listToJson(list);
				String sentStr = "[";
				it = map_m.keySet().iterator();
				while(it.hasNext()) {
					String key = (String)it.next();
					map_m.get(key);
					map_l.get(key);
					
					sentStr += JsonUtil.generalMixJson(map_l.get(key), map_m.get(key), "littleCategory");
					sentStr += ",";
				}
				sentStr = sentStr.substring(0, sentStr.length()-1);
				sentStr += "]";
				log.info(sentStr);
				out.print(sentStr);
	//			out.print(UnicodeUtil.strToUnicode(sentStr));
				CommParameter.SBTYPEJSON = sentStr;
			}else{
				log.info(CommParameter.SBTYPEJSON);
				out.print(CommParameter.SBTYPEJSON);
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
