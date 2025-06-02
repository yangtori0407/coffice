<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


    <!-- 조직도 모달 사용법 - 아래 주석 처리된 버튼 복붙해서 이용 -->
    <!-- <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal" id="diagram">
    Launch demo modal
    </button> -->


<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="exampleModalLabel">조직도</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body" style="height: 400px;">
        <div class="row">
            <div class="col border-end border-grey" style="height: 360px;" id="depBoard"></div>
            <div class="col" style="height: 360px;" id="empBoard"></div>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
        <button type="button" class="btn btn-primary">선택 완료</button>
      </div>
    </div>
  </div>
</div>

<script>
    
    const btn = document.getElementById("diagram")

    let depBoard = document.getElementById('depBoard')

    btn.addEventListener("click", ()=>{
        fetch("부서 리스트를 불러올 요청url", {
            method:"get",
        })
        .then(r=>r.json())
        .then(r=>{
            if(depBoard.innerHTML != "") {
                depBoard.removeChild();
            }
            for(let a of r) {
                let div = document.createElement("div")
                div.innerText = a//.부서명
                depBoard.appendChild(div)
            }
        })

        // 부서에 속한 사원들 목록도 위와 같은 형식으로 불러온 후 empBoard엘리먼트에 appendChild
    })

</script>
