import axios from "axios";

class UserService {

// constructs an axios instance with custom config
    constructor() {
        let apiClient = axios.create({
            // TODO: fetch user Id and add to baseURL
            // TODO: header with token has to be set here instead of store
            baseURL: "http://localhost:8088/api/user/",
        })
        // if status code is in range of 2xx success will be called
        // if status code is outside of range of 2xx error will be called
        apiClient.interceptors.response.use(this.success, this.error);
        this.apiClient = apiClient;
    }

    // gets called if status code is in range of 2xx
    success(response) {
        return response;
    }

    // gets called if status code is outside of range of 2xx
    error(error){
        /*  switch (error.response.status) {
              //TODO: add what should happen if status xyz is returned
          }*/
        return Promise.reject(error)
    }

    getUser(userID) {
        return this.apiClient.get( userID + '/personal')
    }

}
export default new UserService