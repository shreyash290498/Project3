
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="in.co.sunrays.proj3.dto.FacultyDTO"%>
<%@page import="in.co.sunrays.proj3.controller.FacultyListCtl"%>
<%@page import="in.co.sunrays.proj3.model.FacultyModelInt"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.sunrays.proj3.model.ModelFactory"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.sunrays.proj3.util.HTMLUtility"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@page errorPage="ErrorView.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>FacultyList View</title>
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
	<form action="<%=ORSView.FACULTY_LIST_CTL%>" method="post">
		<jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.FacultyDTO"
			scope="request"></jsp:useBean>
		<jsp:useBean id="model"
			class="in.co.sunrays.proj3.model.FacultyModelHibImpl" scope="request"></jsp:useBean>

		<div class="container">
			<div class="row">
				<div class=""></div>
				<div class="">
					<div class="panel">
						<div class="panel-body">
							<div class="panel-heading" align="center">
								<H1>
									<span class="glyphicon glyphicon-list"><b>FacultyList</b></span>
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
							<%
								List facultyList = (List) request.getAttribute("facultyList");
								List subjectList = (List) request.getAttribute("subjectList");
							%>
							<div class="form-inline">
								<%
									if (list.size() > 0 && list != null) {
								%>
								<div class="form-group  text-center">
									<label class="control-label" for="facultyName">Faculty
										:</label>
									<%=HTMLUtility.getList("facultyId", String.valueOf(dto.getId()), facultyList)%>
								</div>
								<%-- &emsp;
								<div class="form-group  text-center">
									<label class="control-label" for="subjectName">Subject :</label>
									<%=HTMLUtility.getList("subjectId", String.valueOf(dto.getSubjectId()), subjectList)%>
								</div> --%>
								&emsp;
								<div class="form-group  text-center">
									<label class="control-label text-center" for="facultyName">FirstName
										: </label> <input type="text" class="form-control" name="firstName"
										size=15 placeholder="Enter Faculty Name"
										value="<%=ServletUtility.getParameter("firstName", request)%>">
								</div>
								<%-- &emsp;
								<div class="form-group  text-center">
									<label class="control-label text-center" for="loginId">LoginID
										: </label> <input type="text" class="form-control" name="loginId"
										size=15 placeholder="Enter Login Id"
										value="<%=ServletUtility.getParameter("loginId", request)%>">
								</div> --%>

								&emsp;
								<div class="form-group">
									<button type="submit" name="operation"
										class=" form-control btn btn-primary"
										value="<%=FacultyListCtl.OP_SEARCH%>">
										<span class="glyphicon glyphicon-search"></span> Search
									</button>
									&emsp;&emsp;

									<button type="submit" name="operation"
										class=" form-control btn btn-warning"
										value="<%=FacultyListCtl.OP_RESET%>">
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
											<th style="text-align: center;">First Name</th>
											<th style="text-align: center;">Last Name</th>
											<th style="text-align: center;">Login Id</th>
											<th style="text-align: center;">Mobile No</th>
											<th style="text-align: center;">Date of Joining</th>
											<th style="text-align: center;">College Name</th>
											<th style="text-align: center;">Course Name</th>
											<th style="text-align: center;">Subject Name</th>
											<th style="text-align: center;">Edit</th>
										</tr>
									</thead>
									<%
										FacultyModelInt collegeModel = ModelFactory.getInstance().getFacultyModel();
											List l = collegeModel.list();
											int count = l.size();
											int pageNo = ServletUtility.getPageNo(request);
											int pageSize = ServletUtility.getPageSize(request);
											int index = ((pageNo - 1) * pageSize);
											Iterator<FacultyDTO> it = list.iterator();
											while (it.hasNext()) {
												dto = it.next();
									%>
									<tbody>
										<tr>
											<td align="left"><input type="checkbox" name="ids"
												onchange="selectone(this)" value="<%=dto.getId()%>"></td>
											<td align="center"><%=++index%></td>
											<td align="center"><%=dto.getFirstName()%></td>
											<td align="center"><%=dto.getLastName()%></td>
											<td align="center"><%=dto.getEmailId()%></td>
											<td align="center"><%=dto.getMobileNo()%></td>
											<td align="center"><%=new SimpleDateFormat("dd/MM/yyyy").format(dto.getCreatedDatetime())%></td>
											<td align="center"><%=dto.getCollegeName()%></td>
											<td align="center"><%=dto.getCourseName()%></td>
											<td align="center"><%=dto.getSubjectName()%></td>
											<td align="center"><a
												href="FacultyCtl?id=<%=dto.getId()%>"> <span
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
												value="<%=FacultyListCtl.OP_PREVIOUS%>"
												<%=(pageNo == 1) ? "disabled" : ""%> class="btn btn-primary">
												<span class="glyphicon glyphicon-backward"></span>
												<%=FacultyListCtl.OP_PREVIOUS%>
											</button>
										</div>
										<div class="col-sm-1"></div>
										<div class="col-sm-2">
											<button type="submit" name="operation"
												value="<%=FacultyListCtl.OP_NEW%>" class="btn btn-primary">
												<span class="glyphicon glyphicon-plus"></span>
												<%=FacultyListCtl.OP_NEW%>
											</button>
										</div>
										<div class="col-sm-1"></div>
										<div class="col-sm-2">
											<button type="submit" name="operation"
												value="<%=FacultyListCtl.OP_DELETE%>" class="btn btn-danger">
												<span class="glyphicon glyphicon-trash"></span>
												<%=FacultyListCtl.OP_DELETE%>
											</button>
										</div>
										<div class="col-sm-1"></div>
										<div class="col-sm-2">
											<button type="submit" name="operation"
												value="<%=FacultyListCtl.OP_NEXT%>"
												<%=(index == count || dto.getId() == 0 || list.size() < pageSize) ? "disabled" : ""%>
												class="btn btn-primary"><%=FacultyListCtl.OP_NEXT%>
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
											value="<%=FacultyListCtl.OP_BACK%>"
											style="width: 150px; height: 47px; font-size: 16px; background-color:;"
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
	<%@include file="Footer.jsp"%>
</body>
</html>
