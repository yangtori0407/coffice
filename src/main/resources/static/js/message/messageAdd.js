let quill = new Quill('#editor', {
    modules: {
        toolbar: [['bold', 'italic', 'underline', 'strike'], // 굵게, 기울임, 밑줄, 취소선
        ['blockquote', 'code-block'], // 인용 블록, 코드 블록

        [{
            'header': 1
        }, {
            'header': 2
        }], // 제목 크기
        [{
            'list': 'ordered'
        }, {
            'list': 'bullet'
        }], // 순서 있는 목록, 순서 없는 목록
        [{
            'script': 'sub'
        }, {
            'script': 'super'
        }], // 위첨자, 아래첨자
        [{
            'indent': '-1'
        }, {
            'indent': '+1'
        }], // 들여쓰기, 내어쓰기
        [{
            'direction': 'rtl'
        }], // 텍스트 방향

        [{
            'size': ['small', false, 'large', 'huge']
        }], // 폰트 크기
        [{
            'header': [1, 2, 3, 4, 5, 6, false]
        }], // 제목 레벨

        [{
            'color': []
        }, {
            'background': []
        }], // 폰트 색, 배경 색
        [{
            'font': []
        }], // 폰트 종류
        [{
            'align': []
        }], // 텍스트 정렬

        ['image'], // 링크, 이미지, 비디오 삽입

        ['clean'] // 포맷 지우기
        ]
    },
    placeholder: '내용을 입력하세요...', // placeholder 텍스트
    theme: 'snow'
});

quill.on("text-change", function () {
    document.getElementById("quill_html").value = quill.root.innerHTML;
})

quill.getModule('toolbar').addHandler("image", function () {
    selectLocalImage();
})

function selectLocalImage() {
    const fileInput = document.createElement("input");
    fileInput.setAttribute("type", "file");

    fileInput.click();

    fileInput.addEventListener("change", function () {
        const formData = new FormData();
        const file = fileInput.files[0];
        formData.append("uploadFile", file); //스프링에서 @RequestParam("uploadFile") 로 받아야한다.
        fetch("/board/quillUpload", {
            method: "POST",
            body: formData
        }).then(r => r.text())
            .then(r => {
                console.log(r);
                const range = quill.getSelection();
                quill.insertEmbed(range.index, 'image', "/files/" + r);

            })

    })

}

let isSubmitting = false;

document.getElementById("submitBtn").addEventListener("click", () => {
    isSubmitting = true;
    const messageTitle = document.getElementById("messageTitle");
    const quill = document.getElementById("quill_html");
    const replyEmail = document.getElementById("replyEmail");
    const receivers = document.querySelectorAll(".receiverPerson");

    const p = new URLSearchParams();
    p.append("messageContents",quill.value);
    p.append("messageTitle", messageTitle.value);
    p.append("replyEmail", replyEmail.value);

    receivers.forEach(e => {
        p.append("receivers", e.getAttribute("data-user-id"));
    })

    fetch("./add",{
        method: "POST",
        body: p
    })
    .then(r => r.text())
    .then(r => {

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

function createReceiver(s) {
    const div = document.createElement("div")
    div.classList.add("mr-2", "d-flex", "align-items-center")
    div.style.borderRadius = "10px";
    div.style.backgroundColor = "rgb(180, 182, 184)";
    div.style.color = "white";
    div.style.padding = "5px 10px";
    div.style.width = "250px";
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

