const addReceiveBtn = document.getElementById("addReceiveBtn")
const selectIngredients = document.getElementById("selectIngredients");
const receive = document.getElementsByName("receive");
let selectValue = null;
const number = document.getElementById("number");

addReceiveBtn.addEventListener("click",()=>{
    
    
    for(let i=0;i<receive.length;i++){
        if(receive[i].checked){
            selectValue = receive[i].value;
            break;
        }
    }

    if(selectValue === null){
        alert("입/출고가 필요합니다.")
        return;
    } else if(selectIngredients.value==="상품을 선택해주세요"){
        alert("상품을 선택해주세요")
        return;
    } else if(number.value===""){
        alert("수량을 입력해주세요.")
        return;
    }
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
    .then(r=>r.json())
    .then(r=>{
        console.log(r.message)
        if(r>0){
            alert("처리되었습니다.")
            location.reload();
        } else if(r.message=="출고불가") {
            alert("재고가 부족합니다.")
        } else {
            alert("다시 부탁드립니다.")
        }
    })
    .catch(e=>{
        console.log(e)
        alert("오류")
    })
})
//갯수 +,- 버튼
function count(type)  { 
  
  let number2 = number.value;
 
  if(type === 'plus') {
    if(number2==""){
        number2 += 1;
    }else {
        number2 = parseInt(number2) + 1;
    }
  }else if(type === 'minus')  {
    if(number2<=0){
        number2 == 0;
    } else {
        number2 = parseInt(number2) - 1;
    }
  }
  
  number.value = number2;
}