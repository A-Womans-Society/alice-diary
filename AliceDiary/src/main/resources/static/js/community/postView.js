function replySubmit(postNum, memberId) {
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	let httpRequest = new XMLHttpRequest();
	let param = "memberId="+memberId+"&postNum="+postNum+"&content=" + document.getElementById("replyContent").value;	
	console.log(postNum);
    httpRequest.onreadystatechange = function(){
	    if (httpRequest.readyState === XMLHttpRequest.DONE) {
	    	if (httpRequest.status === 200) {
	    		let result = JSON.parse(httpRequest.response);
				console.log(result);
				let url = window.location.href;

				let tagArea = document.getElementById('replyTable');
				let newReply = document.createElement('tr');
				newReply.setAttribute('id', 'parentRepContentTable'+result.replyNum);
				let memberId = document.createElement('td');
				memberId.innerText = result.id;
				let repDate = document.createElement('td');
				repDate.innerText = result.repDate;
				let content = document.createElement('td');
				content.innerText = result.repContent;
				let btn = document.createElement('td');
				btn.innerHTML = "<button type='button' onclick='showReplyBox(replyReplyBox"+result.replyNum+")'>답글</button>";
				
				newReply.appendChild(memberId);
				newReply.appendChild(repDate);
				newReply.appendChild(content);
				newReply.appendChild(btn);
				
				let replyBox = document.createElement('tr');
				replyBox.setAttribute('id', "replyReplyBox"+result.replyNum);
				replyBox.style.display = 'none';

				let replyBoxtd1 = document.createElement('td');
				let replyBoxInput = document.createElement('input');
				replyBoxInput.placeholder = "댓글을 입력해주세요";
				replyBoxInput.setAttribute('id', 'replyReplyContent'+result.replyNum);

				let replyBoxtd2 = document.createElement('td');
				let replyBtn = document.createElement('button');
				replyBtn.setAttribute('onclick', "replyReply("+result.postNum+","+result.replyNum+", \""+result.id+"\", replyReplyBox" +
							result.replyNum+", replyReplyContent"+result.replyNum+", parentRepContentTable"+result.replyNum+")");
				replyBtn.innerText="등록";
				replyBoxtd2.appendChild(replyBtn);
				replyBoxtd1.appendChild(replyBoxInput);
				replyBox.appendChild(replyBoxtd1);
				replyBox.appendChild(replyBoxtd2);

				tagArea.appendChild(newReply);
				tagArea.appendChild(replyBox);
				
				document.getElementById('replyContent').value = "";
	     	alert('댓글이 달렸습니다!');
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


function deleteConfirm(num) {
	console.log(num);
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	let httpRequest = new XMLHttpRequest();

	httpRequest.onreadystatechange = function() {
		if (httpRequest.readyState === XMLHttpRequest.DONE) {
			if (httpRequest.status === 200) {
				alert('글이 삭제되었습니다!');
				location.href = "./list";

			} else {
				alert('request에 뭔가 문제가 있어요.');
			}
		}
	};

	//POST로 요청
	httpRequest.open('POST', "./delete", true);
	httpRequest.setRequestHeader(header, token);
	httpRequest.setRequestHeader('Content-type',
			'application/x-www-form-urlencoded');
	httpRequest.send("num=" + num); // 요게 문제였습니다!!
}
	
function showReplyBox(parentReplyBox){
	console.log(parentReplyBox);

	if(parentReplyBox.style.display=="none") {
		parentReplyBox.style.display = "block";
	} else {
 		parentReplyBox.style.display = "none";
	}
}
		
function replyReply(postNum, parentRepNum, memberId, replyReplyBox, replyReplyContent, parentRepContentTable) {
//	$('#replyList').load();
	console.log(postNum);
	console.log(parentRepNum);
	console.log(memberId);
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	let httpRequest = new XMLHttpRequest();
	let param = "memberId="+memberId+"&postNum="+postNum+
	"&parentRepNum="+parentRepNum+"&content="+replyReplyContent.value;	
	console.log(param);
	console.log(parentRepContentTable);
    httpRequest.onreadystatechange = function(){
	    if (httpRequest.readyState === XMLHttpRequest.DONE) {
	    	if (httpRequest.status === 200) {
	       		let result = JSON.parse(httpRequest.response);
				console.log(result);
				let url = window.location.href;

				let tagArea = parentRepContentTable;
				console.log(tagArea);
				let newReply = document.createElement('tr');
				let img = document.createElement('td');
				img.innerHTML = "<img src='../img/replyreply.png' width='20' height='20'>";
				let memberId = document.createElement('td');
				memberId.innerText = result.id;
				let repDate = document.createElement('td');
				repDate.innerText = result.repDate;
				let content = document.createElement('td');
				content.innerText = result.repContent;
				
				console.log(newReply);
				newReply.appendChild(img);				
				newReply.appendChild(memberId);
				newReply.appendChild(repDate);
				newReply.appendChild(content);
				tagArea.after(newReply);
				
				replyReplyBox.style.display = "none";
				replyReplyContent.value = "";
				
	     	alert('댓글이 달렸습니다!');
			} else {
				alert('request에 뭔가 문제가 있어요.');
			}
		}
	};

    //POST로 요청
    httpRequest.open('POST', "replyreply", true);
    httpRequest.setRequestHeader(header,token);
    httpRequest.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    httpRequest.send(param);

}