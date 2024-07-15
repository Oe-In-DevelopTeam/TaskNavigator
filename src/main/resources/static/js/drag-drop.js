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

function saveColumnsToServer(boardId) {
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

function updateColumnPosition(columnId, newPosition, boardId) {
  const columnElement = document.querySelector(`[data-column-id="${columnId}"]`);
  const columnStatus = columnElement ? columnElement.getAttribute('data-status') : 'defaultStatus';

  // 서버에 저장
  saveColumnsToServer(boardId);
}