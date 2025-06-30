const messageCheck = document.querySelectorAll(".messageCheck");


messageCheck.forEach(m => {
    m.addEventListener("click", (e)=>{
        console.log("click")
        let messageNum = m.getAttribute("data-message-num");

        fetch(`/message/getCheck?messageNum=${messageNum}`)
        .then(r => r.json())
        .then(r => {
            //console.log(r);
            const messageCheckArea = document.getElementById(`messageCheckArea${r.messageNum}`);
            messageCheckArea.innerHTML = "";
            createCheck(r);
        })
    })
})

function createCheck(r){
    for(user of r.receiveUsers){     
        const messageCheckArea = document.getElementById(`messageCheckArea${r.messageNum }`);
        const div = document.createElement("div");
        div.classList.add("dropdown-item", "d-flex", "justify-content-between");
        const name = document.createElement("span")
        name.classList.add("mr-2");
        if(user.userId.includes("@")){
            name.innerText = user.userId;
        }else{
            name.innerText = `${user.deptName} ${user.name}`
        }
        
        const check = document.createElement("span"); 
        if(user.userId.includes("@")){
            check.innerText = "외부 이메일";
        }else{
            if(user.checkStatus){
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