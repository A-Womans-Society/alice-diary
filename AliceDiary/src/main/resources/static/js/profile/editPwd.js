function checkPwd() {
		var password = document.getElementById('newPwd');
		var confirmPassword = document.getElementById('confirmNewPwd');
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