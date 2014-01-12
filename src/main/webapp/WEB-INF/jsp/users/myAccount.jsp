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
			
			<fieldset>
				<legend>Selected Notification Methods:</legend>
				<span class="help-block">How we will notify you of pending questions or winnings.</span>
				<c:forEach  items="${notificationMethods}" var="method" varStatus="status">
					<div class="checkbox">
						<label for="notificationMethods${status.index}">
							<form:checkbox path="selectedNotificationMethods" value="${method}" id="notificationMethods${status.index}"/>
							<c:out value="${method}" />
						</label>
					</div>
				</c:forEach>
				<form:errors cssClass="help-block"  path="selectedNotificationMethods" />
			</fieldset>

			<div class="form-group ">
				<button type="submit" class="btn btn-primary">Save</button>
			</div>			
		</form:form>
	</div>
	
	<%@ include file="../includes/footer.jsp" %>
</body>