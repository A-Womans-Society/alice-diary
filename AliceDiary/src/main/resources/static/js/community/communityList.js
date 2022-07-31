function resign(comNum,userId) {
   let httpRequest = new XMLHttpRequest();
   if(confirm('탈퇴를 취소할 수 없습니다. 해당 커뮤니티를 정말 탈퇴하시겠습니까?')){
	   let token = $("meta[name='_csrf']").attr("content");
	   let header = $("meta[name='_csrf_header']").attr("content");
	   let param = "comNum="+comNum+"&userId="+userId;   
	   
	   //POST로 요청
	   httpRequest.open('POST', "/AliceDiary/community/resign", true);
	   httpRequest.setRequestHeader(header, token);
	   httpRequest.setRequestHeader('Content-type',
	         'application/x-www-form-urlencoded');
	   httpRequest.send(param); 
	   
   } 
   
   httpRequest.onreadystatechange = function() {
      if (httpRequest.readyState === XMLHttpRequest.DONE) {
         if (httpRequest.status === 200) {
			location.href = "/AliceDiary/community/checkExist";
         } else {
            alert('request에 뭔가 문제가 있어요.');
         }
      }
   };

}