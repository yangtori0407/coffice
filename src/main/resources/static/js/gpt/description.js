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
        console.log(r)
        let d;
        try{
            d = JSON.parse(r)
        }catch{
            d = null;
        }
        if(Array.isArray(d)){
            if(d[0].menuName){
                const menuName = d.map(m=>`${m.menuName}(₩${m.menuPrice})`);
                console.log(menuName);
                gptLoading.innerText = menuName.join(',\n')+'가 있습니다.';
            } else if(d[0].branchName){
                const branchName = d.map(b=>`${b.branchName}, ₩${b.totalSale}`);
                gptLoading.innerText = '지점별매출현황입니다.\n'+branchName.join('\n')
            } else if(d[0].ingredientsName) {
                const ingredientsName = d.map(i=>`${i.ingredientsName}(₩${i.ingredientsPrice},재고:${i.ingredientsStock})`);
                gptLoading.innerText = ingredientsName.join(',\n')+'가 있습니다'
            }
        }else if(typeof d === 'number'){
            gptLoading.innerText = `총 매출은 ₩${d}입니다.`
        } else {
            gptLoading.innerText=r
        }

        const lastMessage = document.createElement('div');
        lastMessage.style.height = '1px';
        chatBox.appendChild(lastMessage);
        lastMessage.scrollIntoView({ behavior: "smooth" });
    })
    document.getElementById("gptInput").value="";
    setTimeout(()=>{
        chatBox.scrollTop = chatBox.scrollHeight;
    },0);
})

document.getElementById("gptInput").addEventListener("keydown",(e)=>{
    if(e.key==="Enter"){
        e.preventDefault();
        sendGptBtn.click();
    }
})