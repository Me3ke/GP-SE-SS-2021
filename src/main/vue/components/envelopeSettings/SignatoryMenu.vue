<template>
    <div>
        <!-- Add Signatory -->
        <div class="form-group">
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

        <!-- Options for order and reminder -->
        <b-row align-h="center" style="margin-bottom: 1em">
            <b-col class="custom-control custom-switch" style="margin-left:1em">
                <input type="checkbox" class="custom-control-input" id="orderRelevantSwitch" v-model="orderRelevant">
                <label class="custom-control-label" for="orderRelevantSwitch" > {{$t('Settings.DocumentSettings.orderRelevant')}} </label>
            </b-col>
            <b-col v-if="!inModal">
                <b-row>
                    <b-form-checkbox v-model="remind" name="some-radios">
                        {{$t('UploadDoc.remindSignatories')}}
                    </b-form-checkbox>
                </b-row>
                <b-row v-if="remind" style="margin-top: 1em">
                    <b-form-input type="number" v-model="reminderTiming" min="0" style="width:5em;"> </b-form-input>
                    {{$t('UploadDoc.remindDaysBefore')}}
                </b-row>
            </b-col>
        </b-row>



        <!-- List of Signatories -->
        <div class="card" style="height:15em; overflow-y: auto; overflow-x: hidden">
            <draggable v-model="signatoriesArray">
                <div class="drag-drop-element" v-for="signatory in signatoriesArray" :key="signatory.email" style="padding:0.25em">
                    <b-row align-h="between">
                        <!-- Email Address -->
                        <h6>
                            <b-col cols="auto">
                                <b-icon class="icon-hover" icon="trash" @click="deleteSignatory(signatory)"></b-icon>
                                {{signatory.email}}
                            </b-col>
                        </h6>
                        <b-col cols="auto">
                            <b-row align-h="end">
                                <!-- reminder -->
                                <b-col cols="auto" v-if="!inModal && remind" style="margin-right: 0.5em">
                                    <b-row>
                                        {{$t('UploadDoc.reminder')}}
                                        <b-form-input type="number" v-model="signatory.reminderTiming" min="0" style="width:5em; height: 2em; margin-right: 0.5em"> </b-form-input>
                                        {{$t('UploadDoc.reminderShort')}}
                                    </b-row>
                                </b-col>
                                <!-- signature type -->
                                <b-col cols="auto">
                                    <select class="form-control form-control-sm" id="exampleFormControlSelect1" v-model="signatory.signatureType">
                                        <option v-for="signatureType in signatureTypes" :key="signatureType.value" :value="signatureType.value"> {{$t(signatureType.name)}} </option>
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
        signatories: Array,
        orderRelevant: Boolean,
        inModal: Boolean,
    },
    components: {draggable},
    data() {
        return{
            signatoryInput: "",
            signatureTypes: [{
                name: 'UploadDoc.simple',
                value: 1
            }, {
                name: 'UploadDoc.advanced',
                value: 2
            }],
            signatoriesArray: this.signatories,
            remind: false,
            reminderTiming: null
        }
    },
    methods: {
        addSignatory() {
            if(this.signatoriesArray.includes(this.signatoryInput)) {
                // TODO: Error
            } else {
                this.signatoriesArray.push({email: this.signatoryInput, type: "", reminder: ""});
            }
            this.signatoryInput = "";
        },
        deleteSignatory(signatory) {
            this.signatoriesArray.splice(this.signatoriesArray.indexOf(signatory), 1)
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
