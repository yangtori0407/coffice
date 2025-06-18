

const formLinks = document.getElementsByClassName("formLinks");

for (let link of formLinks) {
    link.addEventListener("click", function() {
        let formId = link.dataset.formId;
        let formName = link.dataset.formName;

        let path = "./write?formId="+formId+"&name="+formName;

        location.href = path;

    })
}