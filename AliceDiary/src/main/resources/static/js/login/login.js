$("input:checkbox").click(checkedChange);
	function checkedChange() {
		if ($(this).prop("checked")) {
			$("label[for=" + this.id + "]").text("동의되었습니다.");
			$("label[for=" + this.id + "]").css("color", "blue");
		} else {
			$("label[for=" + this.id + "]").text("동의");
			$("label[for=" + this.id + "]").css("color", "black");
		}
	}
	
	$(document).ready(function() {
		$("#selectAll").click(function() {
			if ($("#selectAll").is(":checked"))
				$("input[name=chk]").prop("checked", true);
			else
				$("input[name=chk]").prop("checked", false);
		});

		$("input[name=chk]").click(function() {
			var total = $("input[name=chk]").length;
			var checked = $("input[name=chk]:checked").length;

			if (total != checked)
				$("#selectAll").prop("checked", false);
			else
				$("#selectAll").prop("checked", true);
		});
	});

	function next() {
		if (!$("#selectAll").is(":checked")) {
			alert("약관에 동의해주세요.");
			return false;
		} else {
			document.location.href = "./register";
		}
	}

	function regCancle() {
		if (!confirm("가입을 취소하시겠습니까?")) {
			return;
		} else {
			alert("가입이 취소되었습니다.");
			var contextPath = $('#contextPathHolder').attr('data-contextPath') ? $(
					'#contextPathHolder').attr('data-contextPath')
					: '';
			location = contextPath + "/";
		}
	}
	
	
//쿠키 이용하여 ID 저장하기	
	$(document).ready(function() {
		var key = getCookie("key"); //저장된 쿠키 값을 가져와서 ID 칸에 넣어준다. 없으면 공백으로 들어감
		$("#userid").val(key);
		
		if($("#userid").val() != "") { //그 전에 ID를 저장해서 처음 페이지 로딩 시, 입력 칸에 저장된 ID가 표시된 상태라면
			$("#checkId").attr("checked",true); //ID저장하기를 체크 상태로 두기
		}

		
		$("#checkId").change(function() { //체크박스에 변화가 있다면
			if($("#checkId").is(":checked")) { //ID저장하기를 체크했을 때
				setCookie("key",$("#userid").val(),7); //7일동안 쿠키 보관
			}else {		//ID저장하기 체크 해제 시
				deleteCookie("key");  //쿠키 삭제
			}
		});
		
		$("#userid").keyup(function() { //ID저장하기를 체크한 상태에서 ID를 입력한 경우에도 쿠키 저장
			if($("#checkId").is(":checked")) {
				setCookie("key",$("#userid").val(),7); 
			}
		});		
	});
		
		//쿠키 저장하기
		//saveId 함수에서 넘겨준 시간을 현재 시간과 비교해서 쿠키를 생성하고 지워주는 역할
		function setCookie(cookieName, value, exdays) {
			var exdate = new Date();
			exdate.setDate(exdate.getDate() + exdays);
			var cookieValue = escape(value) + ((exdays == null) ? "" : ";expires=" + exdate.toGMTString());
			document.cookie = cookieName + "=" + cookieValue;
		}
		
		//쿠키 삭제
		function deleteCookie(cookieName) {
		var expireDate = new Date();
		expireDate.setDate(expireDate.getDate() - 1);
		document.cookie = cookieName + "= " + "; expires="
				+ expireDate.toGMTString();
		}
		
		// 쿠키 가져오기
		function getCookie(cookieName) {
			cookieName = cookieName + '=';
			var cookieData = document.cookie;
			var start = cookieData.indexOf(cookieName);
			var cookieValue = '';
			if (start != -1) { // 쿠키가 존재하면
				start += cookieName.length;
				var end = cookieData.indexOf(';', start);
				if (end == -1) // 쿠키 값의 마지막 위치 인덱스 번호 설정 
					end = cookieData.length;
	                console.log("end위치  : " + end);
				cookieValue = cookieData.substring(start, end);
			}
			return unescape(cookieValue);
		}