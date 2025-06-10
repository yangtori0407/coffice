const kind = document.getElementById("kind")

var calendarEl = document.getElementById("calendar")
var calendar = new FullCalendar.Calendar(calendarEl, {
    themeSystem: 'bootstrap4',
    locale: 'ko',
    timeZone: 'UTC',
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
        left:'prevYear,prev,next,nextYear today',
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

if(kind.innerText.trim() == '일정') {
    let event = {
        title: 'test',
        start: '2025-06-09',
        allDay: true,
        color: '#378006'
    }
    calendar.addEvent(event);
}

if(kind.innerText.trim() == '휴가') {
    let event = {
        title: 'test',
        start: '2025-06-12T15:30:00',
        end: '2025-06-15T11:30:00'
    }
    calendar.addEvent(event);
}

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
    console.log(type)
    let detail = document.getElementById("details")
    console.log(detail.value)
    let sDate = document.getElementById("sDate")
    let sTime = document.getElementById("sTime")
    console.log(sDate.value)
    console.log(sTime.value)
    let eDate = document.getElementById("eDate")
    let eTime = document.getElementById("eTime")
    console.log(eDate.value)
    console.log(eTime.value)
    if(check.checked){
        let rType = document.querySelector('input[name="radioOptions"]:checked').value;
        console.log(rType)
        let sRepeat = document.getElementById("sRepeat")
        let eRepeat = document.getElementById("eRepeat")
        console.log(sRepeat.value)
        console.log(eRepeat.value)
    }

    $("#exampleModal").modal("hide")
})