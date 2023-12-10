<script setup lang="ts">
import {reactive, ref} from "vue";
import type {CSSProperties} from "vue";

const student = ref({
    name: "姓名",
    studentID: "12010000",
    gender: 1,
    college: "ShuDe",
    sleepTime: "23:59", // may be a ( DateTime )
    wakeUpTime: "7:59", // like above
    acTemp: "23",
    snore: false,
    QQ: "123456789",
    email: "123456789@email.com",
    wechat: "wx_id_123456789",
})
const testTemp = ref([24, 25]);

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
</script>

<template>
  <div style="height: 80vh; display: flex; flex-direction: row">
    <el-card class="profile-left-card">
      <div slot="header" class="clearfix">
        <span>学生基本信息</span>
      </div>
      <div>
        <el-form label-position="left" label-width="60px">
          <el-form-item label="姓名">
            <el-row>
            <span>{{ student.name }}</span>
            </el-row>
          </el-form-item>
          <el-form-item label="性别">
            <span>{{ student.gender === 1 ? "男" : "女" }}</span>
          </el-form-item>
          <el-form-item label="学号">
            <span>{{ student.studentID }}</span>
          </el-form-item>
          <el-form-item label="书院">
            <span>{{ student.college }}</span>
          </el-form-item>
          <el-row>
            <el-col style="align-items: center" :span="12">
              <div class="grid-content ep-bg-purple">
                <el-form-item label="书院">
                  <span>{{ student.college }}</span>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <div class="grid-content ep-bg-purple">
                <el-form-item label="书院">
                  <span>{{ student.college }}</span>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </el-card>
    <el-card class="profile-right-card">
      <div slot="header" class="clearfix">
        <span>附加信息</span>
      </div>
      <el-form label-position="left" label-width="80px">
        <el-form-item label="睡觉时间">
          <span>{{ student.sleepTime }}</span>
        </el-form-item>
        <el-form-item label="起床时间">
          <span>{{ student.wakeUpTime }}</span>
        </el-form-item>
        <el-form-item label="空调温度">
          <span>{{ student.acTemp + "℃" }}</span>
        </el-form-item>
        <el-form-item label="调温测试">
          <el-slider v-model="testTemp"
                     range
                     show-stops
                     :marks="marks"
                     :min="16"
                     :max="30"
          />
        </el-form-item>
        <el-form-item label="是否打鼾">
          <span>{{ student.snore === true ? "是" : "否" }}</span>
        </el-form-item>
        <el-form-item label="调整打鼾">
          <el-switch v-model="isSnore"
                     inline-prompt
                     active-text="是"
                     inactive-text="否"
                     style="--el-switch-on-color: #ff4949; --el-switch-off-color: #13ce66"
          />
        </el-form-item>
        <el-form-item label="邮箱">
          <span>{{ student.email }}</span>
        </el-form-item>
        <el-form-item label="QQ">
          <span>{{ student.QQ }}</span>
        </el-form-item>
        <el-form-item label="微信">
          <span>{{ student.wechat }}</span>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
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