<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="th"%>

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
					<div class="label label-default pull-right">id:
						${computer.getId()}</div>
					<h1>Edit Computer</h1>
					<th:errors path="discontinuedAfterIntroduced"
						cssClass="alert alert-danger alert-dismissable" element="div" value="Discontinued must be after Introduced."/>

					<th:form modelAttribute="computerDto" action="editComputer"
						method="POST" id="formEdit">
						<input type="hidden" name="id" value="${computer.getId()}" />
						<fieldset>
							<div class="form-group">
								<label for="name">Computer name</label> <input type="text"
									class="form-control" id="name" name="name"
									value="${computer.getName()}">
							</div>
							<th:errors path="name"
								cssClass="alert alert-danger alert-dismissable" element="div" />

							<div class="form-group">
								<label for="introduced">Introduced date</label> <input
									type="date" class="form-control" id="introduced"
									value="${computer.getIntroduced()}" name="introduced">
							</div>
							<th:errors path="introduced"
								cssClass="alert alert-danger alert-dismissable" element="div" />


							<div class="form-group">
								<label for="discontinued">Discontinued date</label> <input
									type="date" class="form-control" id="discontinued"
									value="${computer.getDiscontinued()}" name="discontinued">
							</div>
							<th:errors path="discontinued"
								cssClass="alert alert-danger alert-dismissable" element="div" />

							<div class="form-group">
								<label for="companyId">Company</label> <select
									class="form-control" id="companyId" name="companyId">
									<option value="0">--</option>
									<c:forEach items="${companies}" var="company">
										<option value="${company.id}"
											<c:if test="${company.getId()==computer.getCompanyId() }">selected="selected"</c:if>>${company.name}</option>
									</c:forEach>

								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Edit" class="btn btn-primary"
								id="submit"> or <a href="<t:TagLink url="dashboard"/>"
								class="btn btn-default">Cancel</a>
						</div>
					</th:form>
				</div>
			</div>
		</div>
	</section>
	<footer>
		<script src="js/jquery.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/jquery.validate.min.js"></script>
		<script src="js/editComputer.js"></script>
	</footer>

</body>
</html>