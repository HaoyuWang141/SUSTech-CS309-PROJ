import {createWebHistory, createRouter} from "vue-router";

import Login from "@/components/LogIn";
import MainPage from "@/components/MainPage";
import Register from "@/components/Register.vue";
import PostEdit from "@/components/PostEdit.vue";
import PostShow from "@/components/PostShow";
import UserInfo from "@/components/UserInfo.vue";
import PostCreate from "@/components/PostCreate";
import ManageDorm from "@/components/ManageDorm";
import ManageStu from "@/components/ManageStu";
import ManageNote from "@/components/ManageNote";
import ManageAllocate from "@/components/ManageAllocate";

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
    {
        path: "/mainpage",
        name: "MainPage",
        component: MainPage,
    },

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
        path: "/postedit/:postId",
        name: "PostEdit",
        component: PostEdit
    },

    {
        path: "/postcreate",
        name: "PostCreate",
        component: PostCreate
    },

    {
        path: "/postshow/:postId",
        name: "PostShow",
        component: PostShow
    },

    {
        path: "/userinfo/:uid",
        name: "Userinfo",
        component: UserInfo
    },
    // {
    //     path: "/section/:secId",
    //     name: "SectionShow",
    //     component: SectionShow
    // },
    {
        path: "/register",
        name: "RegisterPage",
        component: Register,
    }
]

export const router = createRouter({
    history: createWebHistory(),
    routes: routes,
})

export default router
