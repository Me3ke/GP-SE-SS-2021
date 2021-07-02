export default function savePrivateKeyToLink(privateKeyAsString) {
    var link = document.createElement('a');
    link.download = 'privateKey.txt';
    var blob = new Blob([privateKeyAsString], {type: 'text/plain'});
    link.href = window.URL.createObjectURL(blob);
    return link;

}