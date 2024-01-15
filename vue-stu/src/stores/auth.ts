// stores/auth.js
import { defineStore } from "pinia";
import axiosInstance from "@/axios/axiosConfig";
import { ref, computed } from "vue";

export const useAuthStore = defineStore("auth", () => {
    const token = ref<string>("");
    const studentId = ref<string>("");

    const isAuthenticated = computed(() => !!token);

    async function login(username: string, password: string) {
        await axiosInstance
            .post("/student/login", null, {
                params: {
                    studentId: username,
                    password: password,
                },
            })
            .then((response) => {
                token.value = response.data;
                axiosInstance.defaults.headers.common[
                    "Authorization"
                ] = `Bearer ${token.value}`;
                studentId.value = username;
                localStorage.setItem("studentId", username);
            })
            .catch((error) => {
                throw new Error("用户名或密码错误!");
            });
    }

    function logout() {
        token.value = "";
        studentId.value = "";
        delete axiosInstance.defaults.headers.common["Authorization"];
        localStorage.removeItem("studentId");
        localStorage.removeItem("currentTeam");
        localStorage.removeItem("state");
    }

    return {
        token,
        studentId,
        isAuthenticated,
        login,
        logout,
    };
});
