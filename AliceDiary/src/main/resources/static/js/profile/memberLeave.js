function count() {
	$('.DOC_TEXT').keyup(function (e){    
		var content = $(this).val();    
		$('#counter').html("("+content.length+"자 / 최대 500자)");    //글자수 실시간 카운팅    
		
		if (content.length > 500){        
			alert("최대 500자까지 입력 가능합니다.");        
			$(this).val(content.substring(0, 500));        
			$('#counter').html("(500 / 최대500자)");    
		}
	});
}

function leaveCancle() {
	if(!confirm("회원 탈퇴를 취소하시겠습니까?")) {
				return;
	}else {
		alert("회원 탈퇴가 취소되었습니다.");
		var contextPath = $('#contextPathHolder').attr('data-contextPath') ? $('#contextPathHolder').attr('data-contextPath') : '';
		location.href = contextPath + "/AliceDiary/member/"+id;
	}
}

function leaveCheck() {
	if (document.getElementById("password").value.length == 0){
			alert("비밀번호를 입력해주세요.");
			return false;
	}
	if(!confirm("회원 탈퇴 후 재가입이 불가능합니다. 탈퇴하시겠습니까?")) {
		return;
	}else {
		alert("회원 탈퇴가 완료되었습니다. 이용해 주셔서 감사합니다.");
		var contextPath = $('#contextPathHolder').attr('data-contextPath') ? $('#contextPathHolder').attr('data-contextPath') : '';
		location.href= contextPath + "/AliceDiary/";
	}
}