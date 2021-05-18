<template>
    <span>
        <span :class="{active: isActive, inactive: !isActive}" @click="isActive = !isActive" style="padding: 0.5vh 1vw 0; margin:0">
            <h4>
                {{this.text}}
            </h4>
        </span>
    </span>
</template>

<script>
export default {
    name: 'FilterButton',
    props: {
        text: String,
        isActive: Boolean
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
