<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="user.UserDAO" %>
<%@ page import="java.io.PrintWriter" %>
<%
request.setCharacterEncoding("utf-8");
%>
<jsp:useBean id="user" class="user.User" scope="page" />
<!-- join에서 받아온 값 위 user에 설정 -->
<jsp:setProperty name="user" property="userId" />
<jsp:setProperty name="user" property="userPassword" />
<jsp:setProperty name="user" property="userName" />
<jsp:setProperty name="user" property="userGender" />
<jsp:setProperty name="user" property="userEmail" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>JSP 게시판 웹 사이트</title>
</head>
<body>
	<%
	String userID = null;
	if (session.getAttribute("userId")!=null){
		userID = (String)session.getAttribute("userId");
	}
	if (userID != null){
		PrintWriter script = response.getWriter();
		script.print("<script>");
		script.print("alert('이미 로그인이 되어 있습니다.');");
		script.print("location.href='main.jsp'");
		script.print("</script>");
	}
	//입력되지 않은 항목 확인
	if (user.getUserId()==null || user.getUserPassword()==null || user.getUserName()==null || user.getUserGender()==null || user.getUserEmail()==null){
		PrintWriter script = response.getWriter();
		script.print("<script>");
		script.print("alert('입력이 안 된 사항이 있습니다.');");
		script.print("history.back()");
		script.print("</script>");
	} else {
		// 모두 입력되었다면 dao 생성
		UserDAO userDAO = new UserDAO();
			//join 실행
			int result = userDAO.join(user);
			//발생 값이 오류일때
			if(result == -1){
				PrintWriter script = response.getWriter();
				script.print("<script>");
				script.print("alert('이미 존재하는 아이디 입니다.');");
				script.print("history.back()");
				script.print("</script>");
			} else {
				//가입 성공시 세션 발생
				session.setAttribute("userId", user.getUserId());
				//정상적으로 실행시 메인창으로 이동
				PrintWriter script = response.getWriter();
				script.print("<script>");
				script.print("location.href='main.jsp'");
				script.print("</script>");
			}
	}
	%>
</body>
</html>