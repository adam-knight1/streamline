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

   async addTaskToTaskList(title, body, status) {
       try {
           const response = await this.client.post('/task/add', {
               title: title,
               body: body,
               status: status
           });
           console.log("Axios response:", response);
           console.log("Parsed data:", response.data);
           return response.data;
       } catch (error) {
           console.error("Error in addTaskToTaskList:", error);
           return this.handleError("addTaskToTaskList", error);
       }
   }

   }

   


