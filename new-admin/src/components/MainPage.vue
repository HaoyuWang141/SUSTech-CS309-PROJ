<template>
  <div>
    <head-menu class="header"></head-menu>
  </div>
  <el-container style="font-family: SansSerif,sans-serif;font-size: 20px">
    <aside-menu></aside-menu>

    <el-main>

      <el-table
          :data="laysTable"
          @selection-change="sele"
          height="560"
          style="position: absolute; left: 180px; bottom: 0; top: 110px; width: 1000px"
      >
        <el-table-column type="selection" width="55"/>
        <el-table-column width="100" label="名称" prop="layout_name"/>
        <el-table-column label="描述" prop="description"/>
        <el-table-column type="expand" prop="img_url">
          <template v-slot="scope">
            <el-row>

              <el-col span="8"></el-col>
              <el-col span="8">
                <img :src="scope.row.image_url" height="300"/></el-col>
              <el-col span="8"></el-col>
            </el-row>
          </template>

        </el-table-column>


      </el-table>

      <el-button type="success"  style="position: absolute;
      right: 200px; bottom: 10px;"
                 @click="dialogAdd = true" >添加</el-button>

      <el-button type="warning"  style="position: absolute;
      right: 260px; bottom: 10px;"
                 @click="clickUp" >更改</el-button>

    </el-main>
  </el-container>


  <el-drawer
      ref="dra"
      v-model="dialogUp"
      title="更改房型"
      direction="rtl"
      class="demo-drawer"
  >
    <div class="demo-drawer__content">
      <el-form :model="form1">
        <el-form-item label="房型名称" :label-width="formLabelWidth">
          <el-input v-model="form1.layout_name" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="描述" :label-width="formLabelWidth">
          <el-input v-model="form1.description" autocomplete="off"/>
        </el-form-item>

      </el-form>
      <el-button @click="cancelForm1Up" type="info">取消</el-button>
      <el-button @click="submit1Up" type="primary">提交</el-button>
    </div>
  </el-drawer>

  <el-drawer
      ref="drawerRef"
      v-model="dialogAdd"
      title="添加房型"
      direction="rtl"
      class="demo-drawer"
  >
    <div class="demo-drawer__content">
      <el-form :model="form">
        <el-form-item label="房型名称" :label-width="formLabelWidth">
          <el-input v-model="form.layout_name" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="描述" :label-width="formLabelWidth">
          <el-input v-model="form.description" autocomplete="off"/>
        </el-form-item>

      </el-form>
      <el-button @click="cancelFormCreate" type="info">取消</el-button>
      <el-button @click="submitCreate" type="primary">提交</el-button>
    </div>
  </el-drawer>

</template>

<script type="text/javascript">


import HeadMenu from './util/HeadMenu'
import AsideMenu from "@/components/util/AsideMenu";
import axios from "axios";
import "echarts-wordcloud/dist/echarts-wordcloud";
import "echarts-wordcloud/dist/echarts-wordcloud.min";
import elTableInfiniteScroll from 'el-table-infinite-scroll';
import router from "@/router";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "MainPage",
  directives: {
    'el-table-infinite-scroll': elTableInfiniteScroll
  },

  components: {
    AsideMenu,
    // Edit,
    // AsideMenu,
    HeadMenu
  },

  created() {
    this.check()
    this.getAllLayout()
  },

  data() {
    return {
      dialogAdd: false,
      dialogUp: false,
      laysTable: [],
      form: {},
      form1: {},
      seleLay: null
    }
  },

  methods: {

    clickUp() {
      if (this.seleLay === null || this.seleLay === []) {
        alert('未选择')
        return;
      }
      if (this.seleLay.length !== 1) {
        alert('请仅选一个')
        return
      }
      this.form1.layout_name = this.seleLay[0].layout_name
      this.form1.description = this.seleLay[0].description
      this.dialogUp = true
    },

    submit1Up() {
      this.form1.layout_id = this.seleLay[0].layout_id
      console.log(this.form1)
      axios.put('api/admin/dormitory/update/layout', this.form1).then(resp=>{
        alert('成功更改')
        this.getAllLayout()
      })

    },

    cancelForm1Up() {
      this.dialogUp = false
    },

    sele(se) {
      this.seleLay = se
    },

    submitCreate() {
      axios.post('api/admin/dormitory/create/layout', [this.form]).then(resp=>{
        alert('成功添加')
        this.getAllLayout()
      })
    },

    cancelFormCreate() {
      this.dialogAdd = false
    },

    getAllLayout() {
      axios.get('/api/admin/dormitory/get/layout').then(resp => {
        this.laysTable = resp.data
      }).catch(e => {
        console.log(e)
      })
    },

    check() {
      if (localStorage.getItem('act') === null) {
        this.$router.push('/');
      }
    }

  }
}

</script>


<style scoped>

.rows {
  /*border-radius: 15px;*/
  background-clip: padding-box;
  /*margin: 40px auto;*/
  /*width: 1200px;*/
  padding: 15px 35px 15px 55px;
  /*border: 1px solid #eaeaea;*/
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

.row_search {
  /*border-radius: 15px;*/
  background-clip: padding-box;
  /*margin: 90px auto;*/
  /*width: 400px;*/
  /*padding: 15px 35px 15px 55px;*/
  /*border: 1px solid #eaeaea;*/
  /*border: 1px solid #eaeaea;*/
  /*box-shadow: 0 0 25px #cac6c6;*/
}

.row_search /deep/ .el-input {
  left: 0;
  width: 300%;
  box-shadow: 0 0 2px #cac6c6;
}

</style>