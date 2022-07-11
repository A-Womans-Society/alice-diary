var jsonData = eventsList.replace(/&quot;/g, '"');
console.log(jsonData);
var jsonData = jsonData.replace(/\\"/g, '');
console.log(jsonData);

var jsonConvertList = JSON.parse(jsonData);
console.log(jsonConvertList);

console.log(eval(JSON.stringify(jsonConvertList.items)));
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
