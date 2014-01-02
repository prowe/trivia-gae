<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html>
<head>
<%@ include file="../includes/headSection.jsp" %>

<title>Insert title here</title>
</head>
<body>
	<%@ include file="../includes/navbar.jsp" %>
	<h1>Answer question</h1>
	
	<p>
		<c:out value="${userQuestion.contest.question}" />
	</p>
	
	<form method="post">
		<c:forEach items="${userQuestion.contest.possibleAnswers}" var="ans">
			<button name="answer" type="submit" value="<c:out value='${ans}' />"><c:out value='${ans}' /></button>
		</c:forEach>
	</form>
</body>
</html>