const people = document.getElementById("people");

const addRoomBtn = document.getElementById("addRoomBtn");
const inputRoomName = document.getElementById("inputRoomName");

people.addEventListener("click", (e) => {
    if (e.target.classList.contains("delPerson")) {
        const p = e.target.parentElement;
        p.remove();
    }
})

class sendChatInfo {
    name="";
    users=[];
}

addRoomBtn.addEventListener("click", () => {
    let name = inputRoomName.value;
    let users = document.querySelectorAll(".user");
    let info = new sendChatInfo();

    info.name = name;

    users.forEach(element => {
        info.users.push(element.getAttribute("data-user-id"));
    });


    fetch("./addChat", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(info)
    })
    .then(r => r.json())
    .then(r => {
        console.log(r);
        if(r.flag == -1){
            let f =confirm("해당 사람들과 동일한 채팅방이 존재합니다.\n채팅방으로 이동하시겠습니까?")
            if(f){
                location.href = `./chatRoom?chatRoomNum=${r.chatRoomNum}`
            }else{
                location.href = "./main"
            }
        }else if(r.flag == 0){
            alert("채팅방 만들기를 실패했습니다.\n다시 시도하세요.");
        }else{
            let flag = confirm("채팅방으로 가시겠습니까?")
            if(flag){
                location.href = `./chatRoom?chatRoomNum=${r.chatRoomNum}`
            }else{
                location.href = "./main"
            }
        }
    })

})