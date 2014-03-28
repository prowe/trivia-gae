<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url value='/contests/${userQuestion.contest.contestId}/prizeDetails.html' var="prizeDetailsURL" />
<c:url value='/questions/${userQuestion.contest.contestId}/answer.html' var="answerURL" />
<div class="question-panel2" data-answer-url="${answerURL}" >
	<div class="question">
		<i class="fa fa-question-circle"></i><c:out value="${uq.question.question}" />
	</div>
	<div class="answers">
		
		<c:forEach items="${userQuestion.question.possibleAnswers}" var="ans">
			<div class="answer">
				<c:choose>
					<c:when test="${userQuestion.available}">
						<button class="btn-answer" type="submit" value="${ans}"><c:out value="${ans}" /></button>
					</c:when>
					<c:otherwise>
						<button class="btn-answer
							correct-${uq.question.correctAnswer eq ans}
							choosen-${uq.choosenAnswer eq ans}" type="submit" disabled="disabled" ><c:out value="${ans}" /></button>
					</c:otherwise>
				</c:choose>
			</div>
		</c:forEach>
	</div>
	
	<c:choose>
		<c:when test="${userQuestion.available}">
			<div class="prize">
				<i class="fa fa-gift"></i>Answer correctly for ${userQuestion.correctAnswerEntries} entries to win <a data-toggle="modal" data-target="#prizeDetailsModal" href="${prizeDetailsURL}"><c:out value="${userQuestion.contest.prize.title}" /></a>
			</div>
		</c:when>
		<c:when test="${userQuestion.correct}">
			<div class="prize">
				<i class="fa fa-gift"></i>You have 3<!-- TODO: dynamic number --> entries to win <a data-toggle="modal" data-target="#prizeDetailsModal" href="${prizeDetailsURL}"><c:out value="${userQuestion.contest.prize.title}" /></a>
			</div>
		</c:when>
	</c:choose>
	
	<c:choose>
		<c:when test="${userQuestion.available}">
			<div class="remaining-time">
				<i class="fa fa-clock-o"></i>${userQuestion.remainingTime} days remaining to answer
			</div>
		</c:when>
		<c:when test="${userQuestion.contest.inProgress}">
			<div class="remaining-time">
				<i class="fa fa-clock-o"></i>${userQuestion.remainingTime} days remaining in the contest
			</div>
		</c:when>
		<c:otherwise>
			<div class="remaining-time">
				<i class="fa fa-clock-o"></i>Contest ended on ${userQuestion.contest.endTime}
			</div>
		</c:otherwise>
	</c:choose>
	
	<!-- 
	<div class="prize">
		<i class="fa fa-gift"></i>You have 3 entries to win a digital download of "Happy (from 'Dispicable Me 2')" by Pharrell Williams
	</div>
	
	<div class="share">
		<i class="fa fa-share"></i><a href="#">Share this question for a bonus chance to win</a>
	</div>
	<div class="claim">
		<i class="fa fa-gift"></i>Claim your <a href="#">digital download of "Happy (from 'Dispicable Me 2')" by Pharrell Williams</a>
	</div> -->	
</div>