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
	<jsp:include page="/common/header.jsp" />
	<jsp:include page="/common/aside.jsp" />
	
		<div
			class="p-5 mb-3"
			style="position: absolute; left: 400px; top: 80px">
			<!-- <div>${plan.planUserId}</div> -->
			<span class="text-primary" onclick='window.history.back();' style="cursor: pointer">&lt; 뒤로가기</span>
			<h3 class="d-flex justify-content-center mb-3"
				style="overflow: hidden; white-space: nowrap; font-weight: bold">${plan.planName}</h3>
			<h6 class="d-flex justify-content-center mb-3">${plan.planRegisterDate}</h6>	
			
			
			
			<div class="d-flex flex-row">
				<div>
					<div id="plan-map" style="width: 800px; height: 600px"></div>
					
					<div id="plan-distance"></div>
					<div id="plan-shortest-path"><button type="button" class="btn btn-outline-primary" onclick="planMapLoading(1);">최적 경로 찾기</button></div>
					<div id="plan-div-id" style="display:none">${plan.planId}</div>
				</div>
				<div style="width: 20px"></div>
				<div>
					<c:forEach var="trip" varStatus="status" items="${plan.tripList}">
							<h5 style="cursor: pointer" onclick="openOverlay(${status.index})">${status.count}) ${trip.title}</h5>
							<div><i class="bi bi-geo-alt"></i>&nbsp;${trip.addr}</div>
							<!-- <div><img style="width: 300px" src="${trip.firstImage}"/></div> -->
							<div style="height: 20px"></div>
					</c:forEach>
				</div>
			
			</div>

		
		</div>

	
	
	
	
	
	<script src="${root}/assets/js/main.js" type="text/javascript"></script>
	<script src="${root}/assets/js/search.js" type="text/javascript"></script>
</body>
</html>