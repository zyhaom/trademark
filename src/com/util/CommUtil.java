package com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

import com.util.prop.CommParameter;

public class CommUtil {
	public static String[] dayArray = null;
	private final static Logger log = Logger.getLogger(CommUtil.class);
	private static long seeeionTime = 1800000l;//token过期时长,单位：毫秒
//	private static long seeeionTime = 120000l;//token过期时长,单位：毫秒
	
	//短信验证码map<手机号，短信验证码>
	public static Map<String,String> smMap = new HashMap<String, String>();
	//<token(uuid),格林威治时间(精确到毫秒)>
	public static Map<String,Long> tokenMap = new HashMap<String, Long>();
	
	public static boolean hasPreSearchImg = false;
	public static JPushClient client = null;
	static {
		client = new JPushClient(CommParameter.getCommParameterByKey("jPush_masterSecret"), CommParameter.getCommParameterByKey("jPush_appKey"));
	}
	
	//暂存token
	public static void tokenMapAdd(String token,long time){
		tokenMap.put(token, time);
	}
	//判断token，并处理。
	public static boolean tokenMapCheck(String token){
		long now = System.currentTimeMillis();
		long tokenTime = tokenMap.get(token)==null?0l:tokenMap.get(token);
		if(now-tokenTime<seeeionTime){
			tokenMap.put(token, System.currentTimeMillis());
			return true;
		}else{
			tokenMap.remove(token);
			return false;
		}
	}
	
	//暂存短信验证码
	public static void smMapAdd(String userID ,String smCode){
		smMap.put(userID, smCode);
	}
	//判断短信验证码，并处理。
	public static boolean smMapCheck(String userID ,String smCode){
		if(smCode.equals(smMap.get(userID))){
			smMap.remove(userID);
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * @Author:HaoMing(郝明)
	 * @Project_name:trademark
	 * @Full_path:com.util.CommUtil.java
	 * @Date:@2017 2017-3-16 下午2:56:20
	 * @Return_type:void
	 * @Desc :
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		System.out.println(sendShortMessage("1335929975",null));
//		System.out.println(fetchValidateNum(6));
//		System.out.println(tokenMap.get(""));
		
		/*Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("E:\\trademark\\dat\\01\\01\\109.dat","0.283L");
		map.put("E:\\trademark\\dat\\01\\01\\10_100.dat","0.233L");
		map.put("E:\\trademark\\dat\\01\\01\\10_126.dat","0.293L");
		map.put("E:\\trademark\\dat\\01\\01\\10_129.dat","0.218L");
		
		Iterator<?> it = map.keySet().iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			System.out.println(key + "\t" +map.get(key));
		}
		
		List<Entry<Object, Object>> l = orderMap(map);
//		System.out.println(l);
		it = l.iterator();
		while(it.hasNext()){
			Entry<Object, Object> e = (Entry<Object, Object>)it.next();
			System.out.println(e.getKey()+":"+e);
		}*/
		
		
		
//		System.out.println(new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date()));
//		System.out.println(new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss:sss").format(new Date()));
//		System.out.println(new SimpleDateFormat("yyyy-MM-dd a HH:mm").format(new Date()));
//		System.out.println(new SimpleDateFormat("yyyy-MM-dd E a HH:mm").format(new Date(1491553528750l)));
		
		
//		System.out.println(timeFormat(1491553528750l));
//		System.out.println(timeFormat(491453528750l));
//		System.out.println((System.currentTimeMillis()-1491353528750l)/(24*3600000));	
		
//		JPushComm("13359299756", "getMessage", "getMessage", "username", "recordId", "491453528750");
	
//		System.out.println(new SimpleDateFormat("dd").format(new Date(1491553528750l)));
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())));
	}
	
	/**
	 * 
	 * @Author:HaoMing(郝明)
	 * @Project_name:trademark
	 * @Full_path:com.util.CommUtil.java
	 * @Date:@2017 2017-4-7 下午4:52:23
	 * @Return_type:String
	 * @Desc : 返回较人性化的时间格式
	 */
	public static String timeFormat(long timeGLWZ){
		String returnStr = "";
//		returnStr = new SimpleDateFormat("yyyy-MM-dd E a HH:mm").format(new Date(timeGLWZ));
		
		int dayNum = -1;
		String s = new SimpleDateFormat("yyyy-MM-dd").format(new Date(timeGLWZ));
		if(dayArray!=null){
			for (int i = 0; i < dayArray.length; i++) {
				if(s.equals(dayArray[i])){
					dayNum = i;
					break;
				}
			}
		}
		
		switch (dayNum) {
		case 0:
			returnStr = "今天";
			break;
		case 1:
			returnStr = "昨天";
			break;
		case 2:
			returnStr = "前天";
			break;	
		case 3:
			returnStr = "大前天";
			break;	
			
		default:
			returnStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date(timeGLWZ));
			break;
		}
		
		returnStr += new SimpleDateFormat("  E a HH:mm").format(new Date(timeGLWZ));
		return returnStr;
	}
	
	/**
	 * 
	 * @Author:HaoMing(郝明)
	 * @Project_name:ipc_ty
	 * @Full_path:com.comm.util.CommUtil.java
	 * @Date:@2015 2015-12-1 下午3:19:32
	 * @Return_type:String
	 * @Desc : 获取给定位数的验证码
	 */
	public static String fetchValidateNum(int bitNum) {

		int times = 0;// 放大倍数
		switch (bitNum) {
		case 1:
			times = 10;
			break;
		case 2:
			times = 100;
			break;
		case 3:
			times = 1000;
			break;
		case 4:
			times = 10000;
			break;
		case 5:
			times = 100000;
			break;
		case 6:
			times = 1000000;
			break;

		default:
			times = 1;
			break;
		}

		int pn = 0;
		while (true) {
			pn = (int) (Math.random() * times);
			if (String.valueOf(pn).length() == bitNum) {
				// System.out.println(pn);
				break;
			}
		}
		return String.valueOf(pn);
	}
	
	/**
	 * 
	 * @Author:HaoMing(郝明)
	 * @Project_name:ipc_ty
	 * @Full_path:com.comm.util.CommUtil.java
	 * @Date:@2015 2015-12-1 下午3:52:31
	 * @Return_type:void
	 * @Desc : 发送短信验证的方法 【验证码对于一个号码30分钟内不超过9次】
	 */
	public static String sendShortMessage(String phoneNum,String content){
//		if("15029601708".equals(phoneNum)){
//			phoneNum = "13359299756";
//		}
		String validateNum= "";
		int statusCode=0;
		try {
			HttpClient client = new HttpClient();
			PostMethod post = new PostMethod("http://sms.webchinese.cn/web_api/");
			post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=gbk");// 在头文件中设置转码
			if(content == null||"".equals(content)){
				validateNum = fetchValidateNum(6);
				NameValuePair[] data = { new NameValuePair("Uid", "haom"), // 注册的用户名
						new NameValuePair("Key", "3cc5df1b2e96583f3f41"),// 注册成功后,登录网站使用的密钥，这个密钥要登录到国建网然后有一个API接口，点进去就有一个key，可以改，那个才是密钥
						new NameValuePair("smsMob", phoneNum), // 手机号码  
							new NameValuePair("smsText", "尊敬的用户，你在商标检索系统所获取的短信验证码为"+validateNum+",请妥善保管，不要泄露。【商标检索】") };// 设置短信内容
				post.setRequestBody(data);
			}else{
				NameValuePair[] data = { new NameValuePair("Uid", "haom"), // 注册的用户名
						new NameValuePair("Key", "3cc5df1b2e96583f3f41"),// 注册成功后,登录网站使用的密钥，这个密钥要登录到国建网然后有一个API接口，点进去就有一个key，可以改，那个才是密钥
						new NameValuePair("smsMob", phoneNum), // 手机号码
							new NameValuePair("smsText", content) };// 设置短信内容
				post.setRequestBody(data);
			}
			client.executeMethod(post);
			Header[] headers = post.getResponseHeaders();
			statusCode = post.getStatusCode();
			System.out.println("statusCode:" + statusCode);
			for (Header h : headers) {
				System.out.println(h.toString());
			}
			String result = new String(post.getResponseBodyAsString().getBytes("gbk"));
			System.out.println(result);
			if(!"1".equals(result)){
				return result;
			}
			post.releaseConnection();
		} catch (Exception e) {
			log.error("CommUtil sendShortMessage 中国网建发送短信接口异常："+statusCode);
			e.printStackTrace();
		}
		return validateNum;
	}
	
	/**
	 * 
	 * @Author:HaoMing(郝明)
	 * @Project_name:trademark
	 * @Full_path:com.util.CommUtil.java
	 * @Date:@2017 2017-3-21 下午3:01:34
	 * @Return_type:String
	 * @Desc :获得post的json串
	 */
	public static String fetchPostStrFun(HttpServletRequest req) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
		String line = "";
		StringBuffer sb = new StringBuffer();
		while ((line = br.readLine()) != null){
			sb.append(line);
		}
		String s = "";
		if(sb.length()!=0){
			s = sb.toString();
			if(s.contains("\\u")){
				s = UnicodeUtil.unicodeToStr_za(s);
			}
		}
		return s;
	}
	
	//按照value排序
	public static List<Entry<Object,Object>> orderMap(Map<Object, Object> m){
		//这里将map.entrySet()转换成list
        List<Map.Entry<Object,Object>> list = new ArrayList<Map.Entry<Object,Object>>(m.entrySet());
        //然后通过比较器来实现排序
        Collections.sort(list,new Comparator<Map.Entry<Object,Object>>() {
            //升序排序
            public int compare(Entry<Object, Object> o1,Entry<Object, Object> o2) {
                return o1.getValue().toString().compareTo(o2.getValue().toString());
            }
        });
        
        return list;
//        for(Map.Entry<Object,Object> mapping:list){ 
//               System.out.println(mapping.getKey()+":"+mapping.getValue()); 
//          }
	}
	
	/**
	 * 
	 * @Author:HaoMing(郝明)
	 * @Project_name:trademark
	 * @Full_path:com.util.CommUtil.java
	 * @Date:@2017 2017-3-28 下午5:43:45
	 * @Return_type:String[]
	 * @Desc :
	 */
	public static String[] strToArray(String str,String seprater){
		if(str.length() == 0||seprater.length()==0){
			return null;
		}
		return str.split(seprater);
	}
	
	/**
	 * 
	 * @author HaoMing
	 * @2011 2011-11-13 下午02:09:24
	 * @desc :公共的分页方法。
	 * @return 返回总页数.
	 */
	public static int getTotalPage(int totalNumber,int pageSize){
		int totalPage = 0;
		if (totalNumber != 0) {
			if (totalNumber % pageSize == 0) {
				totalPage = totalNumber / pageSize;
			} else {
				totalPage = totalNumber / pageSize + 1;
			}

		} else {
			totalPage = 0;
		}

		return totalPage;
	}
	
	//极光推送 android
	/*public static String JPushAndroid(String rid,String getCode,String getMessage,String username,String recordId,String timeGLWZ){
		try {
			Map<String, String> extras = new HashMap<String, String>();
			extras.put("getCode", getCode);
	        extras.put("getMessage", getMessage);
	        extras.put("username", username);
	        extras.put("recordId", recordId);
//				PushResult result = client.sendIosNotificationWithRegistrationID("你于****年*月*日的商标检索有结果了，请查看。", extras, "191e35f7e07616bb369");
	        if(client!=null){
//	        	PushResult result = client.sendAndroidNotificationWithRegistrationID("商标检索", "你于****年*月*日的商标检索有结果了，请查看。", extras, "120c83f76017e73ce43");
	        	PushResult result = client.sendAndroidNotificationWithRegistrationID("商标检索", "您于"+timeFormat(Long.parseLong(timeGLWZ))+"的商标检索有结果了，请查看。", extras, rid);
	        	return result.toString();
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
						
	}*/
	
//	极光推送 ios
/*	public static String jPushIOS(String rid,String getCode,String getMessage,String username,String recordId,String timeGLWZ){
		try {
			Map<String, String> extras = new HashMap<String, String>();
//	        extras.put("getCode", "0");
//	        extras.put("getMessage", "dddddd");
//	        extras.put("username", "13951624867");
//	        extras.put("recordId", "13404144827_1492070528777.jpg");
	        extras.put("getCode", getCode);
	        extras.put("getMessage", getMessage);
	        extras.put("username", username);
	        extras.put("recordId", recordId);
	        if(client!=null){
//				PushResult result = client.sendIosNotificationWithRegistrationID("你于****年*月*日的商标检索有结果了，请查看。", extras, "191e35f7e07616bb369");
				PushResult result = client.sendIosNotificationWithRegistrationID("您于"+timeFormat(Long.parseLong(timeGLWZ))+"的商标检索有结果了，请查看。", extras, rid);
	        	return result.toString();
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/
	
	public static String JPushComm(String tagid,String getCode,String getMessage,String username,String recordId,String timeGLWZ){
		try {
			Map<String, String> extras = new HashMap<String, String>();
	        extras.put("getCode", getCode);
	        extras.put("getMessage", getMessage);
	        extras.put("username", username);
	        extras.put("recordId", recordId);
			
			PushPayload build = PushPayload.newBuilder()
	                .setPlatform(Platform.all())
	                .setAudience(Audience.tag(tagid))
	                .setNotification(Notification.newBuilder()
	                        .setAlert("您于"+timeFormat(Long.parseLong(timeGLWZ))+"的商标检索有结果了，请查看。")
	                        .addPlatformNotification(AndroidNotification.newBuilder()
	                                .setTitle("商标检索")
	                                .addExtras(extras)
	                                .build())
	                        .addPlatformNotification(IosNotification.newBuilder()
	                                .incrBadge(1)
	                                .addExtras(extras).build())
	                                .build())
                            .setOptions(Options.newBuilder() //ios apns 显示设置生产环境，默认是开发环境
                                .setApnsProduction(true)
                                .build())
	                .build();

			client.sendPush(build);
			return build.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
