import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import TaskListClient from "../api/TaskListClient";

class TaskListPage extends BaseClass {
    constructor() {
        super();
        this.bindClassMethods(['onCreate', 'onUpdate', 'renderTaskList'], this);
        this.dataStore = new DataStore();
    }

    async mount() {
        document.getElementById('create-taskList').addEventListener('submit', this.onCreate);
        document.getElementById('update-taskList').addEventListener('submit', this.onUpdate);
        this.client = new TaskListClient();
        this.dataStore.addChangeListener(this.renderTaskList)
    }

    async renderTaskList() {}

    async onCreate(event) {
        event.preventDefault();

        let userId = document.getElementById("userId-field").value;
        let name = document.getElementById("name-field").value;

        let createdTaskList = await this.client.createTaskList(userId, taskListName, this.errorHandler);

        this.dataStore.set("taskList", createdTaskList);

        if (createdTaskList) {
            this.showMessage(`TaskList ${createdTaskList.taskListName} created successfully!`);
            document.getElementById("created-name").innerHTML = `Your task list name is: ${createdTaskList.taskListName}`;
        } else {
            this.errorHandler("Error creating task list! Try again...");
        }
    }

    async onUpdate(event) {
        event.preventDefault();

         let userId = document.getElementById("user-id-field").value;
         let newName = document.getElementById("updated-name-field").value;

         let updatedTaskList = await this.client.updateUser(userId, updatedName);

         if (updatedTaskList) {
           this.showMessage("Task list updated successfully");
         } else {
           this.errorHandler("Error updating task list! Try again...");
         }
   }
}

const main = async () => {
    const taskListPage = new TaskListPage();
    taskListPage.mount();
};

window.addEventListener('DOMContentLoaded', main);
