import BaseClass from "../util/baseClass";
import axios from 'axios';

export default class LoginClient extends BaseClass {

    constructor(baseURL) {
        super();
        this.bindClassMethods(['loginUser', 'handleError'], this);
        this.client = axios.create({
            baseURL: baseURL
        });
    }

    async loginUser(username, password) {
        try {
            const response = await this.client.post('/login', {
                username: username,
                password: password
            });
            return response.data;
        } catch (error) {
            return this.handleError("loginUser", error);
        }
    }

    handleError(method, error) {
        console.error(method + " failed - " + error);
        if (error.response && error.response.data.message) {
            console.error(error.response.data.message);
        }
        throw error;
    }
}
