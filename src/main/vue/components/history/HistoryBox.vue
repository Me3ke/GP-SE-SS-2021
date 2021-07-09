<template>
    <div
        :class="current ? 'user-box justify-content-between inactive-user history' : 'user-box justify-content-between history'">

        <span>
            {{ document.title }}
        </span>

        <span>
            {{ document.creationDate }}
        </span>

    </div>
</template>

<script>
export default {
    name: "HistoryBox",
    props: {
        document: Object,
        current: Boolean
    }
}
</script>

<style scoped src="../../assets/css/userManagement.css">
</style>
