const host = 'http://' + window.location.host;
const boardsContainer = document.querySelector('.boards-container');

$(document).ready(function () {

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
    <div class="column-container"></div>
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
    });

  })
  .fail(function (jqXHR, textStatus) {
    // logout();
  });
})

boardsContainer.addEventListener('click', (event) => {
  let columnTemplate = `
 <div class="column" draggable="true">
        <div class="column-status-container">
          <span class="column-status"></span>
          <input type="text" class="edit-column-input hidden">
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
  if (event.target.closest('.create-column')) {
    const columnContainer = event.target.closest('.board-container').querySelector('.column-container');
    if (columnContainer) {
      columnContainer.insertAdjacentHTML('beforeend', columnTemplate);
    }
  }

  if (event.target.closest('.remove-column')) {
    const columnToRemove = event.target.closest('.column');
    if (columnToRemove) {
      columnToRemove.remove();
    }
  }

  if (event.target.closest('.edit-column')) {
    const columnToEdit = event.target.closest('.column').querySelector('.edit-column-input');
    if (columnToEdit) {
      columnToEdit.classList.remove('hidden');
    }
  }

  if (event.target.closest('.create-card')) {
    let cardTemplate = `
   <a href="#" class="card" draggable="true">New Card</a> 
    `;
    const cardContainer = event.target.closest('.column').querySelector('.card-container');
    if (cardContainer) {
      cardContainer.insertAdjacentHTML('beforeend', cardTemplate);
    }
  }
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