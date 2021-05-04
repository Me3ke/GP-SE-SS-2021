<template>
    <b-nav-item-dropdown right class="my-dropdown-menu" no-caret>
        <template #button-content>
            <b-icon icon="person-circle" class="my-icon" font-scale="2"></b-icon>
        </template>
        <b-dropdown-item v-b-hover="handleHover" class="my-dropdown-item">
            <b-icon icon="person-circle" v-if="isHovered" class="my-icon-hovered" font-scale="2"></b-icon>
            <b-icon icon="person-circle" v-else class="my-icon" font-scale="2"></b-icon>
            <span class="letters"> {{ $t('Header.Avatar.profile') }} </span>
        </b-dropdown-item>
        <b-dropdown-divider class="my-divider"></b-dropdown-divider>
        <b-dropdown-item v-b-hover="handleHoverTwo" class="my-dropdown-item">
            <b-icon icon="gear" v-if="isHoveredTwo" class="my-icon-hovered" font-scale="2"></b-icon>
            <b-icon icon="gear" v-else class="my-icon" font-scale="2"></b-icon>
            <span class="letters"> {{ $t('Header.Avatar.settings') }} </span>
        </b-dropdown-item>
        <b-dropdown-divider class="my-divider"></b-dropdown-divider>
        <b-dropdown-item v-b-hover="handleHoverThree" class="my-dropdown-item">
            <b-icon icon="question-circle" v-if="isHoveredThree" class="my-icon-hovered" font-scale="2"></b-icon>
            <b-icon icon="question-circle" v-else class="my-icon" font-scale="2"></b-icon>
            <span class="letters"> {{ $t('Header.Avatar.help') }} </span>
        </b-dropdown-item>
        <b-dropdown-divider class="my-divider"></b-dropdown-divider>
        <b-dropdown-item v-b-hover="handleHoverFour" class="my-dropdown-item">
            <b-icon icon="box-arrow-right" v-if="isHoveredFour" class="my-icon-hovered" font-scale="2"></b-icon>
            <b-icon icon="box-arrow-right" v-else class="my-icon" font-scale="2"></b-icon>
            <span class="letters"> {{ $t('Header.Avatar.logout') }} </span>
        </b-dropdown-item>
    </b-nav-item-dropdown>
</template>

<script>
export default {
    name: "Avatar",
    data() {
        return {
            isHovered: false,
            isHoveredTwo: false,
            isHoveredThree: false,
            isHoveredFour: false
        }
    },
    methods: {
        handleHover(hovered) {
            this.isHovered = hovered
        },
        handleHoverTwo(hovered) {
            this.isHoveredTwo = hovered
        },
        handleHoverThree(hovered) {
            this.isHoveredThree = hovered
        },
        handleHoverFour(hovered) {
            this.isHoveredFour = hovered
        }
    }
}
</script>

<style scoped>

.my-icon {
    fill: var(--elsa-blue);
}

.my-icon-hovered {
    fill: whitesmoke;
}

.letters {
    margin-left: 0.75vw;
    position: relative;
    bottom: 0.5vw;
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
