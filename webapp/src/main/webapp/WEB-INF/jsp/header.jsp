<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<header class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<a class="navbar-brand col-xs-7" href="/computerDB/computer"> <spring:message
				code="app.titleBanner" />
		</a>
		<span class="col-xs-5"> 
		
		<a href="?language=en" class="col-xs-2"><img
				src="${pageContext.request.contextPath}/fonts/uk.png" alt="uk flag" style="width: 25px; height: 20px"
				> English</a> 
				<a href="?language=fr" class="col-xs-2"><img
				src="${pageContext.request.contextPath}/fonts/fr.png" alt="fr flag" style="width: 25px; height: 20px">
				Français</a>
				<c:if test="${!loginPage.equals('true')}">
							<a href="<c:url value="/logout" />" >  <img
				src="${pageContext.request.contextPath}/fonts/logout.png" alt="Logout" style="width: 25px; height: 20px"
				></a>
				</c:if>
				
		</span>
		
	</div>
</header>
