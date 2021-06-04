<template>
    <div>
        <div class="form-group">
            <h6>{{$t('Settings.DocumentSettings.addReader')}}</h6>
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
            <div class="custom-control custom-switch">
                <input type="checkbox" class="custom-control-input" id="readerOrderRelevantSwitch" v-model="orderRelevant">
                <label class="custom-control-label" for="readerOrderRelevantSwitch"> {{$t('Settings.DocumentSettings.orderRelevant')}} </label>
            </div>
        </div>
        <div class="card" style="height:15em; overflow-y: auto; overflow-x: hidden">
            <draggable v-model="readers">
                <div class="drag-drop-element" v-for="reader in readers" :key="reader" style="padding:0.25em">
                    <b-row align-h="between">
                        <h6>
                            <b-col cols="auto">
                                <b-icon class="icon-hover" icon="trash" @click="deleteReader(reader)"></b-icon>
                                {{reader}}
                            </b-col>
                        </h6>
                        <b-col cols="auto">
                            <b-icon icon="list" class="icon"></b-icon>
                        </b-col>
                    </b-row>
                </div>
            </draggable>
        </div>
    </div>
</template>

<script>
import draggable from 'vuedraggable'
export default {
    name: "ReaderMenu",
    props: {
        readers: Array,
        orderRelevant: Boolean
    },
    components: {draggable},
    data() {
        return{
            readerInput: ""
        }
    },
    methods: {
        addReader() {
            if(this.readers.includes(this.readerInput)) {
                // TODO: Error
            } else {
                this.readers.push(this.readerInput);
            }
            this.readerInput = "";
        },
        deleteReader(reader) {
            this.readers = this.readers.filter(read => !(read === reader))
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
