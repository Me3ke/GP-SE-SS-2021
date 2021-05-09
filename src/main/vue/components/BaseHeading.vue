<!-- Do be redone -->
<!-- Icon click does not work yet -->
<template>
    <div id="container">
        <b-icon id="icon" icon="arrow-left"
                @click.native="historyThere()  ? $router.go(-1) : $router.push('/')"></b-icon>
        <h4 id="heading">{{ $t(name) }}</h4>
    </div>
</template>

<script>
export default {
    name: "BaseHeading",
    props: {
        name: {
            type: String,
            default: "Insert Heading"
        }
    },
    methods: {
        historyThere() {
            return window.history.length > 2
        }
    }
}
</script>

<style scoped>

#container {
    display: flex;
    align-items: center;
    line-height: 2vw;
    position: fixed;
    left: 1.33vw;
    top: 4.7vw;
}

#heading {
    font-size: 2vw;
    vertical-align: middle;
    line-height: 2vw;
    display: inline-block;
    padding-top: 0.33vw;
    padding-left: 0.33vw;
}

</style>
