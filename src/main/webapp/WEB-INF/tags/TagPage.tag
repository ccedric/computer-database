<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>

<%@ attribute name="page" required="true" description="Current page"%>
<%@ attribute name="maxPage" required="true" description="Maximum number of pages"%>
<%@ attribute name="numberResults" required="true" description="Number of element for each page"%>
<%@ attribute name="search" required="true" description="Searched element"%>

<div class="container text-center">
	<form id="numberPage" method="GET" class="col-xs-8" action="dashboard">
		<ul class="pagination">
			<c:if test="${page != 1}">
				<li><a
					href="<t:TagLink url="dashboard" page="${page-1}" numberResults="${numberResults}" search="${search}"/>"
					aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
				</a></li>
			</c:if>

			<c:forEach var="i" begin="1" end="${maxPage}">
				<c:if test="${(i < page+5) && (i> page-4) }">
					<c:choose>
						<c:when test="${i ==page }">
							<li class="active" ><a
								href="<t:TagLink url="dashboard" page="${i}" numberResults="${numberResults}" search="${search}"/>"
								>${i}</a></li>
						</c:when>
						<c:otherwise>
							<li><a
								href="<t:TagLink url="dashboard" page="${i}" numberResults="${numberResults}" search="${search}"/>">${i}</a></li>
						</c:otherwise>
					</c:choose>
				</c:if>
			</c:forEach>
			
			<c:if test="${page != maxPage}">
				<li><a
					href="<t:TagLink url="dashboard" page="${page+1}" numberResults="${numberResults}" search="${search}"/>"
					aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</c:if>
		</ul>
	</form>


	<div class="btn-group btn-group-sm pull-right col-xs-3" role="group">
		<form id="numberResultsPage" method="GET" action="dashboard">
			<button type="submit" class="btn btn-default <c:if test="${Integer.parseInt(numberResults)==10 }">active</c:if> " name="number-results"
				value="10">10</button>
			<button type="submit" class="btn btn-default <c:if test="${Integer.parseInt(numberResults)==50 }">active</c:if>" name="number-results"
				value="50">50</button>
			<button type="submit" class="btn btn-default <c:if test="${Integer.parseInt(numberResults)==100 }">active</c:if>" name="number-results"
				value="100">100</button>
			<input type="hidden" name="search" value="${search}"/>
		</form>
	</div>
</div>
