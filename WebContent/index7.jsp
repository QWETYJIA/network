<%@ page language="java" contentType="text/html; charset=UTF-8"  
    pageEncoding="UTF-8"%>  
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>  
<%  
    String path = request.getContextPath();  
    String basePath = request.getScheme() + "://"  
            + request.getServerName() + ":" + request.getServerPort()  
            + path + "/";  
%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
<html>  
<head>  
<title>用户上传图片页面</title>  
 <base href="<%=basePath%>">  
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
</head>  
<body>  
    <center>  
      	<form action="${pageContext.request.contextPath }/oneself/file.do" 
             method="post" enctype="multipart/form-data" >  
            <input type="text" name="page">
            <input type="text" name="size">
            <input type="submit" value="查看所有的信息" />  
        </form>  
        <h5>上传结果：</h5>  
        <img alt="暂无图片" src="${fileUrl}" />  
    </center>  
    <h1>文件上传</h1>
    上传
	<form action="${pageContext.request.contextPath }/oneself/upload.do" enctype="multipart/form-data" method="post">
		<input type="file" name="uploadFile">
		<input type="submit"value="上传"/>
	</form>
	下载
    <form action="${pageContext.request.contextPath }/oneself/download.do"
        method="post">
        	<input type="text" name="fileId">
        <input type="submit" value="下载">
    </form>
    	 <form action="${pageContext.request.contextPath }/oneself/fileother.do"
        method="get">
        <input type="text" name="fileName">
        <input type="text" name="password">
        <input type="submit"value="上传"/>
        
    </form>
</body>  
</html>  


