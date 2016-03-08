<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<%@include file="head.html"%>
</head>
<body>
	<%@include file="header.jsp"%>


	<section id="main">
		<div class="container">
			<div class="alert alert-danger">
				 <spring:message code="404.message"/><br />
				<!-- stacktrace -->
			</div>
		</div>
	</section>

	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/dashboard.js"></script>

</body>
</html>