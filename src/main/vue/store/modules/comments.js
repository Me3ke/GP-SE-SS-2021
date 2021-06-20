export const namespaced = true

export const state = {
    public: true,
    comments: [
        {
            commentID: 1,
            date: '17.06.2021',
            fromMail: 'Hans.Schneide.Test@gmail.com',
            fromName: 'Hans Schneider',
            content: "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam",
            answers: [
                {
                    answerID: 1,
                    date: '18.06.2021',
                    fromMail: 'Ruediger.Spieler@gmail.com',
                    fromName: 'Ruediger Spieler',
                    content: "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et",
                },
                {
                    answerID: 2,
                    date: '20.06.2021',
                    fromMail: 'Ruediger.Spieler@mail.com',
                    fromName: 'Ruediger Spieler',
                    content: "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et",
                }]
        },
        {
            commentID: 2,
            date: '20.06.2021',
            fromMail: 'Hans.Schneider.Test@gmail.com',
            fromName: 'Hans Schneider',
            content: "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam",
            answers: []
        },
        {
            commentID: 3,
            date: '20.06.2021',
            fromMail: 'Hans.Schneider.Test@gmail.com',
            fromName: 'Hans Schneider',
            content: "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.",
            answers: []
        },
        {
            commentID: 4,
            date: '20.06.2021',
            fromMail: 'Hans.Schneider.Test@gmail.com',
            fromName: 'Hans Schneider',
            content: 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.',
            answers: [{
                answerID: 3,
                date: '18.06.2021',
                fromMail: 'Ruediger.Spieler@gmail.com',
                fromName: 'Ruediger Spieler',
                content: "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et",
            },
                {
                    answerID: 4,
                    date: '20.06.2021',
                    fromMail: 'Ruediger.Spieler@mail.com',
                    fromName: 'Ruediger Spieler',
                    content: "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et"
                }]
        }
    ]


}

export const mutations = {}

export const actions = {}

export const getters = {
    getPublic: (state) => {
        return state.public
    },
    //return comments, sorted by oldest first
    getCommentsOldest: (state) => {
        return state.comments
    },
    // returns comments, sorted by newest first (answers are still oldest first to avoid confusions)
    getCommentsNewest: (state) => {
        return [].concat(state.comments).reverse();
    }
}
