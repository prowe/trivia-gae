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

<title><spring:message code="brand.title" /> - My Account</title>
</head>
<body>
	<%@ include file="../includes/navbar.jsp" %>
	
	<div id="main-body">
		<h1>My Account</h1>

		<form:form modelAttribute="user">
			<div class="form-group">
				<label class="control-label">Your Email Address</label>
				<div class="">
					<p class="form-control-static"><c:out value="${user.email}" /></p>
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
			
			<div class="form-group emailNotificationEnabled <form:errors path='emailNotificationEnabled'>has-error</form:errors>">
				<div class="checkbox form-input">
					<form:label cssClass="control-label" path="emailNotificationEnabled" for="emailNotificationEnabled">
						<form:checkbox path="emailNotificationEnabled" id="emailNotificationEnabled" />
						Notify by Email
					</form:label>
					<form:errors cssClass="help-block"  path="emailNotificationEnabled" />
					<span class="help-block">Should we use email to notify you when there are new questions available or you have winnings to claim?</span>
				</div>
			</div>
			
			<fieldset class="address-entry" id="address-entry">
				<legend>Address</legend>
				<span class="help-block">The address where prizes that must be sent to you should be mailed.</span>
				
				<div class="form-group line1 <form:errors path='address.line1'>has-error</form:errors>">
					<form:label cssClass="control-label" path="address.line1">Line 1:</form:label>
					<div class="form-input">
						<form:input path="address.line1" cssClass="form-control" />
						<form:errors cssClass="help-block"  path="address.line1" />
					</div>
				</div>
				
				<div class="form-group line2 <form:errors path='address.line2'>has-error</form:errors>">
					<form:label cssClass="control-label" path="address.line2">Line 2:</form:label>
					<div class="form-input">
						<form:input path="address.line2" cssClass="form-control"/>
						<form:errors cssClass="help-block"  path="address.line2" />
					</div>
				</div>
				
				<div class="form-group city <form:errors path='address.city'>has-error</form:errors>">
					<form:label cssClass="control-label" path="address.city">City:</form:label>
					<div class="form-input">
						<form:input path="address.city" cssClass="form-control" />
						<form:errors cssClass="help-block"  path="address.city" />
					</div>
				</div>
				
				<div class="form-group state <form:errors path='address.state'>has-error</form:errors>">
					<form:label cssClass="control-label" path="address.state">State:</form:label>
					<div class="form-input">
						<form:select path="address.state" cssClass="form-control">
							<form:option value=""></form:option>
							<form:options items="${stateCodes}"/>
						</form:select>
						<form:errors cssClass="help-block"  path="address.state" />
					</div>
				</div>
				<div class="form-group zip <form:errors path='address.zip'>has-error</form:errors>">
					<form:label cssClass="control-label" path="address.zip">Zip:</form:label>
					<div class="form-input">
						<form:input path="address.zip" cssClass="form-control" />
						<form:errors cssClass="help-block"  path="address.zip" />
					</div>
				</div>
			</fieldset>
			
			<div class="form-group ">
				<button type="submit" class="btn btn-primary">Save</button>
			</div>			
		</form:form>
	</div>
	
	<%@ include file="../includes/footer.jsp" %>
</body>