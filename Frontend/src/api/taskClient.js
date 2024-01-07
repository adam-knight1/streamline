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

   async createTask(taskData) {
       try {
           const response = await this.client.post(`/task/create`, taskData); //not supposed to be using taskData
           console.log("Received response:", response.data);
           return response.data;
       } catch (error) {
           console.error("Failed to create a task:", error);
           throw error;
   }
   }
   async updateTask(taskId) {
          try {
              const response = await this.client.put(`/task/update/${taskId}`, {
               taskId: taskId
              });
              return response.data;
          } catch (error) {
              console.error("Failed to update task:", error);
              throw error;
      }
      }

   }//end

