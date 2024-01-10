<script setup lang="ts">
import {useRouter} from "vue-router";
import Feature from "./components/Feature.vue";
import {onMounted} from "vue";

interface Feature {
  title: string;
  description: string;
  icon: string;
}

const features: Feature[] = [
  {
    title: "宿舍查看",
    description: "查看宿舍详细信息，包括设施、位置和可用性。",
    icon: "view",
  },
  {
    title: "个人档案",
    description: "管理和更新个人信息和宿舍偏好设置。",
    icon: "profile",
  },
  {
    title: "舍友推荐",
    description: "基于兴趣和生活习惯推荐合适的舍友。",
    icon: "recommend",
  },
  {
    title: "宿舍选择",
    description: "大家喜闻乐见的抢宿舍环节！",
    icon: "select",
  },
  {
    title: "论坛系统",
    description: "讨论区域，用于交流和分享经验。",
    icon: "forum",
  },
];

const router = useRouter();

function navigateToLogin() {
  console.log("navigateToLogin");
  router.push("login");
}

</script>

<template>
  <div class="intro">
    <div class="title-container">
      <h1>SUSTech Dormitory</h1>
      <p>SUSTech CS309 PROJ: 南方科技大学宿舍管理系统</p>
    </div>
    <button @click="navigateToLogin">Login to preview</button>
  </div>

  <section class="features">
    <Feature
        v-for="feature in features"
        :key="feature.title"
        :title="feature.title"
        :description="feature.description"
    />
  </section>
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

/* 主要内容样式 */
main {
  // padding: 1rem 10rem 5rem 10rem;
  border-radius: 8rem;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);

  /* 介绍部分 */

  .intro {
    text-align: center;
    margin: auto;
    margin-bottom: 12rem;
    margin-top: 6rem;

    .title-container {
      max-width: 700px;
      background-color: rgba(0, 0, 0, 0.5);
      border-radius: 3rem;
      margin: auto;
      margin-bottom: 3rem;

      h1 {
        font-size: 3rem;
        color: @primary-color;
        padding: 1rem;
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
    padding: 2rem 0;
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
}
</style>
