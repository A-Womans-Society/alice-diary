/**
 * 쪽지함 내부페이지 자스
 */
 
/* 쪽지함 내부에서 개별 쪽지함 삭제 */	
function deleteMessage(fromId, toId) {
	if (!confirm('이 쪽지함의 모든 쪽지가 사라집니다! 삭제하시겠습니까?')) {
		return false;
	}
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	let httpRequest = new XMLHttpRequest();
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
				location.href="../";
				return false;
				} else {
					alert('request에 뭔가 문제가 있어요.');
				}
			}
		}
	};

    httpRequest.open('POST', "./delete", true);
    httpRequest.setRequestHeader(header,token);
    httpRequest.setRequestHeader('Content-type', 'application/x-www-form-urlencoded; charset=UTF-8');
    httpRequest.send(param);
};
