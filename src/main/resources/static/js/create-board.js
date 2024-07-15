document.addEventListener("DOMContentLoaded", () => {
  const columnBtn = document.querySelector('.create-column');
  const columnContainer = document.querySelector('.column-container');

  if (!columnBtn || !columnContainer) {
    console.error("Missing essential elements for creating columns or cards.");
    return;
  }

  const columnTemplate = `
    <div class="column" draggable="true">
      <div class="column-status-container">
        <span class="column-status">before</span>
        <input type="text" class="edit-column-input hidden" style="display:none;">
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

  const cardTemplate = `<a href="#" class="card" draggable="true">New Card</a>`;

  columnBtn.addEventListener('click', (event) => {
    event.preventDefault();
    columnContainer.insertAdjacentHTML('beforeend', columnTemplate);
    const newColumn = columnContainer.lastElementChild;
    initializeColumn(newColumn);
  });

  // 초기화 함수
  function initializeColumn(column) {
    const editBtn = column.querySelector(".edit-column");
    const columnStatus = column.querySelector(".column-status");
    const editInput = column.querySelector(".edit-column-input");

    editBtn.addEventListener("click", () => {
      editInput.value = columnStatus.textContent;
      columnStatus.style.display = "none";
      editInput.style.display = "inline";
      editInput.focus();
    });

    editInput.addEventListener("blur", () => {
      columnStatus.textContent = editInput.value;
      columnStatus.style.display = "inline";
      editInput.style.display = "none";
    });

    editInput.addEventListener("keypress", (e) => {
      if (e.key === "Enter") {
        editInput.blur();
      }
    });
  }

  // 생성, 삭제 함수 분리
  // drag-drop 이랑 create 중복 삭제, 더 기능 잘되는 곳으로
  columnContainer.addEventListener('click', (event) => {
    if (event.target.closest('.create-card')) {
      const cardContainer = event.target.closest('.column').querySelector('.card-container');
      if (cardContainer) {
        cardContainer.insertAdjacentHTML('beforeend', cardTemplate);
      }
    }

    if (event.target.closest('.remove-column')) {
      const columnToRemove = event.target.closest('.column');
      if (columnToRemove) {
        columnToRemove.remove();
      }
    }
  });
  // 기존 컬럼 초기화
  document.querySelectorAll('.column').forEach(initializeColumn);
});