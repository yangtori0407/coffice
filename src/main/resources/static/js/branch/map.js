	let container = document.getElementById('map');
	   let options = { 
	       center: new kakao.maps.LatLng(37.5642135, 127.0016985), 
	       level: 9
	   };

	   let map = new kakao.maps.Map(container, options);
	   
	   let imageSrc = '/img/커피지도마커3.png',
	   		imageSize = new kakao.maps.Size(35,40),
			imageOption = {offset: new kakao.maps.Point(27,69)};
			
	  let markerImage = new kakao.maps.MarkerImage(imageSrc,imageSize,imageOption)
	   
	   let geocoder = new kakao.maps.services.Geocoder();	   
	   
	   addressList.forEach((i)=>{
			console.log(i.name)
			console.log(i.address)
            console.log(i.status)
            geocoder.addressSearch(i.address,(result,status)=>{
                    if(i.status==1){
                    if(status===kakao.maps.services.Status.OK){
                        let coords = new kakao.maps.LatLng(result[0].y,result[0].x);
                        
                        let marker = new kakao.maps.Marker({
                            map: map,
                            position: coords,
                            image: markerImage
                        })
                        
                        let infowindow = new kakao.maps.InfoWindow({
                            content:'<div style="width:150px;text-align:center;padding:6px 0;">'+i.name+'</div>',
                            removable: true
                        })
                        
                        kakao.maps.event.addListener(marker,'click',()=>{
                            infowindow.open(map,marker);
                        })
                    }
                }
                })
			
	   });

       const addBranchBtn = document.getElementById("addBranchBtn");
       addBranchBtn.addEventListener("click",()=>{
        const userId = document.getElementById("selectUser").value;
        const branchId = document.getElementById("selectBranch").value;

        console.log(userId)
        console.log(branchId)

        const params = new URLSearchParams();
        params.append("userId", userId);
        params.append("branchId", branchId);

        fetch('/branch/updateUser', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: params
        })
        .then(r=>{
            console.log(r)
            if(r.ok){
                alert("추가되었습니다.")
                window.location.reload();
            } else {
                alert("다시추가부탁드립니다.")
            }
        })
        .catch(e=> {
            console.log(e)
            alert("오류");
        });
    });