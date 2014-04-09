<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/includes/headSection.jsp" %>

<title><spring:message code="brand.title" /> - Answered</title>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/includes/navbar.jsp" %>
	<div id="main-body">
		<h2 class="question-text"><c:out value="${userQuestion.question.question}" /></h2>
		<div class="answer-result">
			<c:forEach items="${userQuestion.question.possibleAnswers}" var="ans">
				<div class="answer selected-${userQuestion.choosenAnswer eq ans} correct-${userQuestion.question.correctAnswer eq ans}">
					<button class="answer-btn" disabled="disabled" ><c:out value="${ans}"/></button>
				</div>
			</c:forEach>
		</div>
		<c:if test="${userQuestion.correct }">
			<div class="prize">You have been entered to win a <c:out value="${userQuestion.contest.prize.title}" />!<br />
				(Prizes will be awarded on <spring:eval expression="userQuestion.contest.endTime"  />.)
			</div>
			<%-- <div class="share"><a href="#">Share this question on Twitter for a bonus entry</a></div> --%>
		</c:if>
				
		<div class="submitted-actions">
			<a class="continue-btn" href="/home.html">Continue</a>
		</div>
	</div>
	<%@ include file="/WEB-INF/jsp/includes/footer.jsp" %>
</body>
</html>
<!-- keep -->