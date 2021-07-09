<template>
<div>
    <div class="modal-body">
        <b-alert :show="this.error.noDocument">
            {{$t('UploadDoc.error.noDocument')}}
        </b-alert>

        <!-- TODO: Make placeholder dependend on lacale -->
        <b-form-file
            v-model="fileInput"
            :state="Boolean(fileInput)"
            v-bind:placeholder="$t('UploadDoc.chooseFile')"
            drop-placeholder="Drop file here..."
        ></b-form-file>
    </div>
    <div class="modal-footer">
        <b-container fluid>
            <b-row align-h="end">
                <b-col cols="auto">
                    <button type="button" class="light-btn" @click="close()">
                        <h5>
                            {{$t('UploadDoc.close')}}
                        </h5>
                    </button>
                </b-col>
                <b-col cols="auto">
                    <button type="button" class="elsa-blue-btn" @click="next()">
                        <h5>
                            {{$t('UploadDoc.continue')}}
                        </h5>
                    </button>
                </b-col>
            </b-row>
        </b-container>
    </div>
</div>
</template>

<script>
import {convertUploadFileToBase64} from "@/main/vue/api/fileToBase64Converter";

export default {
    name: "FileInput",
    data() {
        return {
            fileInput: null,
            error: {
                noDocument: false
            }
        }
    },
    methods: {
        close() {
            this.fileInput = null;
            this.$emit('close')
        },
        async next() {
            if(this.fileInput === null) {
                this.error.noDocument = true;
            } else {
                this.error.noDocument = false;
                await this.fillFile();
                this.$emit('nextPage')
            }
        },
        async fillFile() {
            let file = {title: "", type: "", data: ""};
            file.title = this.fileInput.name.split('.')[0];
            file.type = this.fileInput.name.split('.')[1];
            file.data = await this.asyncHandleFunction(this.fileInput);
            this.$emit('updateFile', file);
        },
        // convert the file into an base64 string
        async asyncHandleFunction(file) {
            return await convertUploadFileToBase64(file)
        }
    }
}
</script>

<style scoped>
.alert {
    background-color: var(--sign-doc-hover);
    color: var(--red);
    border-color: var(--red);
}
</style>
