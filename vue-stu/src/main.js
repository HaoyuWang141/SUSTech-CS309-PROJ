import './assets/main.less'
import { createApp } from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/theme-chalk/index.css'
import router from './router/index'
import axiosPlugin from './axios/axiosPlugin';
import { createPinia } from 'pinia'

const app = createApp(App)
const pinia = createPinia()

app.use(ElementPlus)
app.use(router)
app.use(axiosPlugin)
app.use(pinia)
app.mount('#app')