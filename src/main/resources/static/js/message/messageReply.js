let quill = new Quill('#editor', {
    modules: {
        toolbar: [
            ['bold', 'italic', 'underline', 'strike'],
            ['blockquote', 'code-block'],
            [{ 'header': 1 }, { 'header': 2 }],
            [{ 'list': 'ordered' }, { 'list': 'bullet' }],
            [{ 'script': 'sub' }, { 'script': 'super' }],
            [{ 'indent': '-1' }, { 'indent': '+1' }],
            [{ 'direction': 'rtl' }],
            [{ 'size': ['small', false, 'large', 'huge'] }],
            [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
            [{ 'color': [] }, { 'background': [] }],
            [{ 'font': [] }],
            [{ 'align': [] }],
            ['image'],
            ['clean']
        ]
    },
    placeholder: '내용을 입력하세요...',
    theme: 'snow'
});

// 숨겨진 input에서 HTML 내용 꺼내기 (문자처럼 생긴 <p> 등 포함됨)
const q = document.getElementById("quill_html");

// HTML로 복원하는 코드
const temp = document.createElement("textarea");
temp.innerHTML = q.value; // &lt;p&gt; → <p>
const html = temp.value;  // 진짜 HTML로 바뀜

// Quill 에디터에 HTML 붙이기 (줄바꿈 포함해서)
quill.clipboard.dangerouslyPasteHTML(html);

// 수정되면 다시 hidden에 값 넣기
quill.on("text-change", function () {
    document.getElementById("quill_html").value = quill.root.innerHTML;
});

// 이미지 업로드 핸들링
quill.getModule('toolbar').addHandler("image", function () {
    selectLocalImage();
});

function selectLocalImage() {
    const fileInput = document.createElement("input");
    fileInput.setAttribute("type", "file");
    fileInput.click();

    fileInput.addEventListener("change", function () {
        const formData = new FormData();
        const file = fileInput.files[0];
        formData.append("uploadFile", file);

        fetch("/board/quillUpload", {
            method: "POST",
            body: formData
        })
            .then(r => r.text())
            .then(r => {
                const range = quill.getSelection();
                quill.insertEmbed(range.index, 'image', "/files/" + r);
            });
    });
}


let isSubmitting = false;

document.getElementById("submitBtn").addEventListener("click", () => {
    isSubmitting = true;
    const messageTitle = document.getElementById("messageTitle");
    const quill = document.getElementById("quill_html");
    
    const receivers = document.querySelectorAll(".receiverPerson");

    const p = new URLSearchParams();
    p.append("messageContents",quill.value);
    p.append("messageTitle", messageTitle.value);
   

    receivers.forEach(e => {
        p.append("receivers", e.getAttribute("data-user-id"));
    })

    fetch("./add",{
        method: "POST",
        body: p
    })
    .then(r => r.text())
    .then(r => {
        if(r > 0){
            alert("이메일 전송 했습니다.")
            location.href = "./send";
        }
    })
})

window.addEventListener('beforeunload', (event) => {
    // 표준에 따라 기본 동작 방지  
    if (!isSubmitting) {
        event.preventDefault();  // Chrome에서는 returnValue 설정이 필요함  
        event.returnValue = '';
    }
});


const emailInput = document.getElementById("emailInput")
const receiverArea = document.getElementById("receiverArea");

emailInput.addEventListener("keydown", (e) => {
    if (e.key == "Enter") {
        console.log("enter");
        receiverArea.insertBefore(createReceiver(emailInput.value), emailInput); //insertBefore(삽입할_노드, 기준_노드) 기준 노드 위에 삽입할 노드 넣는 방법
        emailInput.value = "";
    }
})
emailInput.addEventListener("blur", (e)=>{
    if(emailInput.value != ""){

        receiverArea.insertBefore(createReceiver(emailInput.value), emailInput); //insertBefore(삽입할_노드, 기준_노드) 기준 노드 위에 삽입할 노드 넣는 방법
        emailInput.value = "";
    }else{
        return;
    }
})

function createReceiver(s) {
    const div = document.createElement("div")
    div.classList.add("mr-2", "d-flex", "align-items-center")
    div.style.borderRadius = "10px";
    div.style.backgroundColor = "rgb(180, 182, 184)";
    div.style.color = "white";
    div.style.padding = "5px 10px";
    //div.style.width = "250px";
    div.style.display = "inline-block";
    div.style.fontSize = "14px";
    div.style.height = "30px";

    const span = document.createElement("span");
    span.classList.add("receiverPerson");
    span.dataset.userId = s;
    span.innerText = s


    const button = document.createElement("button")
    button.classList.add("btn", "btn-sm", "ml-auto", "p-0")
    button.innerText = "X"

    button.addEventListener("click", (e) => {
        e.target.parentElement.remove();
    })

    div.appendChild(span);
    div.appendChild(button);

    return div;
}

