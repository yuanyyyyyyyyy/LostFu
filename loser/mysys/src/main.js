import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import { Table
 } from 'element-ui';
 import '@/utils/dialogDrag'
// import VueSocketIO from 'vue-socket.io'

// Vue.config.productionTip = false

// Vue.use(new VueSocketIO({
//   debug: true,
//   connection: 'http://localhost:8088',
// }))
Vue.config.productionTip = false
Vue.use(ElementUI);
Vue.use(Table)
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
