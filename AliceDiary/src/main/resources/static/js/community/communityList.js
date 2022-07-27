function resign(comNum,userId) {
   console.log(userId);
   let token = $("meta[name='_csrf']").attr("content");
   let header = $("meta[name='_csrf_header']").attr("content");
   let httpRequest = new XMLHttpRequest();
   let param = "comNum="+comNum+"&userId="+userId;   
   
   httpRequest.onreadystatechange = function() {
      if (httpRequest.readyState === XMLHttpRequest.DONE) {
         if (httpRequest.status === 200) {
               if(confirm('탈퇴를 취소할 수 없습니다. 해당 커뮤니티를 정말 탈퇴하시겠습니까?') == true){
                  location.href = "/AliceDiary/community/create";
               } else {
                     return false;         
         }
         } else {
            alert('request에 뭔가 문제가 있어요.');
         }
      }
   };

   //POST로 요청
   httpRequest.open('POST', "/AliceDiary/community/resign", true);
   httpRequest.setRequestHeader(header, token);
   httpRequest.setRequestHeader('Content-type',
         'application/x-www-form-urlencoded');
   httpRequest.send(param); 
}