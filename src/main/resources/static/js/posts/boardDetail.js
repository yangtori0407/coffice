const delBtn = document.getElementById("delBtn");
const comBtn = document.getElementById("comBtn");
const board = document.getElementById("board");
const boardNum = board.getAttribute("data-board-num");

const inputContents = document.getElementById("contents");
const comArea = document.getElementById("comArea");

//원글 삭제 버튼
try {
	
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
} catch (error) {
	
}

//==============댓글=================================

//입력 버튼 눌렀을 때 댓글 작성되게 하는 코드
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
			console.log(r)
			createComment(r.commentContents, r.commentDate, r.commentNum);
			inputContents.value = "";
		})
})

//엔터키 눌렀을 때 댓글 작성되게 하는 것
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
				console.log(r);
				createComment(r.commentContents, r.commentDate, r.commentNum);
				inputContents.value = "";
			})
	}
})

//댓글 영역 생성
function createComment(commentContents, date, commentNum) {
	// 카드 전체
	const card = document.createElement("div");
	card.classList.add("card", "mb-2", "py-1", "border-left-warning");

	// row
	const row = document.createElement("div");
	row.classList.add("row", "w-100", "m-0");

	// 왼쪽 영역 (내용)
	const col10 = document.createElement("div");
	col10.classList.add("col-10", "card-body");

	const dateDiv = document.createElement("div");
	dateDiv.style.fontSize = "13px";
	dateDiv.style.marginBottom = "10px";
	dateDiv.innerText = formatDate(date);

	const contentDiv = document.createElement("div");
	contentDiv.innerText = commentContents;

	col10.appendChild(dateDiv);
	col10.appendChild(contentDiv);

	// 오른쪽 영역 (답글 + 드롭다운)
	const col2 = document.createElement("div");
	col2.classList.add("col-2", "d-flex", "align-items-start", "justify-content-end", "mt-1");

	// 답글 버튼
	const replyBtn = document.createElement("button");
	replyBtn.classList.add("btn", "btn-primary", "mr-4", "reply");
	replyBtn.setAttribute("type", "button");
	replyBtn.setAttribute("data-toggle", "collapse");
	replyBtn.setAttribute("data-target", `#collapse${commentNum}`);
	replyBtn.setAttribute("aria-expanded", "false");
	replyBtn.setAttribute("aria-controls", "collapseExample");
	replyBtn.setAttribute("data-comment-num", commentNum);
	replyBtn.innerText = "답글";

	// 드롭다운
	const dropdown = document.createElement("div");
	dropdown.classList.add("dropdown");

	const dropdownBtn = document.createElement("button");
	dropdownBtn.classList.add("btn", "dropdown-toggle", "p-0", "border-0", "bg-transparent");
	dropdownBtn.type = "button";
	dropdownBtn.setAttribute("data-toggle", "dropdown");
	dropdownBtn.setAttribute("aria-expanded", "false");

	const icon = document.createElement("ion-icon");
	icon.setAttribute("name", "ellipsis-vertical-outline");
	dropdownBtn.appendChild(icon);

	const menu = document.createElement("div");
	menu.classList.add("dropdown-menu", "dropdown-menu-right");

	const editItem = document.createElement("button");
	editItem.classList.add("dropdown-item", "commentUpBtn");
	editItem.setAttribute("type", "button");
	editItem.setAttribute("data-com-num", commentNum)
	editItem.innerText = "수정";

	const deleteItem = document.createElement("button");
	deleteItem.classList.add("dropdown-item", "commentDelBtn");
	deleteItem.setAttribute("type", "button");
	deleteItem.setAttribute("data-com-num", commentNum);
	deleteItem.innerText = "삭제";

	const collapseDiv = document.createElement("div");
	collapseDiv.classList.add("collapse");
	collapseDiv.id = `collapse${commentNum}`;

	menu.appendChild(editItem);
	menu.appendChild(deleteItem);
	dropdown.appendChild(dropdownBtn);
	dropdown.appendChild(menu);

	// 조립
	col2.appendChild(replyBtn);
	col2.appendChild(dropdown);

	row.appendChild(col10);
	row.appendChild(col2);
	card.appendChild(row);

	// DOM에 삽입
	comArea.prepend(collapseDiv);
	comArea.prepend(card);

	const addReply = document.getElementById(`collapse${commentNum}`)	
	createCommentInputBlock(addReply, commentNum);
				
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

///======================댓글> 답글 ============================

//처음 로드했을 때 답글 생성하고 시작하기
document.addEventListener("DOMContentLoaded", function () {
	let r = document.querySelectorAll(".reply")

	r.forEach(btn => {
		fetch(`./replyList?commentNum=${btn.getAttribute("data-comment-num")}`)
			.then(r => r.json())
			.then(r => {
				const addReply = document.getElementById(`collapse${btn.getAttribute("data-comment-num")}`)
				if (r.length > 0) {
					btn.innerText = "답글 " + r.length;
					createReplyDiv(r, addReply, btn.getAttribute("data-comment-num"));

				} else {
					createCommentInputBlock(addReply, btn.getAttribute("data-comment-num"));
				}
			})
	})
});


//답글 보내기
comArea.addEventListener("click", (e) => {
	if (e.target.classList.contains("replySend")) {
		const replyInput = e.target.parentElement.previousElementSibling.firstChild
		let comNum = e.target.getAttribute("data-reply-num");
		let p = new URLSearchParams();
		p.append("commentContents", replyInput.value);
		p.append("commentP", comNum);
		p.append("boardNum", boardNum);
		fetch("./reply", {
			method: "POST",
			body: p
		})
			.then(r => r.json())
			.then(r => {
				const area = e.target.parentElement.parentElement.parentElement
				createReplyClick(r, area);
				replyInput.value = "";
			})
	}
})

//답글의 입력창 영역 생성
function createCommentInputBlock(replyArea, num) {
	// 바깥 div.card-body
	const cardBody = document.createElement("div");
	cardBody.classList.add("card-body", "card", "mb-2", "ml-auto");
	cardBody.style.width = "97%";

	// row
	const row = document.createElement("div");
	row.classList.add("row");

	// col-11
	const col11 = document.createElement("div");
	col11.classList.add("col-11", "pr-1");

	const input = document.createElement("input");
	input.type = "text";
	input.name = "commentContents";
	input.placeholder = "답글을 입력하세요";

	input.classList.add("form-control", "border-0");
	input.style.boxShadow = "none";
	input.style.outline = "none";

	col11.appendChild(input);

	// col-1
	const col1 = document.createElement("div");
	col1.classList.add("col-1", "pl-1");

	const button = document.createElement("button");
	const comNum = document.createAttribute("data-reply-num");
	comNum.value = num;
	button.setAttributeNode(comNum);
	button.type = "button";
	button.classList.add("btn", "btn-success", "w-100", "replySend");
	button.innerText = "입력";

	col1.appendChild(button);

	// 조립
	row.appendChild(col11);
	row.appendChild(col1);
	cardBody.appendChild(row);

	replyArea.appendChild(cardBody);
}

//처음 답글 영역 생성
function createReplyDiv(r, addReply, commentNum) {

	for (let o of r) {

		// 카드 전체
		const card = document.createElement("div");
		card.classList.add("card", "card-body", "mb-2", "ml-auto");
		card.style.width = "97%"

		// row
		const row = document.createElement("div");
		row.classList.add("row", "w-100", "m-0");

		// 왼쪽 영역 (내용)
		const col10 = document.createElement("div");
		col10.classList.add("col-10");

		const dateDiv = document.createElement("div");
		dateDiv.style.fontSize = "13px";
		dateDiv.style.marginBottom = "10px";
		dateDiv.innerText = formatDate(o.commentDate);

		const contentDiv = document.createElement("div");
		contentDiv.innerText = o.commentContents;

		col10.appendChild(dateDiv);
		col10.appendChild(contentDiv);

		// 오른쪽 영역 (답글 + 드롭다운)
		const col2 = document.createElement("div");
		col2.classList.add("col-2", "d-flex", "align-items-start", "justify-content-end", "mt-1");
		// 드롭다운
		const dropdown = document.createElement("div");
		dropdown.classList.add("dropdown");

		const dropdownBtn = document.createElement("button");
		dropdownBtn.classList.add("btn", "dropdown-toggle", "p-0", "border-0", "bg-transparent");
		dropdownBtn.type = "button";
		dropdownBtn.setAttribute("data-toggle", "dropdown");
		dropdownBtn.setAttribute("aria-expanded", "false");

		const icon = document.createElement("ion-icon");
		icon.setAttribute("name", "ellipsis-vertical-outline");
		dropdownBtn.appendChild(icon);

		const menu = document.createElement("div");
		menu.classList.add("dropdown-menu", "dropdown-menu-right");

		// const editItem = document.createElement("button");
		// editItem.classList.add("dropdown-item", "replyUpBtn");
		// editItem.setAttribute("type", "button");
		// editItem.setAttribute("data-com-num", o.commentNum);
		// editItem.innerText = "수정";

		const deleteItem = document.createElement("button");
		deleteItem.classList.add("dropdown-item", "replyDelBtn");
		deleteItem.setAttribute("type", "button");
		deleteItem.setAttribute("data-com-num", o.commentNum);
		deleteItem.innerText = "삭제";

		menu.appendChild(deleteItem);
		dropdown.appendChild(dropdownBtn);
		dropdown.appendChild(menu);

		col2.appendChild(dropdown);

		row.appendChild(col10);
		row.appendChild(col2);
		card.appendChild(row);

		// DOM에 삽입
		addReply.prepend(card);
	}
	createCommentInputBlock(addReply, commentNum)
}

//답글 입력했을 때 영역 생성
function createReplyClick(comment, area) {
	const card = document.createElement("div");
	card.classList.add("card", "card-body", "mb-2", "ml-auto");
	card.style.width = "97%"

	// row
	const row = document.createElement("div");
	row.classList.add("row", "w-100", "m-0");

	// 왼쪽 영역 (내용)
	const col10 = document.createElement("div");
	col10.classList.add("col-10");

	const dateDiv = document.createElement("div");
	dateDiv.style.fontSize = "13px";
	dateDiv.style.marginBottom = "10px";
	dateDiv.innerText = formatDate(comment.commentDate);

	const contentDiv = document.createElement("div");
	contentDiv.innerText = comment.commentContents;

	col10.appendChild(dateDiv);
	col10.appendChild(contentDiv);

	// 오른쪽 영역 (답글 + 드롭다운)
	const col2 = document.createElement("div");
	col2.classList.add("col-2", "d-flex", "align-items-start", "justify-content-end", "mt-1");
	// 드롭다운
	const dropdown = document.createElement("div");
	dropdown.classList.add("dropdown");

	const dropdownBtn = document.createElement("button");
	dropdownBtn.classList.add("btn", "dropdown-toggle", "p-0", "border-0", "bg-transparent");
	dropdownBtn.type = "button";
	dropdownBtn.setAttribute("data-toggle", "dropdown");
	dropdownBtn.setAttribute("aria-expanded", "false");

	const icon = document.createElement("ion-icon");
	icon.setAttribute("name", "ellipsis-vertical-outline");
	dropdownBtn.appendChild(icon);

	const menu = document.createElement("div");
	menu.classList.add("dropdown-menu", "dropdown-menu-right");

	// const editItem = document.createElement("button");
	// editItem.classList.add("dropdown-item", "replyUpBtn");
	// editItem.setAttribute("type", "button");
	// editItem.setAttribute("data-com-num", comment.commentNum);
	// editItem.innerText = "수정";

	const deleteItem = document.createElement("button");
	deleteItem.classList.add("dropdown-item", "replyDelBtn");
	deleteItem.setAttribute("type", "button");
	deleteItem.setAttribute("data-com-num", comment.commentNum);
	deleteItem.innerText = "삭제";

	menu.appendChild(deleteItem);
	dropdown.appendChild(dropdownBtn);
	dropdown.appendChild(menu);

	col2.appendChild(dropdown);

	row.appendChild(col10);
	row.appendChild(col2);
	card.appendChild(row);

	// DOM에 삽입
	area.before(card);
}

///============================댓글 답글 삭제 / 수정=====================================================

//댓글 답글 삭제
comArea.addEventListener("click", (e) => {
	if (e.target.classList.contains("commentDelBtn")) {
		const commentNum = e.target.getAttribute("data-com-num");
		let p = new URLSearchParams();
		p.append("commentNum", commentNum);
		fetch("./commentDelete", {
			method: "POST",
			body: p
		})
			.then(r => r.text())
			.then(r => {
				if (r * 1 == 1) {

					const p = e.target.parentElement.parentElement.parentElement.previousElementSibling
					const coll = e.target.parentElement.previousElementSibling.parentElement
					coll.remove();
					p.innerText = "삭제된 댓글입니다."
				}
			})
	}
	if (e.target.classList.contains("replyDelBtn")) {
		const commentNum = e.target.getAttribute("data-com-num");
		let p = new URLSearchParams();
		p.append("commentNum", commentNum);
		fetch("./commentDelete", {
			method: "POST",
			body: p
		})
			.then(r => r.text())
			.then(r => {
				if (r * 1 == 1) {
					const p = e.target.parentElement.parentElement.parentElement.parentElement.parentElement
					p.remove();
				}
			})
	}
})

comArea.addEventListener("click", (e) => {
	if (e.target.classList.contains("commentUpBtn")) {
		const btnWrapper = e.target.parentElement.parentElement.parentElement; // 버튼이 들어갈 col-2
		const contentWrapper = btnWrapper.previousElementSibling; // 내용이 들어있는 col-10

		const originalText = contentWrapper.querySelector("div:last-child").innerText;
		contentWrapper.innerHTML = ""; // 기존 텍스트 제거

		// input 생성
		const input = document.createElement("input");
		input.type = "text";
		input.classList.add("form-control", "border-0");
		input.style.boxShadow = "none";
		input.style.outline = "none";
		input.value = originalText;

		contentWrapper.appendChild(input);

		// 버튼 영역 초기화
		btnWrapper.innerHTML = "";

		

		// 저장 버튼 생성
		const saveBtn = document.createElement("button");
		saveBtn.classList.add("btn", "btn-primary", "saveCommentBtn", "mr-4");
		saveBtn.setAttribute("data-com-num", e.target.getAttribute("data-com-num"));
		saveBtn.innerText = "저장";

		btnWrapper.appendChild(saveBtn);

		input.addEventListener("keydown", (event)=>{
			if(event.key == "Enter"){
				event.preventDefault();
				saveBtn.click();
			}
		})

		input.focus();
	}
});

comArea.addEventListener("click", (e) => {
	if (e.target.classList.contains("saveCommentBtn")) {
		const inputEl = e.target.parentElement.previousElementSibling.firstChild;
		const newContents = inputEl.value;
		const commentNum = e.target.getAttribute("data-com-num");

		const param = new URLSearchParams();
		param.append("commentContents", newContents);
		param.append("commentNum", commentNum);

		fetch("./commentUpdate", {
			method: "POST",
			body: param
		})
			.then(r => r.json())
			.then(updatedComment => {
				console.log(updatedComment);
				// 기존 card 내의 요소 참조
				const card = e.target.closest(".card");
				const leftCol = card.querySelector(".col-10");
				const rightCol = card.querySelector(".col-2");

				// 왼쪽 영역 초기화 후 재구성
				leftCol.innerHTML = "";

				const dateDiv = document.createElement("div");
				dateDiv.style.fontSize = "13px";
				dateDiv.style.marginBottom = "10px";
				dateDiv.innerText = formatDate(updatedComment.commentDate);

				const contentDiv = document.createElement("div");
				contentDiv.innerText = updatedComment.commentContents;

				leftCol.appendChild(dateDiv);
				leftCol.appendChild(contentDiv);

				// 오른쪽 영역도 원래대로 버튼 2개 넣기
				rightCol.innerHTML = "";

				// 답글 버튼
				const replyWrapper = document.createElement("div");
				replyWrapper.classList.add("d-flex", "flex-column", "mr-4");

				const replyBtn = document.createElement("button");
				replyBtn.classList.add("btn", "btn-primary", "reply");
				replyBtn.setAttribute("data-comment-num", updatedComment.commentNum);
				replyBtn.type = "button";
				replyBtn.setAttribute("data-toggle", "collapse");
				replyBtn.setAttribute("data-target", `#collapse${updatedComment.commentNum}`);
				replyBtn.setAttribute("aria-expanded", "false");
				replyBtn.setAttribute("aria-controls", "collapseExample");
				replyBtn.innerText = "답글";

				replyWrapper.appendChild(replyBtn);

				// 드롭다운
				const dropdown = document.createElement("div");
				dropdown.classList.add("dropdown");

				const dropdownBtn = document.createElement("button");
				dropdownBtn.classList.add("btn", "dropdown-toggle", "p-0", "border-0", "bg-transparent");
				dropdownBtn.type = "button";
				dropdownBtn.setAttribute("data-toggle", "dropdown");
				dropdownBtn.setAttribute("aria-expanded", "false");

				const icon = document.createElement("ion-icon");
				icon.setAttribute("name", "ellipsis-vertical-outline");
				dropdownBtn.appendChild(icon);

				const menu = document.createElement("div");
				menu.classList.add("dropdown-menu", "dropdown-menu-right");

				const editBtn = document.createElement("button");
				editBtn.classList.add("dropdown-item", "commentUpBtn");
				editBtn.setAttribute("data-com-num", updatedComment.commentNum);
				editBtn.type = "button";
				editBtn.innerText = "수정";

				const deleteBtn = document.createElement("button");
				deleteBtn.classList.add("dropdown-item", "commentDelBtn");
				deleteBtn.setAttribute("data-com-num", updatedComment.commentNum);
				deleteBtn.type = "button";
				deleteBtn.innerText = "삭제";

				menu.appendChild(editBtn);
				menu.appendChild(deleteBtn);
				dropdown.appendChild(dropdownBtn);
				dropdown.appendChild(menu);

				rightCol.appendChild(replyWrapper);
				rightCol.appendChild(dropdown);
			});
	}
});


