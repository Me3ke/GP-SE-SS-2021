import auth from "./authAPI";
import user from "./userAPI";
import userData from "@/main/vue/api/userDataAPI";
import documentAPI from "@/main/vue/api/documentAPI";
import envelope from "./envelopeAPI";
import publicKeyAPI from "@/main/vue/api/publicKeyAPI";
import documentUploadAPI from "@/main/vue/api/documentUploadAPI";

export default {
    auth,
    user,
    envelope,
    userData,
    documentAPI,
    publicKeyAPI,
    documentUploadAPI,
}
