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
<body data-context-path="${root}">
	<jsp:include page="/common/header.jsp" />
	<jsp:include page="/common/aside.jsp" />
	<div class="position-relative">
		<div style="height:100px"></div>
		<div class="mb-3 position-absolute top-50 start-50 translate-middle-x" style="width:45%">
			<span class="mb-3 text-primary" onclick="location.href = '${root}/board?action=list'" style="cursor: pointer">&lt; 뒤로가기</span>
			
			<h3 class="mt-3">${board.subject}</h3>
			<span style="font-size: medium"> ${board.userId} | ${board.registerDate} | 조회수 ${board.hit}</span> 

			<hr>
			<div>${board.content}</div>
			<hr>
			<c:if test="${board.userId eq member.userId}">
				<div class="d-flex justify-content-center">
					<input type="button" class="btn text-white btn-outline-primary m-1" onclick='location.href="${root}/board?action=mvModify&boardno=${board.boardNo}"' value="수정하기"/> 
					<input id="article-delete" type="button" class="btn text-white btn-outline-primary m-1" onclick="javascript:deleteArticle()" value = "삭제하기"/>
				</div>
				
				<!-- 삭제하시겠습니까? 필요 시 body에 `data-context-path` 참고! -->
			</c:if>
		</div>
	</div>
	<script src="${root}/assets/js/main.js" type="text/javascript"></script>
</body>
</html>