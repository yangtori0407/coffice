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
        left:'prevYear,prev,next,nextYear today yearGrid',
        center: 'title',
        right: 'addButton dayGridMonth,timeGridWeek,timeGridDay,listWeek'
    },
      initialDate: Date.now(),
      navLinks: true, // can click day/week names to navigate views
      editable: true,
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

    let url = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo"
    let key = 'J0mHq1R1fL8PBzcOJXPlaICPhvWctJpIQoAUJNzx1fUeMzFU9bjNRoAuwfN%2FC1w79pvPN5onz8835x6feTa2yA%3D%3D'
    let now = new Date()
    
    let params = new URLSearchParams({
        serviceKey: key,
        numOfRows: 100,
        _type: 'json',
        solYear: now.getFullYear
    });

    fetch("https://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo?serviceKey=J0mHq1R1fL8PBzcOJXPlaICPhvWctJpIQoAUJNzx1fUeMzFU9bjNRoAuwfN%2FC1w79pvPN5onz8835x6feTa2yA%3D%3D&solYear=2025&numOfRows=100&_type=json")
    .then(r=>r.json())
    .then(r=>{
        console.log(r.response.body.items.item)
        let list = r.response.body.items.item
        for(a of list) {
            let day = new Date()
            day = day.toISOString(a.locdate).substring(0,10)
            console.log(day)
            var event = {
                title: a.dateName,
                start: a.locdate,
                allDay: true,
                color: '#378006'
            }
            calendar.addEvent(event);
            calendar.render();
        }
    })

    // var event = {
    //   title: 'addTest',
    //   start: '2025-06-27',
    //   allDay: true,
    //   color: '#378006'
    // }
    // calendar.addEvent(event);

    // calendar.render();
    </script>