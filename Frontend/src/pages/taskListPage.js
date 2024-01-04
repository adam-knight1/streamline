import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import TaskListClient from "../api/taskListClient";

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

        //let userIdField = document.getElementById("userId-field");
        let taskListNameField = document.getElementById("taskList-name-field");

        //checking if elements are found in DOM
        if(taskListNameField) {
        //let userId = userIdField.value;
        let taskListName = taskListNameField.value;

       //Calling the createTaskList method with returned values
        let createdTaskList = await this.client.createTaskList(taskListName, this.errorHandler);

        if (createdTaskList) {
            this.showMessage(`TaskList ${createdTaskList.taskListName} created successfully!`);
            document.getElementById("created-name").innerHTML = `Your task list name is: ${createdTaskList.taskListName}`;
        } else {
            this.errorHandler("Error creating task list! Try again...");
        }
        }else { //added additional console error if elements are not found
            console.error("One or more elements not found");
        }
    }

    async onUpdate(event) {
        event.preventDefault();

         let userId = document.getElementById("update-task-list-user-id-field").value;
         let newName = document.getElementById("update-task-list-name-field").value;

         let updatedTaskList = await this.client.updateTaskList(userId, newName);

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
