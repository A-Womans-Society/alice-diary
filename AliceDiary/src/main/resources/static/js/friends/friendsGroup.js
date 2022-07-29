document.addEventListener('keydown', function(event) {
	if (event.keyCode === 13) {
		event.preventDefault();
	}
	;
}, true);

function changeGroup(creatorId) {	
	$("#changeGroup").modal('show');
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

function deleteFriend(user) {
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	let httpRequest = new XMLHttpRequest();
	
	let addeeId = document.getElementById("friendId").value;
	httpRequest.onreadystatechange = function() {
		if (httpRequest.readyState === XMLHttpRequest.DONE) {
			if (httpRequest.status === 200) {
				alert("친구가 삭제되었습니다!");
				$(location).attr('href', '/AliceDiary/friends');
				//location.href = "/AliceDiary/friends";
				//location.reload();
				} else{
						alert('request에 뭔가 문제가 있어요.');
				}
			}
		}

	// POST로 요청
	httpRequest.open('POST', "../deleteFriend", true);
	httpRequest.setRequestHeader(header, token);
	httpRequest.setRequestHeader('Content-type',
			'application/x-www-form-urlencoded');
	httpRequest.send("addeeId=" + addeeId);
};
