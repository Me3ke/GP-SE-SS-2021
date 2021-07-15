<template>
  <div>
    <div>
      <Header></Header>
    </div>

    <BaseHeading name="AdminSettings.trustedDomain.title"></BaseHeading>

    <b-container fluid="xl" style="overflow: hidden">
      <div :class="[showOverflow ? 'overflow-auto' : '']" style="height: 85vh">

        <DomainSettingsBox></DomainSettingsBox>
        <SMTPServerSettingsBox></SMTPServerSettingsBox>
      </div>
    </b-container>
  </div>
</template>

<script>
import Header from "@/main/vue/components/header/Header";
import DomainSettingsBox from "@/main/vue/components/settingsPage/DomainSettingsBox";
import SMTPServerSettingsBox from "@/main/vue/components/settingsPage/SMTPServerSettingsBox";

export default {
  name: "TrustedDomain",
  components: {
      SMTPServerSettingsBox,
    DomainSettingsBox, Header
  },
  data() {
    return {
      showOverflow: true
    }
  },
  methods: {
    toggleOverflow() {
      this.showOverflow = !this.showOverflow
    }
  }
}
</script>

<style scoped>
</style>
