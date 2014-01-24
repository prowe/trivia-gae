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
			<div class="prize-pane">
				<div class="prize-title"><a data-toggle="modal" data-target="#prizeDetailsModal" href="<c:url value='/contests/${uq.contest.contestId}/prizeDetails.html' />"><c:out value="${uq.contest.prize.title}" /></a></div>
				
				<div class="form-group" >
					<label class="control-label">Question:</label>
					<p class="form-control-static"><c:out value="${uq.question.question}" /></p>
				</div>
				<div class="form-group" >
					<label class="control-label">Winning Answer:</label>
					<p class="form-control-static"><c:out value="${uq.question.correctAnswer}" /></p>
				</div>
				<div class="redemption-instructions help-text">
					<c:choose>
						<c:when test="${uq.contest.prize.addressRequired and empty uq.contestant.address}">
							This prize is awarded via mail but you have not entered an address. <a href="<c:url value='/users/myAccount.html#address-entry' />">Enter an address</a> and your prize will be mailed to you.
						</c:when>
						<c:when test="${uq.contest.prize.addressRequired}">
							This prize is awarded via mail and will be mailed to you. Ensure your <a href="<c:url value='/users/myAccount.html#address-entry' />">mailing address</a> is accurate.
						</c:when>
					</c:choose>
				</div>
			</div>
		</c:forEach>
	</div>
	<%@ include file="../includes/footer.jsp" %>
</body>
</html>