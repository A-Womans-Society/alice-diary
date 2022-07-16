/**
 * 쪽지함 리스트 자스
 */
$('#exampleModal').on('show.bs.modal', function (event) {
  var button = $(event.relatedTarget) // Button that triggered the modal
  var recipient = button.data('whatever') // Extract info from data-* attributes
  // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
  // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
  var modal = $(this)
//   modal.find('.modal-title').text('쪽지보내기'/)
//   modal.find('.modal-body input').val('recipient-name')
})	


function deleteMessage(fnum, tnum) {
	if (!confirm('정말 이 쪽지함을 삭제하시겠습니까? 돌이킬 수 없습니다!')) {
		return false;
	}
	console.log(fnum);
	console.log(tnum);
	$.ajax({
		type: 'delete',
		url: "./"+fnum+"/"+tnum
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