<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>COFFICE</title>
<link href="/images/3.png" rel="shortcut icon" type="image/x-icon">
<link
	href="https://cdn.jsdelivr.net/npm/quill@2.0.3/dist/quill.snow.css"
	rel="stylesheet" />
<c:import url="/WEB-INF/views/templates/header.jsp"></c:import>
<script type="module"
	src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
<script nomodule
	src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>

</head>

<body id="page-top">
	<div id="wrapper">
		<c:import url="/WEB-INF/views/templates/sidebar.jsp"></c:import>
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<c:import url="/WEB-INF/views/templates/top.jsp"></c:import>
				<div class="container-fluid">

					<!-- contents 내용 -->
					<form method="post">
						<label>제목</label> <input type="text"
							class="card mb-4 py-3 border-left-info" name="boardTitle"
							style="width: 100%;" value="${update.boardTitle}"> <label>내용</label>
						<div class="card" style="margin-bottom: 20px;">
							<div id="editor" style="height: 550px;"></div>
							<input type="hidden" id="quill_html" name="boardContents" value="<c:out value='${update.boardContents }' escapeXml='true'/>">
						</div>
						<div>
							<button id="finishBtn" class="btn btn-primary mb-3" type="submit">수정하기</button>
						</div>
					</form>
				</div>
			</div>
			<!-- end Content -->
			<c:import url="/WEB-INF/views/templates/foot.jsp"></c:import>
		</div>
		<!-- End Content Wrapper -->
	</div>
	<!-- End Wrapper -->
	<script src="https://cdn.jsdelivr.net/npm/quill@2.0.3/dist/quill.js"></script>
	<script>
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

	document.getElementById("finishBtn").addEventListener("click", () =>{
		isSubmitting = true;
	})

	window.addEventListener('beforeunload', (event) => {  
		// 표준에 따라 기본 동작 방지  
		if(!isSubmitting){
			event.preventDefault();  // Chrome에서는 returnValue 설정이 필요함  
			event.returnValue = '';
		}
	});
			
			
				
	</script>
	<c:import url="/WEB-INF/views/templates/footModal.jsp"></c:import>


</body>


</html>