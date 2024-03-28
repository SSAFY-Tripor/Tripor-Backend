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
<body>

<div>
<form action="${root}/board" method="POST">
	<input type="hidden" name="action" value="modify"/>
	<input type="hidden" name="boardno" value="${board.boardNo}"/>
	<input type="text" readonly name="userid" value="글쓴이: ${board.userId}"/>
	<input type="text" name="subject" value="제목: ${board.subject}"/>
	<input type="text" name="content" value="본문내용: ${board.content}"/>
	<input type="submit" value="수정 완료"/> <input type="button" onclick='location.href="${root}/board?action=detail&boardno=${board.boardNo}"' value="수정 취소"/>
</form>
</div>

</body>
</html>