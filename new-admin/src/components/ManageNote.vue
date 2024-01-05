<template>
  <div>
    <head-menu class="header"></head-menu>
  </div>
  <el-container style="font-family: SansSerif,sans-serif;font-size: 20px">
    <aside-menu></aside-menu>
  </el-container>
  
  <el-row style="position: absolute; left: 360px; top: 80px;width: 1000px;height: 60px" gutter="10">
    <el-col :span="6">已发布通知：</el-col>
    <el-col :span="18">
      <el-button type="primary" @click="publishNote">
        发布通知
      </el-button>
    </el-col>
  </el-row>
  
  <el-table id="noteTable" :data="notes"
            height="540"
            style="position: absolute; left: 180px; bottom: 0; top: 140px; width: 1000px">
    <el-table-column width="100" label="发布者id" prop="publisher_id"/>
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
        <span v-else>全部</span>
      </template>
    </el-table-column>
    <el-table-column width="100" label="标题" prop="title"/>
    <el-table-column width="200" label="内容" prop="content"/>
    <el-table-column  label="发布时间" prop="publish_time"/>

  </el-table>
  
  <el-drawer
      ref="drawerRef"
      v-model="dialog_pub_note"
      title="发布通知"
      direction="rtl"
      class="demo-drawer"
  >
    <div class="demo-drawer__content">
      <el-form :model="formNote">
        <el-form-item label="面向性别" :label-width="formLabelWidth">
          <el-select
              v-model="formNote.gender"
              placeholder="请选择"
              clearable
          >
            <el-option label="男" value="1"/>
            <el-option label="女" value="0"/>
            <el-option label="全部" value="-1"/>
          </el-select>
        </el-form-item>
        <el-form-item label="面向学位" :label-width="formLabelWidth">
          <el-select
              v-model="formNote.degree"
              placeholder="请选择"
              clearable
          >
            <el-option label="硕士" value="1"/>
            <el-option label="博士" value="2"/>
          </el-select>
        </el-form-item>

        <el-form-item label="面向入学年份" :label-width="formLabelWidth">
          <el-input v-model="formNote.entry_year" autocomplete="off"/>
        </el-form-item>

        <el-form-item label="标题" :label-width="formLabelWidth">
          <el-input v-model="formNote.title" autocomplete="off"/>
        </el-form-item>


        <el-form-item label="内容" :label-width="formLabelWidth">
          <el-input v-model="formNote.content" autocomplete="off"/>
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
  name: "ManageNote",
  components: {
    HeadMenu, AsideMenu
  },
  data() {
    return {
      dialog_pub_note: false,
      formNote: {},
      notes: [],
      allAdm: []
    }
  },

  created() {
    this.getAllNotes()
  },

  methods: {

    submitNote() {
      let submitForm = JSON.parse(JSON.stringify(this.formNote))
      switch (submitForm.gender) {
        case '1' : submitForm.gender = 1
              break
        case '0' : submitForm.gender = 0
              break
        case '-1' : submitForm.gender = null
      }
      submitForm.entryYear = Number(submitForm.entry_year )
      submitForm.degree = Number(submitForm.degree )
      submitForm.adminAccountId = 1
      console.log(submitForm)
      axios.post('api/admin/notification/publish', null, {
        params: submitForm
          }
      ).then(resp => {
        if (resp.status === 200) {
          alert('成功')
          this.getAllNotes()
        }
      }).catch(e => {
        console.log(e)
      })

    },

    cancelPubNote() {
      this.dialog_pub_note = false
    },
    
    publishNote() {
      this.dialog_pub_note = true
    },
    
    getAllNotes() {
      this.notes = []
      axios.get('api/admin/adminAccount/get').then(resp=>{
        this.allAdm = resp.data
        for (let i = 0; i < this.allAdm.length; i++) {
          let id = this.allAdm[i].account_id
          axios.get('api/admin/notification/get', {
            params: {
              adminAccountId: id
            }
          }).then(res=>{
            // console.log(res.data)
            for (let j = 0; j < res.data.length; j++) {
              res.data[j].publish_time = new Date(res.data[j].publish_time).toLocaleString()
              this.notes.push(res.data[j])
            }
            console.log(this.notes)
          }).catch(e => {
            console.log(e)
          })
        }

      }).catch(e => {
        console.log(e)
      })

    }
  }

}
</script>

<style scoped>

</style>