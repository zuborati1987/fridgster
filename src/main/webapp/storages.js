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

    const trEl = document.createElement('tr');

    trEl.appendChild(nameTdEl);
    storagesTableBodyEl.appendChild(trEl);
}


