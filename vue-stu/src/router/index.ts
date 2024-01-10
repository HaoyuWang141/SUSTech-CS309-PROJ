import {RouteRecordRaw, createRouter, createWebHistory} from "vue-router";

import Layout from "@/layout/index.vue";
import Welcome from "@/views/welcome/index.vue";
import Login from "@/views/login/index.vue";
import Homepage from "@/views/homepage/index.vue";
import Profile from "@/views/profile/index.vue";
import Dormitory from "@/views/dormitory/index.vue";

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
                name: "redirect",
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
                component: Homepage,
            },
            {
                path: "profile",
                name: "Profile",
                component: Profile,
            },
            {path: "dormitory", name: "Dormitory", component: Dormitory},
        ],
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

router.beforeEach(async (to, from) => {
    if (to.name === "Welcome" || to.name === "Login") {
        return true;
    }

    const isAuthenticated = localStorage.getItem("studentId");
    if (!isAuthenticated) {
        return {name: "Login"};
    }
});

export default router;
