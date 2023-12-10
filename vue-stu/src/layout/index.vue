<script setup lang="ts">
import { onMounted, ref, watch, nextTick } from "vue";
import { useRoute, useRouter } from 'vue-router';

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

onMounted(updateBgHeight);

watch(() => route.path, () => {
    // 确保在 DOM 更新后再计算高度
    nextTick(updateBgHeight);
});

function backToHome() {
    router.push("/home")
}
</script>

<template>
    <div ref="bg_color" class="bg-color" />
    <div class="bg-picture" />
    <div class="bg-shadow" />
    <div ref="content" class="content">
        <header class="top-bar">
            <!-- <div class="logo">
                <img alt="Vue logo" src="./assets/logo.png" />
            </div> -->
            <p class="title">SUSTech Dormitory</p>
            <el-link @click.prevent="backToHome()">SUSTech Dormitory</el-link>
            <div class="labels">
                <p>................</p>
            </div>
        </header>
        <main>
            <RouterView />
        </main>
    </div>
</template>

<style scoped lang="less">
// 定义一些常用变量
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
    height: 1200px;
}

.bg-picture {
    width: 100%;
    height: 100vh;
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
    bottom: 0px;
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
}
</style>
