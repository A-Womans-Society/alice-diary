function check() {
   const form = document.getElementById('form');

   if(!form.comName.value.trim()){
      alert("커뮤니티 이름을 입력해주세요!");
      form.comName.focus();
      return false;
   } else {
      return true;
}
};

function deleteCommunity(comNum) {
   console.log(comNum);
   let token = $("meta[name='_csrf']").attr("content");
   let header = $("meta[name='_csrf_header']").attr("content");
   let httpRequest = new XMLHttpRequest();
         
   httpRequest.onreadystatechange = function() {
      if (httpRequest.readyState === XMLHttpRequest.DONE) {
         if (httpRequest.status === 200) {
            alert('커뮤니티가 삭제되었습니다!');
            location.href = "/AliceDiary/community/create";

         } else {
            alert('request에 뭔가 문제가 있어요.');
         }
      }
   };
   
   //POST로 요청
   httpRequest.open('POST', "/AliceDiary/community/communitydelete", true);
   httpRequest.setRequestHeader(header, token);
   httpRequest.setRequestHeader('Content-type',
         'application/x-www-form-urlencoded');
   httpRequest.send("comNum="+comNum); 
}