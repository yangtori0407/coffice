const radios = document.querySelectorAll('input[name="salesType"]');
const selectMenu = document.getElementById('selectMenu');
const orderBody = document.getElementById('orderBody');
const addPurchase = document.getElementById('addPurchase');
const modalFooter = document.getElementsByClassName('modal-footer');
let importOptions = [];
let expenditureOptions =[];
  fetch('/ingredients/menuList')
  .then(r=>r.json())
  .then(r=>{
        importOptions=r;
  })

    fetch('/ingredients/ingredientsList')
  .then(r=>r.json())
  .then(r=>{
        expenditureOptions=r;
  })

  radios.forEach(function(radio) {
    radio.addEventListener('change', function(e) {
      // 기존 option 모두 삭제
      selectMenu.innerHTML = '';

      // 안내용 첫 옵션 추가
      const defaultOption = document.createElement('option');
      const isImport = this.id === 'import'
      defaultOption.text = isImport ? '메뉴를 선택해주세요' : '식자재를 선택해주세요';
      defaultOption.selected = true;
      defaultOption.disabled = true;
      selectMenu.appendChild(defaultOption);

      // 선택에 따라 옵션 채우기
      const optionsToAdd = this.id === 'import' ? importOptions : expenditureOptions;

      optionsToAdd.forEach(function(item) {

        const option = document.createElement('option');
        if(isImport){
            option.value = item.menuId;
            option.text = item.menuName;
        } else {
            option.value = item.ingredientsID;
            option.text = item.ingredientsName;
        }
        selectMenu.appendChild(option);
      });

      const oldButton = document.querySelector('#addImport, #addExpenditure');

        if (oldButton) oldButton.remove();

            
            let button = document.createElement('button');
            button.className = isImport ? "btn btn-primary": "btn btn-danger";
            button.id = isImport ? "addImport" : "addExpenditure";
            button.innerText = isImport ? "수입 추가" : "지출 추가";

          
            button.addEventListener('click', function() {
                let c = confirm("정말 입력하시겠습니까?")
                if(!c){
                    return;
                }
            const selectedMenuId = selectMenu.value;
            const salesQuantity = document.getElementById('salesQuantity').value;
            
  
            let params = new URLSearchParams();

            if(isImport){
                
                params.append("menuId",selectedMenuId);
                params.append("salesQuantity",salesQuantity);
            }else {
                params.append("ingredientsId",selectedMenuId);
                params.append("salesQuantity",salesQuantity);
            }

            isImport ?
            fetch('/ingredients/profit',{
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: params
            })
            .then(r=>r.json())
            .then(r=>{
                console.log(r)
                if(r>0){
                    alert("처리되었습니다.")
                    location.reload();
                }else {
                    alert("다시부탁드립니다.")
                }
            })
            .catch(e=>{
                console.error(e)
                alert("오류")
            }) :
            fetch('/ingredients/expenditure',{
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: params
            })
            .then(r=>r.json())
            .then(r=>{
                console.log(r)
                if(r>0){
                    alert("처리되었습니다.")
                    location.reload();
                }else {
                    alert("다시부탁드립니다.")
                }
            })
            .catch(e=>{
                console.error(e)
                alert("오류")
            })
        });

        modalFooter[0].appendChild(button);
    });
  });
