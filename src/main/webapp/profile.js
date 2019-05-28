
function onProfileLoad(user) {
    clearMessages();
    showContents(['welcome-content', 'logout-content', 'user-menu-content']);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onExpiryResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/expiry');
    xhr.send();
}

function onExpiryResponse() {
    if (this.status === OK) {
        showContents(['user-menu-content','welcome-content']);
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
    const aEl = document.createElement('a');
    aEl.textContent = expiryData.name;
    aEl.href = 'javascript:void(0);';
    aEl.dataset.expiryId = expiryData.id;
    aEl.addEventListener('click', onExpiryClicked);


    const nameTdEl = document.createElement('td');
    nameTdEl.appendChild(aEl);
    const expiryTdEl = document.createElement('td');
    let expiryDate = new Date(convertDate(expiryData.expiry));
    expiryTdEl.textContent = getDateStr(expiryDate);
    const trEl = document.createElement('tr');

    trEl.appendChild(nameTdEl);
    trEl.appendChild(expiryTdEl);
    expiryTableBodyEl.appendChild(trEl);
}

function onExpiryClicked() {
    console.log("to implement");
}
