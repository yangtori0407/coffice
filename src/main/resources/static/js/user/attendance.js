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
    const dateTime = y + "-" + m + "-" + d + " " + h + ":" + min + ":" + s;
    document.getElementById("currentDateTime").innerText = dateTime;
    
  }
  setInterval(updateDateTime, 1000);
  updateDateTime();

  // 출퇴근 버튼
  const checkInBtn = document.querySelector(".checkInBtn");
  const checkOutBtn = document.querySelector(".checkOutBtn");

  // 서버에서 오늘 출퇴근 기록 가져오기
  fetch("/attendance/todayStatus")
    .then(res => res.json())
    .then(data => {
      if (data && data.startTime) {
        const time = new Date(data.startTime).toLocaleTimeString('ko-KR', {
          hour: '2-digit',
          minute: '2-digit',
          second: '2-digit',
          hour12: false
        });
        document.getElementById("startTime").innerText = time;
        checkInBtn.style.display = "none";
        checkOutBtn.style.display = "inline-block";
      }

      if (data && data.endTime) {
        const time = new Date(data.endTime).toLocaleTimeString('ko-KR', {
          hour: '2-digit',
          minute: '2-digit',
          second: '2-digit',
          hour12: false
        });
        document.getElementById("endTime").innerText = time;
        checkOutBtn.disabled = true;
      }
    });

  // 출근 버튼 클릭 시
  checkInBtn.addEventListener("click", () => {
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
        }
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
        }
      });
  });

})
