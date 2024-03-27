<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<c:if test="${sessionScope.member ne null}">
	<c:set var="member" value="${sessionScope.member}" />
</c:if>
<div class="fixed-top">
	<div class="navbar navbar-expand-md bg-white navbar-light">
		<div class="container-fluid justify-content-center">
			<a href="${root}" class="navbar-brand fw-bold fs-1 col-lg-2"
				id="header_title">Tripor</a>


			<div
				class="col-lg-2 d-none d-md-none d-lg-none collapse navbar-collapse d-flex flex-row justify-content-end me-5 pe-5"
				id="header_join_login"
				style="display: block; overflow: hidden; white-space: nowrap;">
				<c:if test="${member eq null}">
					<button id="joinButton" class="btn btn-outline-primary m-1"
						onclick="location.href = '${root}/member?action=mvJoin'">
						회원가입</button>
					<button id="logInButton" class="btn text-white btn-primary m-1"
						onclick="location.href = '${root}/member?action=mvLogin'">
						로그인</button>
				</c:if>
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