import BaseClass from "../util/baseClass";
import axios from 'axios'

export default class TaskClient {
    constructor(baseURL) {
        this.client = axios.create({
            baseURL: baseURL
        });
    }

   async createTask(taskData) {
       try {
           const response = await this.client.post(`/task/create`, taskData);
           console.log("Received response:", response.data);
           return response.data;
       } catch (error) {
           console.error("Failed to create a task:", error);
           throw error;
   }
   }
   async updateTask(taskId, updatedInfo) {
          try {
              const response = await this.client.put(`/task/update/{id}`, updatedInfo);
              return response.data;
          } catch (error) {
              console.error("Failed to update task:", error);
              throw error;
      }
      }

   }//end

