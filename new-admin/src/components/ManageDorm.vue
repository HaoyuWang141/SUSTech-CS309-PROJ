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
        <el-col :span="16">楼栋描述：“{{ this.nowDescribe }}”</el-col>

        <el-col :span="3">
          <el-button type="warning" @click="dialog_up_b = true">
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
            添加/更改
          </el-button>
        </el-col>
        <el-col :span="4">
          <input type="file" @change="handleFileChange"></el-col>

        <!--          <el-tooltip-->
<!--              class="box-item"-->
<!--              effect="dark"-->
<!--              content="请上传Excel表格，需按照下方表格的表头格式"-->
<!--              placement="bottom-start"-->
<!--          >-->
<!--            <el-upload-->
<!--                class="upload"-->
<!--                :show-file-list="false"-->
<!--                action=""-->
<!--                :multiple="false"-->
<!--                :on-change="importExcel"-->
<!--                :limit="1"-->
<!--            >-->
<!--              <el-button type="success">-->
<!--                <el-icon size="14">-->
<!--                  <circle-plus/>-->
<!--                </el-icon>-->
<!--                批量上传-->
<!--              </el-button>-->
<!--            </el-upload>-->
<!--          </el-tooltip>-->


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

        <el-table-column label="为空" prop="is_empty">
          <template v-slot="scope">
            <span v-if="scope.row.is_empty === null">未确定</span>
            <span v-else>{{ scope.row.is_empty ? '是' : '否'}}</span>
          </template>
        </el-table-column>


      </el-table>

    </el-main>

  </el-container>

  <el-drawer
      ref="drawerRef"
      v-model="dialog_create_room"
      title="添加/更改房间"
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
<!--            <el-option label="不确定" value="-1"/>-->
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
<!--            <el-option label="不确定" value="-1"/>-->
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


  <el-drawer
      ref="drawerRef"
      v-model="dialog_up_b"
      title="更改楼栋信息"
      direction="rtl"
      class="demo-drawer"
  >
    <div class="demo-drawer__content">
      <el-form :model="formUB">
        <el-form-item label="楼栋名" :label-width="formLabelWidth">
          <el-input v-model="formUB.building_name" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="描述" :label-width="formLabelWidth">
          <el-input v-model="formUB.description" autocomplete="off"/>
        </el-form-item>



      </el-form>
      <!--      <div class="demo-drawer__footer">-->
      <el-button @click="cancelformUB" type="info">取消</el-button>
      <el-button @click="submitUB" type="primary">提交</el-button>
    </div>
  </el-drawer>


  <el-drawer
      ref="drawerRef"
      v-model="dialog_region_up"
      title="更改宿舍区信息"
      direction="rtl"
      class="demo-drawer"
  >
    <div class="demo-drawer__content">
      <el-form :model="formRegion">
        <el-form-item label="宿舍区名" :label-width="formLabelWidth">
          <el-input v-model="formRegion.region_name" autocomplete="off"/>
        </el-form-item>
      </el-form>
      <!--      <div class="demo-drawer__footer">-->
      <el-button @click="cancelformRegion" type="info">取消</el-button>
      <el-button @click="submitRegion" type="primary">提交</el-button>
    </div>
  </el-drawer>


</template>

<script>
import HeadMenu from "@/components/util/HeadMenu";
import AsideMenu from "@/components/util/AsideMenu";
import axios from "axios";
import {Search, CirclePlus} from "@element-plus/icons-vue";
import router from "@/router";
import * as XLSX from "xlsx";
import {ref} from "vue";

const parsedData = ref([]);


export default {
  name: "ManageDorm",
  components: {
    HeadMenu, AsideMenu, Search, CirclePlus
  },
  data() {
    return {
      inputFid: '',
      inputDid: '',
      dialog_region_up: false,
      formRegion: [],
      dialog_create_room: false,
      formCreateRoom: {
        bed_count: 4
      },

      allLayouts: [],
      layoutDictIdToObj: {},
      layoutDictNameToObj: {},

      tableData: [],
      allRegions: [],
      nowDescribe: '',
      nowBuilding: {},

      dialog_up_b: false,
      formUB: [],

      dict4Name: {},

      selectDorm: []
    }
  },

  created() {
    this.check()
    this.getAllRegions()
    this.getAllLayout()
  },

  methods: {
    check() {
      if (localStorage.getItem('act') === null) {
        this.$router.push('/');
      }
    },

    cancelformRegion() {
      this.dialog_region_up = false
    },

    submitRegion() {
      let submitForm = {
        region_id: this.nowBuilding.region_id,
        region_name: this.formRegion.region_name
      }
      console.log(submitForm)
      let url = 'api/admin/dormitory/update/region'
      axios.put(url,
          submitForm
      ).then(resp => {
        if (resp.status === 200) {
          alert('成功更改')
          router.go(0)
        }
      }).catch(e => {
        console.log(e)
      })

    },

    handleSelectChange(p) {
      this.selectDorm = p
    },

    cancelformUB() {
      this.dialog_up_b = false
    },

    submitUB(){
      let submitForm = JSON.parse(JSON.stringify(this.nowBuilding))
      submitForm.building_name = this.formUB.building_name
      submitForm.description = this.formUB.description



      let url = 'api/admin/dormitory/update/building'
      axios.put(url,
          submitForm
      ).then(resp => {
        if (resp.status === 200) {
          alert('成功更改')
          router.go(0)
        }
      }).catch(e => {
        console.log(e)
      })

    },

    cancelFormCreateRoom() {
      this.dialog_create_room = false
    },

    handleFileChange(event) {
      const file = event.target.files[0];
      this.readFileContent(file);
    },

    readFileContent (file) {
      const reader = new FileReader();
      reader.onload = (e) => {
        const fileContent = e.target.result;
        // 使用 xlsx 库解析文件内容
        const workbook = XLSX.read(fileContent, { type: 'binary' });
        const sheetName = workbook.SheetNames[0];
        const sheet = workbook.Sheets[sheetName];
        // 将解析的数据存储在 Vue 组件中
        parsedData.value = XLSX.utils.sheet_to_json(sheet, { header: 1 });
        console.log(parsedData.value);
        let matrix = parsedData.value
        alert('上传成功！')
        console.log(matrix)
        console.log(this.dict4Name)
        let formCreate = []
        let failList = []
        for (let i = 1; i < matrix.length; i++) {
          let region = matrix[i][0]
          let build = matrix[i][1]
          let layout = matrix[i][7]
          if (! (layout in this.layoutDictNameToObj) ||
              ! (region in this.dict4Name)
          ) {
            failList.push(matrix[i])
            continue
          } else if (! (build in this.dict4Name[region])) {
            failList.push(matrix[i])
            continue
          }

          // formCreate[i-1].is_enpty = matrix[i][8] === '是' ? true :
          //     (matrix[i][8] === '否' ? false : null)
          formCreate.push({})
          formCreate[i-1].layout_id = this.layoutDictNameToObj[layout].layout_id
          formCreate[i-1].building_id = this.dict4Name[region][build].building_id

          formCreate[i-1].floor = matrix[i][2]
          formCreate[i-1].room_number = matrix[i][3]
          formCreate[i-1].bed_count = matrix[i][4]
          formCreate[i-1].gender = matrix[i][5] === '女' ? 0 : 1
          switch (matrix[i][6]) {
            case '': formCreate[i-1].degree = null
                  break
            case '本科': formCreate[i-1].degree = 0
                  break
            case '硕士': formCreate[i-1].degree = 1
                  break
            case '博士': formCreate[i-1].degree = 2
          }
        }
        console.log(formCreate)
        let url = 'api/admin/dormitory/create/dormitory'
        axios.post(url, formCreate).then(resp => {
          if (resp.status === 200) {
            let msg = '添加完成，'
            if (failList.length > 0) {
              msg += '失败列表：\n'
              for (let f of failList) {
                msg += f.toString() + '\n'
              }
            }
            alert(msg)
            this.clickBuild(this.nowBuilding)
          }
        }).catch(e => {
          console.log(e)
        })

      };
      reader.readAsBinaryString(file);
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

      let url = 'api/admin/dormitory/create/dormitory'
      if (this.selectDorm.length === 1) {
        url = 'api/admin/dormitory/update/dormitory'
        submitForm.dormitory_id = this.selectDorm[0].dormitory_id
        axios.put(url,
            [submitForm]
        ).then(resp => {
          if (resp.status === 200) {
            alert('成功更改')
            this.clickBuild(this.nowBuilding)
          }
        }).catch(e => {
          console.log(e)
        })
        return;
      }

      axios.post(url,
          [submitForm]
      ).then(resp => {
        if (resp.status === 200) {
          alert('成功添加')
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

      this.formUB.building_name = nowBuild.building_name
      this.formUB.description = nowBuild.description


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
          this.layoutDictNameToObj[this.allLayouts[i].layout_name.toString()] = this.allLayouts[i]
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

          this.dict4Name[this.allRegions[i].region_name] = this.allRegions[i]

          axios.get('/api/admin/dormitory/get/building', {
            params: {
              regionId: this.allRegions[i].region_id
            }
          }).then(resp => {
            this.allRegions[i].building = []
            for (let j = 0; j < resp.data.length; j++) {
              this.allRegions[i].building.push(resp.data[j])

              this.dict4Name[this.allRegions[i].region_name][resp.data[j].building_name] =
                  resp.data[j]
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