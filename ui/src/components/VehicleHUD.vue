<template>
  <div>
    <div class="car-hud" v-if="type === 'car'">
      <div class="speed" :style="{ transform: 'rotate('+(110+Math.min(310, Math.max(10, speed)))+'deg)' }"></div>
      <div class="rpm" :style="{ transform: 'rotate('+((engine?130:120)+(Math.min(7000, Math.max(0, rpm))*0.04*engine))+'deg)' }"></div>
      <div class="fuel" :style="{ transform: 'rotate('+(130+(Math.min(100, Math.max(0, fuel))*2.8*engine))+'deg)' }"></div>
      <div class="gear" v-if="engine">{{ gearDisplay }}</div>
      <div class="distance" v-if="engine">{{String(distance).padStart(8, '0')}}</div>
      <v-icon :color="light ? 'green' : 'grey darken-3'" class="light">mdi-car-light-dimmed</v-icon>
      <v-icon :color="horn ? 'red' : 'grey darken-3'" class="horn">mdi-bugle</v-icon>
    </div>
    <div class="heli-hud" v-if="type === 'heli'">
      <div class="alt-minor" :style="{ transform: 'rotate('+(((this.height / 10000) * 360) - 90)+'deg)' }"></div>
      <div class="alt-major" :style="{ transform: 'rotate('+(((this.height / 10000) * 36) - 90)+'deg)' }"></div>
      <div class="fuel" :style="{ transform: 'rotate('+(46+(Math.min(100, Math.max(0, fuel))*-0.9*engine))+'deg)' }"></div>
      <div class="rpm" :style="{ transform: 'rotate('+(46+(80*-0.9*engine))+'deg)' }"></div>
      <div class="heading" :style="{ transform: 'rotate('+(-heading)+'deg)' }"></div>
      <v-icon class="heading-plane" size="60" color="grey lighten-1">mdi-airplane</v-icon>
      <div class="speed" :style="{ transform: 'rotate('+(-90+(speed*1.46))+'deg)' }"></div>
      <div class="horizon">
        <div :style="{ transform: 'translate(-97px, -100px) rotate('+(-roll)+'deg) translateY('+pitch+'px)' }">
          <div v-for="y in [-60, -50, -40, -30, -20, -10, 0, 10, 20, 30, 40, 50, 60]" :key="y" :style="{ transform: 'translateY(' + y + 'px)' }" />
        </div>
      </div>
      <div class="horizon-bug"><div class="left" /><div class="right" /></div>
      <div class="light" :class="{ on: light }"><v-icon small color="grey darken-2">mdi-track-light</v-icon></div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'VehicleHUD',
  data() {
    return {
      type: 'none',
      speed: 0,
      rpm: 0,
      fuel: 0,
      gear: 0,
      distance: 0,
      light: false,
      horn: false,
      engine: false,
      roll: 0,
      pitch: 0,
      height: 0,
      heading: 0,
      lastHeading: 0
    }
  },
  computed: {
    gearDisplay() {
      if(this.gear < 0)
        return 'R'
      if(this.gear == 0)
        return 'N'
      return this.gear
    }
  },
  created() {
    this.bus.$on('vehiclehud:data', data => {
      this.type = data.type || 'none'
      this.speed = data.speed || 0
      this.rpm = data.rpm || 0
      this.fuel = data.fuel || 0
      this.gear = data.gear || 0
      this.distance = data.distance || 0
      this.light = data.light || false
      this.horn = data.horn || false
      this.engine = data.engine || false
      this.roll = data.roll || 0
      this.pitch = data.pitch || 0
      this.height = data.height || 0
      let headingDiff = (data.heading || 0) - this.lastHeading
      this.lastHeading = data.heading || 0
      if(headingDiff > 180)
        headingDiff = -(360 - headingDiff)
      if(headingDiff < -180)
        headingDiff = 360 + headingDiff
      this.heading += headingDiff
    })
  }
}
</script>

<style scoped lang="scss">
  .car-hud {
    position: absolute;
    left: 50%;
    bottom: 0px;
    width: 500px;
    height: 200px;
    transform: translateX(-50%);
    background-image: url(../assets/img/tacho.png);
    background-repeat: no-repeat;
    background-size: contain;
    .speed {
        top: 55.7%;
        left: 16.4%;
        position: absolute;
        background: linear-gradient(90deg, transparent 50%, red 50%);
        height: 2px;
        width: 100px;
        transition-duration: 0.5s;
        transition-property: transform;
    }
    .rpm {
        top: 55.7%;
        left: 57.6%;
        position: absolute;
        background: linear-gradient(90deg, transparent 50%, red 50%);
        height: 2px;
        width: 100px;
        transition-duration: 0.5s;
        transition-property: transform;
    }
    .fuel {
        top: 24%;
        left: 43.1%;
        position: absolute;
        background: linear-gradient(90deg, transparent 50%, red 50%);
        height: 1px;
        width: 40px;
        transition-duration: 0.8s;
        transition-property: transform;
    }
    .gear {
        font-family: monospace;
        text-align: center;
        position: absolute;
        color: green;
        left: 47%;
        bottom: 6%;
        transform: translate(-50%, -50%);
    }
    .distance {
        font-size: 9px;
        font-family: monospace;
        text-align: center;
        position: absolute;
        color: green;
        left: 47%;
        bottom: 4.5%;
        transform: translate(-50%, -50%);
    }
    .light {
      position: absolute;
      left: 34%;
      top: 5%;
    }
    .horn {
      position: absolute;
      left: 55%;
      top: 5%;
    }
  }
  .heli-hud {
    position: absolute;
    left: 60%;
    bottom: 0px;
    width: 800px;
    height: 250px;
    transform: translateX(-50%);
    background-image: url(../assets/img/helipanel.png);
    background-repeat: no-repeat;
    background-size: contain;
    .alt-minor {
        top: 62%;
        left: 15.3%;
        position: absolute;
        background: linear-gradient(90deg, transparent 50%, lightgray 50%);
        height: 1px;
        width: 100px;
        transition-duration: 0.5s;
        transition-property: transform;
    }
    .alt-major {
        top: 61.5%;
        left: 18.7%;
        position: absolute;
        background: linear-gradient(90deg, transparent 50%, lightgray 50%);
        border-top-right-radius: 1px;
        border-bottom-right-radius: 1px;
        height: 3px;
        width: 45px;
        transition-duration: 0.5s;
        transition-property: transform;
    }
    .fuel {
        top: 25.5%;
        left: 49.4%;
        position: absolute;
        background: linear-gradient(90deg, transparent 50%, red 50%);
        height: 1px;
        width: 45px;
        transition-duration: 0.8s;
        transition-property: transform;
    }
    .rpm {
        top: 25.5%;
        left: 40.4%;
        position: absolute;
        background: linear-gradient(90deg, transparent 50%, red 50%);
        height: 1px;
        width: 45px;
        transition-duration: 6s;
        transition-property: transform;
    }
    .heading {
        top: 40%;
        left: 69.6%;
        position: absolute;
        background-image: url(../assets/img/helicompass.png);
        background-repeat: no-repeat;
        background-size: contain;
        height: 120px;
        width: 125px;
        transition-duration: 0.5s;
        transition-property: transform;
    }
    .heading-plane {
      position: absolute;
      left: 73.7%;
      top: 50%;
      transform: rotate(-1deg);
    }
    .speed {
        top: 62.5%;
        left: 53%;
        position: absolute;
        background: linear-gradient(90deg, transparent 50%, lightgray 50%);
        height: 1px;
        width: 80px;
        transition-duration: 0.8s;
        transition-property: transform;
    }
    .horizon {
      top: 42%;
      left: 33%;
      position: absolute;
      background-color: lightblue;
      overflow: hidden;
      border-radius: 55px;
      height: 105px;
      width: 105px;
      transition-duration: 0.5s;
      transition-property: transform;
      > div {
        width: 300px;
        height: 300px;
        background: linear-gradient(0deg, green 50%, lightblue 50%);
        div {
          position: absolute;
          width: 40px;
          left: 129px;
          top: 149px;
          border-top: 1px solid darkgray;
        }
      }
    }
    .horizon-bug {
      position: absolute;
      top: 57.5%;
      left: 35.5%;
      .left {
        display: inline-block;
        width: 30px;
        border-top: 2px solid darkorange;
        border-right: 2px solid darkorange;
        height: 7px;
      }
      .right {
        display: inline-block;
        margin-left: 5px;
        width: 30px;
        border-top: 2px solid darkorange;
        border-left: 2px solid darkorange;
        height: 7px;
      }
    }
    .light {
      text-align: center;
      border-radius: 3px;
      position: absolute;
      width: 20px;
      height: 20px;
      background-color: rgb(70, 65, 59);
      left: 35%;
      top: 16%;
      border: 1px solid rgb(45, 45, 59);
      line-height: 1.1;
      &.on {
        border-color: rgb(62, 62, 70);
        box-shadow: 0px 0px 21px -2px rgba(247,216,123,1);
        background-color: rgb(153, 117, 50);
        .v-icon {
          color: rgb(255, 230, 184) !important;
        }
      }
    }
  }
</style>
