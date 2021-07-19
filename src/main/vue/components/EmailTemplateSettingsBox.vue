<template>
    <b-container fluid="sm" style="padding-left: 10px; padding-right: ">

        <b-row align-h="center" align-v="center" class="text-center">
            <div class="card">
                <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                    <h4>
                        {{ $t('EmailTemplate.emailTemSettings') }}
                    </h4>
                </div>
                <b-list-group flush>
                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                        <EmailTemplate style="text-align: left;"></EmailTemplate>
                    </b-list-group-item>
                </b-list-group>
            </div>
        </b-row>

    </b-container>
</template>

<script>
import EmailTemplate from "@/main/vue/components/EmailTemplate";
export default {
    name: "EmailTemplateSettingsBox",
    components: {EmailTemplate}
}
</script>

<style scoped src="../assets/css/settingsPage.css">

</style>
