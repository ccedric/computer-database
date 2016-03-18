<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<spring:message code="login.submit" var="messageSubmit" />

<html>
<head>
<title><spring:message code="app.title" /></title>
<%@include file="head.jsp"%>
</head>

<body onload='document.loginForm.username.focus();'>
	<c:set var="loginPage" value="true" scope="request" />
	<%@include file="header.jsp"%>
	<div class="container">
		<div class="row">
			<div class="col-xs-8 col-xs-offset-2 box">

				<h1>
					<spring:message code="login.title" />
				</h1>

				<div id="login-box">

					<h3>
						<spring:message code="login.message" />
					</h3>

					<c:if test="${error}">
						<div class="error">
							<spring:message code="login.invalid" />
						</div>
					</c:if>
					<c:if test="${msg}">
						<div class="msg">
							<spring:message code="login.logout" />
						</div>
					</c:if>


					<form action="<c:url value='/j_spring_security_check' />"
						method='POST' id="loginForm" name="loginForm">
						<fieldset>
							<div class="form-group">
								<label for="name"><spring:message code="login.user" /></label>
								<input type='text' name='username'>
							</div>

							<div class="form-group">
								<label for="name"><spring:message code="login.password" /></label>
								<input type='password' name='password'>
							</div>
						</fieldset>
						<div>
							<input name="submit" type="submit" value="${messageSubmit }" />
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />

						</div>
					</form>

				</div>
			</div>
		</div>
	</div>

</body>
<footer>
	<%@include file="script.jsp"%>
</footer>

</html>