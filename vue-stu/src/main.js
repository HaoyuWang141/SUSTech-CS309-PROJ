import './assets/main.css'
import { createApp } from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/theme-chalk/index.css'
import {createRouter, createWebHashHistory} from "vue-router";
import Home from './components/Home.vue'
import About from "@/components/About.vue";

const app = createApp(App)
app.use(ElementPlus)

const router = createRouter({
    history: createWebHashHistory(),
    routes: [
        { path: '/', redirect: '/home' },
        { path: '/home', component: Home, name: 'home' },
        { path: '/about', component: About, name: 'about' },
    ]
})
app.use(router)
app.mount('#app')