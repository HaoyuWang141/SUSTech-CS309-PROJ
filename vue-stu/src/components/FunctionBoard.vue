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
        <el-button @click="router.push('dormitory')">
          <el-icon :style="{fontSize: '50px'}">
            <Search/>
          </el-icon>
          <span>查看宿舍</span>
        </el-button>
      </div>
    </template>
  </el-card>
</template>

<style scoped>
body {
  background-color: transparent;
}

.function-board-card {
    border-radius: 20px;
    margin-left: 5%;
    margin-top: 5%;
    margin-bottom: 2.5%;
    margin-right: 2.5%;
    background-color: rgba(0, 0, 0, 0.6);
    border-color: rgba(194, 206, 219, 0.8);
    border-width: 4px;
    border-style: solid;
    border-radius: 40px;
    color: white;
}

.function-board-header {
    text-align: center;
    font-size: 18px;
    font-weight: bold;
}

.el-button {
    background-color: rgba(194, 206, 219, 0.8);
    border: None;
    color: black;
    height: auto;
}

.function-board-layout {
    padding: 20px 20px;
    display: grid;
    grid-gap: 20px;
    grid-template-columns: repeat(10, 1fr);
    grid-template-rows: repeat(10, 1fr);
}
</style>