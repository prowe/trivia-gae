<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html >
<html>
<head>
<%@ include file="../includes/headSection.jsp" %>

<title><spring:message code="brand.title" /> - Winnings</title>
</head>
<body>
	<%@ include file="../includes/navbar.jsp" %>
	
	<div id="main-body">
		<h1>Your Winnings</h1>
		
		<c:if test="${empty userQuestionList}">
			<p>You have not yet won any prizes. <a href="/">Answer more questions</a> for more chances to win.
		</c:if>
		
		<div class="modal fade" id="prizeDetailsModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
		</div>
		
		<c:forEach items="${userQuestionList}" var="uq">
			<div>
				<a data-toggle="modal" data-target="#prizeDetailsModal" href="<c:url value='/contests/${uq.contest.contestId}/prizeDetails.html' />"><c:out value="${uq.contest.prize.title}" /></a>
			</div>
		</c:forEach>
	</div>
	<%@ include file="../includes/footer.jsp" %>
</body>
</html>