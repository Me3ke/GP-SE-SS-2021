<template>
    <b-container fluid="sm">
        <b-row align-h="center" align-v="center" class="text-center">
            <div class="card">
                <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                    <h4>
                        {{ $t('AdminSettings.corporate.companyDetails') }}
                    </h4>
                </div>
                <b-list-group>
                    <b-list-group-item class="d-flex justify-content-center align-items-center">
                        <ImpressumEditor></ImpressumEditor>
                    </b-list-group-item>
                </b-list-group>
            </div>
        </b-row>
    </b-container>
</template>

<script>
import ImpressumEditor from "@/main/vue/components/corporateDesign/ImpressumEditor";
export default {
    name: "CompanyDetailsContainer",
    components: {ImpressumEditor}
}
</script>

<style scoped src="../../assets/css/settingsPage.css">
</style>
