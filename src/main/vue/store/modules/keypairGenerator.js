
    export default function generateKeyPair(){
        var rs = require("jsrsasign");

        var kp = rs.KEYUTIL.generateKeypair("RSA", "512");
        var prv = kp.prvKeyObj;
        var pub = kp.pubKeyObj;
        var prvpem = rs.KEYUTIL.getPEM(prv, "PKCS8PRV");
        var pubpem = rs.KEYUTIL.getPEM(pub, "PKCS8PUB");

        return ({"privateKey": prvpem, "publicKey": pubpem});
    }
