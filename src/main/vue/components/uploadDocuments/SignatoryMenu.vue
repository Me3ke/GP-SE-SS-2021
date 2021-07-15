<template>
    <div>
        <div v-if="!addSignatories && signatoryInputs.length === 0 && addressBookClosed">
            <button class="light-btn" @click="addSignatories = true">
                <h5>
                    <b-icon icon="plus-circle"></b-icon>
                    {{ $t('Settings.DocumentSettings.addSignatory') }}
                </h5>
            </button>
        </div>
        <div v-if="!addSignatories && !(signatoryInputs.length === 0) && addressBookClosed">
            <b-list-group-item style="height:2.5em; padding: 0.25em 0.75em" v-for="signatory in signatories"
                               :key="signatory.email"> {{ signatory.email }}
            </b-list-group-item>
            <b-row align-h="end">
                <b-col cols="auto">
                    <button class="light-btn" @click="addSignatories = true" style="margin:0.5em 0">
                        <h6>
                            <b-icon icon="pencil-fill"></b-icon>
                            {{ $t('Settings.DocumentSettings.edit') }}
                        </h6>
                    </button>
                </b-col>
            </b-row>
        </div>

        <div v-if="addSignatories && addressBookClosed">

            <!-- Add Signatory -->
            <div class="form-group">
                <b-row no-gutters>
                    <b-col cols="12">
                        <b-input-group class="mb-2">
                            <b-input-group-prepend is-text>
                                <b-icon icon="book" style="fill: var(--elsa-blue); cursor: pointer"
                                        @click="addressBook()"></b-icon>
                            </b-input-group-prepend>
                            <b-form-tags
                                class="form-control" v-model="signatoryInput" id="signatoryInput"
                                separator=","
                                :placeholder="$t('Settings.DocumentSettings.placeholderMail')"
                            ></b-form-tags>
                            <b-input-group-append>
                                <button class="elsa-blue-btn add-button" @click="addSignatory()"
                                        style="padding: 0.2em 0.4em;">
                                    <b-icon icon="plus" style="width: 1.5em; height: 1.5em"></b-icon>
                                </button>
                            </b-input-group-append>
                        </b-input-group>
                    </b-col>
                </b-row>
            </div>

            <!-- Set order relevant -->
            <b-row align-h="center" style="margin-bottom: 1em">
                <b-col class="custom-control custom-switch" style="margin-left:1em">
                    <input type="checkbox" class="custom-control-input" id="orderRelevantSwitch"
                           v-model="orderRelevantInput">
                    <label class="custom-control-label" for="orderRelevantSwitch">
                        {{ $t('Settings.DocumentSettings.orderRelevant') }} </label>
                </b-col>
            </b-row>

            <!-- List of Signatories -->
            <div class="card" style="height:15em; overflow-y: auto; overflow-x: hidden" v-if="signatories">
                <draggable v-model="signatoryInputs">
                    <div class="drag-drop-element" v-for="signatory in signatoryInputs" :key="signatory.email"
                         style="padding:0.25em">
                        <b-row align-h="between">
                            <!-- Email Address -->
                            <h6>
                                <b-col cols="auto">
                                    <b-icon class="icon-hover" icon="trash"
                                            @click="deleteSignatory(signatory)"></b-icon>
                                    {{ signatory.email }}
                                </b-col>
                            </h6>
                            <b-col cols="auto">
                                <b-row align-h="end">
                                    <!-- signature type -->
                                    <b-col cols="auto">
                                        <select class="form-control form-control-sm" id="exampleFormControlSelect1"
                                                v-model="signatory.type">
                                            <option v-for="signatureType in signatureTypes" :key="signatureType.value"
                                                    :value="signatureType.value"> {{ $t(signatureType.name) }}
                                            </option>
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
                        {{ $t('DownloadDoc.cancel') }}
                    </button>
                </b-col>
                <b-col cols="auto">
                    <button class="elsa-blue-btn" @click="save()">
                        {{ $t('Settings.DocumentSettings.save') }}
                    </button>
                </b-col>
            </b-row>
        </div>
    </div>
</template>

<script>
import draggable from 'vuedraggable'
import {mapGetters} from "vuex";

export default {
    name: "SignatoryMenu",
    props: {
        signatories: Array,
        addressBookClosed: Boolean
    },
    components: {draggable},
    computed: {
      ...mapGetters({
          allUser: 'userManagement/getAllUsers', // for checking if new registered signatories are getting added for the email templates
          documentProgress: 'document/getDocumentProgressArray',
          document: 'document/getDocumentInfo'
      })
    },
    data() {
        return {
            signatoryInput: [],
            signatoryInputs: [],
            orderRelevantInput: false,
            addSignatories: false,
            noticeNewSignatories: false,
            signatureTypes: [{
                name: 'UploadDoc.simple',
                value: 1
            }, {
                name: 'UploadDoc.advanced',
                value: 2
            }]
        }
    },

    mounted() {
        this.signatoryInputs = this.signatories
    },
    methods: {
        addSignatory() {
            console.log('before add: ', this.signatoryInputs)

            for(let i = 0; i < this.signatoryInput.length; i++) {
                if(this.signatoryInputs.some(signatory => signatory.email === this.signatoryInput[i])) {
                    console.log(this.signatoryInput[i]) // TODO Error
                } else {
                    // if cause for the search after new added signatories
                    if(this.allUser.some(user => user.email === this.signatoryInput[i])) {
                        this.noticeNewSignatories = true
                    }
                    this.signatoryInputs.push({email: this.signatoryInput[i], type: 1});
                }
            }
            console.log('after add: ', this.signatoryInputs)
            this.signatoryInput = [];
        },
        deleteSignatory(signatory) {
            let progressBeforeDelete = this.documentProgress.find(user => parseInt(user.docId) === this.document.id)
            const resultBeforeDelete = this.signatoryInputs.filter(({ email: id1 }) => !progressBeforeDelete.data.signatories.some(({ email: id2 }) => id2 === id1));

            if(resultBeforeDelete.some(user => user.email === signatory.email)){
                this.noticeNewSignatories = false
            }

            this.signatoryInputs.splice(this.signatoryInputs.indexOf(signatory), 1) // delete selected signatory
            let progress = this.documentProgress.find(user => parseInt(user.docId) === this.document.id)
            const results = this.signatoryInputs.filter(({ email: id1 }) => !progress.data.signatories.some(({ email: id2 }) => id2 === id1));
            for(let i = 0; i < results.length; i++) {
                if(this.allUser.some(user => user.email === results[i].email)) {
                    this.noticeNewSignatories = true
                    break
                } else {
                    this.noticeNewSignatories = false
                }
            }
            this.$emit('noticeNewSignatories', this.noticeNewSignatories)
        },
        cancel() {
            this.addSignatories = false;
            this.signatoryInputs = this.signatories;
            this.signatoryInput = [];
            this.orderRelevantInput = false;
        },
        save() {
            this.$emit('updateOrderRelevant', this.orderRelevantInput);
            this.$emit('updateSignatories', this.signatoryInputs);
            if(!this.noticeNewSignatories) {
                let progress = this.documentProgress.find(user => parseInt(user.docId) === this.document.id)
                const results = this.signatoryInputs.filter(({email: id1}) => !progress.data.signatories.some(({email: id2}) => id2 === id1));
                for (let i = 0; i < results.length; i++) {
                    if (this.allUser.some(user => user.email === results[i].email)) {
                        this.noticeNewSignatories = true
                        break
                    } else {
                        this.noticeNewSignatories = false
                    }
                }
            }


            console.log("After Save: ", this.noticeNewSignatories)
            this.$emit('noticeNewSignatories', this.noticeNewSignatories)
            this.addSignatories = false;
        },
        addressBook() {
            this.$emit('showAddressBook')
        },

        async fetchAllUser() {
            await this.$store.dispatch('userManagement/fetchAllUsers')
        }
    },
     beforeMount() {
         this.fetchAllUser()

         if (this.signatories.length !== 0) {
            this.addSignatories = false
            this.signatoryInputs = this.signatories;

            let progress = this.documentProgress.find(user => parseInt(user.docId) === this.document.id)
            const results = this.signatoryInputs.filter(({ email: id1 }) => !progress.data.signatories.some(({ email: id2 }) => id2 === id1));
            console.log(progress)
             console.log(results)
            for(let i = 0; i < results.length; i++) {
                if(this.allUser.some(user => user.email === results[i].email)) {
                    this.noticeNewSignatories = true
                    break
                } else {
                    this.noticeNewSignatories = false
                }
            }
            this.$emit('noticeNewSignatories', this.noticeNewSignatories)
            }
    }
}
</script>

<style scoped>
.btn-outline-secondary {
    visibility: hidden !important;
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

.add-button {
    margin: 0;
    border-color: var(--elsa-blue);
}

.form-control {
    background-color: var(--whitesmoke);
    color: var(--dark-grey);
    border: solid 0.1em var(--elsa-blue);
    border-radius: 0.2em;
}

.input-group > .form-control, .input-group > .form-control-plaintext, .input-group > .custom-select, .input-group > .custom-file {
    height: 1.85em;
    width: 23em;
    max-width: 24em;
}

.form-control:focus, .form-control:active, .b-form-tags.focus, .b-form-tags.active {
    color: var(--dark-grey);
    background-color: var(--whitesmoke);
    border-color: var(--elsa-blue);
    outline: 0;
    box-shadow: 0 0 0 0.2rem var(--elsa-blue-transparent);
}

.form-control::placeholder {
    color: var(--shadow-grey);
    opacity: 1; /* Firefox */
}

.form-control:-ms-input-placeholder {
    color: var(--shadow-grey);
}

.form-control::-ms-input-placeholder {
    color: var(--shadow-grey);
}

/* Settings for differently sized screens */
@media (max-width: 575.98px) {
    .form-control, .input-group > .input-group-prepend > .input-group-text, .input-group-append {
        font-size: 0.5em;
    }

    .input-group > .form-control, .input-group > .form-control-plaintext, .input-group > .custom-select, .input-group > .custom-file, #search {
        width: 32em;
        max-width: 32em;
    }
}

@media (min-width: 576px) and (max-width: 767.98px) {
    .form-control, .input-group > .input-group-prepend > .input-group-text, .input-group-append {
        font-size: 0.7em;
    }

    .input-group > .form-control, .input-group > .form-control-plaintext, .input-group > .custom-select, .input-group > .custom-file, #search {
        width: 34em;
        max-width: 34em;
    }
}

@media (min-width: 768px) and (max-width: 991.98px) and (max-height: 499.98px) {
    .form-control, .input-group > .input-group-prepend > .input-group-text, .input-group-append {
        font-size: 0.8em;
    }

    .input-group > .form-control, .input-group > .form-control-plaintext, .input-group > .custom-select, .input-group > .custom-file, #search {
        width: 29.75em;
        max-width: 29.75em;
    }
}

@media (min-width: 768px) and (max-width: 991.98px) and (min-height: 500px) {
    .form-control, .input-group > .input-group-prepend > .input-group-text, .input-group-append {
        font-size: 0.8em;
    }

    .input-group > .form-control, .input-group > .form-control-plaintext, .input-group > .custom-select, .input-group > .custom-file, #search {
        width: 29.75em;
        max-width: 29.75em;
    }
}

</style>
