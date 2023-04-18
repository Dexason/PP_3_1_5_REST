const editForm = document.getElementById('formEdit');
const edit_id = document.getElementById('edit_id');
const edit_username = document.getElementById('edit_username');
const edit_age = document.getElementById('edit_age');
const edit_roles = document.getElementById('rolesForEditing')
const edit_email = document.getElementById('edit_email');
const edit_password = document.getElementById('edit_password');

async function editModal(id) {
    const urlDataEdit = 'http://localhost:8080/admin/users/' + id;
    let modalEdit = await fetch(urlDataEdit);
    if (modalEdit.ok) {
        let userData =
            await modalEdit.json().then(async user => {
                edit_id.value = `${user.id}`;
                edit_username.value = `${user.username}`;
                edit_age.value = `${user.age}`;
                edit_email.value = `${user.email}`;
                edit_password.value = `${user.password}`;
                edit_roles.value = `${user.roles}`;
            })
    } else {
        alert(`Error, ${modalEdit.status}`)
    }
}

async function editUser() {
    let urlEdit = 'http://localhost:8080/admin/users/edit/' + edit_id.value;
    let roles = [];
    for (let i = 0; i < editForm.roles.options.length; i++) {
        if (editForm.roles.options[i].selected) {
            roles.push("ROLE_" + editForm.roles.options[i].value);
        }
    }
    let method = {
        method: 'PATCH',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            username: editForm.username.value,
            age: editForm.age.value,
            email: editForm.email.value,
            password: editForm.password.value,
            roles: roles
        })
    }

    await fetch(urlEdit, method).then(() => {
        $('#editCloseBtn').click();
        getAdmin();
    })
}