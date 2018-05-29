package com.util.prop;

import java.net.URLDecoder;

public class Unicode2CN {

	/**
	 * @Author:HaoMing(郝明)
	 * @Project_name:checkface
	 * @Full_path:com.util.prop.Unicode2CN.java
	 * @Date:@2014 2014-8-27 下午4:10:38
	 * @Return_type:void
	 * @Desc :
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "\\u8BA8\\u8BBA\\u533A";
		System.out.println(convert(s));
		
		String ss = "\u8BA8\u8BBA\u533A";
		System.out.println(ss);
		System.out.println(URLDecoder.decode(ss));
		
		

	}
	
	public static String convert(String utfString){  
	    StringBuilder sb = new StringBuilder();  
	    int i = -1;  
	    int pos = 0;  
	      
	    while((i=utfString.indexOf("\\u", pos)) != -1){  
	        sb.append(utfString.substring(pos, i));  
	        if(i+5 < utfString.length()){  
	            pos = i+6;  
	            sb.append((char)Integer.parseInt(utfString.substring(i+2, i+6), 16));  
	        }  
	    }  
	      
	    return sb.toString();  
	} 

}
