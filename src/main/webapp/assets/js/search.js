// API 호출 상수
const NUM_OF_ROWS = 50;
const MOBILE_OS = "ETC";
const MOBILE_APP = "Travel";
const TYPE = "json";
const SERVICE_KEY =
	"7alw40yMmyzXmXLifv2zp6RVJiKNrpyFeo4Rz3WNzNzzJOGwP7m1Y28i4OlOwLr%2B6yOiUHI%2FaDkMJw%2BQDqYgNw%3D%3D";

const contextPath = document.querySelector('body').getAttribute('data-context-path');
const url = "http://localhost:8080" + contextPath;

const locationMap = {
	서울: { lat: 37.566535, lng: 126.977969 },
	인천: { lat: 37.4562557, lng: 126.7052062 },
	대전: { lat: 36.350412, lng: 127.384548 },
	대구: { lat: 35.8714354, lng: 128.601445 },
	광주: { lat: 35.1595454, lng: 126.8526012 },
	울산: { lat: 35.5383773, lng: 129.3113596 },
	세종특별자치시: { lat: 36.4801328, lng: 127.2891958 },
	경기도: { lat: 37.28101111, lng: 127.05 },
	강원도: { lat: 37.885352, lng: 127.729829 },
};

// 전역 변수로 정보 저장을 위한 배열 선언
let tourData = [];

// 관광 테마 카테고리 선택 12:관광지, 14:문화시설, 15:축제공연행사, 25:여행코스, 28:레포츠, 32:숙박, 38:쇼핑, 39:음식점
const categoryItems = [
	{ code: 12, name: "관광지" },
	{ code: 14, name: "문화시설" },
	{ code: 15, name: "축제공연행사" },
	{ code: 25, name: "여행코스" },
	{ code: 28, name: "레포츠" },
	{ code: 32, name: "숙박" },
	{ code: 38, name: "쇼핑" },
	{ code: 39, name: "음식점" },
];

// 지도 설정
let markers = [];
let checkMarkers = [];
const container = document.querySelector("#search-map");
const options = {
	center: new window.kakao.maps.LatLng(
		locationMap.서울.lat,
		locationMap.서울.lng
	),
	level: 5,
};

let map;
if (container != null) {
	map = new kakao.maps.Map(container, options);
}
let currentOverlay = null;

const setMapCenter = (lat, lng, selectedRegionName) => {
	const moveLatLon = new kakao.maps.LatLng(lat, lng);
	map.setCenter(moveLatLon);
	if (selectedRegionName == "경기도") {
		map.setLevel(11);
	} else {
		map.setLevel(8);
	}
}

//커스텀 오버레이 추적 변수
const closeOverlay = (item = null) => {
	// 아이템 정보가 제공되었고, planItems 배열에서 아이템 검사
	if (item && planItems.some((planItem) => planItem.title === item.title)) {
		// 아이템이 planItems 배열에 있다면 함수 종료
		return;
	}

	// 아이템이 planItems 배열에 없거나, 아이템 정보가 제공되지 않았다면 기존 오버레이 숨김 로직 수행
	if (currentOverlay) {
		currentOverlay.setMap(null); // 현재 오버레이 숨김
		currentOverlay = null; // 참조 제거
	}
}

const updateMapMarkers = (areaCode, sigunguCode, tour) => {
	let bounds = new kakao.maps.LatLngBounds(); // 모든 마커를 포함할 수 있는 LatLngBounds 객체 생성
	let flag = false;

	// tourData는 API 호출 결과로 얻은 데이터 배열
	tourData.forEach((item) => {
		if (tour == "all" || tour == item.contentTypeId) {
			flag = true;
			const position = new kakao.maps.LatLng(item.latitude, item.longitude); // 각 항목의 위경도를 사용하여 위치 객체 생성

			// 커스텀 이미지의 URL 생성
			const imageUrl = contextPath + "/img/" + item.contentTypeId + ".png"; // 예: item.contentid 값이 "12"이면, 이미지 URL은 "./img/12.png"
			const imageSize = new kakao.maps.Size(45, 45); // 마커 이미지의 크기 설정
			// 마커 이미지 생성
			const markerImage = new kakao.maps.MarkerImage(imageUrl, imageSize);
			const marker = new kakao.maps.Marker({
				position: position, // 마커 위치 설정
				image: markerImage, // 커스텀 마커 이미지 설정
			});

			// 커스텀 오버레이에 표시될 내용 생성
			const content = `<div class="wrap">
                                <div class="info">
                                    <div class="title">
                                        ${item.title}
                                        <div class="close" onclick="currentOverlay.setMap(null);" title="닫기"></div>
                                    </div>
                                    <div class="body">
                                        <div class="img">
                                            <img src="${item.firstImage ? item.firstImage : contextPath + '/img/no_image.jpg'}" width="80px" height="80px">
                                        </div>
                                        <div class="desc">
                                            <div class="ellipsis">주소: ${item.addr ? item.addr : "정보 없음"}</div>
                                            <div class="jibun ellipsis">전화번호: ${item.tel ? item.tel : "정보 없음"}</div>
                                            <div><a onclick="showPlaceDetail(event, '${encodeURIComponent(JSON.stringify(item))}');"  href="#">상세보기</a></div>
                                        </div>
                                    </div>
                                </div>
                            </div>`;
			// 커스텀 오버레이 생성 및 지도에 추가하지 않음 (초기에는 숨김)
			const overlay = new kakao.maps.CustomOverlay({
				content: content,
				map: null,
				position: position,
				// xAnchor: 0.3,
				// yAnchor: 0.91,
				// zIndex: 3,
			});

			// 마커 클릭 시 커스텀 오버레이 표시
			kakao.maps.event.addListener(marker, "click", () => {
				closeOverlay();
				overlay.setMap(map); // 현재 오버레이 표시
				currentOverlay = overlay; // 참조 업데이트
				map.setCenter(position);
			});

			marker.setMap(map); // 마커를 지도에 표시
			markers.push(marker); // 생성된 마커를 markers 배열에 추가
			bounds.extend(position); // LatLngBounds 객체에 현재 마커의 위치를 추가
		}
	});
	// 모든 마커가 포함되도록 지도의 중심과 줌 레벨 조정
	if (flag) {
		map.setBounds(bounds);
	} else {
		alert("해당 관광 정보 지역이 없습니다.");
		fetchAllTourData(areaCode, sigunguCode);
	}
}

const mergeSort = (arr) => {
    if (arr.length <= 1) {
        return arr;
    }

    const mid = Math.floor(arr.length / 2);
    const left = arr.slice(0, mid);
    const right = arr.slice(mid);

    return merge(mergeSort(left), mergeSort(right));
}

const merge = (left, right) => {
    let result = [];
    let leftIndex = 0;
    let rightIndex = 0;

    while (leftIndex < left.length && rightIndex < right.length) {
        if (left[leftIndex].distance < right[rightIndex].distance) {
            result.push(left[leftIndex]);
            leftIndex++;
        } else {
            result.push(right[rightIndex]);
            rightIndex++;
        }
    }

    return result.concat(left.slice(leftIndex)).concat(right.slice(rightIndex));
}

const updateMarker = (contentId) => {
	let bounds = new kakao.maps.LatLngBounds();
	tourData.forEach((item) => {
		if (contentId == item.contentId) {
			closeOverlay();
			let placeDetailDiv = document.querySelector("#placeDetail");

			let placeSaveList = [];
			tourData.forEach((i, index) => {
				if (i.title != item.title) {
					let distance = Math.sqrt(Math.pow(i.latitude - item.latitude, 2) + Math.pow(i.longitude - item.longitude, 2));

					let placeSave = {};
					placeSave.distance = distance;
					placeSave.index = index;

					placeSaveList.push(placeSave);
				}
			});

			let sortList = mergeSort(placeSaveList);

			placeDetailDiv.innerHTML = `<h4>${item.title}</h4>
		         <div style="font-weight:500; font-size:17px">${item.overview ? item.overview : ""}</div>
		         <div style="height: 10px"></div>
		         <img src="${item.firstImage ? item.firstImage : contextPath + '/img/no_image.jpg'}" width="100%">
		         <div style="height: 10px"></div>
		         <div style="font-weight:bold">주소: ${item.addr ? item.addr : "정보 없음"}</div>
		         <div style="font-weight:bold">전화번호: ${item.tel ? item.tel : "정보 없음"}</div>
		         <hr>
		         <div style="font-weight:bold; color:gray; font-size:24px">주변 관광지 추천</div>
		         <div class="text-primary" style="font-size:20px; cursor: pointer" onclick="updateMarker(${tourData[sortList[0].index].contentId})">${sortList.length == 0 ? "" : tourData[sortList[0].index].title}</div>
		         <div class="text-primary" style="font-size:20px; cursor: pointer" onclick="updateMarker(${tourData[sortList[1].index].contentId})">${sortList.length <= 1 ? "" : tourData[sortList[1].index].title}</div>
		         `;
			placeDetailDiv.style.display = "block";


			const position = new kakao.maps.LatLng(item.latitude, item.longitude); // 각 항목의 위경도를 사용하여 위치 객체 생성

			// 커스텀 이미지의 URL 생성
			const imageUrl = contextPath + "/img/" + item.contentTypeId + ".png"; // 예: item.contentid 값이 "12"이면, 이미지 URL은 "./img/12.png"
			const imageSize = new kakao.maps.Size(45, 45); // 마커 이미지의 크기 설정
			// 마커 이미지 생성
			const markerImage = new kakao.maps.MarkerImage(imageUrl, imageSize);
			const marker = new kakao.maps.Marker({
				position: position, // 마커 위치 설정
				image: markerImage, // 커스텀 마커 이미지 설정
			});

			// 커스텀 오버레이에 표시될 내용 생성
			const content = `<div class="wrap">
                                <div class="info">
                                    <div class="title">
                                        ${item.title}
                                        <div class="close" onclick="currentOverlay.setMap(null);" title="닫기"></div>
                                    </div>
                                    <div class="body">
                                        <div class="img">
                                            <img src="${item.firstImage ? item.firstImage : contextPath + '/img/no_image.jpg'}" width="80px" height="80px">
                                        </div>
                                        <div class="desc">
                                            <div class="ellipsis">주소: ${item.addr ? item.addr : "정보 없음"}</div>
                                            <div class="jibun ellipsis">전화번호: ${item.tel ? item.tel : "정보 없음"}</div>
                                            <div><a onclick="showPlaceDetail(event, '${encodeURIComponent(JSON.stringify(item))}');"  href="#">상세보기</a></div>
                                        </div>
                                    </div>
                                </div>
                            </div>`;
			// 커스텀 오버레이 생성 및 지도에 추가하지 않음 (초기에는 숨김)
			const overlay = new kakao.maps.CustomOverlay({
				content: content,
				map: null,
				position: position,
				// xAnchor: 0.3,
				// yAnchor: 0.91,
				// zIndex: 3,
			});


			overlay.setMap(map); // 현재 오버레이 표시
			currentOverlay = overlay; // 참조 업데이트
			map.setCenter(position);


			marker.setMap(map); // 마커를 지도에 표시
			markers.push(marker); // 생성된 마커를 markers 배열에 추가
			bounds.extend(position); // LatLngBounds 객체에 현재 마커의 위치를 추가

		}
	});
}

const showPlaceDetail = (e, p) => {
	const place = JSON.parse(decodeURIComponent(p));
	let placeDetailDiv = document.querySelector("#placeDetail");


	let placeSaveList = [];
	tourData.forEach((item, index) => {
		if (item.title != place.title) {
			let distance = Math.sqrt(Math.pow(item.latitude - place.latitude, 2) + Math.pow(item.longitude - place.longitude, 2));

			let placeSave = {};
			placeSave.distance = distance;
			placeSave.index = index;

			placeSaveList.push(placeSave);
		}
	});

	let sortList = mergeSort(placeSaveList);

	const content = `<h4>${place.title}</h4>
         <div style="font-weight:500; font-size:17px">${place.overview ? place.overview : ""}</div>
         <div style="height: 10px"></div>
         <img src="${place.firstImage ? place.firstImage : contextPath + '/img/no_image.jpg'}" width="100%">
         <div style="height: 10px"></div>
         <div style="font-weight:bold">주소: ${place.addr ? place.addr : "정보 없음"}</div>
         <div style="font-weight:bold">전화번호: ${place.tel ? place.tel : "정보 없음"}</div>
         <hr>
         <div style="font-weight:bold; color:gray; font-size:24px">주변 관광지 추천</div>
         <div class="text-primary" style="font-size:20px; cursor: pointer" onclick="updateMarker(${tourData[sortList[0].index].contentId})">${sortList.length == 0 ? "" : tourData[sortList[0].index].title}</div>
         <div class="text-primary" style="font-size:20px; cursor: pointer" onclick="updateMarker(${tourData[sortList[1].index].contentId})">${sortList.length <= 1 ? "" : tourData[sortList[1].index].title}</div>
         `;
	placeDetailDiv.innerHTML = content;
	placeDetailDiv.style.display = "block";
}

const fetchAllTourData = async (areaCode, sigunguCode, tourType = "all") => {
	// 기존 데이터 초기화
	tourData = [];
	let tourListParam = `/trip?action=mapping&sido=${areaCode}&gugun=${sigunguCode}`;
	if (tourType != "all" && tourType != null) {
		tourListParam += `&type=${tourType}`;
	}
	// await를 사용하여 fetch 요청의 완료를 기다림
	const response = await fetch(`${url}${tourListParam}`);
	const data = await response.json(); // 응답을 JSON으로 변환
	await data.forEach((item) => {
		tourData.push(item);
	});
	// 데이터를 모두 가져온 후의 추가 처리
	updateMapMarkers(areaCode, sigunguCode, tourType);
}

const tourChangeListener = async (region, subRegion, tourSelect) => {
	let selectedContentId = tourSelect.value;
	if (selectedContentId == "") {
		selectedContentId = "all";
	}
	// 모든 마커를 지도에서 제거
	markers.forEach((marker) => {
		marker.setMap(null);
	});
	markers = []; // 마커 배열 초기화

	const areaCode = region.value;
	const sigunguCode = subRegion.value;

	await fetchAllTourData(areaCode, sigunguCode, selectedContentId);
}

const tourLoadingListener = async (region, subRegion) => {
	const tourTypeSelect = document.getElementById("tourType");
	// 이전에 선택된 투어 타입 옵션을 초기화
	tourTypeSelect.innerHTML = '<option value="">관광 타입 선택</option>';
	categoryItems.forEach((item) => {
		const option = document.createElement("option");
		option.value = item.code;
		option.textContent = item.name;
		tourTypeSelect.appendChild(option);
	});

	const areaCode = region.value;
	const sigunguCode = subRegion.value;

	// 두 항목이 선택되었는지 확인
	if (!areaCode || !sigunguCode) {
		alert("시/도 및 구/군 정보를 선택해주세요.");
		return;
	}
	await fetchAllTourData(areaCode, sigunguCode);
	tourTypeSelect.addEventListener("change", () => { tourChangeListener(region, subRegion, tourTypeSelect) });
}

const gugunLoadingListener = async (region) => {
	// 두번째 드롭다운
	const subregionSelect = document.getElementById("subregion");
	const selectedRegionCode = region.value;
	const subRegionParam = `/trip?action=gugun&sido=${selectedRegionCode}`;

	// 이전에 선택된 서브리전 옵션을 초기화
	subregionSelect.innerHTML = '<option value="">시/구 선택</option>';
	markers.forEach((marker) => {
		marker.setMap(null); // 마커를 지도에서 제거
	});
	markers = []; // 마커 배열 초기화

	const response = await fetch(`${url}${subRegionParam}`);
	const data = await response.json();
	data.forEach((item) => {
		const option = document.createElement("option");
		option.value = item.gugunCode;
		option.textContent = item.gugunName;
		subregionSelect.appendChild(option);
	});
	// 선택된 지역의 이름을 가져옴
	const selectedRegionName = region.options[region.selectedIndex].text;
	const coords = locationMap[selectedRegionName]; // 위경도 매핑에서 좌표 검색

	if (coords) {
		setMapCenter(coords.lat, coords.lng, selectedRegionName); // 지도의 중심을 변경
	}
	subregionSelect.addEventListener("change", () => { tourLoadingListener(region, subregionSelect) });
}

const sidoLoadingListener = async () => {
	const regionSelect = document.getElementById("region");
	if (regionSelect == null) return;
	const regionParam = "/trip?action=sido";
	// 데이터 요청 및 처리
	const response = await fetch(`${url}${regionParam}`);
	const data = await response.json();

	// 기본 옵션 추가
	regionSelect.innerHTML = '<option value="">도 선택</option>';
	// 받은 데이터를 통해 옵션 추가
	await data.forEach((item) => {
		const option = document.createElement("option");
		option.value = item.sidoCode;
		option.textContent = item.sidoName;
		regionSelect.appendChild(option);
	});
	regionSelect.addEventListener("change", () => { gugunLoadingListener(regionSelect) });
}

document.addEventListener("DOMContentLoaded", sidoLoadingListener);



// ================ 여행 검색 =======================
// ================ 여행 검색 =======================
// ================ 여행 검색 =======================
// ================ 여행 검색 =======================
// ================ 여행 검색 =======================
// ================ 여행 검색 =======================
// ================ 여행 검색 =======================
// ================ 여행 검색 =======================
// ================ 여행 검색 =======================
// ================ 여행 검색 =======================
let polylines = []; // 선분을 저장할 배열
let tempListItems = []; // makePlan 임시 버퍼
let planItems = []; // plan에 저장될 임시 리스트
let planIdItems = [];

// 목록에서 항목 제거하는 함수
const removeFromPlanList = (listItem, title, index) => {
	// 항목 제거
	listItem.remove();

	// planItems 배열에서 해당 아이템 정보 제거
	planItems = planItems.filter((planItem) => planItem.title !== title);
	checkMarkers = checkMarkers.filter((_, idx) => idx !== index);

	// 모든 선분 제거
	for (var i = 0; i < polylines.length; i++) {
		polylines[i].setMap(null);
	}
	polylines = []; // 선분 배열 초기화

	// 선분 다시 그리기
	for (var i = 1; i < planItems.length; i++) {
		var startItem = planItems[i - 1];
		var endItem = planItems[i];
		var polyline = new kakao.maps.Polyline({
			path: [
				new kakao.maps.LatLng(startItem.latitude, startItem.longitude),
				new kakao.maps.LatLng(endItem.latitude, endItem.longitude),
			],
			// 선분 스타일 설정
		});
		polyline.setMap(map);
		polylines.push(polyline);
	}
}

const removeAllPlanList = (plans) => {
	plans.innerHTML = "";
	polylines.forEach((polyline) => {
		polyline.setMap(null); // 각 선분을 Map에서 제거
	});
	polylines = []; // 배열 초기화
	planItems = [];
	planIdItems = [];



	checkMarkers = [];
}

// 여행 계획 목록에 추가
const addToPlanList = async (index) => {
	console.log(index);
	const item = tempListItems[index];
	// 중복 검사
	if (planItems.some((planItem) => planItem.title === item.title)) {
		alert("이미 추가된 항목입니다.");
		return; // 이미 목록에 존재하는 항목이면 함수 실행을 종료
	}

	checkMarkers.push(markers[index]);

	// 목록 항목 생성
	const planList = document.querySelector("#planItems");
	const listItem = document.createElement("li");
	listItem.innerHTML = `<div class="list-group-item">
        <h5 class="mb-1">${item.title}</h5>
        <p class="mb-1">${item.addr || "주소 정보 없음"}</p>
        <p class="mb-1">${item.tel || "전화번호 정보 없음"}</p>
        <img src="${item.firstImage || contextPath + "/img/no_image.jpg"
		}" width="80" height="80" class="rounded float-right">
        <button onclick="removeFromPlanList(this.parentElement, '${item.title
		}', ${checkMarkers.length - 1})" class="btn btn-danger btn-sm">X</button>
		<hr/>
    </div>
   `;
	listItem.dataset.title = item.title; // 중복 검사를 위한 데이터 속성 설정
	planList.appendChild(listItem);

	// 추가된 아이템 정보를 planItems 배열에 저장
	planItems.push(item);
	planIdItems.push(item.contentId);

	// 새로운 아이템과 마지막 아이템을 연결하는 선분 추가
	if (planItems.length > 1) {
		var lastItem = planItems[planItems.length - 2]; // 마지막에서 두 번째 아이템
		var polyline = new kakao.maps.Polyline({
			path: [
				new kakao.maps.LatLng(lastItem.latitude, lastItem.longitude),
				new kakao.maps.LatLng(item.latitude, item.longitude),
			],
			strokeWeight: 3,
			strokeColor: "#db4040",
			strokeOpacity: 0.8,
			strokeStyle: "solid",
		});
		polyline.setMap(map);
		polylines.push(polyline);
	}
}

// 검색 기능을 수행하는 함수
const performSearch = async () => {
	// 검색 입력 필드에서 검색어 가져오기
	const searchInput = document.getElementById("searchInput").value;

	// 검색어 출력
	if (searchInput.trim() === "") {
		alert("검색어를 입력해주세요.");
		return;
	}
	const encodedKeyword = encodeURIComponent(searchInput.trim());
	console.log(encodedKeyword)
	const planParam = `/trip?action=mapping&keyword=${encodedKeyword}`;
	console.log(`${url}${planParam}`);
	// API 호출
	const response = await fetch(`${url}${planParam}`);
	const data = await response.json();

	if (data == null || data.length == 0) {
		alert("입력한 검색어에 해당되는 데이터가 없습니다.");
		return;
	}


	const bounds = new kakao.maps.LatLngBounds();



	// 1. 기존에 저장된 여행 리스트를 제외하고 검색 결과에 따른 다른 마커 정보 띄워주기
	// 모든 마커를 지도에서 제거
	markers.forEach((marker) => {
		marker.setMap(null);
	});
	markers = []; // 마커 배열 초기화
	// 1-1. 단, 기존에 마커 정보는 남겨두자.
	for (marker of checkMarkers) {
		markers.push(marker);
		marker.setMap(map);
	}
	// 2. tempListItems(addToPlanList 함수에 있음)을 초기화 해줘야 함.
	tempListItems = [];



	await data.forEach((item, index) => {
		// 각 항목의 위치에 마커 생성
		const position = new kakao.maps.LatLng(item.latitude, item.longitude);
		const marker = new kakao.maps.Marker({
			map,
			position,
		});

		// 마커를 markers 배열에 추가
		markers.push(marker);

		// LatLngBounds 객체에 현재 마커의 위치를 추가
		bounds.extend(position);

		// 커스텀 오버레이에 표시될 내용 생성
		const overlayContent = `<div class="wrap">
                                    <div class="info">
                                        <div class="title">
                                            ${item.title}
                                            <div class="close" onclick="currentOverlay.setMap(null);" title="닫기"></div>
                                        </div>
                                        <div class="body">
                                            <div class="img">
                                                <img src="${item.firstImage
				? item.firstImage
				: contextPath + "/img/no_image.jpg"
			}" width="80px" height="80px" onerror="this.src='${contextPath}/img/no_image.jpg'; this.setAttribute('alt', '');">
                                            </div>
                                            <div class="desc">
                                                <div class="ellipsis">주소: ${item.addr
				? item.addr
				: "정보 없음"
			}</div>
                                                <div class="jibun ellipsis">전화번호: ${item.tel
				? item.tel
				: "정보 없음"
			}</div>
                                                <button type="button" onclick="addToPlanList(${index})">추가하기</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>`;
		const overlay = new kakao.maps.CustomOverlay({
			content: overlayContent,
			map: map,
			position: marker.getPosition(),
		});
		overlay.setMap(null); // 초기에는 숨김
		tempListItems.push(item);

		// 마커 클릭 이벤트: 정보 창 표시
		kakao.maps.event.addListener(marker, "click", function() {
			closeOverlay();
			overlay.setMap(map);
			currentOverlay = overlay;
		});
	});
	// 모든 마커가 포함되도록 지도 범위를 조정
	map.setBounds(bounds);
};


// 검색 버튼
// 검색 버튼에 클릭 이벤트 리스너 추가
const searchBtn = document.querySelector("#searchButton");
// 검색 입력 필드에 엔터 키 이벤트 리스너 추가
const searchInput = document.querySelector("#searchInput");

if (searchBtn != null) {
	searchBtn.addEventListener("click", () => {
		performSearch();
	});
}
if (searchInput != null) {
	searchInput.addEventListener("keypress", (event) => {
		// 엔터 키가 눌렸는지 확인
		if (event.keyCode === 13 || event.which === 13) {
			// event.which는 오래된 브라우저 호환성을 위해 추가
			performSearch();
			// 폼의 기본 제출을 방지
			event.preventDefault();
		}
	});
}

const planBtnClickEventListener = () => {
	// 고유한 planId 생성 (예시: 현재 날짜와 시간을 이용)
	//const planId = "plan_" + new Date().toISOString();

	// planItems 배열을 로컬 스토리지에 저장
	//localStorage.setItem(planId, JSON.stringify(planItems));

	const planText = document.getElementById("planItems").innerText;
	if (planText == "" || planText == null) {
		alert("여행을 추가하고 등록해주세요.");
		return;
	}

	alert("여행 계획이 등록되었습니다.");
	const planListForm = document.querySelector("#planListForm");
	const planIdList = document.querySelector("#planIdList");
	planIdList.setAttribute("value", JSON.stringify(planIdItems));
	// 등록 후 planItems 배열 초기화 및 목록 UI 업데이트
	planItems = [];
	removeAllPlanList(document.getElementById("planItems"));
	planListForm.submit();
}

// plan
// 일정 등록하기 버튼
const planBtn = document.querySelector("#savePlanButton");
const cancelBtn = document.querySelector("#canclePlanButton");
if (planBtn != null) {
	planBtn.addEventListener("click", planBtnClickEventListener);
}
if (cancelBtn != null) {
	cancelBtn.addEventListener("click", () => { removeAllPlanList(document.getElementById("planItems")) });
}



/*
===== 여행 디테일 정보 생성 부분 =====
===== 여행 디테일 정보 생성 부분 =====
===== 여행 디테일 정보 생성 부분 =====
===== 여행 디테일 정보 생성 부분 =====
===== 여행 디테일 정보 생성 부분 =====
===== 여행 디테일 정보 생성 부분 =====
*/
const planMap = document.querySelector("#plan-map");
const getContents = async () => {
	const planId = document.querySelector("#plan-div-id").innerText;
	const planIdParam = `/trip?action=mappingPlan&planid=${planId}`;
	const response = await fetch(`${url}${planIdParam}`);
	const data = await response.json();
	return data;
}
let planOverlay = [];

const planMapLoading = async () => {
	// 모든 마커가 포함되도록 지도의 중심과 확대 레벨 조정
	const bounds = new kakao.maps.LatLngBounds();
	const contents = await getContents();
	console.log(contents);
	for (const content of contents) {
		console.log(content)
		/*
		const contentParam = `/trip?action=getTrip&contentid=${content.contentId}`;
		const response = await fetch(`${url}${contentParam}`);
		const item = await response.json();
		*/

		// 위치 정보를 이용하여 마커 생성
		const markerPosition = new kakao.maps.LatLng(content.latitude, content.longitude);
		const marker = new kakao.maps.Marker({
			position: markerPosition,
			map: map
		});

		// 커스텀 오버레이에 표시될 내용 생성
		const overlayContent = `<div class="wrap">
                                    <div class="info">
                                        <div class="title">
                                            ${content.title}
                                            <div class="close" onclick="currentOverlay.setMap(null);" title="닫기"></div>
                                        </div>
                                        <div class="body">
                                            <div class="img">
                                                <img src="${content.firstImage
				? content.firstImage
				: contextPath + "/img/no_image_logo.png"
			}" width="80px" height="80px">
                                            </div>
                                            <div class="desc">
                                                <div class="ellipsis">주소: ${content.addr
				? content.addr
				: "정보 없음"
			}</div>
                                                <div class="jibun ellipsis">전화번호: ${content.tel
				? content.tel
				: "정보 없음"
			}</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>`;
		const overlay = new kakao.maps.CustomOverlay({
			content: overlayContent,
			map: map,
			position: marker.getPosition(),
		});
		overlay.setMap(null); // 초기에는 숨김


		// 마커 클릭 시 커스텀 오버레이 표시
		kakao.maps.event.addListener(marker, "click", () => {
			closeOverlay();
			overlay.setMap(map); // 현재 오버레이 표시
			currentOverlay = overlay; // 참조 업데이트
			map.setCenter(position);
		});

		planOverlay.push(overlay);
		// 마커를 지도에 추가
		marker.setMap(map);
		// LatLngBounds 객체에 현재 마커의 위치를 추가
		bounds.extend(new kakao.maps.LatLng(content.latitude, content.longitude));
	};
	// 선분 추가
	if (contents.length > 1) {
		let lastItem = contents[0]; // 마지막에서 두 번째 아이템
		for (let i = 1; i < contents.length; i++) {
			let polyline = new kakao.maps.Polyline({
				path: [
					new kakao.maps.LatLng(lastItem.latitude, lastItem.longitude),
					new kakao.maps.LatLng(contents[i].latitude, contents[i].longitude),
				],
				strokeWeight: 3,
				strokeColor: "#db4040",
				strokeOpacity: 0.8,
				strokeStyle: "solid",
			});
			polyline.setMap(map);
			lastItem = contents[i];
		}
	}
	// 선분 추가

	map.setBounds(bounds);
}

const openOverlay = (index) => {
	if (currentOverlay == planOverlay[index]) {
		closeOverlay();
	} else {
		closeOverlay();
		planOverlay[index].setMap(map);
		currentOverlay = planOverlay[index];
	}
}

if (planMap != null) {
	map = new kakao.maps.Map(planMap, options);
	planMapLoading();
}


