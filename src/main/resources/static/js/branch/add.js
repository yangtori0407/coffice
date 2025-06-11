function daumPostcode() {
		new daum.Postcode({
			oncomplete: function(data){
				console.log(data)
				let roadAddr = '';
				let extraRoadAddr = '';
				
				if(data.userSelectedType==='R'){
					roadAddr = data.roadAddress;
				} else {
					roadAddr = data.jibunAddress;
				}
				
				if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
					extraRoadAddr += data.bname;
				}
				
				if(data.buildingName !== ''&& data.apartment==='Y'){
					extraRoadAddr += (extraRoadAddr !== ''?','+data.buildingName:data.buildingName);
				}
				
				if(extraRoadAddr!==''){
					extraRoadAddr='('+extraRoadAddr+")";
				}
				
				document.getElementById("branchPostcode").value=data.zonecode;
				document.getElementById("branchAddress").value=roadAddr;
				
			}
		}).open();
	}