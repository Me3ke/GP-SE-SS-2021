<template>
    <div style="overflow-x: hidden">
        <div class="modal-body">
            <!-- Pick Deadline -->
            <b-alert :show="this.error.noEndDate">
                {{ $t('UploadDoc.error.noEndDate') }}
            </b-alert>
            <div>
                <h6>{{ $t('Settings.DocumentSettings.chooseDate') }}</h6>
                <b-row style="margin-bottom: 0.5em">
                    <b-col cols="6">
                        <b-form-datepicker class="mb-2" v-model="endDate"></b-form-datepicker>
                    </b-col>
                    <b-col cols="6">
                        <b-form-timepicker v-model="endTime" :locale="this.$i18n.locale"></b-form-timepicker>
                    </b-col>
                </b-row>
            </div>

            <!-- Add Readers and Signatories -->
            <b-alert :show="this.error.noSignatories">
                {{ $t('UploadDoc.error.noReadSig') }}
            </b-alert>

            <!-- Add readers -->
            <h6>{{ $t('Settings.DocumentSettings.reader') }}</h6>
            <transition-group name="slide" mode="out-in">
                <ReaderMenu :address-book-closed="showReaderMenu" :readers="readers" @updateReaders="updateReaders"
                            @showAddressBook="addressBookToggle(false)" key="1"></ReaderMenu>

                <!-- Select from AddressBook -->
                <AddressBookSelection v-if="showAddressBook && !addressBookMode" :signatories="signatories"
                                      :readers="readers"
                                      :sign="addressBookMode" style="margin-bottom: 0.5rem;"
                                      @showAddressBook="addressBookToggle(false)" key="2"></AddressBookSelection>
            </transition-group>


            <!-- Add signatories -->
            <b-alert :show="this.error.noSignatureType">
                {{ $t('UploadDoc.error.noSignatureType') }}
            </b-alert>

            <h6>{{ $t('Settings.DocumentSettings.signatory') }}</h6>
            <transition-group name="slide" mode="out-in">
                <SignatoryMenu :address-book-closed="showSignatoryMenu" :signatories="signatories"
                               @updateSignatories="updateSignatories"
                               @updateOrderRelevant="updateOrderRelevant"
                               @showAddressBook="addressBookToggle(true)" key="3"></SignatoryMenu>

                <!-- Select from AddressBook -->
                <AddressBookSelection v-if="showAddressBook && addressBookMode" :signatories="signatories"
                                      :readers="readers"
                                      :sign="addressBookMode" style="margin-bottom: 0.5rem;"
                                      @showAddressBook="addressBookToggle(true)" key="4"></AddressBookSelection>
            </transition-group>


        </div>
        <div class="modal-footer">
            <b-container fluid>
                <b-row align-h="end">
                    <b-col cols="auto">
                        <button type="button" class="light-btn" @click="back(); reviewAddSignatory = false">
                            <h5>
                                {{ $t('UploadDoc.back') }}
                            </h5>
                        </button>
                    </b-col>
                    <b-col cols="auto">
                        <button type="button" class="elsa-blue-btn" @click="noProcess()">
                            <h5>
                                {{ $t('UploadDoc.startProcessLater') }}
                            </h5>
                        </button>
                    </b-col>
                    <b-col cols="auto">
                        <button type="button" class="elsa-blue-btn" @click="startProcess()">
                            <h5>
                                {{ $t('UploadDoc.startProcess') }}
                            </h5>
                        </button>
                    </b-col>
                </b-row>
            </b-container>
        </div>
    </div>
</template>

<script>
import SignatoryMenu from "@/main/vue/components/uploadDocuments/SignatoryMenu";
import ReaderMenu from "@/main/vue/components/uploadDocuments/ReaderMenu";
import AddressBookSelection from "@/main/vue/components/uploadDocuments/AddressBookSelection";

export default {
    name: "UploadSettings",
    components: {
        AddressBookSelection,
        SignatoryMenu,
        ReaderMenu
    },
    data() {
        return {
            endDate: null,
            endTime: null,
            readers: [],
            signatories: [],
            orderRelevant: false,
            error: {
                noSignatories: false,
                noSignatureType: false,
                noEndDate: false,
            },
            addressBookMode: false,
            showAddressBook: false,
            showReaderMenu: true,
            showSignatoryMenu: true
        }
    },
    methods: {
        addressBookToggle(mode) {
            this.addressBookMode = mode
            if (mode) {
                this.showSignatoryMenu = !this.showSignatoryMenu
            } else {
                this.showReaderMenu = !this.showReaderMenu
            }
            this.showAddressBook = !this.showAddressBook
        },
        updateReaders(readers) {
            this.readers = readers;
        },
        updateSignatories(signatories) {
            this.signatories = signatories;
        },
        updateOrderRelevant(orderRelevant) {
            this.orderRelevant = orderRelevant;
        },
        back() {
            this.endDate = null;
            this.endTime = null;
            this.readers = [];
            this.signatories = [];
            this.orderRelevant = false;
            this.error = {
                noSignatories: false,
                noSignatureType: false,
                noEndDate: false,
            }
            this.$emit('previousPage')
        },
        noProcess() {
            let settings = {endDate: "", orderRelevant: false, signatories: [], draft: true};

            // set end date
            if (!(this.endTime) && !(this.endDate === null)) {
                let time = this.endTime.split(":")
                settings.endDate = this.settings.endDate + ' ' + time[0] + ':' + time[1];
            }
            // set signatories
            let i;
            for (i = 0; i < this.readers.length; i++) {
                settings.signatories.push({email: this.readers[i].email, type: 0})
            }
            for (i = 0; i < this.signatories.length; i++) {
                settings.signatories.push({email: this.signatories[i].email, type: this.signatories[i].signatureType})
            }

            //set order relevant
            settings.orderRelevant = this.orderRelevant;

            this.$emit('updateSettings', settings)
            this.$emit('nextPage')
        },
        startProcess() {
            if (this.validate()) {
                let settings = {endDate: "", orderRelevant: false, signatories: [], draft: false};

                // set end date
                let time = this.endTime.split(":")
                settings.endDate = this.endDate + ' ' + time[0] + ':' + time[1];

                // set signatories
                let i;
                for (i = 0; i < this.readers.length; i++) {
                    settings.signatories.push({email: this.readers[i].email, type: 0})
                }
                for (i = 0; i < this.signatories.length; i++) {
                    settings.signatories.push({email: this.signatories[i].email, type: this.signatories[i].type})
                }

                //set order relevant
                settings.orderRelevant = this.orderRelevant;

                this.$emit('updateSettings', settings)
                this.$emit('nextPage')
            }
        },
        validate() {
            this.error.noEndDate = this.endDate === null || this.endTime === null;
            this.error.noSignatories = this.readers.length === 0 && this.signatories.length === 0;
            this.error.noSignatureType = false;
            let i;
            for (i = 0; i < this.signatories.length; i++) {
                if (!(this.signatories[i].type === 1 || this.signatories[i].type === 2)) {
                    this.error.noSignatureType = true;
                }
            }
            return !this.error.noEndDate && !this.error.noSignatureType && !this.error.noSignatories;
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

.slide-leave-active,
.slide-enter-active {
    transition: 0.5s;
}

.slide-enter {
    transform: translate(100%, 0);
}

.slide-leave-to {
    transform: translate(-100%, 0);
}
</style>
