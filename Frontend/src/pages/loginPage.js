import BaseClass from "../util/baseClass";
import UserClient from "../api/UserClient";
import axios from 'axios';

class LoginPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onLogin'], this);
        this.dataStore = new DataStore();
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
            const response = await axios.post('/login', {
                username: username,
                password: password
            });

            if (response.status === 200) {
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
