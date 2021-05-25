<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" media="screen">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link href="https://fonts.googleapis.com/css?family=Cute+Font|Poor+Story&display=swap&subset=korean" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Playfair+Display:ital,wght@1,900&display=swap" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.css">
<title>Instagram</title>
<script>
	$(function() {
		$('.slide_gallery').bxSlider({
			// auto : true,
			autoControls : true,
			stopAutoOnClick : true,
			pager : true,
			controls : true
		});
	});
</script>
<style>
#footer {
	position: fixed;
	background-color: white; /*임의색상*/
	left: 0;
	right: 0;
	bottom: 0;
	height: 6rem;
}
#contents {
	bottom: 6rem;
}
.heart {
	background-color: white;
	border: 0;
	outline: 0;
	font-size: 18px;
	float: left;
	color: red;
}
.like {
	margin-top: 10px;
	margin-left: 50px;
}
.delete {
	margrin-left: 50px;
	margin-top: 10px;
}
.write {
	margin-top: 5px;
}
.commentList {
	weight: 500px;
	height: auto;
}
.comment {
	weight: 500px;
	height: 500px;
}
</style>
</head>
<body>
	<sec:authentication property="user.id" var="currentid" />
<div id="contents">
	<div class="r">
		<div>
			<div class="title_image">
				<c:choose>
					<c:when test="${p.user.profile_photo == null}">
						<a href="/main/user/${p.user.id}"> <img
							src="/images/noimage.png" class="tiny_image" align="left">
						</a>
					</c:when>
					<c:otherwise>
						<a href="/main/user/${p.user.id}"> 
							<img src="/images/profile/${p.user.profile_photo}" class="tiny_image" align="left">
						</a>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="userid_txt">
				<a href="/main/user/${p.user.id}">${p.user.userId}</a>
			</div>
		</div>
		<div id="gallery_wrap">
			<ul class="slide_gallery">
				<c:forEach var="img" items="${img}">
					<c:if test="${p.id == img.postId}">
						<li><img src="/images/${p.user.userId}/${img.filename}" class="imgg"></li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
		
		<div class="bar">
			<div class="like"></div>
			<c:if test="${postuserid == currentid}">
					<div class="delete" style="float: right;">
						<form action="/main/delete_post">
							<input type="hidden" name="postid" value="${p.id}">
							<button class="btn btn-default" style="float: right;"
								onclick="return confirm('게시글을 삭제 하시겠습니까?')" type="submit">삭제</button>
						</form>
					</div>
				</c:if>
		</div>
		
		<div class="write">
			<span>${p.description}</span>
		</div>
	</div>
	<div class="comment">
	<div class="container">
		<label for="content">comment</label>
		<form name="commentInsertForm">
			<div class="input-group">
				<input type="hidden" name="pid" id="pid" value="${p.id}" /> 
				<input type="text" class="form-control" id="content" name="content"
					placeholder="내용을 입력하세요."> 
				<input type="hidden" name="uid" value="${currentid}" />
				<span class="input-group-btn">
					<button class="btn btn-default" type="button"
						name="commentInsertBtn">등록</button>
				</span>
			</div>
		</form>
	</div>
	<div class="container">
		<div class="commentList"></div>
	</div>
	</div>
</div>
<div id="footer">
	<%@ include file="../include/bottom.jsp"%>
</div>
	<%@ include file="like.jsp"%>
	<%@ include file="comment.jsp"%>
</body>
</html>