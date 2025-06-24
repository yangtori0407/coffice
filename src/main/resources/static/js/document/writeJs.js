

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


//########################
// 문서 작성 완료 버튼
const btn_complete = document.getElementById("btn_complete");

btn_complete.addEventListener("click", function() {	


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
    
	
	// 문서 컨텐츠
	const input_content = document.getElementById("input_content");
	const insert_content = document.getElementById("insert_content");
    
    input_content.value = insert_content.innerHTML;
	

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
	
	
	// 문서가 최초 작성완료이냐 임시저장 문서를 작성완료이냐에 따라 컨트롤러 경로를 바꿔준다
	  if(insert_content.dataset.voCheck =="임시저장"){
		  let formPath = "/document/updatetemp";
		  submitForm(formPath);
	
	  } else if (insert_content.dataset.voCheck == ""){
	    let formPath = "/document/write";
		submitForm(formPath);
	
	  }
	  
	  
})




// 본문 내용의 data 속성 값으로 documentId를 줘서 문서 정보가 null인지 판단한다.
// null이라면 tds들의 속성에 모두 contenteditable="true" 를 준다. >> 초기 작성 문서라면 내용 입력 가능 하도록 만듦
// null이라면 tds들의 속성에 모두 contenteditable="false" 를 준다. >> 기존 문서라면 내용 입력 불가능 하도록 만듦

// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
// 임시 기능 추가로 documentId가 아닌 status로 구분하는 것으로 변경한다.
// data 속성 값으로 status를 줘서 구분한다
// status가 null 또는 "임시저장"이라면 contenteditable="true"
// 그 이외 경우는 contenteditable="false"를 준다

const insert_content = document.getElementById("insert_content");
const tds = document.getElementsByClassName("tds");
const informations = document.getElementsByClassName("informations");


if(insert_content.dataset.voCheck == "" || insert_content.dataset.voCheck =="임시저장") { 
    // docuVO가 없는 경우 : 초기 작성 화면 또는 docuVO가 있지만 임시저장 상태인 경우	

    for(let info of informations){
        info.contentEditable = "true";
    }
	
    for(let td of tds){
		td.contentEditable = "true"; // 편집 가능
			
	}
	
    // 문서 작성 취소 (나가기) 버튼
    const btn_cancle = document.getElementById("btn_cancle");
	btn_cancle.addEventListener("click", function () {
			  
        const confirmed = confirm("작성 중인 내용은 저장되지 않습니다. 작성 화면에서 나가시겠습니까?");
        if (confirmed) {
          location.href = "/"; // 홈 경로로 이동
        }
        
	});
	
} else {
    // docuVO가 있는 경우 : 상세조회 (결재중, 반려, 승인완료 상태 등의 문서)

    for(let info of informations){
        info.contentEditable = "false";
    }

	for(let td of tds){
        td.contentEditable = "false"; // 편집 불가
	}
	
    // 문서 작성 취소 (나가기) 버튼
    const btn_cancle = document.getElementById("btn_cancle");
	btn_cancle.addEventListener("click", function () {
        
        window.history.back();

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



// 첨부 파일 관련 기능 ////////////////////////////////////////////
var uploadBtn = document.getElementById("uploadBtn");
var fakeInput = document.getElementById("fake_input_files");
var fileListDiv = document.getElementById("fileList");
var form = document.getElementById("form_document");
var selectedFiles = [];

// 파일 선택 버튼 클릭 → input 클릭
uploadBtn.addEventListener("click", function () {
  fakeInput.click();
});

// 파일 선택 시 → 누적 리스트에 추가
fakeInput.addEventListener("change", function (event) {
  var files = Array.from(event.target.files);
  for (var i = 0; i < files.length; i++) {
    var file = files[i];
    selectedFiles.push(file);
	console.log(selectedFiles.length);
    addFileToView(file);
  }

  // 같은 파일 다시 선택할 수 있도록 초기화
  fakeInput.value = "";
});

// 파일 미리보기용 wrapper 생성
function addFileToView(file) {
  var wrapper = document.createElement("div");
  wrapper.className = "file-wrapper";
 

  var nameDiv = document.createElement("div");
  nameDiv.textContent = "파일명: " + file.name;

  var sizeDiv = document.createElement("div");
  sizeDiv.textContent = "크기: " + (file.size / 1024).toFixed(1) + " KB";

  var deleteBtn = document.createElement("button");
  deleteBtn.textContent = "X";
  deleteBtn.type = "button";
  deleteBtn.onclick = function () {
    wrapper.remove();
    var newList = [];
    for (var i = 0; i < selectedFiles.length; i++) {
      if (selectedFiles[i] !== file) {
        newList.push(selectedFiles[i]);
      }
    }
    selectedFiles = newList;
  };

  wrapper.appendChild(deleteBtn);
  wrapper.appendChild(nameDiv);
  wrapper.appendChild(sizeDiv);
  fileListDiv.appendChild(wrapper);
}

// 전체 input + 파일 포함해서 FormData로 전송하는 함수
let submitForm = function submitForm(formPath) {  

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
  for (var j = 0; j < selectedFiles.length; j++) {
    formData.append("attaches", selectedFiles[j]);
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



