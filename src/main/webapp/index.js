const OK = 200;
const BAD_REQUEST = 400;
const UNAUTHORIZED = 401;
const NOT_FOUND = 404;
const INTERNAL_SERVER_ERROR = 500;

let menuContentDivEl;
let loginContentDivEl;
let registerContentDivEl;
let profileButtonDivEl;
let welcomeContentDivEl;
let storagesContentDivEl;
let storageContentDivEl;
let searchContentDivEl;
let shoppingContentDivEl;
let logoutContentDivEl;
let resultsContentDivEl;

function newInfo(targetEl, message) {
    newMessage(targetEl, 'info', message);
}

function newError(targetEl, message) {
    newMessage(targetEl, 'error', message);
}

function newMessage(targetEl, cssClass, message) {
    clearMessages();

    const pEl = document.createElement('p');
    pEl.classList.add('message');
    pEl.classList.add(cssClass);
    pEl.textContent = message;

    targetEl.appendChild(pEl);
}

function clearMessages() {
    const messageEls = document.getElementsByClassName('message');
    for (let i = 0; i < messageEls.length; i++) {
        const messageEl = messageEls[i];
        messageEl.remove();
    }
}

function showContents(ids) {
    const contentEls = document.getElementsByClassName('content');
    for (let i = 0; i < contentEls.length; i++) {
        const contentEl = contentEls[i];
        if (ids.includes(contentEl.id)) {
            contentEl.classList.remove('hidden');
        } else {
            contentEl.classList.add('hidden');
        }
    }
}

function removeAllChildren(el) {
    while (el.firstChild) {
        el.removeChild(el.firstChild);
    }
}

function onNetworkError(response) {
    document.body.remove();
    const bodyEl = document.createElement('body');
    document.appendChild(bodyEl);
    newError(bodyEl, 'Network error, please try reloading the page');
}

function onOtherResponse(targetEl, xhr) {
    if (xhr.status === NOT_FOUND) {
        newError(targetEl, 'Not found');
        console.error(xhr);
    } else {
        const json = JSON.parse(xhr.responseText);
        if (xhr.status === INTERNAL_SERVER_ERROR) {
            newError(targetEl, `Server error: ${json.message}`);
        } else if (xhr.status === UNAUTHORIZED || xhr.status === BAD_REQUEST) {
            newError(targetEl, json.message);
        } else {
            newError(targetEl, `Unknown error: ${json.message}`);
        }
    }
}

function createCheckBoxTd(name, value) {
    const checkBoxEl = document.createElement('input')
    checkBoxEl.setAttribute('type', 'checkbox')
    checkBoxEl.setAttribute('name', name)
    checkBoxEl.setAttribute('value', value)

    const tdEl = document.createElement('td')
    tdEl.appendChild(checkBoxEl)
    return tdEl
}

function getCheckBoxCheckedValues(checkBoxName) {
    const checkBoxEls = document.getElementsByName(checkBoxName);
    let values = [];
    for (let i = 0; i < checkBoxEls.length; i++) {
        const checkboxEl = checkBoxEls.item(i);
        if (checkboxEl.checked) {
            values.push(checkboxEl.value);
        }
    }
    const valuesStrChain = values.join(',');
    return valuesStrChain;
}

function hasAuthorization() {
    return localStorage.getItem('user') !== null;
}

function setAuthorization(user) {
    return localStorage.setItem('user', JSON.stringify(user));
}

function getAuthorization() {
    return JSON.parse(localStorage.getItem('user'));
}

function setUnauthorized() {
    return localStorage.removeItem('user');
}

function onLoad() {
    menuContentDivEl = document.getElementById('user-menu-content');
    loginContentDivEl = document.getElementById('login-content');
    registerContentDivEl = document.getElementById('register-content');
    profileButtonDivEl = document.getElementById('back-to-profile-content');
    welcomeContentDivEl = document.getElementById('welcome-content');
    storagesContentDivEl = document.getElementById('storages-content');
    storageContentDivEl = document.getElementById('storage-content');
    searchContentDivEl = document.getElementById('search-content');
    shoppingContentDivEl = document.getElementById('shopping-list-content');
    logoutContentDivEl = document.getElementById('logout-content');
    resultsContentDivEl = document.getElementById('results-content')

    const loginButtonEl = document.getElementById('login-button');
    loginButtonEl.addEventListener('click', onLoginButtonClicked);

  /*  const registrationButtonEl = document.getElementById('registration-button');
    registrationButtonEl.addEventListener('click', onRegistrationButtonClicked);

    const logoutButtonEl = document.getElementById('logout-button');
    logoutButtonEl.addEventListener('click', onLogoutButtonClicked);
*/
    if (hasAuthorization()) {
        onProfileLoad(getAuthorization());
    }
}

document.addEventListener('DOMContentLoaded', onLoad);
