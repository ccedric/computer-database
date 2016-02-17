<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ attribute name="url" required="true" description="URL of the request"%>
<%@ attribute name="page" required="true" description="Current page"%>
<%@ attribute name="numberResults" required="true" description="Number of element for each page"%>
<%@ attribute name="search" required="true" description="Searched element"%>


<c:url
	value="${url}?page=${page}&number-results=${numberResults}&search=${search}" />