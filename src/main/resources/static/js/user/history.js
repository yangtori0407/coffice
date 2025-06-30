document.addEventListener("DOMContentLoaded", function () {
    // tbody 내의 모든 행 선택
    const rows = document.querySelectorAll("table tbody tr");

    rows.forEach(function (row) {
        row.addEventListener("click", function () {
        // 첫 번째 <td> = 신청번호 위치
        const applyId = row.querySelector("td").innerText.trim();
        console.log("신청번호:", applyId);
        fetch(`/events/vacation/getOne?vacationId=${applyId}`)
        .then(r=>r.json())
        .then(r=>{
            console.log(r)
            let st;
            let et;
            if(r.vacationVO.status == 0) {
                st = "승인 대기"
                et = ""
                updateVacation.setAttribute("style", "display: block;")
            }else {
                st = "승인 완료"
                et = r.vacationVO.editTime.slice(0, 10) + " " + r.vacationVO.editTime.slice(11, 16)
                updateVacation.setAttribute("style", "display: none;")
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
            at.innerHTML = "<strong>승인일 : </strong>"+et
            let userId = document.getElementById("userId")
            if(userId.value == r.vacationVO.userId) {
                updateVacation.innerText = "수정"
            }else {
                updateVacation.innerText = "승인"
            }
        })
        $("#vacationDetailModal").modal("show")
        });
    });
});