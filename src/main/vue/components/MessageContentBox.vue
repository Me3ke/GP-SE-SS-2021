<template>
    <b-container fluid class="card" style="padding:0.5vh">
        <div class="media">
            <div class="media-body">
                <b-container fluid style="overflow:hidden;">
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

                            <hr style="width: 100%">

                            <b-row style="margin-top: 3vh; overflow:hidden; display: flex; text-align: left">
                                <div class="col-auto">
                                    <h6>
                                        {{ this.msg.content }}
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
    name: "MessageContentBox",
    props: {
        msg: Object
    }
}
</script>

<style scoped>

h4 {
    font-size: 3.5vh;
    text-overflow-mode: ellipse;
}

h6 {
    font-size: 2vh;
    text-overflow-mode: ellipse;
}
</style>
