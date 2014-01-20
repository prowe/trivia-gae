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

		<div class="modal fade" id="prizeDetailsModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
		</div>
		<!-- /.modal -->

		<c:forEach items="${userQuestionList}" var="uq">
			<div class="question-pane" id="${uq.contest.contestId}"
				data-action="<c:url value='/questions/${uq.contest.contestId}/answer.json' />" >
				
				<div class="question-text"><c:out value="${uq.question.question}" /></div>
				<div class="answer-group">
					<c:forEach items="${uq.question.possibleAnswers}" var="ans">
						<div class="answer">
							<button class="btn-answer" name="answer" type="submit" value="<c:out value='${ans}' />"><c:out value='${ans}' /></button>
						</div>
					</c:forEach>
				</div>
				<div class="prize-description">
					If you answer this question correctly before <c:out value="${uq.formattedExpirationTime}" />, you will be entered to win a <a data-toggle="modal" data-target="#prizeDetailsModal" href="<c:url value='/contests/${uq.contest.contestId}/prizeDetails.html' />"><c:out value="${uq.contest.prize.title}" /></a>.
				</div>
			</div>
		</c:forEach>
		
	</div>
	<%@ include file="includes/footer.jsp" %>
	
	<script type="text/javascript" src="<c:url value='/resources/js/home.js' />"></script>
</body>
</html>