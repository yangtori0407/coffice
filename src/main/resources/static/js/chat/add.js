const people = document.getElementById("people");

people.addEventListener("click", (e) =>{
    if(e.target.classList.contains("delPerson")){
        const p = e.target.parentElement;
        p.remove();
    }
})