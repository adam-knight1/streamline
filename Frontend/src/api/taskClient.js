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
           console.log("Making request to create a task:", taskData);
           const response = await this.client.post(`/task/create`, taskData);
           console.log("Received response:", response.data);
           return response.data;
       } catch (error) {
           console.error("Failed to create a task:", error);
           throw error;
   }
   }
   async updateTask(updatedInfo) {
          try {
              console.log("Making request to update a task:", updatedInfo);
              const response = await this.client.post(`/task/update/{id}`, updatedInfo);
              console.log("Received response:", response.data);
              return response.data;
          } catch (error) {
              console.error("Failed to update a task:", error);
              throw error;
      }
      }

   }//end

