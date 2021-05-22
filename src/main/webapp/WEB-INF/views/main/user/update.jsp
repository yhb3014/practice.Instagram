
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" media="screen">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link href="https://fonts.googleapis.com/css?family=Cute+Font|Poor+Story&display=swap&subset=korean" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Playfair+Display:ital,wght@1,900&display=swap" rel="stylesheet">
<title>${user.name}님의Feed</title>
<style>
#footer {
	position: fixed;
	background-color: white;
	left: 0;
	right: 0;
	bottom: 0;
	height: 6rem;
}
</style>
</head>
<body>
	<div class="contents">
		<div class="img">
			<div class="title_image">
				<c:choose>
					<c:when test="${user.profile_photo == null}">
						<img src="/images/noimage.png" class="profile_image">
					</c:when>
					<c:otherwise>
						<img src="/images/profile/${user.profile_photo}" class="profile_image">
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="filebox">
			<form action="/main/user/image_insert" id="form" name="form" method="post" enctype="multipart/form-data" autocomplete="off">
				<label for="ex_file">프로필 사진 바꾸기</label> <input type="file" id="ex_file" name="filename" required />
				<button type="submit" class="btn btn-default">업로드</button>
			</form>
		</div>
	</div>
	<div class="info">
	<form action="/main/user/info_update">
		<div class="form-group">
			<label for="name">이름</label>
			<input type="text" class="form-control w200" id="name" 
				name="name" placeholder="이름" value="${user.name}" />
		</div>
		<div class="form-group">
			<label for="website">웹사이트</label>
			<input type="text" class="form-control w200" id="website" 
				name="website" placeholder="웹사이트" value="${user.website}" />
		</div>
		<div class="form-group">
			<label for="intro">소개</label>
			<input type="text" class="form-control w200" id="intro" 
				name="intro" placeholder="소개" value="${user.introduce}" />
		</div>
		<button type="submit" class="btn btn-default">완료</button>
	</form>
</div>
	<div id="footer">
		<%@ include file="../../include/bottom.jsp"%>
	</div>
</body>
</html>