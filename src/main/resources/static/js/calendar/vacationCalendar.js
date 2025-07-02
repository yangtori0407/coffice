const kind = document.getElementById("kind")
let flag = true;
function getCookie(name) {
  const match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
  if (match) return decodeURIComponent(match[2]);
  return null;
}
const uid = getCookie("userId");
const gcolor = "#fb6544"
const pcolor = "#378006"

var calendarEl = document.getElementById("calendar")
var calendar = new FullCalendar.Calendar(calendarEl, {
    themeSystem: 'bootstrap4',
    locale: 'ko',
    customButtons: {
        addButton: {
            text: '휴가 신청',
            click: function() {
                apply()
                $("#exampleModal").modal("show")
            }
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
        $("#exampleModal").modal("show")
    },
    eventClick: function(e) {
        if (e.event.extendedProps.preventClick) {
            console.log("이 이벤트는 클릭 비활성화됨");
            return;
        }
        console.log(e.event.id)
        fetch(`vacation/getOne?vacationId=${e.event.id}`)
        .then(r=>r.json())
        .then(r=>{
            console.log(r)
            let st;
            let et;
            if(r.vacationVO.status == '대기') {
                st = "승인 대기"
                et = ""
                updateVacation.setAttribute("style", "display: block;")
                reject.setAttribute("style", "display: block")
            }else if(r.vacationVO.status == '승인') {
                st = "승인 완료"
                et = r.vacationVO.editTime.slice(0, 10) + " " + r.vacationVO.editTime.slice(11, 16)
                updateVacation.setAttribute("style", "display: none;")
                reject.setAttribute("style", "display: none")
            }else {
                st = "승인 거절"
                et = r.vacationVO.editTime.slice(0, 10) + " " + r.vacationVO.editTime.slice(11, 16)
                updateVacation.setAttribute("style", "display: none;")
                reject.setAttribute("style", "display: none")
            }
            let vid = document.getElementById("vid")
            let t = document.getElementById("vacationType")
            let a = document.getElementById("applier")
            let it = document.getElementById("insertTime")
            let p = document.getElementById("period")
            let s = document.getElementById("status")
            let ac = document.getElementById("accepter")
            let at = document.getElementById("acceptTime")
            vid.value = r.vacationVO.vacationId
            t.innerHTML = "<strong>종류 : </strong>"+r.vacationVO.type
            a.innerHTML = "<strong>신청자 : </strong>"+r.applier.position + " " + r.applier.name
            it.innerHTML = "<strong>신청일 : </strong>"+r.vacationVO.insertTime.slice(0, 10) + " " + r.vacationVO.insertTime.slice(11, 16)
            p.innerHTML = "<strong>신청 기간 : </strong>"+r.vacationVO.startTime.slice(2, 10) + " " + r.vacationVO.startTime.slice(11, 16) + " ~ " + r.vacationVO.endTime.slice(2, 10) + " " + r.vacationVO.endTime.slice(11, 16)
            s.innerHTML = "<strong>처리 상태 : </strong>"+st
            ac.innerHTML = "<strong>승인자 : </strong>"+r.accepter.position + " " + r.accepter.name
            at.innerHTML = "<strong>처리일 : </strong>"+et
            let userId = document.getElementById("userId")
            if(userId.value == r.vacationVO.userId) {
                updateVacation.innerText = "수정"
                // reject.setAttribute("style", "display: none")
            }else {
                updateVacation.innerText = "승인"
                // reject.setAttribute("style", "display: block")
            }
        })
        $("#vacationDetailModal").modal("show")
    }
});


// const chooseOne = document.getElementById("chooseOne")
// chooseOne.addEventListener("click", ()=>{
//     $("#vacationDetailModal").modal("show")
// })

const undo = document.getElementById("undo")
undo.addEventListener("click", ()=>{
    accept()
})

fetch("/events/getHolidays")
.then(r=>r.json())
.then(r=>{
    for(a of r) {
        let event = {
            title: a.dateName,
            start: a.locdate,
            allDay: true,
            color: '#ee0000',
            editable: false,
            extendedProps: {
                preventClick: true
            }
        }
        calendar.addEvent(event);
    }
})

fetch("/events/vacation/getList")
.then(r=>r.json())
.then(r=>{
    console.log(r)
    for(a of r) {
        let clr;
        if(uid == a.userId) {
            clr = pcolor
        }else {
            clr = gcolor
        }
        let event = {
            id: a.vacationId,
            title: a.aposition + " " + a.aname,
            start: a.startTime,
            end: a.endTime,
            color: clr,
            editable: false
        }
        calendar.addEvent(event);
    }
})


function apply() {
    
    fetch("/events/getDepsUsers")
    .then(r=>r.json())
    .then(r=>{
        let accept = document.getElementById("accept")
        let resultAccept = document.getElementById("resultAccept")
        accept.innerHTML = "<option value='' selected>선택</option>"
        resultAccept.innerHTML = "<option value='' selected>선택</option>"
        for(a of r) {
            let opt = document.createElement("option")
            let opt2 = document.createElement("option")
            opt.value = a.userId
            opt2.value = a.userId
            opt.innerText = a.position + " " + a.name
            opt2.innerText = a.position + " " + a.name
            accept.appendChild(opt)
            resultAccept.appendChild(opt2)
        }
    })
    
}


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
    
    fetch("/events/vacation/apply", {
        method: "post",
        body: params
    })
    .then(r=>r.text)
    .then(r=>{
        // console.log(r)
        location.reload()
    })
})

const updateVacation = document.getElementById("updateVacation")
const reject = document.getElementById("reject")
function accept() {

    let applyList = document.getElementById("applyList")
    let acceptList = document.getElementById("acceptList")
    applyList.innerText = ""
    acceptList.innerText = ""

    fetch("/events/vacation/applyList")
    .then(r=>r.json())
    .then(r=>{
        let ul = document.createElement("ul")
        ul.classList.add("list-group")
        for( a of r ) {
            console.log(a.status)
            let stat;
            if(a.status == '대기') {
                stat = "승인 대기 \t <ion-icon name='ellipse' style='color:yellow;'></ion-icon>"
            }else if(a.status == '승인') {
                stat = "승인 완료 \t <ion-icon name='ellipse' style='color:green;'></ion-icon>"
            }else {
                stat = "승인 거절 \t <ion-icon name='ellipse' style='color:red;'></ion-icon>"
            }
            let li = document.createElement("li")
            li.classList.add("list-group-item")
            li.setAttribute("data-vacid", a.vacationId)
            let row = document.createElement("div")
            row.classList.add("row")
            let div = document.createElement("div")
            let div2 = document.createElement("div")
            let div3 = document.createElement("div")
            div.classList.add("col-5")
            div2.classList.add("col-4")
            div3.classList.add("col-3")
            div.innerText = a.startTime.slice(2, 10) + " " + a.startTime.slice(11, 16)
                            + " ~ " + a.endTime.slice(2, 10) + " " + a.endTime.slice(11, 16)
            div2.innerText = "승인자 : " + a.bposition + " " + a.bname
            div3.innerHTML = stat
            li.appendChild(row)
            row.appendChild(div)
            row.appendChild(div2)
            row.appendChild(div3)
            li.addEventListener("click", ()=>{
                fetch(`vacation/getOne?vacationId=${li.dataset.vacid}`)
                .then(r=>r.json())
                .then(r=>{
                    console.log(r)
                    let st;
                    let et;
                    if(r.vacationVO.status == '대기') {
                        st = "승인 대기"
                        et = ""
                        updateVacation.setAttribute("style", "display: block;")
                        reject.setAttribute("style", "display: none")
                    }else if(r.vacationVO.status == '승인') {
                        st = "승인 완료"
                        et = r.vacationVO.editTime.slice(0, 10) + " " + r.vacationVO.editTime.slice(11, 16)
                        updateVacation.setAttribute("style", "display: none;")
                        reject.setAttribute("style", "display: none")
                    }else {
                        st = "승인 거절"
                        et = r.vacationVO.editTime.slice(0, 10) + " " + r.vacationVO.editTime.slice(11, 16)
                        updateVacation.setAttribute("style", "display: none;")
                        reject.setAttribute("style", "display: none")
                    }
                    let vid = document.getElementById("vid")
                    let t = document.getElementById("vacationType")
                    let a = document.getElementById("applier")
                    let it = document.getElementById("insertTime")
                    let p = document.getElementById("period")
                    let s = document.getElementById("status")
                    let ac = document.getElementById("accepter")
                    let at = document.getElementById("acceptTime")
                    vid.value = r.vacationVO.vacationId
                    t.innerHTML = "<strong>종류 : </strong>"+r.vacationVO.type
                    a.innerHTML = "<strong>신청자 : </strong>"+r.applier.position + " " + r.applier.name
                    it.innerHTML = "<strong>신청일 : </strong>"+r.vacationVO.insertTime.slice(0, 10) + " " + r.vacationVO.insertTime.slice(11, 16)
                    p.innerHTML = "<strong>신청 기간 : </strong>"+r.vacationVO.startTime.slice(2, 10) + " " + r.vacationVO.startTime.slice(11, 16) + " ~ " + r.vacationVO.endTime.slice(2, 10) + " " + r.vacationVO.endTime.slice(11, 16)
                    s.innerHTML = "<strong>처리 상태 : </strong>"+st
                    ac.innerHTML = "<strong>승인자 : </strong>"+r.accepter.position + " " + r.accepter.name
                    at.innerHTML = "<strong>처리일 : </strong>"+et
                })
                updateVacation.innerText = "수정"
                reject.setAttribute("style", "display: none")
                $("#listModal").modal("hide")
                $("#vacationDetailModal").modal("show")
            })
            ul.appendChild(li)
        }
        applyList.appendChild(ul);

        //휴가 알림 읽음 처리
        fetch("/notification/checkVacation", {
            method: "POST"
        })
    })

    fetch("/events/vacation/acceptList")
    .then(r=>r.json())
    .then(r=>{
        let ul = document.createElement("ul")
        ul.classList.add("list-group")
        for( a of r ) {
            let stat;
            if(a.status == '대기') {
                stat = "승인 대기 \t <ion-icon name='ellipse' style='color:yellow;'></ion-icon>"
            }else if(a.status == '승인') {
                stat = "승인 완료 \t <ion-icon name='ellipse' style='color:green;'></ion-icon>"
            }else {
                stat = "승인 거절 \t <ion-icon name='ellipse' style='color:red;'></ion-icon>"
            }
            let li = document.createElement("li")
            li.classList.add("list-group-item")
            li.setAttribute("data-vacid", a.vacationId)
            let row = document.createElement("div")
            row.classList.add("row")
            let div = document.createElement("div")
            let div2 = document.createElement("div")
            let div3 = document.createElement("div")
            div.classList.add("col-5")
            div2.classList.add("col-4")
            div3.classList.add("col-3")
            div.innerText = a.startTime.slice(2, 10) + " " + a.startTime.slice(11, 16)
                            + " ~ " + a.endTime.slice(2, 10) + " " + a.endTime.slice(11, 16)
            div2.innerText = "신청자 : " + a.aposition + " " + a.aname
            div3.innerHTML = stat
            li.appendChild(row)
            row.appendChild(div)
            row.appendChild(div2)
            row.appendChild(div3)
            li.addEventListener("click", ()=>{
                fetch(`vacation/getOne?vacationId=${li.dataset.vacid}`)
                .then(r=>r.json())
                .then(r=>{
                    console.log(r)
                    let st;
                    let et;
                    if(r.vacationVO.status == '대기') {
                        st = "승인 대기"
                        et = ""
                        updateVacation.setAttribute("style", "display: block;")
                        reject.setAttribute("style", "display: block")
                    }else if(r.vacationVO.status == '승인') {
                        st = "승인 완료"
                        et = r.vacationVO.editTime.slice(0, 10) + " " + r.vacationVO.editTime.slice(11, 16)
                        updateVacation.setAttribute("style", "display: none;")
                        reject.setAttribute("style", "display: none")
                    }else {
                        st = "승인 거절"
                        et = r.vacationVO.editTime.slice(0, 10) + " " + r.vacationVO.editTime.slice(11, 16)
                        updateVacation.setAttribute("style", "display: none;")
                        reject.setAttribute("style", "display: none")
                    }
                    let vid = document.getElementById("vid")
                    let t = document.getElementById("vacationType")
                    let a = document.getElementById("applier")
                    let it = document.getElementById("insertTime")
                    let p = document.getElementById("period")
                    let s = document.getElementById("status")
                    let ac = document.getElementById("accepter")
                    let at = document.getElementById("acceptTime")
                    vid.value = r.vacationVO.vacationId
                    t.innerHTML = "<strong>종류 : </strong>"+r.vacationVO.type
                    a.innerHTML = "<strong>신청자 : </strong>"+r.applier.position + " " + r.applier.name
                    it.innerHTML = "<strong>신청일 : </strong>"+r.vacationVO.insertTime.slice(0, 10) + " " + r.vacationVO.insertTime.slice(11, 16)
                    p.innerHTML = "<strong>신청 기간 : </strong>"+r.vacationVO.startTime.slice(2, 10) + " " + r.vacationVO.startTime.slice(11, 16) + " ~ " + r.vacationVO.endTime.slice(2, 10) + " " + r.vacationVO.endTime.slice(11, 16)
                    s.innerHTML = "<strong>처리 상태 : </strong>"+st
                    ac.innerHTML = "<strong>승인자 : </strong>"+r.accepter.position + " " + r.accepter.name
                    at.innerHTML = "<strong>처리일 : </strong>"+et
                })
                updateVacation.innerText = "승인"
                reject.setAttribute("style", "display: block")
                $("#listModal").modal("hide")
                $("#vacationDetailModal").modal("show")
            })
            ul.appendChild(li)
        }
        acceptList.appendChild(ul)
    })

    $("#listModal").modal("show")
}

let change = document.getElementById("change")
let saveChange = document.getElementById("saveChange")
change.innerText = "저장"
saveChange.innerText = "닫기"

updateVacation.addEventListener("click", ()=>{
    let vid = document.getElementById("vid")
    let params = new FormData
    params.append("vacationId", vid.value)
    if(updateVacation.innerText == "수정") {
        apply()
        let dis = document.querySelectorAll("input[disabled], select[disabled]");
        let rsDate = document.getElementById("rsDate")
        let rsTime = document.getElementById("rsTime")
        let reDate = document.getElementById("reDate")
        let reTime = document.getElementById("reTime")
        let rvType = document.getElementById("rvType")
        
        for(a of dis) {
            a.disabled = false;
        }

        fetch(`vacation/getOne?vacationId=${vid.value}`)
        .then(r=>r.json())
        .then(r=>{
            rsDate.value = r.vacationVO.startTime.slice(0, 10)
            rsTime.value = r.vacationVO.startTime.slice(11, 16)
            reDate.value = r.vacationVO.endTime.slice(0, 10)
            reTime.value = r.vacationVO.endTime.slice(11, 16)
            rvType.value = r.vacationVO.type
            let selected = document.querySelectorAll(`option[value='${r.vacationVO.approvalAuthority}']`)
            for(a of selected) {
                a.setAttribute("selected", true)
            }
        })

        change.addEventListener("click", ()=>{
            let resultAccept = document.getElementById("resultAccept")
            params.append("type", rvType.value)
            params.append("startTime", rsDate.value+rsTime.value)
            params.append("endTime", reDate.value+reTime.value)
            params.append("approvalAuthority", resultAccept.value)
            fetch("vacation/update", {
                method: "post",
                body: params
            })
            .then(r=>r.text())
            .then(r=>{
                // console.log(r)
                location.reload()
            })
        })

        $("#detailModal").modal("show")
    }else if(updateVacation.innerText == "승인") {
        if(confirm("승인 처리하시겠습니까?")) {
            fetch("/events/vacation/approve", {
                method: "post",
                body: params
            })
            .then(r=>r.text())
            .then(r=>{
                console.log(r)
                accept()
            })
        }else {
            console.log("no")
        }
    }
})

reject.addEventListener("click", ()=>{
    if(confirm("거부 처리하시겠습니까?")) {
        let vid = document.getElementById("vid")
        let params = new FormData
        params.append("vacationId", vid.value)
        fetch("/events/vacation/reject", {
            method: "post",
            body: params
        })
        .then(r=>r.text())
        .then(r=>{
            accept()
        })
    }
})

let goes = document.getElementsByName("goMypage")
for(a of goes) {
    a.addEventListener("click", ()=>{
        location.href = "/user/mypage"
    })
}


$('#exampleModal').on('hidden.bs.modal', function () {
    const modal = document.getElementById('exampleModal');
    const form = modal.querySelector("form");
    if (form) form.reset();
    modal.querySelectorAll('select').forEach(select => {
        select.selectedIndex = 0;
    });
})

document.addEventListener('DOMContentLoaded', function () {
  const vType = document.getElementById('vType');
  const sTime = document.getElementById('sTime');
  const eTime = document.getElementById('eTime');
  const sDate = document.getElementById('sDate');
  const eDate = document.getElementById('eDate');
  const modal = document.getElementById('exampleModal');

  function resetFields() {
    sTime.disabled = false;
    eTime.disabled = false;
    eDate.disabled = false;

    sTime.value = '';
    eTime.value = '';
    sDate.value = '';
    eDate.value = '';
    vType.value = '연차'; // 기본값이 있다면 이걸로
  }

  vType.addEventListener('change', function () {
    const type = vType.value;

    const syncDate = () => {
      if (sDate && eDate) eDate.value = sDate.value;
    };

    switch (type) {
      case '연차':
        sTime.value = '09:00';
        eTime.value = '18:00';
        sTime.disabled = true;
        eTime.disabled = true;
        eDate.disabled = false;
        break;

      case '오전':
        sTime.value = '09:00';
        eTime.value = '14:00';
        sTime.disabled = true;
        eTime.disabled = true;
        syncDate();
        eDate.disabled = true;
        break;

      case '오후':
        sTime.value = '14:00';
        eTime.value = '18:00';
        sTime.disabled = true;
        eTime.disabled = true;
        syncDate();
        eDate.disabled = true;
        break;

      case '시간':
        sTime.disabled = false;
        eTime.disabled = false;
        eDate.disabled = false;
        break;

      default:
        sTime.disabled = false;
        eTime.disabled = false;
        eDate.disabled = false;
        break;
    }
  });

  sDate.addEventListener('change', function () {
    const type = vType.value;
    if (type === '오전' || type === '오후') {
      eDate.value = sDate.value;
    }
  });

  // 모달 닫힐 때 초기화
  $('#exampleModal').on('hidden.bs.modal', function () {
    resetFields();
  });
});

calendar.render();


// $("#exampleModal").modal("hide")
function show() {
    $("#exampleModal").modal("show")
}