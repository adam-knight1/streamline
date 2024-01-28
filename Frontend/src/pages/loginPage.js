import BaseClass from "../util/baseClass";
import LoginClient from '../api/LoginClient';
import DataStore from "../util/DataStore";


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
        //set the local storage for username here since it's currently userId
        let password = document.getElementById("login-password-field").value;

        localStorage.setItem('userId' , username);

        try {
            const userData = await this.client.loginUser(username, password);
            if (userData) {
                localStorage.setItem('userId', username);
                window.location.href = '/taskList.html';
            } else {
                this.showMessage("Invalid username or password.");
            }
        } catch (error) {
            this.showMessage("Failed to authenticate the user.  Please try again or contact support.");
        }
    }
}

const main = async () => {
    const loginPage = new LoginPage();
    loginPage.mount();
};

window.addEventListener('DOMContentLoaded', main);
