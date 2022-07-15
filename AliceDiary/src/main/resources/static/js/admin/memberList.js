/**
 * 어드민 회원목록 자스
 */
function deleteMember(num) {
	if (!confirm('정말 이 회원을 내보내기하시겠습니까?')) {
		return false;
	}
	console.log(num);
	$.ajax({
		type: 'delete',
		url: './member/'+num
	}).done(function(result) {
		console.log(result);
		if (result == 0) {
			alert("회원 탈퇴처리에 실패했습니다! 다시 시도해주세요😥");
		} else if (result == 1) {
			alert("회원이 탈퇴상태로 변경되었습니다!");
			location.reload();
			return false;
		} else {
			console.log("에러에러에러");
		}
	}).fail(function(error) {
		console.log(error);
	});
}

function returnMember(num) {
	if (!confirm('정말 이 회원상태를 복구하시겠습니까?')) {
		return false;
	}
	console.log(num);
	$.ajax({
		type: 'patch',
		url: './member/'+num
	}).done(function(result) {
		console.log(result);
		if (result == 0) {
			alert("회원 복구처리에 실패했습니다! 다시 시도해주세요😥");
		} else if (result == 1) {
			alert("회원이 활동상태로 변경되었습니다!");
			location.reload();
			return false;
		} else {
			console.log("에러에러에러");
		}
	}).fail(function(error) {
		console.log(error);
	});
}