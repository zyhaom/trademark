package com.util.prop;

import java.util.Properties;

public class CommParameter {

	public static String SBTYPEJSON="";
	private static final String property_URL = "CommParameter.properties";
	private static Properties prop = new Properties();

	static {
		new CommParameter().init();
	}
	
	/**
	 * 
	 * @Author:HaoMing(郝明)
	 * @Project_name:checkface
	 * @Full_path:com.util.prop.CommParameter.java
	 * @Date:@2014 2014-8-18 上午10:27:29
	 * @Return_type:String
	 * @Desc : 获取配置文件公共变量的方法
	 */
	public static String getCommParameterByKey(String key) {
		try {
			return prop.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("CommParameter InfoError : " + e.toString());
		}
		return null;
	}

	private void init() {
		try {
			prop.load(getClass().getResourceAsStream(property_URL));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Author:HaoMing(郝明)
	 * @Project_name:wxeg
	 * @Full_path:org.hualv.course.util.prop.CommParameter.java
	 * @Date:@2014 2014-5-4 下午2:23:09
	 * @Return_type:void
	 * @Desc :
	 */
	public static void main(String[] args) {
		try {
			System.out.println(getCommParameterByKey(""));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
