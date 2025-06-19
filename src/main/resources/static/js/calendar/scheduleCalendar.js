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
        let repeatScheduleDiv = document.getElementById("repeatScheduleDiv")
        // console.log(e.event.id)
        // console.log(e.event.groupId)
        console.log(e.event.groupId)
        $("#detailModal").modal("show")
        let scheduleId = e.event.id
        let repeatCheck = document.getElementById("repeatCheck")
        let changeAll = document.getElementById("changeAll")
        let rType = document.querySelector(`input[name="radioOptionsResult"][value="${e.event.extendedProps.repeatType}"]`);
        let eRepeat = document.getElementById("reRepeat")
        let rCount = document.getElementById("resultCount")
        let type = document.querySelector(`input[name="detailResultOptions"][value="${e.event.extendedProps.type}"]`);
        let detailResult = document.getElementById("detailResult")
        let rsDate = document.getElementById("rsDate")
        let rsTime = document.getElementById("rsTime")
        let reDate = document.getElementById("reDate")
        let reTime = document.getElementById("reTime")
        let dis = document.querySelectorAll("input[disabled], textarea[disabled], select[disabled]");
        let change = document.getElementById("change")
        let saveChange = document.getElementById("saveChange")
        let deleteSchedule = document.getElementById("deleteSchedule")
        let startStr = e.event.startStr
        let groupId = e.event.groupId
        let startTime = e.event.extendedProps.startDate
        let endTime = e.event.extendedProps.endDate

        type.checked = true;
        detailResult.innerText = e.event.title
        let rstartDate = e.event.startStr.slice(0, 10)
        let rstartTime = e.event.startStr.slice(11, 16)
        let rendDate = e.event.endStr.slice(0, 10)
        let rendTime = e.event.endStr.slice(11, 16)
        rsDate.value = rstartDate
        rsTime.value = rstartTime
        reDate.value = rendDate
        reTime.value = rendTime
        if(e.event.groupId != "") {
            repeatScheduleDiv.setAttribute("style", "display: block;")
            rType.checked = true;
            if(e.event.extendedProps.repeatEnd != null) {
                eRepeat.value = e.event.extendedProps.repeatEnd.slice(0, 10)
            }else {
                rCount.value = e.event.extendedProps.repeatCount
            }
            repeatCheck.addEventListener("change", (e)=>{
                if(e.target.checked) {
                    changeAll.setAttribute("style", "display: block;")
                    rsDate.value = startTime.slice(0, 10)
                    rsTime.value = startTime.slice(11, 16)
                    reDate.value = endTime.slice(0, 10)
                    reTime.value = endTime.slice(11, 16)
                }else {
                    changeAll.setAttribute("style", "display: none;")
                    rsDate.value = rstartDate
                    rsTime.value = rstartTime
                    reDate.value = rendDate
                    reTime.value = rendTime
                }
            })
        }
        
        change.addEventListener("click", ()=>{
            for(a of dis) {
                a.disabled = false;
            }
        })
        
        saveChange.addEventListener("click", ()=>{
            let checked = document.querySelector('input[name="detailResultOptions"]:checked').value;
            let params = new FormData
            params.append("scheduleId", scheduleId)
            params.append("scheduleType", checked)
            params.append("detail", detailResult.value.trim())
            params.append("startTime", rsDate.value+rsTime.value)
            params.append("endTime", reDate.value+reTime.value)
            
            if(groupId != "" && !repeatCheck.checked) {
                console.log(groupId)
                params.append("exception", true)
                params.append("exceptions[0].repeatId", groupId)
                params.append("exceptions[0].exceptionDate", startStr)
                params.append("exceptions[0].exceptionType", "override")
            }else if(groupId != "" && repeatCheck.checked) {
                let rType = document.querySelector('input[name="radioOptionsResult"]:checked').value;
                let eRepeat = document.getElementById("reRepeat")
                let rCount = document.getElementById("resultCount")
                params.append("repeatId", groupId)
                params.append("repeatType", rType)
                if(eRepeat.value != "") {
                    params.append("repeatEnd", eRepeat.value+" 23:59:59")
                }else if(rCount.value != "") {
                    params.append("repeatCount", rCount.value)
                }
            }
            
            fetch("schedule/update", {
                method: "post",
                body: params
            })
            .then(r=>r.text())
            .then(r=>{
                location.reload()
            })
        })

        deleteSchedule.addEventListener("click", ()=>{
            if(confirm("정말 삭제 하시겠습니까?")) {
                let params = new FormData

                if(groupId != "" && !repeatCheck.checked) {
                    console.log("isException and notAll")
                    params.append("exception", true)
                    params.append("exceptions[0].repeatId", groupId)
                    params.append("exceptions[0].exceptionDate", startStr)
                    params.append("exceptions[0].exceptionType", "skip")
                }else if(groupId != "" && repeatCheck.checked) {
                    console.log("isAll")
                    params.append("repeatId", groupId)
                }else {
                    console.log("notException and notAll")
                    params.append("scheduleId", scheduleId)
                }
                fetch("schedule/delete", {
                    method: "post",
                    body: params
                })
                .then(r=>r.text())
                .then(r=>{
                    // console.log(r)
                    location.reload()
                })
            }
        })
        
        $('#detailModal').on('hidden.bs.modal', function () {
            for(a of dis) {
                a.disabled = true;
                repeatScheduleDiv.setAttribute("style", "display: none;")
                eRepeat.value = null;
                rCount.value = null;
                groupId = null
                startStr = null
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
        let exdate = []
        for(e of a.exceptions) {
            if(e.exceptionDate != null) {
                exdate.push(e.exceptionDate)
            }
        }
        let event = {
            groupId: a.repeatId,
            title: a.detail,
            color: '#378006',
            extendedProps: {
                startDate: a.startTime,
                endDate: a.endTime,
                type: a.scheduleType,
                repeatType: a.repeatType,
                repeatEnd: a.repeatEnd,
                repeatCount: a.repeatCount
            },
            rrule: {
                freq: a.repeatType,
                dtstart: a.startTime,
                until: a.repeatEnd,
                count: a.repeatCount
            },
            exdate: exdate,
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
        if(eRepeat.value != "") {
            params.append("repeatEnd", eRepeat.value+" 23:59:59")
        }else if(rCount.value != "") {
            params.append("repeatCount", rCount.value)
        }
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