<template>
    <div>
        <b-alert :show="this.error.noDate && this.error.noTime" style="margin:0.5em">
            {{$t('Settings.DocumentSettings.error.noDateTime')}}
        </b-alert>

        <b-alert :show="this.error.noDate && !this.error.noTime" style="margin:0.5em">
            {{$t('Settings.DocumentSettings.error.noDate')}}
        </b-alert>

        <b-alert :show="!this.error.noDate && this.error.noTime" style="margin:0.5em">
            {{$t('Settings.DocumentSettings.error.noTime')}}
        </b-alert>

        <b-list-group-item v-if="!this.editDate" style="height: 2.5em; padding: 0.25em 1.5em; text-align: left; background-color: var(--whitesmoke); border-color: var(--dark-grey)">{{this.endDate}}</b-list-group-item>

        <b-row align-h="end" v-if="!this.editDate && !(this.state === 'ARCHIVED')">
            <button class="elsa-blue-btn" style="width:10em; margin: 0.5em 2.5em" @click="editDate = true">
                <b-icon icon="pencil-fill"></b-icon>
                {{$t('Settings.DocumentSettings.edit')}}
            </button>
        </b-row>

        <b-row v-if="this.editDate">
            <b-col cols="6">
                <b-form-datepicker class="my-date-picker" v-model="date"></b-form-datepicker>
            </b-col>
            <b-col cols="6">
                <b-form-timepicker class="my-date-picker" v-model="time" :locale="this.$i18n.locale"></b-form-timepicker>
            </b-col>
        </b-row>

        <b-row align-h="end" v-if="this.editDate">
            <button style="width:8em; margin-right:0.5em; margin-bottom: 0.5em; cursor: pointer;" class="light-btn" @click="cancel()"> {{$t('DownloadDoc.cancel')}} </button>
            <button style="width:8em; margin-right:1.5em; margin-bottom: 0.5em; cursor: pointer;" class="elsa-blue-btn" @click="saveDate()"> {{$t('Settings.DocumentSettings.save')}} </button>
        </b-row>
    </div>
</template>

<script>
export default {
    name: "EndDateSettings",
    props: {
        endDate: String,
        state: String
    },
    data() {
        return {
            editDate: false,
            date: "",
            time: "",
            error: {
                noDate: false,
                noTime: false
            }
        }
    },
    methods: {
        saveDate() {
            if(this.validate()) {
                let time = this.time.split(":");
                let endDate = this.date + ' ' + time[0] + ':' + time[1];
                this.$emit('updateEndDate', endDate);
                this.time = "";
                this.date = "";
                this.editDate = false;
            }
        },
        validate() {
            this.error.noDate = this.date === "";
            this.error.noTime = this.time === "";
            return !this.error.noDate && !this.error.noTime;
        },
        cancel() {
            this.error = {noDate: false, noTime: false};
            this.editDate = false;
            this.date = "";
            this.time = "";
        }
    }
}
</script>

<style scoped>
.alert {
    background-color: var(--sign-doc-hover);
    color: var(--red);
    border-color: var(--red);
}

.form-control {
    background-color: var(--whitesmoke);
    color: var(--dark-grey);
}

.form-control:focus {
    background-color: var(--whitesmoke);
    color: var(--dark-grey);
}

.my-date-picker >>> .dropdown-menu {
    background-color: var(--whitesmoke);
    color: var(--dark-grey);
}

.btn {
    color: var(--dark-grey);
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
