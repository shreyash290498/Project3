
<%@page import="in.co.sunrays.proj3.util.DataUtility"%>
<%@page import="in.co.sunrays.proj3.dto.MarksheetDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.sunrays.proj3.model.ModelFactory"%>
<%@page import="in.co.sunrays.proj3.model.MarksheetModelInt"%>
<%@page import="in.co.sunrays.proj3.controller.MarksheetListCtl"%>
<%@page import="in.co.sunrays.proj3.util.HTMLUtility"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorView.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>MarksheetList View</title>
<!--    favicon-->
<link rel="shortcut icon"
	href="/ORSProject3/theam_wel/image/fav-icon.png" type="image/x-i">

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



</head>

<body class="img-responsive">
	<%@include file="Header.jsp"%>
	<br>
	<br>
	<form action="<%=ORSView.MARKSHEET_LIST_CTL%>" method="post">
		<jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.MarksheetDTO"
			scope="request"></jsp:useBean>
		<jsp:useBean id="model"
			class="in.co.sunrays.proj3.model.MarksheetModelHibImpl"
			scope="request"></jsp:useBean>

		<div class="container">
			<div class="row">
				<div class=""></div>
				<div class="">
					<div class="panel">
						<div class="panel-body">
							<div class="panel-heading" align="center">
								<H1>
									<span class="glyphicon glyphicon-list"><b>MarksheetList</b></span>
									<!-- <hr style="height: 2px; color: #000000;"> -->
								</H1>
								<br> <br>
							</div>
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
							<div class="form-inline">
								<%
									if (list.size() > 0 && list != null) {
								%>
								<%
									List slist = (List) request.getAttribute("sList");
								%>
								&emsp;&emsp;&emsp;&emsp;
								<div class="form-group  text-center">
									<label class="control-label" for="id">Student : </label>
									<%=HTMLUtility.getList("studentId", String.valueOf(dto.getId()), slist)%>
								</div>
								&emsp;&emsp;&emsp;&emsp;
								<div class="form-group  text-center">
									<label class="control-label" for="emailId">RollNumber :</label>
									<input type="text" class="form-control" name="rollno" size=20
										placeholder="Enter Roll Number"
										value="<%=ServletUtility.getParameter("rollno", request)%>">
								</div>
								&emsp;&emsp;&emsp;
								<div class="form-group">
									<button type="submit" name="operation"
										class=" form-control btn btn-primary"
										value="<%=MarksheetListCtl.OP_SEARCH%>">
										<span class="glyphicon glyphicon-search"></span> Search
									</button>
									&emsp;&emsp;
									<button type="submit" name="operation"
										class=" form-control btn btn-warning"
										value="<%=MarksheetListCtl.OP_RESET%>">
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
											<th style="text-align: center;">RollNo</th>
											<th style="text-align: center;">Name</th>
											<th style="text-align: center;">Physics</th>
											<th style="text-align: center;">Chemistry</th>
											<th style="text-align: center;">Maths</th>
											<th style="text-align: center;">Total</th>
											<th style="text-align: center;">Percentage (%)</th>
											<th style="text-align: center;">Grade</th>
											<th style="text-align: center;">Result</th>
											<th style="text-align: center;">Edit</th>

										</tr>
									</thead>
									<%
										MarksheetModelInt marksheetModel = ModelFactory.getInstance().getMarksheetModel();
											List l = marksheetModel.list();
											int count = l.size();
											int pageNo = ServletUtility.getPageNo(request);
											int pageSize = ServletUtility.getPageSize(request);
											int index = ((pageNo - 1) * pageSize);
											Iterator<MarksheetDTO> it = list.iterator();
											while (it.hasNext()) {
												dto = it.next();
												//MarksheetDTO dto = it.next();
												String grade = null;
												String result = "<font color='green'>Pass</font>";
												String div = "";
												long p = DataUtility.getLong(dto.getPhysics() + "");
												long c = DataUtility.getLong(dto.getChemistry() + "");
												long m = DataUtility.getLong(dto.getMaths() + "");
												long totalMarks = (p + c + m);

												float percentage = (float) totalMarks / 3;
												percentage = percentage * 100;
												percentage = Math.round(percentage);
												percentage = percentage / 100;
												int avg = (int) (p + c + m) / 3;
												if (avg >= 80 && !(p <= 32 || c <= 32 || m <= 32)) {
													grade = "A+";
												} else if (avg > 60 && avg <= 80 && !(p <= 32 || c <= 32 || m <= 32)) {
													grade = "A";
												} else if (avg > 40 && avg <= 60 && !(p <= 32 || c <= 32 || m <= 32)) {
													grade = "B";
												} else if (avg < 40 && !(p <= 32 || c <= 32 || m <= 32)) {

													grade = "C";
												} else {
													grade = "D";
													result = "<font color='red'>Fail</font>";
												}
									%>
									<tbody>
										<tr>
											<td align="left"><input type="checkbox" name="ids"
												onchange="selectone(this)" value="<%=dto.getId()%>"></td>
											<td align="center"><%=++index%></td>
											<td align="center"><%=dto.getRollNo()%></td>
											<td align="center"><%=dto.getName()%></td>
											<td align="center"><%=dto.getPhysics() < 33 ? dto.getPhysics() + "<font color='red'>*</font>" : dto.getPhysics()%></td>
											<td align="center"><%=dto.getChemistry() < 33 ? dto.getChemistry() + "<font color='red'>*</font>"
							: dto.getChemistry()%></td>
											<td align="center"><%=dto.getMaths() < 33 ? dto.getMaths() + "<font color='red'>*</font>" : dto.getMaths()%></td>
											<td align="center"><%=totalMarks%></td>
											<td align="center"><%=percentage%></td>
											<td align="center"><%=grade%></td>
											<td align="center"><%=result%></td>
											<td align="center"><a
												href="MarksheetCtl?id=<%=dto.getId()%>"> <span
													class="glyphicon glyphicon-edit"></span>
											</a></td>
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
												value="<%=MarksheetListCtl.OP_PREVIOUS%>"
												<%=(pageNo == 1) ? "disabled" : ""%> class="btn btn-primary">
												<span class="glyphicon glyphicon-backward"></span>
												<%=MarksheetListCtl.OP_PREVIOUS%>
											</button>
										</div>
										<div class="col-sm-1"></div>
										<div class="col-sm-2">
											<button type="submit" name="operation"
												value="<%=MarksheetListCtl.OP_NEW%>" class="btn btn-primary">
												<span class="glyphicon glyphicon-plus"></span>
												<%=MarksheetListCtl.OP_NEW%>
											</button>
										</div>
										<div class="col-sm-1"></div>
										<div class="col-sm-2">
											<button type="submit" name="operation"
												value="<%=MarksheetListCtl.OP_DELETE%>"
												class="btn btn-danger">
												<span class="glyphicon glyphicon-trash"></span>
												<%=MarksheetListCtl.OP_DELETE%>
											</button>
										</div>
										<div class="col-sm-1"></div>
										<div class="col-sm-2">
											<button type="submit" name="operation"
												value="<%=MarksheetListCtl.OP_NEXT%>"
												<%=(index == count || dto.getId() == 0 || list.size() < pageSize) ? "disabled" : ""%>
												class="btn btn-primary"><%=MarksheetListCtl.OP_NEXT%>
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
										<button type="submit" name="operation"
											value="<%=MarksheetListCtl.OP_BACK%>"
											style="width: 150px; height: 47px; font-size: 16px; background-color:"
											class="btn btn-default">
											<span class="glyphicon glyphicon-folder-open"></span>Back
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
	<br>
	<br>
	<%@include file="Footer.jsp"%>
</body>
</html>
