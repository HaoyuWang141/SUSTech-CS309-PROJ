<script setup>
import {Search, ChatDotRound, Tools} from '@element-plus/icons-vue'
import router from "@/router";
</script>
<template>
  <el-card class="function-board-card">
    <template #header>
      <div class="function-board-header">
        <el-icon style="height: 100%;"><Tools /></el-icon>
        <span style="">功能</span>
      </div>
    </template>
    <template class="function-board-layout">
      <div style="grid-column: 1/4; grid-row: 1/6">
        <el-button style="width: 100%; height: 100%" @click="router.push('dormitory')">
          <el-icon :style="{fontSize: '50px'}">
            <Search/>
          </el-icon>
          <span>查看宿舍</span>
        </el-button>
      </div>
      <div style="grid-column: 4/7; grid-row: 1/6;">
        <el-button style="width: 100%; height: 100%">
          <el-icon :style="{fontSize: '50px'}">
            <ChatDotRound />
          </el-icon>
          <span>论坛</span>
        </el-button>
      </div>
    </template>
  </el-card>
</template>

<style scoped>
.function-board-card {
    border-radius: 20px;
    width: 92.5%;
    height: 92.5%;
    margin-top: 2.5%;
    margin-bottom: 5%;
    margin-left: 2.5%;
    margin-right: 5%;
}

.function-board-header {
    text-align: center;
    font-size: 18px;
    font-weight: bold;
}

.function-board-layout {
    display: grid;
    grid-gap: 20px;
    grid-template-columns: repeat(10, 1fr);
    grid-template-rows: repeat(10, 1fr);
}
</style>