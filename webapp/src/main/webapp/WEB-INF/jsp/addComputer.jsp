<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="th"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<spring:message code="add.add" var="messageAdd" />
<spring:message code="computer.computerName" var="messageComputerName" />
<spring:message code="add.introduced" var="messageIntroduced" />
<spring:message code="add.discontinued" var="messageDiscontinued" />

<!DOCTYPE html>
<html>
<head>
<title><spring:message code="app.title" /></title>
<%@include file="head.jsp"%>
</head>
<body onload='document.formAdd.name.focus();'>
	<%@include file="header.jsp"%>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>
						<spring:message code="add.title" />
					</h1>
					<th:errors path="discontinuedAfterIntroduced"
						cssClass="alert alert-danger alert-dismissable" element="div"
						value="Discontinued must be after Introduced." />

					<th:form modelAttribute="computerDto" action="add"
						method="POST" id="formAdd" name="formAdd">
						<fieldset>
							<div class="form-group">
								<label for="name"><spring:message
										code="computer.computerName" /></label> <input type="text"
									class="form-control" id="name"
									placeholder="${messageComputerName}" value="${computer.name}"
									name="name">
							</div>
							<th:errors path="name"
								cssClass="alert alert-danger alert-dismissable" element="div" />

							<div class="form-group">
								<label for="introduced"><spring:message
										code="computer.introduced" /></label> <input type="text"
									class="form-control" id="introduced"
									value="${computer.introduced}"
									placeholder="${messageIntroduced}" name="introduced">
							</div>
							<th:errors path="introduced"
								cssClass="alert alert-danger alert-dismissable" element="div" />

							<div class="form-group">
								<label for="discontinued"><spring:message
										code="computer.discontinued" /></label> <input type="text"
									class="form-control" id="discontinued"
									value="${computer.discontinued}"
									placeholder="${messageDiscontinued}" name="discontinued">
							</div>
							<th:errors path="discontinued"
								cssClass="alert alert-danger alert-dismissable" element="div" />

							<div class="form-group">
								<label for="companyId"><spring:message
										code="computer.company" /></label> <select class="form-control"
									id="companyId" name="companyId">
									<option value="0">--</option>
									<c:forEach items="${companies}" var="company">
										<option value="${company.id}"
											<c:if test="${company.getId()==computer.getCompanyId() }">selected="selected"</c:if>>${company.name}</option>
									</c:forEach>
								</select>
							</div>
							<th:errors path="companyId"
								cssClass="alert alert-danger alert-dismissable" element="div" />

						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="${messageAdd}"
								class="btn btn-primary" id="submit">
							<spring:message code="add.or" />
							<a href="<t:TagLink url="/computer"/>" class="btn btn-default"><spring:message
									code="add.cancel" /></a>
						</div>
					</th:form>
				</div>
			</div>
		</div>
	</section>

	<footer>
		<script type="text/javascript">
			var messageDate = "<spring:message code='error.errorDate'/>";
			var messageFormatDate = new RegExp(
					"<spring:message code='app.regexDate'/>", "");
			var messageDiscontinuedBeforeIntroduced = "<spring:message code='error.discontinuedBeforeIntroduced'/>";
		</script>

		<%@include file="script.jsp"%>
		<script src="${pageContext.request.contextPath}/js/addComputer.js"></script>
	</footer>

</body>
</html>