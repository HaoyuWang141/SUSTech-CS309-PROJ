// axiosPlugin.ts
import { App } from "vue";
import axiosInstance from "./axiosConfig";

export default {
    install(app: App) {
        app.config.globalProperties.$axios = axiosInstance;
    },
};
