function onRegularClicked() {
    clearMessages();
    if (getAuthorization().admin === true) {
        showContents(['shopping-list-content', 'logout-content', 'admin-menu-content']);
    } else {
        showContents(['shopping-list-content', 'logout-content', 'user-menu-content']);
    }

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onShoppingResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/shopping?actual=regular');
    xhr.send();
}

function onShoppingResponse() {
    if (this.status === OK) {
        if (getAuthorization().admin === true) {
            showContents(['admin-menu-content', 'shopping-list-content']);
        } else {
            showContents(['user-menu-content', 'shopping-list-content']);
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
    const numberChanger = document.createElement("input");
    numberChanger.type = "number";
    numberChanger.value = required.amount;
    numberChanger.setAttribute("id", required.id);
    numberChanger.addEventListener('change', onNumberChanged);
    amountTdEl.appendChild(numberChanger);
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
    xhr.addEventListener('load', onRegularClicked);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('DELETE', 'protected/shopping?shoppingIds=' + idStrChain);
    xhr.send();
}

function onNumberChanged() {
    const id = this.getAttribute("id");
    const number = this.value;

    const params = new URLSearchParams();
    params.append("id", id);
    params.append("number", number);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onRegularClicked);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/number');
    xhr.send(params);
}

function onActualClicked() {
    clearMessages();
    if (getAuthorization().admin === true) {
        showContents(['actual-list-content', 'logout-content', 'admin-menu-content']);
    } else {
        showContents(['actual-list-content', 'logout-content', 'user-menu-content']);
    }

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onActualResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/shopping?actual=actual');
    xhr.send();
}

function onActualResponse() {
    if (this.status === OK) {
        if (getAuthorization().admin === true) {
            showContents(['admin-menu-content', 'actual-list-content']);
        } else {
            showContents(['user-menu-content', 'actual-list-content']);
        }
        onActualLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(actualContentDivEl, this);
    }
}

function onActualLoad(actualList) {
    actualTableEl = document.getElementById('actual');
    actualTableBodyEl = actualTableEl.querySelector('tbody');

    appendActualList(actualList);
}

function appendActualList(actualList) {
    removeAllChildren(actualTableBodyEl);

    for (let i = 0; i < actualList.length; i++) {
        const actual = actualList[i];
        appendActual(actual);
    }
}

function appendActual(actual) {

    const nameTdEl = document.createElement('td');
    nameTdEl.textContent = actual.name;
    const amountTdEl = document.createElement('td');
    amountTdEl.textContent = actual.amount;
    const measurementTdEl = document.createElement('td');
    measurementTdEl.textContent = actual.measurement;
    const trEl = document.createElement('tr');

    trEl.appendChild(nameTdEl);
    trEl.appendChild(amountTdEl);
    trEl.appendChild(measurementTdEl);
    actualTableBodyEl.appendChild(trEl);
}
