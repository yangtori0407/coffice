


const btn_temporary = document.getElementById("btn_temporary");

if(btn_temporary){
	btn_temporary.addEventListener("click", function() {
	  // 작성 완료 버튼의 기능에서 마지막 status의 입력 값만 바꿔주었다


	  // writerTime은 시간까지 저장해야하므로 java에서 생성해서 DB에 넣어준다
		// documentId와 formId를 제외한 writer 정보를 받아와준다
		
		// 작성자 이름
		const input_writerName = document.getElementById("input_writerName");
		const insert_writerName = document.getElementById("insert_writerName");
		
		if(!insert_writerName.innerText.trim()) {
		       alert("성함을 입력해주세요");
		       insert_writerName.focus();
		       return;
		    }

		    input_writerName.value = insert_writerName.innerText.trim();
		
		// 작성자 직급
		const input_writerPosition = document.getElementById("input_writerPosition");
		const insert_writerPosition = document.getElementById("insert_writerPosition");
		
		if(!insert_writerPosition.innerText.trim()) {
		       alert("직급을 입력해주세요");
		       insert_writerPosition.focus();
		       return;
		    }

		    input_writerPosition.value = insert_writerPosition.innerText.trim();
		
		// 작성자 부서
		const input_writerDept = document.getElementById("input_writerDept");
		const insert_writerDept = document.getElementById("insert_writerDept");
		
		if(!insert_writerDept.innerText.trim()) {
		       alert("부서를 입력해주세요");
		       insert_writerDept.focus();
		       return;
		    }

		    input_writerDept.value = insert_writerDept.innerText.trim();
				
		// 문서 제목
		const input_title = document.getElementById("input_title");
	    const insert_title = document.getElementById("insert_title");

	    if(!insert_title.innerText.trim()) {
	       alert("제목을 입력해주세요");
	       insert_title.focus();
	       return;
	    }

	    input_title.value = insert_title.innerText.trim();
	  
	  const input_content = document.getElementById("input_content");
	  const insert_content = document.getElementById("insert_content");
	  
	  input_content.value = insert_content.innerHTML;

	  //
	  


	  // 결재자 정보 추출 → JSON 문자열로 변환하여 hidden input에 삽입
	  const approvers = document.querySelectorAll(".employee_approval");
	  const approverList = [];

	  if(approvers.length === 0) {
	     alert("결재 대상을 지정해주세요");       
	     return;
	  }

	  approvers.forEach(approver => {
	      approverList.push({
	          userId: approver.dataset.selectedId,
	          userName: approver.dataset.selectedName,
	          userPosition: approver.dataset.selectedPosition
	      });
	  });
	  
	  const input_approvers = document.getElementById("input_approvers");
	  input_approvers.value = JSON.stringify(approverList);


	// 참조자 정보 추출 → JSON 문자열로 변환하여 hidden input에 삽입
	  const referrers = document.querySelectorAll(".employee_reference");
	  const referrerList = [];

	  referrers.forEach(referrer => {
	      referrerList.push({
	          userId: referrer.dataset.selectedId,
	          userName: referrer.dataset.selectedName,
	          userPosition: referrer.dataset.selectedPosition
	      });
	  });

	const input_referrers = document.getElementById("input_referrers");
	input_referrers.value = JSON.stringify(referrerList);

	console.log(input_referrers.value);

	  // 문서 status 데이터 삽입
	  const input_docuStatus = document.getElementById("input_docuStatus");
	  input_docuStatus.value = "임시저장";


	  
	  // 문서가 최초 임시저장이냐 기존 임시저장이 있냐에 따라 컨트롤러 경로를 바꿔준다
	  	  if(insert_content.dataset.voCheck =="임시저장"){
	  		  let formPath = "/document/updatetemp";
	  		  submitForm2(formPath);
	  	
	  	  } else if (insert_content.dataset.voCheck == ""){
	  	    let formPath = "/document/write";
	  		submitForm2(formPath);
	  	
	  	  }

	})
}



/////////////////////////////////////////////////
// 전체 input + 파일 포함해서 FormData로 전송하는 함수
let submitForm2 = function submitForm(formPath) {
	console.log("submitForm2 진입");  

  var formData = new FormData();

  // form 안의 input 요소들을 전부 formData에 추가
  var inputs = form.querySelectorAll("input[name]");
  for (var i = 0; i < inputs.length; i++) {
    var input = inputs[i];

    // type이 file인 input은 건너뜀
    if (input.type === "file") continue;

    formData.append(input.name, input.value);
  }

  // 신규 파일 추가 (같은 name으로 여러 개 append → 다중 파일 처리됨)  
  console.log("tempJS selectedFiles : " + selectedFiles.length);
  
  for (var j = 0; j < selectedFiles.length; j++) {
    formData.append("attaches", selectedFiles[j]);
  }
  
  // 기존 문서가 있는 경우 화면에 출력된 파일 num 배열도 보내기  
  let files = document.getElementsByClassName("exist-files");
  if(files) {
	
	  for (let file of files){
		formData.append("exists", file.dataset.fileNum);
	  }
	
  }

  
  // fetch API로 ajax 전송
    fetch(formPath, {
      method: "POST",
      body: formData
    })
      .then(response => {
        if (!response.ok) {
          throw new Error("서버 오류 발생");
        }
        return response.text(); // 혹은 .json() (컨트롤러 응답 타입에 따라)
      })
      .then(data => {
        console.log("성공:", data);
        alert("문서가 성공적으로 저장되었습니다");
        // 페이지 리다이렉트 또는 초기화 등 후속 처리
        location.href = data;
      })
      .catch(error => {
        console.error("에러:", error);
        alert("저장 중 오류가 발생했습니다");
      });
  
  
  
}




//------------------------------------------------
// 문서 회수 버튼
const btn_getBack = document.getElementById("btn_getBack");

if(btn_getBack){
	btn_getBack.addEventListener("click", function(){
		
		const documentId = btn_getBack.dataset.documentId;
		
		// form 요소 생성
	    const form = document.createElement("form");
	    form.method = "post";
	    form.action = "/document/updateonlystatus";
	
	    // hidden input 추가
	    const input = document.createElement("input");
	    input.type = "hidden";
	    input.name = "documentId";
	    input.value = documentId;
	    form.appendChild(input);
		
		// body에 form 추가 후 submit
	    document.body.appendChild(form);
	    form.submit();
		
	})
	
}



//------------------------------------------------
// 임시 문서 작성 취소 버튼
const btn_deletetemp = document.getElementById("btn_deletetemp");

if(btn_deletetemp){
	btn_deletetemp.addEventListener("click", function(){
		
		// 경고창 띄우기 만들어야함
		
		const documentId = btn_deletetemp.dataset.documentId;
		
		// form 요소 생성
	    const form = document.createElement("form");
	    form.method = "post";
	    form.action = "/document/deletetemp";
	
	    // hidden input 추가
	    const input = document.createElement("input");
	    input.type = "hidden";
	    input.name = "documentId";
	    input.value = documentId;
	    form.appendChild(input);
		
		// body에 form 추가 후 submit
	    document.body.appendChild(form);
	    form.submit();
		
	})
	
}


//------------------------------------------------
// 기존 파일 프론트에서 지우기
const removers = document.getElementsByClassName("exist-remover");

for(let remover of removers){
	remover.addEventListener("click", function(){
		remover.parentElement.remove();
	})
}


