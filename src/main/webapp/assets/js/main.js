
window.onload = function () {
    const myPageExpandDiv = document.querySelector("#myPageExpand");
    const expandIcon = document.querySelector("#expandIcon");
    const foldIcon = document.querySelector("#foldIcon");

    expandIcon.addEventListener("click", function () {
        myPageExpandDiv.style.display = "block";
        expandIcon.style.display = "none";
        foldIcon.style.display = "block";
    });
    foldIcon.addEventListener("click", function () {
        myPageExpandDiv.style.display = "none";
        expandIcon.style.display = "block";
        foldIcon.style.display = "none";
    });

    // 여행지 검색 입력 필드와 검색 버튼 숨기기
    //document.querySelector(
    //    "#makePlace"
    //).style.cssText = `display: none !important;`;
    //document.querySelector("#planList").style.display = "none";

    // 로그인, 회원가입 버튼 / 로그아웃 버튼
    const joinBtn = document.querySelector("#joinButton");
    const logInBtn = document.querySelector("#logInButton");
    const logOutBtn = document.querySelector("#logOutButton");

    // 테이블 만들기
    const tableBody = document.querySelector("#table_body");
    let idx = localStorage.getItem("cnt");
    for (let i = idx; i >= 1; i--) {
        const postdata = JSON.parse(localStorage.getItem(i));
        tableBody.innerHTML += `
      <tr>
          <td>3</td>
          <th>
          <a href="#!">[공지사항] 개인정보 처리방침 변경안내처리방침</a>
          </th>
          <td>김민선</td>
          <td>2017.07.13</td>
      </tr>
    `;
    }
};

/*
const homeDiv = document.querySelector("#home_div");
const loginDiv = document.querySelector("#login_div");
const joinDiv = document.querySelector("#join_div");
const myPageDiv = document.querySelector("#myPage_div");
const findPwdDiv = document.querySelector("#findPwd_div");
const boardDiv = document.querySelector("#board_div");
const planList = document.querySelector("#planList");

function makeHome() {
    homeDiv.style.display = "block";
    loginDiv.style.display = "none";
    joinDiv.style.display = "none";
    myPageDiv.style.display = "none";
    findPwdDiv.style.display = "none";
    boardDiv.style.display = "none";
    planList.style.display = "none";

    // 관광지 선택 드롭다운 보여주기
    document.querySelector("#searchPlace").style.cssText = `
        display: flex !important;
        flex-direction: row !important;
        justify-content: center; 
        align-items: center; 
        `;
    // 여행지 검색 입력 필드와 검색 버튼 숨기기
    element = document.querySelector("#makePlace");
    element.style.cssText += "display: none !important;";
    document.querySelector("#planList").style.display = "none !important";
}


function makeJoin() {
    homeDiv.style.display = "none";
    loginDiv.style.display = "none";
    joinDiv.style.display = "block";
    myPageDiv.style.display = "none";
    findPwdDiv.style.display = "none";
    boardDiv.style.display = "none";
    planList.style.display = "none";
}

function makeLogIn() {
    homeDiv.style.display = "none";
    loginDiv.style.display = "block";
    joinDiv.style.display = "none";
    myPageDiv.style.display = "none";
    findPwdDiv.style.display = "none";
    boardDiv.style.display = "none";
    planList.style.display = "none";
}

function makePlan() {
    homeDiv.style.display = "block";
    loginDiv.style.display = "none";
    joinDiv.style.display = "none";
    myPageDiv.style.display = "none";
    findPwdDiv.style.display = "none";
    boardDiv.style.display = "none";
    planList.style.display = "block";

    // 관광지 선택 드롭다운 숨기기
    document.querySelector("#searchPlace").style.cssText +=
        "display: none !important;";
    // 여행지 검색 입력 필드와 검색 버튼 보여주기
    document.querySelector("#makePlace").style.cssText = `
        display: flex !important;
        flex-direction: row !important;
        justify-content: center; 
        align-items: center; 
        `;
    document.querySelector("#planList").style.display = "block !important";
}

function makeMyPage() {
    // 로그인이 안 되어 있을 경우
    if (localStorage.getItem("login") == null) {
        alert("로그인 후 이용 가능합니다.");
        return;
    }

    homeDiv.style.display = "none";
    loginDiv.style.display = "none";
    joinDiv.style.display = "none";
    myPageDiv.style.display = "block";
    findPwdDiv.style.display = "none";
    boardDiv.style.display = "none";

    const curUser = localStorage.getItem("currentUser");
    console.log(curUser);
    document.querySelector("#mypage_name").value = JSON.parse(
        localStorage.getItem(curUser)
    ).name;
    document.querySelector("#mypage_email").value = JSON.parse(
        localStorage.getItem(curUser)
    ).email;
    document.querySelector("#mypage_pwd").value = JSON.parse(
        localStorage.getItem(curUser)
    ).pwd;
}

function makeFindPwd() {
    homeDiv.style.display = "none";
    loginDiv.style.display = "none";
    joinDiv.style.display = "none";
    myPageDiv.style.display = "none";
    findPwdDiv.style.display = "block";
    boardDiv.style.display = "none";
}

function makeBoard() {
    homeDiv.style.display = "none";
    loginDiv.style.display = "none";
    joinDiv.style.display = "none";
    myPageDiv.style.display = "none";
    findPwdDiv.style.display = "none";
    boardDiv.style.display = "block";
}
*/

function secession() {
    const y = confirm("정말로 회원탈퇴하시겠습니까?");
    const contextPath = document.querySelector('body').getAttribute('data-context-path');
    const curUser = localStorage.getItem("currentUser");
    if (y) {
		location.href = `${contextPath}/member?action=delete`;
    }
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
/*
const BOARDLIST_LS = "boardLists";
const boardListsObj = [];
let nums = 0;
let author = JSON.parse(
    localStorage.getItem(localStorage.getItem("currentUser"))
).name;
let date = new Date();
let views = Math.floor(Math.random() * 99) + 1;

function onTitleClick(e) {
    postlist.style.display = "none";
    makepost.style.display = "none";
    contentView.style.display = "block";
    contentsContainer.textContent = "";
    const lists = JSON.parse(localStorage.getItem(BOARDLIST_LS));
    const index = e.target.parentNode.id.replace(/[a-z|-]/gi, "");

    const contentsTitles = document.createElement("div");
    contentsTitles.classList.add("contents__titles");

    const contentsColumnFirst = document.createElement("div");
    contentsColumnFirst.classList.add("contents__column");

    const contentsTitle = document.createElement("div");
    contentsTitle.classList.add("contents__title");
    contentsTitle.textContent = lists[index].title;
    contentsTitle.setAttribute("style", "font-size: 40px");

    // contents__titles > column >author, date, views
    const contentsColumnSecond = document.createElement("div");
    contentsColumnSecond.classList.add("contents__column");

    const contentsAuthor = document.createElement("span");
    contentsAuthor.classList.add("contents__author");
    contentsAuthor.textContent = lists[index].author + " | ";

    const contentsDate = document.createElement("span");
    contentsDate.classList.add("contents__date");
    contentsDate.textContent = lists[index].date + " | ";

    const contentsViews = document.createElement("span");
    contentsViews.classList.add("contents__views");

    const contentsContent = document.createElement("div");
    contentsContent.classList.add("contents__content");
    contentsContent.textContent = lists[index].content;
    contentsContent.setAttribute("style", "font-size: 20px");

    contentsColumnFirst.appendChild(contentsTitle);

    contentsColumnSecond.appendChild(contentsAuthor);
    contentsColumnSecond.appendChild(contentsDate);
    contentsColumnSecond.appendChild(contentsViews);

    contentsTitles.appendChild(contentsColumnFirst);
    contentsTitles.appendChild(contentsColumnSecond);

    contentsContainer.appendChild(contentsTitles);
    contentsContainer.appendChild(contentsContent);
}
*/
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
            date: `${date.getFullYear()}.${
                date.getMonth() + 1
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
            date: `${date.getFullYear()}.${
                date.getMonth() + 1
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
if(editorForm != null){
	editorForm.addEventListener("submit", onEditorFormSubmit);
}


