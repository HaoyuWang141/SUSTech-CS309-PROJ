<script setup>
import {onMounted, ref} from "vue";
import axiosInstance from "@/axios/axiosConfig";
import {Notification} from "@element-plus/icons-vue"

const notices = ref([
])
// const currentNoticeIndex = ref(0)
// const currentNotice = ref(notices.value[currentNoticeIndex.value])

async function getNotifications() {
    try {
        const response = await axiosInstance.get(
            "/student/notification/get2",
            {
                params: {
                    studentAccountId: localStorage.getItem("studentId"),
                },
            }
        ).then(response => {
            console.log("getNotification() ->")
            console.log(response)
            notices.value = response.data
        })
    } catch (error) {
        console.error(error);
    }
}

onMounted(() => {
    getNotifications();
});
</script>

<template>
  <el-card class="notice-board-card">
    <template #header>
      <div class="notice-board-header">
        <el-icon><Notification /></el-icon>
        <span>通知公告</span>
      </div>
    </template>
    <div class="notice-board-body">
      <ul>
        <li v-for="(notice, index) in notices" :key="index" class="notice-item">
          {{ notice.content }}
        </li>
      </ul>
    </div>
  </el-card>
</template>

<style scoped>
.notice-board-card {
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
    min-height: 300px;
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