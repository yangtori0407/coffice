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
    let reloadMap = document.getElementById("reloadMap");
    let detailLink = document.querySelectorAll(".detail-link")

    listSearch(addressList, null);

    reloadMap.addEventListener("click",()=>{
        map.setCenter(options.center);
        map.setLevel(options.level);
    })

    function listSearch(list, bounds) {
        list.forEach((i)=>{
            geocoder.addressSearch(i.address,(result,status)=>{
                if(i.status==="운영중"){
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
                                
                                    fetch(`./detail?branchId=${i.id}`)
                                    .then(r=>r.json())
                                    .then(r=>{
                                        
                                        let detailBranchName = document.getElementById("detailBranchName");
                                        let detailUserName = document.getElementById("detailUserName");
                                        let detailStatus = document.getElementById("detailStatus");
                                        
                                        detailBranchName.innerText = r.branchName
                                        detailStatus.innerText = (r.branchStatus? "운영중": "운영안함")

                                        if(r.userVO == null) {
                                            detailUserName.innerText = "없음"
                                        }else {
                                            detailUserName.innerText = r.userVO.name
                                        }
                                        let myModal = new bootstrap.Modal(document.getElementById("detailBranch"))
                                        myModal.show();
                                        
                                    })

                            })
                        bounds.extend(coords);
                        map.setBounds(bounds);
                    }
                }
            })
	    })
    }
// 점주지정등록
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

    //지점지도확대
    for(let i =0; i<detailLink.length;i++){
        detailLink[i].addEventListener("click",(e)=>{       
            const branchId = detailLink[i].getAttribute("data-branch");

            fetch(`./detail?branchId=${branchId}`)
            .then(r=>r.json())
            .then(r=>{ 
                let addressList2= [
                    {
                        id: r.branchId,
				        name:r.branchName,
				        address:r.branchAddress,
				        status:(r.branchStatus? "운영중": "운영안함")
                    }
                ]

                let bounds = new kakao.maps.LatLngBounds()
                if(addressList2[0].status==="운영안함"){
                    alert("지도에 없음")
                }else{
                    listSearch(addressList2,bounds)
                }
            })
        })
    }
    