<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

    <script src='/fullcalendar/dist/index.global.js'></script>
    <script>

    // 일정부분
    var scheduleEl = document.getElementById("schedule")
    var schedule = new FullCalendar.Calendar(scheduleEl, {
        themeSystem: 'bootstrap4',
        contentHeight: 300,
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
	contentHeight: 300,
	locale: 'ko',
    nowIndicator: true,
    headerToolbar: {
        left:'today',
        center: 'title',
        right: 'prev,next'
    },
      initialDate: Date.now(),
      navLinks: false, // can click day/week names to navigate views
      editable: false,
      dayMaxEvents: true, // allow "more" link when too many events
      fixedWeekCount: false,
    });

    calendar.render();
    </script>