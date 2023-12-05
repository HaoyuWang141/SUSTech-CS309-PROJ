<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import Feature from '../components/Feature.vue';

interface Feature {
    title: string;
    description: string;
    icon: string;
}

const features: Feature[] = [
    {
        title: '宿舍查看',
        description: '查看宿舍详细信息，包括设施、位置和可用性。',
        icon: 'view',
    }, {
        title: '个人档案',
        description: '管理和更新个人信息和宿舍偏好设置。',
        icon: 'profile',
    }, {
        title: '舍友推荐',
        description: '基于兴趣和生活习惯推荐合适的舍友。',
        icon: 'recommend',
    },
    {
        title: '宿舍选择',
        description: '大家喜闻乐见的抢宿舍环节！',
        icon: 'select',
    },
    {
        title: '论坛系统',
        description: '讨论区域，用于交流和分享经验。',
        icon: 'forum',
    },
];

const bg_color = ref<HTMLElement | null>(null);
const content = ref<HTMLElement | null>(null);

onMounted(() => {
    if (content.value) {
        const contentHeight = content.value.clientHeight;
        if (bg_color.value) {
            bg_color.value.style.height = `${contentHeight}px`;
        }
    }
});

const router = useRouter();
function navigateToLogin() {
    console.log('navigateToLogin');
    router.push('/login');
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
            <div class="labels">
                <p>................</p>
            </div>
        </header>

        <main>
            <section class="intro">
                <div class="title-container">
                    <h1>SUSTech Dormitory</h1>
                    <p>
                        SUSTech CS309 PROJ: 南方科技大学宿舍管理系统
                    </p>
                </div>
                <button @click="navigateToLogin">Login to preview</button>
            </section>

            <section class="features">
                <Feature v-for="feature in features" :key="feature.title" :title="feature.title"
                    :description="feature.description" :icon="feature.icon" />
            </section>

            <!-- <section>
                <h2>Section 2</h2>
                <p>Section 2 content</p>
            </section> -->
        </main>
    </div>
</template>

<style scoped lang="less">
// 定义一些常用变量
@main-font: 'Arial', sans-serif;
@primary-color: #faf6f6; // 主要文字颜色
@secondary-color: #dfe1d9; // 次要文字颜色
@background-color: #0D1933; // 背景颜色
@button-color: #072056; // 按钮颜色
@button-hover-color: #0056b3; // 按钮悬停颜色
@feature-bg: white; // 特性区域背景颜色
@feature-text-size: 1.5rem; // 特性区域文本大小

//#93d5dc

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
    background: url('../assets/bg.png') no-repeat center center fixed;
    background-size: cover;
    position: absolute;
    top: 0;
    left: 0;
    z-index: 2;
}

.bg-shadow {
    width: 100%;
    height: 100px;
    background: linear-gradient(to bottom, rgba(13, 25, 51, 0), rgba(13, 25, 51, 1));
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
    z-index: 3;
}

/* 头部样式 */
.top-bar {
    display: flex;
    align-items: center;
    height: 100px;
    background: linear-gradient(to bottom, black, rgba(0, 0, 0, 0));
    color: white;
    padding: 0 4rem;

    // .logo {
    //     width: 50px;
    //     height: 50px;
    //     border-radius: 50%;
    //     overflow: hidden;
    //     box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    //     margin-right: 2rem;

    //     img {
    //         width: 100%;
    //         height: 100%;
    //         object-fit: cover;
    //     }
    // }

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

/* 主要内容样式 */
main {
    padding: 1rem 10rem 5rem 10rem;
    border-radius: 8rem;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

/* 介绍部分 */
.intro {
    text-align: center;
    margin-bottom: 12rem;

    .title-container {
        padding-top: 1rem;
        background-color: rgba(0, 0, 0, 0.3);
        border-radius: 3rem;
        width: 50%;
        margin: auto;
        margin-bottom: 3rem;

        h1 {
            font-size: 3rem;
            color: @primary-color;
            margin-bottom: 1rem;
            font-weight: 700;
        }

        p {
            color: @secondary-color;
            font-size: 1.2rem;
            padding-bottom: 3rem;
        }

    }

    button {
        padding: 1rem 1.5rem;
        font-size: 1rem;
        font-weight: 700;
        color: white;
        background-color: @button-color;
        border: none;
        border-radius: 1rem;
        cursor: pointer;
        transition: background-color 0.5s;

        &:hover {
            background-color: @button-hover-color;
        }
    }
}

/* 特性部分 */
.features {
    display: grid; // 使用网格布局
    grid-template-columns: repeat(2, 1fr); // 两列, 每列占据一份可用空间
    gap: 5rem; // 网格间隙
    margin-bottom: 2rem;
}

/* 其他部分 */
section {
    margin-bottom: 2rem;

    h2 {
        font-size: 1.8rem;
        color: @primary-color;
        margin-bottom: 0.5rem;
    }

    p {
        color: @secondary-color;
        font-size: 1rem;
    }
}
</style>
