<template>
    <div style="position:relative">
        <!-- To use translation for props, type in the string which would be inside of $t() as a prop -->
        <BaseHeading name="TestPage.heading"></BaseHeading>
        <div style="position: fixed; margin-top: 50px; margin-left: 50px">
            {{ $t('TestPage.message') }}
            <p>
                <BaseIcon name="book" color="red" stroke-width="1"></BaseIcon>
            </p>
            <b-button class="dark-btn">{{ $t('TestPage.btn') }}</b-button>
            <b-button class="light-btn">{{ $t('TestPage.btn') }}</b-button>
            <b-button class="elsa-blue-btn">{{ $t('TestPage.btn') }}</b-button>
            <b-button class="elsa-blue-btn" disabled>{{ $t('TestPage.btn') }}</b-button>
            <b-button class="green-btn">{{ $t('TestPage.btn') }}</b-button>
            <b-button class="active-filter">{{ $t('TestPage.btn') }}</b-button>
            <b-button class="inactive-filter">{{ $t('TestPage.btn') }}</b-button>
        </div>


    </div>
</template>

<script>
export default {
    name: "TestPage"
}
</script>

<style scoped>

</style>
