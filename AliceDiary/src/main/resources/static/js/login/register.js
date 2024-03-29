function checkId(id) {
	var check = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
	if (check.test(id)){
		alert("ID에는 한글을 입력할 수 없습니다.");
	} else {
	
		var header = $("meta[name='_csrf_header']").attr('content');
		var token = $("meta[name='_csrf']").attr('content');
		var input = document.getElementById("id").value;//id값이 "id"인 입력란의 값을 저장
		if(input.length > 0) {
			$.ajax({
				url : './register/idCheck', //컨트롤러에서 인식할 주소
				type : 'POST', //POST 방식으로 전달
				data : {
					id : input
				},
				beforeSend : function(xhr) {
					xhr.setRequestHeader(header, token);
				},
				success : function(data) { //컨트롤러에서 넘어온 check값을 받는다
					if (data == 0) {
						$('#id_ok').css("display", "inline-block");
						$('#id_already').css("display", "none");
					} else if (data == 1) {
						$('#id_already').css("display", "inline-block");
						$('#id_ok').css("display", "none");
					}
				},
				error : function() {
					alert("에러가 발생했습니다.");
				}
			});
		}else {
			document.getElementById('id_ok').style.display="none";
			document.getElementById('id_already').style.display="none";
		}
}
	};
	
function checkNickname() {
	var header = $("meta[name='_csrf_header']").attr('content');
	var token = $("meta[name='_csrf']").attr('content');
	var input = document.getElementById("name").value; //id값이 "name"인 입력란의 값을 저장
	if(input.length > 0) {
		$.ajax({
			url : './register/nicknameCheck', //컨트롤러에서 인식할 주소
			type : 'POST', //POST 방식으로 전달
			data : {
				id : input
			},
			beforeSend : function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			success : function(data) { //컨트롤러에서 넘어온 check값을 받는다
				if (data == 0) {
					$('#nickname_ok').css("display", "inline-block");
					$('#nickname_already').css("display", "none");
				} else if (data == 1) {
					$('#nickname_already').css("display", "inline-block");
					$('#nickname_ok').css("display", "none");
				}
			},
			error : function() {
				alert("에러가 발생했습니다.");
			}
		});
	}else {
			document.getElementById('nickname_ok').style.display="none";
			document.getElementById('nickname_already').style.display="none";
		}
};
	
function checkPwd() {
	var password = document.getElementById('password');
	var confirmPassword = document.getElementById('confirmPassword');
	var confirmMsg = document.getElementById('confirmMsg');
	var correctColor = "#00ff00";
	var wrongColor = "#ff0000";
	var correct = 0;
	var wrong = 1;

	if (password.value == confirmPassword.value) {
		confirmMsg.innerHTML = "";
		document.regForm.hiddenNum.value = 0;
	} else {
		confirmMsg.style.color = wrongColor;
		confirmMsg.innerHTML = "비밀번호 불일치";
		document.regForm.hiddenNum.value = 1;
		
	}
}	

function regSuccess() {
		if (document.getElementById("id").value.length == 0 || 
			document.getElementById("password").value.length <= 7 ||
			document.getElementById("confirmPassword").value.length == 0 ||
			document.getElementById("name").value.length == 0 ||
			document.getElementById("birth").value.length == 0 ||
			document.getElementById("mobile").value.length == 0 ||
			document.getElementById("email").value.length == 0
			){
			alert("필수 항목을 입력해주세요.");
			return false;
		} else if(document.getElementById("password").value != document.getElementById("confirmPassword").value) {
			alert("비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
			return false;
		}else if(document.getElementById("gender").value=="none") {
			alert("성별을 선택해주세요.");
			return false;
		}else{
			alert("이메일에서 회원 가입 인증을 진행해주세요.");
			return true;
	}
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



function PreviewImage() {
	var preview = new FileReader();
	preview.onload = function(e) {
		document.getElementById("profileImage").src = e.target.result;
	};
	preview.readAsDataURL(document.getElementById("file").files[0]);
};

function existEmail(email){
	var header = $("meta[name='_csrf_header']").attr('content');
	var token = $("meta[name='_csrf']").attr('content');
	console.log(email);
	if (email.length > 0){
		$.ajax({
			url : './register/emailCheck', 
			type : 'POST', //POST 방식으로 전달
			data : {
				email : email
			},
			beforeSend : function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			success : function(result) { //컨트롤러에서 넘어온 check값을 받는다
				console.log(result);
				if (result == 'true') {
					$('#emailSpanNo').css("display", "none");
					$('#emailSpanYes').css("display", "inline-block");
					$('#checkedEmail').value="true";
				} else {
					$('#emailSpanYes').css("display", "none");
					$('#emailSpanNo').css("display", "inline-block");
					$('#checkedEmail').value="false";
				}
			},
			error : function() {
				alert("에러가 발생했습니다.");
			}
		});
	}
}