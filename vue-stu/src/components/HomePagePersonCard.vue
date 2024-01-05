<script setup>
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import { User } from "@element-plus/icons-vue";
import axiosInstance from "@/axios/axiosConfig";

const router = useRouter();
const name = ref("");
const sid = ref("");
const gender = ref(1);
const major = ref("CS");

function goProfile() {
    router.push("profile");
}

async function getSelfInfo() {
    try {
        console.log("Current student: " + localStorage.getItem("studentId"));
        await axiosInstance
            .get("/admin/studentAccount/getStudent", {
                params: {
                    studentId: localStorage.getItem("studentId"),
                },
            })
            .then((response) => {
                console.log("getSelfInfo() -> ");
                console.log(response);
                name.value = response.data.name;
                sid.value = response.data.student_id;
                gender.value = response.data.gender;
                localStorage.setItem("currentTeam", response.data.team_id);
            });
    } catch (error) {
        console.error(error);
    }
}

onMounted(() => {
    getSelfInfo();
});
</script>

<template>
    <el-card class="simple-person-card">
        <template #header>
            <div class="simple-card-header">
                <el-icon>
                    <User />
                </el-icon>
                <span> 个人信息 </span>
            </div>
        </template>
        <div class="simple-card-body">
            <div class="simple-card-left">
                <el-image
                    style="width: 90%; margin-left: 10%"
                    :src="'src/assets/Kobe.jpg'"
                />
            </div>
            <div class="simple-card-right">
                <el-row style="margin-bottom: 10px">
                    <span>姓名：</span>
                    <span>{{ name }}</span>
                </el-row>
                <el-row style="margin-bottom: 10px">
                    <span>学号：</span>
                    <span>{{ sid }}</span>
                </el-row>
                <el-row style="margin-bottom: 10px">
                    <span>性别：</span>
                    <span>{{ gender === 1 ? "男" : "女" }}</span>
                </el-row>
                <el-row style="margin-bottom: 10px">
                    <span>专业：</span>
                    <span>{{ major }}</span>
                </el-row>
                <el-row>
                    <el-button type="primary" @click="goProfile">
                        编辑个人信息
                    </el-button>
                </el-row>
            </div>
        </div>
    </el-card>
</template>

<style scoped>
.simple-person-card {
    border-radius: 20px;
    margin-left: 5%;
    margin-top: 5%;
    margin-bottom: 2.5%;
    margin-right: 2.5%;
    background-color: rgba(0, 0, 0, 0.65);
    border-color: rgba(194, 206, 219, 0.8);
    border-width: 4px;
    border-style: solid;
    border-radius: 40px;
    color: white;
}

.simple-card-header {
    text-align: center;
    font-size: 18px;
    font-weight: bold;
    color: white;
}

.simple-card-body {
    display: flex;
}

.simple-card-left {
    margin-right: 10%;
}

.simple-card-right {
    display: flex;
    flex-direction: column;
    align-items: self-start;
    margin-top: 50px;
    margin-left: 50px;
    font-size: large;
}

.el-button {
    margin-top: 10px;
    margin-left: 200px;
    margin-right: 10px;
    margin-bottom: 10px;
    background-color: rgba(194, 206, 219, 0.8);
    border: None;
    color: #072056;
    font-size: 16px;
    font-weight: bold;
}
</style>
