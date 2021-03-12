
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="in.co.sunrays.proj3.dto.TimeTableDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.sunrays.proj3.model.ModelFactory"%>
<%@page import="in.co.sunrays.proj3.model.TimeTableModelInt"%>
<%@page import="in.co.sunrays.proj3.controller.TimeTableListCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.sunrays.proj3.util.HTMLUtility"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorView.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>TimeTableList View</title>
<!--    favicon-->
<link rel="shortcut icon"
	href="/ORSProject3/theam_wel/image/fav-icon.png" type="image/x-i">
<!-- datepicker files -->
<!-- <link rel="stylesheet"
	href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script> -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/js/bootstrap-datetimepicker.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/css/bootstrap-datetimepicker.min.css">
<style type="text/css">
body {
	background-image: url("/OrsProject_3/img/image2.jpg");
	background-size: cover;
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-position: center;
}

.table-hover tbody tr:hover td {
	background-color: #0064ff36;
}
</style>

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

</head>

<body class="img-responsive">
	<%@include file="Header.jsp"%>
	<br>
	<br>
	<form action="<%=ORSView.TIME_TABLE_LIST_CTL%>" method="post">
		<jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.TimeTableDTO"
			scope="request"></jsp:useBean>
		<jsp:useBean id="model"
			class="in.co.sunrays.proj3.model.TimeTableModelHibImpl"
			scope="request"></jsp:useBean>

		<div class="container">
			<div class="row">
				<div class=""></div>
				<div class="">
					<div class="panel">
						<div class="" align="center">
							<H1>
								<span class="glyphicon glyphicon-list"><b>TimeTableList</b></span>
								<!-- <hr style="height: 2px; color: #000000;"> -->
							</H1>
							<br> <br>
						</div>
						<div class="panel-body">
							<div align="center">
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
							</div>
							<br> <br>
							<%
								List list = ServletUtility.getList(request);
							%>
							<%
								List subjectList = (List) request.getAttribute("subjectList");
								List courseList = (List) request.getAttribute("courseList");
							%>
							<div class="form-inline">
								<%
									if (list.size() > 0 && list != null) {
								%>
								<div class="form-group  text-center">
									<label class="control-label" for="collegeName">Subject
										:</label>
									<%=HTMLUtility.getList("subjectId", String.valueOf(dto.getSubjectId()), subjectList)%>
								</div>
								&emsp;
								<%-- <div class="form-group  text-center">
									<label class="control-label" for="collegeName">Course :</label>
									<%=HTMLUtility.getList("courseId", String.valueOf(dto.getCourseId()), courseList)%>
								</div> --%>
								<%-- &emsp;
								<div class="form-group  text-center">
									<label class="control-label text-center" for="facultyName">Semester
										: </label>
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
								</div> --%>
								&emsp;
								<div class="form-group  text-center">
									<label class="control-label text-center" for="exameDate">ExameDate
										: </label> <input type="text" class="form-control" name="examDate"
										size=15 placeholder="Enter Exame Date" id="datepicker"
										readonly="readonly"
										value="<%=ServletUtility.getParameter("examDate", request)%>">
								</div>

								&emsp;
								<div class="form-group">
									<button type="submit" name="operation"
										class=" form-control btn btn-primary "
										value="<%=TimeTableListCtl.OP_SEARCH%>">
										<span class="glyphicon glyphicon-search"></span> Search
									</button>
									&emsp;&emsp;

									<button type="submit" name="operation"
										class=" form-control btn btn-warning"
										value="<%=TimeTableListCtl.OP_RESET%>">
										<span class="	glyphicon glyphicon-refresh"></span> Reset
									</button>
								</div>
							</div>
							<br> <br>

							<div class="box-body table-responsive">
								<table class="table  table-bordered table-striped table-hover">
									<thead>
										<tr>
											<th align="left"><input type="checkbox" id="mainbox"
												onchange="selectAll(this)"> Select All</th>
											<th style="text-align: center;">S.No.</th>
											<th style="text-align: center;">Course Name</th>
											<th style="text-align: center;">Semester</th>
											<th style="text-align: center;">Subject Name</th>
											<th style="text-align: center;">Exam Date</th>
											<th style="text-align: center;">Exam Time</th>
											<th style="text-align: center;">Edit</th>
										</tr>
									</thead>
									<%
										TimeTableModelInt timetableModel = ModelFactory.getInstance().getTimeTableModel();
											List l = timetableModel.list();
											int count = l.size();
											int pageNo = ServletUtility.getPageNo(request);
											int pageSize = ServletUtility.getPageSize(request);
											int index = ((pageNo - 1) * pageSize);
											Iterator<TimeTableDTO> it = list.iterator();
											while (it.hasNext()) {
												dto = it.next();
									%>
									<tbody>
										<tr>
											<td align="left"><input type="checkbox" name="ids"
												onchange="selectone(this)" value="<%=dto.getId()%>"></td>
											<td align="center"><%=++index%></td>
											<td align="center"><%=dto.getCourseName()%></td>
											<td align="center"><%=dto.getSemester()%></td>
											<td align="center"><%=dto.getSubjectName()%></td>
											<td align="center"><%=new SimpleDateFormat("dd/MM/yyyy").format(dto.getExamDate())%></td>
											<td align="center"><%=dto.getTime()%></td>
											<td align="center"><a
												href="TimeTableCtl?id=<%=dto.getId()%>"> <span
													class="glyphicon glyphicon-edit"></span></a></td>
											<input type="hidden" name="pageNo" value="<%=pageNo%>">
											<input type="hidden" name="pageSize" value="<%=pageSize%>">
										</tr>
									</tbody>
									<%
										}
									%>
								</table>
							</div>
							<div>

								<div class="row">

									<div class="form-group">
										<div class="col-sm-1"></div>
										<div class="col-sm-2">
											<button type="submit" name="operation"
												value="<%=TimeTableListCtl.OP_PREVIOUS%>"
												<%=(pageNo == 1) ? "disabled" : ""%> class="btn btn-primary">
												<span class="glyphicon glyphicon-backward"></span>
												<%=TimeTableListCtl.OP_PREVIOUS%>
											</button>
										</div>
										<div class="col-sm-1"></div>
										<div class="col-sm-2">
											<button type="submit" name="operation"
												value="<%=TimeTableListCtl.OP_NEW%>" class="btn btn-primary">
												<span class="glyphicon glyphicon-plus"></span>
												<%=TimeTableListCtl.OP_NEW%>
											</button>
										</div>
										<div class="col-sm-1"></div>
										<div class="col-sm-2">
											<button type="submit" name="operation"
												value="<%=TimeTableListCtl.OP_DELETE%>"
												class="btn btn-danger">
												<span class="glyphicon glyphicon-trash"></span>
												<%=TimeTableListCtl.OP_DELETE%>
											</button>
										</div>
										<div class="col-sm-1"></div>
										<div class="col-sm-2">
											<button type="submit" name="operation"
												value="<%=TimeTableListCtl.OP_NEXT%>"
												<%=(index == count || dto.getId() == 0 || list.size() < pageSize) ? "disabled" : ""%>
												class="btn btn-primary"><%=TimeTableListCtl.OP_NEXT%>
												<span class="glyphicon glyphicon-forward"></span>
											</button>
										</div>


									</div>
								</div>
							</div>
							<%
								} else {
							%>

							<table align="center">
								<tr>
									<td>
										<button type="sub" name="operation"
											value="<%=TimeTableListCtl.OP_BACK%>"
											style="width: 150px; height: 47px; font-size: 16px; background-color:"
											class="btn btn-default">
											<span class="glyphicon glyphicon-folder-open"></span>&nbsp;&nbsp;Back
										</button>
									</td>
								</tr>
							</table>
							<%
								}
							%>
						</div>


						<div class=""></div>
					</div>
				</div>
	</form>
	<br>
	<br>
	<%@include file="Footer.jsp"%>
</body>
</html>
