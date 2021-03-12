<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@page import="in.co.sunrays.proj3.controller.ForgetPasswordCtl"%>
<%@page import="in.co.sunrays.proj3.util.DataUtility"%>
<%@page import="in.co.sunrays.proj3.util.ServletUtility"%>
<%@page errorPage="ErrorView.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!--    favicon-->
<link rel="shortcut icon" href="theam_wel/image/fav-icon.png"
	type="image/x-icon">
<title>Forget Pasword</title>
<style type="text/css">
body {
	background-image: url("/OrsProject_3/img/image2.jpg");
	background-size: cover;
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-position: center;
}
</style>
</head>
<body class="img-responsive">
	<%@ include file="Header.jsp"%>

	<form action="<%=ORSView.FORGET_PASSWORD_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.UserDTO"
			scope="request">
		</jsp:useBean>
		<div class="container-fluid">
			<br>
			<div class="row ">
				<div class="col-md-4"></div>
				<div class="col-md-4">

					<div class="panel panel-default">
						<!-- <legend>
							<font size="5"><b>Login</b></font>
						</legend> -->
						<div class="panel-heading">

							<b><h1 style="color: saddlebrown0">Forgot your password?</h1></b>

						</div>
						<div class="panel-body " align="center">
							<div class="form-group">
								<h4 align="center">
									<i>Submit your email address and we'll send you password.</i>
								</h4>
							</div>
							<%
								if (!ServletUtility.getSuccessMessage(request).equals("")) {
							%>
							<div class="alert alert-success alert-dismissible">
								<a href="#" class="close" data-dismiss="alert"
									aria-label="close">&times;</a> <strong class="h3"> <%=ServletUtility.getSuccessMessage(request)%></strong>
							</div>
							<%
								}
								if (!ServletUtility.getErrorMessage(request).equals("")) {
							%>
							<div class="alert alert-danger alert-dismissible">
								<a href="#" class="close" data-dismiss="alert"
									aria-label="close">&times;</a> <strong class="h4"> <%=ServletUtility.getErrorMessage(request)%></strong>
							</div>
							<%
								}
							%>
						</div>

						<div class="form-group col-md-12" align="left">
							<label for="username-email" class="col-md-6">Login Id<font
								color="red">*</font></label>
							<div class="col-md-12">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="glyphicon glyphicon-user"></i></span> <input
										placeholder="Enter LoginId" type="text" class="form-control"
										name="emailId"
										value="<%=DataUtility.getStringData(dto.getLogin())%>">
								</div>
								<label class="control-label text-danger  error_msg"
									for="inputError1"> <%=ServletUtility.getErrorMessage("EmailId", request)%><span>&emsp;</span></label>
							</div>
						</div>
						<br> <br>
						<br> <br>
						<div class="form-group text-center">
							<button class="btn btn-primary" type="submit" name="operation"
								value="<%=ForgetPasswordCtl.OP_SEND%>">
								<span class="glyphicon glyphicon-check">Send</span>
							</button>
							&emsp;&emsp;
							<button class="btn btn-warning" type="submit" name="operation"
								value="<%=ForgetPasswordCtl.OP_RESET%>">
								<span><i class="glyphicon glyphicon-refresh"></i>Reset</span>
							</button>

						</div>
					</div>
				</div>
			</div>
		</div>
	</form>

</body>
<%@ include file="Footer.jsp"%>

</html>