/**
 * 쪽지함 내부페이지 자스
 */
   
   function deleteMessage(fnum, tnum) {
   let token = $("meta[name='_csrf']").attr("content");
   let header = $("meta[name='_csrf_header']").attr("content");
   let httpRequest = new XMLHttpRequest();
   let param = "fnum=" + fnum + "&tnum=" + tnum;
   console.log(param);
    httpRequest.onreadystatechange = function(){
       if (httpRequest.readyState === XMLHttpRequest.DONE) {
          if (httpRequest.status === 200) {
             let result = httpRequest.response;
             console.log(fnum);
            console.log(tnum);
            if (result == 0) {
            alert("쪽지함 삭제에 실패했습니다! 다시 시도해주세요😥");
         } else if (result == 1) {
            alert("쪽지함이 성공적으로 삭제되었습니다!");
            location.reload();
            return false;
            } else {
               alert('request에 뭔가 문제가 있어요.');
            }
         }
      }
   };

    //DELETE로 요청
    httpRequest.open('GET', "../delete/" + fnum + "/" + tnum, true);
    httpRequest.setRequestHeader(header,token);
    httpRequest.setRequestHeader('Content-type', 'application/x-www-form-urlencoded; charset=UTF-8');
    httpRequest.send(param);
};