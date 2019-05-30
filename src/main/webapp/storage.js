function onStorageClicked() {
    clearMessages();
    showContents(['storage-content', 'logout-content', 'user-menu-content', 'storages-content']);

    const storageId = this.dataset.storageId;
    const params = new URLSearchParams();
    params.append('storageId', storageId);
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onStorageResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/storage?' + params.toString());
    xhr.send();
}

function onStorageResponse() {
    if (this.status === OK) {
        showContents(['user-menu-content','storage-content', 'storages-content']);
        onStorageLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(storageContentDivEl, this);
    }
}

function onStorageLoad(contents) {
    storedTableEl = document.getElementById('storedfood');
    storedTableBodyEl = storedTableEl.querySelector('tbody');

    appendContents(contents);
}

function appendContents(contents) {
    removeAllChildren(storedTableBodyEl);

    for (let i = 0; i < contents.length; i++) {
        const contentData = contents[i];
        appendContent(contentData);
    }
}

function appendContent(contentData) {

    const nameTdEl = document.createElement('td');
    nameTdEl.textContent =contentData.name;
    const categoryTdEl = document.createElement('td');
    categoryTdEl.textContent = contentData.category;
    const amountTdEl = document.createElement('td');
    amountTdEl.textContent = contentData.amount;
    const measurementTdEl = document.createElement('td');
    measurementTdEl.textContent = contentData.measurement;
    const storedTdEl = document.createElement('td');
    let expiryDate = new Date(convertDate(contentData.expiry));
    storedTdEl.textContent = getDateStr(expiryDate);
    const delChkBox = createCheckBoxTd('food-del', contentData.id);
    const trEl = document.createElement('tr');

    trEl.appendChild(nameTdEl);
    trEl.appendChild(categoryTdEl);
    trEl.appendChild(amountTdEl);
    trEl.appendChild(measurementTdEl);
    trEl.appendChild(storedTdEl);
    trEl.appendChild(delChkBox);
    storedTableBodyEl.appendChild(trEl);
}

function onContentDeleteClicked() {
    const idStrChain = getCheckBoxCheckedValues('food-del');

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onStoragesClicked);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('DELETE', 'protected/search?foodIds=' + idStrChain);
    xhr.send();
}
