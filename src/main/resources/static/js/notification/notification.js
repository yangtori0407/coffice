const socket = new SockJS("/ws-stomp");
const stompClient = Stomp.over(socket);

stompClient.connect({}, function (frame) {
    console.log("Stomp 연결 성공: ", frame);
    console.log("!!!!!!!!!");
    stompClient.subscribe(`/sub/notification`, function (message) {
        console.log("받음")
        const msg = JSON.parse(message.body); //서버에서 json으로 보낸걸 json 객체로 받음
        console.log("받은 메세지 " + msg);
    })
}, function (error) {
    console.error("stomp 연결 실패: ", error);
})

const noticeAdd = document.querySelector("#noticeAdd");

// if(noticeAdd){
//     noticeAdd.addEventListener("click", ()=>{
//         stompClient.send("/pub/notification", {}, "알림");
//     })
// }