const notificationArea = document.getElementById("notificationArea");
const userIdNotification = getUserIdCookie("userId");
//const chatAlert = document.getElementById("chatAlert");

function getUserIdCookie(name) {
    return document.cookie
        .split("; ")
        .find(cookie => cookie.startsWith(name + "="))
        ?.split("=")[1] ?? null;
}

window.addEventListener("load", ()=>{
    fetch("/notification/getNotification")
    .then(r => r.json())
    .then(r => {
        for(j of r){
            createAlert(j);
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
    console.log("Stomp 연결 성공: ", frame);

    //공지사항 알림
    stompClientNotification.subscribe(`/sub/notice`, function (message) {
        console.log("받음")
        const msg = JSON.parse(message.body); //서버에서 json으로 보낸걸 json 객체로 받음
        console.log(msg);
        // msg.notiContents = "공지사항 " + msg.notiContents;
        createAlert(msg);
    })
    //채팅방 알림
    stompClientNotification.subscribe(`/sub/chat/user.${userIdNotification}`, function (message) {
        const msg = JSON.parse(message.body);
        const chatInfo = document.querySelector("#chatInfo");
        //만약 채팅 번호가 존재한다면
        if(chatInfo){
            //그리고 그 채팅번호가 지금 알림 받은 방 번호랑 같다면 알림을 띄우지 않음
            if(chatInfo.getAttribute("data-chat-num") == msg.chatContentsVO.chatRoomNum){
                return;
            }
        }
        const chatAmount = document.querySelector("#chatAmount");
        if(chatAmount){
            let chatAmountNum = Number(chatAmount.innerText);
            chatAmountNum++;
            chatAmount.innerText = chatAmountNum;
        }
        console.log(msg);
        createToast(msg);
    })

}, function (error) {
    console.error("stomp 연결 실패: ", error);
})

function createAlert(msg) {

    const a = document.createElement("a");
    a.classList.add("dropdown-item", "d-flex", "align-items-center", "notification");
    if(msg.notiCheckStatus == 0){
        a.classList.add("nonRead");
        a.style.backgroundColor = "lightgoldenrodyellow";
        // a.style.color = "gray"
    }
    if (msg.notiKind == "NOTICE") {
        a.href = `/notice/detail?noticeNum=${msg.relateId}`
    } else {

        a.href = "#";
    }

    // 왼쪽 아이콘 영역
    const iconWrapper = document.createElement("div");
    iconWrapper.classList.add("mr-3");

    const iconCircle = document.createElement("div");
    iconCircle.classList.add("icon-circle", "bg-info");

    const icon = document.createElement("ion-icon");
    icon.setAttribute("size", "large");
    icon.setAttribute("name", "information-circle-outline");

    iconCircle.appendChild(icon);
    iconWrapper.appendChild(iconCircle);

    // 오른쪽 텍스트 영역
    const textWrapper = document.createElement("div");

    const dateDiv = document.createElement("div");
    dateDiv.classList.add("small", "text-gray-500");
    dateDiv.innerText = formatDate(msg.notiDate);

    const kindDiv = document.createElement("div");
    kindDiv.classList.add("small", "font-weight-bold")
    if(msg.notiKind == "NOTICE"){
        kindDiv.innerText = "[공지사항]"
    }

    const contentSpan = document.createElement("span");
    contentSpan.classList.add("font-weight-bold");
    contentSpan.innerText = msg.notiContents;

    textWrapper.appendChild(dateDiv);
    textWrapper.appendChild(kindDiv);
    textWrapper.appendChild(contentSpan);

    // 전체 구성
    a.appendChild(iconWrapper);
    a.appendChild(textWrapper);

    a.addEventListener("click", ()=>{
        console.log("!!!!a태그!!!!")
        clickNotification(msg.notiNum);
    })

    notificationArea.append(a);
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

//chatStomp에서도 같이 수정하기
function createToast(msg) {
    const container = document.getElementById("chatAlert");

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
    <ion-icon name="chatbubble-ellipses-outline" class="mr-2"></ion-icon>
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

//============알람 클릭했을 때 status 값 변경하는 로직

function clickNotification(notiNum){
    const p = new URLSearchParams();
    p.append("notiNum", notiNum);

    fetch("notification/updateNotiStatus", {
        method: "POST",
        body: p,
        keepalive: true
    })

}