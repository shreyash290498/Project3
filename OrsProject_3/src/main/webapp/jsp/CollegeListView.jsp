
<%@page import="in.co.sunrays.proj3.dto.CollegeDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.sunrays.proj3.model.CollegeModelInt"%>
<%@page import="in.co.sunrays.proj3.model.UserModelInt"%>
<%@page import="in.co.sunrays.proj3.model.ModelFactory"%>
<%@page import="in.co.sunrays.proj3.controller.CollegeListCtl"%>
<%@page import="in.co.sunrays.proj3.util.HTMLUtility"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorView.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>CollegeList View</title>
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
	<form action="<%=ORSView.COLLEGE_LIST_CTL%>" method="post">
		<jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.CollegeDTO"
			scope="request"></jsp:useBean>
		<jsp:useBean id="model"
			class="in.co.sunrays.proj3.model.UserModelHibImpl" scope="request"></jsp:useBean>

		<div class="container">
			<div class="row">
				<div class=""></div>
				<div class="">
					<div class="panel">
						<div class="panel-body">
							<div class="panel-heading" align="center">
								<H1>
									<span class="glyphicon glyphicon-list"><b>CollegeList</b></span>
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
								List collegeList = (List) request.getAttribute("collegeList");
							%>
							<div class="form-inline">
								<%
									if (list.size() > 0 && list != null) {
								%>
								<div class="form-group  text-center">
									<label class="control-label" for="collegeName">College
										:</label>
									<%=HTMLUtility.getList("roleId", String.valueOf(dto.getId()), collegeList)%>
								</div>
								&nbsp;
								<%-- <div class="form-group  text-center">
									<label class="control-label text-center" for="collegename">CollegeName
										: </label> <input type="text" class="form-control" name="collegename"
										size=20 placeholder="Enter College Name"
										value="<%=ServletUtility.getParameter("collegename", request)%>">
								</div> --%>
								&nbsp;
								<div class="form-group  text-center">
									<label class="control-label text-center" for="city">City
										: </label> <input type="text" class="form-control" name="city" size=20
										placeholder="Enter City"
										value="<%=ServletUtility.getParameter("city", request)%>">
								</div>
								&nbsp;
								<div class="form-group">
									<button type="submit" name="operation"
										class=" form-control btn btn-primary"
										value="<%=CollegeListCtl.OP_SEARCH%>">
										<span class="glyphicon glyphicon-search"></span> Search
									</button>
									&nbsp;

									<button type="submit" name="operation"
										class=" form-control btn btn-warning"
										value="<%=CollegeListCtl.OP_RESET%>">
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
											<th style="text-align: center;">Name</th>
											<th style="text-align: center;">Address</th>
											<th style="text-align: center;">State</th>
											<th style="text-align: center;">City</th>
											<th style="text-align: center;">Phone No.</th>
											<th style="text-align: center;">Edit</th>
										</tr>
									</thead>
									<%
										CollegeModelInt collegeModel = ModelFactory.getInstance().getCollegeModel();
											List l = collegeModel.list();
											int count = l.size();
											int pageNo = ServletUtility.getPageNo(request);
											int pageSize = ServletUtility.getPageSize(request);
											int index = ((pageNo - 1) * pageSize);
											Iterator<CollegeDTO> it = list.iterator();
											while (it.hasNext()) {
												dto = it.next();
									%>
									<tbody>
										<tr>
											<td align="left"><input type="checkbox" name="ids"
												onchange="selectone(this)" value="<%=dto.getId()%>"></td>
											<td align="center"><%=++index%></td>
											<td align="center"><%=dto.getName()%></td>
											<td align="center"><%=dto.getAddress()%></td>
											<td align="center"><%=dto.getState()%></td>
											<td align="center"><%=dto.getCity()%></td>
											<td align="center"><%=dto.getPhoneNo()%></td>
											<td align="center"><a
												href="CollegeCtl?id=<%=dto.getId()%>"> <span
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
												value="<%=CollegeListCtl.OP_PREVIOUS%>"
												<%=(pageNo == 1) ? "disabled" : ""%> class="btn btn-primary">
												<span class="glyphicon glyphicon-backward"></span>
												<%=CollegeListCtl.OP_PREVIOUS%>
											</button>
										</div>
										<div class="col-sm-1"></div>
										<div class="col-sm-2">
											<button type="submit" name="operation"
												value="<%=CollegeListCtl.OP_NEW%>" class="btn btn-primary">
												<span class="glyphicon glyphicon-plus"></span>
												<%=CollegeListCtl.OP_NEW%>
											</button>
										</div>
										<div class="col-sm-1"></div>
										<div class="col-sm-2">
											<button type="submit" name="operation"
												value="<%=CollegeListCtl.OP_DELETE%>" class="btn btn-danger">
												<span class="glyphicon glyphicon-trash"></span>
												<%=CollegeListCtl.OP_DELETE%>
											</button>
										</div>
										<div class="col-sm-1"></div>
										<div class="col-sm-2">
											<button type="submit" name="operation"
												value="<%=CollegeListCtl.OP_NEXT%>"
												<%=(index == count || dto.getId() == 0 || list.size() < pageSize) ? "disabled" : ""%>
												class="btn btn-primary"><%=CollegeListCtl.OP_NEXT%>
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
											value="<%=CollegeListCtl.OP_BACK%>"
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
	<%@include file="Footer.jsp"%>
</body>
</html>
