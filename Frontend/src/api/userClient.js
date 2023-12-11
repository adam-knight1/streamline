import BaseClass from "../util/baseClass";
import axios from 'axios'

export default class UserClient {
    constructor(baseURL) {
        this.client = axios.create({
            baseURL: baseURL
        });
    }

   async getUser(userId) {
       try {
           console.log("Making request to get user with userId:", userId);
           const response = await this.client.get(`/user/${userId}`);
           console.log("Received response:", response.data);
           return response.data;
       } catch (error) {
           console.error("Failed to get user:", error);
           throw error;
   }
   }

    async createUser(username, password, email) {
        try {
            const response = await this.client.post('/user', {
                username: username,
                password: password,
                email: email
            });
            return response.data;
        } catch (error) {
            console.error("Failed to create new user:", error);
            throw error;
        }
    }