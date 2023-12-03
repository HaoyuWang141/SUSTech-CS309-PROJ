<script setup>

</script>

<template>
  <div>
    <el-card>
      <div slot="header" class="clearfix">
        <span>学生个人信息</span>
      </div>
      <div>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form label-position="right" label-width="80px">
              <el-form-item label="姓名">
                <span>{{ student.name }}</span>
              </el-form-item>
              <el-form-item label="学号">
                <span>{{ student.studentId }}</span>
              </el-form-item>
              <el-form-item label="书院">
                <span>{{ student.college }}</span>
              </el-form-item>
              <el-form-item label="睡觉时间">
                <span>{{ student.sleepTime }}</span>
              </el-form-item>
              <el-form-item label="空调温度">
                <span>{{ student.acTemperature }}</span>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
      </div>
    </el-card>
  </div>
</template>

<style scoped>

</style>