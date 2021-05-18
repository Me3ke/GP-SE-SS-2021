import _ from "lodash";

export const state = {
    messages: [
        {
            "id": 0,
            "sentBy": "superMail@mailService.de",
            "category": "Reminder",
            "dateSent": "30.04.2021",
            "content": "Das folgende Dokument muss in 3 Tagen signiert sein.",
            "watched": "False",
            "correspondingDocument": {
                "id": "00",
                "title": "Essensplan KW 25"
            }
        },
        {
            "id": 1,
            "sentBy": "bessereMail@mailService.de",
            "category": "Updated",
            "dateSent": "27.04.2021",
            "watched": "False",
            "content": "Das folgende Dokument wurde aktualisiert und alle Unterschirften müssen neu getätigt werden.",
            "correspondingDocument": {
                "id": "11",
                "title": "Steuereinkommenserklärung"
            }
        },
        {
            "id": 2,
            "sentBy": "besteMail@mailService.de",
            "category": "Checked",
            "dateSent": "21.04.2021",
            "watched": "False",
            "content": "Das folgende Dokument wurde erfolgreich von Batman gegengelesen.",
            "correspondingDocument": {
                "id": "22",
                "title": "Essensplan KW 26"
            }
        },
        {
            "id": 3,
            "sentBy": "besteMail@mailService.de",
            "category": "Sign",
            "dateSent": "21.04.2021",
            "watched": "False",
            "content": "Das folgende Dokument wurde erfolgreich von Superman unterschrieben.",
            "correspondingDocument": {
                "id": "22",
                "title": "Essensplan KW 24"
            }
        }, {
            "id": 4,
            "sentBy": "superMail@mailService.de",
            "category": "Reminder",
            "dateSent": "30.04.2021",
            "watched": "True",
            "content": "Das folgende Dokument muss in 3 Tagen signiert sein.",
            "correspondingDocument": {
                "id": "00",
                "title": "Kühlschrank EG"
            }
        },
        {
            "id": 5,
            "sentBy": "bessereMail@mailService.de",
            "category": "Updated",
            "dateSent": "27.04.2021",
            "watched": "True",
            "content": "Das folgende Dokument wurde aktualisiert und alle Unterschirften müssen neu getätigt werden.",
            "correspondingDocument": {
                "id": "11",
                "title": "Kühlschrank 1.OG"
            }
        },
        {
            "id": 6,
            "sentBy": "besteMail@mailService.de",
            "category": "Checked",
            "dateSent": "21.04.2021",
            "watched": "True",
            "content": "Das folgende Dokument wurde erfolgreich von Batman gegengelesen.",
            "correspondingDocument": {
                "id": "22",
                "title": "Bedienungsanleitung Wasserspender"
            }
        },
        {
            "id": 7,
            "sentBy": "besteMail@mailService.de",
            "category": "Sign",
            "dateSent": "21.04.2021",
            "watched": "True",
            "content": "Das folgende Dokument wurde erfolgreich von Superman unterschrieben.",
            "correspondingDocument": {
                "id": "22",
                "title": "Kantinenordnung"
            }
        },
        {
            "id": 8,
            "sentBy": "superMail@mailService.de",
            "category": "Reminder",
            "dateSent": "30.04.2021",
            "watched": "True",
            "content": "Das folgende Dokument muss in 3 Tagen signiert sein.",
            "correspondingDocument": {
                "id": "00",
                "title": "Hygienics and reduced amount of visitor passes"
            }
        },
        {
            "id": 9,
            "sentBy": "bessereMail@mailService.de",
            "category": "Updated",
            "dateSent": "27.04.2021",
            "watched": "True",
            "content": "Das folgende Dokument wurde aktualisiert und alle Unterschirften müssen neu getätigt werden.",
            "correspondingDocument": {
                "id": "11",
                "title": "Programme"
            }
        },
        {
            "id": 10,
            "sentBy": "besteMail@mailService.de",
            "category": "Checked",
            "dateSent": "21.04.2021",
            "watched": "True",
            "content": "Das folgende Dokument wurde erfolgreich von Batman gegengelesen.",
            "correspondingDocument": {
                "id": "22",
                "title": "Mein bestes Dokument"
            }
        },
        {
            "id": 11,
            "sentBy": "besteMail@mailService.de",
            "category": "Sign",
            "dateSent": "21.04.2021",
            "watched": "True",
            "content": "Das folgende Dokument wurde erfolgreich von Superman unterschrieben.",
            "correspondingDocument": {
                "id": "22",
                "title": "Mietvertrag Büroräume"
            }
        },
        {
            "id": 12,
            "sentBy": "superMail@mailService.de",
            "category": "Reminder",
            "dateSent": "30.04.2021",
            "watched": "True",
            "content": "Das folgende Dokument muss in 3 Tagen signiert sein.",
            "correspondingDocument": {
                "id": "00",
                "title": "Handout 2. Kundengespräch"
            }
        },
        {
            "id": 13,
            "sentBy": "bessereMail@mailService.de",
            "category": "Updated",
            "dateSent": "27.04.2021",
            "watched": "True",
            "content": "Das folgende Dokument wurde aktualisiert und alle Unterschirften müssen neu getätigt werden.",
            "correspondingDocument": {
                "id": "11",
                "title": "Plan die Weltherrschaft zu eroberen"
            }
        }
    ],
    selectedMsg: {}
}


export const mutations = {
    CHANGE_SELECTED_MSG(state, msg) {
        state.selectedMsg = msg
    },
    CHANGE_WATCHED_STATUS(state) {
        Object.assign(state.selectedMsg, {"watched": "True"})
    }
}


export const actions = {
    patchChangeSelectedMsg({state, commit}, msg) {
        if (state.selectedMsg !== msg) {
            commit('CHANGE_SELECTED_MSG', msg)
        }
    },
    patchChangeWatchedStatus({state, commit}) {
        if (!_.isEmpty(state.selectedMsg) && state.selectedMsg.watched !== "True") {
            commit('CHANGE_WATCHED_STATUS')
        }
    }
}


export const getters = {
    unwatchedCount: (state) => {
        return state.messages.filter(msg => msg.watched === "False").length
    },
    getFirstNMessages: (state) => (n) => {
        return state.messages.slice(0, n)
    }
}
