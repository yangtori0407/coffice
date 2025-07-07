document.addEventListener("DOMContentLoaded", function () {
  // 실시간 날짜/시간 표시
  function updateDateTime() {
    const now = new Date();
    const y = now.getFullYear();
    const m = (now.getMonth() + 1).toString().padStart(2, '0');
    const d = now.getDate().toString().padStart(2, '0');
    const h = now.getHours().toString().padStart(2, '0');
    const min = now.getMinutes().toString().padStart(2, '0');
    const s = now.getSeconds().toString().padStart(2, '0');
    const dateTime = `${y}-${m}-${d} ${h}:${min}:${s}`;
    document.getElementById("currentDateTime").innerText = dateTime;
  }
  setInterval(updateDateTime, 1000);
  updateDateTime();

  const checkInBtn = document.querySelector(".checkInBtn");
  const checkOutBtn = document.querySelector(".checkOutBtn");

  // 출근/퇴근 시간 표시 함수
  function formatTime(dateStr) {
    return new Date(dateStr).toLocaleTimeString('ko-KR', {
      hour: '2-digit',
      minute: '2-digit',
      second: '2-digit',
      hour12: false
    });
  }

  // 상태 불러오기
  function fetchTodayStatus() {
    fetch("/attendance/todayStatus")
      .then(res => res.json())
      .then(data => {
        if (data && data.startTime) {
          document.getElementById("startTime").innerText = formatTime(data.startTime);
          checkInBtn.style.display = "none";
          checkOutBtn.style.display = "inline-block";
        }
        if (data && data.endTime) {
          document.getElementById("endTime").innerText = formatTime(data.endTime);
          checkOutBtn.disabled = true;
        }
      });
  }

  fetchTodayStatus();

  // 출근 버튼 클릭 시
  checkInBtn.addEventListener("click", () => {
    checkInBtn.disabled = true;

    fetch("/attendance/checkIn", { method: "POST" })
      .then(res => res.json())
      .then(success => {
        if (success) {
          const now = new Date();
          const timeOnly = now.toLocaleTimeString('ko-KR', {
            hour: '2-digit',
            minute: '2-digit',
            second: '2-digit',
            hour12: false
          });
          document.getElementById("startTime").innerText = timeOnly;

          checkInBtn.style.display = "none";
          checkOutBtn.style.display = "inline-block";
        } else {
          alert("출근 등록 실패");
          checkInBtn.disabled = false;
        }
      })
      .catch(() => {
        alert("서버 오류");
        checkInBtn.disabled = false;
      });
  });

  // 퇴근 버튼 클릭 시
  checkOutBtn.addEventListener("click", () => {
    const confirmLogout = confirm("퇴근등록을 하시겠습니까?");
    if (!confirmLogout) return;

    fetch("/attendance/checkOut", { method: "POST" })
      .then(res => res.json())
      .then(success => {
        if (success) {
          const now = new Date();
          const timeOnly = now.toLocaleTimeString('ko-KR', {
            hour: '2-digit',
            minute: '2-digit',
            second: '2-digit',
            hour12: false
          });
          document.getElementById("endTime").innerText = timeOnly;
          checkOutBtn.disabled = true;
        } else {
          alert("퇴근 등록 실패");
        }
      });
  });
});
