const sidebar = document.getElementById("accordionSidebar");

sidebar.addEventListener("click", (e) => {
  const navLink = e.target.closest('.nav-link');
  if (navLink) {
    // 모든 nav 링크 비활성화
    document.querySelectorAll('.nav-link').forEach(link => {
      link.classList.remove('chat-active', 'ingredients-active');
    });

    // 클릭된 메뉴에 따라 클래스 추가
    if (navLink.id === 'message') {
      navLink.classList.add('chat-active');
    } else if (navLink.href && navLink.href.includes('/ingredients')) {
      navLink.classList.add('ingredients-active');
    }
  }
});

