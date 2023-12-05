import './assets/main.css'

import Page from './FirstPage.vue'
import { createApp } from 'vue'
import router from './router/index'

const app = createApp(Page)
app.use(router)
app.mount('#app')
