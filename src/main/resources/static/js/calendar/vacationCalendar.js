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
        },
        listButton: {
            text: '신청 목록',
            click: function() {
                $("#listModal").modal("show")
            }
        }
    },
    nowIndicator: true,
    headerToolbar: {
        left:'prevYear,prev,next,nextYear today',
        center: 'title',
        right: 'listButton addButton'
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

const send = document.getElementById("send")
send.addEventListener("click", ()=>{
    let vType = document.getElementById("vType")
    let sDate = document.getElementById("sDate")
    let sTime = document.getElementById("sTime")
    let eDate = document.getElementById("eDate")
    let eTime = document.getElementById("eTime")
    let accept = document.getElementById("accept")
    
    let params = new FormData
    params.append("userId", userId)
    params.append("type", vType.value)
    params.append("startTime", sDate.value+sTime.value)
    params.append("endTime", eDate.value+eTime.value)
    params.append("approvalAuthority", accept.value)
    
    fetch("http://localhost/events/vacation/apply", {
        method: "post",
        body: params
    })
    .then(r=>r.text)
    .then(r=>{
        console.log(r)
    })
})

const chooseOne = document.getElementById("chooseOne")
chooseOne.addEventListener("click", ()=>{
    $("#vacationDetailModal").modal("show")
})

const undo = document.getElementById("undo")
undo.addEventListener("click", ()=>{
    $("#listModal").modal("show")
})

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