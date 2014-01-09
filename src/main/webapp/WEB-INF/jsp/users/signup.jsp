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

<title>Insert title here</title>
</head>
<body>
	<%@ include file="../includes/navbar.jsp" %>
	
	<div id="main-body">
	<h1>Sign Up</h1>
	
	<form:form cssClass="signup-form" modelAttribute="user">
		<!-- Add CSRF token -->
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						
		<div class="form-group email <form:errors path='email'>has-error</form:errors>">
			<form:label cssClass="control-label" path="email">Email:</form:label>
			<div class="form-input">
				<form:input cssClass="form-control" path="email" />
				<form:errors cssClass="help-block" path="email" />
				<span class="help-block">Your email address. You may use this to sign in.</span>
			</div>
   		</div>
   		
   		<div class="form-group displayName <form:errors path='displayName'>has-error</form:errors>">
			<form:label cssClass="control-label" path="displayName">Display Name:</form:label>
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
   		
   		<!-- 
   		<div class="form-group ">
			<form:label cssClass="control-label" path="phoneNumber">Phone Number:</form:label>
			<div class="form-input">
				<form:input cssClass="form-control" path="phoneNumber" />
				<form:errors cssClass="form-error" path="phoneNumber" />
				<span class="help-block">Optional, unless you want us to alert you of new questions and prizes via text message.</span>
			</div>
   		</div>
   		
   		<fieldset>
   			<legend>Address</legend>
   			<span class="help-block">You can skip this for now, but many prizes are physical and we'll need to know where to mail them to</span>
   			
	   		<div class="form-group ">
				<form:label cssClass="control-label sr-only" path="addressLine1">Line 1:</form:label>
				<div class="form-input">
					<form:input cssClass="form-control" path="addressLine1" />
					<form:errors cssClass="form-error" path="addressLine1" />
				</div>
	   		</div>
	   		<div class="form-group ">
				<form:label cssClass="control-label sr-only" path="addressLine2">Line 2:</form:label>
				<div class="form-input">
					<form:input cssClass="form-control" path="addressLine2" />
					<form:errors cssClass="form-error" path="addressLine2" />
				</div>
	   		</div>
			
			<div class="form-group ">
				<div class="form-input row">
					<div class="col-xs-6">
						<form:input cssClass="form-control" path="city" />
					</div>
					<div class="col-xs-2">
						<form:input cssClass="form-control" path="state" />
					</div>
					<div class="col-xs-4">
						<form:input cssClass="form-control" path="zip" />
					</div>
				</div>
				<div class="row">
					<div class="col-xs-6">
						<form:errors cssClass="form-error" path="city" />
					</div>
					<div class="col-xs-2">
						<form:errors cssClass="form-error" path="state" />
					</div>
					<div class="col-xs-4">
						<form:errors cssClass="form-error" path="zip" />
					</div>
				</div>
	   		</div>
		</fieldset>
		-->
		
		<div class="form-group ">
			<button type="submit" class="btn btn-primary">Complete Sign Up</button>
		</div>
	</form:form>
	</div>
	
	<%@ include file="../includes/footer.jsp" %>
</body>