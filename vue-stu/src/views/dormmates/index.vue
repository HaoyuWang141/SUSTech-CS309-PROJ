<script setup lang="ts">
import {onMounted, ref} from "vue";
import axiosInstance from "@/axios/axiosConfig";

interface PERSON {
  air_conditioner_temperature: number,
  email: string,
  gender: number,
  name: "CYH",
  photo_url: string,
  qq: string,
  sleep_time: string,
  snore: boolean,
  student_id: string,
  wake_up_time: string,
  wechat: string
}

const dormmateList = ref<PERSON[]>([]);

async function getDormmates() {
  try {
    await axiosInstance.get(
        "/student/team/getRecommendation",
        {
          params: {
            studentAccountId: localStorage.getItem("studentId")
          },
        },
    ).then(response => {
      dormmateList.value = response.data;
      console.log("Dormmates getDormmates() ->")
      console.log(response.data)
      //     dormmateList.value.push({air_conditioner_temperature:23,email: "123",gender: 1,name: "CYH",qq: "123",sleep_time: "07:17:00",snore: false,student_id: "12012826",wake_up_time: "09:17:00",wechat: "123"});
      //     dormmateList.value.push({air_conditioner_temperature:23,email: "123",gender: 1,name: "CYH",qq: "123",sleep_time: "07:17:00",snore: false,student_id: "12012826",wake_up_time: "09:17:00",wechat: "123"});
      //     dormmateList.value.push({air_conditioner_temperature:23,email: "123",gender: 1,name: "CYH",qq: "123",sleep_time: "07:17:00",snore: false,student_id: "12012826",wake_up_time: "09:17:00",wechat: "123"});
      //     dormmateList.value.push({air_conditioner_temperature:23,email: "123",gender: 1,name: "CYH",qq: "123",sleep_time: "07:17:00",snore: false,student_id: "12012826",wake_up_time: "09:17:00",wechat: "123"});
    })
  } catch (error) {
    console.log(error)
  }
}

onMounted(() => {
  getDormmates();
})
</script>

<template>
  <h1>
    推荐舍友列表
  </h1>
  <el-container class="dorm-mates-container">
    <el-row gutter="20px" v-for="(person, index) in dormmateList">
      <el-card class="dorm-mates-card">
        <div class="dorm-mates-card-body">
          <div class="left-part">
            <el-image
                style="width: 150px"
                :src="'src/assets/Kobe.jpg'"
            />
          </div>
          <div class="mid-part">
            <el-row style="margin-bottom: 10px">
              <span>姓名：</span>
              <span>{{ person.name }}</span>
            </el-row>
            <el-row style="margin-bottom: 10px">
              <span>学号：</span>
              <span>{{ person.student_id }}</span>
            </el-row>
            <el-row style="margin-bottom: 10px">
              <span>性别：</span>
              <span>{{ person.gender === 1 ? "男" : "女" }}</span>
            </el-row>
          </div>
          <div class="right-part">
            <el-form label-position="left" label-width="80px">
              <el-form-item label="睡觉时间">
                <span>{{ person.sleep_time }}</span>
              </el-form-item>
              <el-form-item label="起床时间">
                <span>{{ person.wake_up_time }}</span>
              </el-form-item>
              <el-form-item label="空调温度">
                <span>{{ person.air_conditioner_temperature + "℃" }}</span>
              </el-form-item>
              <el-form-item label="是否打鼾">
                <span>{{ person.snore === true ? "是" : "否" }}</span>
              </el-form-item>
              <el-form-item label="邮箱">
                <span>{{ person.email }}</span>
              </el-form-item>
              <el-form-item label="QQ">
                <span>{{ person.qq }}</span>
              </el-form-item>
              <el-form-item label="微信">
                <span>{{ person.wechat }}</span>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </el-card>
    </el-row>
  </el-container>

</template>

<style scoped lang="less">
.dorm-mates-container {
  padding-left: 20%;
  padding-right: 20%;
  padding-top: 0;
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
}

.dorm-mates-card {
  width: 100%;
  margin-bottom: 5%;

  .dorm-mates-card-body {
    display: flex;
    flex-direction: row;

    .mid-part {
      margin-left: 10%;
    }

    .right-part {
      margin-left: 10%;
    }
  }
}
</style>