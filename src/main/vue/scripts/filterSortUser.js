let oldSortValue = ''

function filterUser(user, filter) {
    // Filter values
    const admin = filter.admin
    const active = filter.active
    const inactive = filter.inactive
    const search = filter.search

    let resUser

    // only admins?
    if (admin) {
        resUser = user.filter(user => user.roles.includes('ROLE_ADMIN'))
    } else {
        resUser = user
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
        resUser = resUser.filter(user =>
            [user.firstname, user.lastname].join(' ').toLowerCase().includes(search.toLowerCase())
            || user.email.toLowerCase().includes(search.toLowerCase()))
    }

    return resUser
}

function sortUser(user, filter) {
    const sortValue = filter.sort;

    if (oldSortValue === sortValue) {
        return user
    } else {
        oldSortValue = sortValue
    }

    // sorting array depending on option user selected
    switch (sortValue) {
        case 'NEWEST':
            user = user.reverse()
            break
        case 'OLDEST':
            break
        case 'ABCN':
            user = user.sort((a, b) => [a.firstname, a.lastname].join(' ').toLowerCase()
                .localeCompare([b.firstname, b.lastname].join(' ').toLowerCase()))
            break
        case 'CBAN':
            user = user.sort((a, b) => [a.firstname, a.lastname].join(' ').toLowerCase()
                .localeCompare([b.firstname, b.lastname].join(' ').toLowerCase())).reverse()
            break
        case 'ABCE':
            user = user.sort((a, b) => a.email.toLowerCase()
                .localeCompare(b.email.toLowerCase()))
            break
        case 'CBAE':
            user = user.sort((a, b) => a.email.toLowerCase()
                .localeCompare(b.email.toLowerCase())).reverse()
            break
        default:
            break
    }

    return user
}


export {filterUser, sortUser};
