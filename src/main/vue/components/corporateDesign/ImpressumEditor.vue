<template>
    <b-container style="margin-top: 0em">
        <quill-editor
            ref="editor"
            class="editor"
            v-model="text"
        ></quill-editor>


        <button type="button"
                class="mt-1 light-btn"
                v-b-modal="'modal-preview'"
        >
                    <span class="button-txt">
                        Preview
                    </span>
        </button>
        <!--- Preview --->
        <b-modal
            :id="'modal-preview'"
            centered
            :title="'Email Template Preview'"
            hide-footer ok-only
            v-if="text"
            style="margin-top: 2em"
        >
            <div v-html="text"></div>
        </b-modal>
    </b-container>
</template>

<script>
export default {
    name: "ImpressumEditor",

    data() {
        return {
            text: '<h2><strong>Impressum</strong></h2>' +
                '<p>Anbieter:<br />Max Mustermann<br />Musterstraße 1 \n<br />80999 München</p>' +
                '<p>Kontakt:<br />Telefon: 089/1234567-8<br />Telefax: 089/1234567-9<br />E-Mail: mail@mustermann.de<br />Website: www.mustermann.de</p>' +
                '<p> </p>' +
                '<p>Bei redaktionellen Inhalten:</p>' +
                '<p>Verantwortlich nach § 55 Abs.2 RStV<br />Moritz Schreiberling<br />Musterstraße 2<br />80999 München</p>'
        }
    },
    methods: {
        test() {
            console.log(this.text)
        }
    }
}
</script>

<style scoped>

#modal-preview p {
    margin-bottom: 0;
}

</style>
