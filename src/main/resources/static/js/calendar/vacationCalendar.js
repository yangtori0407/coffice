const kind = document.getElementById("kind")
let flag = true;
let cookie = document.cookie
let userId = cookie.substring(cookie.indexOf("=")+1)

var calendarEl = document.getElementById("calendar")
var calendar = new FullCalendar.Calendar(calendarEl, {
    themeSystem: 'bootstrap4',
    locale: 'ko',
    customButtons: {
        addButton: {
            text: '휴가 신청',
            click:  function() {
                        $("#exampleModal").modal("show")

                    }
        }
    },
    nowIndicator: true,
    headerToolbar: {
        left:'prevYear,prev,next,nextYear today',
        center: 'title',
        right: 'addButton'
    },
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
        sDate.value = e.dateStr
        show()
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



fetch("http://localhost/events/getHolidays")
.then(r=>r.json())
.then(r=>{
    for(a of r) {
        let event = {
            title: a.dateName,
            start: a.locdate.toString(),
            allDay: true,
            color: '#ee0000',
            editable: false
        }
        calendar.addEvent(event);
    }
})

fetch("http://localhost/events/getDepsUsers", {
    method: "post",
    headers: {
        'Content-Type': 'application/json'
    },
    body: JSON.stringify({
        "userId" : userId
    })
})
.then(r=>r.json())
.then(r=>{
    console.log(r)
    let accept = document.getElementById("accept")
    for(a of r) {
        let opt = document.createElement("option")
        opt.value = a.userId
        opt.innerText = a.position + " " + a.name
        accept.appendChild(opt)
    }
})

calendar.render();


// $("#exampleModal").modal("hide")
function show() {
    $("#exampleModal").modal("show")
}