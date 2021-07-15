function filterUser(user, filter) {
    // Filter values
    const admin = filter.admin
    const active = filter.active
    const inactive = filter.inactive
    const completeSearch = filter.completeSearch
    const search = filter.search


    let hereUser = user.slice(0)

    let resUser

    // only admins?
    if (admin) {
        resUser = hereUser.filter(user => user.roles.includes('ROLE_ADMIN'))
    } else {
        resUser = hereUser
    }

    // only active?
    if (active) {
        resUser = resUser.filter(user => user.adminValidated)
    }

    // only inactive?
    if (inactive) {
        resUser = resUser.filter(user => !user.adminValidated)
    }

    // search for keywords
    if (search !== '') {
        if (completeSearch) {
            resUser = resUser.filter(user =>
                [user.firstname, user.lastname].join(' ').toLowerCase() === search.toLowerCase()
                || user.email.toLowerCase() === search.toLowerCase())
        } else {
            resUser = resUser.filter(user =>
                [user.firstname, user.lastname].join(' ').toLowerCase().includes(search.toLowerCase())
                || user.email.toLowerCase().includes(search.toLowerCase()))
        }
    }

    return resUser
}

function sortUser(user, filter) {
    const sortValue = filter.sort;
    let hereUser = user.slice(0)


    // sorting array depending on option user selected
    switch (sortValue) {
        case 'NEWEST':
            hereUser = hereUser.reverse()
            break
        case 'OLDEST':
            break
        case 'ABCN':
            hereUser = hereUser.sort((a, b) => [a.firstname, a.lastname].join(' ').toLowerCase()
                .localeCompare([b.firstname, b.lastname].join(' ').toLowerCase()))
            break
        case 'CBAN':
            hereUser = hereUser.sort((a, b) => [a.firstname, a.lastname].join(' ').toLowerCase()
                .localeCompare([b.firstname, b.lastname].join(' ').toLowerCase())).reverse()
            break
        case 'ABCE':
            hereUser = hereUser.sort((a, b) => a.email.toLowerCase()
                .localeCompare(b.email.toLowerCase()))
            break
        case 'CBAE':
            hereUser = hereUser.sort((a, b) => a.email.toLowerCase()
                .localeCompare(b.email.toLowerCase())).reverse()
            break
        default:
            break
    }

    return hereUser
}


export {filterUser, sortUser};
