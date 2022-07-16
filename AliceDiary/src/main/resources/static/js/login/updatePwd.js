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