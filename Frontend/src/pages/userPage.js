import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import UserClient from "../api/UserClient";

class UserPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onCreate', 'onUpdate', 'onDelete', 'onFind', 'renderUser'], this);
        this.dataStore = new DataStore();
    }

    async mount() {
        document.getElementById('create-user').addEventListener('submit', this.onCreate);
        document.getElementById('update-user').addEventListener('submit', this.onUpdate);
        document.getElementById('delete-user').addEventListener('submit', this.onDelete);
        document.getElementById('find-user').addEventListener('submit', this.onFind);
        this.client = new UserClient();

        this.dataStore.addChangeListener(this.renderUser)
    }

    async renderUser() {

        }

    async onCreate(event) {
        event.preventDefault();

        this.dataStore.set("user", null);

        let username = document.getElementById("username-field").value;
        let email = document.getElementById("email-field").value;
        let password = document.getElementById("password-field").value;

        let createdUser = await this.client.createUser(username, password, email, this.errorHandler);

        this.dataStore.set("user", createdUser);

        if (createdUser) {
            this.showMessage(`User ${createdUser.username} created successfully!`);
            document.getElementById("created-user-id").innerHTML = `Your User ID is: ${createdUser.userId}`;
            document.getElementById("keep-it-safe").innerHTML = 'Please keep it somewhere safe!';

        } else {
            this.errorHandler("Error creating user! Try again...");
        }
    }


   async onUpdate(event) {
     event.preventDefault();

     let userId = document.getElementById("update-user-id-field").value;
     let username = document.getElementById("update-username-field").value;
     let email = document.getElementById("update-email-field").value;
     let password = document.getElementById("update-password-field").value;

     let updatedUserInfo = {
       username: username,
       email: email,
       password: password,
     };

     let result = await this.client.updateUser(userId, updatedUserInfo);

     if (result) {
       this.showMessage("User updated successfully");
     } else {
       this.errorHandler("Error updating user");
     }
   }


    async onDelete(event) {
        event.preventDefault();

        let userId = document.getElementById("delete-user-record-field").value;
        console.log("Searching for userId:", userId);


    try {
        await this.client.deleteUser(userId, this.errorHandler);
        this.showMessage(`User with ID ${userId} deleted successfully!`);

        this.dataStore.set("user", null);

    } catch (error) {
        this.showMessage("Failed to delete user.");
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
