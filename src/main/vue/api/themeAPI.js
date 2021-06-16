import axios from "axios";

export default {
    // gives back colors on server
    async getColors() {
        return axios({
            method: "get",
            url: 'http://localhost:8088/api/corporate/colors'
        })
    },
    // puts new colors into database
    async putColors(colors) {
        return axios({
            method: "put",
            url: 'http://localhost:8088/api/corporate/colors',
            headers: {"Content-Type": "application/json"},
            data: {
                colors: colors
            }
        })
    }
}
