const notificationArea = document.getElementById("notificationArea");

const socket = new SockJS("/ws-stomp");
const stompClient = Stomp.over(socket);

class Notification {
    notiNum = 0;
    notiKind = "";
    notiContents = "";
    notiDate = "";
    relateEntity = "";
    relateId = "";
}

stompClient.connect({}, function (frame) {
    console.log("Stomp 연결 성공: ", frame);
    console.log("!!!!!!!!!");
    stompClient.subscribe(`/sub/notice`, function (message) {
        console.log("받음")
        const msg = JSON.parse(message.body); //서버에서 json으로 보낸걸 json 객체로 받음
        console.log(msg);
        msg.notiContents = "공지사항 " + msg.notiContents;
        createAlert(msg);
    })
}, function (error) {
    console.error("stomp 연결 실패: ", error);
})

function createAlert(msg) {

    const a = document.createElement("a");
    a.classList.add("dropdown-item", "d-flex", "align-items-center");
    if(msg.notiKind == "NOTICE"){
        a.href = `/notice/detail?noticeNum=${msg.relateId}`
    }else{

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

    const contentSpan = document.createElement("span");
    contentSpan.classList.add("font-weight-bold");
    contentSpan.innerText = msg.notiContents;

    textWrapper.appendChild(dateDiv);
    textWrapper.appendChild(contentSpan);

    // 전체 구성
    a.appendChild(iconWrapper);
    a.appendChild(textWrapper);

 
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
