const columnBtn = document.querySelector('.create-column');
const columnContainer= document.querySelector('.column-container');


let column = `
<div class="column" draggable="true">
      <div class="column-status-container">
        <input type="text" id="input-board-status" placeholder="Write Status">
        <div class="btn-container">
          <a href="#" class="create-card">
            <i class="fa-solid fa-plus"></i>
          </a>
          <a href="#" class="remove-column">
            <i class="fa-solid fa-xmark"></i>
          </a>
        </div>
      </div>
      <div class="card-container">
      </div>
    </div>
`;

let card = `
<a href="#" class="card" draggable="true">New Card</a>
`;

columnBtn.addEventListener('click', (event) => {
  columnContainer.insertAdjacentHTML('beforeend', column);
})

columnContainer.addEventListener('click', (event) => {
  if (event.target.closest('.create-card')) {
    const cardContainer = event.target.closest('.column').querySelector('.card-container');
    cardContainer.insertAdjacentHTML('beforeend', card);
  }

  if (event.target.closest('.remove-column')) {
    const columnToRemove = event.target.closest('.column');
    columnToRemove.remove();
  }
});