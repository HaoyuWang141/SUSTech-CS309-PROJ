<script setup>
import {ref} from "vue";
const student = ref({
    name: "姓名",
    studentID: "12010000",
    gender: 1,
    college: "ShuDe",
    sleepTime: "23:59", // may be a ( DateTime )
    wakeUpTime: "7:59", // like above
    acTemp: "23",
    snore: true,
    QQ: "qqhao",
    email: "youxiangdizhi",
    wechat: "vxid",

})
</script>

<template>
  <div style="height: 80vh; display: flex; flex-direction: row">
    <el-card class="profile-left-card">
      <div slot="header" class="clearfix">
        <span>学生个人信息</span>
      </div>
      <div>
          <el-col :span="8">
            <el-form label-position="right" label-width="80px">
              <el-form-item label="姓名">
                <span>{{ student.name }}</span>
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

            </el-form>
          </el-col>
      </div>
    </el-card>
    <el-card class="profile-right-card">
      <div slot="header" class="clearfix">
        <span>选宿舍附加信息</span>
      </div>
      <el-form>
        <el-form-item label="睡觉时间">
          <span>{{ student.sleepTime }}</span>
        </el-form-item>
        <el-form-item label="起床时间">
          <span>{{ student.wakeUpTime }}</span>
        </el-form-item>
        <el-form-item label="空调温度">
          <span>{{ student.acTemp }}</span>
        </el-form-item>
        <el-form-item label="是否打鼾">
          <span>{{ student.snore===true ? "是":"否" }}</span>
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
</style>