<template>
    <b-container>

                    <div style="padding-bottom: 1em">

                        <b-button class="elsa-blue-btn"
                                  style="margin-top: 0.2em; margin-bottom: 0.1em; margin-left: 0.7em; width: auto"
                                  v-if="selectedTemplateObject !== {}"
                        >
                            Create Template
                        </b-button>
                    </div>

                    <select v-model="selected" @change="changedValue(selected)">
                        <option v-for="(content, index) in contents" :key="index"
                                v-bind:value="content"> {{content.name}}</option>
                    </select>


                    <button
                        type="button"
                        v-b-modal="'modal-editor'"
                        class="mt-1 light-btn"
                    ><span class="button-txt">
                        Edit
                    </span></button>


                <b-container style="padding-top: 1em; overflow:scroll; overflow-x:hidden; height:300px;" v-html="selected.htmlTemplateBody"></b-container>

                    <!--- Editor --->
                    <b-modal
                        id="modal-editor"
                        ref="modal-editorRef"
                        centered
                        hide-footer ok-only hide-header
                        v-if="selected.htmlTemplateBody"
                        style="margin-top: 2em;"
                    >

                        <b-container style="padding-right: 0; padding-left: 0;">
                            <p> <span> To edit the template, just write in the editor and it will be applied automatically </span></p>

                            <quill-editor
                                ref="editor"
                                v-model="selectedTemplateObject.htmlTemplateBody"
                                class="editor"
                                @change="setNoticeChanges"
                                :options="editorOption"
                                style="width: auto"
                            >
                                <div id="toolbar" slot="toolbar" style="width: auto">
                                    <!-- Add a bold button -->
                                    <button class="ql-bold">Bold</button>
                                    <button class="ql-italic">Italic</button>
                                    <button class="ql-underline">Underline</button>
                                    <!-- Add font size dropdown -->
                                    <select class="ql-size">
                                        <option value="small"></option>
                                        <!-- Note a missing, thus falsy value, is used to reset to default -->
                                        <option selected></option>
                                        <option value="large"></option>
                                        <option value="huge"></option>
                                    </select>
                                    <select class="ql-font">
                                        <option selected="selected"></option>
                                        <option value="serif"></option>
                                        <option value="monospace"></option>
                                    </select>

                                    <button class="ql-link" style="padding-left: 2em; padding-right: 2em"></button>

                                    <!-- You can also add your own -->
                                    <button id="addUser-button" @click="addUserHtml"
                                            style="width: auto; padding-left: 2em; line-height: 0; font-size: 15px"> Add User</button>

                                    <button id="addOwner-button" @click="addOwnerHtml"
                                            style="width: auto; padding-left: 2em; line-height: 0; font-size: 15px"> Add Owner</button>

                                    <button id="endDate-button" @click="addEndDateHtml"
                                            style="width: auto; padding-left: 2em; line-height: 0; font-size: 15px"> Add End Date</button>

                                    <button id="link-button" @click="addLink"
                                            style="width: auto; padding-left: 2em; line-height: 0; font-size: 15px"> Add Document Link</button>

                                </div>

                            </quill-editor>

                            <button
                                type="button"
                                class="mt-1 light-btn"
                                @click="saveEditedTemplate"
                            ><span class="button-txt">
                        Save
                    </span></button>
                        </b-container>

                    </b-modal>



                <!--- Save edited template --->
                <b-modal
                    ref="modal-notice-unsaved-changes"
                    hide-footer ok-only hide-header centered
                    no-close-on-esc no-close-on-backdrop
                >
                    <b-container>
                        <p>
                            Noticed Unsaved Changes!
                        </p>
                        <p>
                            If you want to save the Edited template click on the "Save Template" Button
                        </p>
                        <p>
                            If it is for single use only click on "Single Use" Button
                        </p>
                    </b-container>

                    <div>
                        <button type="button"
                                class="mt-1 light-btn"
                                @click="singleUse"

                        >

                    <span class="button-txt">
                        Single Use
                    </span>
                        </button>

                        <button type="button"
                                class="ml-1 elsa-blue-btn"
                                style="width: auto"
                                @click="saveTemplate"


                        >
                    <span class="button-txt">
                        Save Template
                    </span>
                        </button>
                    </div>
                </b-modal>
    </b-container>
</template>

<script>
import {mapGetters} from "vuex";

export default {
    name: "EmailTemplate",
    data() {
        return {

            // todo api with saved templates
            contents: [],
            copyTemplates: [],
            selected: "SignatureInvitationTemplate",


            noticeUnsavedChanges: false,

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

        getFirstTemplate() {
            if (this.contents > 0) {
                console.log("hallo")
                return {}
            } else {
                console.log('#2')
                return {}
            }
        },

        addUserHtml() {
            let quill = this.$refs.editor.quill;
            quill.focus();
            const cursorPosition = quill.getSelection(true)
            quill.insertText(cursorPosition, "[FirstNameSignatory] [LastNameSignatory]")
        },

        addOwnerHtml() {
            let quill = this.$refs.editor.quill;
            quill.focus();
            const cursorPosition = quill.getSelection(true);
            quill.insertText(cursorPosition, "[FirstNameOwner] [LastNameOwner]")
        },

        addEndDateHtml() {
            let quill = this.$refs.editor.quill;
            quill.focus();
            const cursorPosition = quill.getSelection(true);
            quill.insertText(cursorPosition, "[EndDate]")

            console.log(this.selected)

        },

        addLink() {
            let quill = this.$refs.editor.quill;
            quill.focus();
            const cursorPosition = quill.getSelection(true);
            quill.insertText(cursorPosition, "[Link]")
        },


        // observe changed value and emmit it
        changedValue(event) {
            console.log(this.selected)
            this.selectedTemplateObject = event

            console.log("NEW VALUE: ", this.selectedTemplateObject)

            // todo check the noticedChange boolean and if it is false then warn the user with modal
            //this.compareForEditedTemplates(this.selectedTemplateObject)
            if (!this.noticeUnsavedChanges) {
                this.$emit('saveEmailTemplate', this.selectedTemplateObject)
            }
        },

        setNoticeChanges() {
            this.noticeUnsavedChanges = true
        },

        saveEditedTemplate() {
            if(this.noticeUnsavedChanges === true) {
                this.$refs['modal-notice-unsaved-changes'].show()
            }
            this.$refs['modal-editorRef'].hide()
        },

        singleUse() {
            this.$refs['modal-notice-unsaved-changes'].hide()
        },

        async saveTemplate() {
            await this.$store.dispatch('emailTemplate/editEmailTemplate',
                {
                    templateId: this.selectedTemplateObject.templateID,
                    templateBody: this.selectedTemplateObject.htmlTemplateBody
                })
            await this.$store.dispatch('emailTemplate/fetchEmailTemplate')
            this.contents = this.emailTemplate
            this.$refs['modal-notice-unsaved-changes'].hide()
        }

    },




    async mounted() {
        let test = {
            htmlTemplateBody: "<h1>TEST</h1>",
            name: 'Test',
            subject: "Test Object",
            system: true,
            templateID: 222

        }
        await this.$store.dispatch('emailTemplate/fetchEmailTemplate')
        this.contents = this.emailTemplate


        this.contents.push(test)
        console.log("selected: ", this.selected)


        //copy of templates
        // setting the standard template as initial Value
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

.ql-editor {
    height: 10em !important; /* TODO */
}

#modal-1c__BV_modal_content_ {
    max-height: inherit;
}


</style>
