


const completeReject = document.getElementById("id_completeReject");

completeReject.addEventListener("click", function() {
  const rejectContent = document.getElementById("id_rejectContent");
  let textReason = rejectContent.innerText.trim();

  if(textReason == ""){
    alert("반려 사유를 입력해주세요")

  } else {
    reject_reason.value = textReason;

    const rejectForm = document.getElementById("rejectForm");
    rejectForm.submit();

  }

})