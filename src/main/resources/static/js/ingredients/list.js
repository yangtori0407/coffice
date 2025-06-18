const addIngredientsBtn = document.getElementById("addIngredientsBtn")

addIngredientsBtn.addEventListener("click",()=>{

    const ingredientsName = document.getElementById("ingredientsName").value;
    const ingredientsPrice = document.getElementById("ingredientsPrice").value;

    console.log(ingredientsName);
    console.log(ingredientsPrice);

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