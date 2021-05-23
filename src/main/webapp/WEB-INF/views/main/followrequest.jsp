<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script>
	function followrequestview(){
		$.ajax({
			url : '/follow/request/view',
			type : 'post',
			async : true,
			success : function(data){
				var a = '';
				var b = '';
				$.each(data.fr, function(key, value){
					a += '<div>';
					a += '<div class="img" onclick="location.href=\'/main/user/'+
						value.request.id +'\'">';
					if(value.request.profile_photo == null)
						a += '<img src="/images/noimage.png">';
					else
						a += '<img src="/images/profile/' + value.request.profile_photo + '">';
					
					a += '<span>' + value.request.userId +'</span>';
					a += '</div>'
					a += '<a class="btn btn-default" onclick="follow_accept('+ value.request.id +');">팔로우 승인</a>';			
					a += '</div>';
				});
				b += '<span">팔로우 요청 알림 </span><span>'+ data.followcnt +'개</span>';

				$('.request_user').html(a);
				$('.request_alarm').html(b);
			}
		});
	}
	
	function follow_accept(id){
		$.ajax({
			url : '/follow/request/accept/' + id,
			type : 'post',
			async : true,
			success : function(data){
				alert(data.name + '님 팔로우를 승인하였습니다.');
				followrequestview();
			}
		});
	}


	$(document).ready(function() {
		followrequestview();
	});
</script>