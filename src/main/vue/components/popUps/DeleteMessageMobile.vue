<template>
    <div>
        <transition>
            <div class="modal-mask">
                <div class="modal-wrapper">
                    <div class="modal-fade" role="dialog" id="exampleModalLong" tabindex="-1"
                         aria-labelledby="exampleModalLongTitle" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-scrollable" role="document">
                            <div class="modal-content">
                                <!-- Menu -->
                                <div class="modal-body">

                                    <!-- Error Messages -->
                                    <b-alert :show="showDeleteError"
                                             style="margin-bottom: 1em">
                                        {{ $t('MessagePage.deleteError') }}
                                    </b-alert>

                                    <div>
                                        <div class="step">
                                            {{ $t('MessagePage.sureDelete') }}
                                        </div>


                                        <div style="text-align: right">

                                            <button type="button" class="light-btn"
                                                    @click="closeModal">
                                                <span class="button-txt">
                                                    {{ $t('MessagePage.cancel') }}
                                                </span>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" @click="deleteMsg">
                                                <span class="button-txt">
                                                    {{ $t('MessagePage.delete') }}
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
import {mapGetters} from "vuex";

export default {
    name: "DeleteMessageMobile",
    data() {
        return {
            showDeleteError: false
        }
    },
    methods: {
        // deletes current message
        async deleteMsg() {
            await this.$store.dispatch('messages/patchDeleteMessage')
            if (this.hasError()) {
                this.showDeleteError = true
            } else {
                await this.$store.dispatch('messages/patchChangeSelectedMsg', {})
                await this.$store.dispatch('messages/fetchMessages')
            }
        },
        hasError() {
            return this.deleteRes.status !== 200
        },
        // closes the pop up
        closeModal() {
            this.$emit('closeTrigger');
            this.showDeleteError = false
        }
    },
    computed: {
        ...mapGetters({
            deleteRes: 'messages/getDeleteResponse'
        })
    }
}
</script>

<style scoped src="../../assets/css/signModals.css">
</style>
