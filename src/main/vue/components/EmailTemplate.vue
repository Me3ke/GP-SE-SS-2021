<template>
    <b-container>

                    <div style="padding-bottom: 1em">
                    <select v-model="selected" @change="changedValue(selected)">
                        <option v-for="(content, index) in contents" :key="index"
                                v-bind:value="content"> {{content.name}}</option>
                    </select>


                    <b-button class="elsa-blue-btn"
                              style="margin-top: 0.2em; margin-bottom: 0.1em; margin-left: 0.7em"
                    >
                        Hochladen
                    </b-button>
                    </div>

                <b-container style="padding-right: 0; padding-left: 0">
                <quill-editor
                    ref="editor"
                    v-model="selected.htmlTemplateBody"
                    class="editor"
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

                    <button type="button"
                            class="mt-1 light-btn"
                            v-b-modal="'modal-preview'"
                    >
                    <span class="button-txt">
                        Preview
                    </span>
                    </button>

                    <!--- Preview --->
                    <b-modal
                        :id="'modal-preview'"
                        centered
                        :title="'Email Template Preview'"
                        hide-footer ok-only
                        v-if="selected.htmlTemplateBody"
                        style="margin-top: 2em"
                    >
                        <b-container v-html="selected.htmlTemplateBody"></b-container>
                    </b-modal>



                </b-container>

                <!---<div class="output ql-snow" style="border: 1px solid black; margin-top: 1em">
                    <div class="title">Output</div>
                    <div class="ql-editor" v-html="selected.htmlTemplateBody"></div>
                </div>


                    <b-button class="elsa-blue-btn"
                              style="margin-top: 0.2em; margin-bottom: 0.1em; margin-left: 0.7em"
                              >
                        {{ $t('Settings.MessageSettings.send') }}
                    </b-button>-->


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
            beforeBody: "<!DOCTYPE html>\\n<html lang = \\de\\>\\n\\n\\t<head>\\n\\n\\t\\t<meta charset " +
                "= \\utf8\\>\\n\\t\\t<meta name=\\viewport\\ content=\\width = device - width, initial - " +
                "scale = 1.0\\ >\\n  </head>\\n\\n  <body>\\n\\t\\t",
            selected: "test",
            firstElement: {},
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
            if(this.contents > 0) {
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
            quill.insertText(cursorPosition,"[FirstNameSignatory] [LastNameSignatory]")
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

        changedValue(event) {
            this.selectedTemplateObject = event
            console.log("NEW VALUE: ", this.selectedTemplateObject)
            this.$emit('saveEmailTemplate', this.selectedTemplateObject)

        },

        uploadEmailTemplate() {
            console.log(this.selectedTemplateObject)
            this.$emit('saveEmailTemplate', this.selectedTemplateObject)
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
        this.selected = this.emailTemplate[0]
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


</style>
