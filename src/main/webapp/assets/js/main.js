let expandFlag = false;
const expandClickEventListener = (expandDiv, expandIcon, foldIcon) => {
	if (expandFlag) {
		expandFlag = false;
		expandDiv.style.display = "none";
		expandIcon.style.display = "block";
		foldIcon.style.display = "none";
	} else {
		expandFlag = true;
		expandDiv.style.display = "block";
		expandIcon.style.display = "none";
		foldIcon.style.display = "block";
	}
}
const expandIconClickEventListener = (expandDiv, expandIcon, foldIcon) => {
	expandDiv.style.display = "block";
	expandIcon.style.display = "none";
	foldIcon.style.display = "block";
}
const foldIconClickEventListener = (expandDiv, expandIcon, foldIcon) => {
	expandDiv.style.display = "none";
	expandIcon.style.display = "block";
	foldIcon.style.display = "none";
}

const myPageDiv = document.querySelector("#myPageDiv");
const myPageExpandDiv = document.querySelector("#myPageExpand");
const expandIcon = document.querySelector("#expandIcon");
const expandDiv = document.querySelector("#expandDiv");
const foldIcon = document.querySelector("#foldIcon");
if (expandDiv != null) {
	expandDiv.addEventListener("click", () => { expandClickEventListener(myPageExpandDiv, expandIcon, foldIcon) });
}

// 회원 탈퇴 버튼 클릭 이벤트리스너
const secession = () => {
	const contextPath = document.querySelector('body').getAttribute('data-context-path');
	const y = confirm("정말로 회원탈퇴하시겠습니까?");
	const session = require('web.session');
	if (y && session.memeber != null) {
		location.href = `${contextPath}/member?action=delete`;
	}
}

const getSidoList = async () => {
	const contextPath = document.querySelector('body').getAttribute('data-context-path');
	const url = `http://localhost:8080/${contextPath}`;
	const sidoParam = `/trip?action=sido`;
	// 데이터 요청 및 처리

	const response = await fetch(url + sidoParam);
	const data = await response.json();
	// 기본 옵션 추가
	joinSidoSelector.innerHTML = '<option value="">도 선택</option>';
	// 받은 데이터를 통해 옵션 추가
	data.forEach((item) => {
		const option = document.createElement("option");
		option.value = item.sidoCode;
		option.textContent = item.sidoName;
		joinSidoSelector.appendChild(option);
	});

	// 상수 정의
	// 두번째 드롭다운
	const joinGugunSelector = document.querySelector("#join-gugun");

	joinSidoSelector.addEventListener("change", async function() {
		const selectedRegionCode = this.value;
		const gugunParam = `/trip?action=gugun&sido=${selectedRegionCode}`;
		// 이전에 선택된 서브리전 옵션을 초기화
		joinGugunSelector.innerHTML = '<option value="">시/구 선택</option>';

		const response = await fetch(url + gugunParam)
		const data = await response.json();
		data.forEach((item) => {
			const option = document.createElement("option");
			option.value = item.gugunCode;
			option.textContent = item.gugunName;
			joinGugunSelector.appendChild(option);
		});
	});
}

const joinSidoSelector = document.querySelector("#join-sido")
if (joinSidoSelector != null) {
	getSidoList();
}

