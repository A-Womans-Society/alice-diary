function replySubmit(postNum) {
	let memberId = "TESTER";
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	let httpRequest = new XMLHttpRequest();
	let param = "memberId="+memberId+"&postNum="+postNum+"&content="+document.getElementById("replyContent").value;	

    httpRequest.onreadystatechange = function(){
	    if (httpRequest.readyState === XMLHttpRequest.DONE) {
	    	if (httpRequest.status === 200) {
				var result = JSON.parse(httpRequest.response);
				console.log(result);
				document.getElementById("detailMemo").value = result.memo;
				document.getElementById("detailStartDate").value = result.start;
				document.getElementById("detailEndDate").value = result.end;
				document.getElementById("detailContent").value = result.title;
				document.getElementById("detailLocation").value = result.location;
				document.getElementById("detailAlarm").value = result.alarm;

				if (result.memberList){
					document.getElementById("detailMemberList").value = result.memberList;
				} else {
					document.getElementById("detailMemberList").value = "";
				}

				if(result.publicity){
					document.getElementById("detailPublic").checked = true;
					document.getElementById("detailPrivate").checked = false;
				} else {
					document.getElementById("detailPublic").checked = false;
					document.getElementById("detailPrivate").checked = true;
				}
				document.getElementById("colorbtn").style.background = result.backgroundColor;
				document.getElementById("eventId").value = result.id;
				$("#showEvent").modal();
			} else {
				alert('request에 뭔가 문제가 있어요.');
			}
		}
	};

    //POST로 요청
    httpRequest.open('POST', "reply", true);
    httpRequest.setRequestHeader(header,token);
    httpRequest.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    httpRequest.send(param);
}
