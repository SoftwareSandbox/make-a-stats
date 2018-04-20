import Vue from 'vue'
import App from './App.vue'
import VueResource from 'vue-resource'

Vue.use(VueResource)

let hostname = window.location.hostname

Vue.config.productionTip = false
Vue.url.options.root = 'http://' + hostname + ':3000/'

new Vue({
  render: h => h(App)
}).$mount('#app')
