function onRegisterButtonClicked() {
        showContents(['register-content']);
}

function onRegisterResponse() {
        if (this.status === OK) {
                onRegSuccess();
        } else {
                alert(this.responseText);
        }
}

function onRegSuccess() {
        clearMessages();
        showContents(['login-content']);
}

function onRegistrationButtonClicked() {
        const loginFormEl = document.forms['register-form'];

        const emailInputEl = loginFormEl.querySelector('input[name="email"]');
        const passwordInputEl = loginFormEl.querySelector('input[name="password"]');
        const rePasswordInputEl = loginFormEl.querySelector('input[name="repassword"]');

        const email = emailInputEl.value;
        const password = passwordInputEl.value;
        const repassword = rePasswordInputEl.value;

        const params = new URLSearchParams();
        params.append('email', email);
        params.append('password', password);
        params.append('repassword', repassword);

        const xhr = new XMLHttpRequest();
        xhr.addEventListener('load', onRegisterResponse);
        xhr.addEventListener('error', onNetworkError);
        xhr.open('POST', 'register');
        xhr.send(params);

}
