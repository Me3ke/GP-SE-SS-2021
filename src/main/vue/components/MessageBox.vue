<template>
    <b-container fluid class="card" style="padding:0.5vh">
        <div class="media">

            <!-- For reminder messages -->
            <b-iconstack v-if="msg.category === 'Reminder'" font-scale="2">
                <b-icon style="fill: var(--elsa-blue)" stacked icon="file-earmark-text" scale="1"></b-icon>
                <b-icon style="fill: var(--elsa-blue)" stacked icon="pen-fill" scale="0.5" shift-v="-2" shift-h="4.5"
                        rotate="5"></b-icon>
                <b-icon v-if="msg.watched==='False'" stacked icon="exclamation-circle-fill"
                        style="fill: var(--elsa-blue)"
                        scale="0.5" shift-v="3" shift-h="-5"></b-icon>
            </b-iconstack>

            <!-- For signed messages -->
            <b-iconstack v-else-if="msg.category === 'Sign'" font-scale="2">
                <b-icon style="fill: var(--elsa-blue)" stacked icon="file-earmark-check" scale="1"></b-icon>
                <b-icon style="fill: var(--elsa-blue)" stacked icon="pen-fill" scale="0.5" shift-v="-2" shift-h="4.5"
                        rotate="5"></b-icon>
                <b-icon v-if="msg.watched==='False'" stacked icon="exclamation-circle-fill" class="my-icon"
                        style="fill: var(--elsa-blue)"
                        scale="0.5" shift-v="3" shift-h="-5"></b-icon>
            </b-iconstack>

            <!-- For checked messages -->
            <b-iconstack v-else-if="msg.category === 'Checked'" font-scale="2">
                <b-icon style="fill: var(--elsa-blue)" stacked icon="file-earmark-check" scale="1"></b-icon>
                <b-icon style="fill: var(--elsa-blue)" stacked icon="eye-fill" scale="0.5" shift-v="-4" shift-h="3.5"
                        rotate="5"></b-icon>
                <b-icon v-if="msg.watched==='False'" stacked icon="exclamation-circle-fill"
                        style="fill: var(--elsa-blue)"
                        scale="0.5" shift-v="3" shift-h="-5"></b-icon>
            </b-iconstack>

            <!-- For updated messages -->
            <b-iconstack v-else font-scale="2">
                <b-icon style="fill: var(--elsa-blue)" stacked icon="file-earmark" scale="1"></b-icon>
                <b-icon style="fill: var(--elsa-blue)" stacked icon="arrow-clockwise" scale="0.7" shift-v="-0.4"
                        rotate="45"></b-icon>
                <b-icon v-if="msg.watched==='False'" stacked icon="exclamation-circle-fill"
                        style="fill: var(--elsa-blue)"
                        scale="0.5" shift-v="3" shift-h="-5"></b-icon>
            </b-iconstack>

            <div class="media-body">
                <b-container fluid style="overflow:hidden">
                    <b-row align-h="start">
                        <div class="col-auto">
                            <b-row align-h="between">
                                <div class="col-auto">
                                    <h4>
                                        {{ $t('MessagePage.concerns') }}: {{ this.msg.correspondingDocument.title }}
                                    </h4>
                                </div>
                            </b-row>
                            <b-row align-h="start">
                                <div class="col-auto">
                                    <h6>
                                        {{ $t('MessagePage.sentBy') }}: {{ this.msg.sentBy }}
                                    </h6>
                                </div>
                                <div class="col-auto">
                                    <h6>
                                        {{ $t('MessagePage.sentOn') }}: {{ this.msg.dateSent }}
                                    </h6>
                                </div>
                            </b-row>
                        </div>
                    </b-row>
                </b-container>
            </div>
        </div>
    </b-container>
</template>

<script>
export default {
    name: 'MessageBox',
    props: {
        msg: Object
    }
}
</script>

<style scoped>

h4 {
    font-size: 2vh;
    text-overflow-mode: ellipse;
}

h6 {
    font-size: 1.25vh;
    text-overflow-mode: ellipse;
}

.card:hover {
    background-color: var(--light-grey);
    transition-duration: 0.4s;
}
</style>
