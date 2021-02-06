<template>
  <div class="status-hud" v-if="visible">
    <div class="status">
      <div class="saturation" :style="{ height: saturation + '%' }" />
      <v-icon :color="saturation <= 15 ? 'red' : 'white'" large class="mt-3">mdi-hamburger</v-icon>
    </div>
    <div class="status">
      <div class="hydration" :style="{ height: hydration + '%' }" />
      <v-icon :color="hydration <= 15 ? 'red' : 'white'" large class="mt-3">mdi-beer</v-icon>
    </div>
    <div class="status">
      <div class="health" :style="{ height: health + '%' }" />
      <v-icon :color="health <= 15 ? 'red' : 'white'" large class="mt-3">mdi-heart</v-icon>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CharacterHUD',
  data() {
    return {
      visible: true,
      saturation: 100,
      hydration: 100,
      health: 100
    }
  }
}
</script>

<style scoped lang="scss">
  .status-hud {
    position: absolute;
    right: 10px;
    bottom: 10px;
    .status {
      text-align: center;
      margin-left: 7px;
      margin-right: 7px;
      position: relative;
      float: left;
      width: 60px;
      height: 60px;
      overflow: hidden;
      border-radius: 30px;
      background-color: rgba($color: #606060, $alpha: 0.5);
      div {
        position: absolute;
        bottom: 0px;
        width: 100%;
        &.saturation {
          background-color: orange;
        }
        &.hydration {
          background-color: blue;
        }
        &.health {
          background-color: red;
        }
        opacity: 0.5;
      }
    }
  }
</style>
