<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%
request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="css/bootstrap.min.css">	<!-- css 부트스트랩 지정 -->
	<title>JSP 게시판 웹 사이트</title>
</head>
<body>
	<%
		String userId = null;
		if(session.getAttribute("userId") != null){
			userId = (String)session.getAttribute("userId");
		}
	%>
	<!-- 부트스트랩 표준 네이게이션 바 -->
	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<!-- 화면너비가 넓으면 navbar-collapse이 표시되고, navbar-toggle은 숨겨짐 -->
			<button type="button" class="navbar-toggle collapsed" 
				aria-expanded="false">
				<!-- 아이콘 내 선 (단순이미지용) -->
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class ="navbar-brand" href="main.jsp">JSP 게시판 웹사이트</a>
		</div>
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="main.jsp">메인</a></li>
				<li class="active"><a href="bbs.jsp">게시판</a></li>
			</ul>
			<%
				if(userId==null){
			%>
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" 
							data-toggle="dropdown" role="button" aria-haspopup="true"
							aria-expanded="false">접속하기<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="login.jsp">로그인</a></li>
							<li><a href="join.jsp">회원가입</a></li>
						</ul>
					</li>
				</ul>
			<%		
				} else {
			%>	
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" 
							data-toggle="dropdown" role="button" aria-haspopup="true"
							aria-expanded="false">회원관리<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="logoutAction.jsp">로그아웃</a></li>
						</ul>
					</li>
				</ul>
			<%
				}
			%>
		</div> 
	</nav>
	<div class="container">
		<div class="raw">
			<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd;">
				<thead>
					<tr>
						<th style="background-color: #eeeeee; text-align: center;">번호</th>
						<th style="background-color: #eeeeee; text-align: center;">제목</th>
						<th style="background-color: #eeeeee; text-align: center;">작성자</th>
						<th style="background-color: #eeeeee; text-align: center;">작성일</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>1</td>
						<td>테스트 게시물입니다</td>
						<td>임은진</td>
						<td>2022-06-04</td>
					</tr>
				</tbody>
				<a href="write.jsp" class="btn btn-primary pull-right">글쓰기</a>
			</table>
		</div>
	</div>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>