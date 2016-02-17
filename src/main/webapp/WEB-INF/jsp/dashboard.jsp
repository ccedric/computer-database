<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<%@ page import="java.util.List"%>

<html>

<head>
<title>Computer Database</title>
<%@include file="head.html"%>
</head>
<body>
	<%@include file="header.html"%>
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
	<section id="main">
		<div class="container">
			<h1 id="homeTitle">${nbResults} Computer(s) found</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" method="GET" class="form-inline"
						action="<t:TagLink url="dashboard" page="1" numberResults="${numberResults}" search="${searchByName}"/>">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" <c:if test="${searchByName.length()>1}">value=${searchByName}</c:if> /> <input
							type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer">Add
						Computer</a> <a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="#" method="DELETE">
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
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th>Computer name</th>
						<th>Introduced date</th>
						<!-- Table header for Discontinued Date -->
						<th>Discontinued date</th>
						<!-- Table header for Company -->
						<th>Company</th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${computers}" var="computer">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${computer.id}"></td>
							<td><a href="editCompute" onclick="">${computer.name}</a></td>
							<td>${computer.introduced}</td>
							<td>${computer.discontinued}</td>
							<td>${computer.companyName}</td>

						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<t:TagPage maxPage="${maxPage}" numberResults="${numberResults}"
			search="${searchByName}" page="${page}"></t:TagPage>
	</footer>

	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/dashboard.js"></script>


</body>
</html>