

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
	
	
	const form_document = document.getElementById("form_document");
	form_document.submit()
})


// 본문 내용의 data 속성 값으로 documentId를 줘서 문서 정보가 null인지 판단한다.
// null이라면 tds들의 속성에 모두 contenteditable="true" 를 준다.
// null이라면 tds들의 속성에 모두 contenteditable="false" 를 준다.

const insert_content = document.getElementById("insert_content");
const tds = document.getElementsByClassName("tds");

if(insert_content.dataset.voCheck != "") {
    for(let td of tds){
		td.contentEditable = "false";	
	}
	
} else {
	for(let td of tds){
    	td.contentEditable = "true";
	}
}











