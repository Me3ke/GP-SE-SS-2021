<template>
    <div>
        <b-list-group-item v-if="!this.editDate" style="height: 2.5em; padding: 0.25em 1.5em; text-align: left">{{this.endDate}}</b-list-group-item>

        <b-row align-h="end" v-if="!this.editDate">
            <button class="elsa-blue-btn" style="width:10em; margin: 0.5em 2.5em" @click="editDate = true">
                <b-icon icon="pencil-fill"></b-icon>
                {{$t('Settings.DocumentSettings.edit')}}
            </button>
        </b-row>

        <b-row v-if="this.editDate">
            <b-col cols="6">
                <b-form-datepicker class="mb-2" v-model="date"></b-form-datepicker>
            </b-col>
            <b-col cols="6">
                <b-form-timepicker v-model="time" locale="en"></b-form-timepicker>
            </b-col>
        </b-row>

        <b-row align-h="end" v-if="this.editDate">
            <button style="width:8em; margin-right:0.5em; margin-bottom: 0.5em" class="light-btn" @click="editDate = false"> {{$t('DownloadDoc.cancel')}} </button>
            <button style="width:8em; margin-right:1.5em; margin-bottom: 0.5em" class="elsa-blue-btn" @click="saveDate()"> {{$t('Settings.DocumentSettings.save')}} </button>
        </b-row>
    </div>
</template>

<script>
export default {
    name: "EndDateSettings",
    props: {
        endDate: String
    },
    data() {
        return {
            editDate: false,
            date: "",
            time: "",
        }
    },
    methods: {
        saveDate() {
            let time = this.time.split(":")
            let endDate = this.date + ' ' + time[0] + ':' + time[1];
            this.$emit('updateEndDate', endDate)
        }
    }
}
</script>

<style scoped>

</style>
