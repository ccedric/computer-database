<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ attribute name="url" required="true"
	description="URL of the request"%>
<%@ attribute name="page" description="Current page"%>
<%@ attribute name="numberResults"
	description="Number of element for each page"%>
<%@ attribute name="search" description="Searched element"%>
<%@ attribute name="orderColumn" description="Column ordered"%>
<%@ attribute name="orderOrder" description="Order ascending or descending"%>


<c:choose>
	<c:when test="${url.equals('computer') && (page == null)}">
			<c:url value="${url}" />
	</c:when>
	<c:when test="${url.equals('computer') }">
		<c:url
			value="${url}?page=${page}&pageSize=${numberResults}&search=${search}&column=${orderColumn}&order=${orderOrder}" />
	</c:when>
	<c:otherwise>
		<c:url value="${url}" />
	</c:otherwise>
</c:choose>
