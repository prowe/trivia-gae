<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="includes/headSection.jsp" %>

<title><spring:message code="brand.title" /> - Home</title>
</head>
<body>
	<%@ include file="includes/navbar.jsp" %>
	<div id="main-body">
		<form action="/questions/${currentQuestion.question.questionId}/answer.html" method="POST">
			<h2 class="question-text"><c:out value="${currentQuestion.question.question}" /></h2>
			<div class="answer-selection">
				<c:forEach items="${currentQuestion.question.possibleAnswers}" var="ans">
					<div class="answer">
						<button class="answer-btn" type="submit" name="answer" value="${ans}"><c:out value="${ans}"/></button>
					</div>
				</c:forEach>
			</div>
			<div class="prize">
				Answer correctly before <spring:eval expression="currentQuestion.contest.endTime"  /> to be entered to win: <c:out value="${currentQuestion.contest.prize.title}" />
			</div>
			<div class="question-options">
				<button class="skip-btn" type="submit" name="skip" value="skip">Skip this question</button>
			</div>
		</form>
	</div>
	<%@ include file="includes/footer.jsp" %>
	${currentQuestion.question.correctAnswer}
	<script type="text/javascript" src="<c:url value='/resources/js/home.js' />"></script>
</body>
</html>

<!-- keep -->