
function confirmLogout() {
  if (confirm("정말 로그아웃하시겠습니까?")) {
    document.getElementById("logoutForm").submit();
  }
}