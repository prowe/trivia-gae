<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html >
<html>
<head>
<%@ include file="../includes/headSection.jsp" %>

<title>Create Question</title>
</head>
<body>
	<%@ include file="../includes/navbar.jsp" %>
	
	<div id="main-body">
		<h1>Create a Question</h1>
		
		<form:form modelAttribute="question">
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
			
			<%--
			<fieldset>
				<legend><form:label path="possibleAnswers">Wrong Answers</form:label></legend>
				<form:errors path="possibleAnswers" />
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
			 --%> 
			
			<fieldset>
				<legend><form:label path="possibleAnswers">Answers</form:label></legend>
				<ul class="list-unstyled">
					<c:forEach begin="0" end="3"  var="index">
						<li class="form-group possible-answer-group">
							<form:input cssClass="form-control answer-input" path="possibleAnswers[${index}]" />
							<form:errors path="possibleAnswers[${index}]" />
							<form:radiobutton path="correctAnswer" value="${question.possibleAnswers[index]}" cssClass="correctAnswerRadio"/>
						</li>
					</c:forEach>
				</ul>
			</fieldset>
			
			<div class="form-group  <form:errors path='sourceURL'>has-error</form:errors>">
				<form:label path="sourceURL">Source URL</form:label>
				<form:input cssClass="form-control" path="sourceURL" />
				<form:errors path="sourceURL" />
			</div>
			
			<form:errors path="*" />
			
			<div class="form-group">
				<button class="btn btn-primary" type="submit">Submit</button>
			</div>
		</form:form>
	</div>
	<%@ include file="../includes/footer.jsp" %>
	
	<script type="text/javascript">
		$(function(){
			$('.answer-input').change(function(){
				$(this)
					.closest('.possible-answer-group')
					.find('correctAnswerRadio')
					.val($(this).val());
			});
		});
	</script>
</body>