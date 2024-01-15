<script setup lang="ts">
import {ref, onMounted} from "vue";
import axiosInstance from "@/axios/axiosConfig";
import {useRouter} from "vue-router";
import {ElMessage} from "element-plus";
import {Dormitory} from "@/types/globalTypes";

const router = useRouter();

const imgSrc = ref("src/assets/ldh.jpg");
const name = ref("");
const sid = ref("");
const gender = ref(1);
const visible = ref(false);
const dorm = ref<Dormitory>();

function goProfile() {
  router.push("profile");
}

async function getSelfInfo() {
  try {
    console.log("Current student: " + localStorage.getItem("studentId"));
    await axiosInstance
        .get("/student/team/getStudent", {
          params: {
            studentId: localStorage.getItem("studentId"),
          },
        })
        .then((response) => {
          console.log("getSelfInfo() -> ");
          console.log(response);
          if (response.data.photo_url !== null && response.data.photo_url !== "") {
            imgSrc.value = response.data.photo_url;
          }
          name.value = response.data.name;
          sid.value = response.data.student_id;
          gender.value = response.data.gender;
          dorm.value = response.data.team.dormitory;
          localStorage.setItem("teamId", response.data.team_id);
        });
  } catch (error) {
    console.error(error);
  }
}

onMounted(() => {
  getSelfInfo();
});

function viewDorm() {
  if (dorm.value !== null) {
    visible.value = true;
  } else {
    ElMessage.warning("没有选择宿舍")
  }
}
</script>

<template>
  <el-card class="simple-person-card">
    <template #header>
      <div class="simple-card-header">
        <span> 个人信息 </span>
      </div>
    </template>
    <div class="simple-card-body">
      <div class="simple-card-left">
        <el-image
            :src="imgSrc"
        />
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
          <span>性别：</span>
          <span>{{ gender === 1 ? "男" : "女" }}</span>
        </el-row>
        <el-row>
          <el-button type="primary" @click="viewDorm">
            查看已选宿舍
          </el-button>
          <el-button type="primary" @click="goProfile">
            编辑个人信息
          </el-button>
        </el-row>
      </div>
    </div>
  </el-card>

  <el-dialog title="已选宿舍" v-model="visible">
    <div class="image-container">
      <el-image
          :src="dorm?.layout?.image_url"
          :zoom-rate="1.5"
          :max-scale="7"
          :min-scale="0.2"
          :initial-index="1"
          fit="cover"
          alt="Card image"
      >
      </el-image>
    </div>
    <span class="title">{{ dorm?.layout?.layout_name }}</span>
    <div class="text-item">
      <span>区域：{{ dorm?.building?.region.region_name }}</span>
      <span>楼栋：{{ dorm?.building?.building_name }}</span>
      <span>房号：{{ dorm?.room_number }}</span>
      <span>床数：{{ dorm?.bed_count }}</span>
      <span>简介：{{ dorm?.layout?.description }}</span>
    </div>
  </el-dialog>
</template>

<style scoped lang="less">
@import "@/assets/colors";

.simple-person-card {
  background-color: rgba(0, 0, 0, 0.65);
  border-color: rgba(194, 206, 219, 0.8);
  border-width: 4px;
  border-style: solid;
  border-radius: 40px;
  color: white;

  .simple-card-header {
    text-align: center;
    font-size: 18px;
    font-weight: bold;
    color: white;
  }

  .simple-card-body {
    display: flex;

    .simple-card-left {
      max-width: 150px;
    }

    .simple-card-right {
      display: flex;
      flex-direction: column;
      align-items: self-start;
      margin-top: 10px;
      margin-left: 50px;
      font-size: large;

      .el-button {
        margin: 10px 10px 10px 10px;
        background-color: rgba(194, 206, 219, 0.8);
        border: None;
        color: #072056;
        font-size: 16px;
        font-weight: bold;
      }
    }
  }
}

.el-dialog {
  display: flex;
  flex-direction: column;
  align-items: flex-start; // 确保子元素从容器的左边开始

  .image-container {
    display: flex;
    justify-content: center; // 水平居中
    align-items: center; // 垂直居中
    margin-bottom: 15px; // 提供一些间距

    .el-image {
      max-width: 100%; // 限制最大宽度，以适应容器
      // 高度将自动调整以保持纵横比
    }
  }

  .title {
    text-align: center; // 文本居中
    color: @text-color-blue; // 文本颜色
    font-size: x-large; // 字体大小
    font-weight: bold; // 字体加粗
  }

  span {
    display: block;
    margin-bottom: 10px; // 标题下的间距
    font-size: large;
    font-weight: bold;
  }

  .text-item {
    padding: 0 10%; // 左右留白
    font-size: large;
  }

  .el-button {
    border: None;
    background-color: @button-color;

    &:hover {
      background-color: darken(@button-color, 5%);
    }

    display: block;

    margin-top: 20px;
    padding: 10px 30px;
    margin-left: auto;
  }
}
</style>
