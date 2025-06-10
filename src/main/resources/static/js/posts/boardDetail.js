const delBtn = document.getElementById("delBtn");
const comBtn = document.getElementById("comBtn");
const boardNum = delBtn.getAttribute("data-board-num");

const inputContents = document.getElementById("contents");
const comArea = document.getElementById("comArea");

delBtn.addEventListener("click", () => {
    console.log("click")
    let param = new URLSearchParams();
    param.append("boardNum", boardNum);

    fetch("./delete", {
        method: "POST",
        body: param
    })
        .then(r => r.text())
        .then(r => {
            if (r * 1 == 1) {
                alert("삭제가 완료되었습니다.");
                location.href = "/board/list";
            } else {
                alert("삭제를 실패하였습니다.");
                location.href = "/board/list";
            }


        })
})

comBtn.addEventListener("click", () => {
    let u = new URLSearchParams();
    u.append("commentContents", inputContents.value);
    u.append("boardNum", boardNum);

    fetch("./addComment", {
        method: "POST",
        body: u
    })
        .then(r => r.json())
        .then(r => {
            createComment(r.commentContents, r.commentDate);
            inputContents.value = "";
        })
})

inputContents.addEventListener("keydown", (e) => {
    if (e.key == "Enter") {
        let u = new URLSearchParams();
        u.append("commentContents", inputContents.value);
        u.append("boardNum", boardNum);

        fetch("./addComment", {
            method: "POST",
            body: u
        })
            .then(r => r.json())
            .then(r => {
                createComment(r.commentContents, r.commentDate);
                inputContents.value = "";
            })
    }
})

function createComment(commentContents, date) {
    let div = document.createElement("div")
    div.classList.add("card", "mb-2", "py-1", "border-left-warning");

    let div2 = document.createElement("div");
    div2.classList.add("card-body");

    let div3 = document.createElement("div");
    let style = document.createAttribute("style");
    style.value = "font-size: 13px; margin-bottom: 10px;";
    div3.setAttributeNode(style);
    div3.innerText = formatDate(date);

    let div4 = document.createElement("div");
    div4.innerText = commentContents;

    comArea.prepend(div);
    div.appendChild(div2);
    div2.appendChild(div3);
    div2.appendChild(div4);
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

///===========================================================

comArea.addEventListener("click", )