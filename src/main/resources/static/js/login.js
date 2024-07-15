const loginButton = document.querySelector('button[type="submit"]');

$(document).ready(function () {
  // 토큰 삭제
  Cookies.remove('Authorization', { path: '/' });
});

const href = location.href;

const host = 'http://' + window.location.host;

$('button[type="submit"]').click(function (event) {
  let userId = $('#userId').val();
  let password = $('#userPassword').val();

  console.log("userId: " + userId);
  console.log("password: " + password);

  $.ajax({
    type: "POST",
    url: `/users/login`,
    contentType: "application/json",
    data: JSON.stringify({ userId: userId, password: password }),
  })
  .done(function (res, status, xhr) {
    const token = xhr.getResponseHeader('Authorization');
    console.log("!!@@!!");

    localStorage.setItem('token', token);

    console.log(token);

    // URL 이동 코드 추가
    window.location.href = "http://localhost:8080/home";
  })
  .fail(function (jqXHR, textStatus) {
    alert("아이디 또는 비밀번호가 일치하지 않습니다.");
  });
});

function onLogin() {
  // 현재는 아무 내용도 추가되지 않았습니다.
}

loginButton.addEventListener('click', onLogin);

function beforeSendSetup(xhr) {
  const token = localStorage.getItem("token");
  xhr.setRequestHeader("Content-type", "application/json");
  xhr.setRequestHeader("Authorization", "JWT " + token);
}

function getUserInfo(token) {
  $.ajax({
    type: 'GET',
    url: `/api/user-info`,
    contentType: 'application/json',
    beforeSend: beforeSendSetup,
  })
  .done(function (res, status, xhr) {
    const username = res.username;
    const isAdmin = !!res.admin;

    if (!username) {
      window.location.href = '/api/user/login-page';
      return;
    }

    $('#username').text(username);
    if (isAdmin) {
      $('#admin').text(true);
      showBoard();
    } else {
      showBoard();
    }

    // 로그인한 유저의 폴더
    $.ajax({
      type: 'GET',
      url: `/boards`,
      beforeSend: beforeSendSetup,
      error(error) {
        logout();
      }
    }).done(function (fragment) {
      console.log("success");
    });

  })
  .fail(function (jqXHR, textStatus) {
    logout();
  });
}

function showBoard() {
  $.ajax({
    type: 'GET',
    url: '/api/boards',  // 서버에서 보드 목록을 가져오는 엔드포인트
    contentType: 'application/json',
    beforeSend: beforeSendSetup,
    success: function (data) {
      let boardContainer = $('.board-container'); // 보드 목록을 표시할 HTML 요소
      boardContainer.empty(); // 기존 내용을 비웁니다

      data.boards.forEach(board => {
        let boardElement = `
          <div class="board" data-board-id="${board.id}">
            <div class="board-top">
              <div class="board-intro-container">
                <h3>${board.title}</h3>
                <p>${board.description}</p>
              </div>
              <a href="#" class="create-column">
                <i class="fa-solid fa-plus"></i>
              </a>
            </div>
            <div class="column-container">
              ${board.columns.map(column => `
                <div class="column" draggable="true">
                  <div class="column-status-container">
                    <span class="column-status">${column.status}</span>
                    <input type="text" class="edit-column-input" style="display:none;">
                    <div class="btn-container">
                      <a href="#" class="edit-column">
                        <i class="fa-regular fa-pen-to-square"></i>
                      </a>
                      <a href="#" class="create-card">
                        <i class="fa-solid fa-plus"></i>
                      </a>
                      <a href="#" class="remove-column">
                        <i class="fa-solid fa-xmark"></i>
                      </a>
                    </div>
                  </div>
                  <div class="card-container">
                    ${column.cards.map(card => `
                      <a href="#" class="card" draggable="true">${card.title}</a>
                    `).join('')}
                  </div>
                </div>
              `).join('')}
            </div>
          </div>`;
        boardContainer.append(boardElement); // 보드 요소를 추가합니다
      });
    },
    error: function (error) {
      console.error('Failed to load boards', error);
    }
  });
}

function logout() {
  Cookies.remove('Authorization', { path: '/' });
  localStorage.removeItem('token');
  window.location.href = '/api/user/login-page'; // 로그인 페이지로 리디렉션
}