function makeRequest() {
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	let httpRequest = new XMLHttpRequest();

	let param = "{\"name\":\"" + document.getElementById("name").value
			+ "\", \"mobile\":\"" + document.getElementById("mobile").value
			+ "\", \"email\": \"" + document.getElementById("email").value+"\"}";
	console.log(param);
	let paramTest = "name="+document.getElementById("name").value+"&mobile="+document.getElementById("mobile").value+"&email="+document.getElementById("email").value;
	//POST로 요청
	httpRequest.open('POST', "findId", true);
	httpRequest.setRequestHeader(header, token);
    httpRequest.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	httpRequest.send(paramTest);
	
	httpRequest.onreadystatechange = function() {
		if (httpRequest.readyState === XMLHttpRequest.DONE) {
			if (httpRequest.status === 200) {
				if (httpRequest.response != 0) {
					let member = JSON.parse(httpRequest.response);
					console.log(member);
					document.getElementById("existInfo").innerText = "회원님의 정보와 일치하는 아이디 입니다.";
					document.getElementById("existUserInfoBox").style.display = "block";
					document.getElementById("existUserInfo").innerHTML = "<b>아이디 : "
							+ member.id + "</b> <br/>(가입날짜 : "
							+ member.regDate + ")";
					

				} else {
					document.getElementById("existInfo").innerText = "존재하지 않는 회원입니다.";
					document.getElementById("existUserInfoBox").style.display = "none";
				}
			} else {
				alert('request에 뭔가 문제가 있어요.');
			}
		}
	};
}