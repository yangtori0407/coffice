


const btn_temporary = document.getElementById("btn_temporary");

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
  const input_files = document.getElementById("input_files");


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

  // 문서 status 데이터 삽입
  const input_docuStatus = document.getElementById("input_docuStatus");
  input_docuStatus.value = "임시저장";

  const form_document = document.getElementById("form_document");


  // 문서가 최초 임시저장이냐 기존 임시저장이 있냐에 따라 컨트롤러 경로를 바꿔준다
  if(insert_content.dataset.voCheck =="임시저장"){
    form_document.action="/document/updatetemp"
	form_document.submit(); // 경로를 바꿔서 컨트롤러로 보낸다

  } else if (insert_content.dataset.voCheck == ""){
    form_document.submit(); //기존 write경로를 컨트롤러로 보낸다

  }


})



//------------------------------------------------

const btn_getBack = document.getElementById("btn_getBack");

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










