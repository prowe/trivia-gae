<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html >
<html>
<head>
<%@ include file="../includes/headSection.jsp" %>

<title>Insert title here</title>
</head>
<body>
	<%@ include file="../includes/navbar.jsp" %>
	
	<div id="main-body">
	
	<form:form modelAttribute="contest">
		<!-- Add CSRF token -->
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	                
		Choose Question
		
		<div class="form-group  <form:errors path='duration'>has-error</form:errors>">
			<form:label path="duration">Contest Duration</form:label>
			<form:input cssClass="form-control" path="duration" />
			<form:errors path="duration" />
		</div>
		
		<div class="form-group  <form:errors path='question'>has-error</form:errors>">
		
			<div class="form-input">
				<table class="table">
					<thead>
						<tr>
						
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${questionList}" var="question" varStatus="status">
							<tr>
								<td>
									<form:label path="question" for="question${status.index}">
										<form:radiobutton path="question" id="question${status.index}" value="${question.questionId}" />
										<c:out value="${question.question}" />
									</form:label>
								</td>
								<td>
									<c:forEach items="${question.possibleAnswers}" var="ans">
										<span class="answer"><c:if test="${question.correctAnswer == ans}"><i class="fa fa-check"> </i></c:if><c:out value='${ans}' /></span>
									</c:forEach>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<form:errors path="question" />
			</div>
		</div>
		
		<fieldset>
			<legend>Prize information</legend>
			<div class="form-group  <form:errors path='prize.title'>has-error</form:errors>">
				<form:label path="prize.title">Prize Title</form:label>
				<form:input cssClass="form-control" path="prize.title" />
				<form:errors path="prize.title" />
			</div>
			
			<div class="form-group  <form:errors path='prize.description'>has-error</form:errors>">
				<form:label path="prize.description">Prize Description</form:label>
				<form:textarea id="prizeDescriptionArea" cssClass="form-control" path="prize.description" />
				<form:errors path="prize.description" />
			</div>
			
			<div class="form-group  <form:errors path='prize.redemptionMethod'>has-error</form:errors>">
				<form:label path="prize.redemptionMethod">Method to redeem</form:label>
				<form:select path="prize.redemptionMethod">
					<form:options items="${redemptionMethodList}"/>
				</form:select>
				<form:errors path="prize.redemptionMethod" />
			</div>
			
			<div class="form-group  <form:errors path='prizeQuantity'>has-error</form:errors>">
				<form:label path="prizeQuantity">Quantity to award</form:label>
				<form:input  cssClass="form-control" path="prizeQuantity" />
				<form:errors path="prizeQuantity" />
			</div>
		</fieldset>
		
		<div class="form-group">
			<button class="btn btn-primary" type="submit">Submit</button>
		</div>
	</form:form>
	
	</div>
	
	<%@ include file="../includes/footer.jsp" %>
</body>
</html>