<script setup lang="ts">
import { onMounted, ref, watch, nextTick, reactive } from "vue";
import { useRoute, useRouter } from "vue-router";
import axiosInstance from "@/axios/axiosConfig";

const bg_color = ref<HTMLElement | null>(null);
const content = ref<HTMLElement | null>(null);
const route = useRoute();
const router = useRouter();

const updateBgHeight = () => {
    if (content.value) {
        const contentHeight = content.value.clientHeight;
        if (bg_color.value) {
            bg_color.value.style.height = `${contentHeight}px`;
        }
    }
};

onMounted(() => {
    updateBgHeight();
    if (content.value) {
        const observer = new MutationObserver((mutations) => {
            for (let mutation of mutations) {
                if (mutation.type === "childList") {
                    updateBgHeight();
                }
            }
        });

        observer.observe(content.value, { childList: true, subtree: true });
    }
});

function backToHome() {
    router.push("/home");
}

function showLogOut() {
    console.log(localStorage.getItem("studentId") != undefined);
    return localStorage.getItem("studentId") != undefined;
}

function logout() {
    localStorage.removeItem("studentId");
    localStorage.removeItem("currentTeam");
    router.push("/welcome");
    console.log(localStorage.getItem("studentId") != null);
}

const currentStage = ref(0);
async function getCurrentStage() {
    try {
        await axiosInstance
            .get("/student/dormitory/getStage", {
                params: {
                    studentAccountId: localStorage.getItem("studentId"),
                },
            })
            .then((response) => {
                console.log("getCurrentStage() ->");
                console.log(response);
                currentStage.value = response.data;
            });
    } catch (error) {
        console.error(error);
    }
}

getCurrentStage();
</script>

<template>
    <div ref="bg_color" class="bg-color" />
    <div class="bg-picture" />
    <div class="bg-shadow" />
    <div ref="content" class="content">
        <header class="top-bar">
            <!--      <el-row style="width: 100%">-->
            <!--        <el-col :span="6">-->
            <el-button
                @click="backToHome"
                link
                style="
                    font-size: 30px;
                    font-weight: bold;
                    color: #dfe1d9;
                    align-items: center;
                "
            >
                SUSTech Dormitory
            </el-button>
            <!--        </el-col>-->
            <!--        <el-col style="align-items: center;"-->
            <!--                :span="12">-->
            <div class="steps-container">
                <el-steps align-center :active="currentStage" class="stages">
                    <el-step title="组队阶段"></el-step>
                    <el-step title="收藏阶段"></el-step>
                    <el-step title="正选阶段"></el-step>
                    <el-step title="结束阶段"></el-step>
                </el-steps>
            </div>
            <!--        </el-col>-->
            <!--        <el-col style="display: flex; flex-direction: row-reverse; align-items: center"-->
            <!--                :span="6">-->
            <div class="labels">
                <el-button
                    type="danger"
                    v-show="showLogOut()"
                    @click="logout()"
                >
                    登出
                </el-button>
            </div>
            <!--        </el-col>-->
            <!--      </el-row>-->
        </header>
        <main>
            <RouterView />
        </main>
    </div>
</template>

<style scoped lang="less">
// 定义一些常用变量
@import "@/assets/colors.less";
@main-font: "Arial", sans-serif;
@primary-color: #faf6f6; // 主要文字颜色
@secondary-color: #dfe1d9; // 次要文字颜色
@background-color: #0d1933; // 背景颜色
@button-color: #072056; // 按钮颜色
@button-hover-color: #0056b3; // 按钮悬停颜色
@feature-bg: white; // 特性区域背景颜色
@feature-text-size: 1.5rem; // 特性区域文本大小

.bg-color {
    background-color: @background-color;
    position: absolute;
    top: 0;
    left: 0;
    z-index: 1;
    width: 100%;
}

.bg-picture {
    width: 100%;
    height: 100%;
    background: url("../assets/bg.png") no-repeat center center fixed;
    background-size: cover;
    position: absolute;
    top: 0;
    left: 0;
    z-index: 2;
}

.bg-shadow {
    width: 100%;
    height: 100px;
    background: linear-gradient(
        to bottom,
        rgba(13, 25, 51, 0),
        rgba(13, 25, 51, 1)
    );
    position: absolute;
    bottom: 0;
    left: 0;
    z-index: 3;
}

.content {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    z-index: 4;
}

/* 头部样式 */
.top-bar {
    display: flex;
    align-items: center;
    height: 100px;
    background: linear-gradient(to bottom, black, rgba(0, 0, 0, 0));
    color: white;
    padding: 0 4rem;

    .title {
        font-size: 1.5rem;
        color: @primary-color;
        margin-left: 1rem;
        margin-right: auto;
    }

    .labels {
        margin-left: auto;
        margin-right: 1rem;
    }

    .steps-container {
        width: 50%;
        padding-top: 20px;
        margin: 0 120px;
        background-color: rgba(255, 255, 255, 0.3);
        border-radius: 24px;
        backdrop-filter: blur(5px);
    }
}

/deep/ .el-step__title.is-finish {
    color: @text-color4 !important;
    font-weight: bold;
}

/deep/ .el-step__title.is-process {
    color: @light !important;
    font-weight: bold;
}
</style>
