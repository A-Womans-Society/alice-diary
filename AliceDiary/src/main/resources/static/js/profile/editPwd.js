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
	
function editPwd() {
	if(document.getElementById("newPwd").value != document.getElementById("confirmNewPwd").value) {
		alert("새로운 비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
		return false;
	}else{
		return true;
	}
}