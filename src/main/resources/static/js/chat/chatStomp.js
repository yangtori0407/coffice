const socket = new SockJS("/ws-stomp");
const stompClient = Stomp.over(socket);

const chatInfo = document.getElementById("chatInfo");
const chatNum = chatInfo.getAttribute("data-chat-num");
const userId = chatInfo.getAttribute("data-user-id");
const chatInput = document.getElementById("chat-input");
const sendBtn = document.getElementById("chat-send-btn");

const chatBox = document.getElementById("chat-box");

window.addEventListener("load", function () {
    chatBox.scrollTop = chatBox.scrollHeight;
});

//클래스 이름은 반드시 대문자로 해야한다
class Message {
    chatNum = 0;
    chatRoomNum = "";
    chatContents = "";
    sendDate = "";
    sender = "";
    flag = false;
    fileNum = "";
}

stompClient.connect({}, function (frame) {
    console.log("Stomp 연결 성공: ", frame);

    stompClient.subscribe(`/sub/chatRoom.${chatNum}`, function (message) {
        const msg = JSON.parse(message.body); //서버에서 json으로 보낸걸 json 객체로 받음
        console.log(msg);
        let chat = displayReceiveMessage(msg);
        chatBox.append(chat);
        chatBox.scrollTop = chatBox.scrollHeight;
    })

    stompClient.subscribe(`/sub/chat/user.${userId}`, function (message) {
        const msg = JSON.parse(message.body);
        console.log("채팅알림!!!");
        console.log(msg);
        if (chatNum != msg.chatContentsVO.chatRoomNum) {
            createToast(msg);
        }
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
    if (msg.sender != userId) {
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

            console.log(m);
            stompClient.send("/pub/sendMessage", {}, JSON.stringify(m))
        })
})

//notification에서도 같이 수정하기
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

    
    container.appendChild(toast);

    // 자동 사라지기 (예: 3초 후)
    setTimeout(() => {
        toast.classList.add("hide"); // fade-out 시작
        setTimeout(() => toast.remove(), 1000); // transition 시간과 맞추기
    }, 1500); 
}


//==========================채팅방 알람 켜기 끄기
const alarmBtn = document.getElementById("alarmBtn");

alarmBtn.addEventListener("click", ()=>{
    console.log("!!!!!!!!!!")
    if(alarmBtn.getAttribute("data-ion-name") == "notifications"){
        const p = new URLSearchParams();
        p.append("chatNum",chatNum);

        fetch("/chat/updateAlarm",{
            method: "POST",
            body: p
        })
        .then(r=>r.text())
        .then(r =>{
            if(r * 1 == 1){
                alarmBtn.setAttribute("data-ion-name", 'notifications-outline')
                alarmBtn.innerHTML = '<ion-icon name="notifications-outline"style="vertical-align: middle; font-size: 20px;"></ion-icon>'
            }
        })
    }else{
        const p = new URLSearchParams();
        p.append("chatNum",chatNum);

        fetch("/chat/updateAlarm",{
            method: "POST",
            body: p
        })
        .then(r=>r.text())
        .then(r =>{
            if(r * 1 == 1){
                alarmBtn.setAttribute("data-ion-name", 'notifications')
                alarmBtn.innerHTML = '<ion-icon name="notifications"style="vertical-align: middle; font-size: 20px;"></ion-icon>'
            }
        })
    }
})