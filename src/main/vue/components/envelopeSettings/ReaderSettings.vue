<template>
    <div>
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
            <div class="drag-drop-element" v-for="reader in readerArray" :key="reader.email" style="padding:0.25em">
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
    </div>
</template>

<script>
export default {
    name: "ReaderSettings",
    props: {
        readers: Array
    },
    data() {
        return{
            readerInput: "",
            readerArray: this.readers
        }
    },
    methods: {
        addReader() {
            if(this.readerArray.includes(this.readerInput)) {
                // TODO: Error
            } else {
                this.readerArray.push({email: this.readerInput});
            }
            this.readerInput = "";
        },
        deleteReader(reader) {
            this.readerArray.splice(this.readerArray.indexOf(reader), 1)
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
