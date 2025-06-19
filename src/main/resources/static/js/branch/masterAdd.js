const serviceKey = document.getElementById('serviceKeyHolder').dataset.servicekey;
function code_check(){
	    const contactNumber = document.getElementById('contactNumber').value;

	    if(contactNumber === "" || contactNumber.length !== 10){
	        alert("사업자등록번호를 10자리로 입력하세요.");
	        return;
	    }

	    var data = {
	        "b_no": [contactNumber]
	    };

		var xhr = new XMLHttpRequest();
		   xhr.open("POST", `https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=${serviceKey}`, true);
		   xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
		   xhr.setRequestHeader("Accept", "application/json");

		   xhr.onreadystatechange = function() {
		       if (xhr.readyState === 4) { // 요청 완료
		           if (xhr.status === 200) { // 정상 응답
		               var result = JSON.parse(xhr.responseText);
		               console.log(result);
		               if (result.match_cnt === 1 || result.match_cnt === "1") {
		                   alert("유효한 사업자등록번호입니다.");
		                   document.getElementById('hiddenContactNumber').value = contactNumber;
		               } else {
		                   alert("유효하지 않은 사업자등록번호입니다.");
		                   document.getElementById('hiddenContactNumber').value = "";
		               }
		           } else {
		               console.error("HTTP 요청 오류: ", xhr.status);
		               console.error(xhr.responseText);
		           }
		       }
		   };

		   xhr.send(JSON.stringify(data));
	}