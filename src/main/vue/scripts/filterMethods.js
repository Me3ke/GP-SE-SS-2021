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
        let creationDateMin = false
        let creationDateMax = false
        let endDateMin = false
        let endDateMax = false
        let j
        for (j = 0; j < envelope.documents.length; j++) {
            let document = envelope.documents[j]
            // Filter for state
            if (filters.state.includes(document.state)) {
                stateFilter = true
            }
            //Filter for documents that need to be signed
            if (document.signatory && !document.signed) {
                signatory = true
            }
            //Filter for documents that need to be read
            if (document.reader && !document.read) {
                reader = true
            }
            //Search in document title
            if (document.title.toLowerCase().includes(filters.search.toLowerCase())) {
                search = true
            }
            //Filter data types
            if (filters.dataType === "" || filters.dataType === document.dataType) {
                type = true;
            }
            //Filter CreationDate
            if (filters.creationDateMin === "" || !(dateCompare(document.creationDate, filters.creationDateMin) === -1)) {
                creationDateMin = true
            }
            if (filters.creationDateMax === "" || !(dateCompare(document.creationDate, filters.creationDateMax) === 1)) {
                creationDateMax = true
            }
            //Filter EndDate
            if (filters.endDateMin === "" || !(dateCompare(document.endDate, filters.endDateMin) === -1)) {
                endDateMin = true
            }
            if (filters.endDateMax === "" || !(dateCompare(document.endDate, filters.endDateMax) === 1)) {
                endDateMax = true
            }
        }
        //Search in Envelope name
        if (envelope.name.toLowerCase().includes(filters.search.toLowerCase())) {
            search = true
        }
        // Add matching results
        let matches = true
        if (!(filters.state === "")) {
            if (!stateFilter) {
                matches = false
            }
        }
        if (filters.owner) {
            if (!(store.state.auth.username === envelope.owner.email)) {
                matches = false
            }
        }
        if (filters.signatory && filters.reader) {
            if (!signatory && !reader) {
                matches = false
            }
        } else if (filters.signatory) {
            if (!signatory) {
                matches = false
            }
        } else if (filters.reader) {
            if (!reader) {
                matches = false
            }
        }
        if (!(filters.search === "")) {
            if (!search) {
                matches = false
            }
        }
        if (!(type && creationDateMin && creationDateMax && endDateMin && endDateMax)) {
            matches = false
        }
        if (matches) {
            result.push(envelope)
        }
    }
    return result
}

export {filterEnvelopes};

function dateToInts(date, divider) {
    let day;
    let month;
    let year;
    let numbers = date.split(divider)
    if (divider === "-") {
        day = parseInt(numbers[2])
        month = parseInt(numbers[1])
        year = parseInt(numbers[0])
    } else if (divider === ".") {
        day = parseInt(numbers[0])
        month = parseInt(numbers[1])
        year = parseInt(numbers[2])
    }
    return {day: day, month: month, year: year}
}

// returns 1 if envelopeDate is later than filterDate, -1 if envelopeDate is earlier than filterDate and 0 if envelopeDate and filterDate are the same
function dateCompare(envelopeDate, filterDate) {
    let dateA = dateToInts(envelopeDate, ".");
    let dateB = dateToInts(filterDate, "-")

    if (dateA.year > dateB.year) {
        return 1;
    } else if (dateA.year < dateB.year) {
        return -1;
    } else {
        if (dateA.month > dateB.month) {
            return 1;
        } else if (dateA.month < dateB.month) {
            return -1;
        } else {
            if (dateA.day > dateB.day) {
                return 1;
            } else if (dateA.day < dateB.day) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
