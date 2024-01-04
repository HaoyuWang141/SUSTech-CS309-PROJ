// stores/auth.js
import { defineStore } from "pinia";
import axiosInstance from "@/axios/axiosConfig";

export const useAuthStore = defineStore("auth", {
    state: () => ({
        token: null,
    }),
    getters: {
        isAuthenticated: (state) => !!state.token,
    },
    actions: {
        async login(studentId: String, password: String) {
            axiosInstance
                .post("/student/login", null, {
                    params: {
                        studentId: studentId,
                        password: password,
                    },
                })
                .then((response) => {
                    console.log(response.data);
                    this.token = response.data;
                    axiosInstance.defaults.headers.common[
                        "Authorization"
                    ] = `Bearer ${this.token}`;
                })
                .then(() => {
                    axiosInstance.get("/hello").then((response) => {
                        console.log(response.data);
                    });
                })
                .catch((error) => {
                    console.log(error);
                });
        },
        logout() {
            this.token = null;
            delete axiosInstance.defaults.headers.common["Authorization"];
        },
    },
});
