document.addEventListener('keydown', function(event) {
	if (event.keyCode === 13) {
		event.preventDefault();
	}
	;
}, true);

function openModal() {
	$("#addFriend").modal();
};

function loadMembers() {
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	let httpRequest = new XMLHttpRequest();
	let id = document.getElementById("searchId").value;
	
	httpRequest.onreadystatechange = function() {
		if (httpRequest.readyState === XMLHttpRequest.DONE) {
			if (httpRequest.status === 200) {
				if (httpRequest.response) {
					console.log(httpRequest.response);
					let result = JSON.parse(httpRequest.response);
					console.log(result);

					let html = "<img src=\"img/image.png\"><p>" + result.id
							+ " / " + result.mbti + "</p>";
					document.getElementById("memberInfo").innerHTML = html;

				} else {
					document.getElementById("memberInfo").innerText = "일치하는 회원이 없습니다.";
				}
			} else {
				alert('request에 뭔가 문제가 있어요.');
			}
		}
	}
	// POST로 요청
	httpRequest.open('POST', "friends/searchMember", true);
	httpRequest.setRequestHeader(header, token);
	httpRequest.setRequestHeader('Content-type',
			'application/x-www-form-urlencoded');
	httpRequest.send("id=" + id);
};