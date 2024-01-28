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

            console.log("Login response:", response);

            if (response.data && response.data.userId) {
                localStorage.setItem('userId', response.data.userId);
            } else {
                console.log("UserId not present in response");
            }

            return response.data;
        } catch (error) {
            console.error("Login error:", error);
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
