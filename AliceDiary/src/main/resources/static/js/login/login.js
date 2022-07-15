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