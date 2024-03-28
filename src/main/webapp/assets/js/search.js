// API 호출 상수
const NUM_OF_ROWS = 50;
const MOBILE_OS = "ETC";
const MOBILE_APP = "Travel";
const TYPE = "json";
const SERVICE_KEY =
	"7alw40yMmyzXmXLifv2zp6RVJiKNrpyFeo4Rz3WNzNzzJOGwP7m1Y28i4OlOwLr%2B6yOiUHI%2FaDkMJw%2BQDqYgNw%3D%3D";
const contextPath = document.querySelector('body').getAttribute('data-context-path');
const url = "http://localhost:8080/";

const seoul = {lat: 37.566535, lng: 126.977969};
var locationMap = {
	서울: { lat: 37.566535, lng: 126.977969 },
	인천: { lat: 37.4562557, lng: 126.7052062 },
	대전: { lat: 36.350412, lng: 127.384548 },
	대구: { lat: 35.8714354, lng: 128.601445 },
	광주: { lat: 35.1595454, lng: 126.8526012 },
	부산: { lat: 35.1795543, lng: 129.0756416 },
	울산: { lat: 35.5383773, lng: 129.3113596 },
	세종특별자치시: { lat: 36.4801328, lng: 127.2891958 },
	경기도: { lat: 37.28101111, lng: 127.05 },
	강원특별자치도: { lat: 37.885352, lng: 127.729829 },
};

// 전역 변수로 정보 저장을 위한 배열 선언
var tourData = [];

// 관광 테마 카테고리 선택 12:관광지, 14:문화시설, 15:축제공연행사, 25:여행코스, 28:레포츠, 32:숙박, 38:쇼핑, 39:음식점
var categoryItems = [
	{ code: 12, name: "관광지" },
	{ code: 14, name: "문화시설" },
	{ code: 15, name: "축제공연행사" },
	{ code: 25, name: "여행코스" },
	{ code: 28, name: "레포츠" },
	{ code: 32, name: "숙박" },
	{ code: 38, name: "쇼핑" },
	{ code: 39, name: "음식점" },
];


// ================ 여행 검색 =======================
var polylines = []; // 선분을 저장할 배열
var tempListItems = []; // makePlan 임시 버퍼
var planItems = []; // plan에 저장될 임시 리스트
// 지도 설정
var markers = [];
var container = document.getElementById("search-map");
var options = {
	center: new window.kakao.maps.LatLng(
		seoul.lat,
		seoul.lng
	),
	level: 5,
};
var map = new kakao.maps.Map(container, options);

function setMapCenter(lat, lng) {
	var moveLatLon = new kakao.maps.LatLng(lat, lng);
	map.setCenter(moveLatLon);
}

//커스텀 오버레이 추적 변수
var currentOverlay = null;
function closeOverlay(item = null) {
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

document.addEventListener("DOMContentLoaded", async function() {
	const REQUEST_URL = url + contextPath + "/trip?action=sido";
	console.log(REQUEST_URL);

	// 데이터 요청 및 처리
	await fetch(REQUEST_URL)
		.then((response) => response.json())
		.then((data) => {
			const regionSelect = document.getElementById("region");
			// 기본 옵션 추가
			regionSelect.innerHTML = '<option value="">도 선택</option>';
			// 받은 데이터를 통해 옵션 추가
			data.forEach((item) => {
				const option = document.createElement("option");
				option.value = item.sidoCode;
				option.textContent = item.sidoName;
				regionSelect.appendChild(option);
			});
		})

	// 상수 정의
	// 두번째 드롭다운
	const regionSelect = document.getElementById("region");
	const subregionSelect = document.getElementById("subregion");

	regionSelect.addEventListener("change", async function() {
		const selectedRegionCode = this.value;
		const REQUEST_URL = url + contextPath + `/trip?action=gugun&sido=${selectedRegionCode}`;

		// 이전에 선택된 서브리전 옵션을 초기화
		subregionSelect.innerHTML = '<option value="">시/구 선택</option>';
		markers.forEach(function(marker) {
			marker.setMap(null); // 마커를 지도에서 제거
		});
		markers = []; // 마커 배열 초기화

		await fetch(REQUEST_URL)
			.then((response) => response.json())
			.then((data) => {
				data.forEach((item) => {
					const option = document.createElement("option");
					option.value = item.gugunCode;
					option.textContent = item.gugunName;
					subregionSelect.appendChild(option);
				});
			})
		// 선택된 지역의 이름을 가져옴
		var selectedRegionName = this.options[this.selectedIndex].text;
		var coords = locationMap[selectedRegionName]; // 위경도 매핑에서 좌표 검색

		if (coords) {
			setMapCenter(coords.lat, coords.lng, selectedRegionName); // 지도의 중심을 변경
		}
	});

	// 지도의 중심을 변경하는 함수
	function setMapCenter(lat, lng, selectedRegionName) {
		var moveLatLon = new kakao.maps.LatLng(lat, lng);
		map.setCenter(moveLatLon);
		if (selectedRegionName == "경기도") {
			map.setLevel(11);
		} else {
			map.setLevel(8);
		}
	}

	// 두째 드롭다운
	// 상수 정의
	const tourTypeSelect = document.getElementById("tourType");

	subregionSelect.addEventListener("change", function() {
		// 이전에 선택된 투어 타입 옵션을 초기화
		tourTypeSelect.innerHTML = '<option value="">관광 타입 선택</option>';
		categoryItems.forEach((item) => {
			const option = document.createElement("option");
			option.value = item.code;
			option.textContent = item.name;
			tourTypeSelect.appendChild(option);
		});

		const areaCode = regionSelect.value;
		const sigunguCode = subregionSelect.value;

		console.log("시군구 코드", sigunguCode);

		// 두 항목이 선택되었는지 확인
		if (!areaCode || !sigunguCode) {
			alert("시/도 및 구/군 정보를 선택해주세요.");
			return;
		}

		fetchAllTourData(areaCode, sigunguCode);
	});

	async function fetchAllTourData(areaCode, sigunguCode) {

		// 기존 데이터 초기화
		tourData = [];
		const REQUEST_URL = url + contextPath + `/trip?action=mapping&sido=${areaCode}&gugun=${sigunguCode}`;
		// await를 사용하여 fetch 요청의 완료를 기다림
		const response = await fetch(REQUEST_URL);
		const data = await response.json(); // 응답을 JSON으로 변환
		console.log(REQUEST_URL);
		console.log(data);
		await data.forEach((item) => {
			tourData.push(item);
		});

		// 데이터를 모두 가져온 후의 추가 처리
		updateMapMarkers("all");

	}

	function updateMapMarkers(tour) {
		var bounds = new kakao.maps.LatLngBounds(); // 모든 마커를 포함할 수 있는 LatLngBounds 객체 생성
		// tourData는 API 호출 결과로 얻은 데이터 배열
		tourData.forEach(function(item) {
			if(tour == "all" || tour == item.contentTypeId){
				var position = new kakao.maps.LatLng(item.latitude, item.longitude); // 각 항목의 위경도를 사용하여 위치 객체 생성
		
				// 커스텀 이미지의 URL 생성
				var imageUrl = contextPath + "/img/" + item.contentTypeId + ".png"; // 예: item.contentid 값이 "12"이면, 이미지 URL은 "./img/12.png"
				var imageSize = new kakao.maps.Size(45, 45); // 마커 이미지의 크기 설정
				// 마커 이미지 생성
				var markerImage = new kakao.maps.MarkerImage(imageUrl, imageSize);
				var marker = new kakao.maps.Marker({
					position: position, // 마커 위치 설정
					image: markerImage, // 커스텀 마커 이미지 설정
				});
		
				marker.setMap(map); // 마커를 지도에 표시
				markers.push(marker); // 생성된 마커를 markers 배열에 추가
				bounds.extend(position); // LatLngBounds 객체에 현재 마커의 위치를 추가
		
				// 커스텀 오버레이에 표시될 내용 생성
				var content =
					'<div class="wrap">' +
					'    <div class="info">' +
					'        <div class="title">' +
					item.title +
					'            <div class="close" onclick="closeOverlay()" title="닫기"></div>' +
					"        </div>" +
					'        <div class="body">' +
					'            <div class="img">' +
					'                <img src="' +
					(item.firstImage
						? item.firstImage
						: contextPath + "/img/no_image.jpg") +
					'" alt="이미지" width="80px" height="80px">' +
					"            </div>" +
					'            <div class="desc">' +
					'                <div class="ellipsis">주소: ' +
					(item.addr ? item.addr : "정보 없음") +
					"</div>" +
					'                <div class="jibun ellipsis">전화번호: ' +
					(item.tel ? item.tel : "정보 없음") +
					"</div>" +
					'                <div><a href="#" target="_blank" class="link">상세보기</a></div>' +
					"            </div>" +
					"        </div>" +
					"    </div>" +
					"</div>";
		
				// 커스텀 오버레이 생성 및 지도에 추가하지 않음 (초기에는 숨김)
				var overlay = new kakao.maps.CustomOverlay({
					content: content,
					map: null,
					position: position,
					// xAnchor: 0.3,
					// yAnchor: 0.91,
					// zIndex: 3,
				});
		
				// 마커 클릭 시 커스텀 오버레이 표시
				kakao.maps.event.addListener(marker, "click", function() {
					closeOverlay();
					overlay.setMap(map); // 현재 오버레이 표시
					currentOverlay = overlay; // 참조 업데이트
					map.setCenter(position);
				});
			}
		});
		// 모든 마커가 포함되도록 지도의 중심과 줌 레벨 조정
		map.setBounds(bounds);
	}

	// 세 번째 드롭다운 선택 시 이벤트 리스너 추가
	tourTypeSelect.addEventListener("change", function() {
		const selectedContentId = this.value;
		console.log(selectedContentId);
		// 모든 마커를 지도에서 제거
		markers.forEach(function(marker) {
			marker.setMap(null);
		});
		markers = []; // 마커 배열 초기화

		var bounds = new kakao.maps.LatLngBounds();
		let flag = false;
		tourData.forEach(function(item) {
			if (item.contentTypeId == selectedContentId) {
				flag = true;
				var position = new kakao.maps.LatLng(item.latitude, item.longitude);
				var imageUrl = contextPath + "/img/" + item.contentTypeId + ".png";
				var imageSize = new kakao.maps.Size(45, 45);
				var markerImage = new kakao.maps.MarkerImage(
					imageUrl,
					imageSize
				);
				var marker = new kakao.maps.Marker({
					position: position,
					image: markerImage,
				});

				// 커스텀 오버레이에 표시될 내용 생성
				var content = `<div class="wrap">
                                    <div class="info">
                                        <div class="title">
                                            ${item.title}
                                            <div class="close" onclick="currentOverlay.setMap(null);" title="닫기"></div>
                                        </div>
                                        <div class="body">
                                            <div class="img">
                                                <img src="${item.firstimage
						? item.firstimage
						: contextPath + "/img/no_image.jpg"
					}" width="80px" height="80px">
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
                                                <div><a href="#" target="_blank" class="link">상세보기</a></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>`;

				// 커스텀 오버레이 생성
				var overlay = new kakao.maps.CustomOverlay({
					content: content,
					map: null, // 초기에는 지도에 표시하지 않음
					position: position,
				});

				// 마커 클릭 이벤트. 이벤트 리스너에 클릭 이벤트 추가
				kakao.maps.event.addListener(marker, "click", function() {
					// 이전에 열려있는 오버레이가 있으면 닫기
					closeOverlay();
					// 클릭한 마커에 해당하는 오버레이 지도에 표시
					overlay.setMap(map);
					currentOverlay = overlay; // 현재 오버레이 업데이트
					map.setCenter(position);
				});

				marker.setMap(map);
				markers.push(marker); // 생성된 마커를 배열에 추가
				bounds.extend(position); // 지도 범위 재설정
			}
		});
		if(flag){
			map.setBounds(bounds);
		}else{
			const areaCode = regionSelect.value;
			const sigunguCode = subregionSelect.value;
			alert("해당 관광 정보가 존재하지 않습니다.");
			fetchAllTourData(areaCode, sigunguCode);
		}
		// 모든 마커가 포함되도록 지도의 범위를 재설정
	});

	// ================ 여행 검색 =======================

	// // 이전에 표시된 마커를 모두 제거
	// markers.forEach(marker => marker.setMap(null));
	// markers = []; // 마커 배열 초기화

	// 검색 버튼에 클릭 이벤트 리스너 추가
	const searchBtn = document.querySelector("#searchButton");
	if (searchBtn != null) {
		searchBtn.addEventListener("click", function(event) {
			performSearch();
		});
	}

	// 검색 입력 필드에 엔터 키 이벤트 리스너 추가
	const searchInput = document.querySelector("#searchInput");
	if (searchInput != null) {
		searchInput.addEventListener("keypress", function(event) {
			// 엔터 키가 눌렸는지 확인
			if (event.keyCode === 13 || event.which === 13) {
				// event.which는 오래된 브라우저 호환성을 위해 추가
				performSearch();

				// 폼의 기본 제출을 방지
				event.preventDefault();
			}
		});
	}
});

// 검색 버튼
// 검색 기능을 수행하는 함수
async function performSearch() {
	// 검색 입력 필드에서 검색어 가져오기
	var searchInput = document.getElementById("searchInput").value;

	// 검색어 출력
	if (searchInput.trim() === "") {
		console.log("검색어를 입력해주세요.");
		return;
	}
	var encodedKeyword = encodeURIComponent(searchInput.trim());
	const API_URL = contextPath + `/trip?action=search&keyWord=${encodedKeyword}`;
	// API 호출
	await fetch(API_URL)
		.then((response) => response.json())
		.then((data) => {
			tempListItems = data;
			console.log(data);
			// 새로운 LatLngBounds 객체 생성
			var bounds = new kakao.maps.LatLngBounds();
			tempListItems.forEach((item, index) => {
				// 각 항목의 위치에 마커 생성
				var position = new kakao.maps.LatLng(item.mapy, item.mapx);
				var marker = new kakao.maps.Marker({
					map: map,
					position: position,
				});

				// 마커를 markers 배열에 추가
				markers.push(marker);

				// LatLngBounds 객체에 현재 마커의 위치를 추가
				bounds.extend(position);

				// 커스텀 오버레이에 표시될 내용 생성
				var content = `<div class="wrap">
                                    <div class="info">
                                        <div class="title">
                                            ${item.title}
                                            <div class="close" onclick="currentOverlay.setMap(null);" title="닫기"></div>
                                        </div>
                                        <div class="body">
                                            <div class="img">
                                                <img src="${item.firstimage
						? item.firstimage
						: contextPath + "/img/no_image.jpg"
					}" width="80px" height="80px">
                                            </div>
                                            <div class="desc">
                                                <div class="ellipsis">주소: ${item.addr1
						? item.addr1
						: "정보 없음"
					}</div>
                                                <div class="jibun ellipsis">전화번호: ${item.tel
						? item.tel
						: "정보 없음"
					}</div>
                                                <button onclick="addToPlanList(${index})">추가하기</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>`;
				var overlay = new kakao.maps.CustomOverlay({
					content: content,
					map: map,
					position: marker.getPosition(),
				});
				overlay.setMap(null); // 초기에는 숨김

				// 마커 클릭 이벤트: 정보 창 표시
				kakao.maps.event.addListener(marker, "click", function() {
					closeOverlay();
					overlay.setMap(map);
					currentOverlay = overlay;
				});
			});

			// 모든 마커가 포함되도록 지도 범위를 조정
			map.setBounds(bounds);
			console.log("검색 결과:", items);
		})
}

// 여행 계획 목록에 추가
function addToPlanList(index) {
	var item = tempListItems[index];
	// 중복 검사
	if (planItems.some((planItem) => planItem.title === item.title)) {
		console.log("이미 추가된 항목입니다.");
		return; // 이미 목록에 존재하는 항목이면 함수 실행을 종료
	}

	// 목록 항목 생성
	var planList = document.getElementById("planItems");
	var listItem = document.createElement("li");
	listItem.innerHTML = `<div class="list-group-item">
        <h5 class="mb-1">${item.title}</h5>
        <p class="mb-1">${item.addr1 || "주소 정보 없음"}</p>
        <p class="mb-1">${item.tel || "전화번호 정보 없음"}</p>
        <img src="${item.firstimage || contextPath + "/img/no_image.jpg"
		}" width="80" height="80" class="rounded float-right">
        <button onclick="removeFromPlanList(this.parentElement, '${item.title
		}')" class="btn btn-danger btn-sm">X</button>
    </div>`;
	listItem.dataset.title = item.title; // 중복 검사를 위한 데이터 속성 설정
	planList.appendChild(listItem);

	// 추가된 아이템 정보를 planItems 배열에 저장
	planItems.push(item);

	// 새로운 아이템과 마지막 아이템을 연결하는 선분 추가
	if (planItems.length > 1) {
		var lastItem = planItems[planItems.length - 2]; // 마지막에서 두 번째 아이템
		var polyline = new kakao.maps.Polyline({
			path: [
				new kakao.maps.LatLng(lastItem.mapy, lastItem.mapx),
				new kakao.maps.LatLng(item.mapy, item.mapx),
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

// 목록에서 항목 제거하는 함수
function removeFromPlanList(listItem, title) {
	listItem.remove(); // 항목 제거

	// planItems 배열에서 해당 아이템 정보 제거
	planItems = planItems.filter((planItem) => planItem.title !== title);

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
				new kakao.maps.LatLng(startItem.mapy, startItem.mapx),
				new kakao.maps.LatLng(endItem.mapy, endItem.mapx),
			],
			// 선분 스타일 설정
		});
		polyline.setMap(map);
		polylines.push(polyline);
	}
}

// plan
const planBtn = document.querySelector("#savePlanButton");
if (planBtn != null) {
	planBtn.addEventListener("click", function() {
		// 고유한 planId 생성 (예시: 현재 날짜와 시간을 이용)
		var planId = "plan_" + new Date().toISOString();

		// planItems 배열을 로컬 스토리지에 저장
		localStorage.setItem(planId, JSON.stringify(planItems));

		alert("여행 계획이 등록되었습니다.");

		// 등록 후 planItems 배열 초기화 및 목록 UI 업데이트
		planItems = [];
		document.getElementById("planItems").innerHTML = "";
	});
} 
