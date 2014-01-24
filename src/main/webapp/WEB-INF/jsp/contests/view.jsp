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
			<p class="form-control-static">${contest.question.question}</p>
		</div>
		
		<div class="form-group">
			<label class="control-label">Correct</label>
			<p class="form-control-static">${contest.question.correctAnswer}</p>
		</div>
		
		<div class="form-group">
			<label class="control-label">Possible:
			<ul class="form-control-static unstyled-list">
				<c:forEach items="${contest.question.possibleAnswers}" var="ans">
					<li>${ans }</li>
				</c:forEach>
			</ul>
		</div>
		
		<div class="form-group">
			<label class="control-label">Started</label>
			<p class="form-control-static">${contest.startTime}</p>
		</div>
		
		<div class="form-group">
			<label class="control-label">Prize</label>
			<p class="form-control-static">${contest.prize.title}</p>
		</div>
		
		<div class="form-group">
			<label class="control-label">Prize Description</label>
			<p class="form-control-static">${contest.prize.description}</p>
		</div>
		
		<div class="form-group">
			<label class="control-label">Prize Quantity</label>
			<p class="form-control-static">${contest.prizeQuantity}</p>
		</div>
		
		<div class="form-group">
			<label class="control-label">Ended</label>
			<p class="form-control-static">${contest.endTime}</p>
		</div>
		<div class="form-group">
			<label class="control-label">Winners</label>
			<table class="form-control-static table">
				<thead>
					<tr>
						<th>Email</th>
						<th>Display Name</th>
						<th>Phone</th>
						<th>Address</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${contest.winningAnswers}" var="uq">
					<tr>
						<td><c:out value="${uq.contestant.email}" /></td>
						<td><c:out value="${uq.contestant.displayName}" /></td>
						<td><c:out value="${uq.contestant.phoneNumber}" /></td>
						<td>
							<div><c:out value="${uq.contestant.address.line1}" /></div>
							<div><c:out value="${uq.contestant.address.line2}" /></div>
							<div><c:out value="${uq.contestant.address.city}" /> <c:out value="${uq.contestant.address.state}" />, <c:out value="${uq.contestant.address.zip}" /></div>
						</td>
					</tr>
				</c:forEach>
				</tbody>
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