<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hello World</title>
</head>
<body>
	<h1>Hello World Naveen</h1>
	<h1>Name: <%=request.getAttribute("name") %></h1>
	<h1>Town: <%=request.getAttribute("town") %></h1>
</body>
</html>