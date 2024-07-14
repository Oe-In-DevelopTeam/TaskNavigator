document.addEventListener("DOMContentLoaded", () => {
  const columns = document.querySelectorAll(".column");
  const cards = document.querySelectorAll(".card");

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

  columns.forEach(initializeColumn);

  const columnContainer = document.querySelector('.column-container');
  columnContainer.addEventListener('click', (event) => {
    if (event.target.closest('.create-column')) {
      event.preventDefault();
      const newColumn = document.createElement('div');
      newColumn.classList.add('column');
      newColumn.innerHTML = `
        <div class="column-status-container">
          <span class="column-status">before</span>
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
          <a href="#" class="card" draggable="true">Card Title</a>
        </div>
      `;
      columnContainer.appendChild(newColumn);
      initializeColumn(newColumn);
    }
  });
});