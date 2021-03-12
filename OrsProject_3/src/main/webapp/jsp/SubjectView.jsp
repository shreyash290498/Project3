
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj3.controller.SubjectCtl"%>
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

<title>Subject View</title>
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
	<br>
	<br>
	<form action="<%=ORSView.SUBJECT_CTL%>" method="post">
		<jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.SubjectDTO"
			scope="request"></jsp:useBean>
		<div class="container-fluid">
			<div class="row">
				<div class="col-xs-12  col-sm-12 col-lg-4 col-md-4"></div>
				<div class="col-xs-12  col-sm-12 col-lg-4 col-md-4">
					<div class="panel panel-default">

						<div class="panel-heading">
							<%
								if (dto.getId() > 0) {
							%>
							<h1>Update Subject</h1>
							<%
								} else {
							%>
							<h1>Add Subject</h1>
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
							<%
								List courseList = (List) request.getAttribute("courseList");
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
								<label align="left" class="control-label text-info col-md-6">Course
									Name<font color="red">*</font>
								</label>
								<div class="col-md-12" style="margin-bottom: 30px;">
									<div class="input-group ">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-book"></i></span>
										<%=HTMLUtility.getList("courseId", String.valueOf(dto.getCourseId()), courseList)%>

									</div>
									<label class="control-label text-danger" for="inputError1"><%=ServletUtility.getErrorMessage("courseId", request)%></label>
								</div>
							</div>
							<div class="form-group" align="left">
								<label align="left" class="control-label text-info col-md-6">Subject
									Name<font color="red">*</font>
								</label>
								<div class="col-md-12" style="margin-bottom: 30px;">
									<div class="input-group ">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-book"></i></span> <input
											class="form-control" type="text" name="name"
											value="<%=DataUtility.getStringData(dto.getName())%>"
											placeholder="Enter Subject Name" />

									</div>
									<label class="control-label text-danger" for="inputError1"><%=ServletUtility.getErrorMessage("name", request)%></label>
								</div>
							</div>

							<div class="form-group" align="left">
								<label align="left" class="control-label text-info col-md-6">Semester
									<font color="red">*</font>
								</label>
								<div class="col-md-12" style="margin-bottom: 30px;">
									<div class="input-group ">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-hourglass" aria-hidden="true"></i></span>
										<%
											HashMap map = new HashMap();
											map.put("1", "I");
											map.put("2", "II");
											map.put("3", "III");
											map.put("4", "IV");
											map.put("5", "V");
											map.put("6", "VI");
											map.put("7", "VII");
											map.put("8", "VIII");
										%>
										<%=HTMLUtility.getList("semester", dto.getSemester(), map)%>
									</div>
									<label class="control-label text-danger" for="inputError1"><%=ServletUtility.getErrorMessage("semester", request)%></label>
								</div>
							</div>


							<div class="form-group" align="left">
								<label align="left" class="control-label text-info col-md-6">Description<font
									color="red">*</font></label>
								<div class="col-md-12" style="margin-bottom: 20px;">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-align-justify"></i></span> <input
											class="form-control" type="text" name="description"
											value="<%=DataUtility.getStringData(dto.getDescription())%>"
											placeholder="Enter Description" />
									</div>
									<label class="control-label text-danger" for="inputError1"><%=ServletUtility.getErrorMessage("description", request)%></label>
								</div>
							</div>
							<br> <br>
							<div class="form-group">
								<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
									<%
										if (dto.getId() > 0) {
									%>
									<button class="btn btn-success" type="submit" name="operation"
										value="<%=SubjectCtl.OP_UPDATE%>">
										<span class="glyphicon glyphicon-check">Update</span>
									</button>
									&emsp;
									<button class="btn btn-warning" type="submit" name="operation"
										value="<%=SubjectCtl.OP_CANCEL%>">
										<span class="glyphicon glyphicon-remove">Cancel</span>
									</button>
									<%
										} else {
									%>
									<button class="btn btn-primary" type="submit" name="operation"
										value="<%=SubjectCtl.OP_SAVE%>">
										<span class="glyphicon glyphicon-check">Save</span>
									</button>
									&emsp;
									<button class="btn btn-warning" type="submit" name="operation"
										value="<%=SubjectCtl.OP_RESET%>">
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

				<div class="col-xs-12  col-sm-12 col-lg-4 col-md-4"></div>
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

