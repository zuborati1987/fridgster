let actualStorageId;

function onStorageClicked() {
    clearMessages();
    if (getAuthorization().admin === true) {
        showContents(['storage-content', 'logout-content', 'admin-menu-content', 'storages-content']);
    } else {
        showContents(['storage-content', 'logout-content', 'user-menu-content', 'storages-content']);
    }

    const storageId = this.dataset.storageId;
    actualStorageId = storageId;
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
        if (getAuthorization().admin === true) {
            showContents(['admin-menu-content', 'storage-content', 'storages-content']);
        } else {
            showContents(['user-menu-content', 'storage-content', 'storages-content']);
        }
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
    nameTdEl.textContent = contentData.name;
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

function onContentAddClicked() {
    const idStrChain = getCheckBoxCheckedValues('food-del');

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onStoragesClicked);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/shopping?foodIds=' + idStrChain);
    xhr.send();
}

function onFoodAddToStorageClicked() {
    clearMessages();
    if (getAuthorization().admin === true) {
        showContents(['storages-content', 'logout-content', 'admin-menu-content']);
    } else {
        showContents(['storages-content', 'logout-content', 'user-menu-content']);
    }

    const newFoodName = document.getElementById("newFoodName").value;
    const newFoodCat = document.getElementById("newFoodCat").value;
    const newFoodAmount = document.getElementById("newFoodAmount").value;
    const newFoodMeasurement = document.getElementById("newFoodMeasurement").value;
    const newFoodExpiry = document.getElementById("newFoodExpiry").value;

    const params = new URLSearchParams();
    params.append('newFoodName', newFoodName);
    params.append('newFoodCat', newFoodCat);
    params.append('newFoodAmount', newFoodAmount);
    params.append('newFoodMeasurement', newFoodMeasurement);
    params.append('newFoodExpiry', newFoodExpiry);
    params.append('storageId', actualStorageId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onStoragesClicked);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/storage');
    xhr.send(params);

}
