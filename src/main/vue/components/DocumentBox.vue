<template>
    <div class="card" >
        <div class="media">
            <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="var(--elsa-blue)" class="bi bi-file-earmark-text" viewBox="0 0 16 16">
                <path d="M5.5 7a.5.5 0 0 0 0 1h5a.5.5 0 0 0 0-1h-5zM5 9.5a.5.5 0 0 1 .5-.5h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1-.5-.5zm0 2a.5.5 0 0 1 .5-.5h2a.5.5 0 0 1 0 1h-2a.5.5 0 0 1-.5-.5z"/>
                <path d="M9.5 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V4.5L9.5 0zm0 1v2A1.5 1.5 0 0 0 11 4.5h2V14a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h5.5z"/>
            </svg>
            <div class="media-body">
                <div style="text-align: left; margin-top: 0.3vw">
                        <h4>
                            {{this.doc.name}}
                        </h4>
                </div>
                <div style="text-align: left;">
                    <h6>
                        <span>
                        {{$t('Document.owner')}}: {{this.doc.owner.name}}
                        </span>
                        <span style="margin-left: 3vw">
                            {{$t('Document.date')}}: {{this.doc.dateCreated}}
                        </span>
                    </h6>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'DocumentBox',
    props: {
        doc:Object
    }
}
</script>

<style scoped>
.card {
    width: 96.5%
}

.card:hover {
    background-color: var(--light-grey);
    transition-duration: 0.4s;
}

.bi-file-earmark-text {
    margin: 1.5vh 0.7vw;
}

</style>
