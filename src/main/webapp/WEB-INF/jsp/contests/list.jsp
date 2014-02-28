<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html >
<html>
<head>
<%@ include file="../includes/headSection.jsp" %>

<title>Insert title here</title>
</head>
<body>
	<%@ include file="../includes/navbar.jsp" %>
	<div id="main-body">
		<h1>Contests</h1>
		
		<table class="table contest-list">
			<c:forEach items="${contestList}" var="contest">
				<tr>
					<td><a href="<c:url value='/contests/${contest.contestId}/view.html' />">${contest.contestId}</a></td>
					<td>${contest.question.question}</td>
					<td>${contest.startTime}</td>
					<td>${contest.endTime}</td>
				</tr>
			</c:forEach>
		</table>
		<a href='create.html'>Create Contest</a>
		<a href='recalcStats.html' >Recalculate Stats</a>
	</div>
	<%@ include file="../includes/footer.jsp" %>
</body>
</html>