document.addEventListener("DOMContentLoaded", () => {
  const columnContainer = document.querySelector(".column-container");
  const createBoardBtn = document.querySelector(".create-board");
  const bordContainer = document.querySelector(".board-container");

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
          // api 호출(카드용)
        }
      },
    });
  }

  function initializeSortableColumns(board) {
    console.log("string!!");
    new Sortable(board.querySelector(".column-container"), {
      group: "columns",
      animation: 150,
      // handle: ".board-top",
      // draggable: ".column",
      ghostClass: "blue-background-class",
      onEnd: function (evt) {
        const target = evt.to;
        const from = evt.from;
        const item = evt.item;
        console.log("Column moved from:", from, "to:", target, "item:", item);

        if (
            target &&
            target.classList &&
            !target.classList.contains("column-container")
        ) {
          console.log("String!");
          from.appendChild(item);
          // api 호출(컬럼용)
        }
      },
    });
  }

  document.querySelectorAll(".column").forEach((column) => {
    initializeSortableCardContainer(column);
  });

  initializeSortableColumns(bordContainer);

  function createNewColumn() {
    const newColumn = document.createElement("div");
    newColumn.classList.add("column");
    newColumn.innerHTML = `
      <div class="board-top">
        <div class="board-intro-container">
          <h3>New Board Title</h3>
          <p>This is a new board introduction.</p>
        </div>
        <a href="#" class="create-column">
          <i class="fa-solid fa-plus"></i>
        </a>
      </div>
      <div class="card-container list-group">
        <div class="list-group-item card" draggable="true">New Card Title</div>
      </div>
    `;
    columnContainer.appendChild(newColumn);
    initializeSortableCardContainer(newColumn);
    initializeSortableColumns(bordContainer);
    // api 호출(컬럼 생성 api)
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
  observer.observe(bordContainer, { childList: true, subtree: true });
});