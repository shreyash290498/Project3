
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj3.controller.MarksheetCtl"%>
<%@page import="in.co.sunrays.proj3.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.sunrays.proj3.util.DataUtility"%>
<%@page import="in.co.sunrays.proj3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorView.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Marksheet View</title>
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
	<form action="<%=ORSView.MARKSHEET_CTL%>" method="post">
		<jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.MarksheetDTO"
			scope="request"></jsp:useBean>
		<div class="container-fluid">
			<div class="row">
				<div class="col-xs-12  col-sm-2 col-lg-4 col-md-4"></div>
				<div class="col-xs-12  col-sm-8 col-lg-4 col-md-4">
					<div class="panel panel-default">

						<div class="panel-heading">
							<%
								if (dto.getId() > 0) {
							%>
							<h1>Update Marksheet</h1>
							<%
								} else {
							%>
							<h1>Add Marksheet</h1>
							<%
								}
							%>
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
								<label align="left" class="control-label text-info col-md-6">Roll
									Number <font color="red">*</font>
								</label>
								<div class="col-md-12" style="margin-bottom: 5px;">
									<div class="input-group ">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-registration-mark"></i></span> <input
											class="form-control" type="text" name="rollNo"
											value="<%=DataUtility.getStringData(dto.getRollNo())%>"
											<%=(dto.getId() > 0) ? "readonly" : ""%>
											placeholder="Enter Roll Number" />

									</div>
									<label class="control-label text-danger" for="inputError1"><%=ServletUtility.getErrorMessage("rollNo", request)%></label>
								</div>
							</div>
							<%
								List sList = (List) request.getAttribute("studentList");
							%>
							<div class="form-group" align="left">
								<label align="left" class="control-label text-info col-md-6">StudentName
									<font color="red">*</font>
								</label>
								<div class="col-md-12" style="margin-bottom: 5px;">
									<div class="input-group ">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-user"></i></span>
										<%=HTMLUtility.getList("studentId", String.valueOf(dto.getStudentId()), sList)%>
									</div>
									<label class="control-label text-danger" for="inputError1"><%=ServletUtility.getErrorMessage("studentId", request)%></label>
								</div>
							</div>


							<div class="form-group" align="left">
								<label align="left" class="control-label text-info col-md-6">Physics<font
									color="red">*</font></label>
								<div class="col-md-12" style="margin-bottom: 5px;">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-asterisk"></i></span> <input
											class="form-control" type="text" name="physics"
											value="<%=DataUtility.getStringData(dto.getPhysics()).trim().equals("0") ? ""
					: DataUtility.getStringData(dto.getPhysics())%>"
											placeholder="
											Enter physics No" />
									</div>
									<label class="control-label text-danger" for="inputError1"><%=ServletUtility.getErrorMessage("physics", request)%></label>
								</div>
							</div>
							<div class="form-group" align="left">
								<label align="left" class="control-label text-info col-md-6">Chemistry<font
									color="red">*</font></label>
								<div class="col-md-12" style="margin-bottom: 5px;">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-asterisk"></i></span> <input
											class="form-control" type="text" name="chemistry"
											value="<%=DataUtility.getStringData(dto.getChemistry()).trim().equals("0") ? ""
					: DataUtility.getStringData(dto.getChemistry())%>"
											placeholder="
											Enter Chemistry No" />
									</div>
									<label class="control-label text-danger" for="inputError1"><%=ServletUtility.getErrorMessage("chemistry", request)%></label>
								</div>
								<div class="form-group" align="left">
									<label align="left" class="control-label text-info col-md-6">Maths<font
										color="red">*</font></label>
									<div class="col-md-12" style="margin-bottom: 5px;">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-asterisk"></i></span> <input
												class="form-control" type="text" name="maths"
												value="<%=DataUtility.getStringData(dto.getMaths()).trim().equals("0") ? ""
					: DataUtility.getStringData(dto.getMaths())%>"
												placeholder="
												Enter physics No" />
										</div>
										<label class="control-label text-danger" for="inputError1"><%=ServletUtility.getErrorMessage("maths", request)%></label>
									</div>
								</div>
								<div class="form-group" style="margin-top: 90px;" align="center">
									<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
										<%
											if (dto.getId() > 0) {
										%>
										<button class="btn btn-success" type="submit" name="operation"
											value="<%=MarksheetCtl.OP_UPDATE%>">
											<span class="glyphicon glyphicon-check">Update</span>
										</button>
										&emsp;
										<button class="btn btn-warning" type="submit" name="operation"
											value="<%=MarksheetCtl.OP_CANCEL%>">
											<span class="glyphicon glyphicon-remove">Cancel</span>
										</button>
										<%
											} else {
										%>
										<button class="btn btn-primary" type="submit" name="operation"
											value="<%=MarksheetCtl.OP_SAVE%>">
											<span class="glyphicon glyphicon-check">Save</span>
										</button>
										&emsp;
										<button class="btn btn-warning" type="submit" name="operation"
											value="<%=MarksheetCtl.OP_RESET%>">
											<span><i class="glyphicon glyphicon-refresh"></i>Reset</span>
										</button>
										<%
											}
										%>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-xs-12  col-sm-2 col-lg-4 col-md-4"></div>
				</div>
			</div>
	</form>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<%@ include file="Footer.jsp"%>
</body>

</html>

