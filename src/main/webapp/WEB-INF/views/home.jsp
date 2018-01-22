<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>

<form action="/fileUpload" id="fileUpload" method="post" enctype="multipart/form-data">
<input type="text" name="filename" id="cmd" value="namevla"></br>
<input type="file" name="file">
<input type="submit" value="제출"></br>
</form>

</body>
</html>
