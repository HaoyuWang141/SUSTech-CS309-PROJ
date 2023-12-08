import { RouteRecordRaw, createRouter, createWebHistory } from "vue-router";

import Layout from "@/layout/index.vue";
import Welcome from "@/views/homepage/index.vue";
import Login from "@/views/login/index.vue";
import Home from "@/components/Home.vue";

const routes: Array<RouteRecordRaw> = [
    {
        path: "/layout",
        name: "Layout",
        component: Layout,
    },
    {
        path: "/",
        name: "Main",
        component: Layout,
        children: [
            {
                path: "",
                redirect: "/welcome",
            },
            {
                path: "welcome",
                name: "Welcome",
                component: Welcome,
            },
            {
                path: "login",
                name: "Login",
                component: Login,
            },
            {
                path: "home",
                name: "Home",
                component: Home,
            }
        ],
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
