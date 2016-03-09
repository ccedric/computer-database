<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<header class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<a class="navbar-brand col-xs-7" href="dashboard"> <spring:message
				code="app.titleBanner" />
		</a>
		<span class="col-xs-5"> 
		<a href="?language=en" class="col-xs-2"><img
				src="fonts/uk.png" alt="uk flag" style="width: 25px; height: 20px"
				> English</a> 
				<a href="?language=fr" class="col-xs-2"><img
				src="fonts/fr.png" alt="fr flag" style="width: 25px; height: 20px">
				Français</a>
		</span>
		
	</div>
</header>
