import BaseClass from "../util/baseClass";
import UserClient from "../api/UserClient";

class LoginPage extends BaseClass {

    constructor() {
        super();
      //  this.bindClassMethods(['onLogin'], this);
        this.dataStore = new DataStore();
        this.client = new UserClient();
    }

    async mount() {
      /*  document.getElementById('user-login').addEventListener('submit', this.onLogin);*/
    }

    /*async onLogin(event) { //commented out due to implementing spring security
        event.preventDefault();
        let username = document.getElementById("login-username-field").value;
        let password = document.getElementById("login-password-field").value;

        try {
            const loginResponse = await this.client.loginUser(username, password);

            if (loginResponse && loginResponse.userId) {
                window.location.href = `userHomePage.html?userId=${loginResponse.userId}`;
            } else {
                this.showMessage("Invalid username or password.");
            }
        } catch (error) {
            this.showMessage("An error occurred during login. Please try again.");
        }
    }*/

}

const main = async () => {
    const loginPage = new LoginPage();
    loginPage.mount();
};

window.addEventListener('DOMContentLoaded', main);
