//import axios from "axios";
import guestAuthAPI from "../../api/guestAuthAPI";

const guestAuthorization = {
    state: () => ({
        guestAuthenticated: false,
        guestToken: "none",
        guestUsername: "none"
    }),

    mutations: {

        AUTHENTICATE(state, token, guestUsername) {
            //TODO: Change handling of response token
            if (token !== null) {
                state.guestToken = token
                //TODO: Request if Authenticated for the docID
                state.guestAuthenticated = true
                state.guestUsername = guestUsername;
            } else {
                state.guestAuthenticated = false
                state.guestToke = "none"
                state.guestUsername = "none"
            }
        }
    },
    actions: {

        requestGuestInfo({commit}, {envId, docId, guestToken}) {
            return new Promise((resolve, reject) => {
                //TODO: change request
                guestAuthAPI.guestValidate(envId, docId, guestToken).then(res => {
                    let guestUsername = 'testdulli'
                    console.log(res.data)
                    console.log("logged response")
                    console.log("Status code: "+res.status)
                    commit('AUTHENTICATE', guestToken, guestUsername)
                    resolve()
                }).catch(() => {
                    commit('AUTHENTICATE', null)
                    reject()
                })
            })
        }
    },

    getters: {
        isMyGuestAuthenticated(state) {
            return state.authenticated;
        },
        getGuestToken(state) {
            return state.token;
        },
        getGuestUsername(state) {
            return state.username
        },
        getGuestRole(state) {
            return state.role
        }
    }

}

export default guestAuthorization
