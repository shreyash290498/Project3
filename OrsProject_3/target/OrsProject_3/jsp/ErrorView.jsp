<%@page import="in.co.sunrays.proj3.controller.ORSView"%>
<%@page import="in.co.sunrays.proj3.dto.UserDTO"%>
<%@page import="in.co.sunrays.proj3.controller.ORSView"%>
<%@page import="in.co.sunrays.proj3.dto.UserDTO"%>
<%@page import="in.co.sunrays.proj3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page isErrorPage="true"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Error view</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<style type="text/css">
body {
	background-image: url("/OrsProject_3/img/ErrorView.jpg");
	background-size: cover;
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-position: center;
	margin-top: 200px;
}
</style>
</head>
<body class="img-responsive">
	<form action="">
		<center>
			<%
				UserDTO bean = (UserDTO) session.getAttribute("user");
			%>
			<h1 style="color: red">*****....OOPS!! Something Went
				Wrong....*****Project 3</h1>
				<br>
				<br>
				<br>
			<%
				if (bean == null) {
			%>
			<a href="<%=ORSView.LOGIN_CTL%>" class="btn btn-info"
				style="width: 100px; height: 40px; font-size: 16px">Back</a>
			<%
				} else {
			%>
			<a href="<%=ORSView.WELCOME_CTL%>">Back</a>
			<%
				}
			%>
		</center>
	</form>
</body>
</html>