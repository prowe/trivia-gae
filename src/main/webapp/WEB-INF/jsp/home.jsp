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
			aria-hidden="true">
		</div>
		<!-- /.modal -->
		
		<!-- 
			end points needed:
			
			
			answer the question
			prize details
			share question
			claim the prize
		
		 -->
		
		<div>
			<c:forEach items="${userQuestionList}" var="uq" >
				<c:set scope="request" value="${uq}" var="userQuestion" />
				<%@ include file="questions/questionPanel.jsp" %>
			</c:forEach>
			<script type="text/javascript">
				/* TODO: move me */
				$('.question-panel2 .btn-answer').click(function(event){
					event.preventDefault();
					//TODO: add loading indicator
					var selectedBtn = $(this);
					var questionPane = selectedBtn.closest('.question-panel2');
					$.ajax({
						url: questionPane.data('answer-url'),
						dataType: "html",
						type: "POST",
						data: {
							embedded: true,
							answer: selectedBtn.attr('value')
						},
						success: function(response, textStatus, jqXHR){
							console.log( "Sample of data:", response);
							questionPane.html(response);
						},
						error: function(jqXHR, textStatus, errorThrown){
							console.log("Error: ", errorThrown);
						},
						done: function(jqXHR, textStatus, errorThrown){
							//TODO: remove loading indicator
						}
					});
				});
			</script>
			
				<div class="question-panel2">
					<div class="question">
						<i class="fa fa-question-circle"></i>In what year was pop artist Pharrell Willams born?
					</div>
					<div class="answers">
						<div class="answer">
							<button type="submit">1977</button>
						</div>
						<div class="answer">
							<button type="submit">1979</button>
						</div>
						<div class="answer">
							<button type="submit">1975</button>
						</div>
						<div class="answer">
							<button type="submit">1973</button>
						</div>
					</div>
					<div class="prize">
						<i class="fa fa-gift"></i>Answer correctly for 3 entries to win <a href="#">Happy (from 'Dispicable Me 2')" by Pharrell Williams</a> via digital download
					</div>
					<div class="remaining-time">
						<i class="fa fa-clock-o"></i>4 days remaining to answer
					</div>
					
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
				
				<!-- answered correctly -->
				<div class="question-panel2 correct">
					<div class="question">
						<i class="fa fa-thumbs-up"></i>In what year was pop artist Pharrell Willams born?
					</div>
					<div class="answers">
						<div class="answer">
							<button type="submit">1977</button>
						</div>
						<div class="answer">
							<button type="submit">1979</button>
						</div>
						<div class="answer">
							<button type="submit">1975</button>
						</div>
						<div class="answer correct-answer">
							<button type="submit">1973</button>
						</div>
					</div>
					<div class="prize">
						<i class="fa fa-gift"></i>You have 3 entries to win <a href="#">Happy (from 'Dispicable Me 2')" by Pharrell Williams</a> via digital download
					</div>
					<div class="share">
						<i class="fa fa-share"></i><a href="#">Share this question for a bonus chance to win</a>
					</div>
					<div class="remaining-time">
						<i class="fa fa-clock-o"></i>4 days remaining
					</div>
					
					<!-- 
					<div class="claim">
						<i class="fa fa-gift"></i>Claim your <a href="#">digital download of "Happy (from 'Dispicable Me 2')" by Pharrell Williams</a>
					</div> -->	
				</div>
				
				<!-- winner -->
				<div class="question-panel2 correct winner">
					<div class="question">
						<i class="fa fa-question-circle"></i>In what year was pop artist Pharrell Willams born?
					</div>
					<div class="answers">
						<div class="answer">
							<button type="submit">1977</button>
						</div>
						<div class="answer">
							<button type="submit">1979</button>
						</div>
						<div class="answer">
							<button type="submit">1975</button>
						</div>
						<div class="answer correct-answer">
							<button type="submit">1973</button>
						</div>
					</div>
					<div class="claim">
						<i class="fa fa-gift"></i><a href="#">Claim your prize: Happy (from 'Dispicable Me 2')" by Pharrell Williams</a>
					</div>
				</div>
				
				<!-- looser -->
				<div class="question-panel2 looser">
					<div class="question">
						<i class="fa fa-question-circle"></i>In what year was pop artist Pharrell Willams born?
					</div>
					<div class="answers">
						<div class="answer">
							<button type="submit">1977</button>
						</div>
						<div class="answer">
							<button type="submit">1979</button>
						</div>
						<div class="answer">
							<button type="submit">1975</button>
						</div>
						<div class="answer correct-answer">
							<button type="submit">1973</button>
						</div>
					</div>
					<div class="claim">
						<i class="fa fa-gift"></i>You were not selected to win Happy (from 'Dispicable Me 2')" by Pharrell Williams
						<a href="https://itunes.apple.com/us/album/happy-from-despicable-me-2/id823593445?i=823593456&uo=4" >Download on iTunes</a>
					</div>
				</div>
		</div>
		
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
		 		<div class="panel-collapse collapse ${uq.available or true ? 'in' : ''}" id="${uq.contest.contestId}_details">
		 			<div class="prize-description">
						<c:choose>
							<c:when test="${uq.available}" >
								If you answer this question correctly before <c:out value="${uq.formattedExpirationTime}" />, you will be entered to win a <a data-toggle="modal" data-target="#prizeDetailsModal" href="<c:url value='/contests/${uq.contest.contestId}/prizeDetails.html' />"><c:out value="${uq.contest.prize.title}" /></a>.
							</c:when>
							<c:when test="${uq.winner}">
								Congratulations! You have won a <a data-toggle="modal" data-target="#prizeDetailsModal" href="<c:url value='/contests/${uq.contest.contestId}/prizeDetails.html' />"><c:out value="${uq.contest.prize.title}" /></a>
							</c:when>
							<c:when test="${not uq.contest.inProgress}">
								You did not win this contest. Better luck next time. You can still purchase <a href="https://itunes.apple.com/us/album/happy-from-despicable-me-2/id783656910">${uq.contest.prize.title}</a>
							</c:when>
							<c:when test="${uq.correct}">
								You have been entered to win a <a data-toggle="modal" data-target="#prizeDetailsModal" href="<c:url value='/contests/${uq.contest.contestId}/prizeDetails.html' />"><c:out value="${uq.contest.prize.title}" /></a> for correctly answering this question.
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
		 			<c:if test="${uq.answered and not empty uq.question.sourceURL}">
		 				<div class="source">Source: <a href="${uq.question.sourceURL}"><c:out value="${uq.question.sourceURL}" /></a></div>
		 			</c:if>
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