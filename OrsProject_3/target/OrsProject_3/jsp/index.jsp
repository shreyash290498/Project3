<%@page import="in.co.sunrays.proj3.controller.ORSView"%>
<%@page import="in.co.sunrays.proj3.controller.ORSView"%>
<%@page errorPage="ErrorView.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<title>Index page</title>
<style type="text/css">
body {
	background-image: url("/OrsProject_3/img/img.png");
	background-size: cover;
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-position: center;
}
</style>
<link>
</head>
<body class="img-responsive">
	<form action="">
		<br>
		<br>
		<h1 align="center">

			<img src="<%=ORSView.APP_CONTEXT%>/img/rayslogo.png"
				style="width: 40%; height: 20%;"> <br>
			<br>
			<br> <a href="<%=ORSView.WELCOME_CTL%>" class="btn btn-link"><div
					class="text-danger">
					<font size="7"> Online Result System </font>
				</div></a>

		</h1>
	</form>
</html>