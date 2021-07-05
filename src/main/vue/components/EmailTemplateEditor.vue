<template>
    <b-container>
        <input type="text">

        <quill-editor
            ref="editor"
            v-model="template.htmlTemplateBody"
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
                        style="width: auto; padding-left: 2em; line-height: 0; font-size: 15px"> {{ $t('EmailTemplate.toolbar.addUser') }}</button>

                <button id="addOwner-button" @click="addOwnerHtml"
                        style="width: auto; padding-left: 2em; line-height: 0; font-size: 15px"> {{ $t('EmailTemplate.toolbar.addOwner') }}</button>

                <button id="endDate-button" @click="addEndDateHtml"
                        style="width: auto; padding-left: 2em; line-height: 0; font-size: 15px"> {{ $t('EmailTemplate.toolbar.addEndDate') }}</button>

                <button id="link-button" @click="addLink"
                        style="width: auto; padding-left: 2em; line-height: 0; font-size: 15px"> {{ $t('EmailTemplate.toolbar.documentLink') }}</button>


            </div>
        </quill-editor>

            <button type="button"
                    class="ml-1 elsa-blue-btn"
                    @click="saveTemp">
                     <span class="button-txt">
                         {{ $t('UploadDoc.continue') }}
                     </span>
            </button>


    </b-container>
</template>

<script>
export default {
    name: "EmailTemplateEditor",
   /* props: {
        selectedTemplateObject: null
    },*/
    data() {
        return {
            noticeUnsavedChanges: false,
            template: {
                name: '',
                htmlTemplateBody: '',

            },
            editorOption: {
                modules: {
                    toolbar: '#toolbar'
                }
            }
        }
    },
    methods: {
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
        },

        addLink() {
            let quill = this.$refs.editor.quill;
            quill.focus();
            const cursorPosition = quill.getSelection(true);
            quill.insertText(cursorPosition, "[Link]")
        },

        async saveTemp() {
            this.noticeUnsavedChanges = true // @change for edit
            this.$emit('newTemp', this.template.htmlTemplateBody)
        },

    },
}
</script>

<style scoped>

</style>
