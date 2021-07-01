import store from "@/main/vue/store/store";

function filterEnvelopes(envelopes, filters) {
    /* Default Values:
    state: "",
    owner: false,
    search: "",
    signatory: false,
    reader: false,
    dataType: ""

    creationDateMin: "",
    creationDateMax: "",
    endDateMin: "",
    endDateMax: "",
    */
    let result = [];
    let i;
    for (i = 0; i < envelopes.length; i++) {
        let envelope = envelopes[i]

        let stateFilter = false
        let search = false
        let signatory = false
        let reader = false
        let type = false
        let j
        for (j = 0; j < envelope.documents.length; j++) {
            let document = envelope.documents[j]
            // Filter for state
            if (filters.state.includes(document.state)) {
                stateFilter = true
            }
            //Filter for documents that need to be signed
            if(document.signatory && !document.signed){
                signatory = true
            }
            //Filter for documents that need to be read
            if(document.reader && !document.read){
                reader = true
            }
            //Search in document title
            if(document.title.toLowerCase().includes(filters.search.toLowerCase())) {
                search = true
            }
            //Filter data types
            if(filters.dataType === "" || filters.dataType === document.dataType) {
                type = true;
            }
        }
        //Search in Envelope name
        if(envelope.name.toLowerCase().includes(filters.search.toLowerCase())) {
            search = true
        }
        // Add matching results
        let matches = true
        if(!(filters.state === "")){
            if(!stateFilter) {
                matches = false
            }
        }
        if(filters.owner){
            if(!(store.state.auth.username === envelope.owner.email)) {
                matches = false
            }
        }
        if(filters.signatory && filters.reader){
            if(!signatory && !reader) {
                matches = false
            }
        } else if (filters.signatory) {
            if(!signatory) {
                matches = false
            }
        } else if(filters.reader){
            if(!reader) {
                matches = false
            }
        }
        if(!(filters.search === "")) {
            if(!search) {
                matches = false
            }
        }
        if(!type) {
            matches = false
        }
        if(matches) {
            result.push(envelope)
        }
    }
    return result
}
export{filterEnvelopes};
