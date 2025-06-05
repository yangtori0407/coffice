<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

    <script src='/fullcalendar/dist/index.global.js'></script>
    <script type="text/javascript">

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
    dayCellContent: function (arg) {
      const { date } = arg;
      return date.getDate();
    },
      initialDate: Date.now(),
      navLinks: false, // can click day/week names to navigate views
      editable: false,
      dayMaxEvents: true, // allow "more" link when too many events
      fixedWeekCount: false,
    });

    
    if(new Date().getTime() > localStorage.getItem("calExpTime")) {
        localStorage.clear()
    }
    
    if(localStorage.length < 3) {
        let url = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo"
        let key = `${apiKey}`
        let year = new Date().getFullYear()-1
        
        let t = new Date().getTime()+(7*24*60*60*1000)
        localStorage.setItem("calExpTime", t.toString())
        
        for(let i = 0; i < 3; i++) {
            let params = new URLSearchParams({
                serviceKey: key,
                numOfRows: 100,
                _type: 'json',
                solYear: year+i
            });
            
            fetch(url+"?"+params)
            .then(r=>r.json())
            .then(r=>{
                let list = r.response.body.items.item
                localStorage.setItem("list"+i, JSON.stringify(list));
                list = JSON.parse(localStorage.getItem("list"+i))

            })
        }
    }

    for(let i = 0; i < 3; i++) {
      let list = JSON.parse(localStorage.getItem("list"+i))
  
          for(a of list) {
              var event = {
                  title: a.dateName,
                  start: a.locdate.toString(),
                  allDay: true,
                  color: '#378006'
              }
              schedule.addEvent(event);
              calendar.addEvent(event);
          }
    }


    calendar.render();
    </script>