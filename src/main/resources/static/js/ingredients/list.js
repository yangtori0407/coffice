const addIngredientsBtn = document.getElementById("addIngredientsBtn")

addIngredientsBtn.addEventListener("click",()=>{

    const ingredientsName = document.getElementById("ingredientsName").value;
    const ingredientsPrice = document.getElementById("ingredientsPrice").value;

    console.log(ingredientsName);
    console.log(ingredientsPrice);

        let c = confirm("정말 입력하시겠습니까?")
        if(!c){
            return;
        }

        if(ingredientsName==""){
            alert("상품을 입력하셔야합니다.")
            return;
        }

        if(ingredientsPrice==""){
            alert("수량을 입력하세요.")
            return;
        } else if(ingredientsPrice<=0){
            alert("0보다 작은수는 안됩니다.")
            return;
        }

    const params = new URLSearchParams();
        params.append("ingredientsName", ingredientsName);
        params.append("ingredientsPrice", ingredientsPrice);

        fetch('/ingredients/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: params
        })
        .then(r=>r.json())
        .then(r=>{
            console.log(r)
            if(r.status === 'success'){
                alert(r.message)
                location.reload();
            } else {
                alert(r.message)
            }
        })
        .catch(e=> {
            console.log(e)
            alert("오류");
        });
    });