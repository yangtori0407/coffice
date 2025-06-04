<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script src='/fullcalendar/dist/index.global.js'></script>
    <script>
    var calendarEl = document.getElementById("calendar")
    var calendar = new FullCalendar.Calendar(calendarEl, {
    themeSystem: 'bootstrap5',
    locale: 'ko',
    customButtons: {
      addButton: {
        text: '+',
        click: function() {
          console.log('add')
        }
      }
    },
    nowIndicator: true,
    headerToolbar: {
        left:'prevYear,prev,next,nextYear today editButton',
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

    // let url = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo"
    // let key = `${apiKey}`
    // let year = new Date().getFullYear()
    
    // let params = new URLSearchParams({
    //     serviceKey: key,
    //     numOfRows: 100,
    //     _type: 'json',
    //     solYear: year
    // });

    // fetch(url+"?"+params)
    // .then(r=>r.json())
    // .then(r=>{

    //     let list = r.response.body.items.item
    //     for(a of list) {

    //         var event = {
    //             title: a.dateName,
    //             start: a.locdate.toString(),
    //             allDay: true,
    //             color: '#378006'
    //         }
    //         calendar.addEvent(event);
    //     }
    // })

    let list = JSON.parse(localStorage.getItem("list"))
        console.log(list)

        for(a of list) {

            var event = {
                title: a.dateName,
                start: a.locdate.toString(),
                allDay: true,
                color: '#378006'
            }
            calendar.addEvent(event);
        }

    // var event = {
    //   title: 'addTest',
    //   start: '2025-06-27',
    //   allDay: true,
    //   color: '#378006'
    // }
    // calendar.addEvent(event);

    calendar.render();
    </script>