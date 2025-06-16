

//
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


//
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


// "직인 가져오기" 버튼 클릭 시
document.getElementById('btn-bring-sign').addEventListener('click', function () {
    const wrapper = document.querySelector('.imageWrapper');

    // 파일 input과 이미지 미리보기를 imageWrapper 안에 넣음
    wrapper.innerHTML = `
        <input type="file" id="signFileInput" accept="image/*" style="margin-top:20px;">
        <img id="previewSignImage" style="max-height: 160px; margin-top: 10px;" />
    `;

    const input = document.getElementById('signFileInput');
    const preview = document.getElementById('previewSignImage');

    // 파일 선택 시 미리보기 이미지로 표시
    input.addEventListener('change', function () {
        const file = input.files[0]; // 사용자가 선택한 파일
        if (file) {
            const reader = new FileReader(); // 파일을 읽기 위한 객체
            reader.onload = function (e) {
                preview.src = e.target.result; // 읽은 데이터를 이미지 src에 대입
            };
            reader.readAsDataURL(file); // 파일을 base64 형태로 읽음
        }
    });
});


// "직접 그리기" 버튼 클릭 시
document.getElementById('btn-draw-sign').addEventListener('click', function () {
    const wrapper = document.querySelector('.imageWrapper');

    // 캔버스를 imageWrapper 안에 넣음
    wrapper.innerHTML = `
        <div>
            <label>선 굵기: 
                <select id="lineWidthSelect" class="form-select form-select-sm" style="width: 80px; display:inline-block;">
                    <option value="2">얇게</option>
                    <option value="4" selected>보통</option>
                    <option value="6">두껍게</option>
                </select>
            </label>

            <label style="margin-left: 10px;">색상: 
                <input type="color" id="lineColorPicker" value="#000000" style="vertical-align: middle;">
            </label>
        </div>

        <canvas id="signCanvas" width="160" height="160" 
            style="border:1px solid #ccc; background:white; cursor:crosshair;">
        </canvas>

        <div>
            <label style="margin-left: 10px;"> 
                <input type="text" id="signNameInput" placeholder="직인명 또는 용도를 적어주세요">
            </label>
        </div>

        <div style="margin-top: 10px;">
            <button id="clearCanvasBtn" class="btn btn-sm btn-warning">초기화</button>
        </div>
    `;

    const canvas = document.getElementById('signCanvas');
    const ctx = canvas.getContext('2d'); // 캔버스 2D 드로잉 컨텍스트

    // 선택된 선 옵션 가져오기
    function getCurrentLineOptions() {
        const lineWidth = parseInt(document.getElementById('lineWidthSelect').value, 10);
        const strokeStyle = document.getElementById('lineColorPicker').value;
        return { lineWidth, strokeStyle };
    }

    // 초기 배경: 흰색
    function clearCanvas() {
        ctx.fillStyle = "white";
        ctx.fillRect(0, 0, canvas.width, canvas.height);
    }

    clearCanvas(); // 초기 흰 배경

    let drawing = false;
    

    // 현재 캔버스가 비어 있는지 확인하는 함수
    function isCanvasBlank() {
        const pixelBuffer = new Uint32Array(
            ctx.getImageData(0, 0, canvas.width, canvas.height).data.buffer
        );
        return !pixelBuffer.some(color => color !== 0xFFFFFFFF); // 완전 흰색이면 true
    }

    canvas.addEventListener('mousedown', function (e) {
        // 새로 그리기를 시작할 때 캔버스가 비어 있지 않으면 자동 초기화
        // if (!isCanvasBlank()) {
        //     clearCanvas();
        // } (부가 기능)

        drawing = true;
        ctx.beginPath();
        ctx.moveTo(e.offsetX, e.offsetY);
    });

    // 마우스 움직일 때 선 그리기
    canvas.addEventListener('mousemove', function (e) {
        if (drawing) {
            const { lineWidth, strokeStyle } = getCurrentLineOptions();
            ctx.lineTo(e.offsetX, e.offsetY); // 현재 위치까지 선 연결
            ctx.strokeStyle = strokeStyle;
            ctx.lineWidth = lineWidth;
            ctx.stroke(); // 선 그리기
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
    });
    

});


// "추가하기" 버튼 클릭 시
document.getElementById('btn-send-sign').addEventListener('click', function () {
	const canvas = document.getElementById('signCanvas');	
	const signNameInput = document.getElementById("signNameInput");
    let signName = signNameInput.value.trim();
	const imageWrapper = document.getElementById("id_imageWrapper");
	
    if (!canvas) {
        alert("사인을 먼저 그려주세요.")
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
				
				// 사인 만들기 메뉴화면 초기화 후 모달 닫기				
				imageWrapper.innerHTML = '';
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
            })
            .catch(error => {
                console.error('업로드 중 오류:', error);
                alert('오류 발생');
            });

        }, 'image/png'); // PNG 포맷
    }

	
	// 구현 예정
    const fileInput = document.getElementById('signFileInput');
    if (fileInput && fileInput.files.length > 0) {
        const file = fileInput.files[0];
        console.log("선택한 이미지 파일:", file);
        // 이 파일을 FormData에 넣어 서버로 전송 가능
    }
	
	
});



//




