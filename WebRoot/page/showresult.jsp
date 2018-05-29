<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@page import="java.util.List"%>
<%-- <%@page import="com.comm.Utils"%> --%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

List<String> list = new ArrayList<String>();
list.add("1.gif");
list.add("2.gif");
list.add("3.gif");
list.add("4.gif");
list.add("5.gif");

%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  <%=basePath %><br />
  <%=path %><br />
    <%
    Iterator<?> it = list.iterator();
    while(it.hasNext()){
    String s = (String)it.next();
     %>
    <img  src="<%=path %>/show/<%=s %>" border="5" title="<%=s %> 啊哈哈哈"> <br />
     <%
     }
     %>
  </body>
</html>
