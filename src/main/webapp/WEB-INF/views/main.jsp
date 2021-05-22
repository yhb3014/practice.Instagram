<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" media="screen">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link href="https://fonts.googleapis.com/css?family=Cute+Font|Poor+Story&display=swap&subset=korean" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Playfair+Display:ital,wght@1,900&display=swap" rel="stylesheet">
<link rel="stylesheet" href="/res/main.css" type="text/css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.css">
<title>${user.name}님의Feed</title>
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
	margin-left: 45px;
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
.write {
	margin-top: 5px;
}
<script> $(function() { $('.slide_gallery ').bxSlider({ // auto :true,
	autoControls : true, stopAutoOnClick : true, pager : true, controls : true
	});
});
</script>
</style>
</head>
<body>
	<div id="contents">
		<div class="post">
			<div class="nav">
				<span class="title"> <a href="/main" class="title_ft">Instagram</a>
				</span> <a href="/main"> <span class="glyphicon glyphicon-send" aria-hidden="true"></span>
				</a>
			</div>

			<c:forEach var="p" items="${posting}">
				<!-- java의 for문과 같다 posting list를 for문돌림 -->
				<div class="r">
					<div class="ii">
						<div class="title_image">
							<a href="/main/user/${p.user.id}"> <img src="/images/profile/${p.user.profile_photo}" class="tiny_image">
							</a>
						</div>
						<div class="userid_txt">
							<a href="/main/user/${p.user.id}">${p.user.userId}</a>
						</div>
					</div>
					<div id="gallery_wrap">
						<ul class="slide_gallery">
							<c:forEach var="img" items="${img}">
								<c:if test="${p.id == img.postId}">
									<li><img src="/images/${p.user.userId}/${img.filename}" class="img"></li>
								</c:if>
							</c:forEach>
						</ul>
					</div>
					<div class="bar">
						<span class="glyphicon glyphicon-heart-empty" aria-hidden="true"></span>
						<span class="glyphicon glyphicon-comment" aria-hidden="true" onclick="location.href='/main/post/${p.id}'"></span>
					</div>
					<div class="write">
						<div class="write" style="cursor: pointer;">
						<span onclick="location.href='/main/post/${p.id}'">${p.description}</span>
					</div>
				</div>
			</div>
			</c:forEach>

		</div>
	</div>
	<div id="footer">
		<%@ include file="include/bottom.jsp"%>
	</div>
</body>
</html>