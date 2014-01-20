<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html >
<html>
<head>
<%@ include file="../includes/headSection.jsp" %>

<title><spring:message code="brand.title" /> - History</title>
</head>
<body>
	<%@ include file="../includes/navbar.jsp" %>
	
	<div id="main-body">
		<h1>Question History</h1>
		<table class="table history-table">
			<thead>
				<tr>
					<th>Question</th>
					<th>Answers</th>
					<th>Answered</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${userQuestionList}" var="uq">
					<tr class="${uq.correct ? 'correct' : 'incorrect'}">
						<td><i class="${uq.correct ? 'fa fa-thumbs-up' : 'fa fa-thumbs-down'}"></i>
							<c:out value="${uq.question.question}" />
						</td>
						<td>
							<c:forEach items="${uq.question.possibleAnswers}" var="ans">
								<span class="answer ${(uq.question.correctAnswer == ans) ? 'correct-answer' : ''} ${(uq.choosenAnswer == ans) ? 'choosen-answer' : ''}"><c:out value='${ans}' /></span>
							</c:forEach>
						</td>
						<td>${uq.answerDate}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<%@ include file="../includes/footer.jsp" %>
</body>
</html>