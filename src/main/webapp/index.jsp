<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <c:url value="/index.js" var="indexScriptUrl"/>
        <c:url value="/style.css" var="styleUrl"/>
        <c:url value="/login.js" var="loginScriptUrl"/>
        <c:url value="/back-to-profile.js" var="backToProfileScriptUrl"/>
        <c:url value="/profile.js" var="profileScriptUrl"/>
        <c:url value="/logout.js" var="logoutScriptUrl"/>
        <c:url value="/register.js" var="registerScriptUrl"/>
        <c:url value="/auth.js" var="authScriptUrl"/>
        <c:url value="/date_adder.js" var="dateAdderScriptUrl"/>
        <c:url value="/search.js" var="searchScriptUrl"/>
        <c:url value="/storages.js" var="storagesScriptUrl"/>
        <c:url value="/storage.js" var="storageScriptUrl"/>


        <script src="${storageScriptUrl}"></script>
        <script src="${storagesScriptUrl}"></script>
        <script src="${authScriptUrl}"></script>
        <script src="${registerScriptUrl}"></script>
        <script src="${indexScriptUrl}"></script>
        <script src="${loginScriptUrl}"></script>
        <script src="${backToProfileScriptUrl}"></script>
        <script src="${profileScriptUrl}"></script>
        <script src="${searchScriptUrl}"></script>
        <script src="${dateAdderScriptUrl}"></script>
        <script src="${logoutScriptUrl}"></script>
        <link rel="stylesheet" type="text/css" href="${styleUrl}">
        <title>Fridgster</title>
    </head>
<body>
<div id="user-menu-content" class="hidden content">
    <table>
        <tr id= user-menu-tr>
            <td><a href="javascript:void(0);" onclick="onStoragesClicked();">Storages</a></td>
            <td><a href="javascript:void(0);" onclick="onSearchClicked();">Search</a></td>
            <td><a href="javascript:void(0);" onclick="onShoppingListClicked();">Shopping List</a></td>
            <td><a href="javascript:void(0);" id="logout-button" onclick=onLogoutButtonClicked()>Logout </a></td>
    </table>
</div>
<div id="login-content" class="content">
    <h1>Login</h1>
    <form id="login-form" onsubmit="return false;">
        <input type="text" name="email" placeholder="e-mail">
        <input type="password" name="password" placeholder="name">
        <button id="login-button">Login</button>
        <button id="register-button" onclick=onRegisterButtonClicked()>Register</button>
    </form>
</div>
<div id="register-content" class="hidden content">
    <h1>Register</h1>
    <form id="register-form" onsubmit="return false;">
        <input type="text" name="email" placeholder="Email">
        <br>
        <input type="password" name="password" placeholder="Password">
        <br>
        <input type="password" name="repassword" placeholder="Re-enter password">
        <br>
        <button id="registration-button" onclick=onRegistrationButtonClicked()>Register</button>
    </form>
</div>
<div id="back-to-profile-content" class="hidden content">
    <button onclick="onBackToProfileClicked();">Back to profile</button>
</div>
<div id="welcome-content" class="hidden content">
    <h1>Close expiry</h1>
    <table id="notifications">
        <thead>
        <tr>
            <th>Food</th>
            <th>Expiry</th>
        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
</div>
<div id="storages-content" class="hidden content">
    <h1>Storages</h1>
    <table id="storages">
        <thead>
            <tr>
                <th>Name</th>
            </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
    <h2>Add new storage</h2>
    <form id="storage-form" onsubmit="return false;">
        <input type="text" name="name">
        <button onclick="onStorageAddClicked();">Add</button>
    </form>
</div>
<div id="storage-content" class="hidden content">
    <p>Name: <span id="storage-name"></span></p>
    <table id="storedfood">
        <thead>
        <tr>
            <th>Name</th>
            <th>Category</th>
            <th>Amount</th>
            <th>Measurement</th>
            <th>Expiry</th>
        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
    <h2>Add new food item</h2>
    <form id="food-form" onsubmit="return false;">
        <input type="text" name="name">
        <input type="text" name="category">
        <input type="number" step="any" name="amount"/>
        <input type="text" name="measurement">
        <input type="date" name="expiry">
        <button onclick="onFoodAddClicked();">Add</button>
    </form>
</div>
<div id="search-content" class="hidden content">
    <h1>Search options</h1>
    <form>
        <p>List by: </p>
        <select id="list" onChange="onOptionSelected()">
            <option value="" disabled selected>Select an option</option>
            <option value="name">Name</option>
            <option value="category">Category</option>
            <option value="storage">Place of storage</option>
            <option value="expiry">Date of expiry</option>
        </select>
    </form>
    <form>
        <p>Search by name: </p>
        <input type="text" id="search-name" oninput="onSearchByName()">
    <table id="results-content" class="hidden content">
        <thead>
        <tr>
            <th>Name</th>
            <th>Category</th>
            <th>Amount</th>
            <th>Measurement</th>
            <th>Place of storage</th>
            <th>Expiry</th>
        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
    </table>
    </form>
</div>
<div id="shopping-list-content" class="hidden content">
    <table id="shoppinglist">
        <thead>
        <tr>
            <th>Name</th>
            <th>Amount</th>
            <th>Measurement</th>
        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
    <h2>Add new item to shopping list</h2>
    <form id="shopping-form" onsubmit="return false;">
        <input type="text" name="name">
        <input type="number" step="any" name="amount"/>
        <input type="text" name="measurement">
        <button onclick="onShoppingAddClicked();">Add</button>
    </form>
</div>
</body>
</html>
