<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<spring:message code="dashboard.computerOrCompany"
	var="messageComputerCompany" />
<spring:message code="dashboard.filter" var="messageFilter" />
<spring:message code="app.formatDate" var="messageFormat" />

<html>

<head>
<title><spring:message code="app.title" /></title>
<%@include file="head.html"%>
</head>
<body>
	<%@include file="header.jsp"%>
	<section id="main">

		<!-- Search bar and buttons of the webapp -->
		<div class="container">
			<h1 id="homeTitle">${nbResults}<spring:message
					code="dashboard.found" />
			</h1>

			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" method="GET" class="form-inline"
						action="<t:TagLink url="dashboard" page="1" numberResults="${numberResults}" search="${searchByName}"/>">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="${messageComputerCompany}"
							<c:if test="${searchByName.length()>1}">value=${searchByName}</c:if> />
						<input type="submit" id="searchsubmit" class="btn btn-primary"
							value="${messageFilter}" />
					</form>
				</div>

				<div class="pull-right">
					<a class="btn btn-success" id="addComputer"
						href="<t:TagLink url="add-computer"/>"> <spring:message
							code="dashboard.add" />
					</a> <a class="btn btn-default" id="editComputer"
						href="<t:TagLink url="#"/>" onclick="$.fn.toggleEditMode();">
						<spring:message code="dashboard.edit" />
					</a>
				</div>

			</div>
		</div>

		<form id="deleteForm" action="<t:TagLink url="dashboard"/>"
			method="POST">
			<input type="hidden" name="selection" value="">
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
								href="<t:TagLink url="dashboard" orderColumn="computerName" orderOrder="DESC"/>">
									<spring:message code="computer.computerName" />
							</a><span class="glyphicon glyphicon-triangle-top"></span></th>
						</c:if>
						<c:if
							test="${!orderColumn.equals('computerName') ||  !orderOrder.equals('ASC') }">
							<th><a
								href="<t:TagLink url="dashboard" orderColumn="computerName" orderOrder="ASC"/>"><spring:message
										code="computer.computerName" /> </a> <c:if
									test="${orderColumn.equals('computerName')}">
									<span class="glyphicon glyphicon-triangle-bottom"></span>
								</c:if></th>
						</c:if>

						<!-- Header of the column Introduced date -->
						<c:if
							test="${orderColumn.equals('introduced') && orderOrder.equals('ASC') }">
							<th><a
								href="<t:TagLink url="dashboard" orderColumn="introduced" orderOrder="DESC"/>"><spring:message
										code="computer.introduced" /> </a> <span
								class="glyphicon glyphicon-triangle-top"></span></th>
						</c:if>
						<c:if
							test="${!orderColumn.equals('introduced') ||  !orderOrder.equals('ASC') }">
							<th><a
								href="<t:TagLink url="dashboard" orderColumn="introduced" orderOrder="ASC"/>"><spring:message
										code="computer.introduced" /> </a> <c:if
									test="${orderColumn.equals('introduced')}">
									<span class="glyphicon glyphicon-triangle-bottom"></span>
								</c:if></th>
						</c:if>

						<c:if
							test="${orderColumn.equals('discontinued') && orderOrder.equals('ASC') }">
							<th><a
								href="<t:TagLink url="dashboard" orderColumn="discontinued" orderOrder="DESC"/>"><spring:message
										code="computer.discontinued" /> </a> <span
								class="glyphicon glyphicon-triangle-top"></span></th>
						</c:if>
						<c:if
							test="${!orderColumn.equals('discontinued') ||  !orderOrder.equals('ASC') }">
							<th><a
								href="<t:TagLink url="dashboard" orderColumn="discontinued" orderOrder="ASC"/>"><spring:message
										code="computer.discontinued" /></a> <c:if
									test="${orderColumn.equals('discontinued')}">
									<span class="glyphicon glyphicon-triangle-bottom"></span>
								</c:if></th>
						</c:if>

						<c:if
							test="${orderColumn.equals('companyName') && orderOrder.equals('ASC') }">
							<th><a
								href="<t:TagLink url="dashboard" orderColumn="companyName" orderOrder="DESC"/>"><spring:message
										code="computer.company" /> </a> <span
								class="glyphicon glyphicon-triangle-top"></span></th>
						</c:if>
						<c:if
							test="${!orderColumn.equals('companyName') ||  !orderOrder.equals('ASC') }">
							<th><a
								href="<t:TagLink url="dashboard" orderColumn="companyName" orderOrder="ASC"/>"><spring:message
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
								class="cb" value="${computer.id}"></td>
							<td class="col-xs-3"><a
								href="<t:TagLink url="edit-computer/${computer.getId()}"/>"
								onclick="">${computer.name}</a></td>
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
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/dashboard.js"></script>


</body>
</html>