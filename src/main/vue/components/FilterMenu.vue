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
                                            <option value="OPENREAD">{{$t('Filter.open')}}</option>
                                            <option value="READ">{{$t('Filter.signProcess')}}</option>
                                            <option value="OPEN">{{$t('Filter.readProcess')}}</option>
                                            <option value="CLOSED">{{$t('Filter.closed')}}</option>
                                        </select>
                                    </div>
                                    <!-- Creation Date Span / Day -->
                                    {{$t('Filter.creationDate')}}
                                    <b-icon icon="arrow-counterclockwise" class="filterIcon" @click="resetCreationDate()"></b-icon>
                                    <b-row>
                                        <b-col>
                                            <b-form-datepicker class="mb-2" v-model="filterInput.creationDateMin"></b-form-datepicker>
                                        </b-col>
                                        -
                                        <b-col>
                                            <b-form-datepicker class="mb-2" v-model="filterInput.creationDateMax"></b-form-datepicker>
                                        </b-col>
                                    </b-row>
                                    <!-- End Date Span / Day -->
                                    {{$t('Filter.endDate')}}
                                    <b-icon icon="arrow-counterclockwise" class="filterIcon" @click="resetEndDate()"></b-icon>
                                    <b-row>
                                        <b-col>
                                            <b-form-datepicker class="mb-2" v-model="filterInput.endDateMin"></b-form-datepicker>
                                        </b-col>
                                        -
                                        <b-col>
                                            <b-form-datepicker class="mb-2" v-model="filterInput.endDateMax"></b-form-datepicker>
                                        </b-col>
                                    </b-row>
                                    <!-- data type -->
                                    <div class="form-group">
                                        {{$t('Filter.dataType')}}
                                        <select class="form-control" v-model="filterInput.dataType">
                                            <option>{{$t('Filter.all')}}</option>
                                            <option v-for="type in allDataTypes()" :key="type">{{type}}</option>
                                        </select>
                                    </div>
                                    <!-- owner -->
                                    <b-row >
                                        <input class="form-check-input" type="checkbox" v-model="filterInput.owner">
                                        {{$t('Filter.onlyOwnDocs')}}
                                    </b-row>
                                    <!-- signatory -->
                                    <b-row >
                                        <input class="form-check-input" type="checkbox" v-model="filterInput.signatory">
                                        {{$t('Filter.onlyToSign')}}
                                    </b-row>
                                    <!-- reader -->
                                    <b-row >
                                        <input class="form-check-input" type="checkbox" v-model="filterInput.reader">
                                        {{$t('Filter.onlyToRead')}}
                                    </b-row>

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
                signatory: false,
                reader: false,
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
                    let document = envelope.documents[i];
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
                signatory: false,
                reader: false,
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
        setFilter() {
            this.newFilter.owner = this.filterInput.owner;
            this.newFilter.signatory = this.filterInput.signatory;
            this.newFilter.reader = this.filterInput.reader;
            this.newFilter.creationDateMin = this.filterInput.creationDateMin;
            this.newFilter.creationDateMax = this.filterInput.creationDateMax;
            this.newFilter.endDateMin = this.filterInput.endDateMin;
            this.newFilter.endDateMax = this.filterInput.endDateMax;
            this.newFilter.dataType = this.filterInput.dataType;
            this.newFilter.state = this.filterInput.state;
        },
        initFilter() {
            this.filterInput.owner = this.filter.owner;
            this.filterInput.signatory = this.filter.signatory;
            this.filterInput.reader = this.filter.reader;
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
</style>
