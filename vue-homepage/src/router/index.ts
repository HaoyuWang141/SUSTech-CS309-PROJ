// router/index.ts
import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router';

// 定义路由
// 每个路由都需要映射到一个组件。
const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        redirect: '/home'
    },
    {
        path: '/home',
        name: 'Home',
        component: () => import('../views/Homepage.vue')
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/Login.vue')
    }
];

// 创建路由实例并传递 `routes` 配置
const router = createRouter({
    // 使用 HTML5 的 History 模式
    history: createWebHistory(),
    routes
});

export default router;
