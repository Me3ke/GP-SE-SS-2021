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
        <div>
            <div class="custom-control custom-switch">
                <input type="checkbox" class="custom-control-input" id="orderRelevantSwitch" v-model="orderRelevant">
                <label class="custom-control-label" for="orderRelevantSwitch"> {{$t('Settings.DocumentSettings.orderRelevant')}} </label>
            </div>
        </div>
        <div class="card" style="height:15em; overflow-y: auto; overflow-x: hidden; background-color: var(--whitesmoke); color: var(--dark-grey); border: 1px solid var(--dark-grey)">
            <draggable v-model="signatoriesNew">
                <div class="drag-drop-element" v-for="signatory in signatoriesNew" :key="signatory.email ==='' || signatory.email == null ? signatory.user.email : signatory.email" style="padding:0.25em">
                    <b-row align-h="between">
                        <h6>
                            <b-col cols="auto">
                                <b-icon class="icon-hover" icon="trash" @click="deleteSignatory(signatory)"></b-icon>
                                {{signatory.email ==='' || signatory.email == null ? signatory.user.email : signatory.email}}
                            </b-col>
                        </h6>
                        <b-col cols="auto">
                            <b-row align-h="end">
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
    </div>
</template>

<script>
import draggable from 'vuedraggable'
export default {
    name: "SignatoryMenu",
    props: {
        signatories: {
            type: [
                Array,
                Object
            ]
        },
        orderRelevant: Boolean
    },
    components: {draggable},
    data() {
        return{
            signatoriesNew: [],
            signatoryInput: "",
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

            if(this.signatoriesNew.includes(this.signatoryInput)) {
                // TODO: Error
            } else {
                this.signatoriesNew.push({email: this.signatoryInput, type: ""});
            }
            this.signatoryInput = "";

            this.$emit('update-signatories', this.signatoriesNew)
        },
        deleteSignatory(signatory) {
            this.signatoriesNew = this.signatoriesNew.filter(sig => !(sig === signatory))

            this.$emit('update-signatories', this.signatoriesNew)
        }

    },

    mounted() {
        this.signatoriesNew = this.signatories

        for(let i = 0; i < this.signatoriesNew.length; i++) {
            if(this.signatoriesNew[i].signatureType === "ADVANCED_SIGNATURE") {
                this.signatoriesNew[i].type = 2
            } else if(this.signatoriesNew[i].signatureType === "SIMPLE_SIGNATURE") {
                this.signatoriesNew[i].type = 1
            }
        }
        this.$emit('update-signatories', this.signatoriesNew)

    }
}
</script>

<style scoped>

#exampleFormControlSelect1 {
    background-color: var(--whitesmoke);
    color: var(--dark-grey);
}

.form-control {
    background-color: var(--whitesmoke);
    color: var(--dark-grey);
}

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
