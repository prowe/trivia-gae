<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<div id="main-navbar" class="navbar navbar-default" role="navigation" >
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> 
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="/" ><spring:message code="brand.title" /></a>
		</div>
		<div class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<li><a href="<c:url value='/'/>" ><i class="fa fa-home"></i> Home</a></li>
				<li><a href="<c:url value='/questions/winning.html'/>" ><i class="fa fa-gift"></i> Winnings</a></li>
				<li><a href="<c:url value='/questions/history.html'/>" ><i class="fa fa-clock-o"></i> History</a></li>
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<li ><a href="<c:url value='/contests/list.html'/>">Contests</a></li>
					<li ><a href="<c:url value='/questions/list.html'/>">Questions</a></li>
				</sec:authorize>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="isAuthenticated()">
					<sec:authentication property="principal" var="currentUser" />
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown"><c:out value="${currentUser.displayName}" /> <b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li ><a href="<c:url value='/users/myAccount.html'/>">My Account</a></li>
							<li>
								<a href="#" onclick="javascript:$('#logout-form').submit();">Sign Out</a>
								<form id="logout-form" action="/logout" method="post">
									<!-- Add CSRF token -->
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								</form>
							</li>
						</ul>
					</li>
				</sec:authorize>
				<sec:authorize access="!isAuthenticated()">
					<li><a href="/">Sign In</a></li>
				</sec:authorize>
			</ul>
		</div>
		<!--/.nav-collapse -->
	</div>
</div>