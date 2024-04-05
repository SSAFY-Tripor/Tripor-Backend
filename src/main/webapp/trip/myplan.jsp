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
<body>
	<jsp:include page="/common/header.jsp" />
	<jsp:include page="/common/aside.jsp" />
	
		<div class="position-relative">
		<div class="d-flex flex-row">
				<div style="width: 540px; height: 100%" class="d-none d-xl-block"></div>
				<div id="planListDiv" style="width: 100%">
				<div style="height: 90px"></div>
					<c:if test="${ empty plans }">
						<div class="position-absolute top-50 start-50 translate-middle-x">
							<h4>
								아직 여행 계획이 없어요							
							</h4>
							<div>
								여행 계획 만들러 가기 &gt;&gt;
								<a class="link-primary" href="${root}/trip?action=mvPlan">GO!</a>
							</div>
						</div>
						</c:if>
					<div class="p-5 mb-3 d-flex flex-row flex-wrap">
					
						<c:forEach var="plan" items="${plans}">
							<c:set var="loop_flag" value="false" />
								<div class="card m-1" style="width: 24rem;">
									<c:if test="${not empty plan.tripList}">
										<c:forEach var="trip" items="${plan.tripList}">
											<c:if test="${not loop_flag }">
												<c:if test="${not empty trip.firstImage}">
													<img class="card-img-top" style="height: 200px; object-fit: cover" src="${trip.firstImage}"
														alt="Card image cap">
													<c:set var="loop_flag" value="true" />
												</c:if>
											</c:if>
										</c:forEach>
									</c:if>
		
							<c:if test="${not loop_flag}">
								<img class="card-img-top" style="height: 200px; object-fit: cover" src="${root}/img/no_image_logo.png"
									alt="Tripor 로고">
							</c:if>
							<div class="card-body">
								<h5 class="card-title">${plan.planName}</h5>
								<p class="card-text h6">${plan.tripList[0].title}~
									${plan.tripList[plan.tripList.size() - 1].title}</p>
								<a class="btn btn-primary"
									href="${root}/trip?action=detail&planid=${plan.planId}">자세히보기</a>
							    <a class="btn btn-outline-danger" href="${root}/trip?action=deletePlan&planid=${plan.planId}">삭제하기</a>
							    
							  </div>
							</div>
				
						</c:forEach>
					</div>
					
					
			</div>
		</div>
				
	</div>

	<script src="${root}/assets/js/main.js" type="text/javascript"></script>



</body>
</html>