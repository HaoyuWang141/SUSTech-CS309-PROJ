<script setup>
import ProfileCard from "./components/ProfileCard.vue";
import TeamCard from "./components/TeamCard.vue";
import {onMounted, ref} from "vue";
import axiosInstance from "@/axios/axiosConfig";

const currentStage = ref(2);

async function getCurrentStage() {
  try {
    await axiosInstance
        .get("/student/dormitory/getStage", {
          params: {
            studentAccountId: localStorage.getItem("studentId"),
          },
        })
        .then((response) => {
          console.log("getCurrentStage() ->");
          console.log(response);
          currentStage.value = response.data;
        });
  } catch (error) {
    console.error(error);
  }
}

onMounted(() => {
  // getCurrentStage();
});
</script>

<template>
  <div class="container">
    <!--    <div class="steps-container">-->
    <el-steps simple :active="currentStage" class="steps-container" finish-status="success">
      <el-step title="组队阶段" :class="{ 'active-step': currentStage === 0, 'success-step': currentStage > 0 }"></el-step>
      <el-step title="收藏阶段" :class="{ 'active-step': currentStage === 1, 'success-step': currentStage > 1 }"></el-step>
      <el-step title="正选阶段" :class="{ 'active-step': currentStage === 2, 'success-step': currentStage > 2 }"></el-step>
      <el-step title="确认阶段" :class="{ 'active-step': currentStage === 3, 'success-step': currentStage > 3 }"></el-step>
    </el-steps>
    <!--    </div>-->
    <div class="card-container">
      <ProfileCard/>
    </div>
    <div class="card-container">
      <TeamCard/>
    </div>
  </div>
</template>

<style scoped lang="less">
@import "@/assets/colors.less";

.container {
  margin-top: 30px;
  padding: 0 5%;
  text-align: center;

  .steps-container {
    width: 70%;
    padding: 30px 50px;
    margin: 50px auto;
    background-color: rgba(7, 32, 86, 0.4);
    border-radius: 24px;
    backdrop-filter: blur(5px);

    :deep(.el-step__title) {
      font-weight: bold;
      font-size: 20px;
    }

    :deep(.success-step) {
      .el-step__icon {
        background-color: #67C23A;
      }
      .el-step__title {
        color: #67C23A;
      }
    }

    :deep(.active-step) {
      .el-step__icon {
        background-color: orange;
      }
      .el-step__title {
        color: orange;
      }

    }
  }

  .card-container {
    display: inline-block;
    vertical-align: top;
    margin: 0 2%;
    min-width: 600px;
  }
}
</style>