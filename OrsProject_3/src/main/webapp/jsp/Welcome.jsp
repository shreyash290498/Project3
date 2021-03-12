<%@page import="in.co.sunrays.proj3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="in.co.sunrays.proj3.controller.ORSView"%>
<%@page errorPage="ErrorView.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome View</title>
<style type="text/css">
body {
	background-image: url("/OrsProject_3/img/bg.jpg");
	background-size: cover;
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-position: center;
}
</style>
</head>
<body class="img-responsive">
	<%@ include file="Header.jsp"%>
	<form action="">
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<h1 align="Center">
		<font size="10px" color="red">Welcome to ORS </font>
	</h1>
	</form>
	<%@ include file="Footer.jsp"%>
</body>
</html>