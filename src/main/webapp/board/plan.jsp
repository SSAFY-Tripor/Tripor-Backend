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
		<div class="navbar navbar-expand-md bg-white navbar-light">
			<div class="container-fluid justify-content-center">
				<a href="${root}" class="navbar-brand fw-bold fs-1 col-lg-2"
					id="header_title">Tripor</a>



				<div class="col-lg-8 col-md-12 d-flex flex-row" id="makePlace">
					<input class="form-control mr-sm-2 m-2 w-75 shadow-sm"
						type="search" placeholder="여행지를 검색하세요!" aria-label="Search"
						id="searchInput" />
					<button class="btn text-white btn-primary m-2"
						style="overflow: hiddlen; white-space: nowrap" id="searchButton">
						검색</button>
				</div>
				<div
					class="col-lg-2 d-none d-md-none d-lg-block collapse navbar-collapse d-flex flex-row"
					id="header_join_login"
					style="display: block; overflow: hidden; white-space: nowrap;">


					<c:if test="${member eq null}">
						<button id="joinButton" class="btn btn-outline-primary m-1"
							onclick="location.href = '${root}/member?action=mvJoin'">
							회원가입</button>
					</c:if>
					<button id="logInButton" class="btn text-white btn-primary m-1"
						onclick="location.href = '${root}/member?action=mvLogin'">
						로그인</button>
					<c:if test="${member ne null}">
						<div>
							<span>${member.userName} 님 로그인 중</span>
							<button id="logOutButton" class="btn text-white btn-primary m-1"
								onclick="location.href = '${root}/member?action=logout'">
								로그아웃</button>
						</div>
					</c:if>




				</div>
			</div>
		</div>
		<div id="hr"></div>
	</div>
	<jsp:include page="/common/aside.jsp" />



	<div class="container" style="z-index: 1; padding-left: 0">
		<div class="show_Home" id="home_div" style="position: relative">
			<!-- <div class="dropdowns">
                <select id="region">
                    <option value="">도 선택</option>
                </select>
                <select id="subregion">
                    <option value="">시/구 선택</option>
                </select>
                <select id="tourType">
                    <option value="">관광 타입 선택</option>
                </select>
            </div> -->
			<div id="search-map" class="search-map"
				style="width: 1500px; height: 1000px">
				<div id="planList"
					class="plan-list position-absolute top-0 start-0 bg-light p-3"
					style="width: 20%; height: 100%; overflow-y: auto; background-color: rgba(255, 255, 255, 0.5) !important; z-index: 1000; display: block;">
					<h4 class="text-dark">여행 계획 목록</h4>
					<ul id="planItems" class="list-unstyled"></ul>
					<button id="savePlanButton"
						class="btn btn-outline-primary mt-auto mb-3">일정 등록하기</button>
				</div>
			</div>
		</div>
	</div>


	<script src="${root}/assets/js/main.js" type="text/javascript"></script>
	<script src="${root}/assets/js/search.js" type="text/javascript"></script>

</body>
</html>