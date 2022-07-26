/**
 * 어드민 회원목록 자스
 */

//		const page_elements = document.getElementsByClassName("page-link");
//		Array.from(page_elements).forEach(function(element) {
//		    element.addEventListener('click', function() {
//		        document.getElementById('page').value = this.dataset.page;
//		        document.getElementById('searchForm').submit();
//		    });
//		});
window.onload = function(){
	const btn_search = document.getElementById("btn_search");
	btn_search.addEventListener('click', function() {
		console.log("!!");
	    document.getElementById('keyword').value = document.getElementById('keyword').value;
	    //document.getElementById('page').value = 0;  // 검색버튼을 클릭할 경우 0페이지부터 조회한다.
	    document.getElementById('searchForm').submit();
	});
}

//		function changeSelect() {
//		 	var select = document.getElementById("type");
//			var selectValue = select.options[select.selectedIndex].value;
//			if(selectValue=='status') {
//				document.getElementById("selectStatus").style.display = 'block';
//				document.getElementById("keyword").style.display = 'none';
//			} else {
//				document.getElementById("selectStatus").style.display = 'none';
//				document.getElementById("keyword").style.display = 'block';
//			}
//		}

/* 건의사항 내용보기 모달 열기 */
 function openModal(suggestion){
 let s = suggestion;
    $('#suggestionModal').modal();
 }