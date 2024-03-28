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
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body data-context-path="${root}">
	<div>
	<input type="button" value="이전으로"/>
	<p> 사용자아이디 or 이름: ${board.userId} </p> 
	<p> 조회수: ${board.hit} </p> 
	<p> 게시판 등록 날짜: ${board.registerDate}</p>
	<div>제목 : ${board.content}</div>
	<div>본문 내용 : ${board.subject}</div>
	<c:if test="${board.userId eq member.userId}">
		<input type="button" onclick='location.href="${root}/board?action=mvModify&boardno=${board.boardNo}"' value="수정하기"/> 
		<input type="button" onclick='location.href="${root}/board?action=delete&boardno=${board.boardNo}"' value = "삭제하기"/>
		<!-- 삭제하시겠습니까? 필요 시 body에 `data-context-path` 참고! -->
	</c:if>
	<div> 댓글 영역? </div>
	</div>
</body>
</html>