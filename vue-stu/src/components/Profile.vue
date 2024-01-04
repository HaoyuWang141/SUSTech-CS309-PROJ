<script setup lang="ts">
import {onMounted, reactive, ref} from "vue";
import type {CSSProperties} from "vue";
import axiosInstance from "@/axios/axiosConfig";

const student = ref({
    name: "",
    student_id: "",
    gender: 1,
    photoUrl: "string",
    college: "",
    sleep_time: "", // may be a ( DateTime )
    wake_up_time: "", // like above
    air_conditioner_temperature: 23,
    snore: false,
    qq: "",
    email: "",
    wechat: "",
    teamId: 0,
})

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
const showEditInfo = ref(false);
const editInfoForm = ref({
    studentAccountId: localStorage.getItem("studentId"),
    photoUrl: "",
    sleepTimeString: "",
    wakeUpTimeString: "",
    airConditionerTemperature: 23,
    snore: false,
    qq: "",
    email: "",
    wechat: "",
})

async function confirmEdit() {
    try {
        await axiosInstance.post(
            "student/profile/set2",
            {},
            {
                params: {
                    studentAccountId: localStorage.getItem("studentId"),
                    sleepTimeString: editInfoForm.value.sleepTimeString,
                    wakeUpTimeString: editInfoForm.value.wakeUpTimeString,
                    airConditionerTemperature: editInfoForm.value.airConditionerTemperature,
                    snore: editInfoForm.value.snore,
                    qq: editInfoForm.value.qq,
                    email: editInfoForm.value.email,
                    wechat: editInfoForm.value.wechat,
                },
            },
        ).then(response => {
            console.log("Profile confirmEdit() ->")
            console.log(response)
            showEditInfo.value = false
            getStudentInfo()
        })
    } catch (error) {
        console.log(error)
    }
}

async function getStudentInfo() {
    try {
        await axiosInstance.get(
              "admin/studentAccount/getStudent",
            {
                params: {
                    studentId: localStorage.getItem("studentId"),
                },
            },
        ).then(response => {
                student.value = response.data;
                console.log("Profile: getStudentInfo() ->");
                console.log(response);
            }
        )

    } catch (error) {
        console.log(error)
    }
}

async function createStudentInfo() {
    try {
        const response = await axiosInstance.post(
            "admin/studentAccount/create",
            [student.value]
        )
        console.log("createStudentInfo() success")
        console.log(response)
    } catch (error) {
        console.log(error)
    }
}

onMounted(() => {
    getStudentInfo()
})
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
            <span>{{ student.student_id }}</span>
          </el-form-item>
          <el-form-item label="书院">
            <span>{{ student.college }}</span>
          </el-form-item>
        </el-form>
      </div>
      <el-button @click="getStudentInfo()">get</el-button>
      <el-button @click="createStudentInfo()">create</el-button>
    </el-card>
    <el-card class="profile-right-card">
      <template #header>
        <el-row style="padding: 0">
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
          <span>{{ student.sleep_time }}</span>
        </el-form-item>
        <el-form-item label="起床时间">
          <span>{{ student.wake_up_time }}</span>
        </el-form-item>
        <el-form-item label="空调温度">
          <span>{{ student.air_conditioner_temperature + "℃" }}</span>
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
        <el-time-picker v-model="editInfoForm.sleepTimeString"
                        format="HH:mm"
                        value-format="HH:mm">
        </el-time-picker>
      </el-form-item>
      <el-form-item label="起床时间">
        <el-time-picker v-model="editInfoForm.wakeUpTimeString"
                        format="HH:mm"
                        value-format="HH:mm">
        </el-time-picker>
      </el-form-item>
      <el-form-item label="空调温度">
        <el-slider v-model="editInfoForm.airConditionerTemperature"
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
        <el-input v-model="editInfoForm.qq"
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
                 @click="confirmEdit()">
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
</style>