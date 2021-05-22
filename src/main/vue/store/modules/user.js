import UserService from "@/main/vue/services/UserService"

export const state = {
    user: {
        email: "sehrTolle@email.com",
        firstname: "Otto",
        lastname: "Wehner",
        street: "Siegwardsweg",
        houseNumber: 42,
        postcode: 55555,
        hometown: "Ownerhausen",
        country: "Deutschland",
        birthday: "30-04-2021",
        phoneNumber: "+49 93483932",
        publicKey: "z10f8dh736rz98712c6tz7r983t"
    }
}

export const mutations = {
    SET_USER(state, user) {
        state.user = user
    }
}

export const actions = {
    fetchUser({commit}, userID) {
        UserService.getUser(userID).then(response => {
            commit('SET_USER', response.data)
        }).catch(error => {
            console.log(error)
        })
    }
}

export const getters = {
    User: (state) => {
        return state.user
    }
}
