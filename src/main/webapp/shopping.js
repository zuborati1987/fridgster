function onShoppingListClicked() {
    clearMessages();
    if(getAuthorization().admin === true) {
        showContents(['shopping-list-content', 'logout-content', 'admin-menu-content']);
    } else {
        showContents(['shopping-list-content', 'logout-content', 'user-menu-content']);
    }

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onShoppingResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/shopping');
    xhr.send();
}

function onShoppingResponse() {
    if (this.status === OK) {
        if(getAuthorization().admin === true) {
            showContents(['admin-menu-content','shopping-list-content']);
        } else {
            showContents(['user-menu-content','shopping-list-content']);
        }
        onShoppingLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(shoppingContentDivEl, this);
    }
}

function onShoppingLoad(requiredList) {
    requiredTableEl = document.getElementById('required');
    requiredTableBodyEl = requiredTableEl.querySelector('tbody');

    appendRequiredList(requiredList);
}

function appendRequiredList(requiredList) {
    removeAllChildren(requiredTableBodyEl);

    for (let i = 0; i < requiredList.length; i++) {
        const required = requiredList[i];
        appendRequired(required);
    }
}

function appendRequired(required) {

    const nameTdEl = document.createElement('td');
    nameTdEl.textContent = required.name;
    const amountTdEl = document.createElement('td');
    amountTdEl.textContent = required.amount;
    const measurementTdEl = document.createElement('td');
    measurementTdEl.textContent = required.measurement;
    const delChkBox = createCheckBoxTd('shopping-del', required.id);
    const trEl = document.createElement('tr');

    trEl.appendChild(nameTdEl);
    trEl.appendChild(amountTdEl);
    trEl.appendChild(measurementTdEl);
    trEl.appendChild(delChkBox);
    requiredTableBodyEl.appendChild(trEl);
}

function onShoppingDeleteClicked() {
    const idStrChain = getCheckBoxCheckedValues('shopping-del');

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onShoppingListClicked);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('DELETE', 'protected/shopping?shoppingIds=' + idStrChain);
    xhr.send();
}
