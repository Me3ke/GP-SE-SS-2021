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
                                        <b-icon type="button" icon="x-square" @click="show = false; page = 1">
                                        </b-icon>
                                    </h5>
                                </div>
                                <!-- Menu -->
                                <div class="modal-body">
                                    <!-- Page 1 -->
                                    <div v-if="page === 1">
                                        <div class="form-group files">
                                            <input type="file" id="fileInput" class="form-control" multiple="" style="height: 15vh">
                                        </div>
                                    </div>
                                    <!-- Page 2 -->
                                    <div v-if="page === 2">
                                        <button type="button" class="light-btn" @click="newEnv = false; page = page +1">
                                            <h5>
                                                {{$t('UploadDoc.selectEnv')}}
                                            </h5>
                                        </button>
                                        <button type="button" class="light-btn" @click="newEnv = true; page = page +1">
                                            <h5>
                                                {{$t('UploadDoc.newEnv')}}
                                            </h5>
                                        </button>
                                    </div>
                                    <!-- Page 3 -->
                                    <div v-if="page === 3">
                                        <!-- Choose from envelopes -->
                                        <div v-if="newEnv === false">
                                            <div class="form-group">
                                                <label for="selectEnvelope"> {{$t('UploadDoc.selectEnv')}} </label>
                                                <select class="form-control" id="selectEnvelope">
                                                    <option v-for="envelope in getEnvs(false, false, true)" :key="envelope.id"> {{envelope.name}} </option>
                                                </select>
                                            </div>
                                        </div>
                                        <!-- Create new envelope -->
                                        <div v-if="newEnv === true">
                                            <div class="form-group">
                                                <label for="envelopeNameInput"> {{$t('UploadDoc.newEnv')}} </label>
                                                <input type="text" class="form-control" id="envelopeNameInput" :placeholder="$t('UploadDoc.newEnvPlaceholder')">
                                            </div>
                                        </div>
                                    </div>
                                    <!-- Page 4 -->
                                    <div v-if="page === 4">
                                        <button type="button" class="light-btn" @click="review = true; page = page +1">
                                            <h5>
                                                {{$t('UploadDoc.addReaders')}}
                                            </h5>
                                        </button>
                                        <button type="button" class="light-btn" @click="review = false; page = page +1">
                                            <h5>
                                                {{$t('UploadDoc.skipReview')}}
                                            </h5>
                                        </button>
                                    </div>
                                    <!-- Page 5 -->
                                    <div v-if="page === 5">
                                        <div v-if="review">

                                        </div>
                                        <div v-if="!review">
                                            <SignatoryMenu :signatories="this.signatories"></SignatoryMenu>


                                        </div>
                                    </div>
                                </div>
                                <!-- Buttons -->
                                <div class="modal-footer">
                                    <!-- Page 1 -->
                                    <div v-if="page === 1">
                                        <button type="button" class="light-btn" @click="show = false">
                                            <h5>
                                                {{$t('UploadDoc.close')}}
                                            </h5>
                                        </button>
                                        <button type="button" class="elsa-blue-btn" @click="page = page + 1">
                                            <h5>
                                                {{$t('UploadDoc.continue')}}
                                            </h5>
                                        </button>
                                    </div>
                                    <!-- Page 2 -->
                                    <div v-if="page === 2">
                                        <button type="button" class="light-btn" @click="page = page - 1">
                                            <h5>
                                                {{$t('UploadDoc.back')}}
                                            </h5>
                                        </button>
                                    </div>
                                    <!-- Page 3 -->
                                    <div v-if="page === 3">
                                        <button type="button" class="light-btn" @click="page = page - 1">
                                            <h5>
                                                {{$t('UploadDoc.back')}}
                                            </h5>
                                        </button>
                                        <button type="button" class="elsa-blue-btn" @click="page = page + 1">
                                            <h5>
                                                {{$t('UploadDoc.continue')}}
                                            </h5>
                                        </button>
                                    </div>
                                    <!-- Page 4 -->
                                    <div v-if="page === 4">
                                        <button type="button" class="light-btn" @click="page = page - 1">
                                            <h5>
                                                {{$t('UploadDoc.back')}}
                                            </h5>
                                        </button>
                                    </div>
                                    <!-- Page 5 -->
                                    <div v-if="page === 5">
                                        <button type="button" class="light-btn" @click="page = page - 1">
                                            <h5>
                                                {{$t('UploadDoc.back')}}
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
                </div>
            </transition>
        </div>
    </div>
</template>

<script>
import SignatoryMenu from "@/main/vue/components/SignatoryMenu";
export default {
    name: 'UploadButton',
    props: {
        text: String
    },
    components: {SignatoryMenu},
    data() {
        return {
            show: false,
            page: 1,
            newEnv: false,
            review: true,
            signatories: [],
            signatoryInput: ""
        };
    },
    computed: {
        getEnvs() {
            return this.$store.getters.getEnvelopesFiltered
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

.bi-x-square {
    fill: var(--dark-grey);
}

.bi-x-square:hover {
    fill: var(--light-grey);
    transition: 0.4s;
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
