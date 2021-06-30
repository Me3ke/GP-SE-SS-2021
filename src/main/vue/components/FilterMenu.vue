<template>
    <div>
        <h3>
            <b-icon class="filterIcon" icon="funnel" @click="show = true"></b-icon>
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
                                        <select class="form-control">
                                            <option>{{$t('Filter.all')}}</option>
                                            <option>{{$t('Filter.open')}}</option>
                                            <option>{{$t('Filter.signProcess')}}</option>
                                            <option>{{$t('Filter.readProcess')}}</option>
                                            <option>{{$t('Filter.closed')}}</option>
                                        </select>
                                    </div>
                                    <!-- Creation Date Span / Day -->
                                    {{$t('Filter.creationDate')}}
                                    <b-row>
                                        <b-col>
                                            <b-form-datepicker class="mb-2"></b-form-datepicker>
                                        </b-col>
                                        -
                                        <b-col>
                                            <b-form-datepicker class="mb-2"></b-form-datepicker>
                                        </b-col>
                                    </b-row>
                                    <!-- End Date Span / Day -->
                                    {{$t('Filter.endDate')}}
                                    <b-row>
                                        <b-col>
                                            <b-form-datepicker class="mb-2"></b-form-datepicker>
                                        </b-col>
                                        -
                                        <b-col>
                                            <b-form-datepicker class="mb-2"></b-form-datepicker>
                                        </b-col>
                                    </b-row>
                                    <!-- data type -->
                                    <div class="form-group">
                                        {{$t('Filter.dataType')}}
                                        <select class="form-control">
                                            <option>{{$t('Filter.all')}}</option>
                                        </select>
                                    </div>
                                    <!-- owner -->
                                    <b-row >
                                        <input class="form-check-input" type="checkbox" value="">
                                        {{$t('Filter.onlyOwnDocs')}}
                                    </b-row>
                                    <!-- signatory -->
                                    <b-row >
                                        <input class="form-check-input" type="checkbox" value="">
                                        {{$t('Filter.onlyToSign')}}
                                    </b-row>
                                    <b-row>
                                        {{$t('Filter.withSignatureType')}}
                                        <select class="form-control">
                                            <option>{{$t('Filter.all')}}</option>
                                            <option>{{$t('UploadDoc.simple')}}</option>
                                            <option>{{$t('UploadDoc.advanced')}}</option>
                                        </select>
                                    </b-row>
                                    <!-- reader -->
                                    <b-row >
                                        <input class="form-check-input" type="checkbox" value="">
                                        {{$t('Filter.onlyToRead')}}
                                    </b-row>

                                </div>
                                <!-- Buttons -->
                                <div class="modal-footer">
                                    <b-container fluid>
                                        <b-row align-h="end">
                                            <button type="button" class="light-btn">
                                                <h5>
                                                    {{$t('Filter.removeFilter')}}
                                                </h5>
                                            </button>
                                            <button type="button" class="elsa-blue-btn" >
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
export default {
    name: "FilterMenu",
    data() {
        return{
            show: false
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
