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
                                        {{ $t('TwoFakAuth.read.read') }}
                                    </h4>
                                </div>

                                <!-- Menu -->
                                <div class="modal-body">

                                    <!-- Page 1 (advanced)-->
                                    <div v-if="page === 1">
                                        <b-alert :show="showAlert"
                                                 style="margin-bottom: 1em">
                                            {{ $t('TwoFakAuth.read.fail') }} {{ statusCode }}
                                        </b-alert>

                                        <b-alert :show="showErrorReview"
                                                 style="margin-bottom: 1em">
                                            <div>
                                                {{ $t('TwoFakAuth.serverErrorOne') }}
                                            </div>
                                            <div>
                                                {{ $t('TwoFakAuth.serverErrorTwo') }}
                                            </div>
                                        </b-alert>

                                        <div class="step" v-if="documents.length === 1">
                                            {{ $t('TwoFakAuth.read.sureOne') }}
                                        </div>

                                        <div class="step" v-else>
                                            {{ $t('TwoFakAuth.read.sureMulti') }}
                                        </div>

                                        <div class="content-div">
                                            <ul>
                                                <li v-for="(document, idx) in documents" :key="idx">
                                                    {{ document.title }}
                                                </li>
                                            </ul>
                                        </div>

                                        <div style="text-align: right">
                                            <button type="button" class="light-btn"
                                                    @click="pageBefore = page; page = 3">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.cancel') }}
                                                </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" @click="proofread">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.continue') }}
                                                </span>
                                            </button>
                                        </div>
                                    </div>

                                    <!-- Page 2 (success) -->
                                    <div v-if="page === 2">
                                        <div class="step" style="margin-top: 0">
                                            {{ $t('TwoFakAuth.read.success') }}
                                        </div>

                                        <div style="text-align: right">
                                            <button type="button" class="elsa-blue-btn"
                                                    @click="closeModal()">
                                                <span class="button-txt">
                                                    {{ $t('TwoFakAuth.close') }}
                                                </span>
                                            </button>
                                        </div>
                                    </div>

                                    <!-- Page 3 (leave?) -->
                                    <div v-if="page === 3">
                                        <div class="step" style="margin-top: 0">
                                            {{ $t('TwoFakAuth.read.sure') }}
                                        </div>

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
// TODO: rethink way pop up gets information about documents once it is possible to review multiple files at at time
import {mapGetters} from "vuex";
import _ from "lodash";

export default {
    name: "ProofreadPopUp",
    props: {
        documents: {
            type: Array,
            required: true
        }
    },
    data() {
        return {
            page: 1,
            pageBefore: 0,
            showAlert: false
        }
    },
    methods: {
        // TODO: make adaptions once it is possible to review multiple files at at time
        async proofread() {
            await this.$store.dispatch('document/reviewDocument', {docId: this.docId})

            // everything went fine
            if (this.statusCode === 200) {
                // reloading document in store, so information is coherent with server information
                await this.$store.dispatch('document/fetchDocument', {envId: this.envId, docId: this.docId})
                this.page += 1
                this.showAlert = false
            } else {
                if (!this.showErrorReview) {
                    this.showAlert = true
                }
            }
        },
        closeModal() {
            this.$emit('readTrigger');
            this.page = 1
        }
    },
    computed: {
        ...mapGetters({
            statusCode: 'document/getReviewStatus',
            errorReview: 'document/getErrorReviewDocument'
        }),
        docId() {
            return this.$route.params.docId;
        },
        envId() {
            return this.$route.params.envId;
        },
        showErrorReview: {
            get() {
                return !_.isEmpty(this.errorReview)
            }
        }
    }
}
</script>

<style scoped src="../../assets/css/signModals.css">
</style>

