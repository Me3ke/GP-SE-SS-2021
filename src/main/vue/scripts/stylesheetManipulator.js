import store from "@/main/vue/store/store";

// makes dom changes to color variables whose information is provided in the sheetInformation
export function constructSheet(sheetInformation) {
    let sheetString =
        ":root{\n" +
        "--dark-grey: " + sheetInformation[0] + ";\n" +
        "--elsa-blue: " + sheetInformation[1] + ";\n" +
        "--elsa-blue-transparent: " + hexToRgba(sheetInformation[1], 0.1) + ";\n" +
        "--whitesmoke: " + sheetInformation[2] + ";\n" +
        "--light-grey: " + sheetInformation[3] + ";\n" +
        "--shadow-grey: " + sheetInformation[4] + ";\n" +
        "--open-doc-hover: " + sheetInformation[5] + ";\n" +
        "--closed-doc: " + sheetInformation[6] + ";\n" +
        "--closed-doc-hover: " + sheetInformation[7] + ";\n" +
        "--sign-doc: " + sheetInformation[8] + ";\n" +
        "--sign-doc-hover: " + sheetInformation[9] + ";\n" +
        "--red: " + sheetInformation[10] + ";\n" +
        "--darkRed: " + sheetInformation[11] + ";\n" +
        "--user-info: " + sheetInformation[12] + ";\n" +
        "--modalBackground: " + hexToRgba(sheetInformation[13], 0.5) + ";\n" +
        "}\n" +
        "[data-theme=\"darkMode\"]{\n" +
        "--dark-grey:" + sheetInformation[14] + ";\n" +
        "--elsa-blue:" + sheetInformation[15] + ";\n" +
        "--elsa-blue-transparent: " + hexToRgba(sheetInformation[15], 0.1) + ";\n" +
        "--whitesmoke: " + sheetInformation[16] + ";\n" +
        "--light-grey: " + sheetInformation[17] + ";\n" +
        "--shadow-grey: " + sheetInformation[18] + ";\n" +
        "--open-doc-hover: " + sheetInformation[19] + ";\n" +
        "--closed-doc: " + sheetInformation[20] + ";\n" +
        "--closed-doc-hover: " + sheetInformation[21] + ";\n" +
        "--sign-doc: " + sheetInformation[22] + ";\n" +
        "--sign-doc-hover: " + sheetInformation[23] + ";\n" +
        "--red: " + sheetInformation[24] + ";\n" +
        "--darkRed: " + sheetInformation[25] + ";\n" +
        "--user-info: " + sheetInformation[26] + ";\n" +
        "--modalBackground: " + hexToRgba(sheetInformation[27], 0.65) + ";\n" +
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
