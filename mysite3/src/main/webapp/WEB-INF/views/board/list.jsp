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
				<form id="search_form" action="${home }/board&selPage=1" method="post">
					<input type="text" id="kwd" name="search" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>				
					<c:set var="count" value="${fn:length(list) }"/>
					<c:forEach items="${list }" var="vo" varStatus="status">	
					<tr>
						<td>${vo.no }</td>
						<td style="padding-left:${30*vo.depth-20}px;text-align:left">
							<c:if test="${vo.depth > 0 }">
								<img src="${home }/assets/images/reply.png"/>
							</c:if>
							<c:choose>
								<c:when test="${vo.status==false}">
									<a href="${home }/board/view&no=${vo.no }&kwd=${param.keyWord }&page=${param.page }">${vo.title }</a>							
								</c:when>								
								<c:when test="${vo.status==true}">
									<span>삭제된 글</span>	
								</c:when>								
							</c:choose>							
						</td>
						<td>${vo.userName }</td>
						<td>${vo.hit }</td>
						<td>${vo.regDate }</td>
						<td>
						<c:choose>
						<c:when test="${authUser.no==vo.userNo}">
							<a href="${home }/board/delete&no=${vo.no }&uno=${vo.userNo}" class="del"><img src="${home }/assets/images/recycle.png"/></a>
						</c:when>
						</c:choose>
						</td>
					</tr>
					</c:forEach>	
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:forEach var="selPage" begin="1" end="${pageSu }" step="1" >
						<li><a href="${home }/board?selPage=${selPage}">${selPage}</a></li>
						</c:forEach>
						<!-- <li><a href="${home }/board?a=list&page=${page}">◀</a></li> 
						<li><a href="">1</a></li>
						<li class="selected">2</li>
						<li><a href="">3</a></li>
						<li>4</li>
						<li>5</li>
						<li><a href="">▶</a></li>-->
					</ul>
				</div>					
				<!-- pager 추가 -->
				<c:choose>
					<c:when test="${empty authUser }">
					<h3 align="right">로그인 후 글쓰기를 할 수 있습니다. </h3>
					</c:when>
					<c:otherwise>
						<div class="bottom">
							<a href="${home }/board/write" id="new-book">글쓰기</a>
						</div>
					</c:otherwise>
				</c:choose>

			</div>
		</div>
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp" />
		<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>