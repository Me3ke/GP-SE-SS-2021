<template>
    <b-container>
        <div v-if="page === 0">
            <p v-if="showText && page === 0">
                {{ $t('EmailTemplate.forNewSignatories') }}
            </p>

            <div style="padding-bottom: .2em" class="flex-box-2">
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

                        <select
                            style="margin-top: .5em; background-color: var(--whitesmoke); color: var(--dark-grey)"
                            v-model="selected"
                            @change="changedValue(selected)"
                        >
                            <option
                                v-for="(content, index) in emailTemplate"
                                :key="index"
                                v-bind:value="content"
                            >
                                {{ content.name }}
                            </option>
                        </select>
                    </b-col>

                    <b-col>
                        <button
                            type="button"
                            class="mt-1 light-btn"
                            v-if="selected.system === false"
                            @click="showEditTemplate"
                            style="width: auto;"
                        ><span class="button-txt">
                                {{ $t('EmailTemplate.editTemp') }}
                            </span></button>
                    </b-col>
                </b-row>
            </b-container>

            <b-container><h4><span>{{ $t('EmailTemplate.previewTemp') }}</span></h4>
                <b-container> {{ $t('EmailTemplate.subject') }}: {{ selected.subject }}</b-container>
                <hr>
                <EmailTemplatePreview :htmlString="selected.htmlTemplateBody"></EmailTemplatePreview>
            </b-container>
        </div>

        <div v-else-if="page !== 0">

            <!-- Page 1 Create New Template -->
            <div v-if="page === 1">
                <b-container>
                    <CreateEmailTemplate @newTemp="saveNewTemplate" @startPage="goToStartPage"
                                         :edit="false"></CreateEmailTemplate>
                </b-container>
            </div>

            <!-- Page 2 Delete Templates --->

            <div v-if="page === 2">
                <b-container style="overflow:scroll; overflow-x:hidden;">
                    <b-container v-for="(template, index) in emailTemplate" :key="index"
                                 style="margin-bottom: 1em">

                        <b-row style="padding-bottom: 1px">
                            <b-col>
                                <b-icon v-if="index >= 1" class="icon-hover" icon="trash"
                                        @click="deleteTemplate(template); fetchTemplate()"></b-icon>
                            </b-col>
                            <b-col cols="10">{{ template.name }}</b-col>
                        </b-row>
                    </b-container>
                </b-container>
                <div class="modal-footer">
                    <b-row align-h="end">
                        <b-col cols="auto">
                            <button class="light-btn" @click="goToStartPage">
                                {{ $t('UploadDoc.back') }}
                            </button>
                        </b-col>
                    </b-row>
                </div>
            </div>


            <!-- Page 2 Edit Templates --->
            <div v-if="page === 3">
                <b-container>
                    <EditEmailTemplate :temp="selected" @saveEditTemplate="saveEdit"
                                       @cancelPage="goToStartPage"></EditEmailTemplate>
                </b-container>
                <div class="modal-footer" v-if="page !== 3">
                    <b-row align-h="end">
                        <b-col cols="auto">
                            <button class="light-btn" @click="goToStartPage">
                                {{ $t('UploadDoc.back') }}
                            </button>
                        </b-col>
                        <b-col cols="auto">
                            <button class="elsa-blue-btn" @click="page = page -1">
                                {{ $t('UploadDoc.continue') }}
                            </button>
                        </b-col>
                    </b-row>
                </div>
            </div>
        </div>
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
    props: ['showText'],
    data() {
        return {

            // todo api with saved templates
            contents: [],
            copyTemplates: [],
            selected: '',
            page: 0,


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
            this.$emit('saveEmailTemplate', this.selectedTemplateObject)

        },

        showEditTemplate() {
            this.page = 3
            this.$emit('pages', this.page)

        },


        // Edit Template
        async saveEdit(emittedValue) {
            await this.fetchTemplate()
            this.$forceUpdate()

            if (emittedValue.hideModal) {
                this.page = 0
                this.$forceUpdate()
                this.selected = this.emailTemplate[0] // setting the initial value after closing the modal
            }
        },


        // Create new Template
        showNewCreateTemplate() {
            this.page = 1
            this.$emit('pages', this.page)
        },

        async saveNewTemplate(newTemplate) {
            //todo save new template into databse and store

            //checking if template name and subject is set


            await this.$store.dispatch('emailTemplate/setEmailTemplate', newTemplate)
            await this.fetchTemplate()

            //this.$refs['new-Template'].hide()
            this.page = 0
            this.$emit('pages', this.page)
        },


        // Delete Templates

        showDeleteTemplates() {
            //this.$refs['delete-Template'].show()
            this.page = 2
            this.$emit('pages', this.page)

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

        },

        goToStartPage() {
            this.page = 0
            this.$emit('pages', this.page)
        }
    },


    async beforeMount() {
        await this.$store.dispatch('emailTemplate/fetchEmailTemplate')
        this.contents = this.emailTemplate

        this.selected = this.emailTemplate[0]
        this.selectedTemplateObject = this.emailTemplate[0]

        this.page = 0
        this.$emit('saveEmailTemplate', this.selectedTemplateObject)


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
