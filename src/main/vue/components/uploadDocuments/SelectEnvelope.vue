<template>
<div>
    <div class="modal-body">
        <div v-if="newEnv === null">
            <button type="button" class="light-btn" @click="newEnv = false;">
                <h5>
                    {{$t('UploadDoc.selectEnv')}}
                </h5>
            </button>
            <button type="button" class="light-btn" @click="newEnv = true;" style="margin-top: 0.5em">
                <h5>
                    {{$t('UploadDoc.newEnv')}}
                </h5>
            </button>
        </div>

        <!-- Choose from envelopes -->
        <b-alert :show="this.error.noID">
            {{$t('UploadDoc.error.noEnvelope')}}
        </b-alert>

        <div v-if="newEnv === false">
            <div class="form-group">
                <label for="selectEnvelope"> {{$t('UploadDoc.selectEnv')}} </label>
                <select class="form-control" id="selectEnvelope" style="cursor: pointer;" v-model="envelope.id">
                    <option v-for="envelope in this.envelopes" :key="envelope.id" :value="envelope.id"> {{envelope.name}} </option>
                </select>
            </div>
        </div>

        <!-- Create new envelope -->
        <b-alert :show="this.error.noName">
            {{$t('UploadDoc.error.noNewName')}}
        </b-alert>

        <div v-if="newEnv === true">
            <b-form-group
                v-bind:label="$t('UploadDoc.newEnv')"
                label-align="left"
                label-size="lg"
            >
                <b-form-input
                    id="envelope_input"
                    v-model="envelope.name"
                    v-bind:placeholder="$t('UploadDoc.newEnvPlaceholder')"
                >
                </b-form-input>
            </b-form-group>
        </div>
    </div>
    <div class="modal-footer">
        <b-container fluid>
            <b-row align-h="end">
                <b-col cols="auto" v-if="newEnv === null">
                    <button type="button" class="light-btn" @click="back()">
                        <h5>
                            {{$t('UploadDoc.back')}}
                        </h5>
                    </button>
                </b-col>
                <b-col cols="auto" v-if="!(newEnv === null)">
                    <button type="button" class="light-btn" @click="newEnv = null; envelope = {id: null, name: null}; error = {noName: false, noId: false};">
                        <h5>
                            {{$t('UploadDoc.back')}}
                        </h5>
                    </button>
                </b-col>
                <b-col cols="auto" v-if="!(newEnv === null)">
                    <button type="button" class="elsa-blue-btn" @click="next()">
                        <h5>
                            {{$t('UploadDoc.continue')}}
                        </h5>
                    </button>
                </b-col>
            </b-row>
        </b-container>
    </div>
</div>
</template>

<script>
import {mapGetters} from "vuex";

export default {
    name: "SelectEnvelope",
    data() {
        return {
            newEnv: null, //Boolean
            envelope: {
                id: null, //Integer
                name: null //String
            },
            error: {
                noName: false,
                noID: false
            }
        }
    },
    methods: {
        back() {
            this.$emit('previousPage')
        },
        next() {
            this.error.noName = !!(this.newEnv && (this.envelope.name === "" || this.envelope.name === null));
            this.error.noID = !this.newEnv && this.envelope.id === null;
            if(!this.error.noID && !this.error.noName){
                this.$emit('updateEnvelope', this.envelope);
                this.reset();
                this.$emit('nextPage')
            }
        },
        reset() {
            this.newEnv= null;
            this.envelope= {
                id: null,
                    name: null
            };
            this.error = {
                noName: false,
                noID: false
            };
        }
    },
    created() {
        this.$store.dispatch('envelopes/fetchEnvelopes', {})
    },
    computed: {
        ...mapGetters({
            envelopes: 'envelopes/getEnvelopes'
        })
    }
}
</script>

<style scoped>
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
</style>
