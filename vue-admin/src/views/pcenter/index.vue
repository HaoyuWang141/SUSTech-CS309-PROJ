<template>
  个人主页
  <el-form :model="form" label-width="120px">
    <el-form-item label="姓名" >
      <el-col span="400px">
        <el-input v-model="form.name" />
      </el-col>
    </el-form-item>
    <el-form-item label="身份">
      <el-select v-model="form.region" placeholder="请选择">
        <el-option label="辅导员" value="fdy" />
        <el-option label="宿管" value="sg" />
        <el-option label="其他" value="others" />
      </el-select>
    </el-form-item>
    <el-form-item label="联系方式">
      <el-input v-model="form.desc" type="textarea" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">确认更改</el-button>
      <el-button>取消</el-button>
    </el-form-item>
  </el-form>

</template>

<script lang="ts" setup>
import { reactive } from 'vue'

// do not use same name with ref
const form = reactive({
  name: '某管理员',
  region: '',
  date1: '',
  date2: '',
  delivery: false,
  type: [],
  resource: '',
  desc: '',
})

const onSubmit = () => {
  console.log('submit!')
}
</script>