import {createApp} from 'vue'
import ElementUI from 'element-plus'
import 'element-plus/theme-chalk/index.css'

//引入quill-editor编辑器
// import VueQuillEditor from 'vue-quill-editor'
// import 'quill/dist/quill.core.css'
// import 'quill/dist/quill.snow.css'
// import 'quill/dist/quill.bubble.css'
// Vue.use(VueQuillEditor)


//实现quill-editor编辑器拖拽上传图片
// import Quill from 'quill'
// import { ImageDrop } from 'quill-image-drop-module'
// Quill.register('modules/imageDrop', ImageDrop)

//实现quill-editor编辑器调整图片尺寸
// import { ImageResize } from 'quill-image-resize-module'
// Quill.register('modules/imageResize', ImageResize)


import App from './App'
import router from './router'
import axios from 'axios'


axios.defaults.headers.post['Content-Type'] = 'application/json';
// global.axios=axios;

createApp(App)
    .use(router)
    .use(ElementUI)
    // .use(VueQuillEditor)
    .mount('#app')
