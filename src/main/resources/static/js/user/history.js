function getCookie(name) {
    const match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
    if (match) return decodeURIComponent(match[2]);
    return null;
}
const uid = getCookie("userId");

document.addEventListener("DOMContentLoaded", function () {
    // tbody 내의 모든 행 선택
    const rows = document.querySelectorAll("table tbody tr");
    const updateVacation = document.getElementById("updateVacation")
    const reject = document.getElementById("reject")

    rows.forEach(function (row) {
        row.addEventListener("click", function () {
        // 첫 번째 <td> = 신청번호 위치
        const applyId = row.querySelector("td").innerText.trim();
        console.log("신청번호:", applyId);
        fetch(`/events/vacation/getOne?vacationId=${applyId}`)
        .then(r=>r.json())
        .then(r=>{
            console.log(r)
            function getCookie(name) {
                const match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
                if (match) return decodeURIComponent(match[2]);
                return null;
            }
            const uid = getCookie("userId");
            let st;
            let et;
            if(r.vacationVO.status == '대기') {
                st = "승인 대기"
                et = ""
                updateVacation.setAttribute("style", "display: block;")
                if(uid == r.vacationVO.userId) {
                    updateVacation.innerText = "수정"
                    reject.setAttribute("style", "display: none")
                }else {
                    updateVacation.innerText = "승인"
                    reject.setAttribute("style", "display: block")
                }
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
        $("#vacationDetailModal").modal("show")
        });
    });
});

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
                location.reload()
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
            location.reload()
        })
    }
})

