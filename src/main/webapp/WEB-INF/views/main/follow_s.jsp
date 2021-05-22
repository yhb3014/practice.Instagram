<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script>
	var pageid = '${id}';
	
	function followview(){
		$.ajax({
			url : '/follow/view/' + pageid,
			type : 'post',
			async : true,
			data : {'id' : pageid},
			dataType : "json",
			success : function(data){
				var a = '';
				var b = '';
				if(data.booool == true){
					a += '<a class="btn btn-default" onclick="followDelete('+ pageid +')">' 
						+ '팔로우 취소</a>'
				}
				else{
					a += '<a class="btn btn-default" onclick="followInsert('+ pageid +')">' 
						+ '팔로우</a>'
				}
				b += '<span style="margin-left: 20px;">'+ data.follower +'</span><br> <span>팔로워</span>';
				a += '<a href="/main/user/message/${page_id}" class="btn btn-default">메시지</a>'
				
				$('.follow_d').html(a);
				$('.follower_d').html(b);
			}
		});
	}
	
	function followInsert(id){
		$.ajax({
			url : '/follow/insert/' + id,
			type : 'post',
			success : function(data){
				if(data == 1)
					followview();
			}
		});
	}
	
	function followDelete(id){
		$.ajax({
			url : '/follow/delete/' + id,
			type : 'post',
			success : function(data){
				if(data == 1)
					followview();
			}
		});
	}
	
	$(document).ready(function(){
		followview();
	});
</script>