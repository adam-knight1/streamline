import BaseClass from "../util/baseClass";
import axios from 'axios'
import Toastify from "toastify-js";

export default class TaskClient extends BaseClass {
    constructor(baseURL) {
        super();
        this.bindClassMethods(['addTaskToTaskList','handleError'], this);
        this.client = axios.create({
            baseURL: baseURL
        });
    }

   async addTaskToTaskList(userId, title, body) {
   console.log("Sending task data:", { userId, title, body});
       try {
           const response = await this.client.post('/task/add', {
               userId,
               title,
               body,
           });
           console.log("Axios response:", response);
           return response.data;
       } catch (error) {
           console.error("Error in addTaskToTaskList:", error);
           throw error;
       }
   }

    handleError(method, error) {
           console.error(method + " failed - " + error);
           if (error.response && error.response.data.message) {
               console.error(error.response.data.message);
           }
           throw error;
       }

    async getTasksByUserId(userId) {
            try {
                 const response = await this.client.get(`/task/user/${userId}`);
                 return response.data;
                   } catch (error) {
                       console.error("Error getting tasks by userId:", error);
                       throw error;
           }
       }


   async completeTask(userId, taskId) {
       try {
           const response = await this.client.post(`/task/complete`, {
               userId: userId,
               taskId: taskId
           });
           return response.data;
       } catch (error) {
           console.error("Error completing task:", error);
           throw error;
       }
   }
}

   


