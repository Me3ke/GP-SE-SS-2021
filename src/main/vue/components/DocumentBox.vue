<template>
    <b-container fluid class="card" style="padding:0.5vh">
        <div class="media">
            <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="var(--elsa-blue)" class="bi bi-file-earmark-text" viewBox="0 0 16 16">
                <path d="M5.5 7a.5.5 0 0 0 0 1h5a.5.5 0 0 0 0-1h-5zM5 9.5a.5.5 0 0 1 .5-.5h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1-.5-.5zm0 2a.5.5 0 0 1 .5-.5h2a.5.5 0 0 1 0 1h-2a.5.5 0 0 1-.5-.5z"/>
                <path d="M9.5 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V4.5L9.5 0zm0 1v2A1.5 1.5 0 0 0 11 4.5h2V14a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h5.5z"/>
            </svg>
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
                                        {{$t('Document.owner')}}: {{this.doc.owner.name}}
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


.card:hover {
    background-color: var(--light-grey);
    transition-duration: 0.4s;
}
</style>
