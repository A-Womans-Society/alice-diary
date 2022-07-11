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

		events : eventsList,
		dateClick : function(info) {
			// info.dateStr : 클릭한 날짜
			// info.jsEvent.pageX/Y : 클릭한 날짜의 화면상 위치
			// info.view.type: 현재 view 타입 정보(dayGridMonth)
			// alert('Clicked on: ' + info.dateStr);
			//alert('Coordinates: ' + info.jsEvent.pageX + ',' + info.jsEvent.pageY);
			// alert('Current view: ' + info.view.type);
			
			// change the day's background color just for fun
			// info.dayEl.style.backgroundColor = 'red';
			
			// popup modal
			document.getElementById("startDate").value = info.dateStr;
			document.getElementById("endDate").value = info.dateStr;
			$("#addEvent").modal();
		},
		eventClick : function(info) {
			alert('Event: ' + info.event.title);
			alert('Coordinates: ' + info.jsEvent.pageX + ','
					+ info.jsEvent.pageY);
			alert('View: ' + info.view.type);

			// change the border color just for fun
			info.el.style.borderColor = 'red';
		}

	});
	calendar.render();
});

// checkbox check one element
function checkOnlyOne(element) {
	const checkboxes 
		= document.getElementsByName("public");
  
	checkboxes.forEach((cb) => {
		cb.checked = false;
	})
	element.checked = true;
}

