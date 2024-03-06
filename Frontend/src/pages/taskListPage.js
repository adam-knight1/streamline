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
           completedTasksContainer.innerHTML = '';


           taskArray.forEach(task => {
               const taskElement = document.createElement('div');
               taskElement.setAttribute('data-task-id', task.taskId); //attempting debug
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
      // Log taskId to ensure it's expected
      console.log("Completing task with ID:", taskId);

      const userId = localStorage.getItem('userId');
      if (!userId) {
          console.error("User ID is not available");
          return;
      }

      // Log userId for debugging purposes
      console.log("User ID from localStorage:", userId);

      try {
          // Calling API to mark task complete
          const completeTaskResponse = await this.taskClient.completeTask(userId, taskId);

          // Log the response from the completeTask API call
          console.log("completeTask response:", completeTaskResponse);

          // Update the UI on success
          const taskElement = document.querySelector(`[data-task-id="${taskId}"]`);

          // Log the taskElement to see if it was found
          console.log("Found task element in DOM:", taskElement);

          if (taskElement) {
              taskElement.classList.add('completed');
              taskElement.querySelector('.task-status').textContent = 'Completed';

              const completedTasksContainer = document.getElementById('completed-tasks-container');

              console.log("Completed tasks container:", completedTasksContainer);

              completedTasksContainer.appendChild(taskElement);
          } else {
              console.error("Task element not found in the DOM for taskId:", taskId);
          }
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
