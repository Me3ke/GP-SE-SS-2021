

export default function signDocument(prvKey){
    var rs = require("jsrsasign");
    var sig = new KJUR.crypto.Signature({"alg": "SHA512withRSA"});
    sig.ini
    sig.init(prvKey);
    sig.updateString('aaa');
    var hSigVal = sig.sign();
}