<template>
    <div>
        <transition>
            <div class="modal-mask">
                <div class="modal-wrapper">
                    <div class="modal-fade" role="dialog" id="exampleModalLong" tabindex="-1"
                         aria-labelledby="exampleModalLongTitle" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-scrollable" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4 class="modal-title" id="exampleModalLongTitle">
                                        {{ $t('TwoFakAuth.sign.sign') }}
                                    </h4>
                                </div>

                                <!-- Menu -->
                                <div class="modal-body">

                                    <!-- Page -1 (shows if user has not seen the document yet) -->
                                    <div v-if="page === -1">
                                        <div class="step">
                                            {{ $t('TwoFakAuth.sign.notSeen') }}
                                        </div>


                                        <div style="text-align: right">
                                            <button type="button" class="elsa-blue-btn"
                                                    @click="closeModal">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.close') }}
                                                </span>
                                            </button>
                                        </div>
                                    </div>

                                    <!-- Page 0 -->
                                    <div v-if="page === 0">
                                        <div class="step">
                                            {{ $t('TwoFakAuth.sign.notDone') }}
                                        </div>

                                        <ul>
                                            <li v-if="!hasKey">
                                                <div class="content-div"
                                                     style="justify-content: left; text-align: left">
                                                    {{ $t('TwoFakAuth.sign.keyPair') }}
                                                </div>
                                            </li>
                                        </ul>

                                        <div style="text-align: right">
                                            <button type="button" class="light-btn"
                                                    @click="closeModal">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.close') }}
                                                </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" @click="goToSettings">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.sign.toSettings') }}
                                                </span>
                                            </button>
                                        </div>
                                    </div>


                                    <!-- Page 1 (advanced)-->
                                    <div v-if="page === 1 && advanced">
                                        <div class="step" v-if="documents.length === 1"
                                             style="justify-content: left; text-align: left">
                                            {{ $t('TwoFakAuth.sign.sureOne') }}
                                        </div>

                                        <div class="step" v-else style="justify-content: left; text-align: left">
                                            {{ $t('TwoFakAuth.sign.sureMulti') }}
                                        </div>

                                        <div class="content-div" style="justify-content: left; text-align: left">
                                            <ul>
                                                <li v-for="(document, idx) in documents" :key="idx">
                                                    {{ document.title }}
                                                </li>
                                            </ul>
                                        </div>

                                        <div style="text-align: right">
                                            <button type="button" class="light-btn"
                                                    @click="pageBefore = page; page = 4">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.cancel') }}
                                                </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" @click="page = 2">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.continue') }}
                                                </span>
                                            </button>
                                        </div>
                                    </div>


                                    <!-- Page 1 (simple) -->
                                    <div v-if="page === 1 && !advanced">

                                        <!-- Error Messages -->
                                        <b-alert :show="showAlertSign"
                                                 style="margin-bottom: 1em">
                                            {{ $t('TwoFakAuth.fail') }} {{ statusCodeSimple }}
                                        </b-alert>


                                        <b-alert :show="showErrorSimple"
                                                 style="margin-bottom: 1em">
                                            <div>
                                                {{ $t('TwoFakAuth.serverErrorOne') }}
                                            </div>
                                            <div>
                                                {{ $t('TwoFakAuth.serverErrorTwo') }}
                                            </div>
                                        </b-alert>

                                        <!-- Sign Prompt -->
                                        <div class="step" v-if="documents.length === 1"
                                             style="justify-content: left; text-align: left">
                                            {{ $t('TwoFakAuth.sign.sureOne') }}
                                        </div>

                                        <div class="step" v-else style="justify-content: left; text-align: left">
                                            {{ $t('TwoFakAuth.sign.sureMulti') }}
                                        </div>

                                        <div class="content-div">
                                            <ul>
                                                <li v-for="(document, idx) in documents" :key="idx"
                                                    style="justify-content: left; text-align: left">
                                                    {{ document.title }}
                                                </li>
                                            </ul>
                                        </div>

                                        <!-- Buttons to switch pages -->
                                        <div style="text-align: right">
                                            <button type="button" class="light-btn"
                                                    @click="pageBefore = page; page = 4">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.cancel') }}
                                                </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" @click="signSimple">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.continue') }}
                                                </span>
                                            </button>
                                        </div>
                                    </div>

                                    <!-- Page 3 (key stuff) -->
                                    <div v-if="page === 2">
                                        <!-- Error Messages -->
                                        <b-alert :show="showAlertSign"
                                                 style="margin-bottom: 1em">
                                            {{ $t('TwoFakAuth.fail') }} <!---{{ statusCodeAdvanced }}--->
                                        </b-alert>


                                        <b-alert :show="showErrorSimple"
                                                 style="margin-bottom: 1em">
                                            <div>
                                                {{ $t('TwoFakAuth.serverErrorOne') }}
                                            </div>
                                            <div>
                                                {{ $t('TwoFakAuth.serverErrorTwo') }}
                                            </div>
                                        </b-alert>

                                        <!-- Input Key Prompt -->
                                        <div class="step">
                                            {{ $t('TwoFakAuth.sign.uploadExp') }}
                                            <b-icon id="tooltip-upl" icon="info-circle" class="my-icon"></b-icon>
                                            <b-tooltip target="tooltip-upl" triggers="hover">
                                                {{ $t('TwoFakAuth.sign.accepted') }}
                                            </b-tooltip>
                                        </div>

                                        <div style="display: flex; justify-content: center">
                                            <div class="content-div" style="width: 25em;">
                                                <b-form-file
                                                    v-model="key"
                                                    :state="Boolean(key)"
                                                    :placeholder="$t('TwoFakAuth.sign.placeholder')"
                                                    :drop-placeholder="$t('TwoFakAuth.sign.dropPlaceholder')"
                                                    accept="text/*"
                                                ></b-form-file>
                                            </div>
                                        </div>

                                        <!-- Buttons to switch pages -->
                                        <div style="text-align: right">
                                            <button type="button" class="light-btn"
                                                    @click="pageBefore = page; page = 4">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.cancel') }}
                                                </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" @click="signAdvanced">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.continue') }}
                                                </span>
                                            </button>
                                        </div>
                                    </div>

                                    <!-- Page 4 (success) -->
                                    <div v-if="page === 3">
                                        <div class="step" style="margin-top: 0">
                                            {{ $t('TwoFakAuth.sign.success') }}
                                        </div>

                                        <!-- Button to close -->
                                        <div style="text-align: right">
                                            <button type="button" class="elsa-blue-btn"
                                                    @click="closeModal()">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.close') }}
                                                </span>
                                            </button>
                                        </div>
                                    </div>

                                    <!-- Page 5 (leave?) -->
                                    <div v-if="page === 4">
                                        <div class="step" style="margin-top: 0">
                                            {{ $t('TwoFakAuth.sign.sure') }}
                                        </div>

                                        <!-- Buttons to switch pages/ close -->
                                        <div style="text-align: right">
                                            <button type="button" class="light-btn" @click="page = pageBefore">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.back') }}
                                                </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" @click="closeModal()">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.cancel') }}
                                                </span>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </transition>
    </div>
</template>

<script>
// TODO: rethink way pop up gets information about documents once it is possible to sign multiple files at at time
import {mapGetters} from "vuex";
import _ from "lodash";
import {KJUR} from 'jsrsasign';

export default {
    name: "SignPopUp",
    props: {
        documents: {
            type: Array,
            required: true
        },
        isGuest: {
            type: Boolean,
            default: false
        }
    },
    data() {
        return {
            page: 1,
            pageBefore: 0,

            key: null,
            showAlertSign: false,
        }
    },
    async mounted() {
        // checks if signature is advanced, if so checks if user has set up 2FakAuth and key
        if (this.advanced) {
            await this.$store.dispatch('fetchHasKey')
            if (!this.hasKey) {
                this.page = 0
            }
        }

        // checks if user has already looked at the document or not
        if (!this.isGuest) {
            await this.$store.dispatch('document/fetchSeen', this.$route.params.docId)
            if (!this.hasSeen) {
                this.page = -1
            }
        }
    },
    methods: {
        // makes simple signature api call
        async signSimple() {

            if (this.isGuest) {
                await this.$store.dispatch('signAsGuest', {
                    envId: this.envId,
                    docId: this.docId,
                    guestToken: this.$route.params.tokenId
                })

                // reloading document in store, so information is coherent with server information
                await this.$store.dispatch('requestGuestInfo', {
                    envId: this.envId,
                    docId: this.docId,
                    token: this.$route.params.tokenId
                })
                // goes to success page and toggles alert
                this.page = 3
                this.showAlertSign = false

                return
            }

            await this.$store.dispatch('document/simpleSignDocument', {envId: this.envId, docId: this.docId})

            // everything went fine
            if (this.statusCodeSimple === 200) {
                // reloading document in store, so information is coherent with server information
                await this.$store.dispatch('document/fetchDocumentInfo', {envId: this.envId, docId: this.docId})
                // goes to success page and toggles alert
                this.page = 3
                this.showAlertSign = false
            } else {
                // if api call got to server, but server did not response wit 'ok' shows errorCode to user
                // if api call did not go to server, shows that there are Server Network problems
                if (!this.showErrorSimple) {
                    this.showAlertSign = true
                }
            }
        },
        // checks of user owns correct private-public-keypair
        async signAdvanced() {
            await this.$store.dispatch('fetchUser')
            // gets data out of user file that contains key
            const reader = new FileReader()
            reader.readAsText(this.key)

            // getting user so there is access public key
            reader.onload = async (keyData) => {
                const privateKey = keyData.target.result
                const docId = this.documents[0].identifier

                // encrypting hashed docId with given private key
                var sig = new KJUR.crypto.Signature({"alg": "SHA256withRSA"});

                sig.init(privateKey);
                sig.updateString(docId);
                const signature = sig.sign();// decrypting hashed docId with registered public key
                var sig2 = new KJUR.crypto.Signature({'alg': 'SHA256withRSA'});
                console.log(this.publicKey)
                sig2.init(this.publicKey);
                sig2.updateString(docId);
                const isValid = sig2.verify(signature);
                if (isValid) {
                    await this.$store.dispatch('document/advancedSignDocument', {
                        envId: this.envId,
                        docId: this.docId,
                        signature: signature
                    })
                }

                // everything went fine
                if (this.statusCodeAdvanced === 200) {
                    // reloading document in store, so information is coherent with server information
                    await this.$store.dispatch('document/fetchDocumentInfo', {envId: this.envId, docId: this.docId})
                    // goes to success page and toggles alert
                    this.page = 3
                    this.showAlertSign = false
                } else {
                    // if api call got to server, but server did not response wit 'ok' shows errorCode to user
                    // if api call did not go to server, shows that there are Server Network problems
                    if (!this.showErrorAdvanced) {
                        this.showAlertSign = true
                    }
                }
            }
        },
        // send user to settings page
        goToSettings() {
            this.$emit('signTrigger', false);
            this.page = 1
            this.$router.push('/' + this.$i18n.locale + '/user')
        },
        // closes the pop up
        closeModal() {
            this.$emit('signTrigger');
            this.page = 1
            this.showAlertSign = false
            this.showAlertCode = false
        }
    },
    computed: {
        ...mapGetters({
            statusCodeSimple: 'document/getSimpleSignStatus',
            errorSimple: 'document/getErrorSimpleSignDocument',
            statusCodeAdvanced: 'document/getAdvancedSignStatus',
            errorAdvanced: 'document/getErrorAdvancedSignDocument',

            hasKey: 'getHasKey',
            hasSeen: 'document/getSeen',

            user: 'getUser'
        }),
        publicKey() {
            return this.user.publicKey
        },
        // gives back if advanced signature is needed (if false -> simple signature is needed)
        advanced() {
            return this.documents[0].signatureType === 'ADVANCED_SIGNATURE'
        },
        showErrorSimple: {
            get() {
                return !_.isEmpty(this.errorSimple)
            }
        },
        showErrorAdvanced: {
            get() {
                return !_.isEmpty(this.errorAdvanced)
            }
        },
        docId() {
            return this.$route.params.docId;
        },
        envId() {
            return this.$route.params.envId;
        }
    },
    watch: {
        // checks if input file has correct type
        key(newKey) {
            if (newKey && !newKey.type.startsWith("text/")) {
                this.$nextTick(() => {
                    this.key = null;
                })
            }
        }
    }

}
</script>

<style scoped src="../../assets/css/signModals.css">
</style>
