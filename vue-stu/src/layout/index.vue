<template>
  <div ref="bg_color" class="bg-color"/>
  <div class="bg-picture"/>
  <div class="bg-shadow"/>
  <div ref="content" class="content">
    <header class="top-bar">
      <!--      <el-row style="width: 100%">-->
      <!--        <el-col :span="6">-->
      <el-button
          @click="router.push('/welcome');"
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

      <div class="check-dorm" style="margin-left: auto; margin-right: 30px" v-show="studentId">
        <el-button
            type="primary"
            @click="router.push('/map')"
            class="button-style">
          <el-icon style="margin-right: 10px">
            <HomeFilled/>
          </el-icon>
          查看宿舍
        </el-button>
      </div>
      <div class="check-notify" style="margin-right: 30px" v-show="studentId">
        <el-button
            type="primary"
            @click="showNoticeBoard = true"
            class="button-style">
          <el-icon style="margin-right: 10px">
            <Comment/>
          </el-icon>
          系统通知
        </el-button>
      </div>
      <el-dialog v-model="showNoticeBoard"
                 style="
                 backdrop-filter: blur(3px);
                 background-color: rgba(0,0,0,0.6);
                 border-radius: 30px;"
      >
        <NoticeBoard/>
      </el-dialog>
      <el-dropdown v-show="studentId" hide-timeout="250">
        <el-button class="button-style">
          <el-icon>
            <UserFilled/>
          </el-icon>
          <span style="margin: 0 10px">{{ studentId }}</span>
          <el-icon class="el-icon--right">
            <arrow-down/>
          </el-icon>
        </el-button>

        <template #dropdown>
          <el-dropdown-menu >
            <el-dropdown-item @click="() => {router.push('/home')}" style="font-weight: bold;">个人主页</el-dropdown-item>
            <el-dropdown-item @click="() => {router.push('/chat')}" style="font-weight: bold;">聊天室</el-dropdown-item>
            <el-dropdown-item @click="logout" style="color: darkred; font-weight: bold;">登出</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </header>
    <el-scrollbar style="height: 600px;" class="my-scroll-bar">
      <main>
        <RouterView/>
      </main>
    </el-scrollbar>
  </div>
</template>

<script setup lang="ts">
import {onMounted, ref, computed} from "vue";
import {useRouter} from "vue-router";
import axiosInstance from "@/axios/axiosConfig";
import {useAuthStore} from "@/stores/auth";
import NoticeBoard from "@/layout/components/NoticeBoard.vue";

const router = useRouter();
const authStore = useAuthStore();
const showNoticeBoard = ref(false);
const studentId = computed(() => authStore.studentId);

const bg_color = ref<HTMLElement | null>(null);
const content = ref<HTMLElement | null>(null);

function updateBgHeight() {
  if (content.value) {
    const contentHeight = content.value.clientHeight;
    if (bg_color.value) {
      bg_color.value.style.height = `${contentHeight}px`;
    }
  }
}

function logout() {
  authStore.logout();
  router.push("/welcome");
}

onMounted(() => {
  authStore.studentId = localStorage.getItem('studentId') || "";
  updateBgHeight();
  if (content.value) {
    const observer = new MutationObserver((mutations) => {
      for (let mutation of mutations) {
        if (mutation.type === "childList") {
          updateBgHeight();
        }
      }
    });

    observer.observe(content.value, {childList: true, subtree: true});
  }
});
</script>

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
  background: url("../assets/bg.png") no-repeat   fixed;
  background-size: cover;
  position: absolute;
  top: 0;
  left: 0;
  z-index: 2;
}

.bg-shadow {
  width: 100%;
  height: 100px;
  background: linear-gradient(to bottom,
  rgba(13, 25, 51, 0),
  rgba(13, 25, 51, 1));
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
  //height: 20%;
  background: line
  ar-gradient(to bottom, black, rgba(0, 0, 0, 0));
  color: white;
  padding: 0 4rem;

  .title {
    font-size: 1.5rem;
    color: @primary-color;
    margin-left: 1rem;
    margin-right: auto;
  }
}

.button-style {
  background-color: @button-color;
  display: flex;
  align-items: center;
  font-size: 15px;
  font-weight: bold;
  color: whitesmoke;
  border: 1px solid rgba(34, 204, 228, 0.2);
  padding: 20px;
  border-radius: 12px;
  transition: background-color 0.5s;
  box-shadow: rgba(34, 204, 228, 0.3) 5px 5px 10px;
}

/deep/ .my-scroll-bar {
  .el-scrollbar__thumb {
    background-color: rgba(34, 204, 228, 1);
  }
}
</style>
