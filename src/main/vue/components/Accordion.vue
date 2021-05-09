
<template>
    <div :class="open ? '{{generalInfo}} open' : '{{generalInfo}}'">
        <BIcon></BIcon>

        <div class="question" @click="$emit('toggleOpen', index)">
            <BIconCaretRightFill class="ui-icon-caret-1-n"></BIconCaretRightFill>

            <h2 class="h2-head">{{ generalInfo.question }} </h2>
        </div>
        <div class="answer">{{generalInfo.answer}}</div>

        <h2 class="header-section-2" style="margin-left: 3em;
            font-size: 1.6em;
            margin-top: 1.2em;
            color: #436495"
            v-if="index === 2">
            Rund ums Signieren
        </h2>
        <h2 class="header-section-2" style="margin-left: 3em;
            font-size: 1.6em;
            margin-top: 1.2em;
            color: #436495"
            v-if="index === 8">
            Rund ums Signieren
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
