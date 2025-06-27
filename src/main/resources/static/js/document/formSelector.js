




const btn_formMenu = document.getElementById("btn-formmenu");
const formList = document.getElementById("id_formList");

if(btn_formMenu){

	btn_formMenu.addEventListener("click", function(){
		
		formList.innerHTML = '';
		
	    var formData = new FormData();
	  
	  	// fetch API로 ajax 전송
	    fetch("/document/selectform", {
	      method: "POST",
	      body: formData
	    })	      
	    .then(response => response.json())
	    .then(data => {
	      
	        console.log("폼 양식 조회 성공:", data);
	        
	        // 폼 양식 출력 작업
	        data.forEach(i => {
	            const wrapper = document.createElement('div');
	            wrapper.className = 'formWrappers d-flex align-items-center justify-content-center card border-left-info mb-2';
                
                wrapper.style.fontSize = 'larger';
                wrapper.style.fontWeight = 'bold';
                wrapper.style.color = 'gray';
				wrapper.style.width = '300px';
				wrapper.style.height = '50px';
				wrapper.style.flexShrink = '0';
				wrapper.style.margin = '0 auto';
                
                wrapper.dataset.formId = `${i.formId}`;
	            wrapper.innerHTML = `${i.name}`;
                
	            formList.appendChild(wrapper);
	
	
	        });
	        
	
	    })
	    .catch(error => {
	      console.error("에러:", error);
	      alert("폼 양식 조회 중 오류가 발생했습니다");
	    });
		
	})
	

}


// wrapper는 나중에 동적으로 생기는 요소라 const 가져오기 적용이 안될 수 있으므로 이벤트 위임
formList.addEventListener("click", function (e) {
	
	const wrapper = e.target.closest(".formWrappers");
	if (wrapper && formList.contains(wrapper)) {
		const formId = wrapper.dataset.formId;
		if (formId) {
			location.href = "/document/write?formId=" + formId;
		}
	}
	
});

