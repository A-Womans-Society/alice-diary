
var jsonData = eventsList.replace(/&quot;/g, '"');
var jsonData = jsonData.replace(/\\"/g, '');
if (jsonData.length == 14){
	var jsonConvertList = [];
} else {
	var jsonConvertList = eval(JSON.stringify(JSON.parse(jsonData).items))
}
document.addEventListener('DOMContentLoaded', function() {
	var calendarEl = document.getElementById('calendar');
	var calendar = new FullCalendar.Calendar(calendarEl, {
	    timeZone: 'UTC',
	    
		// set calendar view type
		initialView : 'dayGridMonth',

		// set row height
		height: "100%",
//		aspectRatio: 2.5,
		
		// limit events per day
		dayMaxEvents : 2,

		events : jsonConvertList,
		dateClick : function(info) {
			// popup modal
			document.getElementById("startDate").value = info.dateStr;
			document.getElementById("endDate").value = info.dateStr;
			document.getElementById("endDate").setAttribute('min', info.dateStr);
			$("#addEvent").modal();
		},
		eventClick : function(info) {
			makeRequest(info.event.id);

		}

	});
	calendar.render();
});




function makeRequest(id) {
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	let httpRequest = new XMLHttpRequest();
	
    httpRequest.onreadystatechange = function(){
	    if (httpRequest.readyState === XMLHttpRequest.DONE) {
	    	if (httpRequest.status === 200) {
				var result = JSON.parse(httpRequest.response);
				console.log(result);
				document.getElementById("detailMemo").value = result.memo;
				document.getElementById("detailStartDate").value = result.start;
				document.getElementById("detailEndDate").value = result.end;
				document.getElementById("detailContent").value = result.title;
				document.getElementById("detailLocation").value = result.location;
				document.getElementById("detailAlarm").value = result.alarm;

				if (result.memberList){
					document.getElementById("detailMemberList").value = result.memberList;
				} else {
					document.getElementById("detailMemberList").value = "";
				}

				if(result.publicity){
					document.getElementById("detailPublic").checked = true;
					document.getElementById("detailPrivate").checked = false;
				} else {
					document.getElementById("detailPublic").checked = false;
					document.getElementById("detailPrivate").checked = true;
				}
				document.getElementById("colorbtn").style.background = result.backgroundColor;
				document.getElementById("eventId").value = result.id;
				$("#showEvent").modal();
			} else {
				alert('request에 뭔가 문제가 있어요.');
			}
		}
	};

    //POST로 요청
    httpRequest.open('POST', "showDetail", true);
    httpRequest.setRequestHeader(header,token);
    httpRequest.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    httpRequest.send("id=" + id);
}

function deleteEvent() {
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	let httpRequest = new XMLHttpRequest();
	
    httpRequest.onreadystatechange = function(){
	    if (httpRequest.readyState === XMLHttpRequest.DONE) {
	    	if (httpRequest.status === 200) {
				window.location = "alice";
			} else {
				alert('request에 뭔가 문제가 있어요.');
			}
		}
	};

    //POST로 요청
    httpRequest.open('POST', "deleteEvent", true);
    httpRequest.setRequestHeader(header,token);
    httpRequest.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    httpRequest.send("id=" + document.getElementById("eventId").value);
}


function checkNull(){
	if (document.getElementById("content").value.length == 0){
		alert("내용을 입력해주세요.");
		return false;
	} 
	return true;
}
