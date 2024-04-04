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
<body>
	<jsp:include page="/common/header.jsp" />
	<jsp:include page="/common/aside.jsp" />

	<div class="position-relative">
		<div style="height: 100px"></div>
		<div class="mb-3 position-absolute top-50 start-50 translate-middle-x"
			id="board_div" style="position: relative; top: 1.5rem; width: 47%">
			<h4 class="d-flex justify-content-center"
				style="overflow: hidden; white-space: nowrap">여행 정보를 공유해요</h4>

			<!-- board list area -->
			<div id="board-list" style="overflow: hidden; white-space: nowrap">
				<div class="board__container" id="board-container">
					<button
						class="btn btn-primary ms-auto me-3 d-flex justify-content-end mb-3"
						style="overflow: hidden; white-space: nowrap" type="button"
						onclick='location.href="${root}/board?action=mvWrite"'>
						글쓰기</button>
					<!--  PAGE 및 검색으로 인해 추가 한 부분 -->
					<form id="form-search" class="d-flex mb-2" action="${root}/board">
						<input type="hidden" name="action" value="list" /> <input
							type="hidden" name="pgno" value="1" /> <select id="key"
							name="key" class="form-select form-select-sm ms-5 me-1 w-50"
							aria-label="검색조건">
							<option selected>검색조건</option>
							<option value="subject">제목</option>
							<option value="user_id">작성자</option>
						</select>
						<div class="input-group input-group-sm">
							<input id="word" name="word" type="text" class="form-control"
								placeholder="검색어..." />
							<button id="btn-search" class="btn btn-dark" type="button">검색</button>
						</div>
					</form>
					<!--  PAGE 및 검색으로 인해 추가 한 부분 -->
					<table class="board-table">
						<thead class="board__table-head">
							<tr class="board__titles">
								<th class="board__column th-num col-2 col-sm-2">번호</th>
								<th class="board__column th-title col-4 col-sm-5">제목</th>
								<th class="board__column th-author col-2 col-sm-3">작성자</th>
								<th class="board__column th-date col-3 d-none d-md-block">작성일</th>
								<th class="board__column th-views col-1 col-sm-2">조회</th>
							</tr>
						</thead>
						<tbody class="board__contents" id="board-body">
							<c:set var="boardCount" value="${navigation.totalCount - (navigation.currentPage - 1) * 20}"/> 
							<c:forEach var="board" varStatus="status" items="${boards}">
								<tr>
									<td class="board__column col-2 col-sm-2">${boardCount - status.index}</td>
									<th class="board__column col-4 col-sm-5"><a
										href="${root}/board?action=detail&boardno=${board.boardNo}">${board.subject}</a></th>
									<td class="board__column col-2 col-sm-3">${board.userId}</td>
									<td class="board__column col-3 d-none d-md-block">${board.registerDate}</td>
									<td class="board__column col-1 col-sm-2">${board.hit}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

				</div>
				<!-- 
				<div class="p-5" id="contents_container" style="display: none">
					<button class="btn mb-3" id="back_btn" onclick="javascript:post()">
						< 뒤로가기</button>
					<div class="contents__container p-5"></div>
					 -->
				<!--  페이지 추가  -->
				<div class="row mt-2">${navigation.navigator}</div>
				<!--  페이지 추가  -->
			</div>
		</div>
	</div>
    <form id="form-param" method="get" action="">
      <input type="hidden" id="p-action" name="action" value="">
      <input type="hidden" id="p-pgno" name="pgno" value="">
      <input type="hidden" id="p-key" name="key" value="">
      <input type="hidden" id="p-word" name="word" value="">
    </form>
	<script>
		let pages = document.querySelectorAll(".page-link");
		pages.forEach(function(page) {
			page.addEventListener("click", function() {
				console.log(this.parentNode.getAttribute("data-pg"));
				document.querySelector("#p-action").value = "list";
				document.querySelector("#p-pgno").value = this.parentNode
						.getAttribute("data-pg");
				document.querySelector("#p-key").value = "${param.key}";
				document.querySelector("#p-word").value = "${param.word}";
				document.querySelector("#form-param").submit();
			});
		});
	</script>
	<script src="${root}/assets/js/main.js" type="text/javascript"></script>
</body>
</html>