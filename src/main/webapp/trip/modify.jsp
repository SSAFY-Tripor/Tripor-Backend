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
		<div style="height:100px"></div>
		<form action="${root}/board" method="POST" class="editor__form mb-3 position-absolute top-50 start-50 translate-middle-x"
			id="editor-form" style="width: 45%">
			<div style="height: 90px"></div>
			<h4 class="d-flex justify-content-center"
				style="overflow: hidden; white-space: nowrap">여행 정보를 공유해요</h4>
			<div style="height:10px"></div>
			<input type="hidden" name="action" value="modify"/>
			
			
			<div class="d-flex flex-row mb-3 mt-3">
				<label style="width: 90px">제목</label> <input
					class="editor__title-input p-1 mb-3 ms-3 border rounded"
					type="text" id="editor-title-input" name="subject" value="${board.subject}"
					style="width: 100%" required />
			</div>
			<div class="d-flex flex-row mb-3">
				<label style="width: 90px">내용</label>
				<textarea rows="10"
					class="editor__content-input p-1 mb-3 ms-3 border rounded input-group-lg"
					type="text" id="editor-content-input" name="content"
					style="width: 100%" required>${board.content}</textarea>
			</div>
			
			
			<input type="hidden" name="boardno" value="${board.boardNo}"/>
			<input type="hidden" readonly name="userid" value="글쓴이: ${board.userId}"/>
			<div class="d-flex justify-content-center">
				<button type="submit" class="btn btn-outline-primary me-1">수정 완료</button>
				<button class="btn btn-outline-primary" onclick='location.href="${root}/board?action=detail&boardno=${board.boardNo}"'>수정 취소</button>
			</div>
			
		</form>
	</div>

</body>
</html>