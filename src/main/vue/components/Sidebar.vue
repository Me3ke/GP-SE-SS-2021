<template>
    <div>

        <SignPopUp v-if="showSign" :documents="[document]" @signTrigger="toggleSign()"></SignPopUp>

        <ProofreadPopUp v-if="showProofread" :documents="[document]" @readTrigger="toggleRead()"></ProofreadPopUp>

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

                <!-- Protocol -->
                <b-list-group-item v-if="protocol" @click="goToProtocol" class="mini-list">
                    <b-icon class="my-icon" stacked icon="journal-check"></b-icon>
                </b-list-group-item>

                <!-- History -->
                <b-list-group-item v-if="history" @click="showHistory" class="mini-list">
                    <b-icon icon="clock-history" class="my-icon"></b-icon>
                </b-list-group-item>

                <!-- New Version -->
                <b-list-group-item v-if="isOwner" @click="toggleNewVersion" class="mini-list">
                    <b-icon icon="file-earmark-plus" class="my-icon"></b-icon>
                </b-list-group-item>

                <!-- Settings -->
                <b-list-group-item v-if="isOwner" @click="goToSettings()" class="mini-list">
                    <b-icon icon="gear-fill" class="my-icon"></b-icon>
                </b-list-group-item>
            </b-list-group>
        </b-sidebar>

        <!-- Light Mode (open)-->
        <b-sidebar v-if="theme === ''" aria-labelledby="sidebar-title-light" no-header id="menu" right backdrop>
            <b-list-group>

                <b-list-group-item id="sidebar-title-light"
                                   style="text-align: center; background-color: var(--elsa-blue); padding: 1em 1.25em;">
                    <b-img :src="logoDarkMode" class="logo" :alt="$t('Header.logo')"></b-img>
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

                <!-- Protocol -->
                <b-list-group-item v-if="protocol" @click="goToProtocol">
                    <b-icon class="my-icon" stacked icon="journal-check"></b-icon>
                    <span> {{ $t('DocumentPage.protocol') }} </span>
                </b-list-group-item>

                <!-- History -->
                <b-list-group-item v-if="history" @click="showHistory">
                    <b-icon icon="clock-history" class="my-icon"></b-icon>
                    <span> {{ $t('DocumentPage.history') }} </span>
                </b-list-group-item>

                <!-- New Version -->
                <b-list-group-item v-if="isOwner" @click="toggleNewVersion">
                    <b-icon icon="file-earmark-plus" class="my-icon"></b-icon>
                    <span> {{ $t('DocumentPage.newVersion') }} </span>
                </b-list-group-item>

                <!-- Settings -->
                <b-list-group-item v-if="isOwner" @click="goToSettings()">
                    <b-icon icon="gear-fill" class="my-icon"></b-icon>
                    <span> {{ $t('DocumentPage.settings') }} </span>
                </b-list-group-item>
            </b-list-group>
        </b-sidebar>

        <!-- Dark Mode (open)-->
        <b-sidebar v-else aria-labelledby="sidebar-title-dark" no-header id="menu" right backdrop>
            <b-list-group>
                <b-list-group-item id="sidebar-title-dark"
                                   style="text-align: center; background-color: var(--elsa-blue); padding: 1em 1.25em;">
                    <b-img :src="logoLightMode" class="logo" :alt="$t('Header.logo')"></b-img>
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

                <!-- Protocol -->
                <b-list-group-item v-if="protocol" @click="goToProtocol">
                    <b-icon class="my-icon" stacked icon="journal-check"></b-icon>
                    <span> {{ $t('DocumentPage.protocol') }} </span>
                </b-list-group-item>

                <!-- History -->
                <b-list-group-item v-if="history" @click="showHistory">
                    <b-icon icon="clock-history" class="my-icon"></b-icon>
                    <span> {{ $t('DocumentPage.history') }} </span>
                </b-list-group-item>

                <!-- New Version -->
                <b-list-group-item v-if="isOwner" @click="toggleNewVersion">
                    <b-icon icon="file-earmark-plus" class="my-icon"></b-icon>
                    <span> {{ $t('DocumentPage.newVersion') }} </span>
                </b-list-group-item>

                <!-- Settings -->
                <b-list-group-item v-if="isOwner" @click="goToSettings()">
                    <b-icon icon="gear-fill" class="my-icon"></b-icon>
                    <span> {{ $t('DocumentPage.settings') }} </span>
                </b-list-group-item>
            </b-list-group>
        </b-sidebar>
    </div>
</template>
<script>
import {mapGetters} from "vuex";
import SignPopUp from "@/main/vue/components/popUps/SignPopUp";
import ProofreadPopUp from "@/main/vue/components/popUps/ProofreadPopUp";

export default {
    name: "Sidebar",
    components: {ProofreadPopUp, SignPopUp},
    data() {
        return {
            // TODO: get Info with api
            protocol: true,
            history: true,

            showProofread: false,
            showSign: false,
            showDownload: false,

            logoDarkMode: require('../assets/logos/ELSA_medium_darkmode.svg'),
            logoLightMode: require('../assets/logos/ELSA_medium.svg'),

            isClosed: true
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
        },
        toggleSign(val) {
            if (this.signed) {
                val = false
            }
            this.showSign = val
            this.$emit('triggerOverflow')
            this.$root.$emit('bv::toggle::collapse', 'menu')
        },
        toggleDownload() {
            this.showDownload = !this.showDownload
            this.$emit('triggerOverflow')
        },
        goToProtocol() {
            this.$router.push({name: 'protocol', params: {envId: this.envId, docId: this.docId}})
        },
        //TODO: add possibility to look at history
        showHistory() {
        },
        //TODO: add uploading of new version here
        toggleNewVersion() {
        },
        // TODO. add router push to settings site of document
        goToSettings() {
        }
    },
    computed: {
        ...mapGetters({
            theme: 'theme/getTheme',
            document: 'document/getDocument'
        }),
        isOwner() {
            if (this.document.owner) {
                return this.document.owner.email === this.$store.state.auth.username
            }
            return false
        },
        reader() {
            return this.document.reader
        },
        read() {
            return this.document.read
        },
        readTurn() {
            return this.document.turnToReview
        },
        signatory() {
            return this.document.signatory
        },
        signed() {
            return this.document.signed
        },
        signTurn() {
            return this.document.turnToSign
        },
        docId() {
            return this.$route.params.docId
        },
        envId() {
            return this.$route.params.envId
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
    padding: 2em 1.25em;
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
