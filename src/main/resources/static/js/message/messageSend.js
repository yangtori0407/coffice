const messageCheck = document.querySelectorAll(".messageCheck");
const messageCheckArea = document.getElementById("messageCheckArea");

messageCheck.forEach(m => {
    m.addEventListener("click", ()=>{
        console.log("click")
        let messageNum = m.getAttribute("data-message-num");

        fetch(`/message/getCheck?messageNum=${messageNum}`)
        .then(r => r.json())
        .then(r => {
            console.log(r);
            messageCheckArea.innerHTML = "";
            createCheck(r.receiveUsers);
        })
    })
})

function createCheck(receiveUsers){
    for(r of receiveUsers){

        const div = document.createElement("div");
        div.classList.add("dropdown-item", "d-flex", "justify-content-between");
        const name = document.createElement("span")
        name.classList.add("mr-2");
        if(r.userId.includes("@")){
            name.innerText = r.userId;
        }else{
            name.innerText = `${r.deptName} ${r.name}`
        }
        
        const check = document.createElement("span");
        if(r.userId.includes("@")){
            check.innerText = "외부 이메일";
        }else{
            if(r.messageCheckNum == 0){
                check.innerText = "읽음";
            }else{
                check.innerText = "읽지 않음";
            }
            
        }
        
        div.appendChild(name);
        div.appendChild(check);

        messageCheckArea.appendChild(div);
    }
}