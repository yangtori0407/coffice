const socket = new SockJS("/ws-stomp");
const stompClient = Stomp.over(socket);

const chatInfo = document.getElementById("chatInfo");
const chatNum = chatInfo.getAttribute("data-chat-num");
const userId = getUserIdCookie("userId");
const chatInput = document.getElementById("chat-input");
const sendBtn = document.getElementById("chat-send-btn");

const chatBox = document.getElementById("chat-box");

function getUserIdCookie(name) {
    return document.cookie
        .split("; ")
        .find(cookie => cookie.startsWith(name + "="))
        ?.split("=")[1] ?? null;
}


window.addEventListener("load", function () {
    chatBox.scrollTop = chatBox.scrollHeight;
});

//채팅방 무한 스크롤
chatBox.addEventListener("scroll", ()=>{
   if(chatBox.scrollTop == 0){
    const topChat = chatBox.firstElementChild.getAttribute("data-chat-num");

    if(topChat == null){
        return;
    }

    //채팅 새로 들어오기 전 높이
    const preScrollHeight = chatBox.scrollHeight;

    let p = new URLSearchParams();
    p.append("chatRoomNum", chatNum);
    p.append("chatNum", topChat);
    fetch("./getChatMore",{
        method:"POST",
        body: p
    })
    .then(r=>r.json())
    .then(r => {
        //이미 json 객체
        for(j of r){
            chatBox.prepend(displayReceiveMessage(j));
        }
        
        //더해지고 난 뒤에 스크롤 높이
        const newScrollHeight = chatBox.scrollHeight;
        //불러오고 난 뒤 - 이전
        chatBox.scrollTop = newScrollHeight - preScrollHeight;
    })
       
   } 
})

//클래스 이름은 반드시 대문자로 해야한다
class Message {
    chatNum = 0;
    chatRoomNum = "";
    chatContents = "";
    sendDate = "";
    sender = "";
    flag = false; //파일인지 아닌지
    system = false; //나가는 메세지인지 아닌지
    fileNum = "";
    invite = false; //초대문구
}

stompClient.connect({}, function (frame) {
    //console.log("Stomp 연결 성공: ", frame);

    stompClient.subscribe(`/sub/chatRoom.${chatNum}`, function (message) {
        const msg = JSON.parse(message.body); //서버에서 문자열로 보낸걸 json 객체로 받음
        //console.log(msg);
        let chat = displayReceiveMessage(msg);
        chatBox.append(chat);
        chatBox.scrollTop = chatBox.scrollHeight;
    })

}, function (error) {
    console.error("stomp 연결 실패: ", error);
})

sendBtn.addEventListener("click", () => {
    sendMessage();
    chatInput.value = "";
    chatBox.scrollTop = chatBox.scrollHeight
})

chatInput.addEventListener("keydown", (event) => {
    if (event.key === "Enter" && !event.shiftKey) {
        event.preventDefault();
        sendMessage();
        chatInput.value = "";
        chatBox.scrollTop = chatBox.scrollHeight
    }
});

function sendMessage() {
    let message = new Message();
    message.chatContents = chatInput.value.trim();
    message.chatRoomNum = chatNum;

    if (!chatInput.value) return;

    stompClient.send("/pub/sendMessage", {}, JSON.stringify(message));
}

function displayReceiveMessage(msg) {

    if(String(msg.sender) == 'system'){
        
        const div = document.createElement("div");
        div.classList.add("mx-auto" , "m-1");
        const span = document.createElement("span");
        span.innerText = msg.chatContents;
        div.appendChild(span);

        return div;
    }else if (msg.sender != userId) {
        
        // 최상위 div
        const wrapper = document.createElement("div");
        wrapper.className = "d-flex flex-column align-items-start mb-2";

        // sender 이름
        const senderDiv = document.createElement("div");
        senderDiv.className = "mb-1 text-primary font-weight-bold small";
        senderDiv.textContent = msg.name;
        wrapper.appendChild(senderDiv);

        // 말풍선 + 시간 묶음
        const messageRow = document.createElement("div");
        messageRow.className = "d-flex";

        // 카드 (말풍선)
        const card = document.createElement("div");
        card.className = "card border-left-warning";
        card.style.maxWidth = "70%";

        const cardBody = document.createElement("div");
        cardBody.className = "card-body p-2";
        // ✨ 여기에만 조건 분기 추가 ✨
        if (msg.fileNum !== undefined && msg.fileNum !== null) {
            const link = document.createElement("a");
            link.href = `./fileDown?fileNum=${msg.fileNum}`;
            link.textContent = msg.chatContents;
            cardBody.appendChild(link);
        } else {
            cardBody.textContent = msg.chatContents;
        }

        card.appendChild(cardBody);

        // 시간
        const time = document.createElement("div");
        time.className = "ml-2 text-muted small align-self-end";
        time.textContent = formatDate(msg.sendDate);

        // 조립
        messageRow.appendChild(card);
        messageRow.appendChild(time);
        wrapper.appendChild(messageRow);

        return wrapper;
    } 
    else {
        const wrapper = document.createElement("div");
        wrapper.className = "d-flex justify-content-end mb-2";

        // 시간
        const time = document.createElement("div");
        time.className = "mr-2 text-muted small align-self-end";
        time.textContent = formatDate(msg.sendDate);

        // 카드 (말풍선)
        const card = document.createElement("div");
        card.className = "card border-left-secondary";
        card.style.maxWidth = "60%";

        const cardBody = document.createElement("div");
        cardBody.className = "card-body p-2";
        // ✨ 여기에만 조건 분기 추가 ✨
        if (msg.fileNum !== undefined && msg.fileNum !== null) {
            const link = document.createElement("a");
            link.href = `./fileDown?fileNum=${msg.fileNum}`;
            link.textContent = msg.chatContents;
            cardBody.appendChild(link);
        } else {
            cardBody.textContent = msg.chatContents;
        }

        card.appendChild(cardBody);

        // 조립
        wrapper.appendChild(time);
        wrapper.appendChild(card);

        return wrapper;
    }
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


//================================파일 업로드===========================

const fileUpload = document.getElementById("file-upload");

fileUpload.addEventListener("change", (e) => {
    let file = e.target.files[0];
    const formData = new FormData();
    formData.append("file", file);
    formData.append("chatRoomNum", chatNum);
    fetch("./fileUpload", {
        method: "POST",
        body: formData
    })
        .then(r => r.json())
        .then(r => {
            let m = new Message();
            m.chatContents = r.chatContentsVO.chatContents;
            m.chatRoomNum = r.chatContentsVO.chatRoomNum;
            m.flag = true;
            m.sendDate = r.chatContentsVO.sendDate;
            m.fileNum = r.chatFilesVO.fileNum;
            m.sender = r.chatContentsVO.sender;

            //console.log(m);
            stompClient.send("/pub/sendMessage", {}, JSON.stringify(m))
        })
})

//==========================채팅방 알람 켜기 끄기
const alarmBtn = document.getElementById("alarmBtn");

alarmBtn.addEventListener("click", () => {
    //console.log("!!!!!!!!!!")
    if (alarmBtn.getAttribute("data-ion-name") == "notifications") {
        const p = new URLSearchParams();
        p.append("chatNum", chatNum);

        fetch("/chat/updateAlarm", {
            method: "POST",
            body: p
        })
            .then(r => r.text())
            .then(r => {
                if (r * 1 == 1) {
                    alarmBtn.setAttribute("data-ion-name", 'notifications-outline')
                    alarmBtn.innerHTML = '<ion-icon name="notifications-outline"style="vertical-align: middle; font-size: 20px;"></ion-icon>'
                }
            })
    } else {
        const p = new URLSearchParams();
        p.append("chatNum", chatNum);

        fetch("/chat/updateAlarm", {
            method: "POST",
            body: p
        })
            .then(r => r.text())
            .then(r => {
                if (r * 1 == 1) {
                    alarmBtn.setAttribute("data-ion-name", 'notifications')
                    alarmBtn.innerHTML = '<ion-icon name="notifications"style="vertical-align: middle; font-size: 20px;"></ion-icon>'
                }
            })
    }
})

//==============채팅방 나갈 때 마지막으로 읽은 시간 업데이트 하기

function sendLastReadAt() {
  const url = "./updateLastReadAt";
  const params = new URLSearchParams();
  params.append("chatRoomNum", chatNum);

  //페이지 나갈때도 요청이 보내질 수 있게 요청
  try {
    if (navigator.sendBeacon) {
      // 모바일 및 데스크톱 모두에서 안정적으로 작동
      navigator.sendBeacon(url, params);
    } else {
      // sendBeacon이 없는 아주 오래된 브라우저 fallback
      fetch(url, {
        method: "POST",
        body: params,
        keepalive: true,
      });
    }
  } catch (e) {
    // fallback 시도 실패 → 무시
    console.error("Failed to send read time:", e);
  }
}

// 데스크톱: 페이지 언로드 시도
window.addEventListener("beforeunload", sendLastReadAt);

// 모바일 대응: 페이지 숨김 또는 닫힘
document.addEventListener("visibilitychange", () => {
  if (document.visibilityState === "hidden") {
    sendLastReadAt();
  }
});

//========채팅방 나가기
const exitBtn = document.getElementById("exitBtn");

exitBtn.addEventListener("click", ()=>{
    let p = new URLSearchParams();
    p.append("chatRoomNum", chatNum);
    let flag = confirm("채팅방을 나가시겠습니까?");
    if(flag){

        fetch("./exit",{
            method: "POST",
            body: p
        })
        .then(r=>r.text())
        .then(r => {
            if(r * 1 == 1){
                let m = new Message();
                m.sender = userId
                m.chatContents = 'exit'
                m.system = true;
                m.chatRoomNum = chatNum;
                stompClient.send("/pub/sendMessage", {}, JSON.stringify(m))

                location.href = "./main";
            }
        })
    } else{
        return;
    }
})

//채팅방 사람 목록 가지고 오기
const chatUsersList = document.getElementById("chatUsersList");
const chatUsersListArea = document.getElementById("chatUsersListArea");
chatUsersList.addEventListener("click", () => {

    let current = chatUsersListArea.nextElementSibling;
    while(current){
        const next = current.nextElementSibling;
        if(current.nodeType === 1){
            current.remove();
        }
        current = next;
    }

    //console.log("!!!!!")
    let p = new URLSearchParams();
    p.append("chatRoomNum", chatNum);
    fetch("./chatUsersList", {
        method: "POST",
        body: p
    })
    .then(r => r.json())
    .then(r => {
        for(j of r){
            const div = document.createElement("div");
            div.classList.add("dropdown-item-text", "w-100", "px-3" , "py-2", "existPerson")
            div.innerText = j.deptName + " " + j.name + " " + j.position;
            div.dataset.userId = j.userId;
            chatUsersListArea.after(div);
        }
    })
})