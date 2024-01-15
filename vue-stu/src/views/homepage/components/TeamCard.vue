<script setup lang="ts">
import {onMounted, reactive, ref} from "vue";
import axiosInstance from "@/axios/axiosConfig";
import {ElMessage} from "element-plus";
import {Dormitory} from "@/types/globalTypes";
import LayoutCard from "@/components/layout_card.vue";
import router from "@/router";

const teammates = ref([]);
const invitationList = ref([]);
const inviteDialogVisible = ref(false);
const invitationListVisible = ref(false);
const favorListVisible = ref(false);
const inviteForm = reactive({
  studentID: "",
});
function goDormmates() {
    router.push("/dormmates");
}
async function invite() {
  try {
    await axiosInstance
        .post(
            "/student/team/invite2",
            {},
            {
              params: {
                inviterId: localStorage.getItem("studentId"),
                inviteeId: inviteForm.studentID,
                token: "tokentokentoken",
              },
            }
        )
        .then((response) => {
          console.log("invite() ->");
          console.log(response);
          inviteDialogVisible.value = false;
          ElMessage({
            message: "邀请成功",
            type: "success",
          });
        });
  } catch (error) {
    console.error(error);
  }
}

async function acceptInvitation(index: number) {
  try {
    await axiosInstance
        .post(
            "/student/team/accept2",
            {},
            {
              params: {
                invitationId: invitationList.value[index]["id"],
              },
            }
        )
        .then((response) => {
          console.log("TeamView: acceptInvitation() ->");
          console.log(response);
          invitationListVisible.value = false;
          invitationList.value = [];
          updateTeamMembers();
        });
  } catch (error) {
    console.log(error);
  }
}

async function updateTeamMembers() {
  try {
    await axiosInstance
        .get("/student/team/getTeam2", {
          params: {
            studentAccountId: localStorage.getItem("studentId"),
          },
        })
        .then((response) => {
          console.log("updateTeamMembers() ->");
          console.log(response);
          teammates.value = response.data;
        });
  } catch (error) {
    console.log(error);
  }
}

async function getInvitations() {
  try {
    await axiosInstance
        .get("student/team/getInvitations2", {
          params: {
            studentAccountId: localStorage.getItem("studentId"),
          },
        })
        .then((response) => {
          console.log("getInvitations() ->");
          console.log(response);
          invitationList.value = response.data;
        });
  } catch (error) {
    console.log(error);
  }
}

async function quitTeam() {
  try {
    await axiosInstance
        .post(
            "/student/team/quitTeam2",
            {},
            {
              params: {
                studentAccountId: localStorage.getItem("studentId"),
              },
            }
        )
        .then((response) => {
          console.log("TeamView quitTeam() ->");
          console.log(response);
          updateTeamMembers();
        });
  } catch (error) {
    console.log(error);
  }
}

const favorList = ref<Dormitory[]>([]);

async function getFavorList() {
  favorListVisible.value = true;
  try {
    console.log(localStorage.getItem("currentTeam"));
    await axiosInstance
        .get("student/dormitory/getFavoriteList", {
          params: {
            teamId: localStorage.getItem("currentTeam"),
          },
        })
        .then((response) => {
          console.log("TeamView getFavorList() ->");
          console.log(response);
          favorList.value = response.data;
          ElMessage.success("获取收藏列表成功");
        });
  } catch (error) {
    console.log(error);
    ElMessage.error("获取收藏列表失败");
  }
}

onMounted(() => {
  updateTeamMembers();
  getInvitations();
});
</script>

<template>
  <el-card class="team-view-card">
    <template #header>
      <div class="team-view-header">
        <span>组队信息</span>
      </div>
    </template>
    <div class="team-view-body">
      <div style="display: flex; flex-direction: column; width: 20%">
        <el-button
            type="primary"
            style="width: 80%; margin: 10px 0 0 12px"
            @click="goDormmates()"
        >
          推荐舍友
        </el-button>
        <el-button
            type="primary"
            style="width: 80%; margin: 10px 0 0 12px"
            @click="inviteDialogVisible = true"
        >
          邀请
        </el-button>
        <el-button
            type="primary"
            style="width: 80%; margin: 10px 0 0 12px"
            @click="getFavorList"
        >
          查看收藏
        </el-button>
        <el-button
            type="primary"
            style="width: 80%; margin: 10px 0 0 12px"
            @click="invitationListVisible = true"
        >
          查看邀请
        </el-button>
        <el-button
            type="primary"
            style="width: 80%; margin: 10px 0 0 12px"
            @click="quitTeam()"
        >
          退出队伍
        </el-button>
      </div>
      <div class="team-container">
        <el-table :data="teammates" class="custom-table">
          <el-table-column prop="name" label="姓名"/>
          <el-table-column prop="student_id" label="学号"/>
        </el-table>
      </div>
    </div>
  </el-card>

  <!--  invisible part start-->
  <el-dialog
      v-model="inviteDialogVisible"
      title="邀请"
      style="width: 30%; min-width: 300px"
  >
    <el-form :model="inviteForm">
      <el-form-item label="被邀请者学号">
        <el-input
            v-model="inviteForm.studentID"
            autocomplete="off"
            style="width: 80%; min-width: 100px"
        />
      </el-form-item>
    </el-form>
    <template #footer>
            <span class="dialog-footer">
                <el-button @click="inviteDialogVisible = false"
                >Cancel</el-button
                >
                <el-button type="primary" @click="invite()">
                    Confirm
                </el-button>
            </span>
    </template>
  </el-dialog>
  <el-dialog
      v-model="invitationListVisible"
      title="被邀请记录"
      style="width: 50%; min-width: 300px"
  >
    <el-table style="width: 100%" :data="invitationList">
      <el-table-column prop="inviter_id" label="学号"></el-table-column>
      <el-table-column fixed="right" label="操作">
        <template #default="scope">
          <el-button
              type="primary"
              @click="acceptInvitation(scope.$index)"
          >
            接受
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-dialog>

  <el-dialog v-model="favorListVisible" title="收藏列表">
    <el-row :gutter="20">
      <el-col :span="8" v-for="dorm in favorList" :key="dorm.dormitory_id">
        <LayoutCard :dormitory="dorm"/>
      </el-col>
    </el-row>
  </el-dialog>
  <!--  invisible part end-->
</template>

<style scoped>
.team-view-header {
  text-align: center;
  font-size: 18px;
  font-weight: bold;
}

.team-view-card {
  background-color: rgba(0, 0, 0, 0.6);
  border-color: rgba(194, 206, 219, 0.8);
  border-width: 4px;
  border-style: solid;
  border-radius: 40px;
  color: white;
}

.team-container {
  margin-left: 5%;
  width: 70%;
  height: 100%;
  min-height: 200px;
}

.team-view-body {
  display: flex;
  flex-direction: row;
}

.custom-table {
  width: 100%;
  margin: 10px 0 0 12px;
  min-height: 200px;
  border-radius: 22px;
}

.el-button {
  background-color: rgba(194, 206, 219, 0.8);
  border: None;
  color: black;
}

</style>
