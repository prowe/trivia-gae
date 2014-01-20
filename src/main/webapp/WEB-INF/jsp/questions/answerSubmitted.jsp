<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html>
<head>
<%@ include file="../includes/headSection.jsp" %>
<!-- jsp defunct -->
<title>Insert title here</title>
</head>
<body>
	<%@ include file="../includes/navbar.jsp" %>
	<div id="main-body">
		<h1><c:out value="${userQuestion.contest.question}" /></h1>
		
		<c:choose>
			<c:when test="${userQuestion.correct}">
				You correctly answered "<c:out value="${userQuestion.choosenAnswer}" />", and have been entered for a chance to win a <c:out value="${userQuestion.contest.prize.title}" />!
			</c:when>
			<c:otherwise>
				Sorry, You the correct answer was <c:out value="${userQuestion.contest.correctAnswer}" />, but you choose <c:out value="${userQuestion.choosenAnswer}" />
			</c:otherwise>
		</c:choose>
		
		<c:if test="${userQuestion.correct}">
			<div></div>
		</c:if>
		
		<div class="form-group">
			<a class="btn btn-primary" href="<c:url value='/' />">Continue</a>
		</div>
	</div>
	<%@ include file="../includes/footer.jsp" %>
</body>
</html>