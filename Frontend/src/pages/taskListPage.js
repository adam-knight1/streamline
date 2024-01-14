import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import TaskListClient from "../api/taskListClient";
import TaskClient from "../api/taskClient";

class TaskListPage extends BaseClass {
    constructor() {
        super();
        this.bindClassMethods(['onCreate', 'onUpdate', 'renderTaskList', 'onAddTask'], this);
        this.dataStore = new DataStore();
        this.taskClient = new TaskClient();
    }

    async mount() {
        document.getElementById('create-taskList').addEventListener('submit', this.onCreate);
        document.getElementById('update-taskList').addEventListener('submit', this.onUpdate);
        document.getElementById('find-task-list').addEventListener('submit', this.onFind);
        document.getElementById('create-task').addEventListener('submit', this.onAddTask);
       // document.getElementById('onAddTask').addEventListener('submit', this.onAddTask)

        this.client = new TaskListClient();
        this.dataStore.addChangeListener(this.renderTaskList)
    }

    async renderTaskList() {
    try {
            const tasks = await this.taskClient.getTasks();
            const tasksContainer = document.getElementById('tasks-container');
            tasksContainer.innerHTML = ''; // Clear existing tasks

            tasks.forEach(task => {
                const taskElement = document.createElement('div');
                taskElement.innerHTML = `<p>${task.title}: ${task.body}</p>`;
                tasksContainer.appendChild(taskElement);
            });
        } catch (error) {
            console.error("Error rendering task list:", error);
        }
    }


    async onCreate(event) {
        event.preventDefault();

        let userId = document.getElementById("userId-field").value;
        let taskListName = document.getElementById("taskList-name-field").value;

        this.showMessage(`userId ${userId}`);

       //Calling the createTaskList method with returned values
        let createdTaskList = await this.client.createTaskList(userId, taskListName, this.errorHandler);

        if (createdTaskList) {
            this.showMessage(`TaskList ${createdTaskList.taskListName} created successfully!`);
//            document.getElementById("created-name").innerHTML = `Your task list name is: ${createdTaskList.taskListName}`;
        } else {
            this.errorHandler("Error creating task list! Try again...");
        }
    }

    async onUpdate(event) {
        event.preventDefault();

         let userId = document.getElementById("update-task-list-user-id-field").value;
         let existingName = document.getElementById("update-task-list-existing-name-field").value;
         let newName = document.getElementById("update-task-list-new-name-field").value;

         this.showMessage(`userId ${userId}`);

         let updatedTaskList = await this.client.updateTaskList(userId, existingName, newName);

         if (updatedTaskList) {
           this.showMessage("Task list updated successfully");
           document.getElementById("updated-name").innerHTML = `Your task list name is now: ${createdTaskList.taskListName}`;
         } else {
           this.errorHandler("Error updating task list! Try again...");
         }
   }

     async onFind(event) {
           event.preventDefault();
           let userId = document.getElementById("find-task-list-by-user-id-field").value;
           try {
               const foundTaskList = await this.client.getTaskListByUserId(userId, this.errorHandler);
               if (foundTaskList) {
                   this.displayTaskListDetails(foundTaskList);
               } else {
                   this.showMessage("Task list not found");
               }
           } catch (error) {
               this.errorHandler("An error occurred while fetching the task list");
           }
       }

       async onAddTask(event) {
           event.preventDefault();

           let title = document.getElementById("task-title-field").value;
           let body = document.getElementById("task-body-field").value;
          // let status = document.getElementById("task-status-field").value;

           try {
               let addedTask = await this.taskClient.addTaskToTaskList(title, body, status);
               if (addedTask) {
                   this.showMessage(`Task '${title}' added successfully!`);
                   // Update UI here maybe
               } else {
                   this.errorHandler("Error adding Task! Try again...");
               }
           } catch (error) {
               this.errorHandler("Error in addTaskToTaskList:", error);
           }
       }


       displayTaskListDetails(taskList) {
           const taskListDetails = document.getElementById("task-list-details");
           let tasksHtml = '';

           if(taskList.tasks && taskList.tasks.length) {
               tasksHtml = '<ul>' + taskList.tasks.map(task => `<li>${task}</li>`).join('') + '</ul>';
           }

           taskListDetails.innerHTML = `
               <p><strong>User ID:</strong> ${taskList.userId}</p>
               <p><strong>Task List Name:</strong> ${taskList.taskListName}</p>
               <p><strong>Tasks:</strong> ${tasksHtml}</p>
           `;
       }

}

const main = async () => {
    const taskListPage = new TaskListPage();
    taskListPage.mount();
};

window.addEventListener('DOMContentLoaded', main);
