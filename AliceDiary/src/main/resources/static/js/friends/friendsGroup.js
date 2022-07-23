function openModalGroup() {
	document.getElementById("searchId").innerText = "";
	document.getElementById("memberInfo").innerHTML = "";
	$("#addGroup").modal();
};

function groupCheck(){
	let check = document.getElementById("checkGroup").value;
	if (check == 'false'){
		alert("그룹명을 먼저 입력하세요.");
		return false;
	}else if(!confirm('그룹을 등록하시겠습니까?')){
		return false;
	} else {
		return true;
	}
}

```jsx
function openModalGroup() {
	document.getElementById("searchId").innerText = "";
	document.getElementById("memberInfo").innerHTML = "";
	$("#addGroup").modal();
};

function groupCheck(){
	let check = document.getElementById("checkGroup").value;
	if (check == 'false'){
		alert("그룹명을 먼저 입력하세요.");
		return false;
	}else if(!confirm('그룹을 등록하시겠습니까?')){
		return false;
	} else {
		return true;
	}
}

function addGroup() {
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	let httpRequest = new XMLHttpRequest();
	
	let group = document.getElementById("newGroup").value;
		
	httpRequest.onreadystatechange = function() {
		if (httpRequest.readyState === XMLHttpRequest.DONE) {
			if (httpRequest.status === 200) {
					if (httpRequest.response) {					
					let result = JSON.parse(httpRequest.response);
					document.getElementById("newGroup").value = result;
					document.getElementById("checkGroup").value = true;
					if (result.length == 0) {
						document.getElementById("friendsGroupBody").innerHTML="<tr><td colspan='9' class='text-center'>그룹을 추가해주세요.</td></tr>";
					} else {
					 let resultHtml = "";
					document.getElementById("newGroup").value = result;
					document.getElementById("checkGroup").value = false;
			} else {
				alert('request에 뭔가 문제가 있어요.');
			}
		}
	}
	
	// POST로 요청
	httpRequest.open('POST', "friends/addGroup", true);
	httpRequest.setRequestHeader(header, token);
	httpRequest.setRequestHeader('Content-type',
			'application/x-www-form-urlencoded');
	httpRequest.send("group=" + group);
};

