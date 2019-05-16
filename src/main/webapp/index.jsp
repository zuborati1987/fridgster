<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <c:url value="/style.css" var="styleUrl"/>
        <c:url value="/login.js" var="loginScriptUrl"/>
        <c:url value="/back-to-profile.js" var="backToProfileScriptUrl"/>
        <c:url value="/logout.js" var="logoutScriptUrl"/>
        <link rel="stylesheet" type="text/css" href="${styleUrl}">
        <script src="${loginScriptUrl}"></script>
        <script src="${backToProfileScriptUrl}"></script>
        <script src="${logoutScriptUrl}"></script>
        <title>Fridgster</title>
    </head>
<body>
<div id="login-content" class="content">
    <h1>Login</h1>
    <form id="login-form" onsubmit="return false;">
        <input type="text" name="email">
        <input type="password" name="password">
        <button id="login-button">Login</button>
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
        <input type="text" name="name" placeholder="Name">
        <br>
        <button id="registration-button">Register</button>
    </form>
</div>
<div id="back-to-profile-content" class="hidden content">
    <button onclick="onBackToProfileClicked();">Back to profile</button>
</div>
<div id="logout-content" class="hidden content">
    <button id="logout-button">Logout</button>
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
    <ul>
        <li><a href="javascript:void(0);" onclick="onStoragesClicked();">Storages</a></li>
        <li><a href="javascript:void(0);" onclick="onSearchClicked();">Search</a></li>
        <li><a href="javascript:void(0);" onclick="onShoppingListClicked();">Shopping list</a></li>
    </ul>
</div>
<div id="Storages-content" class="hidden content">
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
        <select name="list">
            <option>Name</option>
            <option>Category</option>
            <option>Place of storage</option>
            <option>Date of expiry</option>
        </select>
    </form>
    <form>
        <p>Search by name: </p>
        <input type="text" name="name">
    <table id="results" class="hidden content">
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
<div id="shopping-list" class="hidden content">
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
