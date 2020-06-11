import Vue from 'vue'
import App from './App.vue'
import {router} from './router'
import i1view from 'iview'
import 'iview/dist/styles/iview.css'; // 导入样式
import instance  from './Util'
import store from './store'
window.$router = router;
Vue.prototype.$axios = instance
Vue.config.productionTip = false
Vue.use(i1view);
new Vue({
  render: h => h(App),
  router,
  store,
  beforeCreate () {
    this.$store.dispatch('routerAdd', null);
    this.$store.dispatch('setControls', null);
  }
}).$mount('#app')
