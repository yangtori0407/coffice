

const formLinks = document.getElementsByClassName("formLinks");

for (let link of formLinks) {
    link.addEventListener("click", function() {
        let formId = link.dataset.formId;

        let path = "./write?formId="+formId;

        location.href = path;

    })
}