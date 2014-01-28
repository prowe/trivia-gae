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
				<p>You don't have any questions. Check back later for more questions to answer and prizes to win.</p>
			</c:when>
			<c:otherwise>
				<p>Answer the questions to be entered to win prizes</p>
			</c:otherwise>
		</c:choose>

		<div class="modal fade" id="prizeDetailsModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
		</div>
		<!-- /.modal -->
		
		
		<!-- 
			each pane:
			Question
			Time remaining or result
			Answers
			Prize
			Share link
		
		 -->
		<c:forEach items="${userQuestionList}" var="uq">
		 	<div class="panel panel-default question-panel" 
		 		id="${uq.contest.contestId}"
		 		data-action="<c:url value='/questions/${uq.contest.contestId}/answer.json' />" >
		 		<div class="panel-heading" data-target="#${uq.contest.contestId}_details" data-toggle="collapse">
			 		<div class="panel-title clearfix">
			 			<div class="question-text"><a><c:out value="${uq.question.question}" /></a></div>
			 			<c:choose>
			 				<c:when test="${uq.available}"><div class="remaining" >${uq.remainingTime} remaining</div></c:when>
			 				<c:otherwise>
			 					<div class="result-indicator"><i class="${uq.correct ? 'fa fa-thumbs-up' : 'fa fa-thumbs-down'}"></i></div>
			 				</c:otherwise>
			 			</c:choose>
			 		</div>
		 		</div>
		 		<div class="panel-collapse collapse ${uq.available ? 'in' : ''}" id="${uq.contest.contestId}_details">
		 			<div class="prize-description">
						<c:choose>
							<c:when test="${uq.available}" >
								If you answer this question correctly before <c:out value="${uq.formattedExpirationTime}" />, you will be entered to win a <a data-toggle="modal" data-target="#prizeDetailsModal" href="<c:url value='/contests/${uq.contest.contestId}/prizeDetails.html' />"><c:out value="${uq.contest.prize.title}" /></a>.
							</c:when>
							<c:when test="${uq.correct}">
								You have been entered to win a <a data-toggle="modal" data-target="#prizeDetailsModal" href="<c:url value='/contests/${uq.contest.contestId}/prizeDetails.html' />"><c:out value="${uq.contest.prize.title}" /></a> for correctly answering this question.
								<a href="#">Share this question for a bonus chance to win.</a>
							</c:when>
							<c:otherwise>
								 You have already answered this question.
							</c:otherwise>
						</c:choose>
						<%--<form action="<c:url value='/questions/${uq.contest.contestId}/shareViaTwitter.html' />" method="post">
							 	<button type="submit" >Share via Twitter</button>
							 </form> --%>
					</div>
		 			<form class="answer-group" action="<c:url value='/questions/${uq.contest.contestId}/answer.html' />" method="post">
				 		<c:forEach items="${uq.question.possibleAnswers}" var="ans">
				 			<c:choose>
				 				<c:when test="${uq.available}">
						 			<button class="btn-answer" name="answer" type="submit" value="<c:out value='${ans}' />"><c:out value='${ans}' /></button>
				 				</c:when>
				 				<c:when test="${uq.question.correctAnswer eq ans}">
				 					<button type="button" class="btn-answer correct" disabled="disabled"><c:out value='${ans}' /></button>
				 				</c:when>
				 				<c:when test="${uq.choosenAnswer eq ans}">
				 					<button type="button" class="btn-answer incorrect" disabled="disabled"><c:out value='${ans}' /></button>
				 				</c:when>
				 				<c:otherwise>
				 					<button type="button" class="btn-answer" disabled="disabled"><c:out value='${ans}' /></button>
				 				</c:otherwise>
				 			</c:choose>
						</c:forEach>
		 			</form>
		 		</div>
		 	</div>
		</c:forEach>
		
		<%/*
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
		*/%>
	</div>
	<%@ include file="includes/footer.jsp" %>
	
	<script type="text/javascript" src="<c:url value='/resources/js/home.js' />"></script>
</body>
</html>