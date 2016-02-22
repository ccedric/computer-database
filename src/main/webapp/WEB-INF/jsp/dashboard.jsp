<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>

<html>

<head>
<title>Computer Database</title>
<%@include file="head.html"%>
</head>
<body>
	<%@include file="header.html"%>
	
	<!-- Alert if the user just created a new computer -->
	<c:if test="${null!=newComputer}">
		<div class="alert alert-success alert-dismissable">
			<button type="button" class="close" data-dismiss="alert"
				aria-hidden="true">&times;</button>
			A new computer has been created. Name of the computer:
			${newComputer.getName()}, introduced date:
			${newComputer.getIntroduced()}, discontinued date:
			${newComputer.getDiscontinued() }
		</div>
	</c:if>
	
	<!-- Alert if the user just updated a new computer -->
	<c:if test="${null!=updateComputer}">
		<div class="alert alert-success alert-dismissable">
			<button type="button" class="close" data-dismiss="alert"
				aria-hidden="true">&times;</button>
			A computer has been updated. Name of the computer:
			${updateComputer.getName()}, introduced date:
			${updateComputer.getIntroduced()}, discontinued date:
			${updateComputer.getDiscontinued() }
		</div>
	</c:if>
	
	<!-- Alert if the user just deleted some computers -->
	<c:if test="${null!=computerDelete}">
		<div class="alert alert-success alert-dismissable">
			<button type="button" class="close" data-dismiss="alert"
				aria-hidden="true">&times;</button>
			${computerDelete } Computer(s) have been deleted successfully
		</div>
	</c:if>
		
	<section id="main">
	
		<!-- Search bar and buttons of the webapp -->
		<div class="container">
			<h1 id="homeTitle">${nbResults} Computer(s) found</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" method="GET" class="form-inline"
						action="<t:TagLink url="dashboard" page="1" numberResults="${numberResults}" search="${searchByName}"/>">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Computer or Company"
							<c:if test="${searchByName.length()>1}">value=${searchByName}</c:if> />
						<input type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer"
						href="<t:TagLink url="add-computer"/>">Add Computer</a> <a
						class="btn btn-default" id="editComputer"
						href="<t:TagLink url="#"/>" onclick="$.fn.toggleEditMode();">Edit</a>
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
								href="<t:TagLink url="dashboard" orderColumn="computerName" orderOrder="DESC"/>">Computer
									name 
							</a><span class="glyphicon glyphicon-triangle-top"></span></th>
						</c:if>
						<c:if
							test="${!orderColumn.equals('computerName') ||  !orderOrder.equals('ASC') }">
							<th><a
								href="<t:TagLink url="dashboard" orderColumn="computerName" orderOrder="ASC"/>">Computer
									name 
							</a><c:if test="${orderColumn.equals('computerName')}">
										<span class="glyphicon glyphicon-triangle-bottom"></span>
									</c:if></th>
						</c:if>

						<!-- Header of the column Introduced date -->
						<c:if
							test="${orderColumn.equals('introduced') && orderOrder.equals('ASC') }">
							<th><a
								href="<t:TagLink url="dashboard" orderColumn="introduced" orderOrder="DESC"/>">Introduced
									date  </a> <span class="glyphicon glyphicon-triangle-top"></span> </th>
						</c:if>
						<c:if
							test="${!orderColumn.equals('introduced') ||  !orderOrder.equals('ASC') }">
							<th><a
								href="<t:TagLink url="dashboard" orderColumn="introduced" orderOrder="ASC"/>">Introduced
									date  </a> <c:if test="${orderColumn.equals('introduced')}">
										<span class="glyphicon glyphicon-triangle-bottom"></span>
									</c:if> </th>
						</c:if>

						<c:if
							test="${orderColumn.equals('discontinued') && orderOrder.equals('ASC') }">
							<th><a
								href="<t:TagLink url="dashboard" orderColumn="discontinued" orderOrder="DESC"/>">Discontinued
									date  </a> <span class="glyphicon glyphicon-triangle-top"></span> </th>
						</c:if>
						<c:if
							test="${!orderColumn.equals('discontinued') ||  !orderOrder.equals('ASC') }">
							<th><a
								href="<t:TagLink url="dashboard" orderColumn="discontinued" orderOrder="ASC"/>">Discontinued
									date</a> <c:if test="${orderColumn.equals('discontinued')}">
										<span class="glyphicon glyphicon-triangle-bottom"></span>
									</c:if> </th>
						</c:if>

						<c:if
							test="${orderColumn.equals('companyName') && orderOrder.equals('ASC') }">
							<th><a
								href="<t:TagLink url="dashboard" orderColumn="companyName" orderOrder="DESC"/>">Company </a> <span class="glyphicon glyphicon-triangle-top"></span> </th>
						</c:if>
						<c:if
							test="${!orderColumn.equals('companyName') ||  !orderOrder.equals('ASC') }">
							<th><a
								href="<t:TagLink url="dashboard" orderColumn="companyName" orderOrder="ASC"/>">Company</a> <c:if test="${orderColumn.equals('companyName')}">
										<span class="glyphicon glyphicon-triangle-bottom"></span>
									</c:if> </th>
						</c:if>
					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${computers}" var="computer">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${computer.id}"></td>
							<td class="col-xs-3"><a
								href="<t:TagLink url="edit-computer?id=${computer.getId()}"/>"
								onclick="">${computer.name}</a></td>
							<td class="col-xs-3">${computer.introduced}</td>
							<td class="col-xs-3">${computer.discontinued}</td>
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

	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/dashboard.js"></script>


</body>
</html>