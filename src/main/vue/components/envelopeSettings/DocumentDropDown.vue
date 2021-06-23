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
            <!-- End Date -->
            <div class="card" style="margin-top:3vh">
                <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                    {{$t('Settings.DocumentSettings.endDate')}}
                </div>
                <div>
                    <b-list-group-item v-if="!this.editDate">{{ this.endDate }}</b-list-group-item>

                    <b-row align-h="end" v-if="!this.editDate">
                        <button class="elsa-blue-btn" style="width:10em; margin: 0.5em 2.5em" @click="editDate = true">
                            <b-icon icon="pencil-fill"></b-icon>
                            {{$t('Settings.DocumentSettings.edit')}}
                        </button>
                    </b-row>

                    <b-form-datepicker class="mb-2" v-if="this.editDate" v-model="endDate"></b-form-datepicker>

                    <b-row align-h="end" v-if="this.editDate">
                        <button style="width:8em; margin-right:0.5em; margin-bottom: 0.5em" class="light-btn" @click="editDate = false"> {{$t('DownloadDoc.cancel')}} </button>
                        <button style="width:8em; margin-right:1.5em; margin-bottom: 0.5em" class="elsa-blue-btn"> {{$t('Settings.DocumentSettings.save')}} </button>
                    </b-row>
                </div>
            </div>

            <!-- Reader -->
            <div class="card" style="margin-top:3vh">
                <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                    {{$t('Settings.DocumentSettings.reader')}}
                </div>
                <div v-if="!this.editReaders">
                    <SignatoryListItem v-for="reader in readers" :key="reader.email" :signatory="reader"></SignatoryListItem>
                </div>

                <b-list-group-item v-if="readers.length === 0 && !this.editReaders">
                    {{$t('Settings.DocumentSettings.noReaders')}}
                </b-list-group-item>

                <b-row align-h="end" v-if="!this.editReaders">
                    <button class="elsa-blue-btn" style="width:10em; margin: 0.5em 2.5em" @click="editReaders = true">
                        <b-icon icon="pencil-fill"></b-icon>
                        {{$t('Settings.DocumentSettings.edit')}}
                    </button>
                </b-row>

                <div style="padding:2em" v-if="this.editReaders">
                    <ReaderMenu :readers="readers"></ReaderMenu>
                </div>

                <b-row align-h="end" v-if="this.editReaders">
                    <button style="width:8em; margin:1em" class="light-btn" @click="editReaders = false"> {{$t('DownloadDoc.cancel')}} </button>
                    <button style="width:8em; margin:1em" class="elsa-blue-btn"> {{$t('Settings.DocumentSettings.save')}} </button>
                </b-row>
            </div>

            <!-- Signatories -->
            <div class="card" style="margin-top:3vh">
                <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                    {{$t('Settings.DocumentSettings.signatory')}}
                </div>

                <div v-if="!this.editSignatories">
                    <SignatoryListItem v-for="signatory in signatories" :key="signatory.email" :signatory="signatory"></SignatoryListItem>
                </div>

                <b-list-group-item v-if="signatories.length === 0 && !this.editSignatories">
                    {{$t('Settings.DocumentSettings.noSignatories')}}
                </b-list-group-item>

                <b-row align-h="end" v-if="!this.editSignatories">
                    <button class="elsa-blue-btn" style="width:10em; margin: 0.5em 2.5em" @click="editSignatories = true">
                        <b-icon icon="pencil-fill"></b-icon>
                        {{$t('Settings.DocumentSettings.edit')}}
                    </button>
                </b-row>


                <div style="padding:2em" v-if="this.editSignatories">
                    <SignatoryMenu :inModal="false" :order-relevant="orderRelevant" :signatories="signatories"></SignatoryMenu>
                </div>

                <b-row align-h="end" v-if="this.editSignatories">
                    <button style="width:8em; margin:1em" class="light-btn" @click="editSignatories = false"> {{$t('DownloadDoc.cancel')}} </button>
                    <button style="width:8em; margin:1em" class="elsa-blue-btn"> {{$t('Settings.DocumentSettings.save')}} </button>
                </b-row>
            </div>
        </b-container>
    </div>
</template>

<script>

import ReaderMenu from "@/main/vue/components/envelopeSettings/ReaderMenu";
import SignatoryMenu from "@/main/vue/components/envelopeSettings/SignatoryMenu";
import SignatoryListItem from "@/main/vue/components/SignatoryListItem";

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
        SignatoryMenu,
        SignatoryListItem
    },
    data() {
        return {
            show: false,
            editSignatories: false,
            editReaders: false,
            editDate: false
        }
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

.list-group-item {
    height: 2.5em;
    padding: 0.5em;
}
</style>
