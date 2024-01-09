import BaseClass from "../util/baseClass";
import axios from 'axios'
import Toastify from "toastify-js";

export default class TaskClient extends BaseClass {
    constructor(baseURL) {
        super();
        this.client = axios.create({
            baseURL: baseURL
        });
    }

   async createTask(userId, taskName, taskDescription, completed) {
       try {
           const response = await this.client.post(`/task/create`, {
           userId: userId,
           taskName: taskName,
           taskDescription: taskDescription,
           completed: completed
           });
           console.log("Received response:", response.data);
           return response.data;
       } catch (error) {
           console.error("Failed to create a task:", error);
           throw error;
   }
   }
   async updateTask(taskId, taskName, taskDescription) {
          try {
              const response = await this.client.put(`/task/update/${taskId}`, {
               taskName: taskName,
               taskDescription: taskDescription
              });
              return response.data;
          } catch (error) {
              console.error("Failed to update task:", error);
              throw error;
      }
      }

   }


