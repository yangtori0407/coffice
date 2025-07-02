const notificationArea = document.getElementById("notificationArea");
const userIdNotification = getUserIdCookie("userId");
//const chatAlert = document.getElementById("chatAlert");
const notificationModal = document.getElementById("notificationModal");
const totalArea = document.getElementById("totalArea");
const alertsDropdown = document.getElementById("alertsDropdown");

function getUserIdCookie(name) {
    return document.cookie
        .split("; ")
        .find(cookie => cookie.startsWith(name + "="))
        ?.split("=")[1] ?? null;
}

//페이지가 로드될 때마다 알림을 미리 가지고 와서 뿌리기
window.addEventListener("load", () => {
    fetch("/notification/getNotification")
        .then(r => r.json())
        .then(r => {
            totalArea.innerText = r.total;
        })
})

alertsDropdown.addEventListener("click", () => {
    //console.log("click");
    fetch("/notification/getNotification")
        .then(r => r.json())
        .then(r => {
            //totalArea.innerText = r.total;
            console.log(r);
            notificationArea.innerHTML = "";
            for (j of r.list) {
                //console.log("알림 for");
                notificationArea.append(createAlert(j, 0));
            }
        })
})

const socketNotification = new SockJS("/ws-stomp");
const stompClientNotification = Stomp.over(socketNotification);

class Notification {
    notiNum = 0;
    notiKind = "";
    notiContents = "";
    notiDate = "";
    relateEntity = "";
    relateId = "";
}

stompClientNotification.connect({}, function (frame) {
    //console.log("Stomp 연결 성공: ", frame);

    //공지사항 알림
    stompClientNotification.subscribe(`/sub/notice`, function (message) {
        const msg = JSON.parse(message.body); //서버에서 json으로 보낸걸 json 객체로 받음
        notificationArea.prepend(createAlert(msg, 0));
        totalArea.innerText = Number(totalArea.innerText) + 1;
        notificationArea.lastElementChild.remove();
        showAlarmTooltip();
        createToast(msg);
    })
    //채팅방 알림
    stompClientNotification.subscribe(`/sub/chat/user.${userIdNotification}`, function (message) {
        const msg = JSON.parse(message.body);
        const chatInfo = document.querySelector("#chatInfo");
        //만약 채팅 번호가 존재한다면
        if (chatInfo) {
            //그리고 그 채팅번호가 지금 알림 받은 방 번호랑 같다면 알림을 띄우지 않음
            if (chatInfo.getAttribute("data-chat-num") == msg.chatContentsVO.chatRoomNum) {
                return;
            }
        }
        const chatAmount = document.querySelector("#chatAmount");
        if (chatAmount) {
            let chatAmountNum = Number(chatAmount.innerText);
            chatAmountNum++;
            chatAmount.innerText = chatAmountNum;
        }
        //console.log(msg);
        createChatToast(msg);
    })
    //모든 알림
    stompClientNotification.subscribe(`/sub/notification/user.${userIdNotification}`, function (message) {
        const msg = JSON.parse(message.body);
        notificationArea.prepend(createAlert(msg, 0));
        totalArea.innerText = Number(totalArea.innerText) + 1;
        notificationArea.lastElementChild.remove();
        showAlarmTooltip();
        createToast(msg);
        //console.log("토스트");
    })
    // //대댓글
    // stompClientNotification.subscribe(`/sub/nof/user.${userIdNotification}`, function (message) {
    //     const msg = JSON.parse(message.body);
        
    //     totalArea.innerText = Number(totalArea.innerText) + 1;
    //     notificationArea.lastElementChild.remove();
    //     showAlarmTooltip();
    //     createToast(msg);
    // })

}, function (error) {
    console.error("stomp 연결 실패: ", error);
})

//헤더 알림 클릭 시 알림 넣기
function createAlert(msg, num) {

    const a = document.createElement("a");

    //알림 더보기 모달
    if (num == 1) {
        a.classList.add("dropdown-item", "d-flex", "align-items-center", "notification", "modalCss");
    } else { //알림창
        a.classList.add("dropdown-item", "d-flex", "align-items-center", "notification");
    }

    if (msg.notiCheckStatus == 0) {
        a.classList.add("nonRead");
        a.style.backgroundColor = "lightgoldenrodyellow";
        // a.style.color = "gray"
    }

    if (msg.notiKind == "NOTICE") {
        a.href = `/notice/detail?noticeNum=${msg.relateId}`
    } else if (msg.notiKind == "BOARD" || msg.notiKind == "REPLY") {
        a.href = `/board/detail?boardNum=${msg.relateId}`
    } else if(msg.notiKind == "MESSAGE"){
        a.href = `/message/receive/detail?messageNum=${msg.relateId}`
    }else if(msg.notiKind == "VACATION"){
        a.href = "/user/vacationHistory";
    }else if(msg.notiKind == "APPROVAL"){
        a.href = "/document/list/onwaiting";
    }else if(msg.notiKind == "REFERENCE"){
        a.href = "/document/list/onreference";
    }else if(msg.notiKind == "DONE" || msg.notiKind == "REJECT"){
        a.href = "/document/list/online";
    }else {
        a.href = "#"; 
    }
    a.setAttribute("data-check-num", msg.notiCheckNum);

    // 왼쪽 아이콘 영역
    const iconWrapper = document.createElement("div");
    iconWrapper.classList.add("mr-3");

    const iconCircle = document.createElement("div");
    if (msg.notiKind == "NOTICE") {
        iconCircle.classList.add("icon-circle", "bg-info");
    } else if (msg.notiKind == "BOARD") {
        iconCircle.classList.add("icon-circle", "bg-info");
    }else if (msg.notiKind == "REPLY") {
        iconCircle.classList.add("icon-circle", "bg-info");
    }else if(msg.notiKind == "MESSAGE"){
        iconCircle.classList.add("icon-circle", "bg-secondary");
    }else if(msg.notiKind == "VACATION"){
        iconCircle.classList.add("icon-circle", "bg-warning");
    }else if(msg.notiKind == "APPROVAL" || msg.notiKind == "REFERENCE" || msg.notiKind == "DONE"){
        iconCircle.classList.add("icon-circle", "bg-primary");
    }else if(msg.notiKind == "REJECT"){
        iconCircle.classList.add("icon-circle", "bg-danger");
    }

    const icon = document.createElement("ion-icon");
    icon.setAttribute("size", "large");
    if (msg.notiKind == "NOTICE") {
        icon.setAttribute("name", "information-circle-outline");
    } else if (msg.notiKind == "BOARD") {
        icon.setAttribute("name", "return-down-forward-outline");
    }else if (msg.notiKind == "REPLY") {
        icon.setAttribute("name", "return-down-forward-outline");
    }else if(msg.notiKind == "MESSAGE"){
        icon.setAttribute("name", "mail-unread-outline");
    }else if(msg.notiKind == "VACATION"){
        icon.setAttribute("name", "calendar-outline");
    }else if(msg.notiKind == "APPROVAL" || msg.notiKind == "REFERENCE" || msg.notiKind == "DONE"){
        icon.setAttribute("name", "document-text-outline");
    }else if(msg.notiKind == "REJECT"){
        icon.setAttribute("name", "alert-outline");
    }
    iconCircle.appendChild(icon);
    iconWrapper.appendChild(iconCircle);

    // 오른쪽 텍스트 영역
    const textWrapper = document.createElement("div");

    const dateDiv = document.createElement("div");
    dateDiv.classList.add("small", "text-gray-500");
    dateDiv.innerText = formatDate(msg.notiDate);

    const kindDiv = document.createElement("div");
    kindDiv.classList.add("small", "font-weight-bold")
    
    if (msg.notiKind == "NOTICE") {
        kindDiv.innerText = "[공지사항]"
    } else if (msg.notiKind == "BOARD" || msg.notiKind == "REPLY" || msg.notiKind == "MESSAGE") {
        kindDiv.innerText = `[${msg.notiContents}]`
    }else if (msg.notiKind == "VACATION") {
        kindDiv.innerText = `[휴가 신청]`
    }else if (msg.notiKind == "APPROVAL" || msg.notiKind == "REFERENCE") {
        kindDiv.innerText = `[결재 요청]`
    }else if (msg.notiKind == "DONE") {
        kindDiv.innerText = `[결재 완료]`
    }else if (msg.notiKind == "REJECT") {
        kindDiv.innerText = `[결재 반려]`
    }       
    const contentSpan = document.createElement("span");
    contentSpan.classList.add("font-weight-bold");
    //익명게시판 댓글
    if (msg.notiKind == "BOARD") {
        contentSpan.innerText = "댓글이 달렸습니다."
    } else if (msg.notiKind == "REPLY") {
        contentSpan.innerText = "대댓글이 달렸습니다."
    } else if(msg.notiKind == "MESSAGE"){
        contentSpan.innerText = "메일이 도착했습니다."
    }else {
        contentSpan.innerText = msg.notiContents;
    }

    textWrapper.appendChild(dateDiv);
    textWrapper.appendChild(kindDiv);
    textWrapper.appendChild(contentSpan);

    // 전체 구성
    a.appendChild(iconWrapper);
    a.appendChild(textWrapper);

    a.addEventListener("click", () => {
        //console.log("!!!!a태그!!!!")
        clickNotification(msg.notiNum);
    })

    return a;

}


function formatDate(r) {
    const date = new Date(r);
    const yy = String(date.getFullYear()).slice(2);

    //padStart(targetLength, padString) : 문자열을 앞쪽에서 특정 길이만큼 채워주는 메서드
    //targetLength : 최종적으로 만들고 싶은 문자열 길이
    //padString : 부족할 경우 앞에 채울 문자열열
    const mm = String(date.getMonth() + 1).padStart(2, '0');
    const dd = String(date.getDate()).padStart(2, '0');
    const hh = String(date.getHours()).padStart(2, '0');
    const min = String(date.getMinutes()).padStart(2, '0')

    return `${yy}-${mm}-${dd} ${hh}:${min}`
}

function createChatToast(msg) {
    const container = document.getElementById("ToastAlert");

    const toast = document.createElement("div");
    toast.className = "toast fade show"; // Bootstrap 스타일 수동 적용
    toast.setAttribute("role", "alert");
    toast.setAttribute("aria-live", "assertive");
    toast.setAttribute("aria-atomic", "true");
    toast.style.minWidth = "250px";
    toast.style.marginBottom = "10px";
    toast.style.backgroundColor = "#fff";
    toast.style.boxShadow = "0 0 10px rgba(0,0,0,0.1)";
    toast.style.borderRadius = "5px";
    toast.style.border = "1px solid #ccc";

    toast.innerHTML = `
        <div class="toast-header d-flex align-items-center">
    <div style="width: 13px;height: 13px;background-color: blue;border-radius: 3px;margin-right: 4px;"></div>
    <strong class="mr-auto">${msg.chatRoomName}</strong>
    <small class="text-muted">${msg.chatContentsVO.formatted}</small>
    <button type="button" class="ml-2 mb-1 close" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>
    </div>

    <div class="toast-body p-2">
    <div class="d-flex flex-column">
        <div class="font-weight-bold mb-1" style="font-size: 14px;">
        ${msg.chatContentsVO.name} <!-- 보낸 사람 -->
        </div>
        <div class="chat-preview">
        ${msg.chatContentsVO.chatContents} <!-- 채팅 내용 -->
        </div>
    </div>
    </div>
  `;

    toast.addEventListener("click", () => {
        location.href = `/chat/chatRoom?chatRoomNum=${msg.chatContentsVO.chatRoomNum}`
    })

    // 닫기 버튼 이벤트
    toast.querySelector(".close").addEventListener("click", () => {
        toast.remove();
    });

    //container.innerHTML = "";
    container.appendChild(toast);

    // 자동 사라지기 (예: 3초 후)
    setTimeout(() => {
        toast.classList.add("hide"); // fade-out 시작
        setTimeout(() => toast.remove(), 1000); // transition 시간과 맞추기
    }, 1500);
}

function createToast(msg) {
    const container = document.getElementById("ToastAlert");

    const toast = document.createElement("div");
    toast.className = "toast fade show";
    toast.setAttribute("role", "alert");
    toast.setAttribute("aria-live", "assertive");
    toast.setAttribute("aria-atomic", "true");
    toast.style.minWidth = "250px";
    toast.style.marginBottom = "10px";
    toast.style.backgroundColor = "#fff";
    toast.style.boxShadow = "0 0 10px rgba(0,0,0,0.1)";
    toast.style.borderRadius = "5px";
    toast.style.border = "1px solid #ccc";

    // ===== HEADER =====
    const header = document.createElement("div");
    header.classList.add("toast-header", "d-flex", "align-items-center");

    const div = document.createElement("div");
    div.style.width = "13px";
    div.style.height = "13px";
    if(msg.notiKind == "REJECT"){

        div.style.backgroundColor = "red";
    }else{
        div.style.backgroundColor = "green";
    }
    div.style.marginRight = "4px"
    div.style.borderRadius = "3px";

    const icon = document.createElement("ion-icon");

    if (msg.notiKind == "NOTICE") {
        icon.setAttribute("name", "information-circle-outline");
    } else if (msg.notiKind == "BOARD" || msg.notiKind == "REPLY") {
        icon.setAttribute("name", "return-down-forward-outline");
    }
    else if (msg.notiKind == "MESSAGE") {
        icon.setAttribute("name", "mail-unread-outline");
    } else if (msg.notiKind == "VACATION") {
        icon.setAttribute("name", "calendar-outline");
    }else if (msg.notiKind == "APPROVAL" || msg.notiKind == "REFERENCE" || msg.notiKind == "DONE") {
        icon.setAttribute("name", "document-text-outline");
    }else if (msg.notiKind == "REJECT") {
        icon.setAttribute("name", "alert-outline");
    }
    icon.classList.add("mr-2");

    const roomName = document.createElement("strong");
    roomName.classList.add("mr-auto");
    if (msg.notiKind == "NOTICE") {
        roomName.textContent = "[공지사항]"
    } else if (msg.notiKind == "BOARD" || msg.notiKind == "REPLY" || msg.notiKind == "MESSAGE") {
        roomName.textContent = `[${msg.notiContents}]`
    }else if (msg.notiKind == "VACATION") {
        roomName.textContent = `[휴가]`
    }else if (msg.notiKind == "APPROVAL" || msg.notiKind == "REFERENCE") {
        roomName.textContent = `[결재 요청]`
    }else if (msg.notiKind == "DONE") {
        roomName.textContent = `[결재 완료]`
    }else if (msg.notiKind == "REJECT") {
        roomName.textContent = `[결재 반려]`
    }

    const closeBtn = document.createElement("button");
    closeBtn.setAttribute("type", "button");
    closeBtn.classList.add("ml-2", "mb-1", "close");
    closeBtn.setAttribute("aria-label", "Close");

    const closeIcon = document.createElement("span");
    closeIcon.setAttribute("aria-hidden", "true");
    closeIcon.innerHTML = "&times;";
    closeBtn.appendChild(closeIcon);

    header.appendChild(div);
    //header.appendChild(icon);
    header.appendChild(roomName);
    header.appendChild(closeBtn);

    // ===== BODY =====
    const body = document.createElement("div");
    body.classList.add("toast-body", "p-2");

    const bodyInner = document.createElement("div");
    bodyInner.classList.add("d-flex", "flex-column");

    const content = document.createElement("div");
    content.classList.add("chat-preview");

    if (msg.notiKind == "BOARD") {
        content.textContent = "댓글이 달렸습니다."
    } else if (msg.notiKind == "REPLY") {
        content.textContent = "대댓글이 달렸습니다."
    } else if(msg.notiKind == "MESSAGE"){
        content.textContent = "메일이 도착했습니다."
    }else{
        content.textContent = msg.notiContents;
    } 
    
    bodyInner.appendChild(content);
    body.appendChild(bodyInner);

    // ===== 이벤트 및 조립 =====
    toast.appendChild(header);
    toast.appendChild(body);

    toast.addEventListener("click", () => {
        if (msg.notiKind == "NOTICE") {

            location.href = `/notice/detail?noticeNum=${msg.relateId}`;
        } else if (msg.notiKind == "BOARD" || msg.notiKind == "REPLY") {
            location.href = `/board/detail?boardNum=${msg.relateId}`;
        } else if(msg.notiKind == "MESSAGE"){
            location.href = `/message/receive/detail?messageNum=${msg.relateId}`;
        }else if(msg.notiKind == "VACATION"){
            location.href = "/user/vacationHistory";
        }else if(msg.notiKind == "APPROVAL"){
            location.href = "/document/list/onwaiting";
        }else if(msg.notiKind == "REFERENCE"){
            location.href = "/document/list/onreference";
        }else if(msg.notiKind == "DONE" || msg.notiKind == "REJECT"){
            location.href = "/document/list/online";
        }
    });

    closeBtn.addEventListener("click", (e) => {
        e.stopPropagation(); // 링크로 가는 기본 동작 막기
        toast.remove();
    });

    container.appendChild(toast);

    setTimeout(() => {
        toast.classList.add("hide");
        setTimeout(() => toast.remove(), 1000);
    }, 3000);
}

//============알람 클릭했을 때 status 값 변경하는 로직

function clickNotification(notiNum) {
    const p = new URLSearchParams();
    p.append("notiNum", notiNum);

    fetch("/notification/updateNotiStatus", {
        method: "POST",
        body: p,
        keepalive: true
    })

}

//=====알림 더보기 눌렀을 때 내용 더 가지고 오는 로직

const moreNotiBtn = document.querySelector("#moreNotiBtn");
const moreNotiModalBtn = document.querySelector("#moreNotiModalBtn");

if (moreNotiBtn) {
    moreNotiBtn.addEventListener("click", () => {
        const lastNotiCheckNum = notificationArea.lastElementChild.getAttribute("data-check-num");
        
        fetch(`/notification/moreNotification?notiCheckNum=${lastNotiCheckNum}`)
            .then(r => r.json())
            .then(r => {
                notificationModal.innerText = "";
                for (j of r) {
                    notificationModal.appendChild(createAlert(j, 1));
                }
            })
    })
}

if (moreNotiModalBtn) {

    moreNotiModalBtn.addEventListener("click", () => {
        if(notificationModal.lastElementChild == null){
            alert("더 이상 불러올 알림이 없습니다.");
            return;
        }
        const lastNotiCheckNum = notificationModal.lastElementChild.getAttribute("data-check-num");
        fetch(`/notification/moreNotification?notiCheckNum=${lastNotiCheckNum}`)
            .then(r => r.json())
            .then(r => {
                if (r.length === 0) {
                    alert("더 이상 불러올 알림이 없습니다.");
                }
                for (j of r) {
                    notificationModal.appendChild(createAlert(j, 1));
                }
            })
    })
}

function showAlarmTooltip() {
    const tooltip = document.getElementById("alarmTooltip");
    tooltip.classList.add("show");

    setTimeout(() => {
        tooltip.classList.remove("show");
    }, 5000); // 3초 후 숨김
}