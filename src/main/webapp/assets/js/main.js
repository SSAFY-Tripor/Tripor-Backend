
window.onload = function() {
	const myPageExpandDiv = document.querySelector("#myPageExpand");
	const expandIcon = document.querySelector("#expandIcon");
	const foldIcon = document.querySelector("#foldIcon");

	expandIcon.addEventListener("click", function() {
		myPageExpandDiv.style.display = "block";
		expandIcon.style.display = "none";
		foldIcon.style.display = "block";
	});
	foldIcon.addEventListener("click", function() {
		myPageExpandDiv.style.display = "none";
		expandIcon.style.display = "block";
		foldIcon.style.display = "none";
	});
};

function secession() {
	const contextPath = document.querySelector('body').getAttribute('data-context-path');
	const y = confirm("정말로 회원탈퇴하시겠습니까?");
	const session = require('web.session');
	if (y && session.memeber != null) {
		location.href = `${contextPath}/member?action=delete`;
	}
}

const getSidoList = async () =>{
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


if (editorForm != null) {
	editorForm.addEventListener("submit", onEditorFormSubmit);
}


// 게시판

const boardTableBody = document.querySelector("#board-body");

const contentsContainer = document.querySelector(".contents__container");

const editorForm = document.querySelector("#editor-form");
const titleInput = document.querySelector("#editor-title-input");
const contentInput = document.querySelector("#editor-content-input");

const contentView = document.querySelector("#contents_container");
const postlist = document.querySelector("#board-container");
const makepost = document.querySelector("#editor-form");

function writePost() {
	postlist.style.display = "none";
	makepost.style.display = "block";
	contentView.style.display = "none";
}

function post() {
	postlist.style.display = "block";
	makepost.style.display = "none";
	contentView.style.display = "none";
}

function assignIndex() {
	const lists = JSON.parse(localStorage.getItem(BOARDLIST_LS));
	if (!lists) {
		nums = 0;
	} else {
		nums = parseInt(lists[lists.length - 1].num) + 1;
	}
}

function onEditorFormSubmit(e) {
	e.preventDefault();

	const title = titleInput.value;
	const content = contentInput.value;

	const lists = JSON.parse(localStorage.getItem(BOARDLIST_LS));
	if (!lists) {
		const objArr = [];
		objArr.push({
			num: `${nums++}`,
			title: `${title}`,
			author: `${author}`,
			date: `${date.getFullYear()}.${date.getMonth() + 1
				}.${date.getDate()}.`,
			views: `${views++}`,
			content: `${content}`,
		});

		localStorage.setItem(BOARDLIST_LS, JSON.stringify(objArr));
	} else {
		lists.push({
			num: `${nums++}`,
			title: `${title}`,
			author: `${author}`,
			date: `${date.getFullYear()}.${date.getMonth() + 1
				}.${date.getDate()}.`,
			views: `${views++}`,
			content: `${content}`,
		});

		localStorage.setItem(BOARDLIST_LS, JSON.stringify(lists));
	}

	titleInput.value = "";
	contentInput.value = "";
	titleInput.focus();
	window.location.reload();
}

function showBoardLists() {
	const lists = JSON.parse(localStorage.getItem(BOARDLIST_LS));

	lists.forEach((list, index) => {
		if (index < 5) {
			const tr = document.createElement("tr");
			tr.classList.add("board__content");

			const tdNum = document.createElement("td");
			tdNum.classList.add("board__content-column");
			tdNum.textContent = list.num;

			const aTitle = document.createElement("a");
			aTitle.href = "#";
			aTitle.id = `link-to-content${index}`;

			const tdTitle = document.createElement("td");
			tdTitle.classList.add("board__content-column");
			tdTitle.textContent = list.title;

			aTitle.appendChild(tdTitle);

			const tdAuthor = document.createElement("td");
			tdAuthor.classList.add("board__content-column");
			tdAuthor.textContent = list.author;

			const tdDate = document.createElement("td");
			tdDate.classList.add("board__content-column");
			tdDate.textContent = list.date;

			const tdViews = document.createElement("td");
			tdViews.classList.add("board__content-column");
			tdViews.textContent = list.views;

			tr.appendChild(tdNum);
			tr.appendChild(aTitle);
			tr.appendChild(tdAuthor);
			tr.appendChild(tdDate);
			tr.appendChild(tdViews);

			boardTableBody.appendChild(tr);
			const linkToContent = document.querySelector(
				`#link-to-content${index}`
			);
			linkToContent.addEventListener("click", onTitleClick);
		} else if (index === 5) {
			const boardIndexMax = Math.ceil(lists.length / 5);
			for (let i = 0; i < boardIndexMax; i++) {
				const indexContainer =
					document.querySelector("#index-container");

				const aIndex = document.createElement("a");
				aIndex.classList.add("board__index-link");

				const spanIndexText = document.createElement("span");
				spanIndexText.classList.add("board__index");
				spanIndexText.textContent = i + 1;

				aIndex.appendChild(spanIndexText);
				indexContainer.appendChild(aIndex);

				aIndex.addEventListener("click", () => {
					showBoardListsNewPage(i);
				});
			}
		}
	});
}

function showBoardListsNewPage(pageIndex) {
	boardTableBody.textContent = "";
	const lists = JSON.parse(localStorage.getItem(BOARDLIST_LS));
	const limitPage = pageIndex * 5;

	lists.forEach((list, index) => {
		if (index >= limitPage && index < limitPage + 5) {
			const tr = document.createElement("tr");
			tr.classList.add("board__content");

			const tdNum = document.createElement("td");
			tdNum.classList.add("board__content-column");
			tdNum.textContent = list.num;

			const aTitle = document.createElement("a");
			aTitle.href = "#";
			aTitle.id = `link-to-content${index}`;

			const tdTitle = document.createElement("td");
			tdTitle.classList.add("board__content-column");
			tdTitle.textContent = list.title;

			aTitle.appendChild(tdTitle);

			const tdAuthor = document.createElement("td");
			tdAuthor.classList.add("board__content-column");
			tdAuthor.textContent = list.author;

			const tdDate = document.createElement("td");
			tdDate.classList.add("board__content-column");
			tdDate.textContent = list.date;

			const tdViews = document.createElement("td");
			tdViews.classList.add("board__content-column");
			tdViews.textContent = list.views;

			tr.appendChild(tdNum);
			tr.appendChild(aTitle);
			tr.appendChild(tdAuthor);
			tr.appendChild(tdDate);
			tr.appendChild(tdViews);

			boardTableBody.appendChild(tr);
			const linkToContent = document.querySelector(
				`#link-to-content${index}`
			);
			linkToContent.addEventListener("click", onTitleClick);
		}
	});
}

function deleteArticle(){
			if (window.confirm('게시물을 삭제하시겠습니까?'))
			{
			    location.href="${root}/board?action=delete&boardno=${board.boardNo}";
			}
}





