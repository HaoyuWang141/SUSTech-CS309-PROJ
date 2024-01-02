import './assets/main.css'
import { createApp } from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/theme-chalk/index.css'
import router from './router/index'
import axiosPlugin from './axios/axiosPlugin';

const app = createApp(App)
app.use(ElementPlus)
app.use(router)
app.use(axiosPlugin)
app.mount('#app')