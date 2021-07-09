function filterHistory(history, filter) {
    // Filter values
    const search = filter.search

    // search for keywords
    if (search !== '') {
        history = history.filter(document =>
            document.title.toLowerCase().includes(search.toLowerCase()))
    }

    return history
}


function sortHistory(history, filter) {
    const sortValue = filter.sort;

    let hereHistory = history.slice(0)

    // sorting array depending on option user selected
    switch (sortValue) {
        case 'NEWEST':
            break
        case 'OLDEST':
            hereHistory = hereHistory.reverse()
            break
        case 'ABC':
            hereHistory = hereHistory.sort((a, b) => a.title.toLowerCase()
                .localeCompare(b.title.toLowerCase()))
            break
        case 'CBA':
            hereHistory = hereHistory.sort((a, b) => a.title.toLowerCase()
                .localeCompare(b.title.toLowerCase())).reverse()
            break
        default:
            break
    }

    return hereHistory
}


export {filterHistory, sortHistory};
