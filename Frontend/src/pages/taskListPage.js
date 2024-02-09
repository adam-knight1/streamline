import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import TaskListClient from "../api/taskListClient";
import TaskClient from "../api/taskClient";

class TaskListPage extends BaseClass {
    constructor() {
        super();
        this.bindClassMethods(['renderTaskList', 'onAddTask'], this);
        this.dataStore = new DataStore();
        this.taskClient = new TaskClient();
    }

    async mount() {
       /* document.getElementById('create-taskList').addEventListener('submit', this.onCreate);
        document.getElementById('update-taskList').addEventListener('submit', this.onUpdate);*/
       /* document.getElementById('find-task-list').addEventListener('submit', this.onFind);*/
        document.getElementById('create-task').addEventListener('submit', this.onAddTask);
       // document.getElementById('onAddTask').addEventListener('submit', this.onAddTask)

        this.client = new TaskListClient();
        this.dataStore.addChangeListener(this.renderTaskList)

                  const userId = localStorage.getItem('userId');
                  if (userId) {
                      await this.renderTaskList(userId);
                  }
    }


   /*async renderTaskList() {
       const userId = localStorage.getItem('userId');

       if (!userId) {
           console.error("User ID is not defined.");
           return;
       }

       try {
           const response = await this.taskClient.getTasksByUserId(userId);
           const taskArray = response.tasks;
           const tasksContainer = document.getElementById('tasks-container');
           tasksContainer.innerHTML = '';

          taskArray.forEach(task => {
              const taskElement = document.createElement('div');
              taskElement.classList.add('task-item');
              taskElement.innerHTML = `
                  <h3>${task.title}</h3>
                  <p>${task.body}</p>
                  <p>Status: ${task.status}</p>
                  <button class="complete-task-btn" onclick="completeTask('${task.taskId}')">Complete</button>
              `;
              tasksContainer.appendChild(taskElement);
          });

       } catch (error) {
           console.error("Error rendering task list:", error);
       }
   }*/

    async renderTaskList() {
        const userId = localStorage.getItem('userId');

        if (!userId) {
            console.error("User ID is not defined.");
            return;
        }

        try {
            const response = await this.taskClient.getTasksByUserId(userId);
            const taskArray = response.tasks;
            const tasksContainer = document.getElementById('tasks-container');
            tasksContainer.innerHTML = ''; // Clear the old tasks

            taskArray.forEach(task => {
                const taskElement = document.createElement('div');
                taskElement.setAttribute('data-task-id', task.taskId); // Set a data attribute for the task ID
                taskElement.classList.add('task-item');
                taskElement.innerHTML = `
                    <h3>${task.title}</h3>
                    <p>${task.body}</p>
                    <p class="task-status">${task.status}</p>
                `;
                // Add the complete button
                const completeButton = document.createElement('button');
                completeButton.textContent = 'Complete';
                completeButton.addEventListener('click', () => this.completeTask(task.taskId));
                taskElement.appendChild(completeButton);

                tasksContainer.appendChild(taskElement);
            });
        } catch (error) {
            console.error("Error rendering task list:", error);
        }
    }

   completeTask = async (taskId) => {
       try {
           // Here I'll send a request to the backend to mark the task as complete

           await this.taskClient.completeTask(taskId);
           // If successful, find the task item in the DOM and update its appearance
           const taskElement = document.querySelector(`[data-task-id="${taskId}"]`);
           taskElement.classList.add('completed');
           taskElement.querySelector('.task-status').textContent = 'Complete';
       } catch (error) {
           console.error("Error completing task:", error);
       }
   }

async onAddTask(event) {
    const userId = localStorage.getItem('userId');
    event.preventDefault();

    let title = document.getElementById("task-title-field").value;
    let body = document.getElementById("task-body-field").value;


    try {
        let addedTask = await this.taskClient.addTaskToTaskList(userId, title, body);
        if (addedTask) {
            this.showMessage(`Task '${title}' added successfully!`);
            await this.renderTaskList(userId);
        } else {
            this.errorHandler("Error adding Task! Try again...");
        }
    } catch (error) {
        this.errorHandler("Error in addTaskToTaskList:", error);
    }
}


       extractUserIdFromUrl() {
           const urlParts = window.location.pathname.split('/');
           return urlParts[urlParts.length - 1];

}
}

const main = async () => {
    const taskListPage = new TaskListPage();
    taskListPage.mount();
};

window.addEventListener('DOMContentLoaded', main);
