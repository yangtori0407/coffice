const kind = document.getElementById("kind")
let flag = true;

var calendarEl = document.getElementById("calendar")
var calendar = new FullCalendar.Calendar(calendarEl, {
    themeSystem: 'bootstrap4',
    locale: 'ko',
    customButtons: {
        addButton: {
            text: '휴가 신청',
            click: apply
        },
        listButton: {
            text: '신청 목록',
            click: accept
        }
    },
    nowIndicator: true,
    headerToolbar: {
        left:'prevYear,prev,next,nextYear today',
        center: 'title',
        right: 'listButton addButton'
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
        apply()
    },
    eventClick: function(e) {
        console.log(e.event)
    }
});


// const chooseOne = document.getElementById("chooseOne")
// chooseOne.addEventListener("click", ()=>{
//     $("#vacationDetailModal").modal("show")
// })

const undo = document.getElementById("undo")
undo.addEventListener("click", ()=>{
    $("#listModal").modal("show")
})

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


function apply() {
    
    fetch("http://localhost/events/getDepsUsers")
    .then(r=>r.json())
    .then(r=>{
        let accept = document.getElementById("accept")
        for(a of r) {
            let opt = document.createElement("option")
            opt.value = a.userId
            opt.innerText = a.position + " " + a.name
            accept.appendChild(opt)
        }
    })
    
    const send = document.getElementById("send")
    send.addEventListener("click", ()=>{
        let vType = document.getElementById("vType")
        let sDate = document.getElementById("sDate")
        let sTime = document.getElementById("sTime")
        let eDate = document.getElementById("eDate")
        let eTime = document.getElementById("eTime")
        let accept = document.getElementById("accept")
        
        let params = new FormData
        params.append("type", vType.value)
        params.append("startTime", sDate.value+sTime.value)
        params.append("endTime", eDate.value+eTime.value)
        params.append("approvalAuthority", accept.value)
        
        fetch("http://localhost/events/vacation/apply", {
            method: "post",
            body: params
        })
        .then(r=>r.text)
        .then(r=>{
            // console.log(r)
            location.reload()
        })
    })

    $("#exampleModal").modal("show")
}

function accept() {

    let applyList = document.getElementById("applyList")
    let acceptList = document.getElementById("acceptList")
    applyList.innerText = ""
    acceptList.innerText = ""

    fetch("http://localhost/events/vacation/applyList")
    .then(r=>r.json())
    .then(r=>{
        console.log(r)
        let ul = document.createElement("ul")
        ul.classList.add("list-group")
        for( a of r ) {
            let stat;
            if(a.status) {
                stat = "승인 대기 \t <ion-icon name='ellipse' style='color:yellow;'></ion-icon>"
            }else {
                stat = "승인 완료 \t <ion-icon name='radio-button-on-outline' style='color:green;'></ion-icon>"
            }
            let li = document.createElement("li")
            li.classList.add("list-group-item")
            li.setAttribute("data-vacid", a.vacationId)
            let row = document.createElement("div")
            row.classList.add("row")
            let div = document.createElement("div")
            let div2 = document.createElement("div")
            let div3 = document.createElement("div")
            div.classList.add("col-6")
            div2.classList.add("col-3")
            div3.classList.add("col-3")
            div.innerText = a.startTime.slice(2, 10) + " " + a.startTime.slice(11, 16)
                            + " ~ " + a.endTime.slice(2, 10) + " " + a.endTime.slice(11, 16)
            div2.innerText = "승인자 : " + a.approvalAuthority
            div3.innerHTML = stat
            li.appendChild(row)
            row.appendChild(div)
            row.appendChild(div2)
            row.appendChild(div3)
            li.addEventListener("click", ()=>{
                fetch(`http://localhost/events/vacation/getOne?vacationId=${li.dataset.vacid}`)
                .then(r=>r.json())
                .then(r=>{
                    console.log(r)
                })
                $("#listModal").modal("hide")
                $("#vacationDetailModal").modal("show")
            })
            ul.appendChild(li)
        }
        applyList.appendChild(ul)

    })

    fetch("http://localhost/events/vacation/acceptList")
    .then(r=>r.json())
    .then(r=>{
        console.log(r)
        let ul = document.createElement("ul")
        ul.classList.add("list-group")
        for( a of r ) {
            let stat;
            if(a.status) {
                stat = "승인 대기 \t <ion-icon name='ellipse' style='color:yellow;'></ion-icon>"
            }else {
                stat = "승인 완료 \t <ion-icon name='radio-button-on-outline' style='color:green;'></ion-icon>"
            }
            let li = document.createElement("li")
            li.classList.add("list-group-item")
            li.setAttribute("data-vac-id", a.vacationId)
            let row = document.createElement("div")
            row.classList.add("row")
            let div = document.createElement("div")
            let div2 = document.createElement("div")
            let div3 = document.createElement("div")
            div.classList.add("col-6")
            div2.classList.add("col-3")
            div3.classList.add("col-3")
            div.innerText = a.startTime.slice(2, 10) + " " + a.startTime.slice(11, 16)
                            + " ~ " + a.endTime.slice(2, 10) + " " + a.endTime.slice(11, 16)
            div2.innerText = "신청자 : " + a.userId
            div3.innerHTML = stat
            li.appendChild(row)
            row.appendChild(div)
            row.appendChild(div2)
            row.appendChild(div3)
            li.addEventListener("click", ()=>{
                $("#listModal").modal("hide")
                $("#vacationDetailModal").modal("show")
            })
            ul.appendChild(li)
        }
        acceptList.appendChild(ul)
    })

    $("#listModal").modal("show")
}

calendar.render();


// $("#exampleModal").modal("hide")
function show() {
    $("#exampleModal").modal("show")
}