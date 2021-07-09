<template>
    <span>
        <span :class="{active: isActiveMutate, inactive: !isActiveMutate}" @click="changeActive()"
              style="padding: 0.5vh 1vw 0; margin:0">
            <h4 v-if="!userManagement">
                {{ this.text }}
            </h4>
            <div v-else>
                  {{ this.text }}
            </div>
        </span>
    </span>
</template>

<script>
export default {
    name: 'FilterButton',
    props: {
        text: String,
        isActive: Boolean,
        switch: {
            type: Boolean,
            default: false
        },
        userManagement: {
            type: Boolean,
            default: false
        }
    },
    data() {
        return {
            isActiveMutate: this.isActive
        }
    },
    methods: {
        // emits event that filter got clicked
        changeActive() {
            this.$emit('activeChange')
            this.isActiveMutate = !this.isActiveMutate
        }
    },
    watch: {
        // watches for changes in inActive prop, if only one of multiple filters should be activated at once
        // changes value of isActiveMutate to current prop value
        isActive: {
            immediate: true,
            handler(val) {
                if (this.switch) {
                    this.isActiveMutate = val
                }
            }
        }
    }
}
</script>

<style scoped>
.active {
    background-color: var(--dark-grey);
    color: var(--whitesmoke);
    border: 0.03vw solid var(--dark-grey);
    border-radius: 70px;
    display: inline-flex
}

.active:focus {
    background-color: var(--dark-grey);
    border: 0.13vw solid var(--dark-grey);
    outline: 0.06vw solid var(--light-grey);
    outline-offset: 0;
}

.inactive {
    background-color: var(--whitesmoke);
    color: var(--dark-grey);
    border: 0.03vw solid var(--dark-grey);
    border-radius: 70px;
    display: inline-flex;
}

.inactive:focus {
    background-color: var(--whitesmoke);
    border: 0.13vw solid var(--dark-grey);
    outline: 0.06vw solid var(--light-grey);
    outline-offset: 0;
}

.inactive:hover {
    background-color: var(--light-grey);
    transition-duration: 0.4s;
}
</style>
