<template>
    <b-nav-item-dropdown right class="my-dropdown-menu" no-caret>
        <template #button-content>
            <b-icon v-if="theme === ''" icon="sun" class="my-icon"></b-icon>
            <b-icon v-else icon="moon" class="my-icon"></b-icon>
        </template>
        <b-dropdown-item @click.prevent="toggleTheme()" class="my-dropdown-item">
            <b-icon icon="moon" class="my-icon"></b-icon>
            <span class="letters"> {{ $t('Header.Avatar.darkmode') }} </span>
        </b-dropdown-item>
        <b-dropdown-divider class="my-divider"></b-dropdown-divider>
        <b-dropdown-item @click.prevent="toggleTheme()" class="my-dropdown-item">
            <b-icon icon="sun" class="my-icon"></b-icon>
            <span class="letters"> {{ $t('Header.Avatar.lightmode') }} </span>
        </b-dropdown-item>
    </b-nav-item-dropdown>
</template>

<script>
export default {
    name: "ModeSwitch",
    data() {
        return {
            'theme': ''
        }
    },
    methods: {
        toggleTheme() {
            this.theme = this.theme === 'darkMode' ? '' : 'darkMode';
            document.documentElement.setAttribute('data-theme', this.theme);
            localStorage.setItem('theme', this.theme);
        }
    }
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
    height: 2em;
    width: auto;
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
    background-color: var(--whitesmoke);
}

.my-dropdown-item:hover >>> .dropdown-item {
    background-color: var(--light-grey);
    transition-duration: 0.4s;
}


/* Settings for differently sized screens */
@media (max-width: 575.98px) {
    .dropdown-menu > li {
        font-size: 0.5em;
    }
}

@media (min-width: 576px) and (max-width: 767.98px) {
    .dropdown-menu > li {
        font-size: 0.41em;
    }
}

@media (min-width: 768px) and (max-width: 991.98px) and (max-height: 499.98px) {
    .dropdown-menu > li {
        font-size: 0.41em;
    }
}

@media (min-width: 768px) and (max-width: 991.98px) and (min-height: 500px) {
    .dropdown-menu > li {
        font-size: 0.75em;
    }
}

@media (min-width: 992px) {
    .dropdown-menu > li {
        font-size: 0.75em;
    }
}
</style>
