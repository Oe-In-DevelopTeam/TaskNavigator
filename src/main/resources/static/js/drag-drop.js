document.addEventListener("DOMContentLoaded", () => {
  const boardContainer = document.querySelector(".board-container");
  const columnContainer = boardContainer.querySelector(".column-container");
  const createBoardBtn = document.querySelector(".create-board");
  const boardId = boardContainer.getAttribute('data-board-id');

  // basic.js에서 가져오기
  const auth = getToken();
  if (auth === undefined || auth === '') {
    window.location.href = '/users/login-page';
    return;
  }

  function initializeSortableCardContainer(column) {
    new Sortable(column.querySelector(".card-container"), {
      group: "cards",
      animation: 150,
      ghostClass: "blue-background-class",
      onEnd: function (evt) {
        const target = evt.to;
        const from = evt.from;
        const item = evt.item;

        if (
            target &&
            target.classList &&
            !target.classList.contains("card-container")
        ) {
          from.appendChild(item);
        } else {
          // 서버에 저장
          saveColumnsToServer();
        }
      },
    });
  }

  function initializeSortableColumns(board) {
    new Sortable(board.querySelector(".column-container"), {
      group: "columns",
      animation: 150,
      ghostClass: "blue-background-class",
      onEnd: function (evt) {
        const target = evt.to;
        const from = evt.from;
        const item = evt.item;

        if (
            target &&
            target.classList &&
            !target.classList.contains("column-container")
        ) {
          from.appendChild(item);
        } else {
          const columnId = item.getAttribute('data-column-id');
          const newPosition = [...target.children].indexOf(item);

          updateColumnPosition(columnId, newPosition);
          console.log("컬럼 이동이 완료되었습니다.")
        }
      },
    });
  }

  document.querySelectorAll(".column").forEach((column) => {
    initializeSortableCardContainer(column);
  });

  initializeSortableColumns(boardContainer);

  function createNewColumn() {
    const newColumn = document.createElement("div");
    newColumn.classList.add("column");
    newColumn.setAttribute('data-status', 'New');
    const columnId = Date.now().toString(); // Unique ID
    newColumn.setAttribute('data-column-id', columnId);
    newColumn.innerHTML = `
      <div class="column-status-container">
        <span class="column-status">New</span>
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
      <div class="card-container list-group"></div>
    `;
    columnContainer.appendChild(newColumn);
    initializeSortableCardContainer(newColumn);
    initializeSortableColumns(boardContainer);
    newColumn.querySelector('.create-card').addEventListener('click', () => createCard(newColumn.querySelector('.card-container'), columnId));

    // 서버에 저장
    saveColumnToServer(newColumn, columnId);
  }

  createBoardBtn.addEventListener("click", createNewColumn);

  const observer = new MutationObserver((mutationsList) => {
    for (const mutation of mutationsList) {
      if (mutation.type === "childList" && mutation.addedNodes.length > 0) {
        mutation.addedNodes.forEach((node) => {
          if (node.classList && node.classList.contains("column")) {
            initializeSortableCardContainer(node);
          }
        });
      }
    }
  });

  observer.observe(columnContainer, { childList: true, subtree: true });
  observer.observe(boardContainer, { childList: true, subtree: true });

  function saveColumnsToServer() {
    const columns = [];
    document.querySelectorAll(".column").forEach(column => {
      const columnId = column.getAttribute('data-column-id');
      const status = column.querySelector('.column-status').textContent;
      const cards = [];
      column.querySelectorAll('.card').forEach(card => {
        cards.push({
          id: card.getAttribute('data-card-id'),
          title: card.textContent
        });
      });
      columns.push({ id: columnId, status: status, cards: cards });
    });

    $.ajax({
      type: 'PUT',
      url: `/boards/${boardId}/columns`,
      contentType: 'application/json',
      data: JSON.stringify(columns),
      success: function (data) {
        console.log('Columns saved successfully:', data);
      },
      error: function (error) {
        console.error('Error saving columns:', error);
      }
    });
  }

  function saveColumnToServer(newColumn, columnId) {
    const status = newColumn.querySelector('.column-status').textContent;
    const columnData = {
      id: columnId,
      status: status,
      cards: []
    };

    $.ajax({
      type: 'POST',
      url: `/boards/${boardId}/columns`,
      contentType: 'application/json',
      data: JSON.stringify(columnData),
      success: function (data) {
        console.log('Column created successfully:', data);
      },
      error: function (error) {
        console.error('Error creating column:', error);
      }
    });
  }

  function deleteColumnFromServer(columnId) {
    $.ajax({
      type: 'DELETE',
      url: `/boards/${boardId}/columns/${columnId}`,
      contentType: 'application/json',
      success: function (data) {
        console.log('Column deleted successfully:', data);
      },
      error: function (error) {
        console.error('Error deleting column:', error);
      }
    });
  }

  function deleteCardFromServer(columnId, cardId) {
    $.ajax({
      type: 'DELETE',
      url: `/boards/${boardId}/columns/${columnId}/cards/${cardId}`,
      contentType: 'application/json',
      success: function (data) {
        console.log('Card deleted successfully:', data);
      },
      error: function (error) {
        console.error('Error deleting card:', error);
      }
    });
  }

  function createCard(cardContainer, columnId) {
    const newCard = document.createElement('div');
    newCard.classList.add('list-group-item', 'card');
    newCard.setAttribute('draggable', 'true');
    const cardId = Date.now().toString(); // Unique ID
    newCard.setAttribute('data-card-id', cardId);
    newCard.textContent = 'New Card Title'; // Set card title
    cardContainer.appendChild(newCard);

    // 서버에 저장
    saveCardToServer(columnId, cardId, 'New Card Title');
  }

  function saveCardToServer(columnId, cardId, title) {
    const cardData = {
      id: cardId,
      title: title
    };

    $.ajax({
      type: 'POST',
      url: `/boards/${boardId}/columns/${columnId}/cards`,
      contentType: 'application/json',
      data: JSON.stringify(cardData),
      success: function (data) {
        console.log('Card created successfully:', data);
      },
      error: function (error) {
        console.error('Error creating card:', error);
      }
    });
  }

  function updateColumnPosition(columnId, newPosition) {
    const columnElement = document.querySelector(`[data-column-id="${columnId}"]`);
    const columnStatus = columnElement ? columnElement.getAttribute('data-status') : 'defaultStatus';

    // 서버에 저장
    saveColumnsToServer();
  }

  // 페이지 로드 시 서버에서 데이터 로드
  $.ajax({
    type: 'GET',
    url: `/boards/${boardId}/columns`,
    contentType: 'application/json',
    success: function (data) {
      if (Array.isArray(data)) {
        data.forEach(columnData => {
          const newColumn = document.createElement("div");
          newColumn.classList.add("column");
          newColumn.setAttribute('data-column-id', columnData.id);
          newColumn.setAttribute('data-status', columnData.status);
          newColumn.innerHTML = `
            <div class="column-status-container">
              <span class="column-status">${columnData.status}</span>
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
            <div class="card-container list-group"></div>
          `;
          columnData.cards.forEach(cardData => {
            const newCard = document.createElement('div');
            newCard.classList.add('list-group-item', 'card');
            newCard.setAttribute('draggable', 'true');
            newCard.setAttribute('data-card-id', cardData.id);
            newCard.textContent = cardData.title;
            newColumn.querySelector('.card-container').appendChild(newCard);
          });
          columnContainer.appendChild(newColumn);
          initializeSortableCardContainer(newColumn);
          initializeSortableColumns(boardContainer);
          newColumn.querySelector('.create-card').addEventListener('click', () => createCard(newColumn.querySelector('.card-container'), columnData.id));
        });
      } else {
        console.error('Unexpected data format:', data);
      }
    },
    error: function (error) {
      console.error('Error loading columns:', error);
    }
  });

  document.addEventListener('click', function(event) {
    if (event.target.closest('.remove-column')) {
      const column = event.target.closest('.column');
      const columnId = column.getAttribute('data-column-id');
      column.remove();

      // 컬럼 삭제 from 서버
      deleteColumnFromServer(columnId);
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
        saveColumnsToServer();
      });
    }

    if (event.target.closest('.remove-card')) {
      const card = event.target.closest('.card');
      const cardId = card.getAttribute('data-card-id');
      const columnId = card.closest('.column').getAttribute('data-column-id');
      card.remove();

      // 카드 삭제 from 서버
      deleteCardFromServer(columnId, cardId);
    }
  });

});