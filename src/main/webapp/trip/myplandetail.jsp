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
<title>Insert title here</title>
</head>
<body>
	<div>${plan.planUserId}</div>
	<div>${plan.planName}</div>
	<hr/>
	
	<c:forEach var="trip" varStatus="status" items="${plan.tripList}">
		<div>
			<div>카운트: ${status.count}</div>
			<div>여행 타입: ${trip.contentTypeId}</div>
			<div>여행 제목: ${trip.title}</div>
			<div>여행 주소: ${trip.addr}</div>
			<div><img src="${trip.firstImage}"/></div>
		</div>
		<hr/>
	</c:forEach>
	
	<button type="button" onclick='window.history.back();'>이전으로</button>
	
</body>
</html>