import BaseClass from "../util/baseClass";
import LoginClient from '../api/LoginClient';
import DataStore from "../util/DataStore";
import UserClient from '../api/UserClient'


class LoginPage extends BaseClass {
    constructor() {
        super();
        this.bindClassMethods(['onLogin'], this);
        this.dataStore = new DataStore();
        this.client = new LoginClient;
        this.userClient = new UserClient();
    }

    async mount() {
        document.getElementById('user-login').addEventListener('submit', this.onLogin);

    }

    async onLogin(event) {
        event.preventDefault();
        console.log('Client:', this.client);

        let username = document.getElementById("login-username-field").value;
        const user = await this.userClient.getUserByUsername(username);
        let password = document.getElementById("login-password-field").value;



        try {
            const userData = await this.client.loginUser(user.userId, password); //the backend logic uses UserId as username for now
            //this can be changed -adam
            if (userData) {
            localStorage.setItem('userId' , user.userId);
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
