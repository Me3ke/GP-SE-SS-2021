<template>
    <div>
        <!-- Show History -->
        <div v-if="!editHistory">
            <b-list-group-item v-if="showHistory" style="height: 2.5em; padding: 0.25em 1.5em; text-align: left">{{$t('UploadDoc.showHistory')}}</b-list-group-item>
            <b-list-group-item v-if="!showHistory" style="height: 2.5em; padding: 0.25em 1.5em; text-align: left">{{$t('UploadDoc.dontShowHistory')}}</b-list-group-item>
        </div>

        <b-list-group-item v-if="editHistory" style="height: 2.5em; padding: 0.25em 1.5em;">
            <b-row align-h="start">
                <div class="custom-control custom-switch">
                    <input type="checkbox" class="custom-control-input" id="showHistory" v-model="showHistoryInput">
                    <label class="custom-control-label" for="showHistory"> {{$t('UploadDoc.showHistory')}} </label>
                </div>
            </b-row>
        </b-list-group-item>

        <b-row align-h="end" v-if="!editHistory">
            <button class="elsa-blue-btn" style="width:10em; margin: 0.5em 2.5em" @click="editHistory = true">
                <b-icon icon="pencil-fill"></b-icon>
                {{$t('Settings.DocumentSettings.edit')}}
            </button>
        </b-row>

        <b-row align-h="end" v-if="editHistory">
            <button style="width:8em; margin:1em" class="light-btn" @click="editHistory = false"> {{$t('DownloadDoc.cancel')}} </button>
            <button style="width:8em; margin:1em" class="elsa-blue-btn" @click="saveHistory()"> {{$t('Settings.DocumentSettings.save')}} </button>
        </b-row>
    </div>
</template>

<script>
export default {
    name: "HistorySettings",
    props: {
        showHistory: Boolean
    },
    data() {
        return {
            showHistoryInput: this.showHistory,
            editHistory: false
        }
    },
    methods: {
        saveHistory() {
            this.$emit('updateHistory', this.showHistoryInput);
            this.editHistory = false;
            this.showHistoryInput = this.showHistory;
        }
    }
}
</script>

<style scoped>

</style>
