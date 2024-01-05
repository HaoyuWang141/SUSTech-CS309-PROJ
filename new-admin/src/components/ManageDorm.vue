<template>
  <div>
    <head-menu class="header"></head-menu>
  </div>
  <el-container style="font-family: SansSerif,sans-serif;font-size: 20px">
    <aside-menu></aside-menu>

    <el-main>

      <el-aside width="160px"
                style="background-color: rgb(48,89,147); position: absolute; left: 160px; bottom: 0; top: 60px">
        <el-menu :default-active="active_side"
                 unique-opened
                 background-color="rgb(48,89,147)"
                 text-color="white"
                 active-text-color="wheat">
          <el-sub-menu v-for="reg of this.allRegions"
                        :key="reg.region_id"
                        :index="reg.region_name"
          >
            <template #title><span>{{ reg.region_name }}</span></template>
            <el-menu-item v-for="buil of reg.building"
                          :key="buil.building_id"
                          :index="buil.building_name"
                          @click="clickBuild(buil)"
            >
              <span>{{ buil.building_name }}</span>
            </el-menu-item>

          </el-sub-menu>

        </el-menu>

      </el-aside>
      <el-row style="position: absolute; left: 360px; top: 80px;width: 1000px;height: 60px" gutter="10">
        <el-col :span="16">描述：“{{ this.nowDescribe }}”</el-col>

        <el-col :span="3">
          <el-button type="warning" @click="dialog_building_up = true">
            更改楼栋信息
          </el-button>
        </el-col>
        <el-col :span="3">
          <el-button type="warning" @click="dialog_region_up = true">
            更改宿舍区信息
          </el-button>
        </el-col>

      </el-row>
      <el-row style="position: absolute; left: 360px; top: 140px;width: 1000px" gutter="10">

        <el-col :span="5">
          <el-input v-model="inputFid" placeholder="请输入楼层" @change="searchFloor"></el-input>
        </el-col>

        <el-col :span="2">
          <el-button type="primary" @click="searchFloor">
            <el-icon size="14">
              <search/>
            </el-icon>
            搜索
          </el-button>
        </el-col>

        <el-col :span="5">
          <el-input v-model="inputDid" placeholder="请输入房号" @change="searchDid"></el-input>
        </el-col>
        <el-col :span="2">
          <el-button type="primary" @click="searchDid">
            <el-icon size="14">
              <search/>
            </el-icon>
            搜索
          </el-button>
        </el-col>
        <el-col :span="1"></el-col>
        <el-col :span="3">
          <el-button type="success" @click="dialog_create_room = true">
            <el-icon size="14">
              <circle-plus/>
            </el-icon>
            添加
          </el-button>
        </el-col>
        <el-col :span="4">
          <el-tooltip
              class="box-item"
              effect="dark"
              content="请上传Excel表格，需按照下方表格的表头格式"
              placement="bottom-start"
          >
            <el-upload
                class="upload"
                :show-file-list="false"
                action=""
                :multiple="false"
                :on-change="importExcel"
                :limit="1"
            >
              <el-button type="success">
                <el-icon size="14">
                  <circle-plus/>
                </el-icon>
                批量上传
              </el-button>
            </el-upload>
          </el-tooltip>




        </el-col>
      </el-row>
      <el-table id="dormTable" :data="tableData"
                height="540"
                @selection-change="handleSelectChange"
                style="position: absolute; left: 340px; bottom: 0; top: 180px; width: 1000px">
        <el-table-column type="selection" width="55"/>
        <el-table-column width="100" label="房间号" prop="room_number"/>
        <el-table-column width="100" label="楼层" prop="floor"/>
        <el-table-column width="100" label="床数" prop="bed_count"/>
        <el-table-column width="100" label="性别" prop="gender">
          <template v-slot="scope">
            <span v-if="scope.row.gender === 0">女</span>
            <span v-else-if="scope.row.gender === 1">男</span>
          </template>
        </el-table-column>
        <el-table-column width="100" label="学生学位" prop="degree">
          <template v-slot="scope">
            <span v-if="scope.row.degree === 0">本科</span>
            <span v-else-if="scope.row.degree === 1">硕士</span>
            <span v-else-if="scope.row.degree === 2">博士</span>
          </template>
        </el-table-column>
        <el-table-column width="100" label="房型" prop="layout_id">
          <template v-slot="scope">
            <span>{{ this.layoutDictIdToObj[scope.row.layout_id.toString()].layout_name }}</span>
          </template>
        </el-table-column>

        <el-table-column label="为空" prop="is_empty"/>



      </el-table>

    </el-main>

  </el-container>

  <el-drawer
      ref="drawerRef"
      v-model="dialog_create_room"
      title="添加房间"
      direction="rtl"
      class="demo-drawer"
  >
    <div class="demo-drawer__content">
      <el-form :model="formCreateRoom">
        <el-form-item label="房号" :label-width="formLabelWidth">
          <el-input v-model="formCreateRoom.room_number" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="楼层" :label-width="formLabelWidth">
          <el-input v-model="formCreateRoom.floor" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="性别" :label-width="formLabelWidth">
          <el-select
              v-model="formCreateRoom.gender"
              placeholder="请选择"
              clearable
          >
            <el-option label="男" value="1"/>
            <el-option label="女" value="0"/>
            <el-option label="不确定" value="-1"/>
          </el-select>
        </el-form-item>
        <el-form-item label="学生学位" :label-width="formLabelWidth">
          <el-select
              v-model="formCreateRoom.degree"
              placeholder="请选择"
              clearable
          >
            <el-option label="硕士" value="1"/>
            <el-option label="博士" value="2"/>
            <el-option label="不确定" value="-1"/>
          </el-select>
        </el-form-item>
        <el-form-item label="房型" :label-width="formLabelWidth">
          <el-select
              v-model="formCreateRoom.layout_id"
              placeholder="请选择"
              clearable
          >
            <el-option v-for="ly of this.allLayouts"
              :key="ly.layout_id" :label="ly.layout_name" :value="ly.layout_id"
            >     </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="床数" :label-width="formLabelWidth">
          <el-input-number v-model="formCreateRoom.bed_count" :min="0" :max="8" />
        </el-form-item>

        <el-form-item label="描述" :label-width="formLabelWidth">
          <el-input v-model="formCreateRoom.description" autocomplete="off"/>
        </el-form-item>

        <el-form-item label="为空房" :label-width="formLabelWidth">
          <el-select
              v-model="formCreateRoom.is_empty"
              placeholder="请选择"
              clearable
          >
            <el-option label="是" value="1"/>
            <el-option label="否" value="0"/>
            <el-option label="不确定" value="-1"/>
          </el-select>
        </el-form-item>

      </el-form>
      <!--      <div class="demo-drawer__footer">-->
      <el-button @click="cancelFormCreateRoom" type="info">取消</el-button>
      <el-button @click="submitCreateRoom" type="primary">提交</el-button>
    </div>
  </el-drawer>

</template>

<script>
import HeadMenu from "@/components/util/HeadMenu";
import AsideMenu from "@/components/util/AsideMenu";
import axios from "axios";
import {Search, CirclePlus} from "@element-plus/icons-vue";


export default {
  name: "ManageDorm",
  components: {
    HeadMenu, AsideMenu, Search, CirclePlus
  },
  data() {
    return {
      inputFid: '',
      inputDid: '',

      dialog_create_room: false,
      formCreateRoom: {
        bed_count: 4
      },

      allLayouts: [],
      layoutDictIdToObj: {},
      tableData: [],
      allRegions: [],
      regionDictName: {},
      nowDescribe: '',
      nowBuilding: {}
    }
  },

  created() {
    this.getAllRegions()
    this.getAllLayout()
  },

  methods: {

    cancelFormCreateRoom() {
      this.dialog_create_room = false
    },

    submitCreateRoom(){
      let submitForm = JSON.parse(JSON.stringify(this.formCreateRoom))
      let floor = Number(this.formCreateRoom.floor)
      if (isNaN(floor)) {
        alert('格式错误')
        return
      } else {
        submitForm.floor = floor
      }

      submitForm.gender = this.setNumNull(this.formCreateRoom.gender)
      submitForm.degree = this.setNumNull(this.formCreateRoom.degree)
      submitForm.is_empty = this.setTFNull(this.formCreateRoom.is_empty)

      submitForm.building_id = this.nowBuilding.building_id
      console.log(submitForm)

      axios.post('api/admin/dormitory/create/dormitory',
          [submitForm]
      ).then(resp => {
        if (resp.status === 200) {
          alert('成功')
          this.clickBuild(this.nowBuilding)
        }
      }).catch(e => {
        console.log(e)
      })

    },

    setNumNull(str_n_1) {
      switch (str_n_1) {
        case '2': return 2
        case '1': return 1
        case '0': return 0
        case '-1': return null
      }
    },

    setTFNull(str10_1) {
      switch (str10_1) {
        case '1': return true
        case '0': return false
        case '-1': return null
      }
    },

    searchDid() {
      let newTable = []
      for (let i = 0; i < this.tableData.length; i++) {
        if (this.tableData[i].room_number.toString() === this.inputDid) {
          newTable.push(this.tableData[i])
        }
      }
      this.tableData = newTable
    },

    searchFloor() {
      let newTable = []
      for (let i = 0; i < this.tableData.length; i++) {
        if (this.tableData[i].floor.toString() === this.inputFid) {
          newTable.push(this.tableData[i])
        }
      }
      this.tableData = newTable
    },

    clickBuild(nowBuild) {
      this.inputFid = ''
      this.inputDid = ''
      this.nowDescribe = nowBuild["description"]
      this.nowBuilding = nowBuild

      axios.get('/api/admin/dormitory/get/dormitory', {
        params: {
          type: 'building',
          id: nowBuild.building_id
        }
      }).then(resp=>{
        this.tableData = resp.data
        console.log(this.tableData)
      }).catch(e => {
        console.log(e)
      })

    },

    getAllLayout() {
      console.log('llllll')
      axios.get('/api/admin/dormitory/get/layout').then(resp => {
        this.allLayouts = resp.data
        for (let i = 0; i < this.allLayouts.length; i++) {
          this.layoutDictIdToObj[this.allLayouts[i].layout_id.toString()] = this.allLayouts[i]
        }
        console.log('!!!!')
        console.log(this.allLayouts)
        console.log(this.layoutDictIdToObj)
      }).catch(e => {
        console.log(e)
      })
    },

    getAllRegions() {
      axios.get('/api/admin/dormitory/get/region').then(resp => {
        this.allRegions = resp.data
        for (let i = 0; i < this.allRegions.length; i++) {

          this.regionDictName[i.toString()] = this.allRegions[i].region_name

          axios.get('/api/admin/dormitory/get/building', {
            params: {
              regionId: this.allRegions[i].region_id
            }
          }).then(resp => {
            this.allRegions[i].building = []
            for (let j = 0; j < resp.data.length; j++) {
              this.allRegions[i].building.push(resp.data[j])
            }
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