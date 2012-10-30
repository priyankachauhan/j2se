<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
	<FRAMESET border=0 frameSpacing=0 rows=68,* frameBorder=NO cols=*>  
		<FRAME name=top src="top.jsp" noResize scrolling=no>
		<FRAMESET name=fst rows="*" cols="162,*" >
			<FRAME name=left src="left.jsp">
			<FRAME name=main src="readme.jsp" frameBorder=no scrolling=yes noresizesa>
		</FRAMESET>
		<NOFRAMES>
			<body><p>此网页使用了框架，但您的浏览器不支持框架。</p></body>
		</NOFRAMES>
	</FRAMESET>

</html>
