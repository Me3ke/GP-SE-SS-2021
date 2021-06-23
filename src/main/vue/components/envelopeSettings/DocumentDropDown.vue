<template>
    <div class="card">
        <button class="drop-down-box" @click="show = !show">
            <div class="media">
                <b-icon icon="file-earmark-text" class="iconBlue" style="margin:0.5em"></b-icon>
                <div class="media-body">
                    <b-container fluid style="text-align: left; margin: 0.3em">
                        <b-row align-h="between">
                            <b-col cols="11">
                                <h4>
                                    {{this.document.title}}
                                </h4>
                            </b-col>
                            <b-col cols="1">
                                <b-icon v-if="!show" icon="caret-down-fill" class="iconDark" style="margin:0.5em"></b-icon>
                                <b-icon v-if="show" icon="caret-up-fill" class="iconDark" style="margin:0.5em"></b-icon>
                            </b-col>
                        </b-row>
                    </b-container>
                </div>
            </div>
        </button>

        <b-container v-if="show">
            <div class="card" style="margin-top:3vh">
                <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                    {{$t('Settings.DocumentSettings.endDate')}}
                </div>
                <div>
                    <p>{{this.endDate}}</p>
                    <b-form-datepicker class="mb-2" v-model="endDate"></b-form-datepicker>
                </div>
            </div>
            <div class="card" style="margin-top:3vh">
                <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                    {{$t('Settings.DocumentSettings.reader')}}
                </div>
                <div style="padding:2em">
                    <ReaderMenu :readers="readers"></ReaderMenu>
                </div>
            </div>
            <div class="card" style="margin-top:3vh">
                <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                    {{$t('Settings.DocumentSettings.signatory')}}
                </div>
                <div style="padding:2em">
                    <SignatoryMenu :signatories="signatories" :order-relevant="orderRelevant" :in-modal="false"></SignatoryMenu>
                </div>
            </div>
            <button style="width:8em; margin:1em" class="elsa-blue-btn"> {{$t('Settings.DocumentSettings.save')}} </button>
        </b-container>
    </div>
</template>

<script>

import ReaderMenu from "@/main/vue/components/envelopeSettings/ReaderMenu";
import SignatoryMenu from "@/main/vue/components/envelopeSettings/SignatoryMenu";

export default {
    name: "documentDropDown",
    props: {
        document: Object,
        readers: Array,
        signatories: Array,
        endDate: String,
        orderRelevant: Boolean
    },
    components: {
        ReaderMenu,
        SignatoryMenu
    },
    data() {
        return {show: false}
    }
}
</script>

<style scoped>
.drop-down-box {
    background-color: var(--whitesmoke);
    color: var(--dark-grey);
    border: 0.03vw solid var(--dark-grey);
    border-radius: 0.1vw;
    width: 100%
}

.drop-down-box:hover {
    background-color: var(--closed-doc-hover);
    transition-duration: 0.4s;
    box-shadow: var(--light-grey);
}

.iconBlue {
    fill: var(--elsa-blue);
    height: 1.5em;
    width: auto;
}

.iconBlue {
    fill: var(--dark-grey);
    height: 1.5em;
    width: auto;
}
</style>
