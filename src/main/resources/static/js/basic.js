import {
  createNewColumn,
  initializeSortableCardContainer,
  initializeSortableColumns,
  deleteColumnFromServer,
  deleteCardFromServer,
  saveColumnsToServer,
  createCard
} from './drag-drop.js';

const host = 'http://' + window.location.host;
let targetId;
let folderTargetId;

$(document).ready(function () {
  const boardsContainer = document.querySelector('.boards-container');

  const auth = getToken();

  if (auth !== undefined && auth !== '') {
    $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
      jqXHR.setRequestHeader('Authorization', auth);
    });
  } else {
    window.location.href = host + '/users/login-page';
    return;
  }

  $.ajax({
    type: 'GET',
    url: `/users/user-info`,
    contentType: 'application/json',
  })
  .done(function (res, status, xhr) {
    const username = res.username;
    const isAdmin = !!res.admin;

    if (!username) {
      window.location.href = '/users/login-page';
      return;
    }

    $('#navUsername').text(username);
    if (isAdmin) {
      // $('#admin').text(true);
      // showProduct();
    } else {
      // showProduct();
    }

    // 로그인한 유저의 폴더
    $.ajax({
      type: 'GET',
      url: `/boards`,
      error(error) {
        // logout();
      }
    }).done(function (res, status, xhr) {

      for (let i = 0; i < res.data.length; i++) {
        let boardTemplate = `
      <div class="board-container">
    <div class="board-top">
      <div class="board-intro-container">
        <h3>${res.data[i].boardName}</h3>
        <p>${res.data[i].info}</p>
      </div>
      <a href="#" class="create-column">
        <i class="fa-solid fa-plus"></i>
      </a>
    </div>
    <div class="column-container">
      
    </div>
  </div>
  `;

        let boardId = res.data[i].boardId;
        boardsContainer.insertAdjacentHTML('beforeend', boardTemplate);

        const currentBoard = boardsContainer.lastElementChild; // 마지막으로 추가된 board-container 요소 가져오기
        const columnContainer = currentBoard.querySelector('.column-container');

        for (let j = 0; j < res.data[i].sections.length; j++) {
          let columnTemplate = `
        <div class="column" draggable="true">
        <div class="column-status-container">
          <span class="column-status">${res.data[i].sections[j].status}</span>
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
        <div class="card-container"></div>
      </div>
        `;

          let columnId = res.data[i].sections[j].sectionId;
          columnContainer.insertAdjacentHTML('beforeend', columnTemplate);

          const currentColumn= columnContainer.lastElementChild; // 마지막으로 추가된 board-container 요소 가져오기
          const cardContainer= currentColumn.querySelector('.card-container');

          for (let k = 0; k < res.data[i].sections[j].cards.length; k++) {
            let cardId = res.data[i].sections[j].cards[k].cardId;
            let cardTemplate = `
            <a href="/boards/${boardId}/columns/${columnId}/cards/${cardId}" class="card" draggable="true">${res.data[i].sections[j].cards[k].title}</a>
            `;

            cardContainer.insertAdjacentHTML('beforeend', cardTemplate);
          }
        }
      }

      // $('#fragment').replaceWith(fragment);
    });

  })
  .fail(function (jqXHR, textStatus) {
    // logout();
  });

  document.addEventListener('click', function(event) {
    if (event.target.closest('.remove-column')) {
      const column = event.target.closest('.column');
      const columnId = column.getAttribute('data-column-id');
      column.remove();

      // 컬럼 삭제 from 서버
      const boardId = column.closest('.board-container').getAttribute('data-board-id');
      deleteColumnFromServer(columnId, boardId);
    }

    if (event.target.closest('.edit-column')) {
      const column = event.target.closest('.column');
      const statusSpan = column.querySelector('.column-status');
      const input = column.querySelector('.edit-column-input');

      statusSpan.style.display = 'none';
      input.style.display = 'inline';
      input.value = statusSpan.textContent;
      input.focus();

      input.addEventListener('blur', () => {
        statusSpan.textContent = input.value;
        statusSpan.style.display = 'inline';
        input.style.display = 'none';

        // 서버에 저장
        const boardId = column.closest('.board-container').getAttribute('data-board-id');
        saveColumnsToServer(boardId);
      });
    }

    if (event.target.closest('.remove-card')) {
      const card = event.target.closest('.card');
      const cardId = card.getAttribute('data-card-id');
      const columnId = card.closest('.column').getAttribute('data-column-id');
      card.remove();

      // 카드 삭제 from 서버
      const boardId = card.closest('.board-container').getAttribute('data-board-id');
      deleteCardFromServer(columnId, cardId, boardId);
    }
  });

  // id 가 query 인 녀석 위에서 엔터를 누르면 execSearch() 함수를 실행하라는 뜻입니다.
  // $('#query').on('keypress', function (e) {
  //   if (e.key == 'Enter') {
  //     execSearch();
  //   }
  // });
  // $('#close').on('click', function () {
  //   $('#container').removeClass('active');
  // })
  // $('#close2').on('click', function () {
  //   $('#container2').removeClass('active');
  // })
  // $('.nav div.nav-see').on('click', function () {
  //   $('div.nav-see').addClass('active');
  //   $('div.nav-search').removeClass('active');
  //
  //   $('#see-area').show();
  //   $('#search-area').hide();
  // })
  // $('.nav div.nav-search').on('click', function () {
  //   $('div.nav-see').removeClass('active');
  //   $('div.nav-search').addClass('active');
  //
  //   $('#see-area').hide();
  //   $('#search-area').show();
  // })
  //
  // $('#see-area').show();
  // $('#search-area').hide();
});

function getToken() {

  let auth = Cookies.get('Authorization');

  if(auth === undefined) {
    return '';
  }

  // kakao 로그인 사용한 경우 Bearer 추가
  if(auth.indexOf('Bearer') === -1 && auth !== ''){
    auth = 'Bearer ' + auth;
  }

  return auth;
}