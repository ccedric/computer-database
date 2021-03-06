<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<spring:message code="dashboard.computerOrCompany"
	var="messageComputerCompany" />
<spring:message code="dashboard.filter" var="messageFilter" />
<spring:message code="app.formatDate" var="messageFormat" />

<html>

<head>
<title><spring:message code="app.title" /></title>
<%@include file="head.jsp"%>
</head>
<body onload='document.searchForm.search.focus();'>
	<%@include file="header.jsp"%>
	<section id="main">

		<!-- Search bar and buttons of the webapp -->
		<div class="container">
			<h1 id="homeTitle">${nbResults}
				<spring:message code="dashboard.found" />
			</h1>

			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" name="searchForm" method="GET" class="form-inline"
						action="<t:TagLink url="/computer" page="1" numberResults="${numberResults}" search="${searchByName}"/>">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="${messageComputerCompany}"
							<c:if test="${searchByName.length()>1}">value=${searchByName}</c:if> />
						<input type="submit" id="searchsubmit" class="btn btn-primary"
							value="${messageFilter}" />
					</form>
				</div>

				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<div class="pull-right">
						<a class="btn btn-success" id="addComputer"
							href="<t:TagLink url="computer/add"/>"> <spring:message
								code="dashboard.add" />
						</a> <a class="btn btn-default" id="editComputer"
							href="<t:TagLink url="#"/>" onclick="$.fn.toggleEditMode();">
							<spring:message code="dashboard.edit" />
						</a>
					</div>
				</sec:authorize>

			</div>
		</div>

		<form id="deleteForm" action="<t:TagLink url="/computer"/>"
			method="POST">
			<input type="hidden" name="selection" value=""> <input
				type="hidden" name="${_csrf.parameterName}" id="${_csrf.parameterName}" value="${_csrf.token}" />

		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a
								href="<t:TagLink url="#"/>" id="deleteSelected"
								onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>

						<!-- Header of the column Computer name -->
						<c:if
							test="${orderColumn.equals('computerName') && orderOrder.equals('ASC') }">
							<th><a
								href="<t:TagLink url="computer" orderColumn="computerName" orderOrder="DESC"/>">
									<spring:message code="computer.computerName" />
							</a><span class="glyphicon glyphicon-triangle-top"></span></th>
						</c:if>
						<c:if
							test="${!orderColumn.equals('computerName') ||  !orderOrder.equals('ASC') }">
							<th><a
								href="<t:TagLink url="computer" orderColumn="computerName" orderOrder="ASC"/>"><spring:message
										code="computer.computerName" /> </a> <c:if
									test="${orderColumn.equals('computerName')}">
									<span class="glyphicon glyphicon-triangle-bottom"></span>
								</c:if></th>
						</c:if>

						<!-- Header of the column Introduced date -->
						<c:if
							test="${orderColumn.equals('introduced') && orderOrder.equals('ASC') }">
							<th><a
								href="<t:TagLink url="computer" orderColumn="introduced" orderOrder="DESC"/>"><spring:message
										code="computer.introduced" /> </a> <span
								class="glyphicon glyphicon-triangle-top"></span></th>
						</c:if>
						<c:if
							test="${!orderColumn.equals('introduced') ||  !orderOrder.equals('ASC') }">
							<th><a
								href="<t:TagLink url="computer" orderColumn="introduced" orderOrder="ASC"/>"><spring:message
										code="computer.introduced" /> </a> <c:if
									test="${orderColumn.equals('introduced')}">
									<span class="glyphicon glyphicon-triangle-bottom"></span>
								</c:if></th>
						</c:if>

						<c:if
							test="${orderColumn.equals('discontinued') && orderOrder.equals('ASC') }">
							<th><a
								href="<t:TagLink url="computer" orderColumn="discontinued" orderOrder="DESC"/>"><spring:message
										code="computer.discontinued" /> </a> <span
								class="glyphicon glyphicon-triangle-top"></span></th>
						</c:if>
						<c:if
							test="${!orderColumn.equals('discontinued') ||  !orderOrder.equals('ASC') }">
							<th><a
								href="<t:TagLink url="computer" orderColumn="discontinued" orderOrder="ASC"/>"><spring:message
										code="computer.discontinued" /></a> <c:if
									test="${orderColumn.equals('discontinued')}">
									<span class="glyphicon glyphicon-triangle-bottom"></span>
								</c:if></th>
						</c:if>

						<c:if
							test="${orderColumn.equals('companyName') && orderOrder.equals('ASC') }">
							<th><a
								href="<t:TagLink url="computer" orderColumn="companyName" orderOrder="DESC"/>"><spring:message
										code="computer.company" /> </a> <span
								class="glyphicon glyphicon-triangle-top"></span></th>
						</c:if>
						<c:if
							test="${!orderColumn.equals('companyName') ||  !orderOrder.equals('ASC') }">
							<th><a
								href="<t:TagLink url="computer" orderColumn="companyName" orderOrder="ASC"/>"><spring:message
										code="computer.company" /></a> <c:if
									test="${orderColumn.equals('companyName')}">
									<span class="glyphicon glyphicon-triangle-bottom"></span>
								</c:if></th>
						</c:if>
					</tr>
				</thead>

				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${computers}" var="computer">
						<fmt:parseDate value="${computer.introduced}"
							pattern="${messageFormat}" var="parsedDateIntroduced" type="date" />
						<fmt:parseDate value="${computer.discontinued}"
							pattern="${messageFormat}" var="parsedDateDiscontinued"
							type="date" />

						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" id="${computer.name}_id" value="${computer.id}"></td>
							<sec:authorize access="hasRole('ROLE_ADMIN')">

								<td class="col-xs-3"><a
									href="computer/edit/${computer.getId()}"
									onclick="" id="${computer.name}_name">${computer.name}</a></td>
							</sec:authorize>

							<sec:authorize access="hasRole('ROLE_USER')">
								<td class="col-xs-3">${computer.name}</td>
							</sec:authorize>

							<td class="col-xs-3"><fmt:formatDate
									value="${parsedDateIntroduced}" type="date"
									pattern="${messageFormat}" /></td>
							<td class="col-xs-3"><fmt:formatDate
									value="${parsedDateDiscontinued}" type="date"
									pattern="${messageFormat}" /></td>
							<td class="col-xs-3">${computer.companyName}</td>

						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<t:TagPage maxPage="${maxPage}" numberResults="${numberResults}"
			search="${searchByName}" page="${page}" />
	</footer>

	<script type="text/javascript">
		var messageView = "<spring:message code='dashboard.view'/>";
		var messageEdit = "<spring:message code='dashboard.edit'/>";
		var messageDelete = "<spring:message code='dashboard.delete'/>";
	</script>
	<%@include file="script.jsp"%>
	<script src="${pageContext.request.contextPath}/js/dashboard.js"></script>


</body>
</html>