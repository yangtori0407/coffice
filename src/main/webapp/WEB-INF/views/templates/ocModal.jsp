<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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

      .remove-pill {
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
          
          <c:choose>
          	<c:when test="${(empty docuVO && isWritePage eq 1) || docuVO.status eq '임시저장'}">
	            <button id="btn_toApprovers" type="button" class="btn btn-info" id="choose">결재선 넣기</button>
	            <button id="btn_toReferrers" type="button" class="btn btn-primary" id="choose">참조선 넣기</button>
	            <button type="button" class="btn btn-primary" id="choose" data-dismiss="modal">선택 완료</button>
          </c:when>
          
          <c:when test="${not empty docuVO && docuVO.status ne '임시저장' }">
          		
          </c:when>
          
          <c:otherwise>
          		<button type="button" class="btn btn-primary" id="choose" data-dismiss="modal">선택 완료</button>
          </c:otherwise>
          
          </c:choose>

          
          <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>          
        
        </div>


      </div>
      
    </div>
    
  </div>


  <script>

    const btn = document.getElementById("diagram")

    let depBoard = document.getElementById('depBoard')
    let empBoard = document.getElementById('empBoard')
    let choose = document.getElementById("choose")

    //사람 추가 후 다시 선택할 때 인원 수 제한 계산을 위한 초기화 과정 (chat)
    //채팅 최대 인원수
    let chatMax = 0;

    btn.addEventListener("click", () => {
      fetch("http://localhost/user/organizationChart", {
        method: "get"
      })
        .then(r => r.json())
        .then(r => {
          depBoard.innerHTML = '';
          empBoard.innerHTML = '';
          document.getElementById('selectedEmployees').innerHTML = '';
          let ul = document.createElement("ul")
          ul.setAttribute("id", 'departmentList')
          ul.classList.add('list-group', 'border-right')
          for (let a of r) {
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

          //chat add 페이지에서만 작동 되는 코드
          const chatPeople = document.querySelector("#chatPeople")
          if (chatPeople) {
            const chatUser = document.querySelectorAll(".chatUser");
            chatMax = chatUser.length;
          }
        })
    })



    depBoard.addEventListener('click', function (e) {
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
        fetch('http://localhost/user/getUsers?' + params, {
          method: "get"
        })
          .then(r => r.json())
          .then(r => {
            if (empBoard.innerHTML != "") {
              empBoard.innerHTML = "";
            }
            let ul = document.createElement("ul")
            ul.classList.add('list-group')
            for (let a of r) {
              let li = document.createElement("li")
              li.classList.add('list-group-item', 'employee-item')
              li.textContent = a.position + " " + a.name
              li.setAttribute('data-emp-id', a.userId);
              li.setAttribute('data-emp-name', a.name);
              li.addEventListener('click', (e) => {
                let empId = e.target.getAttribute("data-emp-id");
                
                //채팅----------------------------------------------------------------------------------
                //채팅방 추가시 본인 선택 불가
                const chatLoginUser = getUserIdCookie("userId");
                //console.log("chatLoginUser");
                if(chatLoginUser == empId){
                  alert("본인을 선택할 수 없습니다.");
                  return;
                }
                let duplication = document.querySelectorAll("div[class='employee-pill']");
                //console.log(e.target.getAttribute("data-emp-id"));
                if (duplication.length > 0) {

                  for (d of duplication) {
                    console.log("선택: ", d.dataset.selectedId);
                    if (d.dataset.selectedId == empId) {
                      alert("중복 선택입니다.");
                      return;

                    }
                  }
                }

                //채팅 최대 인원 수
                if (chatMax >= 25) {
                  alert("최대 인원은 25명까지 입니다.");
                  return;
                }
                chatMax++;
                //---------------------------------------------------------------------------------------------

                addSelectedEmployee(a.userId, a.name, a.position);
                
              });
              ul.appendChild(li)
            }
            empBoard.appendChild(ul)
          })
      }
    });

    function getUserIdCookie(name){
      return document.cookie
                      .split("; ")
                      .find(cookie => cookie.startsWith(name + "="))
                      ?.split("=")[1] ?? null;
    }



    function addSelectedEmployee(id, name, position) {

      const selectedArea = document.getElementById('selectedEmployees');

      // 중복 방지
      if (document.querySelector(`[data-selected-id="${id}"]`)) return;

      const pill = document.createElement('div');
      pill.className = 'employee-pill';
      pill.setAttribute('data-selected-id', id);
      pill.setAttribute('data-selected-name', name);
      pill.setAttribute('data-selected-position', position);

      const nameSpan = document.createElement('span');
      nameSpan.textContent = position + " " + name;

      const removeBtn = document.createElement('span');
      removeBtn.className = 'remove-pill';
      removeBtn.innerHTML = '&times;';
      removeBtn.addEventListener('click', () => {
        chatMax--;
        //console.log(chatMax)
        pill.remove();
      });

      pill.appendChild(nameSpan);
      pill.appendChild(removeBtn);
      selectedArea.appendChild(pill);
    }

    // 선택 완료 후
    choose.addEventListener("click", () => {
      let selected = document.querySelectorAll("div[class='employee-pill']")
      for (a of selected) {
        console.log(a.dataset.selectedId) // 사원번호 = a.dataset.selectedId
      }

      //채팅방 모달창 관련 코드-----------------------
      const s = document.querySelector("#chatPeople");
      if (s) {
        chat(selected);
        chatMax = selected.length;
      }
      //----------------------------


    })


    //=========채팅 만들기 관련 js
    function chat(selected) {

      for (a of selected) {
        console.log("chat" + a.dataset.selectedId) // 사원번호 = a.dataset.selectedId
      }

      const chatPeople = document.getElementById("chatPeople")

      createChat(selected, chatPeople);
    }

    //참여 인원 만드는 함수
    function createChat(selected, chatPeople) {
      for (a of selected) {

        const alertDiv = document.createElement("div");
        alertDiv.classList.add(
          "alert",
          "alert-secondary",
          "d-flex",
          "justify-content-between",
          "align-items-center",
          "chatUser"
        );
        alertDiv.setAttribute("role", "alert");
        alertDiv.setAttribute("data-user-id", a.dataset.selectedId);

        const div = document.createElement("div");
        div.classList.add("d-flex", "flex-column");

        // 2. span 요소 생성
        let nameSpan = document.createElement("span");
        nameSpan.textContent = a.dataset.selectedPosition;
        div.appendChild(nameSpan);
        nameSpan = document.createElement("span");
        nameSpan.textContent = a.dataset.selectedName;
        div.appendChild(nameSpan);

        // 3. button 요소 생성
        const deleteBtn = document.createElement("button");
        deleteBtn.classList.add("btn", "btn-sm", "delPerson");
        deleteBtn.setAttribute("type", "button");
        deleteBtn.textContent = "x";

        // 4. 자식 요소로 추가
        alertDiv.appendChild(div);
        alertDiv.appendChild(deleteBtn);

        chatPeople.appendChild(alertDiv);
      }
    }

  </script>