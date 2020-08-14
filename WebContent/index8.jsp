<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
session.setAttribute("basePath", basePath);
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>所有文件</h3>
	<form action="${pageContext.request.contextPath }/oneself/download.do" 
             method="post" enctype="multipart/form-data" >  
             fileId<input type="text" name="fileId"/><br/>	
            <input type="submit" value="下载" />  
        </form>  
</body>
</html>
