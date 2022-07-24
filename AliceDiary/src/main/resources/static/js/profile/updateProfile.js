
	function PreviewImage() {
		var preview = new FileReader();
		preview.onload = function(e) {
			document.getElementById("profileImage").src = e.target.result;
		};
		preview.readAsDataURL(document.getElementById("file").files[0]);
	};
	
	function updateCancle() {
		if(!confirm("수정을 취소하시겠습니까?")) {
			return;
		}else {
			alert("수정이 취소되었습니다.");
			var contextPath = $('#contextPathHolder').attr('data-contextPath') ? $('#contextPathHolder').attr('data-contextPath') : '';
			location = contextPath + "/AliceDiary/member/"+id;
		}
	}
	