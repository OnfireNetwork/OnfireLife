<template>
  <div id="app">
    <v-app>
      <CharacterHUD />
      <InteractionHUD />
      <VehicleHUD />
      <Inventory />
    </v-app>
  </div>
</template>

<script>
import CharacterHUD from './components/CharacterHUD.vue'
import InteractionHUD from './components/InteractionHUD.vue'
import VehicleHUD from './components/VehicleHUD.vue'
import Inventory from './components/Inventory.vue'

export default {
  name: 'App',
  components: {
    CharacterHUD,
    InteractionHUD,
    VehicleHUD,
    Inventory
  },
  data() {
    return {

    }
  }
}
</script>

<style lang="scss">
html {
  overflow: hidden !important;
}

body {
  margin: 0px;
  background-image: url(assets/img/onset.jpg);
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  background-color: transparent;
}
</style>
