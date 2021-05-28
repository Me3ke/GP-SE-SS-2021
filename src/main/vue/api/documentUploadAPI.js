import axios from "axios";
import store from "@/main/vue/store/store";

export default {
    // gives back document with id docId  in envelop wit id envId, does not download it
    async uploadDocument(envId, file, settings) {
        return axios({
            method: "put",
            url: 'http://localhost:8088/api.elsa.de/user/' + store.state.auth.username + '/envelopes/' + envId,
            data: {
                binary: file.getAsBinary(),
                title: settings.title,
                type: settings.type,
                signatories: settings.signatories,
                readers: settings.readers,
                signatureType: settings.signatureType,
                endDate: settings.endDate,
                orderRelevant: settings.orderRelevant,
                state: settings.state
            }
        })
    }
}
