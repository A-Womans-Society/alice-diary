/**
 * 쪽지함 내부페이지 자스
 */
	function deleteMessage(fnum, tnum) {
		if (!confirm('정말 이 쪽지함을 나가시겠습니까? 돌이킬 수 없습니다!')) {
			return false;
		}
		console.log(fnum);
		console.log(tnum);
		$.ajax({
			type : 'delete',
			url : "../" + fnum + "/" + tnum
		}).done(function(result) {
			console.log(result);
			if (result == 0) {
				alert("쪽지함 삭제에 실패했습니다! 다시 시도해주세요😥");
			} else if (result == 1) {
				alert("쪽지함이 성공적으로 삭제되었습니다!");
				location.reload();
				return false;
			} else {
				console.log("에러에러에러");
			}
		}).fail(function(error) {
			console.log(error);
		});
	}