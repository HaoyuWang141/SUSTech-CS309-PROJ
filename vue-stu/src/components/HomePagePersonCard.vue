<script setup>
import IconCommunity from "@/components/icons/IconCommunity.vue";
import {ref} from "vue";

const name = ref("xingming")
const sid = ref("12010000")
const grade = ref("dayi")
const major = ref("CS")
</script>

<template>
  <el-card class="simple-person-card">
    <template #header>
      <div class="simple-card-header">
        <span>个人信息</span>
      </div>
    </template>
    <div class="simple-card-body">
      <div class="simple-card-left">
        <IconCommunity/>
        <el-button type="primary">
          编辑个人信息
        </el-button>
      </div>
      <div class="simple-card-right">
        <el-row>
          <span>姓名：</span>
          <span>{{ name }}</span>
        </el-row>
        <el-row>
          <span>学号：</span>
          <span>{{ sid }}</span>
        </el-row>
        <el-row>
          <span>年级：</span>
          <span>{{ grade }}</span>
        </el-row>
        <el-row>
          <span>专业：</span>
          <span>{{ major }}</span>
        </el-row>
      </div>
    </div>
  </el-card>
</template>

<style scoped>
.simple-card-header {
    text-align: center;
    font-size: 18px;
    font-weight: bold;
}

.simple-person-card {
    width: 30%;
    height: 30%;
    margin: 2%;
}

.simple-card-body {
    display: flex;
}

.simple-card-left {
    margin-right: 10%;
    display: flex;
    flex-direction: column;
}

.simple-card-right {
//display: flex;
}
</style>