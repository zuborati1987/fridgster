function onStoragesClicked() {
    clearMessages();
    showContents(['storages-content', 'logout-content', 'user-menu-content']);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onStoragesResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/storages');
    xhr.send();
}

function onStoragesResponse() {
    if (this.status === OK) {
        showContents(['user-menu-content','storages-content']);
        onStoragesLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(storagesContentDivEl, this);
    }
}

function onStoragesLoad(storages) {
    storagesTableEl = document.getElementById('storages');
    storagesTableBodyEl = storagesTableEl.querySelector('tbody');

    appendStorages(storages);
}

function appendStorages(storages) {
    removeAllChildren(storagesTableBodyEl);

    for (let i = 0; i < storages.length; i++) {
        const storageData = storages[i];
        appendStorage(storageData);
    }
}

function appendStorage(storageData) {
    const aEl = document.createElement('a');
    aEl.textContent = storageData.name;
    aEl.href = 'javascript:void(0);';
    aEl.dataset.storageId = storageData.id;
    aEl.addEventListener('click', onStorageClicked);


    const nameTdEl = document.createElement('td');
    nameTdEl.appendChild(aEl);
    const delChkBox = createCheckBoxTd('storage-del', storageData.id);


    const trEl = document.createElement('tr');

    trEl.appendChild(nameTdEl);
    trEl.appendChild(delChkBox);
    storagesTableBodyEl.appendChild(trEl);
}

function onStorageAddClicked() {
    clearMessages();
    showContents(['storages-content', 'logout-content', 'user-menu-content']);

    const toAdd = document.getElementById("addStorage").value;
    const params = new URLSearchParams();
    params.append('toAdd', toAdd);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onStoragesClicked);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/storages');
    xhr.send(params);
}

function onStorageDeleteClicked() {
    const idStrChain = getCheckBoxCheckedValues('storage-del');

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onStoragesClicked);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('DELETE', 'protected/storages?storageIds=' + idStrChain);
    xhr.send();
}
