<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <style>
      /* 부서 선택시 강조 스타일 */
      .selected-dept {
        background-color: #007bff;
        color: white;
      }
      .department-item:hover {
        cursor: pointer;
        background-color: #f1f1f1;
      }


      .employee-pill {
        border-radius: 50rem;
        padding: 0.4em 0.75em;
        margin: 0.25em;
        background-color: #007bff;
        color: white;
        display: inline-flex;
        align-items: center;
      }

      .employee-pill .remove-pill {
        margin-left: 0.5em;
        cursor: pointer;
        font-weight: bold;
      }
    </style>

    <!-- 조직도 모달 사용법 - 아래 주석 처리된 버튼 복붙해서 이용 -->
    <!-- Button trigger modal -->
    <!-- <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal" id="diagram">
    Launch demo modal
    </button> -->

    <!-- Modal -->
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">조직도</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body" style="height: 500px;">
        <div class="row">
            <div class="col border-right border-grey" style="height: 360px; overflow: auto;" id="depBoard"></div>
            <div class="col" style="height: 360px; overflow: auto;" id="empBoard"></div>
        </div>
        <hr>
        <div style="min-height: 100px; max-height: 100px; overflow: auto;" class="row">
          <div id="selectedEmployees">
            
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
        <button type="button" class="btn btn-primary" id="choose">선택 완료</button>
      </div>
      </div>
    </div>
    </div>


<script>
    
    const btn = document.getElementById("diagram")

    let depBoard = document.getElementById('depBoard')
    let empBoard = document.getElementById('empBoard')
    let choose = document.getElementById("choose")

    btn.addEventListener("click", ()=>{
        fetch("http://localhost/user/organizationChart", {
            method:"get"
        })
        .then(r=>r.json())
        .then(r=>{
            depBoard.innerHTML = '';
            empBoard.innerHTML = '';
            document.getElementById('selectedEmployees').innerHTML = '';
            let ul = document.createElement("ul")
            ul.setAttribute("id", 'departmentList')
            ul.classList.add('list-group', 'border-right')
            for(let a of r) {
                // let div = document.createElement("div")
                // div.setAttribute("data-dept-id", a.deptId)
                // div.innerText = a.deptName//.부서명
                // depBoard.appendChild(div)
                let li = document.createElement("li")
                li.classList.add('list-group-item', 'department-item')
                li.setAttribute("data-dept-id", a.deptId)
                li.innerText = a.deptName//.부서명
                ul.appendChild(li)
            }
            depBoard.appendChild(ul)
        })
    })

    depBoard.addEventListener('click', function(e) {
      if (e.target && e.target.classList.contains('department-item')) {
        // 기존 선택된 부서 해제
        document.querySelectorAll('.department-item').forEach(el => {
          el.classList.remove('selected-dept');
        });

        // 선택된 부서에 스타일 적용
        e.target.classList.add('selected-dept');

        const deptId = e.target.dataset.deptId;
        // 여기에 empBoard에 사원 목록 불러오는 fetch 추가

        let params = new URLSearchParams
        params.append("deptId", deptId)
        fetch('http://localhost/user/getUsers?'+params, {
          method: "get"
        })
        .then(r=>r.json())
        .then(r=>{
          if(empBoard.innerHTML != "") {
              empBoard.innerHTML = "";
          }
          let ul = document.createElement("ul")
          ul.classList.add('list-group')
          for(let a of r) {
              let li = document.createElement("li")
              li.classList.add('list-group-item', 'employee-item')
              li.textContent = a.position+" "+a.name
              li.setAttribute('data-emp-id', a.userId);
              li.setAttribute('data-emp-name', a.name);
              li.addEventListener('click', () => {
                addSelectedEmployee(a.userId, a.name, a.position);
              });
              ul.appendChild(li)
          }
          empBoard.appendChild(ul)
        })
      }
    });

    function addSelectedEmployee(id, name, position) {
      const selectedArea = document.getElementById('selectedEmployees');

      // 중복 방지
      if (document.querySelector(`[data-selected-id="${id}"]`)) return;

      const pill = document.createElement('div');
      pill.className = 'employee-pill';
      pill.setAttribute('data-selected-id', id);

      const nameSpan = document.createElement('span');
      nameSpan.textContent = position+" "+name;

      const removeBtn = document.createElement('span');
      removeBtn.className = 'remove-pill';
      removeBtn.innerHTML = '&times;';
      removeBtn.addEventListener('click', () => {
        pill.remove();
      });

      pill.appendChild(nameSpan);
      pill.appendChild(removeBtn);
      selectedArea.appendChild(pill);
    }

    // 선택 완료 후
    choose.addEventListener("click", ()=>{
      let selected = document.querySelectorAll("div[class='employee-pill']")
      for(a of selected) {
        console.log(a.dataset.selectedId) // 사원번호 = a.dataset.selectedId
      }
    })



</script>