<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
	                
		<div class="form-group  <form:errors path='question'>has-error</form:errors>">
			<form:label path="question">Question</form:label>
			<form:textarea cssClass="form-control" path="question" rows="3" />
			<form:errors path="question" />
		</div>
		<div class="form-group  <form:errors path='correctAnswer'>has-error</form:errors>">
			<form:label path="correctAnswer">Correct Answer</form:label>
			<form:input cssClass="form-control" path="correctAnswer" />
			<form:errors path="correctAnswer" />
		</div>
		<fieldset>
			<legend><form:label path="possibleAnswers">Wrong Answers</form:label></legend>
			<div class="form-group  <form:errors path='possibleAnswers[0]'>has-error</form:errors>">
				<form:input cssClass="form-control" path="possibleAnswers[0]" />
				<form:errors path="possibleAnswers[0]" />
			</div>
			<div class="form-group  <form:errors path='possibleAnswers[1]'>has-error</form:errors>">
				<form:input cssClass="form-control" path="possibleAnswers[1]" />
				<form:errors path="possibleAnswers[1]" />
			</div>
			<div class="form-group  <form:errors path='possibleAnswers[2]'>has-error</form:errors>">
				<form:input cssClass="form-control" path="possibleAnswers[2]" />
				<form:errors path="possibleAnswers[2]" />
			</div>
		</fieldset>
		
		<fieldset>
			<legend>Prize information</legend>
		
			<div class="form-group  <form:errors path='prizeDescription'>has-error</form:errors>">
				<form:label path="prizeDescription">Prize Description</form:label>
				<form:input cssClass="form-control" path="prizeDescription" />
				<form:errors path="prizeDescription" />
			</div>
			
			<div class="form-group  <form:errors path='prizeQuantity'>has-error</form:errors>">
				<form:label path="prizeQuantity">Quantity to award</form:label>
				<form:input cssClass="form-control" path="prizeQuantity" />
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