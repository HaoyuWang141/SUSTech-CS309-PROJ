// stores/auth.js
import { defineStore } from "pinia";
import axiosInstance from "@/axios/axiosConfig";

export const useAuthStore = defineStore("auth", {
    state: () => ({
        token: null as String | null,
        studentId: "" as String,
    }),
    getters: {
        isAuthenticated: (state) => !!state.token,
    },
    actions: {
        async login(studentId: String, password: String, callback: Function) {
            axiosInstance
                .post("/student/login", null, {
                    params: {
                        studentId: studentId,
                        password: password,
                    },
                })
                .then((response) => {
                    this.token = response.data;
                    axiosInstance.defaults.headers.common[
                        "Authorization"
                    ] = `Bearer ${this.token}`;
                    this.studentId = studentId;
                    localStorage.setItem("studentId",studentId.toString())
                })
                .then(() => {
                    axiosInstance.get("/hello").then((response) => {
                        console.log(response.data);
                    });
                })
                .then(() => {
                    callback();
                })
                .catch((error) => {
                    console.log(333);
                    console.log(error);
                    alert("用户名或密码错误");
                });
        },
        logout() {
            this.token = null;
            delete axiosInstance.defaults.headers.common["Authorization"];
        },
    },
});
