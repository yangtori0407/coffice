const kind = document.getElementById("kind")
let flag = true;
const authority = true;
function getCookie(name) {
  const match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
  if (match) return decodeURIComponent(match[2]);
  return null;
}
const uid = getCookie("userId");
const gcolor = "#fb6544"
const pcolor = "#378006"
let currentEvent = null;
let rType = null;
let type = null;
let scheduleId = null;
let startStr = null;
let groupId = null;
let startTime = null;
let endTime = null;
let rstartDate = null;
let rstartTime = null;
let rendDate = null;
let rendTime = null;
let repeatScheduleDiv = document.getElementById("repeatScheduleDiv")
let repeatCheck = document.getElementById("repeatCheck")
let changeAll = document.getElementById("changeAll")
let eRepeat = document.getElementById("reRepeat")
let rCount = document.getElementById("resultCount")
let detailResult = document.getElementById("detailResult")
let rsDate = document.getElementById("rsDate")
let rsTime = document.getElementById("rsTime")
let reDate = document.getElementById("reDate")
let reTime = document.getElementById("reTime")
let dis = document.querySelectorAll("input[disabled], textarea[disabled], select[disabled]");
let change = document.getElementById("change")
let saveChange = document.getElementById("saveChange")
let deleteSchedule = document.getElementById("deleteSchedule")

var calendarEl = document.getElementById("calendar")
var calendar = new FullCalendar.Calendar(calendarEl, {
    height: 850,
    themeSystem: 'bootstrap4',
    locale: 'ko',
    events: '/events/all?kind=schedule',
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
        right: 'addButton dayGridMonth,listWeek'
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
        if (e.event.extendedProps.preventClick) return;
        
        console.log(e.event)
        currentEvent = e.event
        $("#detailModal").modal("show")
        
        scheduleId = currentEvent.id
        startStr = currentEvent.startStr
        groupId = currentEvent.groupId
        startTime = currentEvent.extendedProps.startDate
        endTime = currentEvent.extendedProps.endDate
        rstartDate = currentEvent.startStr.slice(0, 10)
        rstartTime = currentEvent.startStr.slice(11, 16)
        rendDate = currentEvent.endStr.slice(0, 10)
        rendTime = currentEvent.endStr.slice(11, 16)
        type = document.querySelector(`input[name="detailResultOptions"][value="${currentEvent.extendedProps.type}"]`);
        rType = document.querySelector(`input[name="radioOptionsResult"][value="${currentEvent.extendedProps.repeatType}"]`);
        type.checked = true;
        detailResult.innerText = e.event.title
        rsDate.value = rstartDate
        rsTime.value = rstartTime
        reDate.value = rendDate
        reTime.value = rendTime
        if(e.event.groupId != "" && e.event.groupId != 'null') {
            repeatScheduleDiv.setAttribute("style", "display: block;")
            rType.checked = true;
            if(e.event.extendedProps.repeatEnd != null) {
                eRepeat.value = e.event.extendedProps.repeatEnd.slice(0, 10)
            }else {
                rCount.value = e.event.extendedProps.repeatCount
            }
        }
        
        if(uid != e.event.extendedProps.userId) {
            change.setAttribute("style", "display: none;")
            deleteSchedule.setAttribute("style", "display: none;")
            saveChange.innerText = "닫기"
        }else {
        }
        
    },
    eventDrop: function(e) {
        let params = new FormData
        params.append("scheduleId", e.event.id)
        params.append("startTime", e.event.startStr.slice(0, 10)+e.event.startStr.slice(11, 16))
        params.append("endTime", e.event.endStr.slice(0, 10)+e.event.endStr.slice(11, 16))
        fetch("schedule/dragDrop", {
            method: "post",
            body: params
        })
        .then(r=>r.text())
        .then(r=>{
            console.log(r)
        })
    }
});


change.addEventListener("click", ()=>{
    for(a of dis) {
        a.disabled = false;
    }
})

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

saveChange.addEventListener("click", ()=>{
    let checked = document.querySelector('input[name="detailResultOptions"]:checked').value;
    let params = new FormData
    
    if(groupId != "" && groupId != "null" && !repeatCheck.checked) {
        console.log(groupId)
        // scheduleId = null
        params.append("exception", true)
        params.append("exceptions[0].repeatId", groupId)
        params.append("exceptions[0].exceptionDate", startStr)
        params.append("exceptions[0].exceptionType", "override")
    }else if(groupId != "" && groupId != "null" && repeatCheck.checked) {
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

    // params.append("scheduleId", scheduleId)
    params.append("scheduleType", checked)
    params.append("detail", detailResult.value.trim())
    params.append("startTime", rsDate.value+rsTime.value)
    params.append("endTime", reDate.value+reTime.value)
    
    fetch("schedule/update", {
        method: "post",
        body: params
    })
    .then(r=>r.text())
    .then(r=>{
        // location.reload()
        calendar.refetchEvents()
        $("#detailModal").modal("hide")
    })
})

deleteSchedule.addEventListener("click", ()=>{
    if(confirm("정말 삭제 하시겠습니까?")) {
        let params = new FormData
        
        if(groupId != "" && groupId != "null" && !repeatCheck.checked) {
            console.log("isException and notAll")
            params.append("exception", true)
            params.append("exceptions[0].repeatId", groupId)
            params.append("exceptions[0].exceptionDate", startStr)
            params.append("exceptions[0].exceptionType", "skip")
        }else if(groupId != "" && groupId != "null" && repeatCheck.checked) {
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
            // location.reload()
            calendar.refetchEvents()
            $("#detailModal").modal("hide")
        })
    }
})


// fetch("/events/getHolidays")
// .then(r=>r.json())
// .then(r=>{
    
    //     for(a of r) {
        //         let event = {
            //             title: a.dateName,
            //             start: a.locdate,
            //             allDay: true,
            //             color: '#ee0000',
            //             editable: false,
            //             extendedProps: {
                //                 preventClick: true
                //             }
                //         }
                //         calendar.addEvent(event);
                //     }
                // })
                
                // fetch("/events/getRepeatSchedules")
                // .then(r=>r.json())
                // .then(r=>{
                    //     for(a of r) {
                        //         let clr;
                        //         if(uid == a.userId) {
                            //             clr = pcolor
                            //         }else {
                                //             clr = gcolor
//         }
//         let exdate = []
//         for(e of a.exceptions) {
    //             if(e.exceptionDate != null) {
        //                 exdate.push(e.exceptionDate)
        //             }
        //         }
        //         let event = {
            //             groupId: a.repeatId,
            //             title: a.detail,
            //             color: clr,
            //             editable: false,
            //             extendedProps: {
                //                 startDate: a.startTime,
                //                 endDate: a.endTime,
                //                 type: a.scheduleType,
                //                 repeatType: a.repeatType,
                //                 repeatEnd: a.repeatEnd,
                //                 repeatCount: a.repeatCount,
                //                 userId: a.userId
                //             },
                //             rrule: {
                    //                 freq: a.repeatType,
                    //                 dtstart: a.startTime,
                    //                 until: a.repeatEnd,
                    //                 count: a.repeatCount
                    //             },
                    //             exdate: exdate,
                    //             duration: calculateDurationObject(a.startTime, a.endTime)
                    //         }
                    //         calendar.addEvent(event);
                    //     }
                    // })

// fetch("/events/getSchedules")
// .then(r=>r.json())
// .then(r=>{
    //     for(a of r) {
        //         if(uid == a.userId) {
            //             let event = {
//                 id: a.scheduleId,
//                 title: a.detail,
//                 start: a.startTime,
//                 end: a.endTime,
//                 color: pcolor,
//                 extendedProps: {
    //                     type: a.scheduleType,
    //                     userId: a.userId,
    //                 }
    //             }
    //             calendar.addEvent(event);
    //         }else {
        //             let event = {
            //                 id: a.scheduleId,
//                 title: a.detail,
//                 start: a.startTime,
//                 end: a.endTime,
//                 color: gcolor,
//                 editable: !authority,
//                 extendedProps: {
    //                     type: a.scheduleType,
    //                     userId: a.userId,
    //                 }
    //             }
    //             calendar.addEvent(event);
    //         }
    //     }
    // })
    
    
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
            params.append("repeatEnd", eRepeat.value+"T23:59:59")
        }else if(rCount.value != "") {
            params.append("repeatCount", rCount.value)
        }
    }
    
    fetch("schedule/add", {
        method: "post",
        body: params
    })
    .then(r=>{
        // console.log(r)
        // location.reload()
        calendar.refetchEvents()
    })
    
    $("#exampleModal").modal("hide")
})

function show() {
    $("#exampleModal").modal("show")
}

$('#detailModal').on('hidden.bs.modal', function () {
    for(a of dis) {
        a.disabled = true;
        repeatScheduleDiv.setAttribute("style", "display: none;")
        eRepeat.value = null;
        rCount.value = null;
        groupId = null
        startStr = null
        change.setAttribute("style", "display: block;")
        deleteSchedule.setAttribute("style", "display: block;")
        saveChange.innerText = '저장'
    }
})