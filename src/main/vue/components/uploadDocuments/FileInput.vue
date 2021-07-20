<template>
<div>
    <div class="modal-body">
        <b-alert :show="this.error.noDocument">
            {{$t('UploadDoc.error.noDocument')}}
        </b-alert>

        <div v-for="fileInput in fileInputs" :key="fileInput.index">
            <b-row v-if="fileInput.index > 0">
                <b-col cols="1" >
                    <button class="iconRed" style="padding:0.5em" @click="removeFile(fileInput)">
                        <b-icon icon="x-circle" style="height:1.25em; width: auto"></b-icon>
                    </button>
                </b-col>
                <b-col cols="11">
                    <b-form-file style="margin-bottom: 1em; cursor: pointer"
                                 v-model="fileInput.file"
                                 :state="Boolean(fileInput.file)"
                                 v-bind:placeholder="$t('UploadDoc.chooseFile')"
                    ></b-form-file>
                </b-col>
            </b-row>
            <div v-else>
                <b-form-file style="margin-bottom: 1em; cursor: pointer"
                             v-model="fileInput.file"
                             :state="Boolean(fileInput.file)"
                             v-bind:placeholder="$t('UploadDoc.chooseFile')"
                             drop-placeholder="Drop file here..."
                ></b-form-file>
            </div>
        </div>

        <b-row align-h="center" v-if="moreFiles === undefined">
            <button class="light-btn" @click="addFile()" v-if="!(fileInputs[fileInputs.length - 1].file === null)">
                <h5>
                    <b-icon icon="plus-circle"></b-icon>
                    {{$t('UploadDoc.addMoreFiles')}}
                </h5>
            </button>
        </b-row>

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

import {convertUploadFileToBase64} from "@/main/vue/scripts/fileToBase64Converter";

export default {
    name: "FileInput",
    data() {
        return {
            fileInputs: [{file: null, index: 0}],
            files: [],
            error: {
                noDocument: false
            }
        }
    },
    props: ['moreFiles'],
    methods: {
        close() {
            this.fileInputs = [null];
            this.$emit('close')
        },
        async next() {
            if(this.fileInputs[0].file === null) {
                this.error.noDocument = true;
            } else {
                this.error.noDocument = false;
                await this.fillFile();
                this.$emit('nextPage')
            }
        },
        async fillFile() {
            let files = [];
            let i;
            for (i = 0; i < this.fileInputs.length; i++) {
                let fileInput = this.fileInputs[i].file;
                let file = {title: "", type: "", data: ""};
                file.title = fileInput.name.split('.')[0];
                file.type = fileInput.name.split('.')[1];
                file.data = await this.asyncHandleFunction(fileInput);
                files.push(file);
            }
            this.$emit('updateFiles', files);
        },
        // convert the file into an base64 string
        async asyncHandleFunction(file) {
            return await convertUploadFileToBase64(file)
        },
        addFile() {
            this.fileInputs.push({file: null, index: this.fileInputs[this.fileInputs.length - 1].index + 1});
        },
        removeFile(fileInput) {
            let result = [];
            let i;
            for (i = 0; i < this.fileInputs.length; i++) {
                if(!(fileInput.index === this.fileInputs[i].index)) {
                    result.push(this.fileInputs[i]);
                }
            }
            this.fileInputs = result;
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

.elsa-blue-btn, .light-btn {
    padding: 0.5vh 1vw 0;
    border: 0.03vw solid var(--dark-grey);
    margin: 0.25vh 0.25vw;
}

.elsa-blue-btn:focus, .light-btn:focus {
    border: 0.03vw solid var(--dark-grey);
}

.iconRed {
    color: var(--red);
    border: 0;
    background-color: var(--whitesmoke);
}

.iconRed:hover {
    color: var(--sign-doc);
    border: 0;
    background-color: var(--whitesmoke);
}
</style>
