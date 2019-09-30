<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includes/home.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${home }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form class="board-form" method="post" action="${home }/board">
					<input type = "hidden" name = "a" value="write">
					<input type = "hidden" name = "flag" value="${flag }">
					<c:if test="${flag == 1}">
						<input type="hidden" name="gNo" value="${boardVo.gNo }">
						<input type="hidden" name="oNo" value="${boardVo.oNo }">
						<input type="hidden" name="depth" value="${boardVo.depth }">
					</c:if>
					<table class="tbl-ex">
						<tr>
							<th colspan="2">글쓰기 </th>
						</tr>
						<tr>
							<td class="label">제목</td>
							<td><input type="text" name="title" value=""></td>
						</tr>
						<tr>
							<td class="label">내용</td>
							<td>
								<textarea id="content" name="contents"></textarea>
							</td>
						</tr>
					</table>
					<div class="bottom">
					<a href="${home }/board/list&search=${param.kwd}&selPage=${param.page }">글목록</a>
						<c:choose>
							<c:when test="${empty authUser }">
								<h3 align="right">로그인 후 글쓰기를 할 수 있습니다.</h3>
							</c:when>
							<c:when test="${viewVo.userNo==authUser.no}">
								<a href="${home }/board/modifyno=${viewVo.no }">글수정</a>
								<a href="${home }/board/write&no=${viewVo.no }">답글</a>						
							</c:when>
							<c:otherwise>
								<a href="${home }/board/write&no=${viewVo.no }">답글</a>
						</c:otherwise>
						</c:choose>
					</div>	
				</form>				
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp" />
		<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>