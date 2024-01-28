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

   /* async renderTaskList() {
    try {
            const tasks = await this.taskClient.getTasksByUserId(userId);
            //const tasks = await this.taskClient.getTasks();
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
   }



   /* async onCreate(event) {
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
    }*/

     /*async onFind(event) {
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
       }*/


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

    /*displayTaskListDetails(tasks) {
        const tasksContainer = document.getElementById('tasks-container');
        tasksContainer.innerHTML = '';

        tasks.forEach(task => {
            const taskElement = document.createElement('div');
            taskElement.classList.add('task-item');
            taskElement.innerHTML = `
                <h3>${task.title}</h3>
                <p>${task.body}</p>
                <p>Status: ${task.status}</p>
            `;
            tasksContainer.appendChild(taskElement);
        });
    }*/


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
