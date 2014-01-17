<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="includes/headSection.jsp" %>

<title><spring:message code="brand.title" /> - Home</title>
</head>
<body>
	<%@ include file="includes/navbar.jsp" %>
	<div id="main-body">
		<h1>Welcome!</h1>
		<c:choose>
			<c:when test="${empty userQuestionList}">
				<p>You don't have any questions.</p>
			</c:when>
			<c:otherwise>
				<p>Answer the questions to be entered to win prizes</p>
			</c:otherwise>
		</c:choose>
		
		
		<c:forEach items="${userQuestionList}" var="uq">
			<div class="question-pane" 
				data-action="<c:url value='/questions/${uq.contest.contestId}/answer.json' />" >
				
				<div class="question-text"><c:out value="${uq.contest.question}" /></div>
				<div class="answer-group">
					<c:forEach items="${uq.contest.possibleAnswers}" var="ans">
						<div class="answer">
							<button class="btn-answer" name="answer" type="submit" value="<c:out value='${ans}' />"><c:out value='${ans}' /></button>
						</div>
					</c:forEach>
				</div>
				<div class="prize-description">
					If you answer this question correctly before <c:out value="${uq.formattedExpirationTime}" />, you will be entered to win a <a href="<c:url value='/contests/${uq.contest.contestId}/prizeDetails.html' />"><c:out value="${uq.contest.prize.title}" /></a>.
				</div>
			</div>
		</c:forEach>
		
		<!--
		<c:forEach items="${userQuestionList}" var="uq">
			<div class="question-row" id="${uq.contest.contestId}">
				<div class="question"><c:out value="${uq.contest.question}" /></div>
				<c:choose>
					<c:when test="${not uq.answered}">
						<div class="prize-description">
							If you answer this question correctly before <c:out value="${uq.formattedExpirationTime}" />, you will be entered to win a <a href="<c:url value='/contests/${uq.contest.contestId}/prizeDetails.html' />"><c:out value="${uq.contest.prize.title}" /></a>.
						</div>
						<form class="answer-form" method="post" action="<c:url value='/questions/${uq.contest.contestId}/answer.html' />">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							<c:forEach items="${uq.contest.possibleAnswers}" var="ans">
								<div class="answer">
									<button class="btn btn-answer" name="answer" type="submit" value="<c:out value='${ans}' />"><c:out value='${ans}' /></button>
								</div>
							</c:forEach>
						</form>
					</c:when>
					<c:when test="${uq.correct}">
						<div class="answer-correctness">You correctly answered <c:out value="${uq.contest.correctAnswer}" /> and have been entered to win a <a href="#"><c:out value="${uq.contest.prize.title}" /></a></div>
					</c:when>
					<c:otherwise>
						<div class="answer-correctness">Sorry, you answered, "<c:out value="${uq.choosenAnswer}" />", but the correct answer was "<c:out value="${uq.contest.correctAnswer}" />."</div>
					</c:otherwise>
				</c:choose>
				<c:if test="${uq.winner}">
					<div>Congratulations! You have won a <c:out value="${uq.contest.prize.title}" /></div>
				</c:if>
			</div>
		</c:forEach> -->
		
	</div>
	<%@ include file="includes/footer.jsp" %>
	
	<script type="text/javascript" src="<c:url value='/resources/js/home.js' />"></script>
</body>
</html>