<template>
    <b-container style="margin-top: 0em">
        <quill-editor
            ref="editor"
            class="editor"
            v-model="text"
        ></quill-editor>

        <b-button type="button"
                  size="sm"
                class="mt-1 light-btn"
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
            text: ''
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
