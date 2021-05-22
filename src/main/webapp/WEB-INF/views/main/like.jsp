<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script>
	var id = '${id}'; // postid

	function likeview() {
		$.ajax({
			url : '/like/view',
			type : 'post',
			async : true,
			data : {'id' : id}, // postid
			dataType : "json",
			success : function(data) {
				var a = '';
				if (data.cnt == 1) {   // 포스팅 좋아요 누름
					a += '<a onclick="likeDelete('+ id +');"'
						+ 'class="glyphicon glyphicon-heart heart" aria-hidden="true"></a>'
					a += '<span class="glyphicon glyphicon-comment" aria-hidden="true"></span> <br />'
					a += '<span><b>좋아요 ' + data.total_cnt + '개</b></span>' // 총 좋아요 개수
				} else if (data.cnt == 0) { // 안누름
					a += '<a onclick="likeInsert('+ id +');"' 
						+'class="glyphicon glyphicon-heart-empty heart" aria-hidden="true"></a>'
					a += '<span class="glyphicon glyphicon-comment" aria-hidden="true"></span> <br />'
					a += '<span><b>좋아요 ' + data.total_cnt + '개</b></span>'		
				}
				$('.like').html(a); // -> <div class="like></div>
			}
		});
	}
	
	function likeInsert(pid){
		$.ajax({
			url : '/like/insert/' + pid, // pathvariable로 설정했으므로 postid값을 넘겨줘야됨
			type : 'post',
			success : function(data){
				if(data == 1)
					likeview();
			}
		});
	}
		
	function likeDelete(pid){
		$.ajax({
			url : '/like/delete/' + pid,
			type : 'post',
			success : function(data){
				if(data == 1)
					likeview();
			}
		});
	}
	
	$(document).ready(function() { 
		likeview(); 
	});
</script>