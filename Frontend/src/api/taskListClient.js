import BaseClass from "../util/baseClass";
import axios from 'axios';
import Toastify from "toastify-js";

export default class TaskListClient extends BaseClass {
    constructor(baseURL) {
        super();
        this.client = axios.create({
            baseURL: baseURL
        });
    }

    async createTaskList(userId, taskListName){
        try {
            const response = await this.client.post('/taskList', {
                userId: userId,
                taskListName: taskListName
            });
            return response.data;
        } catch (error) {
            console.error("Failed to create task list:", error);
            throw error;
        }
    }

    async updateTaskList(userId, updatedName){
        try {
            const response = await this.client.put(`/taskList/${taskListId}`, {
                userId: userId,
                updatedName: updatedName
            });
            return response.data;
        } catch (error) {
            console.error("Failed to update task list:", error);
            throw error;
        }
    }
}