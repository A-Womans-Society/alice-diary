function openModalGroup() {
	//document.getElementById("searchId").innerText = "";
	//document.getElementById("memberInfo").innerHTML = "";
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
	
	let groupName = document.getElementById("newGroup").value;
	console.log(groupName);
		
	httpRequest.onreadystatechange = function() {
		if (httpRequest.readyState === XMLHttpRequest.DONE) {
			if (httpRequest.status === 200) {
				location.href = "/AliceDiary/friends/friendsGrouplist";
				location.reload();
				} else{
						alert('request에 뭔가 문제가 있어요.');
				}
			}
		}

	// POST로 요청
	httpRequest.open('POST', "./addGroup", true);
	httpRequest.setRequestHeader(header, token);
	httpRequest.setRequestHeader('Content-type',
			'application/x-www-form-urlencoded');
	httpRequest.send("groupName=" + groupName);
};

