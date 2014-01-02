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
						
		<div class="form-group">
			<form:label cssClass="control-label" path="username">Username:</form:label>
			<div class="form-input">
				<form:input cssClass="form-control" path="username" />
				<form:errors cssClass="form-error" path="username" />
				<span class="help-block">Your unique username within the system. You may use this to sign in.</span>
			</div>
   		</div>
   		
   		<div class="form-group ">
			<form:label cssClass="control-label" path="displayName">Display Name:</form:label>
			<div class="form-input">
				<form:input cssClass="form-control" path="displayName" />
				<form:errors cssClass="form-error" path="displayName" />
				<span class="help-block">What you'd like to be called</span>
			</div>
   		</div>
   		
   		<div class="form-group ">
			<form:label cssClass="control-label" path="email">Email:</form:label>
			<div class="form-input">
				<form:input cssClass="form-control" path="email" />
				<form:errors cssClass="form-error" path="email" />
				<span class="help-block">Your email address</span>
			</div>
   		</div>
   		
   		<div class="form-group ">
			<form:label cssClass="control-label" path="phoneNumber">Phone Number:</form:label>
			<div class="form-input">
				<form:input cssClass="form-control" path="phoneNumber" />
				<form:errors cssClass="form-error" path="phoneNumber" />
				<span class="help-block">A phone number we can use to reach you if we need to.</span>
			</div>
   		</div>
   		
   		<fieldset>
   			<legend>Address</legend>
   			<span class="help-block">We'll use this to mail you your prize</span>
   			
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
		
		<div class="form-group ">
			<button type="submit" class="btn btn-primary">Sign Up</button>
		</div>
	</form:form>
	</div>
	
	<%@ include file="../includes/footer.jsp" %>
</body>