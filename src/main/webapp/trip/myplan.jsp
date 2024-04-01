<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<c:if test="${sessionScope.member ne null}">
	<c:set var="member" value="${sessionScope.member}" />
</c:if>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Tripor</title>
</head>
<body>
	<c:forEach var="plan" items="${plans}">
		<div>${plan.planId}</div>
		<div><a href="#">${plan.planName}</a></div>
		<div>${plan.planRegisterDate}</div>
	</c:forEach>
</body>
</html>