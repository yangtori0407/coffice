function getCookie(name) {
  const match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
  if (match) return decodeURIComponent(match[2]);
  return null;
}
const uid = getCookie("userId");
const gcolor = "#fb6544"
const pcolor = "#378006"

// 일정부분
var scheduleEl = document.getElementById("schedule")
var schedule = new FullCalendar.Calendar(scheduleEl, {
    themeSystem: 'bootstrap4',
    contentHeight: 200,
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
    contentHeight: 400,
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
    eventClick: function(e) {
        location.href = "/events/schedule"
    },
    initialDate: Date.now(),
    navLinks: false, // can click day/week names to navigate views
    editable: false,
    dayMaxEvents: true, // allow "more" link when too many events
    fixedWeekCount: false,
    eventContent: function(arg) {
        return {
        html: `<div style="font-size: 10px;">${arg.event.title}</div>`
        };
    }
});


fetch("/events/getHolidays")
.then(r=>r.json())
.then(r=>{
    for(a of r) {
        let event = {
            title: a.dateName,
            start: a.locdate,
            allDay: true,
            color: '#ee0000'
        }
        schedule.addEvent(event);
        calendar.addEvent(event);
    }
})

fetch("/events/getRepeatSchedules")
.then(r=>r.json())
.then(r=>{
    for(a of r) {
        let clr;
        if(uid == a.userId) {
            clr = pcolor
        }else {
            clr = gcolor
        }
        let exdate = []
        for(e of a.exceptions) {
            if(e.exceptionDate != null) {
                exdate.push(e.exceptionDate)
            }
        }
        let event = {
            groupId: a.repeatId,
            title: a.detail,
            color: clr,
            editable: false,
            extendedProps: {
                startDate: a.startTime,
                endDate: a.endTime,
                type: a.scheduleType,
                repeatType: a.repeatType,
                repeatEnd: a.repeatEnd,
                repeatCount: a.repeatCount,
                userId: a.userId
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
        schedule.addEvent(event)
    }
})

fetch("/events/getSchedules")
.then(r=>r.json())
.then(r=>{
    for(a of r) {
        if(uid == a.userId) {
            let event = {
                id: a.scheduleId,
                title: a.detail,
                start: a.startTime,
                end: a.endTime,
                color: pcolor,
                extendedProps: {
                    type: a.scheduleType,
                    userId: a.userId,
                }
            }
            calendar.addEvent(event);
        }else {
            let event = {
                id: a.scheduleId,
                title: a.detail,
                start: a.startTime,
                end: a.endTime,
                color: gcolor,
                extendedProps: {
                    type: a.scheduleType,
                    userId: a.userId,
                }
            }
            calendar.addEvent(event);
            schedule.addEvent(event);
        }
    }
})

calendar.render();

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