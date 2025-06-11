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

quill.on("text-change", function() {
	document.getElementById("quill_html").value = quill.root.innerHTML;
})

quill.getModule('toolbar').addHandler("image", function() {
	selectLocalImage();
})

function selectLocalImage() {
	const fileInput = document.createElement("input");
	fileInput.setAttribute("type", "file");

	fileInput.click();

	fileInput.addEventListener("change", function() {
		const formData = new FormData();
		const file = fileInput.files[0];
		formData.append("uploadFile", file); //스프링에서 @RequestParam("uploadFile") 로 받아야한다.
		fetch("/notice/quillUpload", {
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

// add btn
const fileAdd = document.getElementById("fileAdd");
const fileArea = document.getElementById("fileArea");

let max = 1;

fileAdd.addEventListener("click", () => {
	

	if(max >= 5){
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

fileArea.addEventListener("click", (e) =>{
	if(e.target.classList.contains("del")){
		console.log("del");
		e.target.parentElement.remove();
		max--;
	}
})