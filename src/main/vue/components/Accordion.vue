
<template>
    <div :class="open ? '{{generalInfo}} open' : '{{generalInfo}}'">
        <BIcon></BIcon>

        <div class="wrapper" style="padding-bottom: 3px; border: 1px solid rgba(0, 0, 0, 0.125); border-radius: 0.25rem; margin-left: 10em" @click="$emit('toggleOpen', index)">

                <div class="question" style="margin-left: -6.8em; margin-top: 0em">
                    <BIconCaretRightFill class="ui-icon-caret-1-n"></BIconCaretRightFill>

                    <h2 class="h2-head">{{$t('UserGuide.section'+section+'['+index+'].question')}}</h2>
                </div>
                <div class="answer">{{$t('UserGuide.section'+section+'['+index+'].answer')}}</div>
        </div>
        <h2 class="header-section-2" style="
            margin-top: 1.2em;
            color: #436495"
            v-if="index >1 ">
            Rund ums Signieren
        </h2>
        <h2 class="header-section-2" style="
            margin-top: 1.2em;
            color: #436495"
            v-if="index === 4">
            Rund ums Dokument
        </h2>
    </div>
</template>

<script>
import { BIcon, BIconCaretRightFill } from 'bootstrap-vue'
export default {
    name: 'Accordion',
    props: ['generalInfo', 'index', 'open', 'section'],
    components: {
        BIcon,
        BIconCaretRightFill
    }
}
</script>
