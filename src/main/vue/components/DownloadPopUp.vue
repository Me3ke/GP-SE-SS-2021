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

                                    <!-- Page 1 (advanced)-->
                                    <div v-if="page === 1">
                                        <div class="step" style="margin-top: 0">
                                            {{ $t('DownloadDoc.where') }}
                                        </div>

                                        <div style="text-align: right">
                                            <button type="button" class="light-btn" @click="closeModal">
                                                <span class="button-txt">
                                                    {{ $t('DownloadDoc.cancel') }}
                                                </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" @click="download">
                                                <span class="button-txt">
                                                    {{ $t('DownloadDoc.download') }}
                                                </span>
                                            </button>
                                        </div>
                                    </div>

                                    <!-- Page 2 (two fac Auth) -->
                                    <div v-if="page === 2">
                                        <div class="step" style="margin-top: 0">
                                            {{ $t('DownloadDoc.success') }}
                                        </div>

                                        <div style="text-align: right">
                                            <button type="button" class="elsa-blue-btn" @click="closeModal">
                                                <span class="button-txt">
                                                    {{ $t('DownloadDoc.close') }}
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
export default {
    name: "DownloadPopUp",
    props: ['docId', 'envId'],
    data() {
        return {
            page: 1,
            path: ' ',
            file1: null
        }
    },
    methods: {
        async download() {
            await this.$store.dispatch('document/downloadDocument', {envId: this.envId, docId: this.docId})
            this.page = 2
        },
        closeModal() {
            this.$emit('closedDownload');
            this.page = 1
        }
    }
}
</script>

<style scoped src="../assets/css/signModals.css">
</style>
