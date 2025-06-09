

const approvalLine = document.getElementById("id_approvalLine");
const referenceLine = document.getElementById("id_referenceLine");

const approvalLine_wrapper = document.getElementById("id_approvalLine_wrapper");
const employees_wrapper = document.getElementById("id_employees_wrapper");

const forms = document.getElementsByClassName("forms");

// form 클릭
for (const form of forms) {
    form.addEventListener("click", function () {
		
		// 모든 폼에서 선택 클래스 제거
        for (const f of forms) {
            f.classList.remove("selected");
        }

        // 클릭한 폼만 선택 표시
        form.classList.add("selected");
		
        console.log("form id:", form.id);

        const formData = new FormData();
        formData.append("formId", form.id);

        fetch("/document/form", {
            method: "POST",
            body: formData,
        })
        .then(response => response.json())
        .then(data => {
            console.log("formVO:", data);
            
            approvalLine.innerHTML = ""; // 초기화

            for (let i = 1; i <= 4; i++) {
                const gradeValue = data[`grade${i}`];
                if (gradeValue !== null && gradeValue !== "") {
                    // 부모 div
                    const wrapper = document.createElement("div");
                    wrapper.className = "approver-wrapper";
                    wrapper.style.marginBottom = "10px";

                    // 제목 div
                    const title = document.createElement("div");
                    title.className = "grade-title";
					title.style.border = "1px solid gray";
                    title.textContent = gradeValue;
                    title.style.fontWeight = "bold";

                    // 직인칸 div
                    const stamp = document.createElement("div");
                    stamp.className = "stamp-box";
                    stamp.style.border = "1px dashed gray";
					stamp.style.width = "120px";
                    stamp.style.height = "120px";
                    					
					// 이름 div
                    const approver_name = document.createElement("div");
                    approver_name.className = "approver_name";
					approver_name.style.border = "1px solid gray";
                    approver_name.textContent = "이름";
                    approver_name.style.fontWeight = "bold";

					// 이름 div
                    const approver_id = document.createElement("div");
                    approver_id.className = "approver_id";
					approver_id.style.border = "1px solid gray";
                    approver_id.textContent = "사번";
                    approver_id.style.fontWeight = "bold";

                    // 조립
                    wrapper.appendChild(title);
                    wrapper.appendChild(stamp);
					wrapper.appendChild(approver_name);
					wrapper.appendChild(approver_id);
                    approvalLine.appendChild(wrapper);
                }
            }
        })
        .catch(error => {
            console.error("에러 발생:", error);
        });
    });
}



const employees = document.getElementsByClassName("employees");

// employee 클릭
for (const employee of employees){
    employee.addEventListener("click", function(){
        console.log(employee.id);
		const employeeId = employee.id;
        const employeeName = employee.textContent;		
		//const employeeProfile = employee.profile; // 사원 클릭 시 결재칸에 이름 넣는 것 까지만 구현함 >> 작업 예정) 프로필 사진도 넣기 (25.06.04)

		if(flag == 1) {
	        // approver_name 중 "이름"인 칸 찾기
	        const approverNames = document.getElementsByClassName("approver_name");
	
	        // approver_name 요소가 아예 없는 경우: 양식을 아직 선택하지 않은 상태
	        if (approverNames.length === 0) {
	            alert("양식을 먼저 선택해 주세요.");
	            return;
	        }
	
	        let assigned = false;
	
	        for (const nameDiv of approverNames) {
	            if (nameDiv.textContent === "이름") {
	                nameDiv.textContent = employeeName;
	                assigned = true;
	                break;
	            }
	        }
	
	        if (!assigned) {
	            alert("결재 라인이 모두 채워졌습니다.");
	            return;
	        }

			// approver_id 중 "사번"인 칸 찾기
	        const approverIds = document.getElementsByClassName("approver_id");

			for (const idDiv of approverIds) {
	            if (idDiv.textContent === "사번") {
	                idDiv.textContent = employeeId;
	                assigned = true;
	                break;
	            }
	        }
			
		} else if(flag == 2) {
			//중복 여부 확인
	        const existingIds = referenceLine.getElementsByClassName("referenceIds");
	         for (const refId of existingIds) {
	             if (refId.textContent === employeeId) {
	                 alert("이미 선택된 사원입니다.");
	                 return;
	             }
	         }
	        
	         const reference = document.createElement("div");
	         reference.className = "referrer-wrapper items";
			 reference.style.width = "50%";
	         reference.style.border = "1px solid black";
	         reference.style.marginTop = "5px";
	
             const referencePosition = document.createElement("div");
	         referencePosition.className = "referencePositions";
	         referencePosition.style.border = "1px solid black";
	         referencePosition.textContent = employee.dataset.position;

	         const referenceName = document.createElement("div");
	         referenceName.className = "referenceNames";
	         referenceName.style.border = "1px solid black";
	         referenceName.textContent = employee.textContent;
	
	         const referenceId = document.createElement("div");
	         referenceId.className = "referenceIds";
	         referenceId.style.border = "1px solid black";		
	         referenceId.textContent = employee.id;
			
             reference.appendChild(referencePosition);
			 reference.appendChild(referenceName);
			 reference.appendChild(referenceId);
			
			 reference.addEventListener("click", function(){
			 	reference.remove();
			 })
	
	         referenceLine.appendChild(reference);
			
		}
		
		
		
		

    })
}


// ✅ 초기화 버튼 생성 및 기능
const resetButton = document.createElement("button");
resetButton.textContent = "결재자 초기화";
resetButton.style.marginTop = "10px";

resetButton.addEventListener("click", function () {
    const approverNames = document.getElementsByClassName("approver_name");
    for (const nameDiv of approverNames) {
        nameDiv.textContent = "이름";
    }
});

approvalLine_wrapper.appendChild(resetButton);


// 사원 넣을 곳 >> 토글 플래그
let flag = 1;

// 사원 집어넣을 곳 >> 결재 지정 버튼
const approvalButton = document.createElement("button");
approvalButton.textContent = "결재선에 넣기";
approvalButton.style.marginTop = "10px";
approvalButton.style.marginRight = "10px";
approvalButton.style.backgroundColor = "rgb(193, 201, 212)";

approvalButton.addEventListener("click", function () {
    flag = 1;
	// approvalButton 눌린 상태로 보이게 하기
	referenceButton.style.backgroundColor = "rgb(193, 201, 212)";
	approvalButton.style.backgroundColor = "rgb(74, 116, 180)";
});

employees_wrapper.appendChild(approvalButton);


// 사원 집어넣을 곳 >> 참조 지정 버튼
const referenceButton = document.createElement("button");
referenceButton.textContent = "참조선에 넣기";
referenceButton.style.marginTop = "10px";
referenceButton.style.backgroundColor = "rgb(193, 201, 212)";

referenceButton.addEventListener("click", function () {
    flag = 2;
	// referenceButton 눌린 상태로 보이게 하기
	approvalButton.style.backgroundColor = "rgb(193, 201, 212)";
	referenceButton.style.backgroundColor = "rgb(74, 116, 180)";
});

employees_wrapper.appendChild(referenceButton);


// 양식 설정 완료하기 버튼
const done_button = document.getElementById("id_done_button");

done_button.addEventListener("click", function(){

    // 양식 변수에 데이터들을 담음
    const selectedForm = document.querySelector(".forms.selected");
	if (!selectedForm) {
	    alert("양식을 선택해 주세요.");
	    return;
	}

    // 결재선 변수에 데이터들을 담음
	const approvers = [];
	const approverWrappers = document.getElementsByClassName("approver-wrapper");

	for (const wrapper of approverWrappers) {
	    const nameDiv = wrapper.querySelector(".approver_name");
	    const idDiv = wrapper.querySelector(".approver_id");
	    const positionDiv = wrapper.querySelector(".grade-title");

	    if (nameDiv && idDiv && positionDiv) {
	        approvers.push({
	            userId: idDiv.textContent.trim(),
	            name: nameDiv.textContent.trim(),
	            position: positionDiv.textContent.trim()
	        });
	    }
	}

    // 참조선 변수에 데이터들을 담음
    const referrers = [];
	const referrerWrappers = document.getElementsByClassName("referrer-wrapper");

	for (const wrapper of referrerWrappers) {
	    const nameDiv = wrapper.querySelector(".referenceNames");
	    const idDiv = wrapper.querySelector(".referenceIds");

	    if (nameDiv && idDiv) {
	        referrers.push({
	            userId: idDiv.textContent.trim(),
	            name: nameDiv.textContent.trim(),
	            position: "" // 참조자는 직급 정보 없을 경우 빈값 처리
	        });
	    }
	}

    // form 채우기
    document.getElementById("formId").value = selectedForm.id;
    document.getElementById("approvers").value = JSON.stringify(approvers);
    document.getElementById("referrers").value = JSON.stringify(referrers);

    // form 전송
    document.getElementById("makeForm").submit();



    /* formData 안쓰고 form 태그 사용

    // formData 객체 생성
    const formData = new FormData();

    // formId
    formData.append("formId", selectedForm.id);

    // approvers (userId, name, position)
    formData.append("approvers", JSON.stringify(approvers));

    // referrers (userId, name, position)
    formData.append("referrers", JSON.stringify(referrers));
    

	fetch("/document/make", {
        method: "POST",
        body: formData,
    })
    .then(response => {
        if (!response.ok) throw new Error("서버 응답 오류");
        return response.text();
    })
    .then(result => {
        console.log("등록 성공:", result);
        alert("문서가 성공적으로 등록되었습니다.");
        // location.href = "/document/list"; // 완료 후 페이지 이동 등 예시
    })
    .catch(error => {
        console.error("에러 발생:", error);
        alert("문서 등록 중 오류가 발생했습니다.");
    });
    */

})






