<script setup>
import {ref} from "vue";
import {useRouter} from "vue-router";

const router = useRouter();
const name = ref("xingming");
const sid = ref("12010000");
const grade = ref("dayi");
const major = ref("CS");

function goProfile() {
    router.push("profile");
}
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
        <img src="@/assets/Kobe.jpg" alt="DaTouZhao" class="head-pic">
      </div>
      <div class="simple-card-right">
        <el-row style="margin-bottom: 10px">
          <span>姓名：</span>
          <span>{{ name }}</span>
        </el-row>
        <el-row style="margin-bottom: 10px">
          <span>学号：</span>
          <span>{{ sid }}</span>
        </el-row>
        <el-row style="margin-bottom: 10px">
          <span>年级：</span>
          <span>{{ grade }}</span>
        </el-row>
        <el-row style="margin-bottom: 10px">
          <span>专业：</span>
          <span>{{ major }}</span>
        </el-row>
        <el-row>
          <el-button type="primary" @click="goProfile">
            编辑个人信息
          </el-button>
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
    border-radius: 20px;
    width: 92.5%;
    height: 92.5%;
    margin-left: 5%;
    margin-top: 5%;
    margin-bottom: 2.5%;
    margin-right: 2.5%;
}

.simple-card-body {
    display: flex;
}

.simple-card-left {
    margin-right: 10%;
    //display: flex;
    //flex-direction: column;
    width: 20%;
}

.simple-card-right {
    display: flex;
    flex-direction: column;
    align-items: self-start;
}

.head-pic {
    width: 120px;
    height: 160px;
    object-fit: cover;
}
</style>