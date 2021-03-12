
<%@page import="in.co.sunrays.proj3.util.DataUtility"%>
<%@page import="in.co.sunrays.proj3.controller.GetMarksheetCtl"%>
<%@page import="in.co.sunrays.proj3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorView.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>GetMarksheet View</title>
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
	<form action="<%=ORSView.GET_MARKSHEET_CTL%>" method="post">
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
									<span class="glyphicon glyphicon-list"><b>GetMarksheet</b></span>
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

							<div class="form-inline">
								<div class="form-group  text-center" style="margin-left: 20%;">
									<label class="control-label" for="rollno">RollNumber :</label>
									<input type="text" class="form-control" name="rollNo" size=20
										placeholder="Enter Roll Number"
										value="<%=ServletUtility.getParameter("rollNo", request)%>">
								</div>
								&emsp;&emsp;&emsp;
								<div class="form-group">
									<button type="submit" name="operation"
										class=" form-control btn btn-primary"
										value="<%=GetMarksheetCtl.OP_GO%>">
										<span class="glyphicon glyphicon-search"></span> Go
									</button>
									&emsp;&emsp;
									<button type="submit" name="operation"
										class=" form-control btn btn-warning"
										value="<%=GetMarksheetCtl.OP_RESET%>">
										<span class="	glyphicon glyphicon-refresh"></span> Reset
									</button>
								</div>
							</div>
							<br> <br>
							<%
								if (dto.getId() > 0) {
							%>
							<input type="hidden" name="id" value="<%=dto.getId()%>">
							<!-- <input type="submit" class="btn btn-primary" name="operation" value="Print"> -->
							<%-- <button type="submit" name="operation"
								class=" form-control btn btn-primary" value="<%=dto.getId()%>">
								<span class="	glyphicon glyphicon-print"></span> Print
							</button> --%>

							<%
								}
							%>
							<br> <br>
							<%
								if (dto.getRollNo() != null && dto.getRollNo().trim().length() > 0) {
									String grade = null;
									String result = null;
									long phyMarks = DataUtility.getLong(dto.getPhysics() + "");
									long chemMarks = DataUtility.getLong(dto.getChemistry() + "");
									long MathMarks = DataUtility.getLong(dto.getMaths() + "");
									long totalMarks = (phyMarks + chemMarks + MathMarks);

									float percentage = (float) totalMarks / 3;
									percentage = percentage * 100;
									percentage = Math.round(percentage);
									percentage = percentage / 100;

									if (phyMarks >32 && chemMarks >32 && MathMarks >32) {
										if (totalMarks >= 280) {
											grade = "A+";
											result = "Pass";
										} else if (totalMarks >= 195) {
											grade = "A";
											result = "Pass";
										} else if (totalMarks < 195 && totalMarks >= 150) {
											grade = "B";
											result = "Pass";
										} else {
											grade = "c";
											result = "Pass";
										}

									} else {
										grade = "D";
										result = "Fail";
									}

									String div = null;
									if (phyMarks >32 && chemMarks >32 && MathMarks >32) {
									if (percentage >= 60) {
										div = "First";
									} else if (percentage >= 45 && percentage < 60) {
										div = "Second";
									} else if (percentage >= 33 && percentage < 45) {
										div = "Third";
									} else if (percentage < 33) {
										div = "Fail";
									}
									}else{
										div = "Fail";
									}
							%>
							<div class="box-body table-responsive">
								<table class="table  table-bordered table-striped table-hover">
									<thead>
									</thead>
									<tbody>
										<tr>
											<td colspan="4" bgcolor="#BFC9CA" align="center"><b><i
													style="font-size: x-large;">Provisional Marksheet</i></b></td>
										</tr>
										<tr>
											<td style="text-align: center;">Roll No. :</td>
											<td colspan="3"><%=DataUtility.getStringData(dto.getRollNo())%></td>
										</tr>
										<tr>
											<td style="text-align: center;">Name :</td>
											<td colspan="3" class="space"><%=DataUtility.getStringData(dto.getName())%></td>
										</tr>
										<tr>
											<td style="text-align: center;"><b>Subject:</b></td>
											<td style="text-align: center;"><b>Maximum Marks:</b></td>
											<td style="text-align: center;"><b>Minimum Marks:</b></td>
											<td style="text-align: center;"><b>Obtained Marks:</b></td>
										</tr>
										<tr>
											<td style="text-align: center;">Physics</td>
											<td style="text-align: center;">100</td>
											<td style="text-align: center;">33</td>
											<td style="text-align: center;"><%=DataUtility.getStringData(dto.getPhysics())%></td>
										</tr>
										<tr>
											<td style="text-align: center;">Chemistry</td>
											<td style="text-align: center;">100</td>
											<td style="text-align: center;">33</td>
											<td style="text-align: center;"><%=DataUtility.getStringData(dto.getChemistry())%></td>
										</tr>
										<tr>
											<td style="text-align: center;">Math</td>
											<td style="text-align: center;">100</td>
											<td style="text-align: center;">33</td>
											<td style="text-align: center;"><%=DataUtility.getStringData(dto.getMaths())%></td>
										</tr>
										<tr>
											<td style="text-align: center;">Total</td>
											<td style="text-align: center;">300</td>
											<td style="text-align: center;"></td>
											<td style="text-align: center;"><%=totalMarks%></td>
										</tr>
									</tbody>
								</table>
							</div>

							<div class="box-body table-responsive">
								<table class="table  table-bordered table-striped table-hover">

									<tr>
										<td style="text-align: center;">Result
										</th>
										<%
											if ("PASS".equalsIgnoreCase(result)) {
										%>
										<td align="center"><font color="green"><%=result%></font></td>
										<td style="text-align: center;">Grade:
										</th>
										<td align="center"><font color="green"><%=grade%></font></td>
										<%
											} else {
										%>

										<td align="center"><font color="red"><%=result%></font></td>
										<td style="text-align: center;">Grade:
										</th>
										<td align="center"><font color="green"><%=grade%></font></td>
										<%
											}
										%>

										<td style="text-align: center;">Division:
										</th>
										<td style="text-align: center;"><%=div%></td>

										<td style="text-align: center;">Percentage:
										</th>
										<td align="center"><%=percentage%>%</td>

									</tr>

								</table>
							</div>
							<%
								}
							%>
							<div></div>


							<div class=""></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
	<br>
	<br>
	<%@include file="Footer.jsp"%>
</body>
</html>
