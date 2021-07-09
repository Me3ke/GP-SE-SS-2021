import axios from "axios";
import store from "@/main/vue/store/store";

export default {
    // gives back address book of logged in user
    async getBook() {
        return axios({
            method: "get",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/directory'
        })
    },
    // adds new entry to address book of logged in user
    async putEntry(newEntry) {
        return axios({
            method: "put",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/directory/newEntry',
            data: {
                'email': newEntry.email,
                "firstname": newEntry.firstname,
                "lastname": newEntry.lastname,
                "favorite": newEntry.favorite,
                "note": newEntry.note
            }
        })
    },
    // changes address book entry of logged in user
    async changeEntry(entryId, newEntry) {
        return axios({
            method: "post",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/directory/' + entryId,
            data: {
                'id': entryId,
                'email': newEntry.email,
                "firstname": newEntry.firstname,
                "lastname": newEntry.lastname,
                "favorite": newEntry.favorite,
                "note": newEntry.note
            }
        })
    },
    // deletes address book entry of logged in user
    async deleteEntry(entryId) {
        return axios({
            method: "delete",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/directory/' + entryId
        })
    },
    // gives back address book settings of logged in user
    async getBookSettings() {
        return axios({
            method: "get",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/directory/directorySettings'
        })
    },
    // sets address book settings of logged in user
    async putBookSettings(settings) {
        return axios({
            method: "put",
            url: 'http://localhost:8088/api/user/' + store.state.auth.username + '/directory/changeSettings',
            data: {
                'addAllAutomatically': settings.all,
                'addDomainAutomatically': settings.domain
            }
        })
    }
}
