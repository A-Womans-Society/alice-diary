function check() {
	const form = document.getElementById('form');

	if(!form.title.value.trim()){
		alert("제목을 입력해주세요!");
		form.title.focus();
		return false;
	} else if(!form.content.value.trim()) {
		alert("내용을 입력해주세요!");
		form.content.focus();
		return false;
	} else {
		return true;
}
}

function fileCnt(obj){
    let maxCnt = 2;   // 첨부파일 최대 개수
    let nowCnt = obj.files.length;  // 현재 선택된 첨부파일 개수

    // 첨부파일 개수 확인
    if (nowCnt > maxCnt) {
        alert("첨부파일은 최대 " + maxCnt + "개 까지 첨부 가능합니다.");
         document.querySelector("input[type=file]").value = "";
        return false;
        
    } else {
        return true;

    }
       
}
		