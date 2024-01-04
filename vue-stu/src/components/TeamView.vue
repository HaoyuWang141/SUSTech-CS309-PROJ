<script setup lang="ts">
import {onMounted, reactive, ref} from "vue";
import axiosInstance from "@/axios/axiosConfig";
import {HomeFilled} from "@element-plus/icons-vue"

const teammates = ref([]);
const invitationList = ref([]);
const inviteDialogVisible = ref(false);
const invitationListVisible = ref(false);
const inviteForm = reactive({
    studentID: "",
});

function removeTeammate(index: number) {
    teammates.value.splice(index, 1)
    //TODO
}

async function invite() {
    try {
        await axiosInstance.post(
            "/student/team/invite2",
            {},
            {
                params: {
                    inviterId: localStorage.getItem("studentId"),
                    inviteeId: inviteForm.studentID,
                    token: "tokentokentoken",
                }
            },
        ).then(response => {
            console.log("invite() ->")
            console.log(response)
        })
    } catch (error) {
        console.error(error);
    }
}

async function acceptInvitation(index:number) {
    try{

    }catch (error) {
        console.log(error)
    }
}
async function updateTeamMembers() {
    try {
        await axiosInstance.get(
            "student/team/getTeam2",
            {
                params: {
                    studentAccountId: localStorage.getItem("studentId"),
                },
            }
        ).then(
            response => {
                console.log("updateTeamMembers() ->")
                console.log(response)
            }
        )
    } catch (error) {
        console.log(error)
    }
}

async function getInvitations() {
    try {
        await axiosInstance.get(
            "student/team/getInvitations2",
            {
                params: {
                    studentAccountId: localStorage.getItem("studentId"),
                },
            }
        ).then(response => {
            console.log("getInvitations() ->")
            console.log(response)
        })
    } catch (error) {
        console.log(error)
    }
}

onMounted(() => {

})
</script>

<template>
  <!--  invisible part start-->
  <el-dialog v-model="inviteDialogVisible" title="邀请" style="width: 30%; min-width: 300px">
    <el-form :model="inviteForm">
      <el-form-item label="被邀请者学号">
        <el-input v-model="inviteForm.studentID" autocomplete="off" style="width: 80%; min-width: 100px"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="inviteDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="invite()">
          Confirm
        </el-button>
      </span>
    </template>
  </el-dialog>
  <el-dialog title="被邀请记录"
             style="width: 50%; min-width: 300px">
    <el-table style="width: 100%"
              :data="invitationList">
      <el-table-column prop="name" label="姓名"></el-table-column>
      <el-table-column prop="student_id" label="学号"></el-table-column>
      <el-table-column fixed="right" label="操作">
        <template #default="scope">
          <el-button type="primary" @click="">
            接受
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-dialog>
  <!--  invisible part end-->
  <el-card class="team-view-card">
    <template #header>
      <div class="team-view-header">
        <el-icon>
          <HomeFilled/>
        </el-icon>
        <span>组队信息</span>
      </div>
    </template>
    <div class="team-view-body">
      <div style="display: flex; flex-direction: row">
        <el-button type="primary" @click="inviteDialogVisible = true">
          邀请
        </el-button>
        <el-button type="primary">
          查看收藏
        </el-button>
        <el-button type="primary" @click="getInvitations()">
          查看邀请
        </el-button>
      </div>
      <div>
        <!--        this div is for displaying teammates-->
        <el-table :data="teammates" style="width: 100%">
          <el-table-column prop="name" label="姓名"/>
          <el-table-column prop="sid" label="学号"/>
          <el-table-column fixed="right" label="操作">
            <template #default="scope">
              <el-popconfirm
                  width="250px"
                  confirm-button-text="Yes"
                  cancel-button-text="No"
                  title="Are you sure to remove him/her?"
                  @confirm="removeTeammate(scope.$index)"
                  @cancel=""
              >
                <template #reference>
                  <el-button
                      type="danger"
                      size="small">
                    移除
                  </el-button>
                </template>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </el-card>
</template>

<style scoped>
.team-view-header {
    text-align: center;
    font-size: 18px;
    font-weight: bold;
}

.team-view-card {
    border-radius: 20px;
    width: 92.5%;
    height: 92.5%;
    margin-top: 2.5%;
    margin-bottom: 5%;
    margin-left: 5%;
    margin-right: 2.5%;
}
</style>