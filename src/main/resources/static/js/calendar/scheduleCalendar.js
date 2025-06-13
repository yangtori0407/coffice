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
        },
        수정: {
            text: 'editable',
            click: function() {
                calendar.setOption('editable', flag)

                flag = !flag
            }
        }
    },
    nowIndicator: true,
    headerToolbar: {
        left:'prevYear,prev,next,nextYear today',
        center: 'title',
        right: 'addButton 수정 dayGridMonth,listWeek'
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

        let type = document.querySelector(`input[name="detailResultOptions"][value="${e.event.extendedProps.type}"]`);
        type.checked = true;
        let detailResult = document.getElementById("detailResult")
        detailResult.innerText = e.event.title
        let rsDate = document.getElementById("rsDate")
        rsDate.value = e.event.startStr.slice(0, 10)
        let rsTime = document.getElementById("rsTime")
        rsTime.value = e.event.startStr.slice(11, 16)
        let reDate = document.getElementById("reDate")
        reDate.value = e.event.endStr.slice(0, 10)
        let reTime = document.getElementById("reTime")
        reTime.value = e.event.endStr.slice(11, 16)
        
        let dis = document.querySelectorAll("input[disabled], textarea[disabled], select[disabled]");
        let change = document.getElementById("change")
        change.addEventListener("click", (e)=>{
            for(a of dis) {
                a.disabled = false;
            }
        })
    
        $("#detailModal").modal("show")

        $('#detailModal').on('hidden.bs.modal', function () {
            for(a of dis) {
                a.disabled = true;
            }
        })
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

fetch("http://localhost/events/getRepeatSchedules")
.then(r=>r.json())
.then(r=>{
    for(a of r) {
        console.log(a.startTime)
        let event = {
            groupId: a.repeatId,
            title: a.detail,
            color: '#378006',
            extendedProps: {
                type: a.scheduleType
            },
            rrule: {
                freq: a.repeatType,
                dtstart: a.startTime,
                until: a.repeatEnd,
                count: a.repeatCount
            },
            duration: calculateDurationObject(a.startTime, a.endTime)
        }
        calendar.addEvent(event);
    }
})

fetch("http://localhost/events/getSchedules")
.then(r=>r.json())
.then(r=>{
    for(a of r) {
        if(a.scheduleType == 'group') {
            let event = {
                id: a.scheduleId,
                title: a.detail,
                start: a.startTime,
                end: a.endTime,
                color: '#378006',
                editable: false,
                extendedProps: {
                    type: a.scheduleType
                }
            }
            calendar.addEvent(event);
        }else {
            let event = {
                id: a.scheduleId,
                title: a.detail,
                start: a.startTime,
                end: a.endTime,
                color: '#378006',
                extendedProps: {
                    type: a.scheduleType
                }
            }
            calendar.addEvent(event);
        }
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
    params.append("startTime", sDate.value+sTime.value)
    params.append("endTime", eDate.value+eTime.value)

    if(check.checked){
        let rType = document.querySelector('input[name="radioOptions"]:checked').value;
        let eRepeat = document.getElementById("eRepeat")
        let rCount = document.getElementById("repeatCount")
        params.append("repeatType", rType)
        params.append("repeatEnd", eRepeat.value+" 23:59:59")
        params.append("repeatCount", rCount.value)
    }

    fetch("schedule/add", {
        method: "post",
        body: params
    })
    .then(r=>{
        console.log(r)
        location.reload()
    })

    $("#exampleModal").modal("hide")
})

function show() {
    $("#exampleModal").modal("show")
}

function calculateDurationObject(startStr, endStr) {
  const start = new Date(startStr);
  const end = new Date(endStr);
  const ms = end - start;
  console.log(ms)

  const seconds = Math.floor(ms / 1000);
  const minutes = Math.floor((seconds % 3600) / 60);
  const hours = Math.floor((seconds / 3600) % 24);
  const days = Math.floor(seconds / (3600 * 24));

  const duration = {};
  if (days > 0) duration.days = days;
  if (hours > 0) duration.hours = hours;
  if (minutes > 0) duration.minutes = minutes;
  return duration;
}