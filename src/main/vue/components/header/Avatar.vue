<template>
    <b-nav-item-dropdown right class="my-dropdown-menu" no-caret @hide="onDropdownHide">
        <template #button-content>
            <b-icon icon="person-circle" class="my-icon"></b-icon>
        </template>

        <!-- Account Settings -->
        <b-dropdown-item class="my-dropdown-item" @click="routeToProfile">
            <b-icon icon="person-circle" class="my-icon"></b-icon>
            <span class="letters"> {{ $t('Header.Avatar.profile') }} </span>
        </b-dropdown-item>

        <b-dropdown-divider class="my-divider"></b-dropdown-divider>

        <!-- Admin Settings -->
        <b-dropdown-item
            v-if="isAdmin"
            class="my-dropdown-item"
            @click.stop="toggleAdmin">
            <b-iconstack class="my-icon">
                <b-icon stacked icon="gear" class="my-icon"></b-icon>
                <b-icon stacked icon="circle-fill" class="my-icon" scale="0.5" style="fill: var(--whitesmoke)"></b-icon>
                <b-icon stacked icon="person-fill" class="my-icon" scale="0.7" shift-v="1"></b-icon>
            </b-iconstack>
            <span class="letters"> {{ $t('Header.Avatar.adminSetting.settings') }} </span>

            <div v-bind:class="[mobile ? 'sub-menu-mobile':'sub-menu']" v-show="showAdmin">
                <b-dropdown-item class="my-inner-dropdown-item" @click="routeToUserManage">
                    <b-iconstack class="my-icon">
                        <b-icon stacked icon="person" class="my-icon"></b-icon>
                        <b-icon stacked icon="pencil-fill" class="my-icon" scale="0.5" shift-v="-2.5" shift-h="6"
                                rotate="5"></b-icon>
                    </b-iconstack>
                    <span class="letters"> {{ $t('Header.Avatar.adminSetting.manage') }} </span>
                </b-dropdown-item>

                <b-dropdown-divider class="my-divider"></b-dropdown-divider>

                <b-dropdown-item class="my-inner-dropdown-item" @click="routeToCorporate">
                    <b-icon icon="brush" class="my-icon"></b-icon>
                    <span class="letters"> {{ $t('Header.Avatar.adminSetting.design') }} </span>
                </b-dropdown-item>

                <b-dropdown-divider class="my-divider"></b-dropdown-divider>

                <b-dropdown-item class="my-inner-dropdown-item">
                    <b-icon icon="at" class="my-icon"></b-icon>
                    <span class="letters"> {{ $t('Header.Avatar.adminSetting.filter') }} </span>
                </b-dropdown-item>
            </div>


        </b-dropdown-item>

        <b-dropdown-divider v-if="isAdmin" class="my-divider"></b-dropdown-divider>

        <!-- Mode switch -->
        <b-dropdown-item v-if="theme === ''" class="my-dropdown-item" @click="toggleTheme('darkMode')">
            <b-icon icon="moon" class="my-icon"></b-icon>
            <span class="letters"> {{ $t('Header.Avatar.darkmode') }} </span>
        </b-dropdown-item>
        <b-dropdown-item v-else class="my-dropdown-item" @click="toggleTheme('')">
            <b-icon icon="sun" class="my-icon"></b-icon>
            <span class="letters"> {{ $t('Header.Avatar.lightmode') }} </span>
        </b-dropdown-item>

        <b-dropdown-divider class="my-divider"></b-dropdown-divider>

        <!-- Help -->
        <b-dropdown-item class="my-dropdown-item" @click="routeToHelp">
            <b-icon icon="question-circle" class="my-icon"></b-icon>
            <span class="letters"> {{ $t('Header.Avatar.help') }} </span>
        </b-dropdown-item>
        <b-dropdown-divider class="my-divider"></b-dropdown-divider>
        <b-dropdown-item class="my-dropdown-item" @click="logout">
            <b-icon icon="box-arrow-right" class="my-icon"></b-icon>
            <span class="letters"> {{ $t('Header.Avatar.logout') }} </span>
        </b-dropdown-item>
    </b-nav-item-dropdown>
</template>

<script>
import {mapGetters} from 'vuex';

export default {
    name: "Avatar",
    data() {
        return {
            'showAdmin': false,
            'mobile': window.innerWidth < 576,
            close: true
        }
    },
    created() {
        // reacts when screen size changes
        window.addEventListener("resize", this.updateMobile);
        // setting theme
        document.documentElement.setAttribute('data-theme', this.theme);
    },
    destroyed() {
        // removes event listener
        window.removeEventListener("resize", this.updateMobile);
    },
    methods: {
        toggleClose() {
            this.close = !this.close
        },
        onDropdownHide(bvEvent) {
            if (!this.close) {
                bvEvent.preventDefault();
            }
        },
        routeToProfile() {
            this.$router.push('/' + this.$i18n.locale + '/user')
        },
        routeToHelp() {
            this.$router.push('/' + this.$i18n.locale + '/help')
        },
        routeToCorporate() {
            this.$router.push('/' + this.$i18n.locale + '/adminSettings/corporate')
        },
        routeToUserManage() {
            this.$router.push('/' + this.$i18n.locale + '/adminSettings/userManagement')
        },
        toggleTheme(mode) {
            //changes mode
            this.$store.dispatch('theme/setTheme', {theme: mode})
            document.documentElement.setAttribute('data-theme', this.theme);
        },
        toggleAdmin() {
            if (!this.mobile) {
                this.toggleClose()
            }
            this.showAdmin = !this.showAdmin
        },
        logout() {
            localStorage.removeItem('store')
            localStorage.clear()
            this.$router.push('/' + this.$i18n.locale + '/login')
        },
        updateMobile() {
            // sets mobile depending on screen width (if smaller than 576 dropdown menu is collapsed)
            this.mobile = window.innerWidth < 576
        }
    },
    computed: {
        ...mapGetters({
            isAdmin: 'isAdmin',
            theme: 'theme/getTheme'
        })
    }
}
</script>

<style scoped>

.sub-menu {
    position: absolute;
    right: 100%;
    top: 0;
    padding: 0;
    color: var(--dark-grey);
    text-align: left;
    list-style: none;
    background-clip: border-box;
    border: 1px solid var(--elsa-blue);

}

.sub-menu-mobile {
    color: var(--dark-grey);
    text-align: left;
    list-style: none;
    border: 1px solid var(--elsa-blue);
}

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
    border-color: var(--light-grey);
}

.my-dropdown-item >>> .dropdown-item {
    color: var(--dark-grey);
    padding-left: 0.5vw;
    background-color: var(--whitesmoke);
    position: relative;
    z-index: 1 !important;
}

.my-dropdown-item:hover >>> .dropdown-item {
    background-color: var(--closed-doc-hover);
    transition-duration: 0.4s;
}

.my-inner-dropdown-item:hover >>> .dropdown-item {
    background-color: var(--open-doc-hover);
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
