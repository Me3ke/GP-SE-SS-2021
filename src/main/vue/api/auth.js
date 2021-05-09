import axios from 'axios';

export default {
    login(username, password) { //<1>
        const credentials = new URLSearchParams();
        credentials.append('username', username);
        credentials.append('password', password);
        return axios.post('/login', credentials); //<2>
    }
}
