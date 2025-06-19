const radios = document.querySelectorAll('input[name="salesType"]');
const selectMenu = document.getElementById('selectMenu');
const orderBody = document.getElementById('orderBody');
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
    radio.addEventListener('change', function() {
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
            option.value = item.ingredientsId;
            option.text = item.ingredientsName;
        }
        selectMenu.appendChild(option);
      });
    });
  });