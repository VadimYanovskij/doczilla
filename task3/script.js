const mainTasksDate = document.querySelector('.main_tasks-date-sort');
const tasksList = document.querySelector('.tasks_list');
const tasksStatusCheckbox = document.getElementById('check');
const currentDateBtn = document.getElementById('today');
const calendar = document.getElementById('calendar');
let currentDate = (new Date()).toISOString().slice(0, 10);
let currentDateShow = Date.parse(new Date());
let data = [];
let isDidTask = false;
let url = 'https://todo.doczilla.pro/api/todos?limit=10';
let inputSearch = document.querySelector(".header_search");
let searchBtn = document.querySelector(".search-icon");
let searhFromDate = "";
let searhToDate = "";

let dateBlock = `
<div class="main_tasks-date">${currentDate}</div>
`

mainTasksDate.insertAdjacentHTML("afterbegin", dateBlock)

getData(url)

async function getData(url) {
    await fetch(url)
        .then((response) => {
            return response.json()
        })
        .then((dataTasks) => {
            data = dataTasks
        });
    showTasks(data)
    return data
}


function showTasks(data) {
    data.forEach((element, index) => {
        let taskCard;
        if (element.status == true) {
            taskCard = `
        <div class="task_card task_card-checked" id=${element.id}>
          <div class="taskCard-content">
          <h4 class="task_card-title">${element.name}</h4>
          <p>${element.shortDesc}</p>
        </div>
        <div class="taskCard-status">
          <input class="taskCard-status-check" type="checkbox" checked id="id${index}"><label for="id${index}"></label>
          <p class="taskCard-status-time">${element.date.slice(0, 10)}</p>
        </div>           
        
        </div>
        `
        } else {
            taskCard = `
        <div class="task_card" id=${element.id}>
          <div class="taskCard-content">
          <h4 class="task_card-title">${element.name}</h4>
          <p>${element.shortDesc}</p>
        </div>
        <div class="taskCard-status">
          <input class="taskCard-status-check" type="checkbox" id="id${index}"><label for="id${index}"></label>
          <p class="taskCard-status-time">${element.date.slice(0, 10)}</p>
        </div>           
        
        </div>
        `
        }
        tasksList.insertAdjacentHTML("afterbegin", taskCard);
    });
}


tasksStatusCheckbox.addEventListener("click", showNotDone);

function showNotDone() {
    let tasks = document.querySelectorAll('.task_card-checked');
    if (tasksStatusCheckbox.checked) {
        isDidTask = true;
        tasks.forEach(el => {
            el.classList.add('done')
        })
    }
    else {
        isDidTask = false;
        tasks.forEach(el => {
            el.classList.remove('done')
        })
    }
}

function findTask() {
    let inputSearchValue = inputSearch.value;
    tasksList.innerHTML = "";
    serachUrl = "https://todo.doczilla.pro/api/todos/find?q=" + inputSearchValue + "&limit=10";
    getData(serachUrl);
}

inputSearch.addEventListener('keydown', function (e) {
    if (e.keyCode === 13) {
        findTask()
    }
})

searchBtn.addEventListener("click", findTask)

inputSearch.addEventListener("click", function () {
    inputSearch.value = ""
})

function dateTask(searhFromDate, searhToDate) {
    tasksList.innerHTML = "";
    serachDateUrl = " https://todo.doczilla.pro/api/todos/date?from=" + searhFromDate + "&to=" + searhToDate + "&limit=10";
    getData(serachDateUrl);
}


calendar.addEventListener("change", (event) => {
    searhFromDate = Date.parse(event.target.value);
    searhToDate = +Date.parse(event.target.value) + 86399999;
    dateTask(searhFromDate, searhToDate)
})



