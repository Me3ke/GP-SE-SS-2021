import axios from "axios";
import store from "@/main/vue/store/store";

export default {
    // gives back document with id docId  in envelop wit id envId, does not download it
    async uploadDocument(envId, file, settings) {
        return axios({
            method: "put",
            url: 'http://localhost:8088/api.elsa.de/user/' + store.state.auth.username + '/envelopes/' + envId,
            data: {
                data: file.data,
                title: file.name,
                type: file.type,
                signatories: settings.signatories,
                endDate: settings.endDate,
                orderRelevant: settings.orderRelevantReaders,
            }
        })
    }
}
