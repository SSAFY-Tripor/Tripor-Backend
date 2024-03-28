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
    <body>
    	<jsp:include page="/common/header.jsp"/>
        <jsp:include page="/common/aside.jsp"/> 
        
        

        <div class="position-relative">
            <div style="height:100px"></div>
            <div
                class="shadow-sm rounded border p-5 mb-3 position-absolute top-50 start-50 translate-middle-x"
                id="login_div"
                style="
                    position: relative;
                    top: 3rem;
                    width: 740px;
                "
            >
                <h4>로그인</h4>
                <p class="mb-5">환영해요! 다시 오셨네요 :)</p>
                <form id="form-login" method="POST" action="${root}/member">
                	<input type="hidden" name="action" value="login">
                    <div class="form_group">
                    <div class="d-flex flex-row justify-content-between">
                        <label>아이디</label><br />
                        <span>
                        	<input style="width:15px; height:15px" class="m-1" type="checkbox" id="saveid" name="saveid" /> 아이디 저장 <br/>
                        </span>
                     </div>  
                        <input
                            class="form-contro p-1 mb-3"
                            type="text"
                            id="userid" 
                            name="userid"
                            style="width: 100%"
                            placeholder="아이디를 입력해주세요."
                            required
                        />
                  
                        
                        <label>비밀번호</label><br />
                        <input
                            class="form-control p-1 mb-5"
                            type="password"
                            id="userpwd" 
                            name="userpwd"
                            style="width: 100%"
                            placeholder="비밀번호를 입력해주세요."
                            required
                        />
                        <button
                       	 	type="submit"
                       	 	id="regist"
                            class="col-12 btn btn-primary mb-3"
                        >
                            로그인
                        </button>
                        <div class="d-flex justify-content-center">
                            <a
                                href="#"
                                class="text-decoration-none text-primary"
                                onclick="location.href = '${root}/member?action=mvJoin'"
                                >회원가입</a
                            >
                            <span class="ps-1 pe-1">|</span>
                            <a
                                href="#"
                                class="text-decoration-none text-primary"
                                onclick="location.href = '${root}/member?action=mvFindPwd'"
                                >비밀번호 찾기</a
                            >
                        </div>
                    </div>
                </form>
            </div>
        </div>
 

        <script src="${root}/assets/js/main.js" type="text/javascript"></script>
        <script src="${root}/assets/js/search.js" type="text/javascript"></script>

    </body>
</html>