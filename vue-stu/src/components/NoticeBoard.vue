<script setup>
import { ref } from "vue";

const notices = ref([
    "这是第一条通知。",
    "这是第二条通知，很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长。",
    "3",
    "4",
])
const currentNoticeIndex = ref(0)
const currentNotice = ref(notices.value[currentNoticeIndex.value])
// function formatNotice(notice) {
//     return notice.length > 20 ? `${notice.slice(0, 20)}...` : notice;
// }
</script>

<template>
  <el-card class="notice-board-card">
    <template #header>
      <div class="notice-board-header">
        <span>通知公告</span>
      </div>
    </template>
    <div class="notice-board-body">
      <ul>
        <li v-for="(notice, index) in notices" :key="index" class="notice-item">
          {{ notice }}
        </li>
      </ul>
    </div>
  </el-card>
</template>

<style scoped>
.notice-board-card {
    border-radius: 20px;
    width: 92.5%;
    height: 92.5%;
    margin-left: 2.5%;
    margin-top: 5%;
    margin-bottom: 2.5%;
    margin-right: 5%;
}

.notice-board-header {
    text-align: center;
    font-size: 18px;
    font-weight: bold;
}

.notice-board-body {
    overflow: hidden;
    display: flex;
    padding: 0;
}

.notice-item {
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    line-height: 1.5;
}
</style>