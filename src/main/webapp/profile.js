function onProfileLoad() {
    clearMessages();
    if (getAuthorization().admin === true) {
        showContents(['welcome-content', 'logout-content', 'admin-menu-content']);
    } else {
        showContents(['welcome-content', 'logout-content', 'user-menu-content']);
    }

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onExpiryResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/expiry');
    xhr.send();
}

function onExpiryResponse() {
    if (this.status === OK) {
        if (getAuthorization().admin === true) {
            showContents(['admin-menu-content', 'welcome-content']);
        } else {
            showContents(['user-menu-content', 'welcome-content']);
        }
        onExpiryLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(welcomeContentDivEl, this);
    }
}

function onExpiryLoad(expiries) {
    expiryTableEl = document.getElementById('notifications');
    expiryTableBodyEl = expiryTableEl.querySelector('tbody');

    appendExpiry(expiries);
}

function appendExpiry(expiries) {
    removeAllChildren(expiryTableBodyEl);

    for (let i = 0; i < expiries.length; i++) {
        const expiryData = expiries[i];
        appendExpiryEl(expiryData);
    }
}

function appendExpiryEl(expiryData) {

    const nameTdEl = document.createElement('td');
    nameTdEl.textContent = expiryData.name;
    const expiryTdEl = document.createElement('td');
    let expiryDate = new Date(convertDate(expiryData.expiry));
    expiryTdEl.textContent = getDateStr(expiryDate);
    const trEl = document.createElement('tr');

    trEl.appendChild(nameTdEl);
    trEl.appendChild(expiryTdEl);
    expiryTableBodyEl.appendChild(trEl);
}
