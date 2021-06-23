<template>
    <b-container fluid="sm">
        <b-row align-h="center" align-v="center" class="text-center">
            <div class="card">
                <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                    <h4>
                        Email Templates
                    </h4>

                </div>

                <b-list-group-item class="d-flex justify-content-center align-items-center">
                    <select v-model="selected">
                        <option disabled value="">Please select one</option>
                        <option v-for="(content, index) in contents" :key="index" v-bind:value="content.content"> {{content.name}}</option>
                    </select>


                    <b-button class="elsa-blue-btn"
                              style="margin-top: 0.2em; margin-bottom: 0.1em; margin-left: 0.7em"
                    >
                        Hochladen
                    </b-button>
                </b-list-group-item>

                {{selected}}


                <quill v-model="selected" output="html"/>

                <b-list-group-item class="d-flex justify-content-end align-items-center">
                    <!---
                    <transition name="saved">
                            <span  class="content-div">
                                {{ $t('Settings.saved') }}
                            </span>
                    </transition>
                    ---->

                    <b-button class="elsa-blue-btn"
                              style="margin-top: 0.2em; margin-bottom: 0.1em; margin-left: 0.7em"
                              >
                        {{ $t('Settings.MessageSettings.send') }}
                    </b-button>
                </b-list-group-item>

            </div>
        </b-row>

    </b-container>
</template>

<script>

export default {
    name: "EmailTemplate",
    data() {
        return {

            // todo api with saved templates
            contents: [
                {
                    name: '1',
                    content: '<h1>Hello World</h1><p>Here is some text</p'
                },
                {
                    name: '2',
                    content: '<h1>Hello World#2</h1><p>Here is some text</p'
                },
                {
                    name: '3',
                    content: 'test'
                }
            ],
            selected: null,


        }
    }
}
</script>

<style scoped src="../assets/css/settingsPage.css">


</style>
