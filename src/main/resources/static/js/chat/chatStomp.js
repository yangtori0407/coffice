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
}

stompClient.connect({}, function (frame) {
    console.log("Stomp 연결 성공: ", frame);

    stompClient.subscribe(`/sub/chatRoom.${chatNum}`, function (message) {
        const msg = JSON.parse(message.body); //서버에서 json으로 보낸걸 json 객체로 받음
        console.log("받은 메세지 " + msg);
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
        cardBody.textContent = msg.chatContents;
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
        cardBody.textContent = msg.chatContents;

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