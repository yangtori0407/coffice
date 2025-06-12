const kind = document.getElementById("kind")
let flag = true;

var calendarEl = document.getElementById("calendar")
var calendar = new FullCalendar.Calendar(calendarEl, {
    themeSystem: 'bootstrap4',
    locale: 'ko',
    customButtons: {
        addButton: {
            text: '+',
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

if(kind.innerText.trim() == '일정') {
    let event = {
        title: 'test',
        start: '2025-06-09',
        allDay: true,
        color: '#378006',
        source: 'detail'
    }
    calendar.addEvent(event);
}

if(kind.innerText.trim() == '휴가') {
    let event = {
        title: 'test',
        start: '2025-06-12T15:30:00'
    }
    calendar.addEvent(event);
}

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

fetch("http://localhost/events/getSchedule")
.then(r=>r.json())
.then(r=>{
    for(a of r) {
        let event = {
            title: a.detail,
            start: a.startTime,
            end: a.endTime,
            color: '#378006'
        }
        calendar.addEvent(event);
    }
})


calendar.render();

// 반복 일정
const repeat = document.getElementById("repeat-condition")
const check = document.getElementById("gridCheck")

check.addEventListener("click", (e)=>{
    if(e.target.checked) {
        repeat.style.display = 'block';
    }else {
        repeat.style.display = 'none';
    }
})

const send = document.getElementById("send")
send.addEventListener("click", ()=>{
    let type = document.querySelector('input[name="inlineRadioOptions"]:checked').value;
    let detail = document.getElementById("details")
    let sDate = document.getElementById("sDate")
    let sTime = document.getElementById("sTime")
    let eDate = document.getElementById("eDate")
    let eTime = document.getElementById("eTime")

    let params = new FormData()
    params.append("scheduleType", type)
    params.append("detail", detail.value)
    params.append("startTime", sDate.value+" "+sTime.value)
    params.append("endTime", eDate.value+" "+eTime.value)

    if(check.checked){
        let rType = document.querySelector('input[name="radioOptions"]:checked').value;
        let sRepeat = document.getElementById("sRepeat")
        let eRepeat = document.getElementById("eRepeat")
        params.append("repeatType", rType)
        params.append("repeatStart", sRepeat.value)
        params.append("repeatEnd", eRepeat.value)
    }

    fetch("schedule/add", {
        method: "post",
        body: params
    })
    .then(r=>{
        console.log(r)
    })

    $("#exampleModal").modal("hide")
})

function show() {
    $("#exampleModal").modal("show")
}