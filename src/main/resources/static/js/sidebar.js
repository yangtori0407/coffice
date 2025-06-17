const message = document.getElementById("message");
const sidebar = document.getElementById("accordionSidebar");

sidebar.addEventListener("click", (e) => {
    console.log("click");
    //e.target.classList.add("chat-active");
    message.classList.remove("chat-active");
})
