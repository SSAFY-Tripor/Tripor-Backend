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
	<div class="fixed-top">
	<jsp:include page="/common/header.jsp" />
	<jsp:include page="/common/aside.jsp" />
	

	<form id="planListForm" method="POST" action="${root}/trip">
		<input type="hidden" name="action" value="planAdd"/>
		<input type="hidden" id="planIdList" name="planIdList" value=""/>
		<div class="container" style="z-index: 1; padding-left: 0">
			<div class="show_Home" id="home_div" style="position: relative"> 
				<div id="search-map" class="search-map position-relative"
					style="width: 3000px; height: 1000px">
					
					<div id="planList"
						class="plan-list bg-light p-3"
						style="position: absolute; left: 380px; width: 15%; height: 100%; overflow-y: auto; background-color: rgba(255, 255, 255, 0.7) !important; z-index: 1000; display: block;">
						
						<span class="mb-3 ms-2" id="planNameGroup">
						     <i class="bi bi-pen-fill me-1"></i>		
							<input 
								value="${member.userName}의 여행 계획"
								id="planName" />
									
						</span>
					
						<div class="d-flex flex-row" id="makePlace">
								<input class="form-control mr-sm-2 m-2 w-75 shadow-sm"
									type="search" placeholder="여행지를 검색하세요!" aria-label="Search"
									id="searchInput" />
								<button class="btn text-white btn-primary m-2" type="button"
									style="overflow: hiddlen; white-space: nowrap" id="searchButton">
									검색</button>
							</div>
							<ul id="planItems" class="list-unstyled m-2"></ul>
						<button type="button" id="savePlanButton"
							class="btn btn-outline-primary mt-auto mb-3 ms-2">일정 등록하기</button>
						<button type="button" id="canclePlanButton"
							class="btn btn-outline-danger mt-auto mb-3">일정 초기화</button>
					</div>
				</div>
			</div>
		</div>
	</form>

	<script src="${root}/assets/js/main.js" type="text/javascript"></script>
	<script src="${root}/assets/js/search.js" type="text/javascript"></script>

</body>
</html>