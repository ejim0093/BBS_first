<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bbs.BbsDAO" %>
<%@ page import="java.io.PrintWriter" %>
<%
request.setCharacterEncoding("utf-8");
%>
<jsp:useBean id="bbs" class="bbs.Bbs" scope="page" />
<!-- join에서 받아온 값 위 user에 설정 -->
<jsp:setProperty name="bbs" property="bbsTitle" />
<jsp:setProperty name="bbs" property="bbsContent" />
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
	if (userID == null){
		PrintWriter script = response.getWriter();
		script.print("<script>");
		script.print("alert('로그인이 필요합니다.');");
		script.print("location.href='login.jsp'");
		script.print("</script>");
	} else { // 정상적으로 로그인이 되어있는 경우에만 진입		
		//입력되지 않은 항목 확인
		if (bbs.getBbsTitle()==null || bbs.getBbsContent()==null){
			PrintWriter script = response.getWriter();
			script.print("<script>");
			script.print("alert('입력이 안 된 사항이 있습니다.');");
			script.print("history.back()");
			script.print("</script>");
		} else {
			// 모두 입력되었다면 dao 생성
			BbsDAO bbsDAO = new BbsDAO();
				//join 실행
				int result = bbsDAO.write(bbs.getBbsTitle(), userID, bbs.getBbsContent());
				//발생 값이 오류일때
				if(result == -1){
					PrintWriter script = response.getWriter();
					script.print("<script>");
					script.print("alert('글쓰기에 실패했습니다');");
					script.print("history.back()");
					script.print("</script>");
				} else {
					//정상적으로 실행시 게시판으로 이동
					PrintWriter script = response.getWriter();
					script.print("<script>");
					script.print("location.href='bbs.jsp'");
					script.print("</script>");
				}
		}
	}
	%>
</body>
</html>