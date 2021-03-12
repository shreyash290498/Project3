<%@page import="in.co.sunrays.proj3.controller.LoginCtl"%>
<%@page import="in.co.sunrays.proj3.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj3.util.DataUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@page errorPage="ErrorView.jsp"%>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">


<!--    favicon-->
<link rel="shortcut icon" href="theam_wel/image/fav-icon.png"
	type="image/x-icon">
<title>Login View</title>

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
<%@ include file="Header.jsp"%>
<body class="img-responsive">


	<form action="<%=ORSView.LOGIN_CTL%>" method="post">


		<jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.UserDTO"
			scope="request"></jsp:useBean>
		<input type="hidden" name="URI"
			value="<%=session.getAttribute("uri")%>"> <input
			type="hidden" name="id" value="<%=dto.getId()%>"> <input
			type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
		<input type="hidden" name="modifiedBy"
			value="<%=dto.getModifiedBy()%>"> <input type="hidden"
			name="createdDatetime"
			value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
		<input type="hidden" name="modifiedDatetime"
			value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

		<div class="container-fluid">
			<br>
			<div class="row ">
				<div class="col-xs-12  col-sm-12 col-lg-4 col-md-4"></div>
				<div class="col-xs-12  col-sm-12 col-lg-4 col-md-4">

					<div class="panel panel-default">
						<div class="panel-heading">
							<b><h1>Login</h1></b>
						</div>
						<div class="panel-body " align="center">
							<%
								if (!ServletUtility.getSuccessMessage(request).equals("")) {
							%>
							<div class="alert alert-success alert-dismissible">
								<a href="#" class="close" data-dismiss="alert"
									aria-label="close">&times;</a> <strong class="h4"> <%=ServletUtility.getSuccessMessage(request)%></strong>
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


							<div class="form-group" align="left">
								<label for="username-email" align="left"
									class="control-label text-info col-md-6">Login Id<font
									color="red"> *</font></label>
								<div class="col-md-12" style="margin-bottom:">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-user"></i></span> <input
											placeholder="Enter LoginId" type="text" class="form-control"
											name="login"
											value="<%=DataUtility.getStringData(dto.getLogin())%>">
									</div>
									<label class="control-label text-danger  error_msg"
										for="inputError1"> <%=ServletUtility.getErrorMessage("login", request)%>
										<span>&emsp;</span></label>
								</div>
							</div>

							<div class="form-group " align="left">
								<label for="password" class="control-label text-info col-md-6">Password<font
									color="red"> *</font></label>
								<div class="col-md-12" style="margin-bottom:;">
									<div class="input-group ">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-lock"></i></span> <input name="password"
											placeholder="Enter Password" type="password"
											class="form-control"
											value="<%=DataUtility.getStringData(dto.getPassword())%>">
									</div>
									<label class="control-label text-danger  error_msg"
										for="inputError1"> <%=ServletUtility.getErrorMessage("password", request)%><span>&emsp;</span></label>
								</div>
							</div>
							&emsp;
							<div class="form-group  col-md-6" align="left"
								style="margin-top: 20px;">
								<a href="<%=ORSView.FORGET_PASSWORD_CTL%>"> <b><font><u>Forget
												password ?</u></font></b></a>
							</div>

							<div class="form-group ">
								<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
									<%-- 	<input type="submit" class="btn btn-success" name="operation"
							value="<%=LoginCtl.OP_SIGN_IN%>"> --%>

									<button type="submit" class="btn btn-primary" name="operation"
										value="<%=LoginCtl.OP_SIGN_IN%>">
										<span class="glyphicon glyphicon-check"></span> Signin
									</button>
									&emsp;
									<button class="btn btn-warning btn-cancel-action"
										name="operation" value="<%=LoginCtl.OP_RESET%>">
										<span class="glyphicon glyphicon-refresh"></span>Reset
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

		</div>

	</form>

</body>
<br>
<br>
<br>
<br>
<%@ include file="Footer.jsp"%>
</html>
