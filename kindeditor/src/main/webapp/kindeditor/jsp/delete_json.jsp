<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.io.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page import="org.json.simple.*" %>
<%
	
	String param1 = request.getParameter("参数项1");
	String param2 = request.getParameter("参数项2");
	
	//文件保存目录路径
	String savePath = pageContext.getServletContext().getRealPath(param1);
	
	System.out.println(savePath);
	System.out.println(param1);
	System.out.println(param2);
	
	File deleteFile = new File(param1);
	
	if(param2 == "D"){
		deleteFile.delete();
	}else{
		deleteFile.delete();
	}
%>
