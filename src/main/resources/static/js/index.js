const columns = document.querySelectorAll(".column");
const cards = document.querySelectorAll(".card");

cards.forEach((card) => {
  card.addEventListener("dragend", (event) => {
    columns.forEach((column) => {
       column.addEventListener("drop", (columnEvent) => {
         console.log(columnEvent.target);
       })
    })
  })
})
columns.forEach((column) => {
  column.addEventListener("dragdrop", (columnEvent) => {
    console.log(columnEvent.target);
  })
})

cards.forEach((card) => {
  card.addEventListener("drop", (event) => {
    columns.forEach((column) => {
      column.addEventListener("dragover", (columnEvent) => {
        console.log(columnEvent.target);
      })
    })
  })
})

cards.forEach((card) => {
  card.addEventListener("drop", (event) => {
    console.log(event.target);
  })
})