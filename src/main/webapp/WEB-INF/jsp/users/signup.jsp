<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../includes/headSection.jsp" %>

<title><spring:message code="brand.title" /> - Sign Up</title>
</head>
<body>
	<%@ include file="../includes/navbar.jsp" %>
	
	<div id="main-body">
	<h1>Sign Up</h1>
	<p>
		Fill out the fields below to create an account.
	</p>
	<form:form cssClass="signup-form" modelAttribute="user">
		<!-- Add CSRF token -->
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<form:errors path="*" />
						
		<div class="form-group email <form:errors path='username'>has-error</form:errors>">
			<form:label cssClass="control-label" path="username">Email:</form:label>
			<div class="form-input">
				<form:input cssClass="form-control" path="username" />
				<form:errors cssClass="help-block" path="username" />
				<span class="help-block">You may use this to sign in. We never share your email address.</span>
			</div>
   		</div>
   		
   		<div class="form-group displayName <form:errors path='displayName'>has-error</form:errors>">
			<form:label cssClass="control-label" path="displayName">Your Name:</form:label>
			<div class="form-input">
				<form:input cssClass="form-control" path="displayName" />
				<form:errors cssClass="help-block"  path="displayName" />
				<span class="help-block">What you'd like to be called</span>
			</div>
   		</div>
   		
   		<div class="form-group password <form:errors path='password'>has-error</form:errors>">
			<form:label cssClass="control-label" path="password">Password:</form:label>
			<div class="form-input">
				<form:password cssClass="form-control" path="password" />
				<form:errors cssClass="help-block"  path="password" />
				<span class="help-block"></span>
			</div>
   		</div>
   		
   		<div class="form-group passwordConfirmation <form:errors path='passwordConfirmation'>has-error</form:errors>">
			<form:label cssClass="control-label" path="passwordConfirmation">Confirm Password:</form:label>
			<div class="form-input">
				<form:password cssClass="form-control" path="passwordConfirmation" />
				<form:errors cssClass="help-block" path="passwordConfirmation" />
				<span class="help-block"></span>
			</div>
   		</div>
   		
		<div class="form-group submit">
			<button type="submit" class="btn btn-primary">Complete Sign Up</button>
		</div>
	</form:form>
	</div>
	
	<%@ include file="../includes/footer.jsp" %>
</body>

<!-- keep -->