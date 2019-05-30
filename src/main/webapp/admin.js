function onAdminButtonClicked() {
    clearMessages();
    if(getAuthorization().admin === true) {
        showContents(['admin-content', 'logout-content', 'admin-menu-content']);
    } else {
        showContents(['welcome-content', 'logout-content', 'user-menu-content']);
    }

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onAdminResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/admin');
    xhr.send();
}

function onAdminResponse() {
    if (this.status === OK) {
        if(getAuthorization().admin === true) {
            showContents(['admin-menu-content', 'admin-content']);
        } else {
            showContents(['user-menu-content', 'welcome-content']);
        }
        onAdminLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(adminContentDivEl, this);
    }
}

function onAdminLoad(users) {
    usersTableEl = document.getElementById('users');
    usersTableBodyEl = usersTableEl.querySelector('tbody');

    appendUsers(users);
}

function appendUsers(users) {
    removeAllChildren(usersTableBodyEl);

    for (let i = 0; i < users.length; i++) {
        const userData = users[i];
        appendUser(userData);
    }
}

function appendUser(userData) {

    const nameTdEl = document.createElement('td');
    nameTdEl.textContent = userData.name;
    const emailTdEl = document.createElement('td');
    emailTdEl.textContent = userData.email;
    const delChkBox = createCheckBoxTd('user-del', userData.id);
    const trEl = document.createElement('tr');

    trEl.appendChild(nameTdEl);
    trEl.appendChild(emailTdEl);
    trEl.appendChild(delChkBox);
    usersTableBodyEl.appendChild(trEl);
}

function onUserDeleteClicked() {
    const idStrChain = getCheckBoxCheckedValues('user-del');

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onAdminButtonClicked);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('DELETE', 'protected/admin?userIds=' + idStrChain);
    xhr.send();
}
