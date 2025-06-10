const addReceiveBtn = document.getElementById("addReceiveBtn")
addReceiveBtn.addEventListener("click",()=>{
    const selectIngredients = document.getElementById("selectIngredients");
    const receive = document.getElementsByName("receive");
    let selectValue = null;
    const number = document.getElementById("number");

    for(let i=0;i<receive.length;i++){
        if(receive[i].checked){
            selectValue = receive[i].value;
            break;
        }
    }

    
    console.log(selectIngredients.value);
    console.log(number.value);
    
    const params = new URLSearchParams();
        if(selectValue){
            params.append("receive", selectValue);
        }
        params.append("number", number.value);
        params.append("ingredientsID",selectIngredients.value);

    fetch('/ingredients/addHistory',{
        method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: params
    })
    .then(r=>{
        console.log(r)
        if(r.ok){
            alert("처리되었습니다.")
            location.reload();
        } else {
                alert("다시추가부탁드립니다.")
        }
    })
    .catch(e=>{
        console.log(e)
        alert("오류")
    })
})