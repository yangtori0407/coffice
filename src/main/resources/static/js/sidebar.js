const chatSideBar = document.getElementById("chatSideBar");
const sidebar = document.getElementById("accordionSidebar");

sidebar.addEventListener("click", (e) => {
    console.log("click");
    //e.target.classList.add("chat-active");
    chatSideBar.classList.remove("chat-active");
})
