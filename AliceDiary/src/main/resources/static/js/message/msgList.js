/**
 * 쪽지함 목록 자스
 */

/* 쪽지함 목록에서 개별 쪽지함 삭제 */
function deleteMessage(fromId, toId) {
   if (!confirm('이 쪽지함의 모든 쪽지가 사라집니다! 삭제하시겠습니까?')) {
      return false;
   }
   let token = $("meta[name='_csrf']").attr("content");
   let header = $("meta[name='_csrf_header']").attr("content");
   let httpRequest = new XMLHttpRequest();
   let detail = document.getElementById('detail').value;
   
   let param = "fromId=" + fromId + "&toId=" + toId;
   console.log(param);
    httpRequest.onreadystatechange = function(){
       if (httpRequest.readyState === XMLHttpRequest.DONE) {
          if (httpRequest.status === 200) {
             let result = httpRequest.response;
             console.log(fromId);
            console.log(toId);
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

    if (detail == 'true') {
    	httpRequest.open('POST', "../delete", true);
    } else {
	    httpRequest.open('POST', "./delete", true);
    }
    httpRequest.setRequestHeader(header,token);
    httpRequest.setRequestHeader('Content-type', 'application/x-www-form-urlencoded; charset=UTF-8');
    httpRequest.send(param);
};

/* 쪽지 전송 모달 열기 */
 function openModal(){
    $('#messageModal').modal();
 }

/* 쪽지 전송 시 아이디 체크 */
$("#msgForm").ready(function(){
    let formCheck = function() { // 폼 검증
      $('.selectpicker').selectpicker();
      let form = document.getElementById("msgForm");
      let toIdChk = document.getElementById("messageToId");
      let fromId = document.getElementById("fromId").value;
      if(form.messageToId.value=="") {
        alert("받는 분의 아이디를 입력해 주세요.");
        form.messageToId.focus();
        return false;
      } else if(form.content.value=="") {
        alert("쪽지내용을 입력해 주세요.");
        form.content.focus();
        return false;
      }else if (form.messageToId.value == fromId) {
         alert("본인에게 쪽지를 보낼 수는 없습니다!");
         form.messageToId.focus();
         return false;
      } else {
         return true;
     }
    }
    $("#writeBtn").on("click", function(){ // 쪽지 보내기 클릭 시
      if (formCheck()) {
         console.log(formCheck());
         console.log("뭐지");
      let form = $("#msgForm");
         //form.attr("action", "");
         form.attr("method", "post");
        form.submit();
    }
       
    });
  });