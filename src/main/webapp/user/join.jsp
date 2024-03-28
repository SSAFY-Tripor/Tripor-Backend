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
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet"
        />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <title>Tripor</title>

        <link rel="preconnect" href="https://fonts.googleapis.com" />
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
        <link
            href="https://fonts.googleapis.com/css2?family=Gamja+Flower&family=Gowun+Dodum&display=swap"
            rel="stylesheet"
        />
        <link
            rel="stylesheet"
            href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.0/font/bootstrap-icons.css"
        />
        <link rel="stylesheet" href="${root}/assets/css/style.css" />
    </head>
    <body data-context-path="${root}">
    	<jsp:include page="/common/header.jsp"/>
        <jsp:include page="/common/aside.jsp"/>

        <div class="position-relative">
            <div style="height:100px"></div>
            <div
                class="shadow-sm rounded border p-5 mb-3 position-absolute top-50 start-50 translate-middle-x"
                id="join_div"
                style="
                    position: relative;
                    top: 3rem;
                    width: 740px;
                "
            >
                <h4>회원가입</h4>
                <p class="mb-5">환영해요! 여행을 떠나볼까요?</p>
                <form id="form-join" method="POST" action="${root}/member">
                	<input type="hidden" name="action" value="join">
                    <div class="form_group">
                        <label>이름</label><br />
                        <input
                            class="p-1 mb-3"
                            type="text"
                            id="username"
                            name="username"
                            style="width: 100%"
                            placeholder="이름을 입력해주세요."
                            required
                        />
                        <label>아이디</label><br />
                        <input
                            class="p-1 mb-3"
                            type="text"
                            id="userid"
                            name="userid"
                            style="width: 100%"
                            placeholder="아이디를 입력해주세요."
                            required
                        />
                        <label>비밀번호</label><br />
                        <input
                            class="p-1 mb-3"
                            type="password"
                            id="userpwd"
                            name="userpwd"
                            style="width: 100%"
                            placeholder="비밀번호를 입력해주세요."
                            required
                        />
                        <label>비밀번호 확인</label><br />
                        <input
                            class="p-1 mb-3"
                            type="password"
                            id="userpwdcheck"
                            name="userpwdcheck"
                            style="width: 100%"
                            placeholder="비밀번호를 한 번 더 입력해주세요."
                            required
                        />
                        <label>이메일</label><br />
                        <div class="d-flex flex-row">
			                <input
			                  type="text"
			                  class="p-1 mb-3 me-1"
			                  id="emailid"
			                  name="emailid"
			                  placeholder="이메일 아이디를 입력해주세요."
			                  style="width: 46%"
			                  required
			                />
			                <span>@</span>
			                <select
			                  class="p-1 ms-2 mb-3"
			                  id="emaildomain"
			                  style="width: 50%"
			                  name="emaildomain"
			                  aria-label="이메일 도메인 선택"
			                >
			                  <option selected>선택</option>
			                  <option value="ssafy.com">싸피</option>
			                  <option value="google.com">구글</option>
			                  <option value="naver.com">네이버</option>
			                  <option value="kakao.com">카카오</option>
			                </select>
			              </div>
			              <label>지역</label><br />
                        <div class="d-flex flex-row">
			                <select
			                  class="p-1 mb-3"
			                  id="join-sido"
			                  name="sido"
			                  style="width: 50%"
			                  aria-label="시도 선택"
			                >
			                  <option selected>선택</option>
			                  
			                </select>
			                <select
			                  class="p-1 ms-1 mb-3"
			                  id="join-gugun"
			                  style="width: 50%"
			                  name="gugun"
			                  aria-label="구군 선택"
			                >
			                  <option selected>선택</option>
			                  
			                </select>
			              </div>
							<button
                       	 	type="submit"
                       	 	id="regist"
                            class="col-12 btn btn-primary mb-3 mt-2"
                        	>
                            회원가입
                        </button>
                        
                    </div>
                    
                </form>
            </div>
        </div>
        <script src="${root}/assets/js/main.js" type="text/javascript"></script>

    </body>
</html>