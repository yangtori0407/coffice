const chatBox = document.getElementById("chatBox");
const sendGptBtn = document.getElementById("sendGptBtn");

sendGptBtn.addEventListener("click",()=>{
    const gptInput = document.getElementById("gptInput").value;

    if(!gptInput) {
        alert("입력하세요!")
        return;
    }

	    const myMsg = document.createElement('div');
	    myMsg.className = 'chat-bubble me';
	    myMsg.innerText = gptInput;
	    chatBox.appendChild(myMsg);
		
		const gptLoading = document.createElement('div');
		gptLoading.className = 'chat-bubble gpt';
		gptLoading.innerText = 'GPT가 입력 중입니다...';
		chatBox.appendChild(gptLoading);
		chatBox.scrollTop = chatBox.scrollHeight;

    fetch('/gpt/description',{
        method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({prompt:gptInput})
    })
    .then(r=>r.text())
    .then(r=>{
        
        gptLoading.innerText=r
    })
})