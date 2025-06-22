

const btn_toApprovers = document.getElementById("btn_toApprovers");
const btn_toReferrers = document.getElementById("btn_toReferrers");


// 결재자 추가 버튼 누르기
if (btn_toApprovers) {
    btn_toApprovers.addEventListener("click", function(){
        const box_approvers = document.getElementById("box_approvers");
        // employee-pill 변동 없도록 Array로 따로 저장
        const employee_pills = Array.from(document.getElementsByClassName("employee-pill"));
		
		// 사원이 0명인지 체크
		if(employee_pills.length < 1){
			alert("추가할 사원을 지정해주세요");
			return;
		}
        
        // 사원이 최대치 4명 넘는지 체크
		if(employee_pills.length > 4){
			alert("결재자 지정은 최대 4명까지만 가능합니다");
			return;
		}
		
		// box 초기화 해주고
		box_approvers.innerHTML = '';
		
		// 사원들 다시 채워 넣기
        for(let employee of employee_pills) {
			// 클래스 초기화 후 employee_approval 클래스 추가
            employee.className = 'employee_approval';
            box_approvers.appendChild(employee);
        }
		
		
		// 위쪽 결재칸 영역 가져오기 (4칸)
        const approverWrappers = document.querySelectorAll('.approver-wrapper');

        // 모든 결재칸 초기화 + 숨기기
        for (let i = 0; i < approverWrappers.length; i++) {
            let wrapper = approverWrappers[i];
            wrapper.style.visibility = 'hidden';            
        }

        // employee 정보로 위 결재칸 채우기
        for (let i = 0; i < employee_pills.length; i++) {
            let employee = employee_pills[i];
            let wrapper = approverWrappers[i];
            if (!wrapper) continue;

            // ✅ wrapper 표시
            wrapper.style.visibility = 'visible';

            // 이름과 직급 추출 (span 내부 기준)
            let span = employee.querySelector('span');
            let text = span ? span.textContent.trim() : '';
            let split = text.split(' ');
            let name = split[0] || '';
            let position = split[1] || '';

            wrapper.querySelector('.grade-title').textContent = position;
            wrapper.querySelector('.approver_name').textContent = name;
            wrapper.querySelector('.stamp-box').innerHTML = ''; // 직인은 없음
        }
		
		
		

        
    })
}


// 참조자 추가 버튼 누르기
if (btn_toReferrers) {
    btn_toReferrers.addEventListener("click", function(){
        const box_referrers = document.getElementById("box_referrers");
        // employee-pill 변동 없도록 Array로 따로 저장
        const employee_pills = Array.from(document.getElementsByClassName("employee-pill"));
		
        // 사원이 0명인지 체크
		if(employee_pills.length < 1){
			alert("추가할 사원을 지정해주세요");
			return;
		}

		// 사원이 최대치 4명 넘는지 체크
		if(employee_pills.length > 10){
			alert("참조자 지정은 최대 10명까지만 가능합니다");
			return;
		}
		// box 초기화 해주고
		box_referrers.innerHTML = '';
		
		// 사원들 다시 채워 넣기
        for(let employee of employee_pills) {
			// 우측 결재 박스 채우기 > 클래스 초기화 후 employee_reference 클래스 추가
            employee.className = 'employee_reference';
            box_referrers.appendChild(employee);
			
			
			// 상단 결재 박스 채우기
			
			
        }
    })
}


// 우측 결재자, 참조자 박스 리셋버튼
const btn_resetApprovaers = document.getElementById("btn_resetApprovaers");
const btn_resetReferrers = document.getElementById("btn_resetReferrers");


btn_resetApprovaers.addEventListener("click", function(){
	const box_approvers = document.getElementById("box_approvers");
	box_approvers.innerHTML='';
})


btn_resetReferrers.addEventListener("click", function(){
	const box_referrers = document.getElementById("box_referrers");
	box_referrers.innerHTML='';
})


// 문서 작성 완료 버튼
const btn_complete = document.getElementById("btn_complete");

btn_complete.addEventListener("click", function() {	


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
    input_docuStatus.value = "진행중";
	
	const form_document = document.getElementById("form_document");
	form_document.submit()
})




// 본문 내용의 data 속성 값으로 documentId를 줘서 문서 정보가 null인지 판단한다.
// null이라면 tds들의 속성에 모두 contenteditable="true" 를 준다. >> 초기 작성 문서라면 내용 입력 가능 하도록 만듦
// null이라면 tds들의 속성에 모두 contenteditable="false" 를 준다. >> 기존 문서라면 내용 입력 불가능 하도록 만듦
const insert_content = document.getElementById("insert_content");
const tds = document.getElementsByClassName("tds");

// 문서 작성 취소 (나가기) 버튼
const btn_cancle = document.getElementById("btn_cancle");

if(insert_content.dataset.voCheck != "") { // docuVO가 있는 경우 : 상세조회 등 (!! 임시저장 문서에 관한 기능은 추가로 구현해야함)
    for(let td of tds){
		td.contentEditable = "false";
			
	}
	
	btn_cancle.addEventListener("click", function () {
			  
	    location.href = "/"; // 홈 경로로 이동
	  
	});
	
} else { // docuVO가 없는 경우 : 초기 작성 화면 
	for(let td of tds){
    	td.contentEditable = "true";
	}
	
	btn_cancle.addEventListener("click", function () {
	  const confirmed = confirm("작성 중인 내용은 저장되지 않습니다. 작성 화면에서 나가시겠습니까?");
	  if (confirmed) {
	    location.href = "/"; // 홈 경로로 이동
	  }
	});
			
}



// 행추가 버튼, 행삭제 버튼, 양식의 tbody1, trs(trs는 클래스)을 가져온다.
const add_row = document.getElementById("add-row");
const remove_row = document.getElementById("remove-row");

const tbody1 = document.getElementById("tbody1");
const trs = document.getElementsByClassName("trs");


    let tr1 = trs[0].cloneNode(true); // true로 해야 하위 요소 (td들)까지 복사된다.


// 행 추가
add_row.addEventListener("click", function () {
  const newRow = tr1.cloneNode(true);
  
  	// td 내용 초기화
	const tds = newRow.querySelectorAll("td");
	for (let i = 0; i < tds.length; i++) {
	  tds[i].textContent = "";
	}
  
  tbody1.appendChild(newRow);
});

// 행 삭제
remove_row.addEventListener("click", function () {
  const rows = tbody1.getElementsByClassName("trs");
  if (rows.length > 1) {
    tbody1.removeChild(rows[rows.length - 1]);
  } else {
    alert("최소 한 개의 행은 남아있어야 합니다.");
  }
});





