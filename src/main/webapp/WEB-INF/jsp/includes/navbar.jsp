<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>



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
			<a class="navbar-brand" href="/" >Quel Marth</a>
		</div>
		<div class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<li ><a href="#">Home</a></li>
				<li ><a href="<c:url value='/contests/list.html'/>">Contests</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="isAuthenticated()">
					<sec:authentication property="principal" var="currentUser" />
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown"><c:out value="${currentUser.username}" /> <b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li>
								<form action="/logout" method="post">
									<!-- Add CSRF token -->
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
									<button type="submit" class="btn btn-link">Sign Out</button>
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