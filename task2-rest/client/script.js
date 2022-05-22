const table = document.getElementById("table");
let url = "http://localhost:8080/v1/api/student";

fetch(url)
    .then(response => response.json())
    .then(data => {
        showStudents(data)
    });

function showStudents(students) {
    students.forEach(student => {
        let row = `
      <tr>
      <td>${student.id}</td>
      <td>${student.lastname}</td>
      <td>${student.name}</td>
      <td>${student.middleName}</td>
      <td>${student.birthday}</td>
      <td>${student.group}</td>
      <td>
      <p class="delete" onClick=deleteStudent(${student.id})>Delete</p>       
      </td>
  </tr>      
      `
        table.insertAdjacentHTML('beforeend', row)
    });
}

function deleteStudent(id) {
    fetch(url+"/"+id, {
        method: 'DELETE'
    })
}