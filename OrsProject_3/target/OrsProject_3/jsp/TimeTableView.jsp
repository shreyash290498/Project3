
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj3.controller.TimeTableCtl"%>
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

<title>TimeTable View</title>
<!--    favicon-->
<link rel="shortcut icon" href="theam_wel/image/fav-icon.png"
	type="image/x-icon">
<!-- datepicker files -->
<!-- <link rel="stylesheet"
	href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
 -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/js/bootstrap-datetimepicker.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/css/bootstrap-datetimepicker.min.css">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript">
	function noSunday(date) {
		if (date.getDay() == 0) {
			return [ false ];
		} else {
			return [ true ];
		}
	};
	$(function() {
		$("#datepicker").datepicker({
			dateFormat : 'mm/dd/yy',
			defaultDate : "01/02/2020",
			changeMonth : true,
			changeYear : true,
			yearRange : '0:+1',
			minDate : '+1',
			beforeShowDay : noSunday
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

<body class="img-responsive">
	<%@ include file="Header.jsp"%>
	<form action="<%=ORSView.TIME_TABLE_CTL%>" method="post">
		<jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.TimeTableDTO"
			scope="request"></jsp:useBean>
		<div class="container-fluid">
			<div class="row">
				<div class="col-xs-3  col-sm-3 col-lg-4 col-md-4"></div>
				<div class="col-xs-6  col-sm-6 col-lg-4 col-md-4">
					<div class="panel panel-default">

						<div class="panel-heading">
							<%
								if (dto.getId() > 0) {
							%>
							<h1>Update TimeTable</h1>
							<%
								} else {
							%>
							<h1>Add TimeTable</h1>
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
							<%
								List courseList = (List) request.getAttribute("courseList");
							%>
							<div class="form-group" align="left">
								<label align="left" class="control-label text-info col-md-6">CourseName
									<font color="red">*</font>
								</label>
								<div class="col-md-12" style="margin-bottom: 5px;">
									<div class="input-group ">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-book"></i></span>
										<%=HTMLUtility.getList("courseId", String.valueOf(dto.getCourseId()), courseList)%>
									</div>
									<label class="control-label text-danger" for="inputError1"><%=ServletUtility.getErrorMessage("courseId", request)%></label>
								</div>
							</div>
							<%
								List subjectList = (List) request.getAttribute("subjectList");
							%>
							<div class="form-group" align="left">
								<label align="left" class="control-label text-info col-md-6">SubjectName
									<font color="red">*</font>
								</label>
								<div class="col-md-12" style="margin-bottom: 5px;">
									<div class="input-group ">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-book"></i></span>
										<%=HTMLUtility.getList("subjectId", String.valueOf(dto.getSubjectId()), subjectList)%>

									</div>

									<label class="control-label text-danger" for="inputError1"><%=ServletUtility.getErrorMessage("subjectId", request)%></label>
								</div>
							</div>
							<div class="form-group" align="left">
								<label align="left" class="control-label text-info col-md-6">Semester
									<font color="red">*</font>
								</label>
								<div class="col-md-12" style="margin-bottom: 5px;">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="fa fa-venus-double"></i></span>
										<%
											LinkedHashMap map = new LinkedHashMap();
											map.put("I", "I");
											map.put("II", "II");
											map.put("III", "III");
											map.put("IV", "IV");
											map.put("V", "V");
											map.put("VI", "VI");
											map.put("VII", "VII");
											map.put("VIII", "VIII");
										%>
										<%=HTMLUtility.getList("semester", dto.getSemester(), map)%>
									</div>
									<label class="control-label text-danger" for="inputError1"><%=ServletUtility.getErrorMessage("semester", request)%></label>
								</div>
							</div>

							<div class="form-group" align="left">
								<label align="left" class="control-label text-info col-md-6">ExameDate
									<font color="red">*</font>
								</label>
								<div class="col-md-12" style="margin-bottom: 5px;">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-calendar"></i></span> <input
											class="form-control" type="text" name="examDate"
											id="datepicker"
											value="<%=DataUtility.getDateString(dto.getExamDate())%>"
											placeholder="Enter ExamDate" readonly="readonly" />
									</div>
									<label class="control-label text-danger" for="inputError1"><%=ServletUtility.getErrorMessage("examDate", request)%></label>
								</div>
							</div>
							<div class="form-group" align="left">
								<label align="left" class="control-label text-info col-md-6">ExamTime
									<font color="red">*</font>
								</label>
								<div class="col-md-12" style="margin-bottom: 5px;">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="fa fa-venus-double"></i></span>
										<%
											HashMap tmap = new HashMap();
											tmap.put("08:00 AM To 10:00 AM", "08:00 AM To 10:00 AM");
											tmap.put("12:00 PM To 02:00 PM", "12:00 PM To 02:00 PM");
											tmap.put("03:00 PM To 05:00 PM", "03:00 PM To 05:00 PM");
										%>
										<%=HTMLUtility.getList("time", dto.getTime(), tmap)%>
									</div>
									<label class="control-label text-danger" for="inputError1"><%=ServletUtility.getErrorMessage("time", request)%></label>
								</div>
							</div>
							<div class="form-group" style="margin-top: 90px;">
								<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
									<%
										if (dto.getId() > 0) {
									%>
									<button class="btn btn-success" type="submit" name="operation"
										value="<%=TimeTableCtl.OP_UPDATE%>">
										<span class="glyphicon glyphicon-check">Update</span>
									</button>
									&emsp;
									<button class="btn btn-warning" type="submit" name="operation"
										value="<%=TimeTableCtl.OP_CANCEL%>">
										<span class="glyphicon glyphicon-remove">Cancel</span>
									</button>
									<%
										} else {
									%>
									<button class="btn btn-primary" type="submit" name="operation"
										value="<%=TimeTableCtl.OP_SAVE%>">
										<span class="glyphicon glyphicon-check">Save</span>
									</button>
									&emsp;
									<button class="btn btn-warning" type="submit" name="operation"
										value="<%=TimeTableCtl.OP_RESET%>">
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

				<div class="col-xs-3  col-sm-3 col-lg-4 col-md-4"></div>
			</div>
		</div>
		<br> <br> <br> <br> <br> <br>
	</form>
	<%@ include file="Footer.jsp"%>
</body>

</html>

