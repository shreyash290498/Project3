
<%@page import="in.co.sunrays.proj3.controller.ChangePasswordCtl"%>
<%@page import="in.co.sunrays.proj3.util.DataUtility"%>
<%@page import="in.co.sunrays.proj3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorView.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>ChangePassword View</title>
<!--    favicon-->
<link rel="shortcut icon" href="theam_wel/image/fav-icon.png"
	type="image/x-icon">


<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
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
	<form action="<%=ORSView.CHANGE_PASSWORD_CTL%>" method="post">
		<jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.UserDTO"
			scope="request"></jsp:useBean>
		<div class="container-fluid">
			<div class="row">
				<div class="col-xs-12  col-sm-2 col-lg-4 col-md-4"></div>
				<div class="col-xs-12  col-sm-8 col-lg-4 col-md-4">
					<div class="panel panel-default">

						<div class="panel-heading">
							<b><h1>ChangePasword</h1></b>
						</div>

						<div class="panel-body" align="center">
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
							<br> <input type="hidden" name="id" value="<%=dto.getId()%>">
							<input type="hidden" name="createdBy"
								value="<%=dto.getCreatedBy()%>"> <input type="hidden"
								name="modifiedBy" value="<%=dto.getModifiedBy()%>"> <input
								type="hidden" name="createdDatetime"
								value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
							<input type="hidden" name="modifiedDatetime"
								value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

							<div class="form-group" align="left">
								<label align="left" class="control-label text-info col-md-6">Old
									Password<font color="red">*</font>
								</label>
								<div class="col-md-12" style="margin-bottom: 5px;">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-lock"></i></span> <input
											class="form-control" type="password" name="oldPassword"
											value="" placeholder="Enter Old PassWord" />
									</div>
									<label class="control-label text-danger" for="inputError1"><%=ServletUtility.getErrorMessage("oldPassword", request)%></label>
								</div>
							</div>
							<div class="form-group" align="left">
								<label align="left" class="control-label text-info col-md-6">New
									Password <font color="red">*</font>
								</label>
								<div class="col-md-12" style="margin-bottom: 5px;">
									<div class="input-group ">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-lock"></i></span> <input
											class="form-control" type="password" name="newPassword"
											value="" placeholder="Enter New Password" />

									</div>
									<label class="control-label text-danger" for="inputError1"><%=ServletUtility.getErrorMessage("newPassword", request)%></label>
								</div>
							</div>
							<div class="form-group" align="left">
								<label align="left" class="control-label text-info col-md-6">ConfirmPassword
									<font color="red">*</font>
								</label>
								<div class="col-md-12" style="margin-bottom: 5px;">
									<div class="input-group ">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-lock"></i></span> <input
											class="form-control" type="password" name="confirmPassword"
											value="" placeholder="Enter ConfirmPassword" />
									</div>

									<label class="control-label text-danger" for="inputError1"><%=ServletUtility.getErrorMessage("confirmPassword", request)%></label>
								</div>
							</div>

							<div class="form-group" style="margin-top: 90px;">
								<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
									<button class="btn btn-success" type="submit" name="operation"
										value="<%=ChangePasswordCtl.OP_UPDATE%>">
										<span class="glyphicon glyphicon-check">Update</span>
									</button>
									&emsp;
									<button class="btn btn-warning" type="submit" name="operation"
										value="<%=ChangePasswordCtl.OP_CHANGE_MY_PROFILE%>"
										style="width: 178px;">
										<span class="glyphicon glyphicon-edit">Change My
											Profile</span>
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="col-xs-12  col-sm-2 col-lg-4 col-md-4"></div>
			</div>
		</div>
		<br> <br> <br> <br> <br> <br>
	</form>
	<%@ include file="Footer.jsp"%>
</body>

</html>

