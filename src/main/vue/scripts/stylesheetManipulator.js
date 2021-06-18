import store from "@/main/vue/store/store";

// makes dom changes to color variables whose information is provided in the sheetInformation
export function constructSheet(sheetInformation) {

    let sheetString =
        ":root{\n" +
        "--dark-grey: " + sheetInformation[0] + ";\n" +
        "--elsa-blue: " + sheetInformation[1] + ";\n" +
        "--elsa-blue-transparent: " + hexToRgba(sheetInformation[1], 0.1) + ";\n" +
        "--whitesmoke: whitesmoke;\n" +
        "--light-grey: #D8D8D9;\n" +
        "--shadow-grey: #ACACAC;\n" +
        "--open-doc-hover: #ababab;\n" +
        "--closed-doc: #E5E5E5;\n" +
        "--closed-doc-hover: #C9C9C9;\n" +
        "--sign-doc: #FFE3E3;\n" +
        "--sign-doc-hover: #FFBABA;\n" +
        "--red: #C93A3A;\n" +
        "--user-info: whitesmoke;\n" +
        "--modalBackground: rgba(0, 0, 0, .5);\n" +
        "--darkRed: #a22c2c; \n" +
        "}\n" +
        "[data-theme=\"darkMode\"]{\n" +
        "--dark-grey:" + sheetInformation[0] + ";\n" +
        "--elsa-blue: #436495;\n" +
        "--elsa-blue-transparent: rgba(67, 100, 149, 0.1);\n" +
        "--whitesmoke: whitesmoke;\n" +
        "--light-grey: #D8D8D9;\n" +
        "--shadow-grey: #ACACAC;\n" +
        "--open-doc-hover: #ababab;\n" +
        "--closed-doc: #E5E5E5;\n" +
        "--closed-doc-hover: #C9C9C9;\n" +
        "--sign-doc: #FFE3E3;\n" +
        "--sign-doc-hover: #FFBABA;\n" +
        "--red: #C93A3A;\n" +
        "--user-info: whitesmoke;\n" +
        "--modalBackground: rgba(0, 0, 0, .5);\n" +
        "--darkRed: #a22c2c; \n" +
        "}\n"

    store.dispatch('theme/setStylesheet', sheetString).then(() => loadSheet())
}

// loads sheet saved in localStorage to dom (mandatory after initial start of application and after every page refresh)
export async function loadSheet() {

    // checks if sheet has initially been loaded
    if (!store.getters["theme/getInitialLoad"]) {

        // getting colors from server
        await store.dispatch('theme/fetchColors')
        // constructing sheet with colors from server
        constructSheet(store.getters["theme/getColors"])

        // changing initialLoad to true, so sheet won't be constructed countless times
        store.dispatch('theme/setInitialLoad', true).then()
    }

    /* Applying sheet to DOM */
    let style = document.createElement('style');
    // loading sheet from localStorage
    style.innerHTML = store.getters["theme/getSheet"]
    // applying sheet to dom
    document.head.appendChild(style);
}

// converts hex to rgba (taken from :https://stackoverflow.com/questions/21646738/convert-hex-to-rgba)
function hexToRgba(hex, alpha) {
    const r = parseInt(hex.slice(1, 3), 16),
        g = parseInt(hex.slice(3, 5), 16),
        b = parseInt(hex.slice(5, 7), 16);
    return "rgba(" + r + ", " + g + ", " + b + ", " + alpha + ")";
}
