<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html>
<head>
<%@ include file="../includes/headSection.jsp" %>

<title>Insert title here</title>
</head>
<body>
	<%@ include file="../includes/navbar.jsp" %>
	
	<div id="main-body">
		<div class="form-group">
		    <label class="control-label">Sponsor</label>
			<p class="form-control-static">${contest.sponsor}</p>
		</div>
	
		<div class="form-group">
			<label class="control-label">Question</label>
			<p class="form-control-static">${contest.question}</p>
		</div>
		
		<div class="form-group">
			<label class="control-label">Correct</label>
			<p class="form-control-static">${contest.correctAnswer}</p>
		</div>
		
		<div class="form-group">
			<label class="control-label">Possible:
			<ul class="form-control-static unstyled-list">
				<c:forEach items="${contest.possibleAnswers}" var="ans">
					<li>${ans }</li>
				</c:forEach>
			</ul>
		</div>
		
		<div class="form-group">
			<label class="control-label">Started</label>
			<p class="form-control-static">${contest.startTime}</p>
		</div>
		
		<div class="form-group">
			<label class="control-label">Ended</label>
			<p class="form-control-static">xxx</p>
		</div>
		<div class="form-group">
			<label class="control-label">Winners</label>
			<table class="form-control-static">
				<c:forEach items="${contest.winningAnswers}" var="uq">
					<tr>
						<td><c:out value="${uq.contestant.username}" /></td>
						<td><c:out value="${answerDate}" /></td>
						<td><c:out value="${uq.correctAnswerTicket}" /></td>
					</tr>
				</c:forEach>
			</table>
			
		</div>
		
		<div class="form-group row">
			<form class="col-md-6" action="<c:url value='/contests/${contest.contestId}/start.html' />" method="post">
				<!-- Add CSRF token -->
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<button type="submit" class="btn btn-default">Start Contest</button>
			</form>
			
			<form class="col-md-6" action="<c:url value='/contests/${contest.contestId}/stop.html' />" method="post">
				<!-- Add CSRF token -->
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<button type="submit" class="btn btn-default">Stop Contest</button>
			</form>
		</div>
	</div>
	<%@ include file="../includes/footer.jsp" %>
</body>
</html>