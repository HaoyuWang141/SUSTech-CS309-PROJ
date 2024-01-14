<script setup lang="ts">
import {ref, onMounted} from "vue";
import axiosInstance from "@/axios/axiosConfig";
import {useRouter} from "vue-router";

const router = useRouter();

const imgSrc = ref("src/assets/ldh.jpg");
const name = ref("");
const sid = ref("");
const gender = ref(1);

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
          console.log(response.data.photo_url)
          console.log(response.data.photoUrl)
          if (response.data.photo_url !== null && response.data.photo_url !== "") {
            imgSrc.value = response.data.photo_url;
          }
          name.value = response.data.name;
          sid.value = response.data.student_id;
          gender.value = response.data.gender;
          localStorage.setItem("currentTeam", response.data.team_id);
        });
  } catch (error) {
    console.error(error);
  }
}

onMounted(() => {
  getSelfInfo();
});
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
</template>

<style scoped lang="less">
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
        margin: 10px 10px 10px 200px;
        background-color: rgba(194, 206, 219, 0.8);
        border: None;
        color: #072056;
        font-size: 16px;
        font-weight: bold;
      }
    }
  }
}
</style>
