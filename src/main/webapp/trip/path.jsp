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
        <script
            type="text/javascript"
            src="//dapi.kakao.com/v2/maps/sdk.js?appkey=21fdb12952447fb1ea9bc350b14a70bf&libraries=services,clusterer,drawing"
        ></script>
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
        
        


        <div class="container row">
            <div class="col-1 d-none d-xxl-block"></div>
            <div
                class="show_logIn p-5 mb-3 col-md-6 col-sm-4"
                id="board_div"
                style="position: relative; top: 1.5rem"
            >
                <h4
                    class="d-flex justify-content-center"
                    style="overflow: hiddlen; white-space: nowrap"
                >
                    여행 정보를 공유해요
                </h4>

                <!-- board list area -->
                <div id="board-list">
                    <div class="container md-6">
                        <div class="board__container" id="board-container">
                            <button
                                class="btn btn-primary ms-auto me-3 d-flex justify-content-end mb-3"
                                style="overflow: hidden; white-space: nowrap"
                                onclick="javascript:writePost()"
                            >
                                글쓰기
                            </button>

                            <table class="board-table">
                                <thead class="board__table-head">
                                    <tr class="board__titles">
                                        <th class="board__column th-num col-2">
                                            번호
                                        </th>
                                        <th
                                            class="board__column th-title col-4"
                                        >
                                            제목
                                        </th>
                                        <th
                                            class="board__column th-author col-2"
                                        >
                                            작성자
                                        </th>
                                        <th class="board__column th-date col-2">
                                            작성일
                                        </th>
                                        <th
                                            class="board__column th-views col-1"
                                        >
                                            조회
                                        </th>
                                    </tr>
                                </thead>
                                <tbody
                                    class="board__contents"
                                    id="board-body"
                                ></tbody>
                            </table>
                        </div>

                        <form
                            action="#"
                            class="editor__form"
                            id="editor-form"
                            style="display: none; width: 770px"
                        >
                            <div class="d-flex flex-row mb-3 mt-3">
                                <label style="width: 90px">제목</label>
                                <input
                                    class="editor__title-input p-1 mb-3 ms-3 border rounded"
                                    type="text"
                                    id="editor-title-input"
                                    style="width: 100%"
                                    value=""
                                    required
                                />
                            </div>
                            <div class="d-flex flex-row mb-3">
                                <label style="width: 90px">내용</label>
                                <textarea
                                    rows="10"
                                    class="editor__content-input p-1 mb-3 ms-3 border rounded input-group-lg"
                                    type="text"
                                    id="editor-content-input"
                                    style="width: 100%"
                                    value=""
                                    required
                                ></textarea>
                            </div>
                            <div class="d-flex justify-content-center">
                                <button
                                    class="editor__btn btn btn-outline-primary mb-3"
                                    id="editor-submit-btn"
                                    onclick="javascript:post()"
                                >
                                    작성 완료
                                </button>
                            </div>
                        </form>
                    </div>

                    <div
                        class="p-5"
                        id="contents_container"
                        style="display: none"
                    >
                        <button
                            class="btn mb-3"
                            id="back_btn"
                            onclick="javascript:post()"
                        >
                            < 뒤로가기
                        </button>
                        <div class="contents__container p-5"></div>
                    </div>
                </div>
            </div>



        <script src="${root}/assets/js/main.js" type="text/javascript"></script>
        <script src="${root}/assets/js/search.js" type="text/javascript"></script>

    </body>
</html>