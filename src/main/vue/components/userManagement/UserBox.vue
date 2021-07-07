<template>
    <div v-if="!seen"
         :class="deactivated ? 'user-box justify-content-between inactive-user unseen-user' : 'user-box justify-content-between unseen-user'">
        <div>
            <span>
                {{ user.firstname }} {{ user.lastname }}
            </span>

            <span>
                <b-badge>{{ $t('AdminSettings.manage.new') }}</b-badge>
            </span>

        </div>

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

    <div v-else
         :class="deactivated ? 'user-box justify-content-between inactive-user' : 'user-box justify-content-between'">
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
        admin: Boolean,
        seen: Boolean
    }
}
</script>

<style scoped src="../../assets/css/userManagement.css">
</style>
