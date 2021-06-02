<template>
    <div>
        <div class="form-group">
            <h6>{{$t('Settings.DocumentSettings.addSignatory')}}</h6>
            <b-row no-gutters>
                <b-col cols="11">
                    <input type="text" class="form-control" v-model="signatoryInput" id="signatoryInput" :placeholder="$t('Settings.DocumentSettings.placeholderMail')">
                </b-col>
                <b-col cols="1">
                    <button class="elsa-blue-btn" @click="addSignatory()" style="padding: 0.2em 0.4em;">
                        <b-icon icon="plus" style="width: 1.5em; height: 1.5em"></b-icon>
                    </button>
                </b-col>
            </b-row>
        </div>
        <div class="card" style="height:15em; overflow-y: auto; overflow-x: hidden">
            <draggable v-model="signatories">
                <div class="drag-drop-element" v-for="signatory in signatories" :key="signatory.email" style="padding:0.25em">
                    <b-row align-h="between">
                        <h6>
                            <b-col cols="auto">
                                <b-icon class="icon-hover" icon="trash" @click="deleteSignatory(signatory)"></b-icon>
                                {{signatory.email}}
                            </b-col>
                        </h6>
                        <b-col cols="auto">
                            <b-row align-h="end">
                                <b-col cols="auto">
                                    <select class="form-control form-control-sm" id="exampleFormControlSelect1" v-model="signatory.type">
                                        <option>einfach</option>
                                        <option>fortgeschritten</option>
                                    </select>
                                </b-col>
                                <b-col cols="auto">
                                    <b-icon icon="list" class="icon"></b-icon>
                                </b-col>
                            </b-row>
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
    name: "SignatoryMenu",
    props: {
        signatories: Array
    },
    components: {draggable},
    data() {
        return{signatoryInput: "", }
    },
    methods: {
        addSignatory() {
            if(this.signatories.includes(this.signatoryInput)) {
                // TODO: Error
            } else {
                this.signatories.push({email: this.signatoryInput, type: ""});
            }
            this.signatoryInput = "";
        },
        deleteSignatory(signatory) {
            this.signatories = this.signatories.filter(sig => !(sig === signatory))
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
