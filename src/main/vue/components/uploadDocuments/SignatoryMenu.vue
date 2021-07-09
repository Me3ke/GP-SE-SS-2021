<template>
    <div>
        <div v-if="!addSignatories && signatoryInputs.length === 0">
            <button class="light-btn" @click="addSignatories = true">
                <h5>
                    <b-icon icon="plus-circle"></b-icon>
                    {{$t('Settings.DocumentSettings.addSignatory')}}
                </h5>
            </button>
        </div>
        <div v-if="!addSignatories && !(signatoryInputs.length === 0)">
            <b-list-group-item style="height:2.5em; padding: 0.25em 0.75em" v-for="signatory in signatories" :key="signatory.email"> {{signatory.email}}</b-list-group-item>
            <b-row align-h="end">
                <b-col cols="auto">
                    <button class="light-btn" @click="addSignatories = true" style="margin:0.5em 0">
                        <h6>
                            {{$t('Settings.DocumentSettings.edit')}}
                        </h6>
                    </button>
                </b-col>
            </b-row>
        </div>

        <div v-if="addSignatories">

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

            <!-- Set order relevant -->
            <b-row align-h="center" style="margin-bottom: 1em">
                <b-col class="custom-control custom-switch" style="margin-left:1em">
                    <input type="checkbox" class="custom-control-input" id="orderRelevantSwitch" v-model="orderRelevantInput">
                    <label class="custom-control-label" for="orderRelevantSwitch" > {{$t('Settings.DocumentSettings.orderRelevant')}} </label>
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
                                        <select class="form-control form-control-sm" id="exampleFormControlSelect1" v-model="signatory.type">
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

            <b-row align-h="end" style="margin-top:0.5em">
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
import draggable from 'vuedraggable'
export default {
    name: "SignatoryMenu",
    props: {
        signatories: Array
    },
    components: {draggable},
    data() {
        return {
            signatoryInput: "",
            signatoryInputs: [],
            orderRelevantInput: false,
            addSignatories: false,
            signatureTypes: [{
                name: 'UploadDoc.simple',
                value: 1
            }, {
                name: 'UploadDoc.advanced',
                value: 2
            }]
        }
    },
    methods: {
        addSignatory() {
            if(this.signatoryInputs.includes(this.signatoryInput)) {
                // TODO: Error
            } else {
                this.signatoryInputs.push({email: this.signatoryInput, type: 1});
            }
            this.signatoryInput = "";
        },
        deleteSignatory(signatory) {
            this.signatoryInputs.splice(this.signatoryInputs.indexOf(signatory), 1)
        },
        cancel() {
            this.addSignatories = false;
            this.signatoryInputs = this.signatories;
            this.signatoryInput = "";
            this.orderRelevantInput = false;
        },
        save() {
            this.$emit('updateOrderRelevant', this.orderRelevantInput);
            this.$emit('updateSignatories', this.signatoryInputs);
            this.addSignatories = false;
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
