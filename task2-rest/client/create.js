const nameStudent = document.getElementById("name");
const lastname = document.getElementById("lastname");
const middleName = document.getElementById("middleName");
const birthday = document.getElementById("birthday");
const group = document.getElementById("group");
const save = document.getElementById("save");

let url = "http://localhost:8080/v1/api/student";

save.addEventListener("click", function () {
    let student = {
        name: nameStudent.value,
        lastname: lastname.value,
        middleName: middleName.value,
        birthday: birthday.value,
        group: group.value
    }
    let xhr = new XMLHttpRequest();
    xhr.open("POST", url);   
    xhr.setRequestHeader("Accept", "application/json");
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onload = () => console.log(xhr.responseText);    
    xhr.send(JSON.stringify(student));
})