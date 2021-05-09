<template>
    <b-nav-item-dropdown right class="my-dropdown-menu" no-caret>
        <template #button-content>
            <b-icon icon="person-circle" class="my-icon" font-scale="2"></b-icon>
        </template>
        <b-dropdown-item class="my-dropdown-item">
            <b-icon icon="person-circle" class="my-icon" font-scale="2"></b-icon>
            <span class="letters"> {{ $t('Header.Avatar.profile') }} </span>
        </b-dropdown-item>
        <b-dropdown-divider class="my-divider"></b-dropdown-divider>
        <b-dropdown-item class="my-dropdown-item">
            <b-icon icon="gear" class="my-icon" font-scale="2"></b-icon>
            <span class="letters"> {{ $t('Header.Avatar.settings') }} </span>
        </b-dropdown-item>
        <b-dropdown-divider class="my-divider"></b-dropdown-divider>
        <b-dropdown-item class="my-dropdown-item">
            <b-icon icon="question-circle" class="my-icon" font-scale="2"></b-icon>
            <span class="letters"> {{ $t('Header.Avatar.help') }} </span>
        </b-dropdown-item>
        <b-dropdown-divider class="my-divider"></b-dropdown-divider>
        <b-dropdown-item class="my-dropdown-item">
            <b-icon icon="box-arrow-right" class="my-icon" font-scale="2"></b-icon>
            <span class="letters"> {{ $t('Header.Avatar.logout') }} </span>
        </b-dropdown-item>
    </b-nav-item-dropdown>
</template>

<script>
export default {
    name: "Avatar"
}
</script>

<style scoped>

.letters {
    margin-left: 0.75vw;
    position: relative;
    bottom: 0.5vw;
}

.my-icon {
    fill: var(--elsa-blue);
}

.my-dropdown-menu >>> .dropdown-menu {
    color: var(--dark-grey);
    padding-top: 0;
    padding-bottom: 0;
    margin: 0;
    border-color: var(--elsa-blue);
}

.my-divider >>> .dropdown-divider {
    margin-top: 0;
    margin-bottom: 0;
}

.my-dropdown-item >>> .dropdown-item {
    color: var(--dark-grey);
    padding-left: 0.5vw;
}

.my-dropdown-item:hover >>> .dropdown-item {
    background-color: var(--light-grey);
    transition-duration: 0.4s;
}

</style>
