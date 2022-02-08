import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Element from 'element-ui'
import "element-ui/lib/theme-chalk/index.css"
Vue.use(Element)
import './axios.js' // 请求拦截

import axios from 'axios'
// 全局注册
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
// use
Vue.use(mavonEditor)
import './permission.js' // 路由拦截


Vue.prototype.$axios = axios 
Vue.prototype.$store = store //全局使用

Vue.config.productionTip = false

new Vue({
  router,
  store,
  beforeCreate(){
    Vue.prototype.$bus = this;
  },
  render: h => h(App)
}).$mount('#app')
