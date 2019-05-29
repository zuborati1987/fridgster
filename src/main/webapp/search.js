function onSearchClicked() {
    clearMessages();
    showContents(['search-content', 'logout-content', 'user-menu-content']);
}

function onOptionSelected() {

    const list  = document.getElementById("list");
    const selected = list.options[list.selectedIndex].value;
    const params = new URLSearchParams();
    params.append('selected', selected);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onSearchResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/search');
    xhr.send(params);
}

function onSearchByName() {

    const tofind = document.getElementById("search-name").value;
    const selected = "search";
    const params = new URLSearchParams();
    params.append('selected', selected);
    params.append('tofind', tofind);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onSearchResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/search');
    xhr.send(params);
}

function onSearchResponse() {
    if (this.status === OK) {
        showContents(['user-menu-content','results-content', 'search-content', 'logout-content']);
        onSearchLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(resultsContentDivEl, this);
    }
}

function onSearchLoad(results) {
    resultsTableEl = document.getElementById('results-content');
    resultsTableBodyEl = resultsTableEl.querySelector('tbody');

    appendResults(results);
}

function appendResults(results) {
    removeAllChildren(resultsTableBodyEl);

    for (let i = 0; i < results.length; i++) {
        const resultData = results[i];
        appendResult(resultData);
    }
}

function appendResult(resultData) {

    const nameTdEl = document.createElement('td');
    nameTdEl.textContent =resultData.name;
    const categoryTdEl = document.createElement('td');
    categoryTdEl.textContent = resultData.category;
    const amountTdEl = document.createElement('td');
    amountTdEl.textContent = resultData.amount;
    const measurementTdEl = document.createElement('td');
    measurementTdEl.textContent = resultData.measurement;
    const storageTdEl = document.createElement('td');
    storageTdEl.textContent = resultData.storage;
    const expiryTdEl = document.createElement('td');
    let expiryDate = new Date(convertDate(resultData.expiry));
    expiryTdEl.textContent = getDateStr(expiryDate);
    const trEl = document.createElement('tr');

    trEl.appendChild(nameTdEl);
    trEl.appendChild(categoryTdEl);
    trEl.appendChild(amountTdEl);
    trEl.appendChild(measurementTdEl);
    trEl.appendChild(storageTdEl);
    trEl.appendChild(expiryTdEl);
    resultsTableBodyEl.appendChild(trEl);
}
