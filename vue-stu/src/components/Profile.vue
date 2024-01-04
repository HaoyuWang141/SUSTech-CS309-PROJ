<script setup lang="ts">
import {reactive, ref} from "vue";
import type {CSSProperties} from "vue";
import axiosInstance from "@/axios/axiosConfig";

const student = ref({
    name: "姓名",
    studentId: "12010000",
    gender: 1,
    photoUrl: "string",
    college: "ShuDe",
    sleepTime: "23:59", // may be a ( DateTime )
    wakeUpTime: "7:59", // like above
    airConditionerTemperature: "23",
    acTempInt:23,
    snore: false,
    qq: "123456789",
    email: "123456789@email.com",
    wechat: "wx_id_123456789",
    teamId: 0,
    team: {
        teamId: 0,
        dormitoryId: 0,
        dormitory: {
            dormitoryId: 0,
            floor: 0,
            roomNumber: "string",
            bedCount: 0,
            description: "string",
            layoutId: 0,
            layout: {
                layoutId: 0,
                layoutName: "string",
                description: "string",
                imageUrl: "string"
            },
            isEmpty: true,
            gender: 0,
            degree: 0,
            buildingId: 0,
            building: {
                buildingId: 0,
                buildingName: "string",
                description: "string",
                regionId: 0,
                region: {
                    regionId: 0,
                    regionName: "string"
                }
            }
        }
    }
})
const testTemp = ref(26);
const showEditInfo = ref(false);

interface Mark {
    style: CSSProperties
    label: string
}

type Marks = Record<number, Mark | string>
const marks = reactive<Marks>({
    16: {
        style: {
            color: '#1968fa',
        },
        label: '16°C',
    },
    30: {
        style: {
            color: '#f54b4b',
        },
        label: '30°C',
    },
});
const isSnore = ref(false);
const editInfoForm = ref({
    sleepTime: "",
    wakeUpTime: "",
    acTemp: 26,
    snore: false,
    QQ: "",
    email: "",
    wechat: "",
})

// async function confirmEdit() {
//     try{
//         const response = await axiosInstance.
//     } catch (error) {
//         console.log(error)
//     }
// }

async function getStudentInfo() {

}

async function createStudentInfo() {
    try{
        const response = await axiosInstance.post(
            "admin/studentAccount/create",
            [student.value]
        )
        console.log("createStudentInfo() success")
    }catch (error) {
        console.log(error)
    }
}
</script>

<template>
  <div style="height: 80vh; display: flex; flex-direction: row">
    <el-card class="profile-left-card">
      <template #header>
        学生基本信息
      </template>
      <div>
        <el-form label-position="left" label-width="60px">
          <el-form-item label="姓名">

            <span>{{ student.name }}</span>

          </el-form-item>
          <el-form-item label="性别">
            <span>{{ student.gender === 1 ? "男" : "女" }}</span>
          </el-form-item>
          <el-form-item label="学号">
            <span>{{ student.studentId }}</span>
          </el-form-item>
          <el-form-item label="书院">
            <span>{{ student.college }}</span>
          </el-form-item>
        </el-form>
      </div>
      <el-button @click="createStudentInfo()">ceshi</el-button>
    </el-card>
    <el-card class="profile-right-card">
      <template #header>
        <el-row>
          <el-col :span="12">
            <div>附加信息</div>
          </el-col>
          <el-col :span="12">
            <div style="width: 100%; display: flex; flex-direction: row-reverse;">
              <el-button round type="primary" @click="showEditInfo = true">
                编辑信息
              </el-button>
            </div>
          </el-col>
        </el-row>
      </template>
      <el-form label-position="left" label-width="80px">
        <el-form-item label="睡觉时间">
          <span>{{ student.sleepTime }}</span>
        </el-form-item>
        <el-form-item label="起床时间">
          <span>{{ student.wakeUpTime }}</span>
        </el-form-item>
        <el-form-item label="空调温度">
          <span>{{ student.airConditionerTemperature + "℃" }}</span>
        </el-form-item>
        <el-form-item label="是否打鼾">
          <span>{{ student.snore === true ? "是" : "否" }}</span>
        </el-form-item>
        <el-form-item label="邮箱">
          <span>{{ student.email }}</span>
        </el-form-item>
        <el-form-item label="QQ">
          <span>{{ student.qq }}</span>
        </el-form-item>
        <el-form-item label="微信">
          <span>{{ student.wechat }}</span>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
  <el-drawer v-model="showEditInfo"
             size="60%"
             :with-header="false"
             :direction="'rtl'">
    <div>
      <h1>编辑附加信息</h1>
    </div>

    <el-form style="margin: 20px; max-width: 400px" label-width="80px" label-position="left">
      <el-form-item label="睡觉时间">
        <el-time-picker v-model="editInfoForm.sleepTime"
                        format="HH:mm">
        </el-time-picker>
      </el-form-item>
      <el-form-item label="起床时间">
        <el-time-picker v-model="editInfoForm.wakeUpTime"
                        format="HH:mm">
        </el-time-picker>
      </el-form-item>
      <el-form-item label="空调温度">
        <el-slider v-model="editInfoForm.acTemp"
                   style="min-width: 250px"
                   :marks="marks"
                   :min="16"
                   :max="30"
        />
      </el-form-item>
      <el-form-item label="是否打鼾">
        <el-switch v-model="editInfoForm.snore"
                   inline-prompt
                   active-text="是"
                   inactive-text="否"
                   style="--el-switch-on-color: #ff4949; --el-switch-off-color: #13ce66"
        />
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="editInfoForm.email"
                  placeholder="输入邮箱"
                  clearable>
        </el-input>
      </el-form-item>
      <el-form-item label="QQ">
        <el-input v-model="editInfoForm.QQ"
                  placeholder="输入QQ"
                  clearable>
        </el-input>
      </el-form-item>
      <el-form-item label="微信">
        <el-input v-model="editInfoForm.wechat"
                  placeholder="输入微信"
                  clearable/>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button round
                 type="primary"
                 @click="">
        确认修改
      </el-button>
    </template>
  </el-drawer>
</template>

<style scoped>
.profile-left-card {
    width: 30%;
    margin-left: 5%;
    height: 100%;
}

.profile-right-card {
    width: 50%;
    margin-left: 5%;
    height: 100%;
}

.grid-content {
    border-radius: 4px;
    min-height: 30px;
}

.ep-bg-purple {
    background-color: #13ce66;
}
</style>