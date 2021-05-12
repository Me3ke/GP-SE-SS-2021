
<template>
    <div :class="open ? '{{generalInfo}} open' : '{{generalInfo}}'">
        <BIcon></BIcon>

        <div class="wrapper">
                <div class="question" style= "margin-left: -6.8em; margin-top: 0;" @click="$emit('toggleOpen', index)">
                    <BIconCaretRightFill class="ui-icon-caret-1-n"></BIconCaretRightFill>

                    <h2 class="h2-head">{{$t('UserGuide.section'+section+'['+index+'].question')}}</h2>
                </div>
                <div class="answer" style="margin-left: 3em">
                    {{$t('UserGuide.section'+section+'['+index+'].answer')}} <br>
                    <img class="answer-image" v-if="generalInfo.img != null" :src="require('../assets/faq/' + generalInfo.img + '')"
                         alt="">
                </div>
        </div>
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
