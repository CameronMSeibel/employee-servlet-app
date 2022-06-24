const empsBtn = document.getElementById("employees-btn");
const empsContainer = document.getElementById("emps-container");

empsBtn.addEventListener("click", () => {
    const hostname = window.location.hostname;
    fetch(`http://${hostname}:8080/employee-servlet-app/employees`)
        .then(res => res.json())
        .then(data => {
            empsContainer.innerHTML += `
            <tr>
                <th scope="col">ID</th>
                <th scope="col">First</th>
                <th scope="col">Last</th>
                <th scope="col">Username</th>
            </tr>`;
            for(let emp of data){
                empsContainer.innerHTML += `
                    <tr>
                        <th scope="row">${emp.id}</th>
                        <td>${emp.firstName}</td>
                        <td>${emp.lastName}</td>
                        <td>${emp.username}</td>
                    </tr>
                `;
            }
            empsContainer.style.setProperty("display", "block")
            const employees = document.getElementsByClassName("employee");
            for(let employee of employees){
                employee.addEventListener("click", function() {
                    console.log(this);
                    this.style.setProperty("display", "none");
                });
            }
        })
        .catch(e => console.error(e));
});



