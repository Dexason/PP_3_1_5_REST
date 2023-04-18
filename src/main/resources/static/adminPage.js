const adminUrl = 'http://localhost:8080/admin/users'

async function getAdmin() {
    let response = await fetch(adminUrl);

    if (response.ok) {
        let listUsers = await response.json();
        getAllUsers(listUsers);
    } else {
        alert("Error: " + response.status);
    }
}

function getAllUsers(listUsers) {
    const tbody = document.getElementById('my-table');

    let tr = '';
    for (let user of listUsers) {
        let roles = [];
        for (let role of user.roles) {
            roles.push(" " + role.name.toString().substring(5))
        }
        tr += `<tr>
        <td>${user.id}</td>
        <td>${user.username}</td>
        <td>${user.age}</td>
        <td>${user.email}</td>
        <td>${roles}</td>
        <td>
            <button class="btn btn-info" style="color:white" data-bs-toggle="modal" 
            data-bs-target="#modalEdit"
            onclick="editModal(${user.id})">Edit</button>
        </td>
        <td>
            <button class="btn btn-danger" style="color:white" data-bs-toggle="modal" 
            data-bs-target="#modalDelete"
            onclick="deleteModal(${user.id})">Delete</button>
        </td>
    </tr>`
    }
    tbody.innerHTML = tr;
}

getAdmin();


function userAuthInfo() {
    const tbody = document.getElementById('tableUserBody');
    const panel = document.getElementById("admin-header");
    fetch('http://localhost:8080/admin/viewUser')
        .then((res) => res.json())
        .then((user) => {
            let temp = '';
            let roles = [];
            for (let role of user.roles) {
                roles.push(" " + role.name.toString().substring(5))
            }
            temp += `<tr>
        <td>${user.id}</td>
        <td>${user.username}</td>
        <td>${user.age}</td>
        <td>${user.email}</td>
        <td>${roles}</td>
        </tr>`;
            tbody.innerHTML = temp;
            panel.innerHTML = `<h5>${user.email} with roles: ${roles}</h5>`
        });
}

userAuthInfo()