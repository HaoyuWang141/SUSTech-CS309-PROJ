import {createWebHistory, createRouter} from "vue-router";

import Login from "@/components/LogIn";
import MainPage from "@/components/MainPage";
import ManageDorm from "@/components/ManageDorm";
import ManageStu from "@/components/ManageStu";
import ManageNote from "@/components/ManageNote";
import ManageAllocate from "@/components/ManageAllocate";
import EXL from "@/components/EXL";

const routes = [
    {
        path: "/",
        redirect: "/login"
    },
    {
        path: "/login",
        name: "Login",
        component: Login,
    },
    // {
    //     path: "/mainpage",
    //     name: "MainPage",
    //     component: MainPage,
    // },

    {
        path: "/dormitory",
        name: "ManageDorm",
        component: ManageDorm
    },
    {
        path: "/student",
        name: "ManageStu",
        component: ManageStu
    },
    {
        path: "/notification",
        name: "ManageNote",
        component: ManageNote
    },
    {
        path: "/allocate",
        name: "ManageAllocate",
        component: ManageAllocate
    },
    {
        path: "/123",
        name: "123",
        component: EXL
    }
]

export const router = createRouter({
    history: createWebHistory(),
    routes: routes,
})

export default router
