// 일정부분
var scheduleEl = document.getElementById("schedule")
var schedule = new FullCalendar.Calendar(scheduleEl, {
    themeSystem: 'bootstrap4',
    contentHeight: 300,
    locale: 'ko',
    nowIndicator: true,
    initialView: 'dayGridDay',
    headerToolbar: {
        left: 'prev,next',
        center: 'title',
        right: '' // user can switch between the two
    }
});

schedule.render();


// 달력부분
var calendarEl = document.getElementById("calendar")
var calendar = new FullCalendar.Calendar(calendarEl, {
    themeSystem: 'bootstrap4',
    contentHeight: 300,
    locale: 'ko',
    nowIndicator: true,
    headerToolbar: {
        left:'today',
        center: 'title',
        right: 'prev,next'
    },
    dayCellContent: function (arg) {
        const { date } = arg;
        return date.getDate();
    },
    initialDate: Date.now(),
    navLinks: false, // can click day/week names to navigate views
    editable: false,
    dayMaxEvents: false, // allow "more" link when too many events
    fixedWeekCount: false,
});


fetch("http://localhost/events/getHolidays")
.then(r=>r.json())
.then(r=>{
    console.log(r)
    for(a of r) {
        let event = {
            title: a.dateName,
            start: a.locdate.toString(),
            allDay: true,
            color: '#ee0000'
        }
        schedule.addEvent(event);
        calendar.addEvent(event);
    }
})

calendar.render();