<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<c:if test="${sessionScope.member ne null}">
	<c:set var="member" value="${sessionScope.member}" />
</c:if>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=21fdb12952447fb1ea9bc350b14a70bf&libraries=services,clusterer,drawing"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<title>Tripor</title>

<link rel="preconnect" href="https://fonts.googleapis.com" />
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
<link
	href="https://fonts.googleapis.com/css2?family=Gamja+Flower&family=Gowun+Dodum&display=swap"
	rel="stylesheet" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.0/font/bootstrap-icons.css" />
<link rel="stylesheet" href="${root}/assets/css/style.css" />
</head>
<body data-context-path="${root}">
	<div id="plan-map" style="width: 1000px; height: 700px"></div>
	<div id="plan-div-id">${plan.planId}</div>
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
	<script src="${root}/assets/js/main.js" type="text/javascript"></script>
	<script src="${root}/assets/js/search.js" type="text/javascript"></script>
</body>
</html>