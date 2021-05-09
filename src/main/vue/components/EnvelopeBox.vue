<template>
    <div class="card" >
        <div class="media">
            <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="var(--elsa-blue)" class="bi bi-envelope" viewBox="0 0 16 16">
                <path d="M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V4zm2-1a1 1 0 0 0-1 1v.217l7 4.2 7-4.2V4a1 1 0 0 0-1-1H2zm13 2.383-4.758 2.855L15 11.114v-5.73zm-.034 6.878L9.271 8.82 8 9.583 6.728 8.82l-5.694 3.44A1 1 0 0 0 2 13h12a1 1 0 0 0 .966-.739zM1 11.114l4.758-2.876L1 5.383v5.73z"/>
            </svg>
            <div class="media-body">
                <div style="text-align: left;  margin-top: 0.3vw">
                        <h4>
                            {{this.env.name}}
                        </h4>
                </div>
                <div style="text-align: left;">
                    <h6>
                        <span>
                            {{$t('Document.owner')}}: {{this.env.owner.name}}
                        </span>
                        <span style="margin-left: 3vw">
                            {{$t('Document.date')}}: {{this.env.dateCreated}}
                        </span>
                    </h6>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'EnvelopeBox',
    props: {
        env:Object
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

.bi-envelope {
    margin: 1.5vh 0.7vw;
}
</style>
