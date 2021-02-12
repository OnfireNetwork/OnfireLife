<template>
  <div class="interaction-hud" v-if="visible">
    <div class="center-circle">
      <v-icon x-large color="white">mdi-gesture-double-tap</v-icon>
    </div>
    <div v-for="(item,i) in items" :key="i" :class="{ option: true, ['opt' + positions[i]]: true, active: current === positions[i] }">
      <v-icon large color="white" style="margin-top: 10px;">mdi-{{ icons[item] || 'alert-circle' }}</v-icon>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CharacterHUD',
  data() {
    return {
      visible: false,
      mousePos: {
        x: 0,
        y: 0
      },
      items: [
        'lockcar',
        'inventory',
        'handsup',
        'phone',
        'test'
      ],
      icons: {
        test: 'flask',
        lockcar: 'key-variant',
        unlockcar: 'key-variant',
        cuff: 'handcuffs',
        uncuff: 'handcuffs',
        inventory: 'treasure-chest',
        handsup: 'human-handsup',
        phone: 'cellphone'
      }
    }
  },
  computed: {
    current() {
      const relPos = {
        x: this.mousePos.x - (window.innerWidth / 2),
        y: this.mousePos.y - (window.innerHeight / 2)
      }
      if(Math.sqrt(Math.pow(relPos.x, 2) + Math.pow(relPos.y, 2)) < 120)
        return null;
      const angle = (Math.atan(relPos.y / relPos.x) * (180 / Math.PI)) + (relPos.x > 0 ? 90 : 270)
      const item = Math.floor((angle + 18) / 36) % 10;
      return item
    },
    positions() {
      switch(this.items.length) {
        case 1:
          return [0]
        case 2:
          return [9, 1]
        case 3:
          return [9, 0, 1]
        case 4:
          return [8, 9, 1, 2]
        case 5:
          return [8, 9, 0, 1, 2]
        case 6:
          return [7, 8, 9, 1, 2, 3]
        case 7:
          return [7, 8, 9, 0, 1, 2, 3]
        case 8:
          return [6, 7, 8, 9, 1, 2, 3, 4]
        case 9:
          return [6, 7, 8, 9, 0, 1, 2, 3, 4]
        case 10:
          return [6, 7, 8, 9, 0, 1, 2, 3, 4, 5]
      }
      return []
    }
  },
  methods: {
    getCurrentItem() {
      const current = this.current
      if(current == null)
        return null
      const positions = this.positions
      for(let i=0; i<positions.length; i++) {
        if(positions[i] === current)
          return this.items[i]
      }
      return null
    },
    mousemove(e) {
      this.mousePos.x = e.clientX
      this.mousePos.y = e.clientY
    },
    click() {
      if(this.visible) {
        const item = this.getCurrentItem()
        if(item) {
          alert(item)
        }
      }
    }
  },
  created() {
    window.addEventListener("mousemove", this.mousemove)
    window.addEventListener("click", this.click)
  },
  beforeDestroy() {
    window.removeEventListener("mousemove", this.mousemove)
    window.removeEventListener("click", this.click)
  }
}
</script>

<style scoped lang="scss">
@use 'sass:math';
  .interaction-hud {
    color: white;
    text-align: center;
    user-select: none;
    .center-circle {
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
        padding-top: 70px;
        font-size: 24px;
        width: 180px;
        height: 180px;
        border-radius: 90px;
        border: 2px solid white;
        background-color: rgba($color: orange, $alpha: 0.4)
    }
    .option {
        width: 60px;
        height: 60px;
        border-radius: 30px;
        border: 1px solid white;
        background-color: rgba($color: orange, $alpha: 0.4);
        position: absolute;
        transform: translate(-50%, -50%);
        @for $i from 0 through 9 {
        &.opt#{$i} {
            left: calc(50% - #{- math.sin($i * 36deg) * 180}px);
            top: calc(50% - #{math.cos($i * 36deg) * 180}px);
        }
        }
        &.active {
            background-color: rgba($color: red, $alpha: 0.4);
        }
    }
  }
</style>
