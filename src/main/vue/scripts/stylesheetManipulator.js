import store from "@/main/vue/store/store";

// makes dom changes to color variables whose information is provided in the sheetInformation
export function constructSheet(sheetInformation) {
    let sheetString =
        ":root{\n" +
        "--dark-grey: " + sheetInformation[0] + ";\n" +
        "--elsa-blue: " + sheetInformation[1] + ";\n" +
        "--elsa-blue-transparent: " + hexToRgba(sheetInformation[1], 0.1) + ";\n" +
        "--elsa-blue-lighter: " + LightenDarkenColor(sheetInformation[1], 229.5) + ";\n" +
        "--whitesmoke: " + sheetInformation[2] + ";\n" +
        "--light-grey: " + sheetInformation[3] + ";\n" +
        "--shadow-grey: " + sheetInformation[4] + ";\n" +
        "--open-doc-hover: " + sheetInformation[5] + ";\n" +
        "--closed-doc: " + sheetInformation[6] + ";\n" +
        "--closed-doc-hover: " + sheetInformation[7] + ";\n" +
        "--sign-doc: " + sheetInformation[8] + ";\n" +
        "--sign-doc-hover: " + sheetInformation[9] + ";\n" +
        "--draft-doc: " + sheetInformation[10] + ";\n" +
        "--draft-doc-hover: " + sheetInformation[11] + ";\n" +
        "--red: " + sheetInformation[12] + ";\n" +
        "--darkRed: " + sheetInformation[13] + ";\n" +
        "--user-info: " + sheetInformation[14] + ";\n" +
        "--modalBackground: " + hexToRgba(sheetInformation[15], 0.5) + ";\n" +
        "}\n" +
        "[data-theme=\"darkMode\"]{\n" +
        "--dark-grey:" + sheetInformation[16] + ";\n" +
        "--elsa-blue:" + sheetInformation[17] + ";\n" +
        "--elsa-blue-transparent: " + hexToRgba(sheetInformation[17], 0.1) + ";\n" +
        "--elsa-blue-lighter: " + LightenDarkenColor(sheetInformation[17], -175.75) + ";\n" +
        "--whitesmoke: " + sheetInformation[18] + ";\n" +
        "--light-grey: " + sheetInformation[19] + ";\n" +
        "--shadow-grey: " + sheetInformation[20] + ";\n" +
        "--open-doc-hover: " + sheetInformation[21] + ";\n" +
        "--closed-doc: " + sheetInformation[22] + ";\n" +
        "--closed-doc-hover: " + sheetInformation[23] + ";\n" +
        "--sign-doc: " + sheetInformation[24] + ";\n" +
        "--sign-doc-hover: " + sheetInformation[25] + ";\n" +
        "--draft-doc: " + sheetInformation[26] + ";\n" +
        "--draft-doc-hover: " + sheetInformation[27] + ";\n" +
        "--red: " + sheetInformation[28] + ";\n" +
        "--darkRed: " + sheetInformation[29] + ";\n" +
        "--user-info: " + sheetInformation[30] + ";\n" +
        "--modalBackground: " + hexToRgba(sheetInformation[31], 0.65) + ";\n" +
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

// lightens/darkens color: https://css-tricks.com/snippets/javascript/lighten-darken-color/
function LightenDarkenColor(col, amt) {

    var usePound = false;

    if (col[0] === "#") {
        col = col.slice(1);
        usePound = true;
    }

    var num = parseInt(col, 16);

    var r = (num >> 16) + amt;

    if (r > 255) r = 255;
    else if (r < 0) r = 0;

    var b = ((num >> 8) & 0x00FF) + amt;

    if (b > 255) b = 255;
    else if (b < 0) b = 0;

    var g = (num & 0x0000FF) + amt;

    if (g > 255) g = 255;
    else if (g < 0) g = 0;

    return (usePound ? "#" : "") + (g | (b << 8) | (r << 16)).toString(16);

}
