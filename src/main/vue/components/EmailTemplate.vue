<template>
    <b-container>
                    <div style="padding-bottom: 1em" class="flex-box-2">

                        <button class="elsa-blue-btn"
                                  style="margin-top: 0.2em; margin-bottom: 0.1em; margin-left: 0.7em; width: auto"
                                  v-if="selectedTemplateObject !== {}"
                                  @click="showNewCreateTemplate"
                        >
                            {{ $t('EmailTemplate.createTemp') }}
                        </button>

                        <button class="elsa-blue-btn"
                                style="margin-top: 0.2em; margin-bottom: 0.1em; margin-left: 0.7em; width: auto;"
                                @click="showDeleteTemplates"
                        >
                            {{ $t('EmailTemplate.deleteTemp') }}
                        </button>
                    </div>

                    <b-container>
                        <b-row>
                        <b-col>

                        <select style="margin-top: .5em" v-model="selected" @change="changedValue(selected)">
                        <option v-for="(content, index) in emailTemplate" :key="index"
                                v-bind:value="content"> {{content.name}}</option>
                    </select>
                        </b-col>

                        <b-col>
                            <button
                                type="button"
                                v-b-modal="'modal-editor'"
                                class="mt-1 light-btn"
                                v-if="selected.system === false"
                                style="width: auto;"
                            ><span class="button-txt">
                                {{ $t('EmailTemplate.editTemp') }}
                            </span></button>
                        </b-col>
                        </b-row>
                    </b-container>

                <b-container style="padding-top: 1em;"> <h4><span>{{ $t('EmailTemplate.previewTemp') }}</span></h4>
                    <b-container> Subject: {{ selected.subject }}</b-container>
                    <hr>
                    <EmailTemplatePreview :htmlString="selected.htmlTemplateBody"></EmailTemplatePreview>
                </b-container>


                    <!--- Edit Template --->
                    <b-modal
                        id="modal-editor"
                        ref="modal-editorRef"
                        :title="'Edit ' + selected.name + ' Template' "
                        centered
                        hide-footer ok-only
                        style="margin-top: 2em;"
                    >
                       <b-container style="padding-right: 0; padding-left: 0;">
                            <p> <span> {{ $t('EmailTemplate.editSentence') }} </span></p>
                            <EditEmailTemplate :temp="selected" @saveEditTemplate="saveEdit"></EditEmailTemplate>
                       </b-container>

                   </b-modal>

                <!--- Create new Template createNewEmailTemplate --->
                <b-modal
                    title="Create new Template"
                    hide-footer ok-only centered
                    ref="new-Template"
                >
                    <b-container>
                        <CreateEmailTemplate @newTemp="saveNewTemplate" :edit="false"></CreateEmailTemplate>
                    </b-container>
                </b-modal>

                <!--- Delete Template --->
                <b-modal
                    title="Delete Templates"
                    hide-footer ok-only centered
                    ref="delete-Template"
                >
                    <b-container style="overflow:scroll; overflow-x:hidden;">
                        <b-container v-for="(template, index) in emailTemplate" :key="index"
                                     style="margin-bottom: 1em">

                            <b-row style="padding-bottom: 1px">
                                <b-col>
                                    <b-icon v-if="index >= 1" class="icon-hover" icon="trash" @click="deleteTemplate(template); fetchTemplate()"></b-icon>
                                </b-col>
                                <b-col cols="10">{{template.name}}</b-col>
                            </b-row>
                        </b-container>
                    </b-container>
                </b-modal>

    </b-container>
</template>

<script>
import {mapGetters} from "vuex";
import EmailTemplatePreview from "@/main/vue/components/EmailTemplatePreview";
import EditEmailTemplate from "@/main/vue/components/EditEmailTemplate";
import CreateEmailTemplate from "@/main/vue/components/CreateEmailTemplate";

export default {
    name: "EmailTemplate",
    components: {CreateEmailTemplate, EditEmailTemplate, EmailTemplatePreview},
    data() {
        return {

            // todo api with saved templates
            contents: [],
            copyTemplates: [],
            selected: '',



            // Object which is going to be emitted
            selectedTemplateObject: {},



            editorOption: {
                modules: {
                    toolbar: '#toolbar'
                }
            }
        }
    },
    methods: {

        // observe changed value and emmit it
        changedValue(event) {
            this.selectedTemplateObject = event
            console.log(event)
            this.$emit('saveEmailTemplate', this.selectedTemplateObject)

        },


        // Edit Template
        async saveEdit(emittedValue) {
            await this.fetchTemplate()
            this.$forceUpdate()

            if(emittedValue.hideModal) {
                this.$refs['modal-editorRef'].hide()
                this.$forceUpdate()

                this.selected = this.emailTemplate[0] // setting the initial value after closing the modal
            }
        },


        // Create new Template
        showNewCreateTemplate() {
            this.$refs['new-Template'].show()

        },

         async saveNewTemplate(newTemplate) {
             //todo save new template into databse and store

             //checking if template name and subject is set



             await this.$store.dispatch('emailTemplate/setEmailTemplate', newTemplate)
             await this.fetchTemplate()

             this.$refs['new-Template'].hide()
         },




        // Delete Templates

        showDeleteTemplates() {
            this.$refs['delete-Template'].show()
        },

        async deleteTemplate(template) {
            await this.$store.dispatch('emailTemplate/deleteEmailTemplate', template.templateID)
            await this.fetchTemplate()
            this.selected = this.emailTemplate[0]

            this.$forceUpdate();

        },

        async fetchTemplate() {
            await this.$store.dispatch('emailTemplate/fetchEmailTemplate');
            this.$forceUpdate();

        }
    },




    async beforeMount() {
        await this.$store.dispatch('emailTemplate/fetchEmailTemplate')
        this.contents = this.emailTemplate

        this.selected = this.emailTemplate[0]
        this.selectedTemplateObject = this.emailTemplate[0]
    },

    computed: {
        ...mapGetters({
            emailTemplate: 'emailTemplate/getEmailTemplates'
        }),
    }
}
</script>

<style scoped src="../assets/css/settingsPage.css">

</style>
