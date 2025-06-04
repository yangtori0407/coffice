

const approvalLine = document.getElementById("id_approvalLine");
const referenceLine = document.getElementById("id_referenceLine");

const approvalLine_wrapper = document.getElementById("id_approvalLine_wrapper");

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
                    wrapper.className = "grade-item";
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
                    stamp.style.height = "150px";
                    
					
					// 이름 div
                    const approver_name = document.createElement("div");
                    approver_name.className = "approver_name";
					approver_name.style.border = "1px solid gray";
                    approver_name.textContent = "이름";
                    approver_name.style.fontWeight = "bold";

                    // 조립
                    wrapper.appendChild(title);
                    wrapper.appendChild(stamp);
					wrapper.appendChild(approver_name);
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
		const employeeProfile = employee.data-profile; // 사원 클릭 시 결재칸에 이름 넣는 것 까지만 구현함 >> 작업 예정) 프로필 사진도 넣기 (25.06.04)

        // approver_name 중 "이름"인 칸 찾기
        const approverNames = document.getElementsByClassName("approver_name");
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
		
		
		
		// 중복 여부 확인
        // const existingIds = referenceLine.getElementsByClassName("referenceIds");
        // for (const refId of existingIds) {
        //     if (refId.textContent === employeeId) {
        //         alert("이미 선택된 사원입니다.");
        //         return;
        //     }
        // }
        
        // const reference = document.createElement("div");
        // reference.className = "references items";
		// reference.style.width = "50%";
        // reference.style.border = "1px solid black";
        // reference.style.marginTop = "5px";

        // const referenceName = document.createElement("div");
        // referenceName.className = "referenceNames";
        // referenceName.style.border = "1px solid black";
        // referenceName.textContent = employee.textContent;

        // const referenceId = document.createElement("div");
        // referenceId.className = "referenceIds";
        // referenceId.style.border = "1px solid black";		
        // referenceId.textContent = employee.id;
		
		// reference.appendChild(referenceName);
		// reference.appendChild(referenceId);
		
		// reference.addEventListener("click", function(){
		// 	reference.remove();
		// })

        // referenceLine.appendChild(reference);

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













