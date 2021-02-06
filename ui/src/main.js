import Vue from 'vue'
import App from './App.vue'
import store from './store'
import vuetify from './plugins/vuetify';
import '@babel/polyfill'
import 'roboto-fontface/css/roboto/roboto-fontface.css'
import '@mdi/font/css/materialdesignicons.css'

import bus from './bus'

window.emitVueEvent = (name, data) => {
  bus.$emit(name, data)
}

Vue.config.productionTip = false

Vue.mixin({
  data() {
    return {
      bus: bus
    }
  }
})

new Vue({
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')