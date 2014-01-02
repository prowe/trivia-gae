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
		
		<form action="<c:url value='/contests/${contest.sponsor.username}/${contest.contestId}/start.html' />" method="post">
			<button type="submit" class="btn btn-default">Start Contest</button>
		</form>
	
	</div>
	<%@ include file="../includes/footer.jsp" %>
</body>
</html>