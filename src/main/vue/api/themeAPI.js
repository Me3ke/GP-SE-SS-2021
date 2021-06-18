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
    },
    // gives back logos
    async getLogos() {
        return axios({
            method: "get",
            url: 'http://localhost:8088/api/corporate/logo'
        })
    },
    // puts corporate logo into database
    async putLogos(logo, logoDark, logoType, logoDarkType) {
        return axios({
            method: "put",
            url: 'http://localhost:8088/api/corporate/logo',
            data: {
                logo: logo,
                logoDark: logoDark,
                logoType: logoType,
                logoDarkType: logoDarkType
            }
        })
    }
}
