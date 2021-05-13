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
                "title": "Mein super Dokument"
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
                "title": "Mein besseres Dokument"
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
                "title": "Mein bestes Dokument"
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
                "title": "Mein drittes Dokument"
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
                "title": "Mein super Dokument"
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
                "title": "Mein besseres Dokument"
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
                "title": "Mein bestes Dokument"
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
                "title": "Mein drittes Dokument"
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
                "title": "Mein super Dokument"
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
                "title": "Mein besseres Dokument"
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
                "title": "Mein drittes Dokument"
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
                "title": "Mein super Dokument"
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
                "title": "Mein besseres Dokument"
            }
        },
        {
            "id": 14,
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
            "id": 15,
            "sentBy": "besteMail@mailService.de",
            "category": "Sign",
            "dateSent": "21.04.2021",
            "watched": "True",
            "content": "Das folgende Dokument wurde erfolgreich von Superman unterschrieben.",
            "correspondingDocument": {
                "id": "22",
                "title": "Mein drittes Dokument"
            }
        },
        {
            "id": 16,
            "sentBy": "superMail@mailService.de",
            "category": "Reminder",
            "dateSent": "30.04.2021",
            "watched": "True",
            "content": "Das folgende Dokument muss in 3 Tagen signiert sein.",
            "correspondingDocument": {
                "id": "00",
                "title": "Mein super Dokument"
            }
        },
        {
            "id": 17,
            "sentBy": "bessereMail@mailService.de",
            "category": "Updated",
            "dateSent": "27.04.2021",
            "watched": "True",
            "content": "Das folgende Dokument wurde aktualisiert und alle Unterschirften müssen neu getätigt werden.",
            "correspondingDocument": {
                "id": "11",
                "title": "Mein besseres Dokument"
            }
        },
        {
            "id": 18,
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
            "id": 19,
            "sentBy": "besteMail@mailService.de",
            "category": "Sign",
            "dateSent": "21.04.2021",
            "watched": "True",
            "content": "Das folgende Dokument wurde erfolgreich von Superman unterschrieben.",
            "correspondingDocument": {
                "id": "22",
                "title": "Mein drittes Dokument"
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
