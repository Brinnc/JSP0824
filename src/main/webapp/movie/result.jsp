<%@page import="org.sp.mvc.model.blood.BloodManager"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<% 
	//이 jsp요청과 관련된 session으로부터 어떤 저장된 데이터가 들어있다면 꺼내서 결과로 사용
	//String msg=(String)session.getAttribute("msg");

	String msg=(String)request.getAttribute("msg");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<p>영화 평점</p>
	<h1><%=msg %></h1>
</body>
</html>