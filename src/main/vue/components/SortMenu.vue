<template>
    <div>
        <h3>
            <b-icon class="sortIcon" icon="filter-left" @click="show = true;"></b-icon>
        </h3>
        <div v-if="show">
            <transition>
                <div class="modal-mask">
                    <div class="modal-wrapper">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title">{{$t('Sort.title')}}</h5>
                                    <h5>
                                        <b-icon type="button" icon="x-square" @click="show = false;">
                                        </b-icon>
                                    </h5>
                                </div>
                                <div class="modal-body">
                                    <div>
                                        {{$t('Sort.first')}}
                                        <select class="form-control" v-model="sortFirst">
                                            <option v-for="option in options" :key="option.value" :value="option.value">
                                                <b-icon :icon="option.icon"></b-icon>
                                                {{ $t('Sort.' + option.value) }}
                                            </option>
                                        </select>
                                    </div>
                                    <div style="margin-top: 1em">
                                        {{$t('Sort.second')}}
                                        <select class="form-control" v-model="sortSecond">
                                            <option v-for="option in secondOptions()" :key="option.value" :value="option.value">
                                                <b-icon :icon="option.icon"></b-icon>
                                                {{ $t('Sort.' + option.value) }}
                                            </option>
                                        </select>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <b-container fluid>
                                        <b-row align-h="end">
                                            <b-col cols="auto">
                                                <button type="button" class="light-btn" @click="reset()">
                                                    <h5>
                                                        {{$t('DownloadDoc.cancel')}}
                                                    </h5>
                                                </button>
                                            </b-col>
                                            <b-col cols="auto">
                                                <button type="button" class="elsa-blue-btn" @click="update();">
                                                    <h5>
                                                        {{$t('Sort.apply')}}
                                                    </h5>
                                                </button>
                                            </b-col>
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
    name: "SortMenu",
    data() {
        return {
            show: false,
            sortFirst: "default",
            sortSecond: "",
            options: [
                {
                    value: "default",
                    icon:"",
                },
                {
                    value: "creation",
                    icon:"",
                },
                {
                    value: "creation-rev",
                    icon:"",
                },
                {
                    value: "end",
                    icon:"",
                },
                {
                    value: "name",
                    icon:"",
                },
                {
                    value: "name-rev",
                    icon:"",
                },
                {
                    value: "state",
                    icon:"",
                },
                {
                    value: "state-rev",
                    icon:"",
                },
                {
                    value: "role",
                    icon:"",
                },
                {
                    value: "role-rev",
                    icon:"",
                }
            ]
        }
    },
    methods: {
        secondOptions() {
            let options = [];
            if(["state","state-rev","role","role-rev"].includes(this.sortFirst)){
                let i;
                for(i = 0; i < this.options.length; i++) {
                    let option = this.options[i];
                    if(!(this.sortFirst.includes(option.value)) && !(option.value.includes(this.sortFirst)) && !(option.value === "default")) {
                        options.push(option);
                    }
                }
            }
            if(options.length > 0 && this.sortSecond === "") {
                this.sortSecond = "name";
            } else if(options.length === 0) {
                this.sortSecond = "";
            }
            return options;
        },
        reset() {
           this.sortFirst = "default";
           this.sortSecond = "";
           this.show = false;
        },
        update() {
            this.$emit('updateSort', this.sortFirst, this.sortSecond);
            this.reset();
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

.sortIcon {
    fill: var(--dark-grey);
}

.sortIcon:hover {
    fill: var(--light-grey);
    transition-duration: 0.4s;
}
</style>
