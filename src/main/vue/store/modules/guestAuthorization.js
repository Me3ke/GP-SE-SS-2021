const guestAuthorization = {
    state: () => ({
        authenticated: null,
        token: null,
        username: null,
        documentID: null
    }),
    mutations: {
            AUTHENTICATE(state, token) {
                if (token !== null) {
                    state.token = token
                    state.authenticated = true

                } else {
                    state.authenticated = false
                }
            }
        }
}
