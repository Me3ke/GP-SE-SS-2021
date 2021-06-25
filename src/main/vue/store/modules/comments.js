import commentAPI from "@/main/vue/api/commentsAPI";

export const namespaced = true

export const state = {
    public: true,
    comments: {},
    errorFetchComments: {},
    answerResponse: {},
    errorAnswerResponse: {},
    commentResponse: {},
    errorCommentResponse: {}
}

export const mutations = {
    // sets comments-array
    SET_COMMENTS(state, comments) {
        state.comments = comments
    },
    SET_POST_ANSWER_RESPONSE(state, response) {
        state.answerResponse = response
    },
    SET_POST_COMMENT_RESPONSE(state, response) {
        state.commentResponse = response
    },

    SET_ERROR_FETCH_COMMENTS(state, error) {
        state.errorFetchComments = error
    },

    ERROR_SET_POST_ANSWER_RESPONSE(state, error) {
        state.errorAnswerResponse = error
    },

    ERROR_SET_POST_COMMENT_RESPONSE(state, error) {
        state.errorCommentResponse = error
    }
}

export const actions = {

    // fetches comments for document with id documentId
    fetchComments({commit}, docId) {
        return commentAPI.getComments(docId).then(response => {
            commit('SET_COMMENTS', response.data)
            commit('SET_ERROR_FETCH_COMMENTS', {})
        }).catch(error => {
            commit('SET_ERROR_FETCH_COMMENTS', error)
        })
    },

    // posts answer to comment with id commentId
    postAnswers({commit}, {docId, commentId, content}) {
        return commentAPI.answerComment(docId, commentId, content).then(response => {
            commit('SET_POST_ANSWER_RESPONSE', response.data)
            commit('ERROR_SET_POST_ANSWER_RESPONSE', {})
        }).catch(error => {
            commit('ERROR_SET_POST_ANSWER_RESPONSE', error)
        })
    },

    // posts comment on document with id docId
    postComment({commit}, {docId, content}) {
        return commentAPI.writeComment(docId, content).then(response => {
            commit('SET_POST_COMMENT_RESPONSE', response.data)
            commit('ERROR_SET_POST_COMMENT_RESPONSE', {})
        }).catch(error => {
            commit('ERROR_SET_POST_COMMENT_RESPONSE', error)
        })
    },

    // resets comments in order to clean local storage
    resetComments({commit}) {
        commit('SET_COMMENTS', {})
        commit('SET_POST_ANSWER_RESPONSE', {})
        commit('SET_POST_COMMENT_RESPONSE', {})
        commit('SET_ERROR_FETCH_COMMENTS', {})
        commit('ERROR_SET_POST_ANSWER_RESPONSE', {})
        commit('ERROR_SET_POST_COMMENT_RESPONSE', {})
    },
}

export const getters = {
    getPublic: (state) => {
        return state.public
    },
    //return comments, sorted by oldest first
    getCommentsOldest: (state) => {
        return state.comments.comments
    },
    // returns comments, sorted by newest first (answers are still oldest first to avoid confusions)
    getCommentsNewest: (state) => {
        return [].concat(state.comments.comments).reverse();
    },

    getFetchCommentsError: (state) => {
        return state.errorFetchComments
    },
    getPostCommentError: (state) => {
        return state.errorCommentResponse
    },
    getAnswerCommentError: (state) => {
        return state.errorAnswerResponse
    }
}
