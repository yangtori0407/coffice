


const btn_temporary = document.getElementById("btn_temporary");  

btn_temporary.addEventListener("click", function() {
  // 작성 완료 버튼의 기능에서 마지막 status의 입력 값만 바꿔주었다


  // formId 및 writer 정보는 이미 들어가 있으므로 데이터 입력 생략
	// writerTime은 시간까지 저장해야하므로 java에서 생성해서 DB에 넣어준다
	
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
form_document.submit()

})