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
      },
      events: [
        {
          id: 1,
          title: 'All Day Event',
          start: '2025-05-01',
          startRecur: '2025-05-05',
          endRecur: '2025-05-28',
          daysOfWeek: ['4']
        },
        {
          title: 'Long Event',
          start: '2025-05-07',
          end: '2025-05-10'
        },
        {
          groupId: 999,
          title: 'Repeating Event',
          start: '2025-05-09T16:00:00'
        },
        {
          groupId: 999,
          title: 'Repeating Event',
          start: '2025-05-16T16:00:00'
        },
        {
          title: 'Conference',
          start: '2025-05-11',
          end: '2025-05-13'
        },
        {
          title: 'Meeting',
          start: '2025-05-12T10:30:00',
          end: '2025-05-12T12:30:00'
        },
        {
          title: 'Lunch',
          start: '2025-05-12T12:00:00'
        },
        {
          title: 'Meeting',
          start: '2025-05-12T14:30:00'
        },
        {
          title: 'Happy Hour',
          start: '2025-05-12T17:30:00'
        },
        {
          title: 'Dinner',
          start: '2025-05-12T20:00:00'
        },
        {
          title: 'Birthday Party',
          start: '2025-05-13T07:00:00'
        },
        {
          title: 'Click for Google',
          url: 'http://google.com/',
          start: '2025-05-28'
        }
      ]
    });

    var event = {
      title: 'addTest',
      start: '2025-05-27',
      allDay: true
    }
    calendar.addEvent(event);

    calendar.render();
    </script>