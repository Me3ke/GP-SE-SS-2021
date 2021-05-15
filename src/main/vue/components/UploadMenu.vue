<template>
    <div>
        <button class="elsa-blue-btn" @click="show = true">
            <h4>
                <b-icon icon="plus-circle"></b-icon>
                {{$t('UploadDoc.upload')}}
            </h4>
        </button>
        <div v-if="show">
            <transition>
                <div class="modal-mask">
                    <div class="modal-wrapper">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title">{{$t('UploadDoc.menuTitle')}}</h5>
                                    <h5>
                                        <b-icon type="button" icon="x-square" @click="show = false">
                                        </b-icon>
                                    </h5>
                                </div>
                                <div class="modal-body">
                                    <div class="form-group files">
                                        <input type="file" class="form-control" multiple="" style="height: 15vh">
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="light-btn" @click="show = false">
                                        <h5>
                                            {{$t('UploadDoc.close')}}
                                        </h5>
                                    </button>
                                    <button type="button" class="elsa-blue-btn">
                                        <h5>
                                            {{$t('UploadDoc.upload')}}
                                        </h5>
                                    </button>
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
    name: 'UploadButton',
    props: {
        text: String
    },
    data() {
        return {show: false};
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

.bi-x-square {
    fill: var(--dark-grey);
}

.bi-x-square:hover {
    fill: var(--light-grey);
    transition: 0.4s;
}

.elsa-blue-btn, .light-btn {
    padding: 0.5vh 1vw 0
}

</style>
