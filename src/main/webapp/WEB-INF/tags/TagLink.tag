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
	<c:when test="${url.equals('dashboard') }">
		<c:url
			value="${url}?page=${page}&number-results=${numberResults}&search=${search}&order-column=${orderColumn}&order-order=${orderOrder}" />
	</c:when>
	<c:otherwise>
		<c:url value="${url}" />
	</c:otherwise>
</c:choose>
