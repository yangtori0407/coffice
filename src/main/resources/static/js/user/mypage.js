
document.getElementById("confirmPwBtn").addEventListener("click", function() {
  const pw = document.getElementById("checkPassword").value;

  fetch("mypage/checkPassword", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ password: pw })
  })
  .then(response => response.json())
  .then(data => {
    if (data.result === true) {
      location.href = "/user/update";
    } else {
      document.getElementById("pwError").style.display = "block";
    }
  });
});
