<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<c:if test="${sessionScope.member ne null}">
	<c:set var="member" value="${sessionScope.member}" />
</c:if>
<c:if test="${findpwd ne null}">
	<c:set var="findpwd" value="${findpwd}" />
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
            <div style="height:100px"></div>
            <form method="POST" action="${root}/member">
			<input type="hidden" name="action" value="findpwd"/>
			<div class="shadow-sm rounded border p-5 mb-3 position-absolute top-50 start-50 translate-middle-x"
				id="findPwd_div"
				style="position: relative; top: 3rem; width: 740px;">
				<h4 class="mb-4">비밀번호 찾기</h4>
				<div class="form_group">
					<div class="d-flex flex-row mb-3">
						<label style="width: 90px">아이디</label> <input
							class="p-1 mb-3 ms-3" type="text" id="findPwd_id"
							style="width: 100%" name="userid" value="" required />
					</div>
					<div class="d-flex flex-row mb-3">
						<label style="width: 90px">이메일</label> <input
							class="p-1 mb-3 ms-3" type="email" id="findPwd_email"
							style="width: 100%" name="useremail" value="" required />
					</div>
					<div class="d-flex flex-row mb-3">
						<label style="width: 90px">이름</label> <input class="p-1 mb-3 ms-3"
							type="text" id="findPwd_name" name="username" style="width: 100%" value=""
							required />
					</div>
					<button class="col-12 btn btn-primary mb-3">
						비밀번호 찾기</button>
					<p id="pwd_ans" class="d-flex justify-content-center">비밀번호는 <strong>${findpwd}</strong> 입니다.</p>
				</div>
			</div>
		</form>
        </div>


	<script src="${root}/assets/js/main.js" type="text/javascript"></script>

</body>
</html>