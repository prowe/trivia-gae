<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html>
<head>
<%@ include file="../includes/headSection.jsp" %>

<title><spring:message code="brand.title" /> - Questions</title>
</head>
<body>
	<%@ include file="../includes/navbar.jsp" %>
	
	<div id="main-body">
		<h1>Questions</h1>
		
		<table class="table">
			<thead>
				<tr>
				
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${questionList}" var="question">
					<tr>
						<td><c:out value="${question.question}" /></td>
						<td>
							<c:forEach items="${question.possibleAnswers}" var="ans">
								<span class="answer"><c:if test="${question.correctAnswer == ans}"><i class="fa fa-check"> </i></c:if><c:out value='${ans}' /></span>
							</c:forEach>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<a href="create.html">Create new question</a>
	</div>
	<%@ include file="../includes/footer.jsp" %>
</body>
</html>