function deleteConfirm(comNum) {
   if (!confirm('커뮤니티를 정말 삭제하시겠습니까?')) {
      return false;
   }
   console.log(comNum);
   let token = $("meta[name='_csrf']").attr("content");
   let header = $("meta[name='_csrf_header']").attr("content");
   let httpRequest = new XMLHttpRequest();

   httpRequest.onreadystatechange = function() {
      if (httpRequest.readyState === XMLHttpRequest.DONE) {
         if (httpRequest.status === 200) {
            alert('커뮤니티가 삭제되었습니다!');
            location.href = "../list";

         } else {
            alert('request에 뭔가 문제가 있어요.');
         }
      }
   };

   //POST로 요청
   httpRequest.open('POST', "/AliceDiary/admin/community/delete", true);
   httpRequest.setRequestHeader(header, token);
   httpRequest.setRequestHeader('Content-type',
         'application/x-www-form-urlencoded');
   httpRequest.send("comNum=" + comNum); 
}