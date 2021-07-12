<template>
  <div>
    <div>
      <Header></Header>
    </div>

    <BaseHeading name="AdminSettings.trustedDomain.title"></BaseHeading>

    <b-container fluid="xl" style="overflow: hidden">
      <div :class="[showOverflow ? 'overflow-auto' : '']" style="height: 85vh">

        <DomainSettingsBox></DomainSettingsBox>

      </div>
    </b-container>
  </div>
</template>

<script>
import Header from "@/main/vue/components/header/Header";
import DomainSettingsBox from "@/main/vue/components/settingsPage/DomainSettingsBox";

export default {
  name: "TrustedDomain",
  components: {
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
