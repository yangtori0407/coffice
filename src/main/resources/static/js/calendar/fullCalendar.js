const kind = document.getElementById("kind")

var calendarEl = document.getElementById("calendar")
var calendar = new FullCalendar.Calendar(calendarEl, {
    themeSystem: 'bootstrap4',
    locale: 'ko',
    customButtons: {
        addButton: {
            text: '+',
            click: function() {
                $("#exampleModal").modal("show")
            }
        }
    },
    nowIndicator: true,
    headerToolbar: {
        left:'prevYear,prev,next,nextYear today editButton',
        center: 'title',
        right: 'addButton dayGridMonth,근태,listWeek'
    },
    // views: {
    //     근태: {
    //         type: 'timeGrid',
    //         duration: { days: 7},
    //         slotMinTime: '07:00:00',
    //         slotMaxTime: '22:00:00'
    //     }
    // },
    initialDate: Date.now(),
    navLinks: false, // can click day/week names to navigate views
    editable: false,
    dayMaxEvents: true, // allow "more" link when too many events
    fixedWeekCount: false,
    dayCellContent: function (arg) {
    const { date } = arg;
    return date.getDate();
    },
    dateClick: function(e) {
    console.log(e)
    },
    eventClick: function(e) {
        console.log(e.event)
    },
    eventDrop: function(e) {
    console.log("EventDrop", e.event.start)
        let t = new Date(e.event.start)
    console.log(t.getHours())
    },
    eventResize: function(e) {
    console.log("EventResize", e.event.start)
    let t = new Date(e.event.start)
    console.log(t.getHours())
    }
});

for(let i = 0; i < 3; i++) {
    let list = JSON.parse(localStorage.getItem("list"+i))

        for(a of list) {

            let event = {
                title: a.dateName,
                start: a.locdate.toString(),
                allDay: true,
                color: '#378006'
            }
            calendar.addEvent(event);
        }
}

if(kind.innerText.trim() == '일정 관리') {
    let event = {
                title: 'test',
                start: '20250605',
                allDay: true,
                color: '#378006'
            }
            calendar.addEvent(event);
}

if(kind.innerText.trim() == '휴가 관리') {
    let event = {
                title: 'test',
                start: '20250605',
                allDay: true,
                color: '#000000'
            }
            calendar.addEvent(event);
}

calendar.render();