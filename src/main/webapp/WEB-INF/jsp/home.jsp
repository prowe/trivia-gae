<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="includes/headSection.jsp" %>

<title>Insert title here</title>
</head>
<body>
	<%@ include file="includes/navbar.jsp" %>
	<div id="main-body">
	<h1>Welcome!</h1>
		<c:forEach items="${userQuestionList}" var="uq">
			<div class="row well" id="${uq.contest.contestId}">
				<div class="col-lg-6">
					<c:out value="${uq.contest.question}" />
				</div>
				<div class="col-lg-6">
					<c:choose>
						<c:when test="${not uq.answered}">
							<form class="row" method="post" action="<c:url value='/questions/${uq.contest.contestId}/answer.html' />">
								<!-- Add CSRF token -->
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<c:forEach items="${uq.contest.possibleAnswers}" var="ans">
									<div class="col-lg-3">
										<button class="btn btn-default btn-block" name="answer" type="submit" value="<c:out value='${ans}' />"><c:out value='${ans}' /></button>
									</div>
								</c:forEach>
							</form>
						</c:when>
						<c:when test="${uq.correct}">
							<div>You correctly answered <c:out value="${uq.contest.correctAnswer}" /></div>
						</c:when>
						<c:otherwise>
							<div>Sorry, you answered, "<c:out value="${uq.choosenAnswer}" />", but the correct answer was "<c:out value="${uq.contest.correctAnswer}" />."</div>
						</c:otherwise>
					</c:choose>
					<c:if test="${uq.winner}">
						<div>Congratulations! You have won <c:out value="${uq.contest.prizeDescription}" /></div>
					</c:if>
				</div>
			</div>
		</c:forEach>
	</div>
	<%@ include file="includes/footer.jsp" %>
</body>
</html>