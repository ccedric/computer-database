<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="th"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<spring:message code="edit.edit" var="messageEdit" />
<spring:message code="app.formatDate" var="messageFormat" />


<fmt:parseDate value="${computer.introduced}" pattern="yyyy-MM-dd"
	var="parsedDateIntroduced" type="date" />
<fmt:parseDate value="${computer.discontinued}" pattern="yyyy-MM-dd"
	var="parsedDateDiscontinued" type="date" />

<html>
<head>
<title><spring:message code="app.title" /></title>
<%@include file="head.jsp"%>
</head>
<body onload='document.formEdit.name.focus();'>
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
					<th:form modelAttribute="computerDto"
						action="${pageContext.request.contextPath}/computer/edit"
						method="POST" id="formEdit" name="formEdit">

						<input type="hidden" name="id" id="id" value="${computer.getId()}" />
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
										code="computer.introduced" /></label> <input type="text"
									class="form-control" id="introduced"
									value='<fmt:formatDate
									value="${parsedDateIntroduced}" type="date"
									pattern="${messageFormat}" />'
									name="introduced">
							</div>
							<th:errors path="introduced"
								cssClass="alert alert-danger alert-dismissable" element="div" />


							<div class="form-group">
								<label for="discontinued"><spring:message
										code="computer.discontinued" /></label> <input type="text"
									class="form-control" id="discontinued"
									value='<fmt:formatDate
									value="${parsedDateIntroduced}" type="date"
									pattern="${messageFormat}" />'
									name="discontinued">
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
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="${messageEdit }"
								class="btn btn-primary" id="submit">
							<spring:message code="edit.or" />
							<a href="<t:TagLink url="/computer"/>" class="btn btn-default"><spring:message
									code="edit.cancel" /></a>
						</div>
						<input type="hidden" name="${_csrf.parameterName}"
							id="${_csrf.parameterName}" value="${_csrf.token}" />

					</th:form>
				</div>
			</div>
		</div>
	</section>
	<footer>
		<script type="text/javascript">
			var messageDate = "<spring:message code='error.errorDate'/>";
			var messageDiscontinuedBeforeIntroduced = "<spring:message code='error.discontinuedBeforeIntroduced'/>";
			var messageFormatDate = new RegExp(
					"<spring:message code='app.regexDate'/>", "");
		</script>

		<%@include file="script.jsp"%>
		<script src="${pageContext.request.contextPath}/js/editComputer.js"></script>
	</footer>

</body>
</html>