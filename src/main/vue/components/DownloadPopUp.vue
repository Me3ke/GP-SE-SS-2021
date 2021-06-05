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
                                        {{ $t('DownloadDoc.heading') }}
                                    </h4>
                                </div>

                                <!-- Menu -->
                                <div class="modal-body">

                                    <!-- Page 1 -->
                                    <div v-if="page === 1">
                                        <div v-if="isProtocol" class="step" style="margin-top: 0">
                                            {{ $t('DownloadProtocol.sure') }}
                                        </div>
                                        <div v-else class="step" style="margin-top: 0">
                                            {{ $t('DownloadDoc.sure') }}
                                        </div>

                                        <div style="text-align: right">
                                            <b-button type="button" class="light-btn"
                                                      @click="closeModal">
                                                <span class="button-txt">
                                                   {{ $t('DownloadDoc.cancel') }}
                                                </span>
                                            </b-button>
                                            <b-button v-if="isProtocol" type="button" class="elsa-blue-btn"
                                                      @click="closeModal"
                                                      :href="'/api/documents/' + docId + '/protocol/download'">
                                                <span class="button-txt">
                                                     {{ $t('DownloadDoc.download') }}
                                                </span>
                                            </b-button>
                                            <b-button v-else type="button" class="elsa-blue-btn"
                                                      @click="closeModal"
                                                      :href="'/api/user/' + $store.state.auth.username + '/envelopes/' + envId + '/documents/' + docId + '/download'">
                                                <span class="button-txt">
                                                     {{ $t('DownloadDoc.download') }}
                                                </span>
                                            </b-button>
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
export default {
    name: "DownloadPopUp",
    props: {
        'docId': {
            type: [Number, String]
        },
        'envId': {
            type: Number,
            default: 0
        },
        'isProtocol': {
            type: Boolean,
            default: false
        }
    },
    data() {
        return {
            page: 1,
            path: ' ',
            file1: null
        }
    },
    methods: {
        closeModal() {
            this.$emit('closedDownload');
            this.page = 1
        }
    }
}
</script>

<style scoped src="../assets/css/signModals.css">
</style>
