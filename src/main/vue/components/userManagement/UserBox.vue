<template>
    <div :class="deactivated ? 'user-box justify-content-between inactive-user' : 'user-box justify-content-between'">
        <span>
            {{ user.firstname }} {{ user.lastname }}
        </span>

        <span>
               {{ user.email }}
        </span>

        <span v-if="deactivated" style="font-weight: 503">
               {{ $t('AdminSettings.manage.filter.inactive') }}
        </span>
        <span v-if="admin" style="color: var(--elsa-blue); font-weight: 503">
               {{ $t('AdminSettings.manage.filter.admin') }}
        </span>
        <span v-if="!deactivated && !admin"></span>
    </div>
</template>

<script>
export default {
    name: "UserBox",
    props: {
        user: Object,
        deactivated: Boolean,
        admin: Boolean
    }
}
</script>

<style scoped src="../../assets/css/userManagement.css">
</style>
