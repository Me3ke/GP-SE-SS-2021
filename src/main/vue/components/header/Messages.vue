<template>
    <b-nav-item-dropdown right class="my-dropdown-menu" no-caret>
        <template #button-content>
            <b-icon icon="envelope" class="my-icon" font-scale="2"></b-icon>
        </template>
        <b-dropdown-item class="my-dropdown-item">
        </b-dropdown-item>
    </b-nav-item-dropdown>
</template>

<script>
export default {
    name: "Messages"
}
</script>

<style scoped>

.my-icon {
    fill: var(--elsa-blue);
}

.my-divider >>> .dropdown-divider {
    margin-top: 0;
    margin-bottom: 0;
}

.my-dropdown-menu >>> .dropdown-menu {
    color: var(--dark-grey);
    padding-top: 0;
    padding-bottom: 0;
    margin: 0;
    border-color: var(--elsa-blue);
}

.my-dropdown-item >>> .dropdown-item {
    color: var(--dark-grey);
    padding-left: 0.5vw;
}

.my-dropdown-item:hover >>> .dropdown-item {
    color: whitesmoke;
    background-color: var(--elsa-blue);
    opacity: 80%;
    transition-duration: 0.4s;
}

.my-dropdown-item:active >>> .dropdown-item {
    color: whitesmoke;
    background-color: var(--elsa-blue);
}
</style>
