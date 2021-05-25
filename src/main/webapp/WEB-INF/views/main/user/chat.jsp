<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script>
	var id = '${id}';

	$('[name=chatbtn]').click(function() {
		var insertData = $('[name=chatinput]').serialize();
		chatInsert(insertData);
	});
	
	function chatInsert(insertData) {
		$.ajax({
			url: '/chat/insert',
			type: 'post',
			data: insertData,
			success : function(data){
				if(data == 1){
					chatlist();
					 // 메시지를 보내면 메시지 입력창은 초기화
					$('.textiiii').val('');
				}
			}
		});
	}
	
	function chatlist() {
		$.ajax({
			url: '/chat/list/' + id,
			type: 'post',
			async : true,
			data : {'id' : id},
			dataType : "json",
			success : function(data) {
				var a = '';
				$.each(data, function(key, value){
					a += '<div class="hmhm">';
					if(value.send.id == id) { // 보낸건 오른쪽
						a += '<div class="sasa">';
						a += '<span>'+ value.content +'</span>';
						a += '</div>';
					}
					else // 받은건 왼쪽
						a += '<span>' + value.content + '</span>';
					a += '<br /></div>';
				});
				$('.msg_view').html(a);
			}
		});
	}
	$(document).ready(function() {
		setInterval(chatlist, 3000); // 3초
	});
</script>