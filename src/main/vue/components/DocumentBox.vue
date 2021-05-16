<template>
    <b-container fluid class="card" style="padding:0.5vh">
        <div class="media">
            <b-icon icon="file-earmark-text" class="my-icon"></b-icon>
            <div class="media-body">
                <b-container fluid style="overflow:hidden">
                    <b-row align-h="start">
                        <div class="col-auto">
                            <b-row align-h="between">
                                <div class="col-auto">
                                    <h4>
                                        {{this.doc.title}}
                                    </h4>
                                </div>
                                <div class="col-auto">
                                    <div v-if="doc.state === 'closed'" style="text-align: right; margin-left: 1vw">
                                        <h6>
                                            <!-- Time until document needs to be signed TODO -->
                                            {{$t('Document.closed')}}
                                        </h6>
                                    </div>
                                    <div v-if="doc.state === 'open'" style="text-align: right; margin-left: 1vw">
                                        <h6>
                                            <!-- Time until document needs to be signed TODO -->
                                            {{$t('Document.open')}}
                                        </h6>
                                    </div>
                                </div>
                            </b-row>
                            <b-row align-h="start">
                                <div class="col-auto">
                                    <h6>
                                        {{$t('Document.owner')}}: {{this.doc.owner.firstname}} {{this.doc.owner.lastname}}
                                    </h6>
                                </div>
                                <div class="col-auto" style="margin-left: 1vw">
                                    <h6>
                                        {{$t('Document.date')}}: {{this.doc.creationDate}}
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
    name: 'DocumentBox',
    props: {
        doc: Object
    }
}
</script>

<style scoped>
.bi-file-earmark-text {
    margin: 0 0.7vw;
}

.my-icon {
    fill: var(--elsa-blue);
    height: 2em;
    width: auto;
}

.card:hover {
    background-color: var(--light-grey);
    transition-duration: 0.4s;
}
</style>
