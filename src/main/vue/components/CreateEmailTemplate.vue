<template>
    <b-container>
        <b-alert :show="subjectEmpty"> <!--- TODO COLOR--->
            Subject is empty
        </b-alert>

        <b-container style="padding-bottom: .5em">
            <span>{{ $t('EmailTemplate.templateName') }}</span>
            <b-form-input v-model="template.name" :placeholder="$t('EmailTemplate.enterName')"></b-form-input>
        </b-container>
        <b-container style="padding-bottom: .5em">
            <span>{{ $t('EmailTemplate.templateSubject') }}</span>
            <b-form-input v-model="template.subject" :placeholder="$t('EmailTemplate.enterSubject')"></b-form-input>
        </b-container>
        <quill-editor
            ref="editor"
            v-model="template.htmlTemplateBody"
            class="editor"
            :options="editorOption"
            style="width: auto; padding-bottom: .5em"
        >
            <div id="toolbar" slot="toolbar" style="width: auto">
                <!-- Add a bold button -->
                <button class="ql-bold">Bold</button>
                <button class="ql-italic">Italic</button>
                <button class="ql-underline">Underline</button>
                <!-- Add font size dropdown -->
                <select class="ql-size">
                    <option value="small"></option>
                    <option selected></option>
                    <option value="large"></option>
                    <option value="huge"></option>
                </select>

                <!-- You can also add your own -->
                <button id="addUser-button" @click="addUserHtml"
                        style="width: auto; padding-left: 2em; line-height: 0; font-size: 15px">
                    {{ $t('EmailTemplate.toolbar.addUser') }}
                </button>

                <button id="addOwner-button" @click="addOwnerHtml"
                        style="width: auto; padding-left: 2em; line-height: 0; font-size: 15px">
                    {{ $t('EmailTemplate.toolbar.addOwner') }}
                </button>

                <button id="endDate-button" @click="addEndDateHtml"
                        style="width: auto; padding-left: 2em; line-height: 0; font-size: 15px">
                    {{ $t('EmailTemplate.toolbar.addEndDate') }}
                </button>

                <button id="docTitle-button" @click="addDocTitle"
                        style="width: auto; padding-left: 2em; line-height: 0; font-size: 15px">
                    {{ $t('EmailTemplate.toolbar.documentTitle') }}
                </button>

                <button id="link-button" @click="addLink"
                        style="width: auto; line-height: 0; font-size: 15px">
                    {{ $t('EmailTemplate.toolbar.documentLink') }}
                </button>


            </div>
        </quill-editor>
        <div class="text-right">
            <button type="button"
                    class="ml-1 light-btn"
                    @click="cancel">
                         <span class="button-txt">
                             {{ $t('UploadDoc.back') }}
                         </span>
            </button>
            <button type="button"
                    class="ml-1 elsa-blue-btn"
                    @click="saveTemp">
                         <span class="button-txt">
                            {{ $t('EmailTemplate.Save') }}
                         </span>
            </button>
        </div>

    </b-container>
</template>

<script>
export default {
    name: "CreateEmailTemplate",
    data() {
        return {
            subjectEmpty: false,

            template: {
                htmlTemplateBody: '',
                subject: '',
                name: ''
            },
            editorOption: {
                placeholder: this.$i18n.t('EmailTemplate.editorPlaceholder') + '\n' + '',

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

        addDocTitle() {
            let quill = this.$refs.editor.quill;
            quill.focus();
            const cursorPosition = quill.getSelection(true);
            quill.insertText(cursorPosition, "[DocumentTitle]")
        },

        addLink() {
            let quill = this.$refs.editor.quill;
            quill.focus();
            const cursorPosition = quill.getSelection(true);
            quill.insertText(cursorPosition, "[Link]")
        },

        saveTemp() {
            if (this.template.name === '') {
                this.template.name = 'Email Template'
            }


            if (this.template.subject !== '') {
                this.$emit('newTemp', this.template)
                this.$emit('startPage')
                this.subjectEmpty = false
            } else {
                this.subjectEmpty = true
            }
        },

        cancel() {
            this.subjectEmpty = false
            this.template.name = ''
            this.template.htmlTemplateBody = ''
            this.template.subject = ''
            this.$emit('startPage')

        }

    }
}
</script>

<style scoped src="../assets/css/settingsPage.css">

</style>
