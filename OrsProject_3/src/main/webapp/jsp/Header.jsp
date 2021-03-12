
<!DOCTYPE html>
<%@page import="in.co.sunrays.proj3.dto.RoleDTO"%>
<%@page import="in.co.sunrays.proj3.controller.ORSView"%>
<%@page import="in.co.sunrays.proj3.dto.UserDTO"%>
<%@page errorPage="ErrorView.jsp" %>
<html>
<title></title>
<!-- favicon -->
<link rel="shortcut icon" href="../theam_wel/image/fav-icon.png"
	type="image/x-icon">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>


<!-- datepicker files -->
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<style type="text/css">
#dcolor {
	font-size: 110%;
	color: white;
}

/* .glyphicon-refresh:hover {
	animation: fa-spin 1s infinite linear;
} */
.btn-primary {
	height: 35px;
	width: 90px;
	background: lightseagreen;
	box-shadow: 0 0 1px #ccc;
	-webkit-transition: all 0.5s ease-in-out;
	border: 0px;
	color: #fff;
	border-color: none;
}

.btn-primary:hover {
	-webkit-transform: scale(1.1);
	background: #31708f;
}

.btn-success {
	height: 35px;
	width: 90px;
	box-shadow: 0 0 1px #ccc;
	-webkit-transition: all 0.5s ease-in-out;
	border: 0px;
	color: #fff;
	border-color: none;
}

.btn-success:hover {
	-webkit-transform: scale(1.1);
	background: green;
}

.btn-info {
	height: 35px;
	width: 90px;
	box-shadow: 0 0 1px #ccc;
	-webkit-transition: all 0.5s ease-in-out;
	border: 0px;
	color: #fff;
	border-color: none;
}

.btn-info:hover {
	-webkit-transform: scale(1.1);
	background: #33d6ff;
}

.btn-warning {
	height: 35px;
	width: 90px;
	box-shadow: 0 0 1px #ccc;
	-webkit-transition: all 0.5s ease-in-out;
	border: 0px;
	color: #fff;
	border-color: none;
}

.btn-warning:hover {
	-webkit-transform: scale(1.1);
	background: #ff9933;
}

.btn-danger {
	height: 35px;
	width: 90px;
	box-shadow: 0 0 1px #ccc;
	-webkit-transition: all 0.5s ease-in-out;
	border: 0px;
	color: white;
	border-color: none;
}

.btn-danger:hover {
	-webkit-transform: scale(1.1);
	background: #ff3333;
}

.btn-default {
	height: 47px;
	width: 150px;
	font-size: 16px;
	box-shadow: 0 0 1px #ccc;
	-webkit-transition: all 0.5s ease-in-out;
	border: 0px;
	color: black;
	border-color: white;
}

.btn-default:hover {
	-webkit-transform: scale(1.1);
	background: white;
}

.text-primary {
	color: blue;
}

.text-danger {
	margin-left: 50px;
	color: #ff0000;
}

.table {
	bordercolor: blue;
	background-color: gray;
}

.navbar-inverse {
	/*  background-color: #7986CB ; */
	border-color: silver;
	color: black;
	/*  background-image: url("img/images (2)navar00.jpg");  */
}

element.style {
	color: #000;
}

body {
	padding-top: 110px;
}

#top {
	margin-bottom: 100px;
}

.glyphicon-edit {
	color: #800000;
}

.panel {
	box-shadow: 9px 8px 7px #001a33;
	background: rgba(255, 255, 255, 0.7);
}

.panel>.panel-heading {
	background-position: center;
	font-size: 30px;
	text-align: center;
	background: rgba(255, 250, 225, 0.5);
	color: #993333;
	/* font-family: cursive; */
	background-size: appworkspace;
	-webkit-background-size: cover;
	-moz-background-size: cover;
	-o-background-size: cover;
}

.dropdown-menu {
	background-color: #dedfe2;
}

.list-heading {
	background-color: #060613;
}

#error {
	font-size: 20px;
	position: center;
	color: red;
}

#success {
	font-size: 20px;
	position: center;
	color: green;
}
</style>
<script type="text/javascript">
	$(function() {
		$(".dropdown").hover(function() {
			$('.dropdown-menu', this).stop(true, true).fadeIn("fast");
			$(this).toggleClass('open');
		}, function() {
			$('.dropdown-menu', this).stop(true, true).fadeOut("fast");
			$(this).toggleClass('open');
		});
	});

	$(function() {
		$(".dropdown").click(function() {
			$(".dropdown-menu", this).toggle();
		});
	});
</script>
<script type="text/javascript">
	function selectAll(source) {
		checkboxes = document.getElementsByName('ids');

		for (var i = 0, n = checkboxes.length; i < n; i++) {
			checkboxes[i].checked = source.checked;
		}
	}

	$(document).ready(function() {
		$('[name="ids"]').click(function() {
			if (!($(this)[0].checked)) {
				$('[onclick="selectAll(this)"]')[0].checked = false;
			}
			;

		});

	});
</script>

<script>
	function selectAll(source) {
		checkboxes = document.getElementsByName('ids');
		for (var i = 0, n = checkboxes.length; i < n; i++) {
			checkboxes[i].checked = source.checked;
		}
	}

	function selectone(so) {
		checkboxes = document.getElementById('mainbox');
		unbox = document.getElementsByName('ids');
		var box = false;
		for (var i = 0, n = unbox.length; i < n; i++) {
			if (unbox[i].checked == true) {
				box = true;
			} else {
				box = false;
				break;
			}
		}
		checkboxes.checked = box;
	}
</script>

</head>

<body>
	<nav class=" navbar navbar-inverse navbar-fixed-top"
		style="background-color:">
		<div class="container-fluid">
			<img alt="" src="/OrsProject_3/img/rayslogo.png" align="left"
				style="width: 10%; height: 50%;">
			<%
				UserDTO userDto = (UserDTO) session.getAttribute("user");
			%>
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar-collapse"
					aria-expanded="false">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="<%=ORSView.WELCOME_CTL%>" id="dcolor"
					style="font-size: 150%;"><span class="glyphicon glyphicon-home"
					style="margin-left: 25%;"></span></a>
			</div>

			<div class="collapse navbar-collapse" id="navbar-collapse">
				<ul class="nav navbar-nav navbar-right" style="background-color:">
					<%
						if (userDto == null) {
					%>
					<li><a href="<%=ORSView.LOGIN_CTL%>" id="dcolor"
						style="width: auto; font-family: cursive; color: #e0ebeb;"> <span
							class="glyphicon glyphicon-log-in"
							style="font-size: 20px; margin-right: 8px;"></span> Login
					</a></li>
					<li><a href="<%=ORSView.USER_REGISTRATION_CTL%>" id="dcolor"
						style="width: auto; font-family: cursive; color: #e0ebeb;"> <span
							class="glyphicon glyphicon-user"
							style="font-size: 20px; margin-right: 8px;"></span> Register
					</a></li>

					<%
						} else {
					%>
					<li class="dropdown"><a id="dcolor" class="dropdown-toggle"
						data-toggle="dropdown aria-haspopup=" true" aria-expanded="false">
							<b> <span class="glyphicon glyphicon-user" aria-hidden="true"
								style="font-size: 18px; margin-right: 3px;"></span> <%
 	if (userDto != null) {
 %>Hello,<%=userDto.getFirstName()%>(<%=session.getAttribute("role")%>)<span
								class="caret"></span> <%
 	}
 %>
						</b>
					</a>
						<ul class="dropdown-menu" style="background-color:">

							<li><a href="<%=ORSView.MY_PROFILE_CTL%>"> <span
									class="glyphicon glyphicon-edit" aria-hidden="true"
									style="color:"></span> <font style="font-family: cursive;"><b>Edit
											Profile</b></font></a></li>

							<li><a href="<%=ORSView.CHANGE_PASSWORD_CTL%>"> <span
									class="glyphicon glyphicon-cog" style="color:"></span> <font
									style="font-family: cursive;"><b>Change Password</b></font>
							</a></li>


							<li><a href="<%=ORSView.JAVA_DOC_VIEW%>" target="blank">
									<span class="glyphicon glyphicon-file" aria-hidden="true"
									style="color: #734d26"></span> <font
									style="font-family: cursive;"><b>Java Doc</b></font>
							</a></li>


							<li><a href="<%=ORSView.LOGIN_CTL%>?operation=logout"> <font
									style="font-family: cursive; color: #800000;"> <span
										class="glyphicon glyphicon-log-out" aria-hidden="true"></span>
										<b>Logout</b></font></a></li>
						</ul></li>
				</ul>
				<%
					if (userDto.getRoleId() == RoleDTO.ADMIN) {
				%>

				<ul class="nav navbar-nav">
					<li class="dropdown"><a class="dropdown-toggle" id="dcolor"
						data-toggle="dropdown" href="#">User<span class="caret"></span>
					</a>
						<ul class="dropdown-menu" style="background-color: wheat">
							<li><a href="<%=ORSView.USER_CTL%>"><span
									class="glyphicon glyphicon-user" style="color: #990000"></span><font
									style="font-family: cursive;"><b>Add User</b></font></a></li>
							<li><a href="<%=ORSView.USER_LIST_CTL%>"><span
									class="glyphicon glyphicon-list-alt" style="color: #734d26"></span><font
									style="font-family: cursive;"><b>UserList</b></font></a></li>
						</ul></li>

					<li class="dropdown"><a class="dropdown-toggle" id="dcolor"
						data-toggle="dropdown" href="#">Role<span class="caret"></span>
					</a>
						<ul class="dropdown-menu" style="background-color: wheat">
							<li><a href="<%=ORSView.ROLE_CTL%>"><span
									class="glyphicon glyphicon-education" style="color: #990000"></span><font
									style="font-family: cursive;"><b>Add Role</b></font></a></li>
							<li><a href="<%=ORSView.ROLE_LIST_CTL%>"><span
									class="glyphicon glyphicon-list-alt" style="color: #734d26"></span><font
									style="font-family: cursive;"><b>RoleList</b></font></a></li>
						</ul></li>



					<li class="dropdown"><a class="dropdown-toggle" id="dcolor"
						data-toggle="dropdown" href="#">Student<span class="caret"></span>
					</a>
						<ul class="dropdown-menu" style="background-color: wheat">
							<li><a href="<%=ORSView.STUDENT_CTL%>"><span
									class="glyphicon glyphicon-education" style="color: #990000"></span><font
									style="font-family: cursive;"><b>Add Student</b></font></a></li>
							<li><a href="<%=ORSView.STUDENT_LIST_CTL%>"><span
									class="glyphicon glyphicon-list-alt" style="color: #734d26"></span><font
									style="font-family: cursive;"><b>StudentList</b></font></a></li>
						</ul></li>


					<li class="dropdown"><a class="dropdown-toggle" id="dcolor"
						data-toggle="dropdown" href="#">College<span class="caret"></span>
					</a>
						<ul class="dropdown-menu" style="background-color: wheat">
							<li><a href="<%=ORSView.COLLEGE_CTL%>"><span
									class="glyphicon glyphicon-education" style="color: #990000"></span><font
									style="font-family: cursive;"><b>Add College</b></font></a></li>
							<li><a href="<%=ORSView.COLLEGE_LIST_CTL%>"><span
									class="glyphicon glyphicon-list-alt" style="color: #734d26"></span><font
									style="font-family: cursive;"><b>CollegeList</b></font></a></li>
						</ul></li>

					<li class="dropdown"><a class="dropdown-toggle" id="dcolor"
						data-toggle="dropdown" href="#">Marksheet<span class="caret"></span>
					</a>
						<ul class="dropdown-menu" style="background-color: wheat">
							<li><a href="<%=ORSView.GET_MARKSHEET_CTL%>"><span
									class="glyphicon glyphicon-user" style="color: #734d26"></span><font
									style="font-family: cursive;"><b>GetMarksheet</b></font></a></li>
							<li><a href="<%=ORSView.MARKSHEET_CTL%>"><span
									class="glyphicon glyphicon-education" style="color: #990000"></span><font
									style="font-family: cursive;"><b>Add Marksheet</b></font></a></li>
							<li><a href="<%=ORSView.MARKSHEET_LIST_CTL%>"><span
									class="glyphicon glyphicon-list-alt" style="color: #734d26"></span><font
									style="font-family: cursive;"><b>MarksheetList</b></font></a></li>
							<li><a href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>"><span
									class="glyphicon glyphicon-list-alt" style="color: #734d26"></span><font
									style="font-family: cursive;"><b>MarksheetMeritList</b></font></a></li>
						</ul></li>

					<li class="dropdown"><a class="dropdown-toggle" id="dcolor"
						data-toggle="dropdown" href="#">Faculty<span class="caret"></span>
					</a>
						<ul class="dropdown-menu" style="background-color: wheat">
							<li><a href="<%=ORSView.FACULTY_CTL%>"><span
									class="glyphicon glyphicon-education" style="color: #990000"></span><font
									style="font-family: cursive;"><b>Add Faculty</b></font></a></li>
							<li><a href="<%=ORSView.FACULTY_LIST_CTL%>"><span
									class="glyphicon glyphicon-list-alt" style="color: #734d26"></span><font
									style="font-family: cursive;"><b>FacultyList</b></font></a></li>
						</ul></li>

					<li class="dropdown"><a class="dropdown-toggle" id="dcolor"
						data-toggle="dropdown" href="#">Course<span class="caret"></span>
					</a>
						<ul class="dropdown-menu" style="background-color: wheat">
							<li><a href="<%=ORSView.COURSE_CTL%>"><span
									class="glyphicon glyphicon-education" style="color: #990000"></span><font
									style="font-family: cursive;"><b>Add Course</b></font></a></li>
							<li><a href="<%=ORSView.COURSE_LIST_CTL%>"><span
									class="glyphicon glyphicon-list-alt" style="color: #734d26"></span><font
									style="font-family: cursive;"><b>CourseList</b></font></a></li>
						</ul></li>
					<li class="dropdown"><a class="dropdown-toggle" id="dcolor"
						data-toggle="dropdown" href="#">Subject<span class="caret"></span>
					</a>
						<ul class="dropdown-menu" style="background-color: wheat">
							<li><a href="<%=ORSView.SUBJECT_CTL%>"><span
									class="glyphicon glyphicon-education" style="color: #990000"></span><font
									style="font-family: cursive;"><b>Add Subject</b></font></a></li>
							<li><a href="<%=ORSView.SUBJECT_LIST_CTL%>"><span
									class="glyphicon glyphicon-list-alt" style="color: #734d26"></span><font
									style="font-family: cursive;"><b>SubjectList</b></font></a></li>
						</ul></li>
					<li class="dropdown"><a class="dropdown-toggle" id="dcolor"
						data-toggle="dropdown" href="#">TimeTable<span class="caret"></span>
					</a>
						<ul class="dropdown-menu" style="background-color: wheat">
							<li><a href="<%=ORSView.TIME_TABLE_CTL%>"><span
									class="glyphicon glyphicon-education" style="color: #990000"></span><font
									style="font-family: cursive;"><b>Add TimeTable</b></font></a></li>
							<li><a href="<%=ORSView.TIME_TABLE_LIST_CTL%>"><span
									class="glyphicon glyphicon-list-alt" style="color: #734d26"></span><font
									style="font-family: cursive;"><b>TimeTableList</b></font></a></li>
						</ul></li>


				</ul>
				<%
					}
				%>
				<%
					if (userDto.getRoleId() == RoleDTO.COLLEGE_SCHOOL) {
				%><ul class="nav navbar-nav">


					<li class="dropdown"><a class="dropdown-toggle" id="dcolor"
						data-toggle="dropdown" href="#">Student<span class="caret"></span>
					</a>
						<ul class="dropdown-menu" style="background-color: wheat">
							<li><a href="<%=ORSView.STUDENT_CTL%>"><span
									class="glyphicon glyphicon-education" style="color: #990000"></span><font
									style="font-family: cursive;"><b>Add Student</b></font></a></li>
							<li><a href="<%=ORSView.STUDENT_LIST_CTL%>"><span
									class="glyphicon glyphicon-list-alt" style="color: #734d26"></span><font
									style="font-family: cursive;"><b>StudentList</b></font></a></li>
						</ul></li>
					<li class="dropdown"><a class="dropdown-toggle" id="dcolor"
						data-toggle="dropdown" href="#">Marksheet<span class="caret"></span>
					</a>
						<ul class="dropdown-menu" style="background-color: wheat">
							<li><a href="<%=ORSView.GET_MARKSHEET_CTL%>"><span
									class="glyphicon glyphicon-user" style="color: #734d26"></span><font
									style="font-family: cursive;"><b>GetMarksheet</b></font></a></li>
							<li><a href="<%=ORSView.MARKSHEET_CTL%>"><span
									class="glyphicon glyphicon-education" style="color: #990000"></span><font
									style="font-family: cursive;"><b>Add Marksheet</b></font></a></li>
							<li><a href="<%=ORSView.MARKSHEET_LIST_CTL%>"><span
									class="glyphicon glyphicon-list-alt" style="color: #734d26"></span><font
									style="font-family: cursive;"><b>MarksheetList</b></font></a></li>
							<li><a href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>"><span
									class="glyphicon glyphicon-list-alt" style="color: #734d26"></span><font
									style="font-family: cursive;"><b>MarksheetMeritList</b></font></a></li>
						</ul></li>

					<li class="dropdown"><a class="dropdown-toggle" id="dcolor"
						data-toggle="dropdown" href="#">Faculty<span class="caret"></span>
					</a>
						<ul class="dropdown-menu" style="background-color: wheat">
							<li><a href="<%=ORSView.FACULTY_CTL%>"><span
									class="glyphicon glyphicon-education" style="color: #990000"></span><font
									style="font-family: cursive;"><b>Add Faculty</b></font></a></li>
							<li><a href="<%=ORSView.FACULTY_LIST_CTL%>"><span
									class="glyphicon glyphicon-list-alt" style="color: #734d26"></span><font
									style="font-family: cursive;"><b>FacultyList</b></font></a></li>
						</ul></li>

				</ul>
				<%
					}
				%>
				<%
					if (userDto.getRoleId() == RoleDTO.KIOSK) {
				%><ul class="nav navbar-nav">
					<li class="dropdown"><a class="dropdown-toggle" id="dcolor"
						data-toggle="dropdown" href="#">Marksheet<span class="caret"></span>
					</a>
						<ul class="dropdown-menu" style="background-color: wheat">

							<li><a href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>"><span
									class="glyphicon glyphicon-list-alt" style="color: #734d26"></span><font
									style="font-family: cursive;"><b>MarksheetMeritList</b></font></a></li>
						</ul></li>

				</ul>
				<%
					}
				%>
				<%
					if (userDto.getRoleId() == RoleDTO.STUDENT) {
				%><ul class="nav navbar-nav">
					<li class="dropdown"><a class="dropdown-toggle" id="dcolor"
						data-toggle="dropdown" href="#">Marksheet<span class="caret"></span>
					</a>
						<ul class="dropdown-menu" style="background-color: wheat">
							<li><a href="<%=ORSView.GET_MARKSHEET_CTL%>"><span
									class="glyphicon glyphicon-user" style="color: #734d26"></span><font
									style="font-family: cursive;"><b>GetMarksheet</b></font></a></li>

						</ul></li>
				</ul>
				<%
					}
				%>
				<%
					}
				%>
			</div>
		</div>
	</nav>
</body>
</html>