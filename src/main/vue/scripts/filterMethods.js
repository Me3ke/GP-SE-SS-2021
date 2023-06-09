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
        let envelope = envelopes[i];
        let stateFilter = false;
        let search = false;
        let signatory = false;
        let reader = false;
        let type = false;
        let creationDateMin = false;
        let creationDateMax = false;
        let endDateMin = false;
        let endDateMax = false;
        let j;
        for (j = 0; j < envelope.documents.length; j++) {
            let document = envelope.documents[j];
            // Filter for state
            if (filters.state.includes(document.state) && !document.draft) {
                stateFilter = true;
            } else if (filters.state === "DRAFT" && document.draft) {
                stateFilter = true;
            }
            //Filter for documents that need to be signed
            if (document.signatory && !document.signed) {
                signatory = true;
            }
            //Filter for documents that need to be read
            if (document.reader && !document.read) {
                reader = true;
            }
            //Search in document title
            if (document.title.toLowerCase().includes(filters.search.toLowerCase())) {
                search = true;
            }
            //Filter data types
            if (filters.dataType === "" || filters.dataType === document.dataType) {
                type = true;
            }
            //Filter CreationDate
            if (filters.creationDateMin === "" || !(dateCompare(document.creationDate, filters.creationDateMin) === -1)) {
                creationDateMin = true;
            }
            if (filters.creationDateMax === "" || !(dateCompare(document.creationDate, filters.creationDateMax) === 1)) {
                creationDateMax = true;
            }
            //Filter EndDate
            if (!(dateCompare(document.endDate, filters.endDateMin) === -1) || filters.endDateMin === "") {
                endDateMin = true;
            }
            if (filters.endDateMax === "" || !(dateCompare(document.endDate, filters.endDateMax) === 1)) {
                endDateMax = true;
            }
        }
        //Search in Envelope name
        if (envelope.name.toLowerCase().includes(filters.search.toLowerCase())) {
            search = true;
        }
        // Add matching results
        let matches = true;
        if (!(filters.state === "")) {
            if (!stateFilter) {
                matches = false;
            }
        }
        if (filters.owner) {
            if (!(store.state.auth.username === envelope.owner.username)) {
                matches = false;
            }
        }
        if (filters.signatory && filters.reader) {
            if (!signatory && !reader) {
                matches = false;
            }
        } else if (filters.signatory) {
            if (!signatory) {
                matches = false;
            }
        } else if (filters.reader) {
            if (!reader) {
                matches = false;
            }
        }
        if (!(filters.search === "")) {
            if (!search) {
                matches = false;
            }
        }
        if (!(type && creationDateMin && creationDateMax && endDateMin && endDateMax)) {
            matches = false;
        }
        if (matches) {
            result.push(envelope);
        }
    }
    return result
}

export {filterEnvelopes};

function sortEnvelopes(envelopes, filters) {
    let sortFirst = filters.sortFirst;
    let sortSecond = filters.sortSecond;
    let result = envelopes;

    switch(sortSecond) {
        case "creation":
            result = sortCreationDate(result);
            break;
        case "creation-rev":
            result = sortCreationDate(result).reverse();
            break;
        case "name":
            result = sortName(result);
            break;
        case "name-rev":
            result = sortName(result).reverse();
            break;
        case "state":
            result = sortState(result);
            break;
        case "state-rev":
            result = sortState(result).reverse();
            break;
        case "role":
            result = sortRole(result);
            break;
        case "role-rev":
            result = sortRole(result).reverse();
            break;
        default: //end
            result = sortEndDate(result);
    }
    switch(sortFirst) {
        case "creation":
            result = sortCreationDate(result);
            break;
        case "creation-rev":
            result = sortCreationDate(result).reverse();
            break;
        case "end":
            result = sortEndDate(result);
            break;
        case "name":
            result = sortName(result);
            break;
        case "name-rev":
            result = sortName(result).reverse();
            break;
        case "state-rev":
            result = sortState(result).reverse();
            break;
        case "role":
            result = sortRole(result);
            break;
        case "role-rev":
            result = sortRole(result).reverse();
            break;
        default: //state
            result = sortState(result);
    }
    return result;
}

export {sortEnvelopes};

function sortName(envelopes) {
    return envelopes.sort((a,b) => a.name.toLowerCase().localeCompare(b.name.toLowerCase()))
}

function sortCreationDate(envelopes) {
    return envelopes.sort((a,b) => dateCompare(a.creationDate, b.creationDate))
}

function sortEndDate(envelopes) {
    return envelopes.sort((a,b) => dateCompare(getEarliestEndDate(a), getEarliestEndDate(b)))
}

function getEarliestEndDate(envelope) {
    let result = envelope.documents[0].endDate;
    let i;
    for (i = 1; i < envelope.documents.length; i++) {
        if(dateCompare(result, envelope.documents[i].endDate) < 0) {
            result = envelope.documents[i].endDate;
        }
    }
    return result;
}

function sortState(envelopes) {
    let important = [];
    let draft = [];
    let sign = [];
    let read = [];
    let closed = [];
    let i;
    for (i = 0; i < envelopes.length; i++) {
        if(!(getState(envelopes[i]) === "ARCHIVED")) {
            if(toSign(envelopes[i])) {
                important.push(envelopes[i]);
            } else {
                if(containsDraft(envelopes[i])) {
                    draft.push(envelopes[i]);
                } else if(getState(envelopes[i]) === "SIGN") {
                    sign.push(envelopes[i]);
                } else {
                    read.push(envelopes[i]);
                }
            }
        } else {
            closed.push(envelopes[i]);
        }
    }
    return important.concat(draft.concat(sign.concat(read.concat(closed))));
}

function getState(envelope) {
    let status = "ARCHIVED";
    let i;
    for(i = 0; i < envelope.documents.length; i++) {
        if(envelope.documents[i].state === "REVIEW") {
            status = "REVIEW";
        }
        if(envelope.documents[i].state === "SIGN") {
            return "SIGN";
        }
    }
    return status;
}

function containsDraft(envelope) {
    let i;
    for(i = 0; i < envelope.documents.length; i++) {
        if(envelope.documents[i].draft) {
            return true;
        }
    }
    return false;
}

function sortRole(envelopes) {
    let signatory = [];
    let reader = [];
    let owner = [];

    let i;
    for(i = 0; i < envelopes.length; i++) {
        switch(getRole(envelopes[i])) {
            case "OWNER":
                owner.push(envelopes[i]);
                break;
            case "READER":
                reader.push(envelopes[i]);
                break;
            default:
                signatory.push(envelopes[i]);
                break;
        }
    }
    return signatory.concat(reader.concat(owner));
}

function getRole(envelope) {
    if(envelope.owner === store.state.auth.username) {
        return "OWNER";
    }
    let i;
    for(i = 0; i < envelope.documents.length; i++) {
        if(envelope.documents[i].signatory) {
            return "SIGNATORY";
        }
    }
    return "READER"
}

function toSign(envelope) {
    let i;
    for(i = 0; i < envelope.documents.length; i++) {
        if(envelope.documents[i].signatory && !envelope.documents[i].signed && envelope.documents[i].turnToSign){
            return true;
        }
        if(envelope.documents[i].reader && !envelope.documents[i].read && envelope.documents[i].turnToReview){
            return true;
        }
    }
    return false;
}

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
