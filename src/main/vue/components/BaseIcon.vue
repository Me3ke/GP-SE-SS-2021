<template>
    <div class="iconWrapper" v-html="svg">
    </div>
</template>

<script>
import feather from 'feather-icons'

export default {
    name: 'Icon',
    props: {
        name: String,
        color: {
            type: String,
            default: "#47525E"
        },
        width: {
            type: [Number, String],
            default: 24
        },
        height: {
            type: [Number, String],
            default: 24
        },
        strokeWidth: {
            type: [Number, String],
            default: 2
        }
    },
    computed: {
        svg() {
            return feather.icons[this.name].toSvg({
                class: "icon",
                width: this.width,
                height: this.height,
                color: this.color,
                'stroke-width': this.strokeWidth
            })
        }
    }
}
</script>

<style scoped>
.iconWrapper {
    align-items: center
}
</style>
