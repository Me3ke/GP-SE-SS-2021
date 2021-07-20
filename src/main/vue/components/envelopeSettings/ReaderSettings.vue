<template>
    <div>
        <div v-if="!this.editReaders">
            <div>
                <SignatoryListItem v-for="reader in readers" :key="reader.email" :signatory="reader"></SignatoryListItem>
            </div>

            <b-list-group-item v-if="readers.length === 0" style="height: 2.5em; padding: 0.5em; background-color: var(--whitesmoke); border-color: var(--dark-grey)">
                {{$t('Settings.DocumentSettings.noReaders')}}
            </b-list-group-item>

            <b-row align-h="end" v-if="!(this.state === 'ARCHIVED')">
                <button class="elsa-blue-btn" style="width:10em; margin: 0.5em 2.5em" @click="editReaders = true;">
                    <b-icon icon="pencil-fill"></b-icon>
                    {{$t('Settings.DocumentSettings.edit')}}
                </button>
            </b-row>
        </div>

        <div v-if="this.editReaders" style="padding: 2em">
            <div class="form-group" >
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

            <div class="card" style="height:15em; overflow-y: auto; overflow-x: hidden">
                <div class="drag-drop-element" v-for="reader in readerInputs" :key="reader.email" style="padding:0.25em">
                    <b-row align-h="between">
                        <h6>
                            <b-col cols="auto">
                                <b-icon class="icon-hover" style="cursor:pointer;" icon="trash" @click="deleteReader(reader)"></b-icon>
                                {{reader.email}}
                            </b-col>
                        </h6>
                    </b-row>
                </div>
            </div>

            <b-row align-h="end">
                <button style="width:8em; margin:1em" class="light-btn" @click="editReaders = false"> {{$t('DownloadDoc.cancel')}} </button>
                <button style="width:8em; margin:1em" class="elsa-blue-btn" @click="saveReaders()"> {{$t('Settings.DocumentSettings.save')}} </button>
            </b-row>
        </div>
    </div>
</template>

<script>
import SignatoryListItem from "@/main/vue/components/envelopeSettings/SignatoryListItem";

export default {
    name: "ReaderSettings",
    props: {
        readers: Array,
        state: String
    },
    components: {
        SignatoryListItem
    },
    data() {
        return{
            editReaders: false,
            readerInput: "",
            readerInputs: this.readers,
            error: {
                alreadyExists: false
            }
        }
    },
    methods: {
        addReader() {
            if(!(this.readerInput === "")) {
                this.error.alreadyExists = false;
                let i;
                for(i = 0; i < this.readerInputs.length; i++) {
                    if(this.readerInput === this.readerInputs[i].email) {
                        this.error.alreadyExists = true;
                    }
                }
                if(!this.error.alreadyExists) {
                    this.signatoryInputs.push({email: this.signatoryInput, signatureType: 0})
                }
            }
            this.readerInput = "";
        },
        deleteReader(reader) {
            this.readerInputs.splice(this.readerInputs.indexOf(reader), 1);
        },
        saveReaders() {
            this.$emit('updateReader', this.makeReaderSettings());
            this.readerInput = "";
            this.editReaders = false;
        },
        makeReaderSettings() {
            let readerSettings = [];
            let i;
            for (i = 0; i < this.readerInputs.length; i++) {
                let reader = this.readerInputs[i];
                let readerSetting = {email: reader.email, signatureType: reader.signatureType, status: this.getStatus(reader), remind: false, reminderTiming: -1, signedOn: this.getSignedOn(reader)};
                readerSettings.push(readerSetting);
            }
            return readerSettings;
        },
        getStatus(reader) {
            let i;
            for (i = 0; i < this.readers.length; i++) {
                if(this.readers[i].email === reader.email) {
                    return this.readers[i].status;
                }
            }
            return false;
        },
        getSignedOn(reader) {
            let i;
            for (i = 0; i < this.readers.length; i++) {
                if(this.readers[i].email === reader.email) {
                    return this.readers[i].signedOn;
                }
            }
            return "";
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
</style>
