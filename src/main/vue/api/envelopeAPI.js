import axios from "axios";
import store from "@/main/vue/store/store";

export default {
    // gives back all envelopes associated with user
    async getEnvelopes() {
        return axios({
            method: "get",
            url: 'http://localhost:8088/api.elsa.de/user/' + store.state.auth.username + '/envelopes' /*,
            params: {
                request: "Ich bin eine coole Request!!!!"
                titleFilter: filter.title,
                nameFilter: "",
                envelopeIDFilter: filter.envelopeID,
                stateFilter: filter.state,
                ownerIDFilter: filter.owner,
                creationDateFilterFrom: filter.creationDateMin,
                creationDateFilterTo: filter.creationDateMax,
                endDateFilterFrom: filter.endDateMin,
                endDateFilterTo: filter.endDateMax,
                signatureTypeFilter: filter.signatureType,
                dataType: filter.datatype,
                signatoryIDs: filter.signatories,
                readerIDs: filter.readers,
                signed: filter.signed,
                read: filter.read,
                pageLimit: pageLimit,
                page: page,
                sort: sort
            } */
        })
    }
}
