function loadFriends(memberId) {
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	let httpRequest = new XMLHttpRequest();
	
	let param = "userId="+memberId+"&searchFriend=" + document.getElementById("searchFriend").value;	
	console.log(param);
	
	httpRequest.onreadystatechange = function() {
		if (httpRequest.readyState === XMLHttpRequest.DONE) {
			if (httpRequest.status === 200) {
				if (httpRequest.response) {					
					let result = JSON.parse(httpRequest.response);

					if (result.length == 0 || document.getElementById("searchFriend").value == "" ) {
						document.getElementById("friendsBody").innerHTML="<tr><td colspan='9' class='text-center'>친구를 다시 검색해주세요.</td></tr>";
					} else {
						let resultHtml = "";
						for (let idx in result) {
							resultHtml += "<tr><td><input type='checkbox' name='selected' value="+result[idx].id+"></td><td>"+result[idx].id
							+"</td><td>"+result[idx].name+"</td><td>"+result[idx].groupName+"</td></tr>";
						//	"</td><td><button type='button' onclick='openModalInfo("+result[idx].id+")'>정보</button></td></tr>";
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
	httpRequest.open('POST', "searchFriend", true);
	httpRequest.setRequestHeader(header, token);
	httpRequest.setRequestHeader('Content-type',
			'application/x-www-form-urlencoded');
	httpRequest.send(param);
};

function selectFriends() {

	let searchList = document.getElementsByName("selected").length;

  	if(searchList == 0){
  		document.getElementById("selectedFriends").innerHTML="<tr><td colspan='9' class='text-center'>친구를 초대해주세요.</td></tr>";
  	} else {	
                let resultHtml = "";
    	for (let i=0; i<searchList; i++) {
        	if (document.getElementsByName("selected")[i].checked == true){
          		console.log("도착");
      
						resultHtml += "<tr><td>"+document.getElementsByName("selected")[i].value+"</td></tr>";
						console.log(resultHtml);
						document.getElementById("selectedFriends").innerHTML=resultHtml;					
				
			}
		} 		
            	alert("초대회원에 추가되었습니다! 나의 커뮤니티를 생성해주세요!");
	}	
}

function check() {
	let form = document.getElementById('form');
	let selectBox=document.getElementById("comMembers").value;
	
	if(!form.comName.value.trim()){
		alert("커뮤니티 이름을 입력해주세요!");
		form.comName.focus();
		return false;
	} else if(selectBox.length == 0) {
		alert("친구를 초대하세요");
		return false;
	} else {
		return true;
	}
}