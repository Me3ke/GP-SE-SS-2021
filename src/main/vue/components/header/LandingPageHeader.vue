<template>
  <b-navbar toggleable="sm" id="background" sticky>
    <!-- To-Do: Add  real Route to Home -->
    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
    <b-collapse id="nav-collapse" is-nav style="height: 4em">
      <b-navbar-nav class="ml-auto">
        <LanguageSwitcher style="margin-top: 0.25em"></LanguageSwitcher>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</template>

<script>
import LanguageSwitcher from "@/main/vue/components/header/LanguageSwitcher";
export default {
  name: "LandingPageHeader",
  components: {LanguageSwitcher},
}
</script>

<style scoped>

#responsive-img {
  height: 2em;
  width: auto;
}

#background {
  background-image: linear-gradient(to right, rgba(0, 0, 0, 0.1), rgba(0, 0, 0, 0.2), rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.2), rgba(0, 0, 0, 0.1)),
  url(../../assets/header_background.png);
  background-size: cover;
  align-content: center;
}

/* Settings for differently sized screens */
@media (max-width: 575.98px) {
  .navbar-brand, .collapse, .navbar-toggler {
    font-size: 0.5em;
  }
}

@media (min-width: 576px) and (max-width: 767.98px) {
  .navbar-brand, .collapse, .navbar-toggler {
    font-size: 0.41em;
  }
}

@media (min-width: 768px) and (max-width: 991.98px) and (max-height: 499.98px) {
  .navbar-brand, .collapse, .navbar-toggler {
    font-size: 0.41em;
  }
}

@media (min-width: 768px) and (max-width: 991.98px) and (min-height: 500px) {
  .navbar-brand, .collapse, .navbar-toggler {
    font-size: 0.75em;
  }
}

@media (min-width: 992px) {
  .navbar-brand, .collapse, .navbar-toggler {
    font-size: 0.75em;
  }
}
</style>