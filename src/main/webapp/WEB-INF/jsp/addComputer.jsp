<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>


<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<%@include file="head.html"%>
</head>
<body>
	<%@include file="header.html"%>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>Add Computer</h1>
					<c:if test="${null!=errors}">
						<div class="alert alert-danger alert-dismissable">
							<button type="button" class="close" data-dismiss="alert"
								aria-hidden="true">&times;</button>
							${errors}
						</div>
					</c:if>

					<form action="<t:TagLink url="add-computer"/>" method="POST" id="formAdd">
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <input
									type="text" class="form-control" id="computerName"
									placeholder="Computer name" name="computerName">
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label> <input
									type="text" class="form-control" id="introduced"
									placeholder="Introduced date: yyyy-MM-dd"
									name="introduced">
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> <input
									type="text" class="form-control" id="discontinued"
									placeholder="Discontinued date: yyyy-MM-dd"
									name="discontinued">
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select
									class="form-control" id="companyId" name="companyId">
									<option value="0">--</option>
									<c:forEach items="${companies}" var="company">
										<option value="${company.id}">${company.name}</option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Add" class="btn btn-primary"
								id="submit"> or <a href="<t:TagLink url="dashboard"/>"
								class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>

	<footer>
		<script src="js/jquery.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/jquery.validate.min.js"></script>
		<script src="js/addComputer.js"></script>
	</footer>

</body>
</html>