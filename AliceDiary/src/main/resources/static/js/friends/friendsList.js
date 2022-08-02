document.addEventListener('keydown', function(event) {
    if (event.keyCode === 13) {
       event.preventDefault();
    };
 }, true);
 
 function openModal() {
    document.getElementById("searchName").value = "";
    document.getElementById("memberInfo").innerHTML = "<div class='d-flex justify-content-center align-items-center h-100'>친구를 검색하세요.</div>";
    document.getElementById("userName").innerText = "";
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
 };
 
 
 function loadMembers() {
     let token = $("meta[name='_csrf']").attr("content");
     let header = $("meta[name='_csrf_header']").attr("content");
     let httpRequest = new XMLHttpRequest();
     let name = document.getElementById("searchName").value;
     let check = document.getElementById("checkSearch").value;
     let userName = document.getElementById("userName").value;
     console.log(name);
     if (name.length == 0) {
         alert('친구의 닉네임을 입력하세요!');
     } else if (name == userName) {
         alert('자신을 친구 추가할 수 없습니다!');
     } else {
         // POST로 요청
         httpRequest.open('POST', "/AliceDiary/friends/searchMember", true);
         httpRequest.setRequestHeader(header, token);
         httpRequest.setRequestHeader('Content-type',
             'application/x-www-form-urlencoded');
         httpRequest.send("name=" + name);
     }
     
     httpRequest.onreadystatechange = function () {
         if (httpRequest.readyState === XMLHttpRequest.DONE) {
             if (httpRequest.status === 200) {
                 if (httpRequest.response.length != 0) {
                     console.log(httpRequest.response);
                     let result = JSON.parse(httpRequest.response);
                     console.log(result.status);
                     let mbti = result.mbti;
                     let target = result.name;
                     if (target === 'noFriend') {
                     	document.getElementById("memberInfo").innerHTML = "<div class='d-flex justify-content-center align-items-center h-100'>일치하는 회원이 없습니다.</div>";
                    	document.getElementById("checkSearch").value = false;
                     } else {
                     	
                     	let row = document.createElement('div');
						row.setAttribute('class', 'row h-100');
						
						let col1 = document.createElement('div');
						col1.setAttribute('class', 'col-6 h-100 d-flex justify-content-center align-items-center');
						col1.innerHTML = "<img src='/AliceDiary/upload/profile/" + result.profileImg + "' class='w-75 h-75'>";
                     	document.getElementById("checkSearch").value = true;
                         			 
                     	let col2 = document.createElement('div');
                     	col2.setAttribute('class', 'col-6 d-flex justify-content-center align-items-center');
                     	
                     	let table = document.createElement('table');
                     	table.setAttribute('class', 'table');
                         	
                     	if (mbti === null) {
	                         mbti = "비밀-★";

                         	table.innerHTML = "<tr><th>닉네임</th><td>"+target+"</td></tr><tr><th>MBTI</th><td>"+mbti+"</td></tr>";
                        
	                     } else {
                      	
	                     	table.innerHTML = "<tr><th>닉네임</th><td>"+target+"</td></tr><tr><th>MBTI</th><td>"+mbti+"</td></tr>";
                         }
                         col2.appendChild(table);
                         
                         row.appendChild(col1);
                         row.appendChild(col2);
                         
                         document.getElementById("memberInfo").innerHTML = "";
                         document.getElementById("memberInfo").appendChild(row);
                         
                    }
                 } else {
                     document.getElementById("memberInfo").innerHTML = "<div class='d-flex justify-content-center align-items-center h-100'>일치하는 회원이 없습니다.</div>";
                     document.getElementById("checkSearch").value = false;
                 }
             } else {
                 alert('request에 뭔가 문제가 있어요.');
             }
         }
     };
 };
 
 
 
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
                 console.log(result);
                if (result.length == 0) {
                   document.getElementById("friendsBody").innerHTML="<tr><td colspan='9' class='text-center'>친구를 추가해주세요.</td></tr>";
                } else {
                   let resultHtml = "";
                   for (let idx in result) {
                      resultHtml += "<tr><td>"+(Number(idx)+1)+"</td><td>"+result[idx].name+"</td><td>"+result[idx].mobile+"</td><td>"+result[idx].birth+"</td><td>"+result[idx].gender+"</td><td>"+result[idx].email+"</td><td>"+result[idx].groupName+"</td><td><a href='/AliceDiary/friends/friendInfo/"+result[idx].id+"'><input type='button' class='btn btn-dark' value='정보'></a></td></tr>";
                   }
                   document.getElementById("friendsBody").innerHTML=resultHtml;               
                }
             }   
          } else {
             alert('request에 뭔가 문제가 있어요.');
          }
       }
    };
    
    // POST로 요청
    httpRequest.open('POST', "friends/searchFriend", true);
    httpRequest.setRequestHeader(header, token);
    httpRequest.setRequestHeader('Content-type',
          'application/x-www-form-urlencoded');
    httpRequest.send("friends=" + friends);
 };