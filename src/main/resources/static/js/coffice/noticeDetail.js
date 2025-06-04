const delBtn = document.getElementById("delBtn");

delBtn.addEventListener("click", ()=>{
    console.log("click")
    const noticeNum = delBtn.getAttribute("data-notice-num");
    let param = new URLSearchParams();
    param.append("noticeNum", noticeNum);

    fetch("./delete", {
        method: "POST",
        body: param
    })
    .then(r => r.text())
    .then(r =>{
        if(r*1 == 1){
            alert("삭제가 완료되었습니다.");
            location.href = "/notice/list";
        }

        
    })
})
