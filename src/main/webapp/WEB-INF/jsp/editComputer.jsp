<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="th"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<spring:message code="edit.edit" var="messageEdit"/>

<!DOCTYPE html>
<html>
<head>
<title><spring:message code="app.title" /></title>
<%@include file="head.html"%>
</head>
<body>
	<%@include file="header.jsp"%>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">id:
						${computer.getId()}</div>
					<h1>
						<spring:message code="edit.title" />
					</h1>
					<th:errors path="discontinuedAfterIntroduced"
						cssClass="alert alert-danger alert-dismissable" element="div"
						value="Discontinued must be after Introduced." />

					<th:form modelAttribute="computerDto" action="editComputer"
						method="POST" id="formEdit">
						<input type="hidden" name="id" value="${computer.getId()}" />
						<fieldset>
							<div class="form-group">
								<label for="name"><spring:message
										code="computer.computerName" /></label> <input type="text"
									class="form-control" id="name" name="name"
									value="${computer.getName()}">
							</div>
							<th:errors path="name"
								cssClass="alert alert-danger alert-dismissable" element="div" />

							<div class="form-group">
								<label for="introduced"><spring:message
										code="computer.introduced" /></label> <input type="date"
									class="form-control" id="introduced"
									value="${computer.getIntroduced()}" name="introduced">
							</div>
							<th:errors path="introduced"
								cssClass="alert alert-danger alert-dismissable" element="div" />


							<div class="form-group">
								<label for="discontinued"><spring:message
										code="computer.discontinued" /></label> <input type="date"
									class="form-control" id="discontinued"
									value="${computer.getDiscontinued()}" name="discontinued">
							</div>
							<th:errors path="discontinued"
								cssClass="alert alert-danger alert-dismissable" element="div" />

							<div class="form-group">
								<label for="companyId"><spring:message code="computer.company" /></label> <select
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
							<input type="submit" value="${messageEdit }" class="btn btn-primary"
								id="submit"> <spring:message code="edit.or" /> <a href="<t:TagLink url="dashboard"/>"
								class="btn btn-default"><spring:message code="edit.cancel" /></a>
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