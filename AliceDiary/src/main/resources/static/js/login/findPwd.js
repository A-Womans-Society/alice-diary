function makeRequest() {
		let token = $("meta[name='_csrf']").attr("content");
		let header = $("meta[name='_csrf_header']").attr("content");
		let httpRequest = new XMLHttpRequest();

		let param = 'id=' + document.getElementById("id").value
				+ '&name=' + document.getElementById("name").value
				+ '&mobile=' + document.getElementById("mobile").value;

		httpRequest.onreadystatechange = function() {
			if (httpRequest.readyState === XMLHttpRequest.DONE) {
				if (httpRequest.status === 200) {
					if (httpRequest.response != 0) {
						let member = JSON.parse(httpRequest.response);
						location.href="./updatePwd/"+member.num;
					} else {
						alert("일치하는 유저 없음");
					}
				} else {
					alert('request에 뭔가 문제가 있어요.');
				}
			}
		};

		//POST로 요청
		httpRequest.open('POST', "findPwd", true);
		httpRequest.setRequestHeader(header, token);
		httpRequest.setRequestHeader('Content-type',
				'application/x-www-form-urlencoded');
		httpRequest.send(param);
	}