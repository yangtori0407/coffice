const addIngredientsBtn = document.getElementById("addIngredientsBtn")

addIngredientsBtn.addEventListener("click",()=>{

    const ingredientsName = document.getElementById("ingredientsName").value;
    const ingredientsPrice = document.getElementById("ingredientsPrice").value;
    const ingredientsFile = document.getElementById("ingredientsFile").files[0];

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

    const formData = new FormData();
    formData.append("ingredientsName", ingredientsName);
    formData.append("ingredientsPrice", ingredientsPrice);
    if(ingredientsFile!==undefined){
        formData.append("ingredientsFile", ingredientsFile);
    }

        fetch('/ingredients/add', {
            method: 'POST',
            body: formData
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