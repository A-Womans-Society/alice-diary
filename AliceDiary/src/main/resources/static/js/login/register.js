function regSuccess() {
		if (document.getElementById("id").value.length == 0 || document.getElementById("password").value.length == 0 ||document.getElementById("confirmPassword").value.length == 0 ||document.getElementById("name").value.length == 0 ||document.getElementById("birth").value.length == 0 ||document.getElementById("mobile").value.length == 0 ||document.getElementById("email").value.length == 0){
			alert("필수 항목을 입력해주세요.");
			return false;
		} 
		alert("가입을 축하합니다!");
		return true;
	}
	
	
	function regCancle() {
		if(!confirm("가입을 취소하시겠습니까?")) {
			return;
		}else {
			alert("가입이 취소되었습니다.");
			var contextPath = $('#contextPathHolder').attr('data-contextPath') ? $('#contextPathHolder').attr('data-contextPath') : '';
			location = contextPath + "/";
		}
	}
	
	function checkPwd() {
		var password = document.getElementById('password');
		var confirmPassword = document.getElementById('confirmPassword');
		var confirmMsg = document.getElementById('confirmMsg');
		var correctColor = "#00ff00";
		var wrongColor = "#ff0000";

		if (password.value == confirmPassword.value) {
			confirmMsg.innerHTML = "";
		} else {
			confirmMsg.style.color = wrongColor;
			confirmMsg.innerHTML = "비밀번호 불일치";
		}
	}

	function PreviewImage() {
		var preview = new FileReader();
		preview.onload = function(e) {
			document.getElementById("profileImage").src = e.target.result;
		};
		preview.readAsDataURL(document.getElementById("file").files[0]);
	};

	function checkId() {
		var header = $("meta[name='_csrf_header']").attr('content');
		var token = $("meta[name='_csrf']").attr('content');
		var id = document.getElementById("id").value; //id값이 "id"인 입력란의 값을 저장
		$.ajax({
			url : './register/idCheck', //컨트롤러에서 인식할 주소
			type : 'post', //POST 방식으로 전달
			data : {
				id : id
			},
			beforeSend : function(xhr) { //데이터 전송 전에 헤더에 csrf값 설정
				xhr.setRequestHeader(header, token);
			},
			success : function(check) { //컨트롤러에서 넘어온 check값을 받는다
				if (check != 1) {
					$('.id_ok').css("display", "inline-block");
					$('.id_already').css("display", "none");
				} else {
					$('.id_already').css("display", "inline-block");
					$('.id_ok').css("display", "none");
				}
			},
			error : function() {
				alert("에러가 발생했습니다.");
			}
		});
	};