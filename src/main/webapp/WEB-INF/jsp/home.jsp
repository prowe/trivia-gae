<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="includes/headSection.jsp" %>

<title>Insert title here</title>
</head>
<body>
	<%@ include file="includes/navbar.jsp" %>
	<div id="main-body">
	<h1>Welcome!</h1>
		<c:forEach items="${userQuestionList}" var="uq">
			<div class="well">
				<c:out value='${uq.contest.question}' />
				<a href="<c:url value='/questions/${uq.contestant.username}/${uq.contest.contestId}/answer.html' />">Answer</a>
			</div>
		</c:forEach>
	</div>
	<%@ include file="includes/footer.jsp" %>
</body>
</html>