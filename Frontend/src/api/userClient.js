import BaseClass from "../util/baseClass";
import axios from 'axios';

export default class UserClient extends BaseClass {

    constructor(baseURL) {
        super();
        this.bindClassMethods(['getUser', 'createUser', 'handleError'], this);
        this.client = axios.create({
            baseURL: baseURL
        });
    }

    async getUser(userId) {
        try {
            const response = await this.client.get(`/user/${userId}`);
            console.log("Received response:", response.data);
            return response.data;
        } catch (error) {
            return this.handleError("getUser", error);
        }
    }

    async getUserByUsername(username) {
            try {
                const response = await this.client.get(`/user/name/${username}`);
                console.log("Received response:", response.data);
                return response.data;
            } catch (error) {
                return this.handleError("getUserByUsername", error);
            }
        }

    async createUser(username, password, email) {
        try {
            const response = await this.client.post('/user/create', {
                username: username,
                password: password,
                email: email
            });
            console.log("Axios response:", response);
            console.log("Parsed data:", response.data);
            return response.data;
        } catch (error) {
            console.error("Error in createUser:", error)
            return this.handleError("createUser", error);
        }
    }

   /* async updateUser(userId, updatedInfo) {
        try {
            const response = await this.client.put(`/user/${userId}`, updatedInfo);
            return response.data;
        } catch (error) {
            return this.handleError("updateUser", error);
        }
    }*/

    /*async deleteUser(userId) {
        try {
            const response = await this.client.delete(`/user/${userId}`);
            return response.data;
        } catch (error) {
            return this.handleError("deleteUser", error);
        }
    }*/



    handleError(method, error) {
        console.error(method + " failed - " + error);
        if (error.response && error.response.data.message) {
            console.error(error.response.data.message);
        }
        throw error;
    }
    }
