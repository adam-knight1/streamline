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
        document.getElementById('create-task').addEventListener('submit', this.onAddTask);

        this.client = new TaskListClient();
        this.dataStore.addChangeListener(this.renderTaskList)

                  const userId = localStorage.getItem('userId');
                  if (userId) {
                      await this.renderTaskList(userId);
                  }
    }


   /* async renderTaskList() {
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
    }*/

    /*async renderTaskList() {
        const userId = localStorage.getItem('userId');

        if (!userId) {
            console.error("User ID is not defined.");
            return;
        }

        try {
            const response = await this.taskClient.getTasksByUserId(userId);
            const taskArray = response.tasks;
            const incompleteTasksContainer = document.getElementById('incomplete-tasks-container');
            const completedTasksContainer = document.getElementById('completed-tasks-container');

            incompleteTasksContainer.innerHTML = '';
            completedTasksContainer.innerHTML = '';

            taskArray.forEach(task => {
                const taskElement = document.createElement('div');
                taskElement.setAttribute('data-task-id', task.taskId);
                taskElement.classList.add('task-item');
                taskElement.innerHTML = `
                    <h3>${task.title}</h3>
                    <p>${task.body}</p>
                    <p class="task-status">${task.status}</p>
                `;
                const completeButton = document.createElement('button');
                completeButton.textContent = 'Complete';
                completeButton.addEventListener('click', () => this.completeTask(task.taskId));
                taskElement.appendChild(completeButton);

                if (task.status === 'incomplete') {
                    incompleteTasksContainer.appendChild(taskElement);
                } else if (task.status === 'complete') {
                    completedTasksContainer.appendChild(taskElement);
                    taskElement.classList.add('completed'); // Add this line to style completed tasks
                }
            });
        } catch (error) {
            console.error("Error rendering task list:", error);
        }
    }*/


   /*completeTask = async (taskId) => {
       const userId = localStorage.getItem('userId'); // userID is in local storage
       if (!userId) {
           console.error("User ID is not available");
           return;
       }

       try {
           // Calling api to mark complete
           await this.taskClient.completeTask(userId, taskId);

           // update the UI on success
           const taskElement = document.querySelector(`[data-task-id="${taskId}"]`);
           if (taskElement) {
               taskElement.classList.add('completed');
               taskElement.querySelector('.task-status').textContent = 'Complete';
           } else {
               console.error("Task element not found in the DOM");
           }
       } catch (error) {
           console.error("Error completing task:", error);
       }
   }*/

   /*async renderTaskList() {
       const userId = localStorage.getItem('userId');
       if (!userId) {
           console.error("User ID is not defined.");
           return;
       }

       try {
           const response = await this.taskClient.getTasksByUserId(userId);
           const incompleteTasksContainer = document.getElementById('incomplete-tasks-container');
           const completedTasksContainer = document.getElementById('completed-tasks-container');

           // Clear the old tasks
           incompleteTasksContainer.innerHTML = '';
           completedTasksContainer.innerHTML = '';

           response.tasks.forEach(task => {
               const taskElement = document.createElement('div');
               taskElement.setAttribute('data-task-id', task.taskId);
               taskElement.classList.add('task-item');
               taskElement.innerHTML = `
                   <h3>${task.title}</h3>
                   <p>${task.body}</p>
                   <p class="task-status">${task.status}</p>
               `;

               if (task.status === 'Complete') {
                   completedTasksContainer.appendChild(taskElement);
               } else {
                   const completeButton = document.createElement('button');
                   completeButton.textContent = 'Complete';
                   completeButton.addEventListener('click', () => this.completeTask(task.taskId));
                   taskElement.appendChild(completeButton);
                   incompleteTasksContainer.appendChild(taskElement);
               }
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
           const incompleteTasksContainer = document.getElementById('incomplete-tasks-container');
           const completedTasksContainer = document.getElementById('completed-tasks-container');
           incompleteTasksContainer.innerHTML = ''; // Clear old tasks
           completedTasksContainer.innerHTML = ''; // Clear old tasks

           taskArray.forEach(task => {
               const taskElement = document.createElement('div');
               taskElement.classList.add('task-item');
               taskElement.innerHTML = `
                   <h3>${task.title}</h3>
                   <p>${task.body}</p>
                   <p class="task-status">${task.status}</p>
               `;
               const completeButton = document.createElement('button');
               completeButton.textContent = 'Complete';
               completeButton.addEventListener('click', () => this.completeTask(task.taskId));
               taskElement.appendChild(completeButton);

               if (task.status === 'complete' || task.status === 'Complete') {
                   taskElement.classList.add('completed');
                   completedTasksContainer.appendChild(taskElement);
               } else {
                   incompleteTasksContainer.appendChild(taskElement);
               }
           });
       } catch (error) {
           console.error("Error rendering task list:", error);
       }
   }



   async completeTask(taskId) {
           const userId = localStorage.getItem('userId'); userId in localStorage
           if (!userId) {
               console.error("User ID is not available");
               return;
           }

           try {
               // Calling API to mark the task as complete
               const completeTaskResponse = await this.taskClient.completeTask(userId, taskId);

               // Update the UI on success
               const taskElement = document.querySelector(`[data-task-id="${taskId}"]`);
               if (taskElement) {
                   taskElement.classList.add('completed');
                   taskElement.querySelector('.task-status').textContent = 'Completed';

                   const completedTasksContainer = document.getElementById('completed-tasks-container');
                   completedTasksContainer.appendChild(taskElement);
               } else {
                   console.error("Task element not found in the DOM");
               }


           } catch (error) {
               console.error("Error completing task:", error);
               // Handle the error
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
