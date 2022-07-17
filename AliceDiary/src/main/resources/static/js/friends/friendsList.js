document.addEventListener('keydown', function(event) {
	if (event.keyCode === 13) {
		event.preventDefault();
	}
	;
}, true);

function openModal() {
	document.getElementById("searchId").value = "";
	document.getElementById("memberInfo").innerHTML = "";
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

					let html = "<img src=\"img/image5.png\"><p>" + result.id
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

function loadFriends() {
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	let httpRequest = new XMLHttpRequest();
	
	let friends = document.getElementById("searchFriend").value;
	
	httpRequest.onreadystatechange = function() {
		if (httpRequest.readyState === XMLHttpRequest.DONE) {
			if (httpRequest.status === 200) {
				console.log(httpRequest.response);
				console.log(httpRequest.response.length);
				if (httpRequest.response ) {
					
					let result = JSON.parse(httpRequest.response);
					console.log(result);
					let resultHtml = "";
					for (let idx in result) {
						resultHtml += "<tr><td>"+idx+"</td><td>"+result[idx].id+"</td><td>"+result[idx].name+"</td><td>"+result[idx].mobile+"</td><td>"+result[idx].birth+"</td><td>"+result[idx].gender+"</td><td>"+result[idx].email+"</td><td>"+result[idx].groupName+"</td></tr>";
					}
					document.getElementById("friendsBody").innerHTML=resultHtml;					
				}
			} else {
				alert('request에 뭔가 문제가 있어요.');
			}
		}
	}
	
	// POST로 요청
	httpRequest.open('POST', "friends/searchFriend", true);
	httpRequest.setRequestHeader(header, token);
	httpRequest.setRequestHeader('Content-type',
			'application/x-www-form-urlencoded');
	httpRequest.send("friends=" + friends);
};