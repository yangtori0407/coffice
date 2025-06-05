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
    dayMaxEvents: false, // allow "more" link when too many events
    fixedWeekCount: false,
});


// localStorage 만료시간
if(new Date().getTime() > localStorage.getItem("calExpTime")) {
    localStorage.clear()
}


// 초기 진입시 api조회 조건 추가 및 작년, 올해,내년 공휴일 조회
if(localStorage.length < 3) {
    let url = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo"
    let key = `${apiKey.value}`
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
            addEvent();
        })
    }
}

// event 추가 함수
function addEvent() {
    for(let i = 0; i < 3; i++) {
        let list = JSON.parse(localStorage.getItem("list"+i))
    
            for(a of list) {
                let event = {
                    title: a.dateName,
                    start: a.locdate.toString(),
                    allDay: true,
                    color: '#378006'
                }
                schedule.addEvent(event);
                calendar.addEvent(event);
            }
    }
}

addEvent();

calendar.render();