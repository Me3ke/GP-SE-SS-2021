<template>
    <div>
        <h3>
            <b-icon class="filterIcon" icon="funnel" @click="show = true; initFilter()"></b-icon>
        </h3>
        <div v-if="show">
            <transition>
                <div class="modal-mask">
                    <div class="modal-wrapper">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title">{{$t('Filter.filterTitle')}}</h5>
                                    <h5>
                                        <b-icon type="button" icon="x-square" @click="show = false;">
                                        </b-icon>
                                    </h5>
                                </div>
                                <!-- Menu -->
                                <div class="modal-body">
                                    <!-- state -->
                                    Status
                                    <div class="form-group">
                                        <select class="form-control" v-model="filterInput.state">
                                            <option value="">{{$t('Filter.all')}}</option>
                                            <option value="DRAFT">{{$t('Document.draft')}}</option>
                                            <option value="REVIEWSIGN">{{$t('Filter.open')}}</option>
                                            <option value="SIGN">{{$t('Filter.signProcess')}}</option>
                                            <option value="REVIEW">{{$t('Filter.readProcess')}}</option>
                                            <option value="ARCHIVED">{{$t('Filter.closed')}}</option>
                                        </select>
                                    </div>
                                    <!-- Creation Date Span / Day -->
                                    {{$t('Filter.creationDate')}}
                                    <b-icon icon="arrow-counterclockwise" class="filterIcon" @click="resetCreationDate()"></b-icon>
                                    <b-row>
                                        <b-col>
                                            <b-form-datepicker class="my-date-picker" v-model="filterInput.creationDateMin"></b-form-datepicker>
                                        </b-col>
                                        -
                                        <b-col>
                                            <b-form-datepicker class="my-date-picker" v-model="filterInput.creationDateMax"></b-form-datepicker>
                                        </b-col>
                                    </b-row>
                                    <!-- End Date Span / Day -->
                                    {{$t('Filter.endDate')}}
                                    <b-icon icon="arrow-counterclockwise" class="filterIcon" @click="resetEndDate()"></b-icon>
                                    <b-row>
                                        <b-col>
                                            <b-form-datepicker class="my-date-picker" v-model="filterInput.endDateMin"></b-form-datepicker>
                                        </b-col>
                                        -
                                        <b-col>
                                            <b-form-datepicker class="my-date-picker" v-model="filterInput.endDateMax"></b-form-datepicker>
                                        </b-col>
                                    </b-row>
                                    <!-- data type -->
                                    <div class="form-group">
                                        {{$t('Filter.dataType')}}
                                        <select class="form-control" v-model="filterInput.dataType">
                                            <option value="">{{$t('Filter.all')}}</option>
                                            <option v-for="type in allDataTypes()" :key="type">{{type}}</option>
                                        </select>
                                    </div>
                                    <!-- role -->
                                    {{$t('Filter.onlyRoles')}}
                                    <b-icon icon="arrow-counterclockwise" class="filterIcon" @click="resetRole()"></b-icon>
                                    <b-row>
                                        <input class="form-check-input" type="checkbox" v-model="filterInput.owner" id="checkOwner">
                                        <label class="form-check-label" for="checkOwner">
                                            {{$t('Filter.owner')}}
                                        </label>
                                    </b-row>
                                    <div class="form-check" style="padding: 0">
                                        <b-row>
                                            <input class="form-check-input" type="radio" name="role" id="checkReader" value="reader" v-model="filterInput.role">
                                            <label class="form-check-label" for="checkReader">
                                                {{$t('Filter.reader')}}
                                            </label>
                                        </b-row>
                                        <b-row>
                                            <input class="form-check-input" type="radio" name="role" id="checkSignatory" value="signatory" v-model="filterInput.role">
                                            <label class="form-check-label" for="checkSignatory">
                                                {{$t('Filter.signatory')}}
                                            </label>
                                        </b-row>
                                        <b-row>
                                            <input class="form-check-input" type="radio" name="role" id="checkReaderSignatory" value="readerOrSignatory" v-model="filterInput.role">
                                            <label class="form-check-label" for="checkReaderSignatory">
                                                {{$t('Filter.readerOrSignatory')}}
                                            </label>
                                        </b-row>
                                    </div>
                                </div>
                                <!-- Buttons -->
                                <div class="modal-footer">
                                    <b-container fluid>
                                        <b-row align-h="end">
                                            <button type="button" class="light-btn" @click="resetFilter(); show = false">
                                                <h5>
                                                    {{$t('Filter.removeFilter')}}
                                                </h5>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" @click="setFilter(); show = false">
                                                <h5>
                                                    {{$t('Filter.filter')}}
                                                </h5>
                                            </button>
                                        </b-row>
                                    </b-container>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </transition>
        </div>
    </div>
</template>


<script>
import {mapGetters} from "vuex";

export default {
    name: "FilterMenu",
    props: {
        filter: Object
    },
    data() {
        return{
            show: false,
            filterInput: {
                owner: false,
                role: "",
                state: "",
                creationDateMin: "",
                creationDateMax: "",
                endDateMin: "",
                endDateMax: "",
                dataType: ""
            },
            newFilter: this.filter,
            signatureTypes: [{
                name: 'Filter.all',
                value: 0
            }, {
                name: 'UploadDoc.simple',
                value: 1
            }, {
                name: 'UploadDoc.advanced',
                value: 2
            }],

        }
    },
    created() {
        this.$store.dispatch('envelopes/fetchEnvelopes', {})
    },
    computed: {
        ...mapGetters({
            allEnvelopes: 'envelopes/getEnvelopes',
        })
    },
    methods: {
        allDataTypes() {
            let dataTypes = [];
            let i;
            for(i = 0; i < this.allEnvelopes.length; i++) {
                let envelope = this.allEnvelopes[i]
                let j;
                for(j = 0; j < envelope.documents.length; j++) {
                    let document = envelope.documents[j];
                    if(!dataTypes.includes(document.dataType)) {
                        dataTypes.push(document.dataType);
                    }
                }
            }
            return dataTypes;
        },
        resetFilter() {
            this.filterInput = {
                owner: false,
                role: "",
                state: "",
                creationDateMin: "",
                creationDateMax: "",
                endDateMin: "",
                endDateMax: "",
                dataType: ""
            };
            this.setFilter();
        },
        resetEndDate() {
            this.filterInput.endDateMin = "";
            this.filterInput.endDateMax = "";
        },
        resetCreationDate() {
            this.filterInput.creationDateMin = "";
            this.filterInput.creationDateMax = "";
        },
        resetRole() {
            this.filterInput.role = null;
        },
        setFilter() {
            this.newFilter.owner = this.filterInput.owner;
            if(this.filterInput.role === "readerOrSignatory") {
                this.newFilter.reader = true;
                this.newFilter.signatory = true;
            } else if(this.filterInput.role === "reader") {
                this.newFilter.reader = true;
                this.newFilter.signatory = false;
            } else if(this.filterInput.role === "signatory") {
                this.newFilter.reader = false;
                this.newFilter.signatory = true;
            } else {
                this.newFilter.reader = false;
                this.newFilter.signatory = false
            }
            this.newFilter.creationDateMin = this.filterInput.creationDateMin;
            this.newFilter.creationDateMax = this.filterInput.creationDateMax;
            this.newFilter.endDateMin = this.filterInput.endDateMin;
            this.newFilter.endDateMax = this.filterInput.endDateMax;
            this.newFilter.dataType = this.filterInput.dataType;
            this.newFilter.state = this.filterInput.state;
        },
        initFilter() {
            this.filterInput.owner = this.filter.owner;
            if(this.filter.signatory && this.filter.reader) {
                this.filterInput.role = "readerOrSignatory"
            } else if(this.filter.signatory) {
                this.filterInput.role = "signatory"
            } else if(this.filter.reader) {
                this.filterInput.role = "reader"
            } else {
                this.filterInput.role = ""
            }
            this.filterInput.creationDateMin = this.filter.creationDateMin;
            this.filterInput.creationDateMax = this.filter.creationDateMax;
            this.filterInput.endDateMin = this.filter.endDateMin;
            this.filterInput.endDateMax = this.filter.endDateMax;
            this.filterInput.dataType = this.filter.dataType;
            this.filterInput.state = this.filter.state;
        }
    }
}
</script>

<style scoped>
.modal-mask {
    position: fixed;
    z-index: 10000;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, .5);
    display: table;
}

.modal-wrapper {
    display: table-cell;
    vertical-align: middle;
}

.modal-content {
    background-color: var(--whitesmoke);
}

.filterIcon {
    fill: var(--dark-grey);
}

.filterIcon:hover {
    fill: var(--light-grey);
    transition-duration: 0.4s;
}

.row {
    padding: 0.5em 2em
}

.elsa-blue-btn, .light-btn {
    padding: 0.5vh 1vw 0;
    border: 0.03vw solid var(--dark-grey);
    margin: 0.25vh 0.25vw;
}

.elsa-blue-btn:focus, .light-btn:focus {
    border: 0.03vw solid var(--dark-grey);
}

.form-control {
    background-color: var(--whitesmoke);
    color: var(--dark-grey);
}

.form-control:focus {
    background-color: var(--whitesmoke);
    color: var(--dark-grey);
}

.my-date-picker >>> .dropdown-menu {
    background-color: var(--whitesmoke);
    color: var(--dark-grey);
}

.btn-outline-primary {
    color: var(--elsa-blue);
}

.btn {
    color: var(--dark-grey);
}
</style>
