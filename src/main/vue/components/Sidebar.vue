<template>
    <div>

        <SignPopUp v-if="showSign" :documents="[document]" :is-guest="isGuest" @signTrigger="toggleSign()"></SignPopUp>

        <ProofreadPopUp v-if="showProofread" :documents="[document]" :is-guest="isGuest"
                        @readTrigger="toggleRead()"></ProofreadPopUp>

        <upload-new-version-button @closeModal="showNewVersionPopup = false" @close="updateShowVersionPopUp" @closePopUp="updateShowVersionPopUp" :clicked="showNewVersionPopup" :docID="docId" :envID="envId" :document="document"></upload-new-version-button>

        <!-- Closed -->
        <b-sidebar v-if="isClosed" visible id="mini-sidebar" aria-labelledby="sidebar-title-closed" no-header right>
            <b-list-group>
                <b-list-group-item id="sidebar-title-closed menu-toggle" style="background-color: var(--elsa-blue);"
                                   class="mini-list" v-b-toggle.menu>
                    <b-icon icon="list" class="my-icon" id="toggler"></b-icon>
                </b-list-group-item>

                <!-- Proofread -->
                <b-list-group-item v-if="(reader && readTurn) || (reader && read)" @click="toggleRead(true)"
                                   :class="read ? 'inactive-item mini-list' : 'mini-list'">
                    <b-icon icon="eyeglasses" class="my-icon"></b-icon>
                </b-list-group-item>

                <!-- Sign -->
                <b-list-group-item v-if="(signatory && signTurn) || (signatory && signed)" @click="toggleSign(true)"
                                   :class="signed ? 'inactive-item mini-list' : 'mini-list'">
                    <b-icon icon="pen" class="my-icon"></b-icon>
                </b-list-group-item>

                <!-- Comments -->
                <b-list-group-item v-if="!isGuest" @click="goToComments" class="mini-list">
                    <b-icon class="my-icon" stacked icon="chat-right-dots"></b-icon>
                </b-list-group-item>

                <!-- Protocol -->
                <b-list-group-item v-if="!isGuest" @click=" goToProtocol
                " class="mini-list">
                    <b-icon class="my-icon" stacked icon="journal-check"></b-icon>
                </b-list-group-item>

                <!-- History -->
                <b-list-group-item v-if="!isGuest" @click="showHistory" class="mini-list">
                    <b-icon icon="clock-history" class="my-icon"></b-icon>
                </b-list-group-item>

                <!-- New Version -->
                <b-list-group-item v-if="isOwner && document.state !== 'CLOSED'" @click="toggleNewVersion"
                                   class="mini-list">
                    <b-icon icon="file-earmark-plus" class="my-icon"></b-icon>
                </b-list-group-item>

                <!-- Settings -->
                <b-list-group-item v-if="isOwner" @click="goToSettings()" class="mini-list">
                    <b-icon icon="gear-fill" class="my-icon"></b-icon>
                </b-list-group-item>

                <!-- Metadata -->
                <b-list-group-item v-if="!isGuest" @click="showMeta()" class="mini-list">
                    <b-icon icon="info-circle" class="my-icon"></b-icon>
                </b-list-group-item>
            </b-list-group>
        </b-sidebar>

        <!-- Light Mode (open)-->
        <b-sidebar v-if="theme === ''" aria-labelledby="sidebar-title-light" no-header id="menu" right backdrop>
            <b-list-group>

                <b-list-group-item id="sidebar-title-light"
                                   style="text-align: center; background-color: var(--elsa-blue); padding: 1em 1.25em;">
                    <b-img v-if="darkEmpty" :src="elsaDark" class="logo" :alt="$t('Header.logo')"></b-img>
                    <img v-else
                         :src="getDarkSource()" class="logo"
                         :alt="$t('Header.logo')"
                         style="margin-left: 2em">
                </b-list-group-item>

                <!-- Proofread -->
                <b-list-group-item v-if="(reader && readTurn) || (reader && read)" @click="toggleRead(true)"
                                   :class="read ? 'inactive-item' : ''">
                    <b-icon icon="eyeglasses" class="my-icon"></b-icon>
                    <span v-if="read"> {{ $t('DocumentPage.didRead') }} </span>
                    <span v-else> {{ $t('DocumentPage.doRead') }} </span>
                </b-list-group-item>

                <!-- Sign -->
                <b-list-group-item v-if="(signatory && signTurn) || (signatory && signed)" @click="toggleSign(true)"
                                   :class="signed ? 'inactive-item' : ''">
                    <b-icon icon="pen" class="my-icon"></b-icon>
                    <span v-if="signed"> {{ $t('DocumentPage.didSign') }} </span>
                    <span v-else> {{ $t('DocumentPage.doSign') }} </span>
                </b-list-group-item>

                <!-- Comments -->
                <b-list-group-item v-if="!isGuest" @click="goToComments">
                    <b-icon class="my-icon" stacked icon="chat-right-dots"></b-icon>
                    <span> {{ $t('DocumentPage.comments') }} </span>
                </b-list-group-item>

                <!-- Protocol -->
                <b-list-group-item v-if="!isGuest" @click="goToProtocol">
                    <b-icon class="my-icon" stacked icon="journal-check"></b-icon>
                    <span> {{ $t('DocumentPage.protocol') }} </span>
                </b-list-group-item>

                <!-- History -->
                <b-list-group-item v-if="!isGuest" @click="showHistory">
                    <b-icon icon="clock-history" class="my-icon"></b-icon>
                    <span> {{ $t('DocumentPage.history') }} </span>
                </b-list-group-item>

                <!-- New Version -->
                <b-list-group-item v-if="isOwner && document.state !== 'CLOSED'" @click="toggleNewVersion">
                    <b-icon icon="file-earmark-plus" class="my-icon"></b-icon>
                    <span> {{ $t('DocumentPage.newVersion') }} </span>
                </b-list-group-item>

                <!-- Settings -->
                <b-list-group-item v-if="isOwner" @click="goToSettings()">
                    <b-icon icon="gear-fill" class="my-icon"></b-icon>
                    <span> {{ $t('DocumentPage.settings') }} </span>
                </b-list-group-item>

                <!-- Metadata -->
                <b-list-group-item v-if="!isGuest" @click="showMeta()">
                    <b-icon icon="info-circle" class="my-icon"></b-icon>
                    <span v-if="!metaShown"> {{ $t('DocumentPage.meta.metaShow') }} </span>
                    <span v-else> {{ $t('DocumentPage.meta.metaNo') }} </span>
                </b-list-group-item>

            </b-list-group>
        </b-sidebar>

        <!-- Dark Mode (open)-->
        <b-sidebar v-else aria-labelledby="sidebar-title-dark" no-header id="menu" right backdrop>
            <b-list-group>
                <b-list-group-item id="sidebar-title-dark"
                                   style="text-align: center; background-color: var(--elsa-blue); padding: 1em 1.25em;">
                    <b-img v-if="lightEmpty" :src="elsaLight" class="logo"
                           :alt="$t('Header.logo')"></b-img>
                    <img v-else
                         :src="getLightSource()" class="logo"
                         :alt="$t('Header.logo')"
                         style="margin-left: 2em">
                </b-list-group-item>

                <!-- Proofread -->
                <b-list-group-item v-if="(reader && readTurn) || (reader && read)" @click="toggleRead(true)"
                                   :class="read ? 'inactive-item' : ''">
                    <b-icon icon="eyeglasses" class="my-icon"></b-icon>
                    <span v-if="read"> {{ $t('DocumentPage.didRead') }} </span>
                    <span v-else> {{ $t('DocumentPage.doRead') }} </span>
                </b-list-group-item>

                <!-- Sign -->
                <b-list-group-item v-if="(signatory && signTurn) || (signatory && signed)" @click="toggleSign(true)"
                                   :class="signed ? 'inactive-item' : ''">
                    <b-icon icon="pen" class="my-icon"></b-icon>
                    <span v-if="signed"> {{ $t('DocumentPage.didSign') }} </span>
                    <span v-else> {{ $t('DocumentPage.doSign') }} </span>
                </b-list-group-item>

                <!-- Comments -->
                <b-list-group-item v-if="!isGuest" @click="goToComments">
                    <b-icon class="my-icon" stacked icon="chat-right-dots"></b-icon>
                    <span> {{ $t('DocumentPage.comments') }} </span>
                </b-list-group-item>

                <!-- Protocol -->
                <b-list-group-item v-if="!isGuest" @click="goToProtocol">
                    <b-icon class="my-icon" stacked icon="journal-check"></b-icon>
                    <span> {{ $t('DocumentPage.protocol') }} </span>
                </b-list-group-item>

                <!-- History -->
                <b-list-group-item v-if="!isGuest" @click="showHistory">
                    <b-icon icon="clock-history" class="my-icon"></b-icon>
                    <span> {{ $t('DocumentPage.history') }} </span>
                </b-list-group-item>

                <!-- New Version -->
                <b-list-group-item v-if="isOwner &&  document.state !== 'CLOSED'" @click="toggleNewVersion">
                    <b-icon icon="file-earmark-plus" class="my-icon"></b-icon>
                    <span> {{ $t('DocumentPage.newVersion') }} </span>
                </b-list-group-item>

                <!-- Settings -->
                <b-list-group-item v-if="isOwner" @click="goToSettings()">
                    <b-icon icon="gear-fill" class="my-icon"></b-icon>
                    <span> {{ $t('DocumentPage.settings') }} </span>
                </b-list-group-item>

                <!-- Metadata -->
                <b-list-group-item v-if="!isGuest" @click="showMeta()">
                    <b-icon icon="info-circle" class="my-icon"></b-icon>
                    <span v-if="!metaShown"> {{ $t('DocumentPage.meta.metaShow') }} </span>
                    <span v-else> {{ $t('DocumentPage.meta.metaNo') }} </span>
                </b-list-group-item>
            </b-list-group>
        </b-sidebar>
    </div>
</template>
<script>
import {mapGetters} from "vuex";
import SignPopUp from "@/main/vue/components/popUps/SignPopUp";
import ProofreadPopUp from "@/main/vue/components/popUps/ProofreadPopUp";
import UploadNewVersionButton from "@/main/vue/components/uploadNewVersionButton";
import _ from "lodash";

export default {
    name: "Sidebar",
    components: {UploadNewVersionButton, ProofreadPopUp, SignPopUp},
    props: {
        isGuest: {
            type: Boolean,
            default: false
        }
    },
    data() {
        return {
            showProofread: false,
            showSign: false,
            showDownload: false,
            showUploadNewVersion: false,
            showNewVersionPopup: false,

            elsaLight: require('../assets/logos/ELSA_medium.svg'),
            elsaDark: require('../assets/logos/ELSA_medium_darkmode.svg'),

            isClosed: true,
            metaShown: false
        }
    },
    methods: {
        toggleRead(val) {
            if (this.read) {
                val = false
            }
            this.showProofread = val
            this.$emit('triggerOverflow')
            this.$root.$emit('bv::toggle::collapse', 'menu')
            this.$store.dispatch('document/setSeenFalse')
        },
        toggleSign(val) {
            if (this.signed) {
                val = false
            }
            this.showSign = val
            this.$emit('triggerOverflow')
            this.$root.$emit('bv::toggle::collapse', 'menu')
            this.$store.dispatch('document/setSeenFalse')
        },
        toggleDownload() {
            this.showDownload = !this.showDownload
            this.$emit('triggerOverflow')
            this.$store.dispatch('document/setSeenFalse')
        },
        goToComments() {
            this.$router.push({name: 'comments', params: {envId: this.envId, docId: this.docId}})
        },
        goToProtocol() {
            this.$router.push({name: 'protocol', params: {envId: this.envId, docId: this.docId}})
        },
        showHistory() {
            this.$router.push({name: 'history', params: {envId: this.envId, docId: this.docId}})
        },
        toggleNewVersion() {
            this.showUploadNewVersion = true //!this.showUploadNewVersion
            this.showNewVersionPopup = this.showUploadNewVersion;


            this.$root.$emit('bv::toggle::collapse', 'menu')
            this.$emit('triggerOverflow')

            //this.$bvModal.show('modal-' + this.docId + 'a')
        },
        // todo sidebar menu collapse
        updateShowVersionPopUp(value) {
            this.showNewVersionPopup = value
        },

        goToSettings() {
            this.$store.dispatch('documentSettings/fetchEnvelopeSettings', {envId: this.envId}).then(() => this.$router.push({name: 'settings', params: {envId: this.envId}}));
        },
        showMeta() {
            this.metaShown = !this.metaShown
            this.$emit('detailTrigger')
        },
        getLightSource() {
            if (this.logoLightType === 'svg') {
                return 'data:image/svg+xml;base64,' + this.logoLight
            } else {
                return 'data:image/' + this.logoLightType + ';base64,' + this.logoLight
            }
        },
        getDarkSource() {
            if (this.logoDarkType === 'svg') {
                return 'data:image/svg+xml;base64,' + this.logoDark
            } else {
                return 'data:image/' + this.logoDarkType + ';base64,' + this.logoDark
            }
        },
        tokenId() {
            return this.$route.params.tokenId;
        },
    },
    computed: {
        ...mapGetters({
            theme: 'theme/getTheme',
            newDocumentId: 'document/getNewDocumentId',
            newDocumentError: 'document/getNewDocumentError',
            newDocumentStatus: 'document/getEditDocumentStatus',
            logoLight: 'theme/getLightLogo',
            logoDark: 'theme/getDarkLogo',
            logoLightType: 'theme/getLightLogoType',
            logoDarkType: 'theme/getDarkLogoType',

            document: 'document/getDocumentInfo',
            guestSignatory: 'getGuestSignatory'
        }),
        isOwner() {
            if (this.document.owner) {
                return this.document.owner.username === this.$store.state.auth.username
            }
            return false
        },
        reader() {
            if (this.isGuest) {
                return this.guestSignatory.reader
            } else {
                return this.document.reader
            }
        },
        read() {
            if (this.isGuest) {
                return this.guestSignatory.read
            } else {
                return this.document.read
            }
        },
        readTurn() {
            if (this.isGuest) {
                return this.guestSignatory.turnToReview
            } else {
                return this.document.turnToReview
            }
        },
        signatory() {
            if (this.isGuest) {
                return this.guestSignatory.signatory
            } else {
                return this.document.signatory
            }
        },
        signed() {
            if (this.isGuest) {
                return this.guestSignatory.signed
            } else {
                return this.document.signed
            }
        },
        signTurn() {
            if (this.isGuest) {
                return this.guestSignatory.turnToSign
            } else {
                return this.document.turnToSign
            }
        },
        docId() {
            return this.$route.params.docId
        },
        envId() {
            return this.$route.params.envId;
        },
        uploadNewDocumentError() {
            return !_.isEmpty(this.newDocumentError)
        },
        lightEmpty() {
            return this.logoLight === ""
        },
        darkEmpty() {
            return this.logoDark === ""
        }
    }
}
</script>

<style scoped>
#toggler {
    fill: var(--whitesmoke);
}

.list-group-item {
    background-color: var(--whitesmoke);
    border-color: var(--light-grey);
    padding: 1.5em 1.25em;
}

.mini-list.list-group-item {
    padding: 0.5em 0;
    text-align: center;
}

.list-group-item:hover {
    background-color: var(--light-grey);
    cursor: pointer;
}

.inactive-item:hover {
    cursor: default;
    background-color: var(--whitesmoke);
}

.my-icon {
    fill: var(--elsa-blue);
    height: 1.2em;
    width: auto;
    padding-right: 0.3em;
}

.mini-list.my-icon {
    padding: 0;
}

.logo {
    height: 2em;
    width: auto;
}

#mini-sidebar {
    z-index: -1 !important;
}


/* Settings for differently sized screens */
@media (max-width: 575.98px) and (min-height: 370px) {
    .my-icon {
        font-size: 0.5em;
    }
}

@media (max-width: 575.98px) and (max-height: 369.98px) {
    .my-icon {
        font-size: 0.5em;
    }
}

@media (min-width: 576px) and (max-width: 767.98px) {
    .my-icon {
        font-size: 0.7em;
    }
}

@media (min-width: 768px) and (max-width: 991.98px) and (max-height: 499.98px) {
    .my-icon {
        font-size: 0.8em;
    }
}

@media (min-width: 768px) and (max-width: 991.98px) and (min-height: 500px) {
    .my-icon {
        font-size: 0.9em;
    }
}

@media (min-width: 992px) {
    .my-icon {
        font-size: 1.2em;
    }
}
</style>
