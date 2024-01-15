<script setup lang="ts">
import {onMounted, ref} from "vue";
import axiosInstance from "@/axios/axiosConfig";
import {StudentAccount} from "@/types/globalTypes";

const dormmateList = ref<StudentAccount[]>([]);

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
  <el-container class="dorm-mates-container">
    <div class="text-container">
      <h1>
        推荐舍友列表
      </h1>
      <p>
        以下是根据你的个人信息推荐的舍友列表，你可以通过点击“开启聊天”或“邀请组队”来与他们取得联系。
      </p>
      <p>
        推荐舍友是根据您的空调温度、睡觉时间、起床时间、是否打鼾等信息进行推荐的，您可以在个人信息页面修改这些信息。
      </p>
    </div>
    <el-row gutter="20px" v-for="(person, index) in dormmateList">
      <el-card class="dorm-mates-card">
        <div class="dorm-mates-card-body">
          <div class="left-part">
            <el-image
                style="width: 150px"
                :src="person?.photoUrl?person.photoUrl:'src/assets/ldh.jpg'"
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
            <el-row style="margin-top: 50px">
              <el-button @click="">
                开启聊天
              </el-button>
              <el-button @click="">
                邀请组队
              </el-button>
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
@import "@/assets/colors";

.dorm-mates-container {
  padding: 10px 20%;
  padding-top: 0;
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;

  .text-container {
    padding: 20px 20px 20px 80px;
    margin-bottom: 30px;
    background-color: rgba(0, 0, 0, 0.7);
    border: None;
    border-radius: 22px;
    min-width: 600px;

    h1 {
      font-size: 40px;
      font-weight: 600;
      color: white;
      margin-bottom: 20px;
    }

    p {
      font-size: 16px;
      font-weight: 400;
      color: whitesmoke;
      margin-bottom: 20px;
    }
  }
}

.el-card {
  min-width: 600px;
}

.dorm-mates-card {
  margin: 5px auto;
  width: 80%;
  background-color: rgba(0, 0, 0, 0.7);
  border: none;
  border-radius: 22px;

  .dorm-mates-card-body {
    display: flex;
    flex-direction: row;

    .mid-part {
      margin-left: 10%;

      span {
        color: white;
        font-size: large;
      }
    }

    .right-part {
      margin-left: 10%;
      color: whitesmoke;

      .el-form-item {
        margin-bottom: 0;

      }

      :deep(.el-form-item__label) {
        color: white;
        font-size: 15px;
        font-weight: 600;
      }
    }
  }
}

.el-button {
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
  margin: 0 20px 10px 0;
}
</style>