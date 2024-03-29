/**
 * 어드민 회원목록 자스
 */

function replySubmit(postNum, memberId) {
   let token = $("meta[name='_csrf']").attr("content");
   let header = $("meta[name='_csrf_header']").attr("content");
   let httpRequest = new XMLHttpRequest();
   let param = "memberId="+memberId+"&postNum="+postNum+"&content=" + document.getElementById("replyContent").value;   
   console.log(postNum);
    httpRequest.onreadystatechange = function(){
       if (httpRequest.readyState === XMLHttpRequest.DONE) {
          if (httpRequest.status === 200) {
             let result = JSON.parse(httpRequest.response);
            
            let tagArea = document.getElementById('replyList');
            let newReply = document.createElement('ul');
            newReply.setAttribute('id', 'parentRepContentTable'+result.replyNum);
            newReply.setAttribute('class', 'comments pt-2');
                                    
            if (result.profileImg == 'default'){
               newReply.innerHTML="<img src='/AliceDiary/upload/Alice.png' class='avatar' alt=''>";
            } else {
               newReply.innerHTML="<img src='/AliceDiary/upload/"+ result.profileImg+"' class='avatar' alt=''>";
            }
            
            let postComments = document.createElement('div');
            postComments.setAttribute('class', 'post-comments');
            let meta = document.createElement('p');
            meta.setAttribute('class','meta');
                                                         
            let span1 = document.createElement('span');
            span1.innerHTML = "&nbsp;<span style='color:black;'>"+ result.id +"</span>&nbsp;<span>"+result.repDate+"</span>";
                
            let span2 = document.createElement('span');
            let btn1 = document.createElement('span');
            btn1.innerHTML = "<button type='button' class='btn btn-sm' onclick='deleteParent("+result.replyNum+")'>삭제</button>";                  
            let btn2 = document.createElement('span');
            btn2.innerHTML = "<button type='button' class='btn btn-sm' onclick='showReplyBox(replyReplyBox"+result.replyNum+")'>답글</button>";

                span2.appendChild(btn2);
                span2.appendChild(btn1);

                meta.appendChild(span1);
                meta.appendChild(span2);

                let content = document.createElement('p');
                content.setAttribute('class', 'm-0 pb-2');
                content.style.fontSize = 'small';
                let contentSpan = document.createElement('span');
                contentSpan.style.wordBreak = 'break-all';
                contentSpan.innerText = result.repContent;
                
                content.appendChild(contentSpan);

                postComments.appendChild(meta);
                postComments.appendChild(content);



            
            let replyBox = document.createElement('li');
            replyBox.setAttribute('id', "replyReplyBox"+result.replyNum);
            replyBox.style.display = 'none';

            let replyBoxtd1 = document.createElement('span');
            let replyBoxInput = document.createElement('input');
            replyBoxInput.placeholder = "댓글을 입력해주세요";
            replyBoxInput.setAttribute('id', 'replyReplyContent'+result.replyNum);

            let replyBoxtd2 = document.createElement('span');
            let replyBtn = document.createElement('button');
            replyBtn.setAttribute('onclick', "replyReply("+result.postNum+","+result.replyNum+", \""+result.id+"\", replyReplyBox" +
                     result.replyNum+", replyReplyContent"+result.replyNum+", parentRepContentTable"+result.replyNum+")");
            replyBtn.innerText="등록";
            replyBoxtd2.appendChild(replyBtn);
            replyBoxtd1.appendChild(replyBoxInput);
            
            replyBox.appendChild(replyBoxtd1);
            replyBox.appendChild(replyBoxtd2);
            
            newReply.appendChild(postComments);
                newReply.appendChild(replyBox);

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
   
function showReplyBox(parentReplyBox){
   console.log(parentReplyBox);

   if(parentReplyBox.style.display=="none") {
      parentReplyBox.style.display = "block";
   } else {
       parentReplyBox.style.display = "none";
   }
}
      
function replyReply(postNum, parentRepNum, memberId, replyReplyBox, replyReplyContent, parentRepContentTable) {
   console.log(postNum);
   console.log(parentRepNum);
   console.log(memberId);
   let token = $("meta[name='_csrf']").attr("content");
   let header = $("meta[name='_csrf_header']").attr("content");
   let httpRequest = new XMLHttpRequest();
   let param = "memberId="+memberId+"&postNum="+postNum+
   "&parentRepNum="+parentRepNum+"&content="+replyReplyContent.value;   

    httpRequest.onreadystatechange = function(){
       if (httpRequest.readyState === XMLHttpRequest.DONE) {
          if (httpRequest.status === 200) {
                let result = JSON.parse(httpRequest.response);

            let tagArea = parentRepContentTable;

            let newReply = document.createElement('ul');
            newReply.setAttribute('id', 'childRepContentTable'+result.replyNum);
                newReply.setAttribute('class', 'childComments');
              
                if (result.profileImg == 'default'){
               newReply.innerHTML="<img src='/AliceDiary/upload/Alice.png' class='avatar' alt=''>";
            } else {
               newReply.innerHTML="<img src='/AliceDiary/upload/"+ result.profileImg+"' class='avatar' alt=''>";
            }
         
            
                let postComments = document.createElement('div');
            postComments.setAttribute('class', 'post-comments');
            let meta = document.createElement('p');
            meta.setAttribute('class','meta');

                let span1 = document.createElement('span');
            span1.innerHTML = "&nbsp;<span style='color:black;'>"+ result.id +"</span>&nbsp;<span>"+result.repDate+"</span>";

            let span2 = document.createElement('span');
            let btn1 = document.createElement('span');
            btn1.innerHTML = "<button type='button' class='btn btn-sm' onclick='deleteChild("+result.replyNum+")'>삭제</button>";   
                
                span2.appendChild(btn1);

                meta.appendChild(span1);
                meta.appendChild(span2);

                let content = document.createElement('p');
                content.setAttribute('class', 'm-0 pb-2');
                content.style.fontSize = 'small';
                let contentSpan = document.createElement('span');
                contentSpan.style.wordBreak = 'break-all';
                contentSpan.innerText = result.repContent;

                content.appendChild(contentSpan);

                postComments.appendChild(meta);
                postComments.appendChild(content);

            newReply.appendChild(postComments);
                tagArea.after(newReply);
            
            replyReplyBox.style.display = "none";
            replyReplyContent.value = "";
            
           alert('댓글이 달렸습니다!');
         } else {
            alert('request에 뭔가 문제가 있어요.');
         }
      }
   };

    //POST로 요청
    httpRequest.open('POST', "replyreply", true);
    httpRequest.setRequestHeader(header,token);
    httpRequest.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    httpRequest.send(param);

}

function deleteChild(childNum) {
   console.log(childNum);
   let token = $("meta[name='_csrf']").attr("content");
   let header = $("meta[name='_csrf_header']").attr("content");
   let httpRequest = new XMLHttpRequest();

   httpRequest.onreadystatechange = function() {
      if (httpRequest.readyState === XMLHttpRequest.DONE) {
         if (httpRequest.status === 200) {
         console.log("childRepContentTable"+childNum);
            let removeTr = document.getElementById("childRepContentTable"+childNum);
            console.log(removeTr);
            removeTr.parentNode.removeChild(removeTr);
            alert('댓글이 삭제되었습니다!');

         } else {
            alert('request에 뭔가 문제가 있어요.');
         }
      }
   };

   //POST로 요청
   httpRequest.open('POST', "./deletereply", true);
   httpRequest.setRequestHeader(header, token);
   httpRequest.setRequestHeader('Content-type',
         'application/x-www-form-urlencoded');
   httpRequest.send("num=" + childNum); // 요게 문제였습니다!!
}

function deleteParent(pNum) {
   console.log(pNum);
   let token = $("meta[name='_csrf']").attr("content");
   let header = $("meta[name='_csrf_header']").attr("content");
   let httpRequest = new XMLHttpRequest();

   httpRequest.onreadystatechange = function() {
      if (httpRequest.readyState === XMLHttpRequest.DONE) {
         if (httpRequest.status === 200) {
            let removeTr = document.getElementById("parentRepContentTable"+pNum);
            removeTr.innerHTML = "<span colspan='4'>삭제된 댓글입니다.</span>";

            alert('댓글이 삭제되었습니다!');

         } else {
            alert('request에 뭔가 문제가 있어요.');
         }
      }
   };

   //POST로 요청
   httpRequest.open('POST', "./deletereply", true);
   httpRequest.setRequestHeader(header, token);
   httpRequest.setRequestHeader('Content-type',
         'application/x-www-form-urlencoded');
   httpRequest.send("num=" + pNum); // 요게 문제였습니다!!
}

function openModalPost(postNum, userId){
   let token = $("meta[name='_csrf']").attr("content");
   let header = $("meta[name='_csrf_header']").attr("content");
   let httpRequest = new XMLHttpRequest();

   httpRequest.onreadystatechange = function() {
      if (httpRequest.readyState === XMLHttpRequest.DONE) {
         if (httpRequest.status === 200) {
            if (httpRequest.response == 0) {
               $('#postReportModal').modal('show');   
            } else {         
               alert("이미 신고한 게시물입니다.");
            }
            
         } else {
            alert('request에 뭔가 문제가 있어요.');
         }
      }
   };

   //POST로 요청
   httpRequest.open('POST', "./postreportcheck", true);
   httpRequest.setRequestHeader(header, token);
   httpRequest.setRequestHeader('Content-type',
         'application/x-www-form-urlencoded');
   httpRequest.send("postNum=" + postNum+"&userId="+userId); 
   
}

function postReport(userId, postNum, reportReason, content) {
   console.log(postNum);
   console.log(userId);
   
   let token = $("meta[name='_csrf']").attr("content");
   let header = $("meta[name='_csrf_header']").attr("content");
   let httpRequest = new XMLHttpRequest();
   let param = "userId="+userId+"&postNum="+postNum+
   "&reportReason="+document.querySelector('input[name="reportReasons"]:checked').value
   +"&content="+document.getElementById("reportContent").value;
   console.log(param);
   
    httpRequest.onreadystatechange = function(){
       if (httpRequest.readyState === XMLHttpRequest.DONE) {
          if (httpRequest.status === 200) {
                let result = JSON.parse(httpRequest.response);
            console.log(result);
            document.getElementById("reportContent").value = "";
              alert('게시글이 신고되었습니다.');
           
            $("#postReportModal").modal('hide');
         } else {
            alert('request에 뭔가 문제가 있어요.');
         }
      }
   };

    //POST로 요청
    httpRequest.open('POST', "reportpost", true);
    httpRequest.setRequestHeader(header,token);
    httpRequest.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    httpRequest.send(param);

}

function openModalReply(replyNum, userId){
   document.getElementById('replyTarget').value = replyNum;
   
   let token = $("meta[name='_csrf']").attr("content");
   let header = $("meta[name='_csrf_header']").attr("content");
   let httpRequest = new XMLHttpRequest();

   httpRequest.onreadystatechange = function() {
      if (httpRequest.readyState === XMLHttpRequest.DONE) {
         if (httpRequest.status === 200) {
            if (httpRequest.response == 0) {
               $('#replyReportModal').modal('show');      
            } else {         
               alert("이미 신고한 댓글입니다.");
            }
            
         } else {
            alert('request에 뭔가 문제가 있어요.');
         }
      }
   };
   
   //POST로 요청
   httpRequest.open('POST', "./replyreportcheck", true);
   httpRequest.setRequestHeader(header, token);
   httpRequest.setRequestHeader('Content-type',
         'application/x-www-form-urlencoded');
   httpRequest.send("replyNum=" + replyNum+"&userId="+userId); // 요게 문제였습니다!!
   
}

function replyReport(userId, reportReason, content) {
   console.log(userId);
   let replyTarget = document.getElementById('replyTarget').value;
   console.log(replyTarget);
   let token = $("meta[name='_csrf']").attr("content");
   let header = $("meta[name='_csrf_header']").attr("content");
   let httpRequest = new XMLHttpRequest();
   let param = "userId="+userId+"&replyNum="+replyTarget+
   "&reportReason="+document.querySelector('input[name="reportReasons"]:checked').value
   +"&content="+document.getElementById("reportRepContent").value;
   console.log(param);
   
    httpRequest.onreadystatechange = function(){
       if (httpRequest.readyState === XMLHttpRequest.DONE) {
          if (httpRequest.status === 200) {
                let result = JSON.parse(httpRequest.response);
            console.log(result);
            document.getElementById("reportRepContent").value = "";
              alert('댓글이 신고되었습니다.');
           
            $("#replyReportModal").modal('hide');
         } else {
            alert('request에 뭔가 문제가 있어요.');
         }
      }
   };

    //POST로 요청
    httpRequest.open('POST', "reportreply", true);
    httpRequest.setRequestHeader(header,token);
    httpRequest.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    httpRequest.send(param);

}

function openModalMsg(msgTo, msgFrom){
   document.getElementById('replyMsgTo').value = msgTo;
   $('#replyMsg').modal('show');   
         
};

function replyMsg(msgFrom, content) {
   let replyMsgTo = document.getElementById('replyMsgTo').value;
   console.log("msgFrom:"+msgFrom);
   console.log("replyMsgTo:"+replyMsgTo);
   let token = $("meta[name='_csrf']").attr("content");
   let header = $("meta[name='_csrf_header']").attr("content");
   let httpRequest = new XMLHttpRequest();
   let param = "messageFromId="+msgFrom+"&messageToId="+replyMsgTo
   +"&content="+document.getElementById("replyMsgContent").value;
   console.log(param);
   
    httpRequest.onreadystatechange = function(){
       if (httpRequest.readyState === XMLHttpRequest.DONE) {
          if (httpRequest.status === 200) {
    
            document.getElementById("reportRepContent").value = "";
              alert('쪽지를 보냈습니다!');
           
            $("#replyMsg").modal('hide');
         } else {
            alert('request에 뭔가 문제가 있어요.');
         }
      }
   };

    //POST로 요청
    httpRequest.open('POST', "replymsg", true);
    httpRequest.setRequestHeader(header,token);
    httpRequest.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    httpRequest.send(param);

}