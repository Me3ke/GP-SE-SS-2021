<template>
    <b-container class="container" style="margin-top: 0">
        <quill-editor
            ref="editor"
            class="editor"
            v-model="text"
            :options="editorOption"
        >
        </quill-editor>
        <b-list-group-item class="d-flex justify-content-end align-items-center"
                           style=" padding-top: 0.1em; padding-bottom: 0.1em;">

            <transition name="saved">
                            <span v-if="showSave" style="margin-right: 1em;">
                                {{ $t('Settings.saved') }}
                            </span>
            </transition>

            <b-button
                class="light-btn"
                v-b-modal="'modal-preview'"
                style="margin-top: 0.2em; margin-bottom: 0.1em; margin-right: 1em;"
            >
                <span class="button-txt">
                    Preview
                </span>
            </b-button>

            <b-button class="elsa-blue-btn"
                      @click="saveImpressum"
                      style="margin-top: 0.2em; margin-bottom: 0.1em;"

            >
                        <span class="button-txt">
                            Save
                        </span>
            </b-button>
        </b-list-group-item>


        <!--- Preview --->
        <b-modal
            class="modal-class"
            :id="'modal-preview'"
            centered scrollable
            :title="'Impressum Preview'"
            hide-footer ok-only
            v-if="text"
            style="margin-top: 2em;"
        >

            <b-container>
                <b-container class="preview" v-html="text"></b-container>
            </b-container>


        </b-modal>
    </b-container>
</template>

<script>
import {mapGetters} from "vuex";

export default {
    name: "ImpressumEditor",

    data() {
        return {
            text: '',
            showSave: false,


            editorOption: {
                modules: {
                    toolbar: [
                        //[{'font': []}],
                        ['bold', 'italic', 'underline'],
                        ['blockquote'],
                        //[{'header': 1}, {'header': 2}],
                        [{'header': [1, 2, 3, 4, 5, 6, false]}],
                        [{'align': []}],
                        [{'list': 'ordered'}, {'list': 'bullet'}],
                    ]
                }
            }
        }
    },
    methods: {
        async fetchImpressumMessage() {
            await this.$store.dispatch('impressum/fetchImpressum')
        },

        async saveImpressum() {
            await this.$store.dispatch('impressum/updateImpressum', this.text)
            await this.fetchImpressumMessage()

            console.log(this.impressumPutResponse.status)
            if (this.impressumPutResponse.status === 200) {
                // show saved notification
                console.log("test")
                this.showSave = true
                setTimeout(() => {
                    this.showSave = false
                }, 2000);
            }



        }
    },
        computed: {
            ...mapGetters({
                impressumMessage: 'impressum/getImpressumResponse',
                impressumPutResponse: 'impressum/getImpressumPutResponse'
            })
        },

        async created() {
            await this.fetchImpressumMessage()
        },

        async beforeMount() {
            await this.fetchImpressumMessage()
            this.$forceUpdate()
        },

     mounted() {
            this.fetchImpressumMessage()
            this.$forceUpdate()
            this.text = this.impressumMessage
            console.log(typeof this.impressumMessage)
        }
}
</script>

<style scoped src="../../assets/css/settingsPage.css">



</style>
