import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import UserClient from "../api/UserClient";

class UserPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onCreate', 'renderUser'], this);
        this.dataStore = new DataStore();
        this.client = new UserClient;
    }

    async mount() {
        document.getElementById('create-user').addEventListener('submit', this.onCreate);
        this.client = new UserClient();

        this.dataStore.addChangeListener(this.renderUser)
    }

    async renderUser() {
    //slated for deletion
        }

    async onCreate(event) {
        event.preventDefault();
        let username = document.getElementById("username-field").value;
        let email = document.getElementById("email-field").value;
        let password = document.getElementById("password-field").value;

        try {
            let createdUser = await this.client.createUser(username, password, email);
            console.log("Created user response:", createdUser);
            // Success handling
            this.showMessage(`User ${createdUser.username} created successfully!`);
            document.getElementById("keep-it-safe").innerHTML = 'Account created successfully!';
            document.getElementById("created-user-id").innerHTML = `Your username is: ${createdUser.username}`;
        } catch (error) {
        //handling duplicate user name per userClient create method

            if (error.message === 'UsernameUnavailable') {
                this.showMessage("Username is already taken. Please try another.", true); // Assuming second parameter `true` indicates an error
            } else {
                // Generic error handling
                this.showMessage("Error creating user! Please try again...", true);
            }
        }
    }

    async onFind(event) {
        event.preventDefault();
        let userId = document.getElementById("find-user-id-field").value;
        try {
            const foundUser = await this.client.getUser(userId, this.errorHandler);
            if (foundUser) {
                this.displayUserDetails(foundUser);
            } else {
                this.showMessage("User not found");
            }
        } catch (error) {
            this.errorHandler("An error occurred while fetching the user");
        }
    }

    async onFindByUsername(event){
        event.preventDefault();
        let username = document.getElementById("find-user-by-username-field");
        try {
            const foundUser = await this.client.getUserByUsername(username, this.errorHandler);
            if (foundUser) {
                this.displayUserDetails(foundUser)
            } else {
                this.showMessage("User not found by username")
             }
           } catch (error) {
                this.errorHandler("An error occurred while fetching user by username")
        }
    }


displayUserDetails(user) {
    const userDetails = document.getElementById("user-details");
    userDetails.innerHTML = `
        <p><strong>Username:</strong> ${user.username}</p>
        <p><strong>Email:</strong> ${user.email}</p>
    `;
}
}

const main = async () => {
    const userPage = new UserPage();
    userPage.mount();
};

window.addEventListener('DOMContentLoaded', main);
