var jsonData = eventsList.replace(/&quot;/g, '"');
var jsonData = jsonData.replace(/\\"/g, '');
var jsonConvertList = JSON.parse(jsonData);

document.addEventListener('DOMContentLoaded', function() {
	var calendarEl = document.getElementById('calendar');
	var calendar = new FullCalendar.Calendar(calendarEl, {
	    timeZone: 'UTC',
	    
		// set calendar view type
		initialView : 'dayGridMonth',

		// set row height
		height: '100%',
		aspectRatio: 2.5,
		
		// limit events per day
		dayMaxEvents : 2,

		events : eval(JSON.stringify(jsonConvertList.items)),
		dateClick : function(info) {
			// popup modal
			document.getElementById("startDate").value = info.dateStr;
			document.getElementById("endDate").value = info.dateStr;
			$("#addEvent").modal();
		},
		eventClick : function(info) {
//			alert('Event: ' + info.event.title);
//			alert('Coordinates: ' + info.jsEvent.pageX + ','
//					+ info.jsEvent.pageY);
//			alert('View: ' + info.view.type);

			// change the border color just for fun
//			info.el.style.borderColor = 'red';
			makeRequest(info.event.id);

		}

	});
	calendar.render();
});





function makeRequest(id) {
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	let httpRequest = new XMLHttpRequest();
	
	if(!httpRequest) {
		alert('XMLHTTP 인스턴스를 만들 수가 없어요 ㅠㅠ');
	    return false;
	}
    httpRequest.onreadystatechange = function(){
	    if (httpRequest.readyState === XMLHttpRequest.DONE) {
	    	if (httpRequest.status === 200) {
	//	    	document.getElementById("idCheckResult").innerHTML = httpRequest.responseText;
				 var result = JSON.parse(httpRequest.response);
				console.log(result);
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


// checkbox check one element
function checkOnlyOne(element) {
	const checkboxes 
		= document.getElementsByName("public");
  
	checkboxes.forEach((cb) => {
		cb.checked = false;
	})
	element.checked = true;
}
