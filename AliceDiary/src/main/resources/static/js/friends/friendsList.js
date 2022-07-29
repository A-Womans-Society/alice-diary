document.addEventListener('keydown', function(event) {
	if (event.keyCode === 13) {
		event.preventDefault();
	}
	;
}, true);

function openModal() {
	document.getElementById("searchName").innerText = "";
	document.getElementById("memberInfo").innerHTML = "";
	$("#addFriend").modal();
};

function addOk(){
	let check = document.getElementById("checkSearch").value;
	if (check == 'false'){
		alert("친구 검색을 먼저 진행하세요.");
		return false;
	}else if(!confirm('친구를 추가하시겠습니까?')){
		return false;
	} else {
		return true;
	}
}

function loadMembers() {
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	let httpRequest = new XMLHttpRequest();
	let name = document.getElementById("searchName").value;
		
	httpRequest.onreadystatechange = function() {
		if (httpRequest.readyState === XMLHttpRequest.DONE) {
			if (httpRequest.status === 200) {
				if (httpRequest.response.length != 0) {
					let result = JSON.parse(httpRequest.response);
					let mbti = result.mbti;
					if (mbti === null) {
						mbti = "비밀-★";
						let html = "<img src='/AliceDiary/upload/profile/"+result.profileImg+"' id='picture'><p>" + result.name
                                + " / " + mbti + "</p>";
						document.getElementById("memberInfo").innerHTML = html;
                        document.getElementById("checkSearch").value = true;
                        document.getElementById("picture").style.width="100px";
                        
					} else {
                        let html = "<img src='/AliceDiary/upload/profile/"+result.profileImg+"' id='picture'><p>" + result.name
                                + " / " + mbti + "</p>";
                        document.getElementById("memberInfo").innerHTML = html;
                        document.getElementById("checkSearch").value = true;
                        document.getElementById("picture").style.width="100px";
                        
                    }					
				} else {
					document.getElementById("memberInfo").innerText = "일치하는 회원이 없습니다.";
					document.getElementById("checkSearch").value = false;					
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
	httpRequest.send("name=" + name);
};

// 대소문자 구분 없이 검색하는 방법

function loadFriends() {
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	let httpRequest = new XMLHttpRequest();
	
	let friends = document.getElementById("searchFriend").value;
	
	httpRequest.onreadystatechange = function() {
		if (httpRequest.readyState === XMLHttpRequest.DONE) {
			if (httpRequest.status === 200) {
				if (httpRequest.response) {					
					let result = JSON.parse(httpRequest.response);

					if (result.length == 0) {
						document.getElementById("friendsBody").innerHTML="<tr><td colspan='9' class='text-center'>친구를 추가해주세요.</td></tr>";
					} else {
						let resultHtml = "";
						for (let idx in result) {
							resultHtml += "<tr><td>"+(Number(idx)+1)+"</td><td>"+result[idx].id+"</td><td><a href='/AliceDiary/friends/friendInfo/"+result[idx].id+"'>"+result[idx].name+"</a></td><td>"+result[idx].mobile+"</td><td>"+result[idx].birth+"</td><td>"+result[idx].gender+"</td><td>"+result[idx].email+"</td><td>"+result[idx].groupName+"</td></tr>";
						}
						document.getElementById("friendsBody").innerHTML=resultHtml;					
					}
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
