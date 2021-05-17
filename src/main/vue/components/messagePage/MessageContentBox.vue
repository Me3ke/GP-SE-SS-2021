<template>
    <b-container fluid class="card" style="padding:0.5vh; background-color: var(--whitesmoke); border-color: var(--dark-grey)">
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

                            <hr style="width: 100%; background-color: var(--dark-grey)">

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


/* Extra small devices (portrait phones, less than 576px) */
@media (max-width: 575.98px) {
    h4 {
        font-size: .62em;
        text-overflow-mode: ellipse;
    }

    h6 {
        font-size: .43em;
        text-overflow-mode: ellipse;
    }
}

/* Small devices (landscape phones, 576px and up) */
@media (min-width: 576px) and (max-width: 767.98px) {
    h4 {
        font-size: .52em;
        text-overflow-mode: ellipse;
    }

    h6 {
        font-size: .33em;
        text-overflow-mode: ellipse;
    }
}

/* Large Phones in landscape mode */
@media (min-width: 768px) and (max-width: 991.98px) and (max-height: 499.98px) {
    h4 {
        font-size: .77em;
        text-overflow-mode: ellipse;
    }

    h6 {
        font-size: .54em;
        text-overflow-mode: ellipse;
    }
}

/* Medium devices (tablets, 768px and up) */
@media (min-width: 768px) and (max-width: 991.98px) and (min-height: 500px) {
    h4 {
        font-size: 1.2em;
        text-overflow-mode: ellipse;
    }

    h6 {
        font-size: .87em;
        text-overflow-mode: ellipse;
    }
}

/* Large devices (desktops, 992px and up)*/
@media (min-width: 992px) and (max-width: 1199.98px) {
    h4 {
        text-overflow-mode: ellipse;
    }

    h6 {
        text-overflow-mode: ellipse;
    }
}

/* Extra large devices (large desktops, 1200px and up)*/
@media (min-width: 1200px) {
    h4 {
        text-overflow-mode: ellipse;
    }

    h6 {
        text-overflow-mode: ellipse;
    }
}
</style>
