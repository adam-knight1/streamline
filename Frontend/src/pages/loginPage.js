import BaseClass from "../util/baseClass";
import LoginClient from '../api/LoginClient';

class LoginPage extends BaseClass {
    constructor() {
        super();
        this.bindClassMethods(['onLogin'], this);
        this.dataStore = new DataStore();
        this.client = new LoginClient;
    }

    async mount() {
        document.getElementById('user-login').addEventListener('submit', this.onLogin);
       // this.loginClient = new LoginClient;

    }

    async onLogin(event) {
        event.preventDefault();
        console.log('Client:', this.client);

        let username = document.getElementById("login-username-field").value;
        let password = document.getElementById("login-password-field").value;

        try {
            const userData = await this.client.loginUser(username, password);
            if (userData) {
                window.location.href = '/taskList.html';
            } else {
                this.showMessage("Invalid username or password.");
            }
        } catch (error) {
            this.showMessage("An error occurred during login. Please try again.");
        }
    }
}

const main = async () => {
    const loginPage = new LoginPage();
    loginPage.mount();
};

window.addEventListener('DOMContentLoaded', main);
