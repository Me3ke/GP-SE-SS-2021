<template>
    <div>
        <div v-if="!this.editSignatories">
            <div>
                <SignatoryListItem v-for="signatory in signatories" :key="signatory.email" :signatory="signatory"></SignatoryListItem>
            </div>

            <b-list-group-item v-if="signatories.length === 0">
                {{$t('Settings.DocumentSettings.noSignatories')}}
            </b-list-group-item>

            <b-row align-h="end">
                <button class="elsa-blue-btn" style="width:10em; margin: 0.5em 2.5em" @click="editSignatories = true;">
                    <b-icon icon="pencil-fill"></b-icon>
                    {{$t('Settings.DocumentSettings.edit')}}
                </button>
            </b-row>
        </div>

        <div v-if="this.editSignatories" style="padding: 2em">
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
                    <input type="checkbox" class="custom-control-input" id="orderRelevantSwitch" v-model="orderRelevantInput">
                    <label class="custom-control-label" for="orderRelevantSwitch" > {{$t('Settings.DocumentSettings.orderRelevant')}} </label>
                </b-col>
                <b-col>
                    <b-row>
                        <b-form-checkbox v-model="remindInput" name="some-radios">
                            {{$t('UploadDoc.remindSignatories')}}
                        </b-form-checkbox>
                    </b-row>
                    <b-row v-if="remindInput" style="margin-top: 1em">
                        <b-form-input type="number" v-model="reminderTimingInput" min="0" style="width:5em;"> </b-form-input>
                        {{$t('UploadDoc.remindDaysBefore')}}
                    </b-row>
                </b-col>
            </b-row>

            <!-- List of Signatories -->
            <div class="card" style="height:15em; overflow-y: auto; overflow-x: hidden">
                <draggable v-model="signatoryInputs">
                    <div class="drag-drop-element" v-for="signatory in signatoryInputs" :key="signatory.email" style="padding:0.25em">
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

            <b-row align-h="end">
                <button style="width:8em; margin:1em" class="light-btn" @click="editSignatories = false"> {{$t('DownloadDoc.cancel')}} </button>
                <button style="width:8em; margin:1em" class="elsa-blue-btn" @click="saveSignatories()"> {{$t('Settings.DocumentSettings.save')}} </button>
            </b-row>
        </div>
    </div>
</template>

<script>
import draggable from 'vuedraggable'
import SignatoryListItem from "@/main/vue/components/envelopeSettings/SignatoryListItem";
export default {
    name: "SignatorySettings",
    props: {
        signatories: Array,
        orderRelevant: Boolean,
    },
    components: {draggable, SignatoryListItem},
    data() {
        let remind = false;
        let reminderTiming = null;
        if(this.signatories.length > 0) {
            remind = this.signatories[0].remind;
            reminderTiming = this.signatories[0].reminderTiming;
        }
        return{
            editSignatories: false,
            signatoryInput: "",
            signatureTypes: [{
                name: 'UploadDoc.simple',
                value: 1
            }, {
                name: 'UploadDoc.advanced',
                value: 2
            }],
            signatoryInputs: this.signatories,
            remindInput: remind,
            reminderTimingInput: reminderTiming,
            orderRelevantInput: this.orderRelevant
        }
    },
    methods: {
        addSignatory() {
            if(this.signatoryInputs.includes(this.signatoryInput)) {
                // TODO: Error
            } else {
                this.signatoryInputs.push({email: this.signatoryInput, signatureType: ""});
            }
            this.signatoryInput = "";
        },
        deleteSignatory(signatory) {
            this.signatoryInputs.splice(this.signatoryInputs.indexOf(signatory), 1)
        },
        saveSignatories() {
            this.$emit('updateSignatories', this.makeSignatorySettings(), this.orderRelevantInput);
            this.signatoryInput = "";
            this.editSignatories = false;
        },
        makeSignatorySettings() {
            let reminderTiming;
            if(this.reminderTimingInput === null){
                reminderTiming = -1;
            } else {
                reminderTiming = this.reminderTimingInput;
            }
            let signatorySettings = [];
            let i;
            for (i = 0; i < this.signatoryInputs.length; i++) {
                let signatory = this.signatoryInputs[i];
                let signatorySetting = {email: signatory.email, signatureType: signatory.signatureType, status: this.getStatus(signatory), remind: this.remindInput, reminderTiming: reminderTiming, signedOn: this.getSignedOn(signatory)};
                signatorySettings.push(signatorySetting);
            }
            return signatorySettings;
        },
        getStatus(signatory) {
            let i;
            for (i = 0; i < this.signatories.length; i++) {
                if(this.signatories[i].email === signatory.email) {
                    return this.signatories[i].status;
                }
            }
            return false;
        },
        getSignedOn(signatory) {
            let i;
            for (i = 0; i < this.signatories.length; i++) {
                if(this.signatories[i].email === signatory.email) {
                    return this.signatories[i].signedOn;
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
</style>
