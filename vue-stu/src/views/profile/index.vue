<script setup lang="ts">
import {onMounted, reactive, ref} from "vue";
import type {CSSProperties} from "vue";
import axiosInstance from "@/axios/axiosConfig";
import {StudentAccount} from "@/types/globalTypes";

const student = ref<StudentAccount>()

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
        "/student/team/getStudent", {
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

onMounted(() => {
  getStudentInfo()
})
</script>

<template>
  <div style="height:75vh; display: flex; flex-direction: row; justify-content: center;margin-top: 40px">
    <el-card class="profile-left-card">
      <template #header>
        学生基本信息
      </template>
      <div>
        <el-image
            :src="student?.photoUrl ? student.photoUrl : 'src/assets/ldh.jpg'"/>
        <el-form label-position="left" label-width="60px">
          <el-form-item label="姓名">
            <span>{{ student?.name }}</span>
          </el-form-item>
          <el-form-item label="性别">
            <span>{{ student?.gender === 1 ? "男" : "女" }}</span>
          </el-form-item>
          <el-form-item label="学号">
            <span>{{ student?.student_id }}</span>
          </el-form-item>
          <!--          <el-form-item label="书院">-->
          <!--            <span>{{ student.college }}</span>-->
          <!--          </el-form-item>-->
        </el-form>
      </div>
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
          <span>{{ student?.sleep_time }}</span>
        </el-form-item>
        <el-form-item label="起床时间">
          <span>{{ student?.wake_up_time }}</span>
        </el-form-item>
        <el-form-item label="空调温度">
          <span>{{ student?.air_conditioner_temperature + "℃" }}</span>
        </el-form-item>
        <el-form-item label="是否打鼾">
          <span>{{ student?.snore === true ? "是" : "否" }}</span>
        </el-form-item>
        <el-form-item label="邮箱">
          <span>{{ student?.email }}</span>
        </el-form-item>
        <el-form-item label="QQ">
          <span>{{ student?.qq }}</span>
        </el-form-item>
        <el-form-item label="微信">
          <span>{{ student?.wechat }}</span>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
  <el-drawer v-model="showEditInfo"
             size="40%"
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

<style scoped lang="less">
@import "@/assets/colors.less";

.el-form-item {
  margin: 20px 30px;

  :deep(.el-form-item__label) {
    font-size: 18px;
    font-weight: bold;
    color: white;
    min-width: 120px;
  }

  span {
    font-weight: normal;
  }
}

.profile-left-card {
  min-width: 300px;
  width: 30%;
  margin-left: 5%;
  height: 100%;

  background-color: rgba(0, 0, 0, 0.7);
  color: #dfe1d9;
  font-size: 22px;

  .el-image {
    width: 30%;
    min-width: 100px;
    margin: 30px auto;
    display: block;
  }
}

.profile-right-card {
  min-width: 400px;
  width: 40%;
  margin-left: 5%;
  height: 100%;

  background-color: rgba(0, 0, 0, 0.7);
  color: #dfe1d9;
  font-size: 22px;

  .el-button {
    background-color: @button-color;
    display: flex;
    align-items: center;
    color: whitesmoke;
    border: 1px solid rgba(34, 204, 228, 0.2);
    padding: 20px;
    border-radius: 12px;
    transition: background-color 0.5s;
    box-shadow: rgba(34, 204, 228, 0.3) 5px 5px 10px;
    margin-right: 20px;
  }
}

.el-drawer {
  h1 {
    color: black;
  }

  :deep(.el-form-item__label) {
    color: #333; /* 例如，设置为深灰色 */
  }

  .el-form {
      :deep(.el-form-item__label) {
        color: black;
      }
  }

  .el-button {
    background-color: @button-color;
    color: whitesmoke;
    border: 1px solid rgba(34, 204, 228, 0.2);
    padding: 20px;
    border-radius: 12px;
    transition: background-color 0.5s;
    box-shadow: rgba(34, 204, 228, 0.3) 5px 5px 10px;
    margin-right: 60px;
  }
}
</style>