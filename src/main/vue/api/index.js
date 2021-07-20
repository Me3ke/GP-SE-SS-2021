import auth from "./authAPI";
import user from "./userAPI";
import userData from "@/main/vue/api/userDataAPI";
import documentAPI from "@/main/vue/api/documentAPI";
import envelope from "./envelopeAPI";
import publicKeyAPI from "@/main/vue/api/publicKeyAPI";
import documentUploadAPI from "@/main/vue/api/documentUploadAPI";
import emailTemplateAPI from "@/main/vue/api/emailTemplateAPI";
import impressumAPI from "@/main/vue/api/impressumAPI";
import documentSettingsAPI from "@/main/vue/api/documentSettingsAPI";
import trustedDomainAPI from "@/main/vue/api/trustedDomainAPI";

export default {
    auth,
    user,
    envelope,
    userData,
    documentAPI,
    publicKeyAPI,
    documentUploadAPI,
    emailTemplateAPI,
    impressumAPI,
    documentSettingsAPI,
    trustedDomainAPI
}
