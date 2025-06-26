const delBtn = document.getElementById("delBtn");

delBtn.addEventListener("click", ()=>{
    const p = new URLSearchParams();
    p.append("messageNum", delBtn.getAttribute("data-message-num"));
    //p.append("kind", delBtn.getAttribute("data-message-kind"));

    fetch("./delete", {
        method: "POST",
        body: p
    })
    .then(r => r.text())
    .then(r => {
        if(r * 1 == 1){
            alert("삭제되었습니다.")
            const url = window.location.href;
            if(url.includes("receive")){
                location.href = "/message/receive";
            }else {
                location.href = "/message/send";
            }
        }
    })
})