<template>
    <div>
        {{$t('TestPage.message')}}
        <p><BaseIcon name="book" color="red" stroke-width="1"></BaseIcon></p>
        <b-button class="dark-btn">{{$t('TestPage.btn')}}</b-button>
        <b-button class="light-btn" >{{$t('TestPage.btn')}}</b-button>
        <b-button class="elsa-blue-btn" >{{$t('TestPage.btn')}}</b-button>
        <b-button class="elsa-blue-btn"  disabled>{{$t('TestPage.btn')}}</b-button>
        <b-button class="green-btn">{{$t('TestPage.btn')}}</b-button>
        <b-button class="active-filter">{{$t('TestPage.btn')}}</b-button>
        <b-button class="inactive-filter">{{$t('TestPage.btn')}}</b-button>
    </div>
</template>

<script>
export default {
    name: "TestPage"
}
</script>

<style scoped>

</style>
