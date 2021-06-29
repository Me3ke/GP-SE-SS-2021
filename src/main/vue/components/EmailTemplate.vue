<template>
    <b-container fluid="sm">
        <b-row align-h="center" align-v="center" class="text-center">
            <div class="card">
                <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                    <h4>
                        Email Templates
                    </h4>

                </div>

                <b-list-group-item class="d-flex justify-content-center align-items-center">
                    <div style="alignment: left">
                        <input  placeholder="edit me" />
                    </div>

                    <select v-model="selected">
                        <option disabled value="">Please select one</option>
                        <option v-for="(content, index) in contents" :key="index" v-bind:value="content.content"> {{content.name}}</option>
                    </select>


                    <b-button class="elsa-blue-btn"
                              style="margin-top: 0.2em; margin-bottom: 0.1em; margin-left: 0.7em"
                    >
                        Hochladen
                    </b-button>
                </b-list-group-item>


                <quill-editor
                    ref="editor"
                    v-model="selected"
                    class="editor"
                    :options="editorOption"
                >
                    <div id="toolbar" slot="toolbar">
                        <!-- Add a bold button -->
                        <button class="ql-bold">Bold</button>
                        <button class="ql-italic">Italic</button>
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

                        <!-- You can also add your own -->
                        <button class="ql-add-user-button" @click="addUserHtml"
                                style="width: auto; padding-left: 2em; line-height: 0; font-size: 15px"> Add User</button>

                        <button class="ql-add-owner-button ql-float" @click="addOwnerHtml"
                                style="width: auto; padding-left: 2em; line-height: 0; font-size: 15px"> Add Owner</button>

                        <button class="ql-add-end-date-button ql-float" @click="addEndDateHtml"
                                style="width: auto; padding-left: 2em; line-height: 0; font-size: 15px"> Add End Date</button>
                    </div>

                </quill-editor>

                <div class="output ql-snow" style="border: 1px solid black; margin-top: 1em">
                    <div class="title">Output</div>
                    <div class="ql-editor" v-html="selected"></div>
                </div>

                <b-list-group-item class="d-flex justify-content-end align-items-center">

                    <b-button class="elsa-blue-btn"
                              style="margin-top: 0.2em; margin-bottom: 0.1em; margin-left: 0.7em"
                              >
                        {{ $t('Settings.MessageSettings.send') }}
                    </b-button>
                </b-list-group-item>

            </div>
        </b-row>

    </b-container>
</template>

<script>

export default {
    name: "EmailTemplate",
    data() {
        return {

            // todo api with saved templates
            contents: [
                {
                    name: '1',
                    content: '<h1>Hello World#1</h1><p>Here is some text</p'
                },
                {
                    name: '2',
                    content: '<h1>Hello World#2</h1><p>Here is some text</p'
                },
                {
                    name: '3',
                    content: '<h1>Hello World#3</h1><p>Here is some text</p'
                }
            ],
            selected: 'Email Templates hier gestalten',
            editorOption: {
                modules: {
                    toolbar: '#toolbar',
                    handler: {

                    }

                }

               /* modules: {
                    toolbar: [
                        ['bold', 'italic', 'underline', 'strike'],
                        ['blockquote', 'code-block'],
                        [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
                        [{ 'list': 'ordered' }, { 'list': 'bullet' }],
                       // [{ 'direction': 'rtl' }],
                        [{ 'font': [] }],
                        [{ 'color': [] }, { 'background': [] }],
                        [{ 'align': [] }],
                        ['clean'],
                        [{ 'name': ['small', false, 'large', 'huge'] }],  // custom dropdown

                    ]
                }*/
            }
        }
    },
    methods: {

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

        }
    }
}
</script>

<style scoped src="../assets/css/settingsPage.css">

.ql-editor {
    height: 10em !important; /* TODO */
}



</style>
