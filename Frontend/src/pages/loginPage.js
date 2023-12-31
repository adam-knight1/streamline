import BaseClass from "../util/baseClass";
import UserClient from "../api/UserClient";

class LoginPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onLogin'], this);
        this.client = new UserClient();
    }

    async mount() {
        document.getElementById('user-login').addEventListener('submit', this.onLogin);
    }

    async onLogin(event) {
        event.preventDefault();
        let username = document.getElementById("login-username-field").value;
        let password = document.getElementById("login-password-field").value;

        try {
           //will fill this out soon - adam
        } catch (error) {
        }
    }
}

const main = async () => {
    const loginPage = new LoginPage();
    loginPage.mount();
};

window.addEventListener('DOMContentLoaded', main);
