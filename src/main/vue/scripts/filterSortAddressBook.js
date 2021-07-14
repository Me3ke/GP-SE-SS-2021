function filterBook(book, filter) {
    // Filter values
    const favorite = filter.favorite
    const search = filter.search

    let hereBook = book.slice(0)

    let resBook

    // only admins?
    if (favorite) {
        resBook = hereBook.filter(entry => entry.favorite)
    } else {
        resBook = hereBook
    }

    // search for keywords
    if (search !== '') {
        resBook = resBook.filter(entry =>
            [entry.firstname, entry.lastname].join(' ').toLowerCase().includes(search.toLowerCase())
            || entry.email.toLowerCase().includes(search.toLowerCase()))
    }

    return resBook
}

function sortBook(book, filter) {
    const sortValue = filter.sort;
    let hereBook = book.slice(0)


    // sorting array depending on option user selected
    switch (sortValue) {
        case 'NEWEST':
            hereBook = hereBook.reverse()
            break
        case 'OLDEST':
            break
        case 'ABCN':
            hereBook = hereBook.sort((a, b) => [a.firstname, a.lastname].join(' ').toLowerCase()
                .localeCompare([b.firstname, b.lastname].join(' ').toLowerCase()))
            break
        case 'CBAN':
            hereBook = hereBook.sort((a, b) => [a.firstname, a.lastname].join(' ').toLowerCase()
                .localeCompare([b.firstname, b.lastname].join(' ').toLowerCase())).reverse()
            break
        case 'ABCE':
            hereBook = hereBook.sort((a, b) => a.email.toLowerCase()
                .localeCompare(b.email.toLowerCase()))
            break
        case 'CBAE':
            hereBook = hereBook.sort((a, b) => a.email.toLowerCase()
                .localeCompare(b.email.toLowerCase())).reverse()
            break
        default:
            break
    }

    return hereBook
}


export {filterBook, sortBook};
