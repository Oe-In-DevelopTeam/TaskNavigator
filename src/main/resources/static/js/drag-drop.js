export function initializeSortableCardContainer(column, boardId) {
  new Sortable(column.querySelector(".card-container"), {
    group: "cards",
    animation: 150,
    ghostClass: "blue-background-class",
    onEnd: function (evt) {
      const target = evt.to;
      const from = evt.from;
      const item = evt.item;

      if (target && target.classList && !target.classList.contains("card-container")) {
        from.appendChild(item);
      } else {
        // 서버에 저장
        saveColumnsToServer(boardId);
      }
    },
  });
}

export function initializeSortableColumns(board, boardId) {
  new Sortable(board.querySelector(".column-container"), {
    group: "columns",
    animation: 150,
    ghostClass: "blue-background-class",
    onEnd: function (evt) {
      const target = evt.to;
      const from = evt.from;
      const item = evt.item;

      if (target && target.classList && !target.classList.contains("column-container")) {
        from.appendChild(item);
      } else {
        const columnId = item.getAttribute('data-column-id');
        const newPosition = [...target.children].indexOf(item);

        updateColumnPosition(columnId, newPosition, boardId);
        console.log("컬럼 이동이 완료되었습니다.")
      }
    },
  });
}

export function createNewColumn(columnContainer, boardContainer, boardId) {
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
  initializeSortableCardContainer(newColumn, boardId);
  initializeSortableColumns(boardContainer, boardId);
  newColumn.querySelector('.create-card').addEventListener('click', () => createCard(newColumn.querySelector('.card-container'), columnId, boardId));

  // 서버에 저장
  saveColumnToServer(newColumn, columnId, boardId);
}

export function saveColumnsToServer(boardId) {
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

export function saveColumnToServer(newColumn, columnId, boardId) {
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

export function deleteColumnFromServer(columnId, boardId) {
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

export function deleteCardFromServer(columnId, cardId, boardId) {
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

export function createCard(cardContainer, columnId, boardId) {
  const newCard = document.createElement('div');
  newCard.classList.add('list-group-item', 'card');
  newCard.setAttribute('draggable', 'true');
  const cardId = Date.now().toString(); // Unique ID
  newCard.setAttribute('data-card-id', cardId);
  newCard.textContent = 'New Card Title'; // Set card title
  cardContainer.appendChild(newCard);

  // 서버에 저장
  saveCardToServer(columnId, cardId, 'New Card Title', boardId);
}

export function saveCardToServer(columnId, cardId, title, boardId) {
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

export function updateColumnPosition(columnId, newPosition, boardId) {
  const columnElement = document.querySelector(`[data-column-id="${columnId}"]`);
  const columnStatus = columnElement ? columnElement.getAttribute('data-status') : 'defaultStatus';

  // 서버에 저장
  saveColumnsToServer(boardId);
}