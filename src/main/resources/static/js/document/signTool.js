

// 사인 선택하면 강조 기능
const signWrappers = document.getElementsByClassName("signWrapper");

for (const wrapper of signWrappers) {
    
    wrapper.addEventListener("click", function(){

        // 모든 wrapper의 테두리 초기화
        for (const other of signWrappers) {
        other.style.border = "none";
        other.classList.remove("selectedSign");
        }
        // 클릭된 것만 강조
        wrapper.style.border = "5px solid skyblue";
        wrapper.classList.add("selectedSign");
                        
    });
    
}


// 사인 사용하기 버튼
const useSign = document.getElementById("id_useSign");

useSign.addEventListener("click", function(){

    // 선택된 사인의 id를 가져와서
    const selectedSign = document.querySelector(".selectedSign");

    if(selectedSign){
        const selectedSignid = selectedSign.querySelector(".sign-id");
        const signId = selectedSignid.dataset.signId;

        // form 채우기				
        document.getElementById("signId").value = signId;
        // documentId는 html에서 먼저 넣음
        // userId는 html에서 먼저 넣음

        // form 전송
        document.getElementById("proceedForm").submit();

        
    } else {        
		alert("직인 또는 사인을 먼저 선택해주세요");
        return;
    }

})

//
// 사인 그리기 기능
const canvas = document.getElementById('signCanvas');
const canvasCtx = canvas.getContext('2d'); // 캔버스 2D 드로잉 컨텍스트

// 선택된 선 옵션 가져오기
function getCurrentLineOptions() {
    const lineWidth = parseInt(document.getElementById('lineWidthSelect').value, 10);
    const strokeStyle = document.getElementById('lineColorPicker').value;
    return { lineWidth, strokeStyle };
}

// 초기 배경: 흰색
function clearCanvas() {
    canvasCtx.fillStyle = "white";
    canvasCtx.fillRect(0, 0, canvas.width, canvas.height);
}

clearCanvas(); // 초기 흰 배경

let drawing = false;


// 현재 캔버스가 비어 있는지 확인하는 함수
function isCanvasBlank() {
    const pixelBuffer = new Uint32Array(
        canvasCtx.getImageData(0, 0, canvas.width, canvas.height).data.buffer
    );
    return !pixelBuffer.some(color => color !== 0xFFFFFFFF); // 완전 흰색이면 true
}

canvas.addEventListener('mousedown', function (e) {
    // 새로 그리기를 시작할 때 캔버스가 비어 있지 않으면 자동 초기화
    // if (!isCanvasBlank()) {
    //     clearCanvas();
    // } (부가 기능)

    drawing = true;
    canvasCtx.beginPath();
    canvasCtx.moveTo(e.offsetX, e.offsetY);
});

// 마우스 움직일 때 선 그리기
canvas.addEventListener('mousemove', function (e) {
    if (drawing) {
        const { lineWidth, strokeStyle } = getCurrentLineOptions();
        canvasCtx.lineTo(e.offsetX, e.offsetY); // 현재 위치까지 선 연결
        canvasCtx.strokeStyle = strokeStyle;
        canvasCtx.lineWidth = lineWidth;
        canvasCtx.stroke(); // 선 그리기
    }
});

// 마우스 떼면 그리기 중단
canvas.addEventListener('mouseup', function () {
    drawing = false;
});

// 캔버스 밖으로 나가도 그리기 중단
canvas.addEventListener('mouseleave', function () {
    drawing = false;
});

// 초기화 버튼을 누르면 캔버스를 비우고 다시 그릴 수 있게 설정
document.getElementById('clearCanvasBtn').addEventListener('click', function () {
    clearCanvas();
	const signNameInput = document.getElementById("signNameInput");
	signNameInput.value="";
});
    



//
// "추가하기" 버튼 클릭 시
document.getElementById('btn-send-sign').addEventListener('click', function () {

	const canvas = document.getElementById('signCanvas');

    if (!canvas) {
        alert("사인을 그려주세요")
        return;
    }

	const signNameInput = document.getElementById("signNameInput");
    let signName = signNameInput.value.trim();
	
    if (signName === "") {
        alert("사인 용도 또는 별명을 입력해주세요")
        return;
    }

    if (canvas) {
        // 캔버스를 Blob 형태의 이미지로 변환
        canvas.toBlob(function (blob) {
            const formData = new FormData();

            // Blob을 MultipartFile[]와 호환되는 형태로 append
            formData.append('attaches', blob, `${signName}`+'.png');			

            // signVO의 필드들도 함께 보내고 싶다면 formData.append로 추가
            // formData.append('signName', '홍길동');
            // formData.append('userId', 'test01');
            // ...

            fetch('./addSign', {
                method: 'POST',
                body: formData
            })
            .then(response => response.json())
            .then(data => {
				
				// 내용 초기화 후 모달 닫기
				clearCanvas();
				const signNameInput = document.getElementById("signNameInput");
				signNameInput.value="";
				$('#id-sign-maker').modal('hide');

                alert('서명이 업로드되었습니다.');
                
                const signList = document.getElementById('id_signList');
                signList.innerHTML = ''; // 기존 리스트 비우기

                data.forEach(i => {
                    const wrapper = document.createElement('div');
                    wrapper.className = 'signWrapper';
                    wrapper.innerHTML = `
                        <div class="sign-box" style="border: 1px dashed gray; width: 120px; height: 120px;">
                            <img src="/signs/${i.saveName}" style="width: 100%; height: 100%;">
                        </div>
                        <div class="sign-name" style="border: 1px solid gray; font-weight: bold;">${i.originName}</div>
                        <div class="sign-id" data-sign-id="${i.fileNum}"></div>
                    `;
                    signList.appendChild(wrapper);


                });
				
				// signWrapper 클릭 시 seleceted 옵션 날아가서 다시 부여
				const signWrappers = document.getElementsByClassName("signWrapper");

				for (const wrapper of signWrappers) {
				    
				    wrapper.addEventListener("click", function(){

				        // 모든 wrapper의 테두리 초기화
				        for (const other of signWrappers) {
				        other.style.border = "none";
				        other.classList.remove("selectedSign");
				        }
				        // 클릭된 것만 강조
				        wrapper.style.border = "5px solid skyblue";
				        wrapper.classList.add("selectedSign");
				                        
				    });
				    
				}
				
				
            })
            .catch(error => {
                console.error('업로드 중 오류:', error);
                alert('오류 발생');
            });

        }, 'image/png'); // PNG 포맷
    }

	
	
});



//




