<template>
    <b-container class="container" style="margin-top: 0">
        <quill-editor
            ref="editor"
            class="editor"
            v-model="text"
            :options="editorOption"
        >
        </quill-editor>

        <b-button type="button"
                  size="sm"
                class="light-btn"
                v-b-modal="'modal-preview'"
        >
                    <span class="button-txt">
                        Preview
                    </span>
        </b-button>
        <b-button type="button"
                  size="sm"
                class="mt-1 elsa-blue-btn"
                @click="saveImpressum"
        >
                    <span class="button-txt">
                        Save
                    </span>
        </b-button>
        <b-button class="light-btn"
                  style="margin-top: 0.2em; margin-bottom: 0.1em; margin-right: 1em;">
            {{ $t('AdminSettings.corporate.default') }}
        </b-button>


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
            console.log(this.text)
            await this.$store.dispatch('impressum/updateImpressum', this.text)
            await this.fetchImpressumMessage()



        }
    },
        computed: {
            ...mapGetters({
                impressumMessage: 'impressum/getImpressumResponse',
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

<style scoped src="../../assets/css/signModals.css">



</style>
