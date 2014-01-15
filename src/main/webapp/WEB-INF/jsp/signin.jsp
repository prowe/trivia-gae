<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="includes/headSection.jsp" %>
<title><spring:message code="brand.title" /> - Sign In</title>
</head>
<body>
	<%--@ include file="includes/navbar.jsp" --%>
	
	<div id="main-body" class="row">
		<div class="col-lg-8 sigin-explination">
			<h2>How it works</h2>
			<p>
				We'll ask you trivia questions. If you answer them correctly before they expire, you will be entered into a contest to win a prize.
			</p>
			<p>
				Log in or create an account to get started.
			</p>
		</div>
		
		<!-- Login form -->
	    <div class="col-lg-4 signin-panel">
	    	<div class="panel-heading">Sign in</div>
	        <div class="panel-body">
	            <!-- Error message is shown if login fails. -->
	            <c:if test="${param.error != null}">
				     <div class="alert alert-danger alert-dismissable">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
				        Your login attempt was not successful, try again.<br />
				        Reason: <c:out value="${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}" />
				    </div>
				</c:if>
	            <!-- Specifies action and HTTP method -->
	            <form class="local-signin" action="/" method="POST" role="form">
	                <!-- Add CSRF token -->
	                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<div id="form-group-email" class="form-group ">
					    <label class="control-label" for="user-email">Email:</label>
					    <input id="user-email" name="username" type="text" class="form-control" />
					</div>
					<div id="form-group-password" class="form-group ">
					    <label class="control-label" for="user-password">Password:</label>
					    <input id="user-password" name="password" type="password" class="form-control"/>
					</div>
					<div class="form-group ">
					    <button type="submit" class="btn btn-default">Sign In</button>
						<a href="/users/signup.html">Sign Up</a>
					</div>
	            </form>
	            
	            <div class="row">
					<!-- Twitter sign in Button -->
					<form class="col-sm-6" action="<c:url value='/signin/twitter'/>" method="post">
						<!-- Add CSRF token -->
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		              	<button type="submit" class="btn btn-default">
		              		<img height="14px" width="14px" src="<c:url value='/resources/images/Twitter_logo_blue.png' />" />
		              		Sign in with Twitter
		              	</button>
					</form>
					
					<!-- Facebook sign in Button -->
					<form class="col-sm-6" action="<c:url value='/signin/facebook'/>" method="post">
						<!-- Add CSRF token -->
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		              	<button type="submit" class="btn btn-default">
		              		<img height="14px" width="14px" src="<c:url value='/resources/images/FB-f-Logo__blue_29.png' />" />
		              		Sign in with Facebook
		              	</button>
					</form>
				</div>
	        </div>
       </div>
    </div>
    
    <%@ include file="includes/footer.jsp" %>
</body>
</html>