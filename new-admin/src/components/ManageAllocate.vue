<template>
  <div>
    <head-menu class="header"></head-menu>
  </div>
  <el-container style="font-family: SansSerif,sans-serif;font-size: 20px">
    <aside-menu></aside-menu>
  </el-container>



  <el-row style="position: absolute; left: 360px; top: 80px;width: 1000px;height: 60px" gutter="10">
    <el-col :span="6">已发布选房状态（时间段）：</el-col>
    <el-col :span="18">
      <el-button type="primary" @click="pubState">
        发布选房状态
      </el-button>
    </el-col>
  </el-row>

  <el-table id="noteTable" :data="states"
            height="540"
            style="position: absolute; left: 180px; bottom: 0; top: 140px; width: 1000px">
    <el-table-column width="100" label="面向学位" prop="degree">
      <template v-slot="scope">
        <span v-if="scope.row.degree === 0">本科</span>
        <span v-else-if="scope.row.degree === 1">硕士</span>
        <span v-else-if="scope.row.degree === 2">博士</span>
      </template>
    </el-table-column>
    <el-table-column width="100" label="面向年级" prop="entry_year"/>
    <el-table-column width="100" label="面向性别" prop="gender">
      <template v-slot="scope">
        <span v-if="scope.row.gender === 0">女</span>
        <span v-else-if="scope.row.gender === 1">男</span>
      </template>
    </el-table-column>
    <el-table-column width="100" label="阶段状态" prop="stage">
      <template v-slot="scope">
        <span v-if="scope.row.stage === 0">组队阶段</span>
        <span v-else-if="scope.row.stage === 1">收藏阶段</span>
        <span v-else-if="scope.row.stage === 2">正选阶段</span>
        <span v-else-if="scope.row.stage === 3">结束阶段</span>
      </template>
    </el-table-column>
    <el-table-column  label="开始时间" prop="start_time"/>
    <el-table-column  label="结束时间" prop="end_time"/>

  </el-table>

  <el-drawer
      ref="drawerRef"
      v-model="dialog_pub_state"
      title="发布选房状态"
      direction="rtl"
      class="demo-drawer"
  >
    <div class="demo-drawer__content">
      <el-form :model="formState">
        <el-form-item label="面向性别" :label-width="formLabelWidth">
          <el-select
              v-model="formState.gender"
              placeholder="请选择"
              clearable
          >
            <el-option label="男" value="1"/>
            <el-option label="女" value="0"/>
          </el-select>
        </el-form-item>
        <el-form-item label="面向学位" :label-width="formLabelWidth">
          <el-select
              v-model="formState.degree"
              placeholder="请选择"
              clearable
          >
            <el-option label="硕士" value="1"/>
            <el-option label="博士" value="2"/>
          </el-select>
        </el-form-item>
        <el-form-item label="面向入学年份" :label-width="formLabelWidth">
          <el-input v-model="formState.entry_year" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="起止时间" :label-width="formLabelWidth">
          <el-date-picker
              v-model="timeRange"
              type="datetimerange"
              start-placeholder="Start date"
              end-placeholder="End date"
              format="YYYY-MM-DD HH:mm:ss"
              date-format="YYYY/MM/DD ddd"
              time-format="A hh:mm:ss"
          />
        </el-form-item>
        <el-form-item label="状态" :label-width="formLabelWidth">
          <el-select
              v-model="formState.state"
              placeholder="请选择"
              clearable
          >
            <el-option label="组队阶段" value="0"/>
            <el-option label="收藏阶段" value="1"/>
            <el-option label="正选阶段" value="2"/>
            <el-option label="结束阶段" value="3"/>
          </el-select>
        </el-form-item>


        
      </el-form>
      <!--      <div class="demo-drawer__footer">-->
      <el-button @click="cancelPubNote" type="info">取消</el-button>
      <el-button @click="submitNote" type="primary">提交</el-button>
    </div>
  </el-drawer>



</template>

<script>
import HeadMenu from "@/components/util/HeadMenu";
import AsideMenu from "@/components/util/AsideMenu";
import axios from "axios";

export default {
  name: "ManageAllocate",
  components: {
    HeadMenu, AsideMenu
  },

  data() {
    return {
      dialog_pub_state: false,
      formState: {},
      states: [],
      allAdm: [],
      timeRange: ''
    }
  },

  created() {
    this.getAllState()
  },

  methods: {
    submitNote() {
      console.log(this.formState)
      console.log(this.timeRange[0].getTime())
      console.log(this.timeRange[1].getTime())
      let submitForm = JSON.parse(JSON.stringify(this.formState))

      submitForm.entryYear = Number(submitForm.entry_year )
      submitForm.degree = Number(submitForm.degree )
      submitForm.gender = Number(submitForm.gender )
      submitForm.state = Number(submitForm.state )
      submitForm.startTime = this.timeRange[0].getTime()
      submitForm.endTime = this.timeRange[1].getTime()

      console.log(submitForm)
      axios.post('api/admin/allocation/setState', null, {
            params: submitForm
          }
      ).then(resp => {
        if (resp.status === 200) {
          alert('成功')
          this.getAllState()
        }
      }).catch(e => {
        console.log(e)
      })
    },
    cancelPubNote() {
      this.dialog_pub_state = false
    },
    pubState() {
      this.dialog_pub_state = true
    },

    getAllState() {
      this.states = []
          axios.get('api/admin/allocation/getState').then(res=>{
            console.log(res.data)
            for (let j = 0; j < res.data.length; j++) {
              res.data[j].start_time = new Date(res.data[j].start_time).toLocaleString()
              res.data[j].end_time = new Date(res.data[j].end_time).toLocaleString()
              this.states.push(res.data[j])
            }
            console.log('???')
            console.log(this.states)
          }).catch(e => {
            console.log(e)
          })


    }
  }


}
</script>

<style scoped>

</style>