<template>
    <b-container>
        <b-alert :show="subjectEmpty"> <!--- TODO COLOR--->
            Subject is empty
        </b-alert>

        <b-container style="padding-bottom: .5em">
            <span>{{$t('EmailTemplate.templateName')}}</span>
            <b-form-input v-model="copyTemplate.name" :placeholder="$t('EmailTemplate.enterName')"></b-form-input>
        </b-container>
        <b-container style="padding-bottom: .5em">
            <span>{{$t('EmailTemplate.templateSubject')}}</span>
            <b-form-input v-model="copyTemplate.subject" :placeholder="$t('EmailTemplate.enterSubject')"></b-form-input>
        </b-container>
        <quill-editor
            ref="editor"
            :content="template.htmlTemplateBody"
            class="editor"
            @change="onEditorChange($event)"
            :options="editorOption"
            style="width: auto"
        >
            <div id="toolbar" slot="toolbar" style="width: auto">
                <button class="ql-bold">Bold</button>
                <button class="ql-italic">Italic</button>
                <button class="ql-underline">Underline</button>

                <select class="ql-size">
                    <option value="small"></option>
                    <option selected></option>
                    <option value="large"></option>
                    <option value="huge"></option>
                </select>


                <button id="addUser-button" @click="addUserHtml"
                        style="width: auto; padding-left: 2em; line-height: 0; font-size: 15px"> {{ $t('EmailTemplate.toolbar.addUser') }}</button>

                <button id="addOwner-button" @click="addOwnerHtml"
                        style="width: auto; padding-left: 2em; line-height: 0; font-size: 15px"> {{ $t('EmailTemplate.toolbar.addOwner') }}</button>

                <button id="endDate-button" @click="addEndDateHtml"
                        style="width: auto; padding-left: 2em; line-height: 0; font-size: 15px"> {{ $t('EmailTemplate.toolbar.addEndDate') }}</button>

                <button id="docTitle-button" @click="addDocTitle"
                        style="width: auto; padding-left: 2em; line-height: 0; font-size: 15px"> {{ $t('EmailTemplate.toolbar.documentLink') }}</button>

                <button id="link-button" @click="addLink"
                        style="width: auto; padding-left: 2em; line-height: 0; font-size: 15px"> {{ $t('EmailTemplate.toolbar.documentLink') }}</button>


            </div>
        </quill-editor>
        <div class="text-right">
            <button type="button"
                    class="ml-1 elsa-blue-btn"
                    @click="saveEditedTemp">
                         <span class="button-txt">
                             {{ $t('EmailTemplate.Save') }}
                         </span>
            </button>
        </div>

    </b-container>
</template>


<script>
export default {
    name: "EditEmailTemplate",
    props: {
        temp: Object
    },
    data() {
        return {
            subjectEmpty: false,
            template: {
                templateID: 0,
                htmlTemplateBody: '',
                subject: '',
                name: '',
                system: false
            },
            copyTemplate: {},

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


        async saveEditedTemp() {


            if(this.copyTemplate.subject !== '') {
                console.log('not empty')

                console.log(this.copyTemplate)
                console.log('------------------')


                await this.$store.dispatch('emailTemplate/editEmailTemplate', this.copyTemplate)
                await this.$store.dispatch('emailTemplate/fetchEmailTemplate');
                this.$forceUpdate();

                this.subjectEmpty = false

                let emittedValue = {
                    templateId: this.copyTemplate.templateID,
                    hideModal: !this.subjectEmpty
                }

                this.$emit('saveEditTemplate', emittedValue)
            } else {
                this.subjectEmpty = true
            }


        },

        onEditorChange(edited) {
            this.copyTemplate.htmlTemplateBody = edited.html
        }
    },

    created() {
        this.template = this.temp

    },
    mounted() {
        this.copyTemplate = Object.assign({}, this.template)
    }
}
</script>

<style scoped src="../assets/css/settingsPage.css">



</style>
