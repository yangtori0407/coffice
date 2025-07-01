//quill load
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

//수정 전 내용이 들어가게 함함
const q = document.getElementById("quill_html");

quill.root.innerHTML = q.value;

//내용이 변경될 때마다 hidden input 안에 내용을 넣어 서버에 전달 할 수 있게 함함
quill.on("text-change", function () {
	document.getElementById("quill_html").value = quill.root.innerHTML;
})

quill.getModule('toolbar').addHandler("image", function () {
	selectLocalImage();
})

//이미지 업로드한거 하드디스크에 저장 후 url 주소로 반환환
function selectLocalImage() {
	const fileInput = document.createElement("input");
	fileInput.setAttribute("type", "file");

	fileInput.click();

	fileInput.addEventListener("change", function () {
		const formData = new FormData();
		const file = fileInput.files[0];
		formData.append("uploadFile", file); //스프링에서 @RequestParam("uploadFile") 로 받아야한다.
		fetch("/notice/quillUpload", {
			method: "POST",
			body: formData
		}).then(r => r.text())
			.then(r => {
				//console.log(r);
				const range = quill.getSelection();
				quill.insertEmbed(range.index, 'image', "/files/" + r);

			})

	})

}

// add btn
const fileAdd = document.getElementById("fileAdd");
const fileArea = document.getElementById("fileArea");
const attacheArea = document.getElementById("attacheArea");

const finishBtn = document.getElementById("finishBtn");

let max = parseInt(fileArea.getAttribute("data-file-length"));
let delAttach = [];

fileAdd.addEventListener("click", () => {

	if (max >= 5) {
		alert("첨부파일은 최대 5개만 가능합니다.");
		return;
	}

	const div = document.createElement("div");
	div.classList.add("d-flex", "mr-1")
	const file = document.createElement("input");
	file.setAttribute("type", "file");
	file.classList.add("form-control-file");
	file.setAttribute("name", "attaches");

	const del = document.createElement("button");
	del.setAttribute("type", "button")
	del.classList.add("btn", "btn-danger", "del");
	del.innerText = "X"

	div.appendChild(file);
	div.appendChild(del);

	fileArea.appendChild(div);
	max++;
})

//첨부파일 목록 삭제
fileArea.addEventListener("click", (e) => {
	if (e.target.classList.contains("del")) {
		//console.log("del");
		e.target.parentElement.remove();
		max--;
	}
})

//기존 첨부파일 목록 삭제
attacheArea.addEventListener("click", (e) => {
	if(e.target.classList.contains("attachDelete")){
		delAttach.push(e.target.getAttribute("data-file-num"));
		e.target.parentElement.remove();
		max--;
		//console.log(delAttach);
	}
})

//수정된 내용 전송
//기존 파일 중 삭제되는 애들도 보내야하기 때문에 fetch로 보낸다
const title = document.getElementById("noticeTitle");
const contents = document.getElementById("quill_html").value;


finishBtn.addEventListener("click", () => {
	//console.log("click");
	const formData = new FormData();
	const files = document.querySelectorAll('input[type="file"]');
	files.forEach((input, index) => {
		//input => input 태그 하나하나씩
		//input.files => 파일객체
		if(input.files.length > 0){
			formData.append("attaches", input.files[0]);
		}
		//console.log("파일파일")
	});
	
	formData.append("noticeTitle", title.value);
	formData.append("noticeContents", quill.root.innerHTML);
	for(let t of delAttach){
		formData.append("deleteFile", t);
	}
	formData.append("noticeNum", fileArea.getAttribute("data-notice-num"));

	fetch("/notice/update", {
		method: "POST",
		body: formData
	})
	.then(r => r.text())
	.then(r => {
		if(r * 1 == 1){
			alert("수정이 완료 되었습니다.");
			location.href = "./detail?noticeNum=" + fileArea.getAttribute("data-notice-num");
		}
	})
})
