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
            const response = await this.client.post('/taskList/create', {
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
            const response = await this.client.put(`/taskList/${userId}`, {
                userId: userId,
                updatedName: updatedName
            });
            return response.data;
        } catch (error) {
            console.error("Failed to update task list:", error);
            throw error;
        }
    }
/*
    async getTaskListByUserId(userId) {
            try {
                const response = await this.client.get(`/taskList/${userId}`);
                console.log("Received response:", response.data);
                return response.data;
            } catch (error) {
                return this.handleError("getTaskListByUserId", error);
            }
        }*/

        async getTasksByUserId(userId) {
            try {
                const response = await this.client.get(`/task/user/${userId}`);
                return response.data;
            } catch (error) {
                console.error("Error getting tasks by userId:", error);
                throw error;
            }

}
}