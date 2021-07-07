<template>
    <div>
        <div v-if="!addReaders && readerInputs.length === 0">
            <button class="light-btn" @click="addReaders = true">
                <h4>
                    <b-icon icon="plus-circle"></b-icon>
                    {{$t('Settings.DocumentSettings.addReader')}}
                </h4>
            </button>
        </div>
        <div v-if="addReaders && !(readerInputs.length === 0)">
            <b-list-group-item v-for="reader in readerInputs" :key="reader.email"> {{reader.email}} </b-list-group-item>

            <button class="light-btn" @click="addReaders = true">
                <h4>
                    {{$t('Settings.DocumentSettings.edit')}}
                </h4>
            </button>
        </div>

        <div v-if="addReaders">
            <div class="form-group">
                <b-row no-gutters>
                    <b-col cols="11">
                        <input type="text" class="form-control" v-model="readerInput" id="readerInput" :placeholder="$t('Settings.DocumentSettings.placeholderMail')">
                    </b-col>
                    <b-col cols="1">
                        <button class="elsa-blue-btn" @click="addReader()" style="padding: 0.2em 0.4em;">
                            <b-icon icon="plus" style="width: 1.5em; height: 1.5em"></b-icon>
                        </button>
                    </b-col>
                </b-row>
            </div>
            <div >
            </div>
            <div class="card" style="height:15em; overflow-y: auto; overflow-x: hidden">
                <div class="drag-drop-element" v-for="reader in readerInputs" :key="reader.email" style="padding:0.25em">
                    <b-row align-h="between">
                        <h6>
                            <b-col cols="auto">
                                <b-icon class="icon-hover" icon="trash" @click="deleteReader(reader)"></b-icon>
                                {{reader.email}}
                            </b-col>
                        </h6>
                    </b-row>
                </div>
            </div>
            <b-row align-h="end">
                <b-col cols="auto">
                    <button class="light-btn" @click="cancel()">
                        {{$t('DownloadDoc.cancel')}}
                    </button>
                </b-col>
                <b-col cols="auto">
                    <button class="elsa-blue-btn" @click="save()">
                        {{$t('Settings.DocumentSettings.save')}}
                    </button>
                </b-col>
            </b-row>
        </div>
    </div>
</template>

<script>
export default {
    name: "ReaderMenu",
    props: {
        readers: Array
    },
    data() {
        return{
            readerInput: "",
            readerInputs: [],
            addReaders: false
        }
    },
    methods: {
        addReader() {
            if(this.readerInputs.includes(this.readerInput)) {
                // TODO: Error
            } else {
                this.readerInputs.push({email: this.readerInput});
            }
            this.readerInput = "";
        },
        deleteReader(reader) {
            this.readerInputs.splice(this.readerInputs.indexOf(reader), 1)
        },
        cancel() {
            this.addReaders = false;
            this.readerInputs = [];
            this.readerInput = "";
        },
        save() {
            this.readers = this.readerInputs;
            this.addReaders = false;
        }
    }
}
</script>

<style scoped>
.drag-drop-element {
    border-bottom: 0.015em solid var(--light-grey);
}

.icon, .icon-hover{
    fill: var(--dark-grey);
    margin-left: 0.2em;
    margin-right: 0.2em;
}

.icon-hover:hover {
    fill: var(--light-grey);
    transition-duration: 0.4s;
}
</style>
