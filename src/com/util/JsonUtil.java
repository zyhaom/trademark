package com.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
//import net.sf.json.JsonConfig;
//import net.sf.json.util.PropertyFilter;
import com.google.gson.Gson;

//import com.vo.scene.SubmitTaskVO;

public class JsonUtil {

	//
	public static String generalMixJson(List<Map<String,String>> list,Map<String,String> map,String listName){
		
		String json = mapToJson(map);
		json = json.replace("}", ",");
		json += "\""+listName+"\":"+listToJson(list);
		json += "}";
//		System.out.println(json);
		return json;
	}
	
	/**ok
	 * 
	 * @author : haoming
	 * @date : 2012-8-20下午01:52:07
	 * @Unity_web com.action.phone.mp.JsonUtil.java.javaToJson
	 * @returnType : void
	 * @desc :java对象转换为json对象
	 */
//	@SuppressWarnings("static-access")
	/*public static void javaToJson() {
		DeviceVO vo = new DeviceVO();
		vo.setDependDeviceID("?");
//		vo.setDeviceID("?");
		vo.setDeviceName("?");
		vo.setDeviceStat(0);
		vo.setDeviceType(0);
		vo.setIfvalid(0);
		vo.setUserID("?");
		String json=new JSONArray().fromObject(vo).toString();
    	System.out.println("json: "+json); 
	}*/
	
	//带过滤字段的beanToJson方法
	/*public static String beanVOToJson(Object vo,final List filterList) {
		String returnStr="";
//		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
    	if(filterList != null){
    		JsonConfig jsonConfig=new JsonConfig();
        	PropertyFilter filter = new PropertyFilter() {
    			
    			@Override
    			public boolean apply(Object arg0, String arg1, Object arg2) {
    				// Object o  String fieldName  Stirng fieldValue
    				if(filterList.contains(arg1)){
    					return true;
    				}else{
    					return false;
    				}
    			}
    		};
    		jsonConfig.setJsonPropertyFilter(filter);
//    		returnStr = jsonArray.fromObject(vo, jsonConfig).toString();
    		
    		returnStr = jsonObject.fromObject(vo, jsonConfig).toString();
    	}else{
//    		returnStr = jsonArray.fromObject(vo).toString();
    		returnStr = jsonObject.fromObject(vo).toString();
    	}
    	
//    	System.out.println(returnStr);
    	return returnStr;
	}*/
	
	/**
	 * @author : haoming
	 * @date : 2012-8-20下午01:45:54
	 * @Unity_web com.action.phone.mp.JsonUtil.java.jsonToJava
	 * @returnType : void
	 * @desc :json对象转换为java对象
	 */
	@SuppressWarnings("static-access")
	public static Object jsonToJava(String jsonStr,Class<?> beanClass) {
//		String jsonStr = "[{\"addTime\":\"2011-09-19 14:23:02\",\"iccid\":\"1111\",\"id\":0,\"imei\":\"2222\",\"imsi\":\"3333\",\"phoneType\":\"4444\",\"remark\":\"aaaa\",\"tel\":\"5555\"}]"; // 接收{}对象，此处接收数组对象会有异常
		if (jsonStr.indexOf("[") != -1) {
			jsonStr = jsonStr.replace("[", "");
		}
		if (jsonStr.indexOf("]") != -1) {
			jsonStr = jsonStr.replace("]", "");
		}
		JSONObject obj = new JSONObject().fromObject(jsonStr);
		return JSONObject.toBean(obj,beanClass);
	}
	
	public static  <T> List<T> getJavaCollection(T clazz, String jsons) {
		List<T> objs = null;
		JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(jsons);
		if (jsonArray != null) {
			objs = new ArrayList<T>();
			List list = (List) JSONSerializer.toJava(jsonArray);
			for (Object o : list) {
				JSONObject jsonObject = JSONObject.fromObject(o);
				T obj = (T) JSONObject.toBean(jsonObject, clazz.getClass());
				objs.add(obj);
			}
		}
		return objs;
	}
	
	/** ok
	 * 从json HASH表达式中获取一个map
	 * @param jsonString
	 * @return map
	 * @desc json 转换成map
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getMap4Json(String jsonString) {
		
		/*if(jsonString.startsWith("\\u")){
			jsonString = UnicodeUtil.unicodeToStr(jsonString);
		}*/
		
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Iterator<?> keyIter = jsonObject.keys();
		String key;
		Object value;
		Map valueMap = new HashMap();
		while (keyIter.hasNext()) {
			key = (String) keyIter.next();
			value = jsonObject.get(key);
			valueMap.put(key, value);
		}
		return valueMap;
	}
	
	/**
	 * @Author:HaoMing(郝明)
	 * @Project_name:ipc_ty
	 * @Full_path:com.comm.util.JsonUtil.java
	 * @Date:@2015 2015-12-14 上午9:31:56
	 * @Return_type:void
	 * @Desc :
	 */
	public static void main(String[] args) {
		
		
		/*String jsonStr = "";
		StringBuffer sb = new StringBuffer();
		sb.append("[                              ".trim());
		sb.append("{                                       ".trim());
		sb.append("  		\"ID\": \"\",                  ".trim());
		sb.append("      \"startTime\": \"1\",             ".trim());
		sb.append("      \"repeatModeFix\": \"1\",         ".trim());
		sb.append("      \"userID\": \"188\",              ".trim());
		sb.append("      \"conditionDeviceID\": \"1\",     ".trim());
		sb.append("      \"taskDeviceID\": \"1\",          ".trim());
		sb.append("      \"sceneID\": \"100\",             ".trim());
		sb.append("      \"endTime\": \"1\",               ".trim());
		sb.append("      \"conditionID\": \"1\",           ".trim());
		sb.append("      \"fixedTime\": \"1\",             ".trim());
		sb.append("      \"repeatModeSegment\": \"1\",     ".trim());
		sb.append("      \"taskID\": \"1\"                 ".trim());
		sb.append("},                                       ".trim());
		sb.append("{                                       ".trim());
		sb.append("  		\"ID\": \"\",                  ".trim());
		sb.append("      \"startTime\": \"1\",             ".trim());
		sb.append("      \"repeatModeFix\": \"1\",         ".trim());
		sb.append("      \"userID\": \"188\",              ".trim());
		sb.append("      \"conditionDeviceID\": \"1\",     ".trim());
		sb.append("      \"taskDeviceID\": \"1\",          ".trim());
		sb.append("      \"sceneID\": \"100\",             ".trim());
		sb.append("      \"endTime\": \"1\",               ".trim());
		sb.append("      \"conditionID\": \"1\",           ".trim());
		sb.append("      \"fixedTime\": \"1\",             ".trim());
		sb.append("      \"repeatModeSegment\": \"1\",     ".trim());
		sb.append("      \"taskID\": \"1\"                 ".trim());
		sb.append("}                                       ".trim());
		sb.append("]                              ".trim());
		jsonStr = sb.toString();
		
		List<SubmitVO> list = new JsonUtil().getJavaCollection(new SubmitVO(), jsonStr);
		for (SubmitVO vo : list) {
			System.out.println(vo.getConditionDeviceID());
		}*/
		
		
		/*String json = "{\"result\":{\"data\":{\"accessToken\":\"at.0m3qcek2ai87pfhhduk261w0an0himlu-5mk8cftnz1-1ws5v5k-bp8oidfah\",\"userId\":\"4675d304ef3f0e39\"},\"code\":\"200\",\"msg\":\"操作成功!\"}}";
		Map map = new HashMap();
		
		while(true){
			map = getMap4Json(json);
			Iterator<?> it = map.keySet().iterator();
			if(it.hasNext()){
				String key=it.next().toString();
				String value = map.get(key).toString();
				if(!key.equals("accessToken")){
//					getMap4Json(value);
					json = value;
				}else{
					System.out.println(value);
					break;
				}
			}
		}*/
		
//		javaToJson();
		
		/*DeviceVO vo = new DeviceVO();
		vo.setDependDeviceID("?");
		vo.setDeviceID("?");
		vo.setDeviceName("?");
		vo.setDeviceStat(0);
		vo.setDeviceType(0);
		vo.setIfvalid(0);
		vo.setUserID("?");
		
		List<String> list = new ArrayList<String>();
		list.add("deviceID");
		list.add("deviceStat");
		System.out.println(beanVOToJson(vo, list));*/
		
//		Map<String,String> map = new HashMap<String, String>();
//		map.put("deviceID", "123123132");
//		map.put("deviceName", "jkjkljkljkl");
//		System.out.println(mapToJson(map));
//		
//		map = (Map<String, String>) jsonToMap(mapToJson(map));
//		System.out.println(map.get("deviceName"));
		
		
//		List<CameraInfo> list = new ArrayList<CameraInfo>();
//		list.add(new CameraInfo("24242342343","某摄像机1","123456"));
//		list.add(new CameraInfo("24242342344","某摄像机2","123457"));
//		list.add(new CameraInfo("24242342345","某摄像机3","123458"));
//		System.out.println(listToJson(list));
		
//		Set<Object> set = new HashSet<Object>();
		
//		set.add("1");
//		set.add("2");
//		set.add("2");
		
//		System.out.println(setToJson(null));
		
	}
	
	/**
	 * 
	 * @Author:HaoMing(郝明)
	 * @Project_name:bj_xyb
	 * @Full_path:com.util.JsonUtil.java
	 * @Date:@2015 2015-12-16 上午10:44:09
	 * @Return_type:Map<?,?>
	 * @Desc : map转换成json串
	 */
	public static Map<?,?> jsonToMap(String jsonjStr){
		Map<Object,Object> map = new HashMap<Object, Object>();
		JSONObject jsonObject = JSONObject.fromObject(jsonjStr);
		Iterator<?> keyIter = jsonObject.keys();
		String key;
		Object value;
		while (keyIter.hasNext()) {
			key = (String) keyIter.next();
			value = jsonObject.get(key);
			map.put(key, value);
		}
		return map;
	}
	
	/**
	 * 
	 * @Author:HaoMing(郝明)
	 * @Project_name:bj_xyb
	 * @Full_path:com.util.JsonUtil.java
	 * @Date:@2015 2015-12-16 上午10:44:31
	 * @Return_type:String
	 * @Desc :json串转换成map
	 */
	public static String mapToJson(Map<?,?> map){
		if(map.isEmpty()){
			return "";
		}
		
		String jsonStr = "";
		JSONObject jsonObject = JSONObject.fromObject(map);
//		System.out.println(jsonObject.toString());
		jsonStr = jsonObject.toString();
		return jsonStr;
	}
	
	public static String listToJson(List<?> list){
		return new Gson().toJson(list);
	}
	
	public static String setToJson(Set<?> set){
		return new Gson().toJson(set);
	}
	
}
