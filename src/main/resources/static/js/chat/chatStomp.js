const socket = new SockJS("/ws-stomp");
const stompClient = Stomp.over(socket);

const chatInfo = document.getElementById("chatInfo");
const chatNum = chatInfo.getAttribute("data-chat-num");
const userId = chatInfo.getAttribute("data-user-id");
const chatInput = document.getElementById("chat-input");
const sendBtn = document.getElementById("chat-send-btn");

const chatBox = document.getElementById("chat-box");

//클래스 이름은 반드시 대문자로 해야한다
class Message{
    chatNum= 0;
    chatRoomNum = "";
    chatContents = "";
    sendDate = "";
    sender= "";
}

stompClient.connect({}, function(frame) {
    console.log("Stomp 연결 성공: ", frame);

    stompClient.subscribe(`/sub/chatRoom.${chatNum}`, function(message) {
        const msg = JSON.parse(message.body); //서버에서 json으로 보낸걸 json 객체로 받음
        console.log("받은 메세지 " + msg);
        displayReceiveMessage(msg);
    })
}, function(error){
    console.error("stomp 연결 실패: ", error);
})

sendBtn.addEventListener("click", () => {
    sendMessage();
    chatInput.value = "";
})

chatInput.addEventListener("keydown", (event) => {
  if (event.key === "Enter" && !event.shiftKey) {
    event.preventDefault();
    sendMessage();
    chatInput.value = "";
  }
});

function sendMessage(){
    let message = new Message();
    message.chatContents = chatInput.value.trim();
    message.chatRoomNum = chatNum;

    if(!chatInput.value) return;

    displaySenderMessage(message);

    stompClient.send("/pub/sendMessage", {}, JSON.stringify(message));
}

function displayReceiveMessage(msg){
    if(msg.sender != userId){

    }
}

function displaySenderMessage(msg){

}