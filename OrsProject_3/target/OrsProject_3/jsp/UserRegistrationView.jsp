
<%@page import="in.co.sunrays.proj3.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.sunrays.proj3.controller.UserRegistrationCtl"%>
<%@page import="in.co.sunrays.proj3.util.DataUtility"%>
<%@page import="in.co.sunrays.proj3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorView.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>UserRegistration View</title>
<!--    favicon-->
<link rel="shortcut icon" href="theam_wel/image/fav-icon.png"
	type="image/x-icon">

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/js/bootstrap-datetimepicker.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/css/bootstrap-datetimepicker.min.css">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript">
	$(function() {
		$("#datepicker").datepicker({
			changeMonth : true,
			changeYear : true,
			dateFormat : 'mm/dd/yy',
			yearRange : "-34:-18",
			defaultDate : "01/01/2000",
		});
	});
</script>

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
	<form action="<%=ORSView.USER_REGISTRATION_CTL%>" method="post">
		<jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.UserDTO"
			scope="request"></jsp:useBean>
		<div class="container-fluid">
			<div class="row">
				<div class="col-xs-3  col-sm-3 col-lg-4 col-md-4"></div>
				<div class="col-xs-6  col-sm-6 col-lg-4 col-md-4">
					<div class="panel panel-default">

						<div class="panel-heading">
							<label><h1>User Registration</h1></label>
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
								<label align="left" class="control-label text-info col-md-6">FirstName
									<font color="red">*</font>
								</label>
								<div class="col-md-12" style="margin-bottom: 5px;">
									<div class="input-group ">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-user"></i></span> <input
											class="form-control" type="text" name="firstName"
											value="<%=DataUtility.getStringData(dto.getFirstName())%>"
											placeholder="Enter First Name" />

									</div>
									<label class="control-label text-danger" for="inputError1"><%=ServletUtility.getErrorMessage("firstName", request)%></label>
								</div>
							</div>
							<div class="form-group" align="left">
								<label align="left" class="control-label text-info col-md-6">LastName
									<font color="red">*</font>
								</label>
								<div class="col-md-12" style="margin-bottom: 5px;">
									<div class="input-group ">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-user"></i></span> <input
											class="form-control" type="text" name="lastName"
											value="<%=DataUtility.getStringData(dto.getLastName())%>"
											placeholder="Enter Last Name" />

									</div>

									<label class="control-label text-danger" for="inputError1"><%=ServletUtility.getErrorMessage("lastName", request)%></label>
								</div>
							</div>


							<div class="form-group" align="left">
								<label align="left" class="control-label text-info col-md-6">LoginId<font
									color="red">*</font></label>
								<div class="col-md-12" style="margin-bottom: 5px;">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-log-in"></i></span> <input
											class="form-control" type="text" name="loginId"
											value="<%=DataUtility.getStringData(dto.getLogin())%>"
											<%=(dto.getId() > 0) ? "readonly" : ""%>
											placeholder="Enter LoginId" />
									</div>
									<label class="control-label text-danger" for="inputError1"><%=ServletUtility.getErrorMessage("loginId", request)%></label>
								</div>
							</div>
							<div class="form-group" align="left">
								<label align="left" class="control-label text-info col-md-6">
									Password <font color="red">*</font>
								</label>
								<div class="col-md-12" style="margin-bottom: 5px;">
									<div class="input-group ">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-lock"></i></span> <input
											class="form-control" type="password" name="password" value=""
											placeholder="Enter New Password" />

									</div>
									<label class="control-label text-danger" for="inputError1"><%=ServletUtility.getErrorMessage("password", request)%></label>
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
							<div class="form-group" align="left">
								<label align="left" class="control-label text-info col-md-6">Gender
									<font color="red">*</font>
								</label>
								<div class="col-md-12" style="margin-bottom: 5px;">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="fa fa-venus-double"></i></span>
										<%
											HashMap map = new HashMap();
											map.put("M", "Male");
											map.put("F", "Female");
											String gList = HTMLUtility.getList("gender", dto.getGender(), map);
										%>
										<%=gList%>
									</div>
									<label class="control-label text-danger" for="inputError1"><%=ServletUtility.getErrorMessage("gender", request)%></label>
								</div>
							</div>
							<div class="form-group" align="left">
								<label align="left" class="control-label text-info col-md-6">Mobile
									No.<font color="red">*</font>
								</label>
								<div class="col-md-12" style="margin-bottom: 5px;">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-earphone"></i></span> <input
											class="form-control" type="text" name="mobileNo"
											value="<%=DataUtility.getStringData(dto.getMobileNo())%>"
											placeholder="Enter Mobile Number" />
									</div>
									<label class="control-label text-danger" for="inputError1"><%=ServletUtility.getErrorMessage("mobileNo", request)%></label>
								</div>
							</div>
							<div class="form-group" align="left">
								<label align="left" class="control-label text-info col-md-6">Date
									Of Birth<font color="red">*</font>
								</label>
								<div class="col-md-12" style="margin-bottom: 5px;">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-calendar"></i></span> <input
											class="form-control" type="text" name="dob" id="datepicker"
											readonly="readonly"
											value="<%=DataUtility.getDateString(dto.getDob())%>"
											placeholder="Enter Date Of Birth" />
									</div>
									<label class="control-label text-danger" for="inputError1"><%=ServletUtility.getErrorMessage("dob", request)%></label>
								</div>
							</div>
							<div class="form-group" style="margin-top: 90px;">
								<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">

									<button class="btn btn-primary" type="submit" name="operation"
										value="<%=UserRegistrationCtl.OP_SIGN_UP%>">
										<span class="glyphicon glyphicon-check">SignUp</span>
									</button>
									&emsp;
									<button class="btn btn-warning" type="submit" name="operation"
										value="<%=UserRegistrationCtl.OP_RESET%>">
										<span class="glyphicon glyphicon-refresh">Reset</span>
									</button>

								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="col-xs-3  col-sm-3 col-lg-4 col-md-4"></div>
			</div>
		</div>
		<br> <br> <br> <br> <br> <br>
	</form>
</body>
<%@ include file="Footer.jsp"%>
</html>

