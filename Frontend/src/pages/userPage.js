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
        console.log('Client:', this.client);

        this.dataStore.set("user", null);

        let username = document.getElementById("username-field").value;
        let email = document.getElementById("email-field").value;
        let password = document.getElementById("password-field").value;

        let createdUser = await this.client.createUser(username, password, email, this.errorHandler);
        console.log("Created user response:", createdUser);

        this.dataStore.set("user", createdUser);

        if (createdUser) {
            this.showMessage(`User ${createdUser.username} created successfully!`);
            //this.showMessage(`User ${createdUser.userId} created successfully!`); //userId for debugging purposes
            document.getElementById("keep-it-safe").innerHTML = 'Account created successfully!';
            document.getElementById("created-user-id").innerHTML = `Your username is: ${createdUser.username}`;

        } else {
            this.errorHandler("Error creating user! Try again...");
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
