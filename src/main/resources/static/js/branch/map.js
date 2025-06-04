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
    let mapSubmit = document.getElementById("mapSubmit");
    let reloadMap = document.getElementById("reloadMap");
        
    listSearch(addressList, null);
        
    mapSubmit.addEventListener("click",(e)=>{
        e.preventDefault();
           searchPlaces();
    })

    reloadMap.addEventListener("click",()=>{
        map.setCenter(options.center);
        map.setLevel(options.level);
    })
       
    function searchPlaces() {
        let keyword = document.getElementById("keyword").value;
            
            if(!keyword.replace(/^\s+|\s+$/g, '')){
                alert("입력해주세요.")
                return false;
            }
        
        let filteredList = addressList.filter(item =>
            item.name.includes(keyword) || item.address.includes(keyword)   
        );

            if (filteredList.length === 0) {
                alert("검색 결과가 존재하지 않습니다.");
                return;
            }  
            let bounds = new kakao.maps.LatLngBounds(); 
            listSearch(filteredList,bounds);
    }

    function listSearch(list, bounds) {
        list.forEach((i)=>{
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