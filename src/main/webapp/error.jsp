<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 관리 에러</title>
<style>
nav {
display : flex;
justify-content : space-between;
align-items : center
}
nav a {
margin-right: 10px
}
</style>
</head>
<body>
	<nav>
		<h1>오류 페이지</h1>
	</nav>
	<%-- 페이지만의 내용 --%>
	<div>
		오류가 발생했습니다.
		<div>
			<a href="${root}">메인 화면으로 이동</a>
		</div>
	</div>
</body>
</html>