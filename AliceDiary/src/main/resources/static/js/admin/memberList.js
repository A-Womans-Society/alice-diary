/**
 * 어드민 회원목록 자스
 */

function deleteMember(num) {
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	let httpRequest = new XMLHttpRequest();
	
    httpRequest.onreadystatechange = function(){
	    if (httpRequest.readyState === XMLHttpRequest.DONE) {
	    	if (httpRequest.status === 200) {
	    		let result = httpRequest.response;
				if (result == 0) {
					alert("회원 탈퇴처리에 실패했습니다! 다시 시도해주세요😥");
				} else if (result == 1) {
					alert("회원이 탈퇴상태로 변경되었습니다!");
					location.reload();
					return false;
				} else {
					alert('request에 뭔가 문제가 있어요.');
				}
			}
		}
	};

    //POST로 요청
    httpRequest.open('DELETE', './member/'+num, true);
    httpRequest.setRequestHeader(header,token);
    httpRequest.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    httpRequest.send("num=" + num);
};

function returnMember(num) {
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	let httpRequest = new XMLHttpRequest();
	
    httpRequest.onreadystatechange = function(){
	    if (httpRequest.readyState === XMLHttpRequest.DONE) {
	    	if (httpRequest.status === 200) {
	    		let result = httpRequest.response;
				if (result == 0) {
				alert("회원 복구처리에 실패했습니다! 다시 시도해주세요😥");
			} else if (result == 1) {
				alert("회원이 활동상태로 변경되었습니다!");
				location.reload();
				return false;
				} else {
					alert('request에 뭔가 문제가 있어요.');
				}
			}
		}
	};

    //POST로 요청
    httpRequest.open('PATCH', './member/'+num, true);
    httpRequest.setRequestHeader(header,token);
    httpRequest.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    httpRequest.send("num=" + num);
};

function changeSelect() {
 	var select = document.getElementById("type");
	var selectValue = select.options[select.selectedIndex].value;
	if(selectValue=='status') {
		document.getElementById("selectStatus").style.display = 'block';
		document.getElementById("keyword").style.display = 'none';
	} else {
		document.getElementById("selectStatus").style.display = 'none';
		document.getElementById("keyword").style.display = 'block';
	}
}

