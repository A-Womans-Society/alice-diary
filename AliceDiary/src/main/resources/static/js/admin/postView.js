function replySubmit(postNum, memberId) {
	console.log(postNum);
	console.log(memberId);
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	let httpRequest = new XMLHttpRequest();
	let param = "memberId="+memberId+"&postNum="+postNum+"&content="+document.getElementById("replyContent").value;	

	
    httpRequest.onreadystatechange = function(){
	    if (httpRequest.readyState === XMLHttpRequest.DONE) {
	    	if (httpRequest.status === 200) {
	    		let result = JSON.parse(httpRequest.response);
				console.log(result);
				let url = window.location.href;

				let tagArea = document.getElementById('replyTable');
				let newReply = document.createElement('tr');
				let memberId = document.createElement('td');
				memberId.innerText = result.id;
				
				let repDate = document.createElement('td');
				repDate.innerText = result.repDate;
				
				let content = document.createElement('td');
				content.innerText = result.repContent;
				
				let btn = document.createElement('td');
				btn.innerHTML = "<button type='button'>답글쓰기</button>";
				
				newReply.appendChild(memberId);
				newReply.appendChild(repDate);
				newReply.appendChild(content);
				newReply.appendChild(btn);
				tagArea.appendChild(newReply);
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
