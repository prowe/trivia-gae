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
	<h1>Home</h1>

	<table>
		<c:forEach items="${contestList}" var="contest">
			<tr>
				<td>${contest.sponsor.username}</td>
				<td><a href="<c:url value='/contests/${contest.sponsor.username}/${contest.contestId}/view.html' />">${contest.contestId}</a></td>
				<td>${contest.question}</td>
				<td>${contest.correctAnswer}</td>
				<td>
					<ul>
					<c:forEach items="${contest.possibleAnswers}" var="ans">
						<li>${ans }</li>
					</c:forEach>
					</ul>
				</td>
			</tr>
		</c:forEach>
	</table>
	<a href='<c:url value="/contests/create.html" />'>Create Contest</a>
	
	
	<table>
		<c:forEach items="${userQuestionList}" var="uq">
			<tr>
				<td><a href="<c:url value='/questions/${uq.contestant.username}/${uq.contest.contestId}/answer.html' />">${uq.contest.question}</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>