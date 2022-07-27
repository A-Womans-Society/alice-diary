function deleteFile(num, postNum) {
   console.log("fileNum=" + num + "&postNum="+ postNum);
   let token = $("meta[name='_csrf']").attr("content");
   let header = $("meta[name='_csrf_header']").attr("content");
   let httpRequest = new XMLHttpRequest();
   let param = "num="+num+"&postNum="+postNum;   
   
   console.log(param);
   
   httpRequest.onreadystatechange = function() {
      if (httpRequest.readyState === XMLHttpRequest.DONE) {
         if (httpRequest.status === 200) {
         console.log("!!!" + httpRequest.response);
         let result = JSON.parse(httpRequest.response);
            alert('파일이 삭제되었습니다!');
            location.reload();
      
         } else {
            alert('request에 뭔가 문제가 있어요.');
         }
   }   
   };

   //POST로 요청
   httpRequest.open('POST', "/AliceDiary/community/put/filedelete", true);
   httpRequest.setRequestHeader(header, token);
   httpRequest.setRequestHeader('Content-type',
         'application/x-www-form-urlencoded');
   httpRequest.send(param);
}
         
function fileCnt(obj){
    let maxCnt = 2;   // 첨부파일 최대 개수
    let selectCnt = obj.files.length;  // 현재 선택된 첨부파일 개수
   
    let addedCnt = document.getElementsByClassName('attachedFile').length;
    let totalCnt = selectCnt + addedCnt;
    console.log(addedCnt);

    // 첨부파일 개수 확인
    if (totalCnt > maxCnt) {
        alert("첨부파일은 총" + maxCnt + "개 까지 첨부 가능합니다.");
         document.querySelector("input[type=file]").value = "";
        return false;
        
    } else {
        return true;

    }
       
}