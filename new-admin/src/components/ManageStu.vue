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
          <!--&lt;!&ndash;          <el-menu-item>&ndash;&gt;-->
          <!--&lt;!&ndash;            <span>博士生</span>&ndash;&gt;-->
          <!--            <el-sub-menu>-->
          <!--              <template #title>-->
          <!--                博士生-->
          <!--              </template>-->
          <!--              <el-menu-item>-->
          <!--                2020年入学-->
          <!--              </el-menu-item>-->
          <!--              <el-menu-item>-->
          <!--                2021年入学-->
          <!--              </el-menu-item>-->
          <!--            </el-sub-menu>-->
          <!--&lt;!&ndash;          </el-menu-item>&ndash;&gt;-->

          <el-menu-item @click="setDoctor" index="doct">
            <span>博士生</span>
          </el-menu-item>
          <el-menu-item @click="setMaster" index="mast">
            <span>硕士生</span>
          </el-menu-item>

        </el-menu>

      </el-aside>
      <!--      <el-col position: absolute; left: 320px; bottom: 0; top: 60px>-->

      <!--        <el-button @click="cl"> ddddd </el-button>-->
      <el-row style="position: absolute; left: 360px; top: 80px;width: 1000px" gutter="10">
        <el-col :span="8">
          <el-input v-model="inputSid" placeholder="请输入学号" @change="searchSid"></el-input>
        </el-col>
        <el-col :span="3">
          <el-button type="primary" @click="searchSid">
            <el-icon size="14">
              <search/>
            </el-icon>
            搜索
          </el-button>
        </el-col>
        <el-col :span="2"></el-col>
        <el-col :span="3">
          <el-button type="success" @click="dialog = true">
            <el-icon size="14">
              <circle-plus/>
            </el-icon>
            添加
          </el-button>
        </el-col>
        <el-col :span="3">
          <el-tooltip
              class="box-item"
              effect="dark"
              content="请上传Excel表格，需按照下方表格的表头格式"
              placement="bottom-start"
          >

            <el-button type="success">
              <el-icon size="14">
                <circle-plus/>
              </el-icon>
              批量上传
            </el-button>

          </el-tooltip>
        </el-col>

        <el-col :span="4">
          <input type="file" @change="handleFileChange"></el-col>

      </el-row>


      <el-table id="stuTable" :data="this.tableData"
                height="560"
                @selection-change="handleSelectionChange"
                style="position: absolute; left: 340px; bottom: 0; top: 120px; width: 1000px">
        <el-table-column type="selection" width="55"/>
        <el-table-column width="100" label="学号" prop="student_id"/>
        <el-table-column width="100" label="入学年份" prop="student_id">
          <template v-slot="scope">
            <span v-if="scope.row.gender < 2">{{ parseInt((scope.row.student_id - 10000000) / 100000) + 2000 }}</span>
          </template>
        </el-table-column>

        <el-table-column width="100" label="姓名" prop="name"/>
        <el-table-column width="50" label="性别" prop="gender">
          <template v-slot="scope">
            <span v-if="scope.row.gender === 0">女</span>
            <span v-else-if="scope.row.gender === 1">男</span>
          </template>
        </el-table-column>

        <el-table-column width="120" label="QQ号" prop="qq"/>
        <el-table-column width="200" label="E-mail" prop="email"/>
        <el-table-column label="微信" prop="wechat"/>

        <el-table-column label="已组队" prop="team_id">
          <template v-slot="scope">
            <span v-if="scope.row.team_id === null">否</span>
            <span v-else>是</span>
          </template>
        </el-table-column>
        <el-table-column label="已选宿舍" prop="team_id">
          <template v-slot="scope">
            <span v-if="scope.row.team === null">否</span>
            <span v-else-if="scope.row.team.dormitory_id === null">否</span>
            <span v-else>是</span>
          </template>
        </el-table-column>

      </el-table>

      <!--      </el-col>-->

      <el-button type="danger" style="position: absolute;
      right: 120px; bottom: 10px;"
                 @click="handleDelete">
        删除
      </el-button>

      <el-button type="warning" style="position: absolute;
      right: 200px; bottom: 10px;"
                 @click="handleUpdate">更改</el-button>

      <el-button type="primary" style="position: absolute;
      right: 280px; bottom: 10px;"
                 @click="handleExpo">导出</el-button>

    </el-main>
  </el-container>

  <el-drawer
      ref="drawerRef"
      v-model="dialog"
      title="添加学生"
      direction="rtl"
      class="demo-drawer"
  >
    <div class="demo-drawer__content">
      <el-form :model="form">
        <el-form-item label="学号" :label-width="formLabelWidth">
          <el-input v-model="form.student_id" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="姓名" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="性别" :label-width="formLabelWidth">
          <el-select
              v-model="form.gender"
              placeholder="请选择"
              clearable
          >
            <el-option label="男" value="1"/>
            <el-option label="女" value="0"/>
          </el-select>
        </el-form-item>
        <el-form-item label="QQ" :label-width="formLabelWidth">
          <el-input v-model="form.qq" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="Email" :label-width="formLabelWidth">
          <el-input v-model="form.email" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="微信" :label-width="formLabelWidth">
          <el-input v-model="form.wechat" autocomplete="off"/>
        </el-form-item>

      </el-form>
      <!--      <div class="demo-drawer__footer">-->
      <el-button @click="cancelFormCreate" type="info">取消</el-button>
      <el-button @click="submitCreate" type="primary">提交</el-button>
    </div>
  </el-drawer>

  <el-drawer
      ref="drawerRef"
      v-model="dialog_u"
      title="更改学生信息"
      direction="ltr"
      class="demo-drawer"
  >
    <div class="demo-drawer__content">
      <el-form :model="form_u">
        <el-form-item label="学号" :label-width="formLabelWidth">
          {{selectedStu[0].student_id}}
        </el-form-item>
        <el-form-item label="姓名" :label-width="formLabelWidth">
          <el-input v-model="form_u.name" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="性别" :label-width="formLabelWidth">
          <el-select
              v-model="form_u.gender"
              placeholder="请选择"
              clearable
          >
            <el-option label="男" value="1"/>
            <el-option label="女" value="0"/>
          </el-select>
        </el-form-item>
        <el-form-item label="QQ" :label-width="formLabelWidth">
          <el-input v-model="form_u.qq" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="Email" :label-width="formLabelWidth">
          <el-input v-model="form_u.email" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="微信" :label-width="formLabelWidth">
          <el-input v-model="form_u.wechat" autocomplete="off"/>
        </el-form-item>

      </el-form>
      <!--      <div class="demo-drawer__footer">-->
      <el-button @click="cancelFormUpdate" type="info">取消</el-button>
      <el-button @click="submitUpdate" type="primary">提交</el-button>
    </div>
  </el-drawer>

</template>

<script>
import HeadMenu from "@/components/util/HeadMenu";
import AsideMenu from "@/components/util/AsideMenu";
import axios from "axios";
import {Search, CirclePlus} from "@element-plus/icons-vue";
import * as XLSX from 'xlsx';

import {ElMessage, ElMessageBox} from "element-plus";
import {ref} from "vue";

const parsedData = ref([]);


export default {
  name: "ManageStu",
  components: {
    HeadMenu, AsideMenu, Search, CirclePlus
  },

  created() {
    this.check()
    this.getAllRegions()
    this.getAllMaster()
    this.getAllDoctor()
  },

  data() {
    return {
      allRegions: [],
      regionIdNameDict: {},
      buildIdObjDict: {},

      formLabelWidth: '80px',
      form: {},
      dialog: false,

      form_u: {},
      dialog_u: false,


      showingMaster: false,
      tableData: [],
      master: [],
      doctor: [],
      inputSid: "",

      selectedStu: [],
    }
  },

  methods: {
    check() {
      if (localStorage.getItem('act') === null) {
        this.$router.push('/');
      }
    },

    getAllRegions() {
      axios.get('/api/admin/dormitory/get/region').then(resp => {
        this.allRegions = resp.data
        for (let i = 0; i < this.allRegions.length; i++) {

          this.regionIdNameDict[this.allRegions[i].region_id.toString()] = this.allRegions[i].region_name

          axios.get('/api/admin/dormitory/get/building', {
            params: {
              regionId: this.allRegions[i].region_id
            }
          }).then(resp => {
            this.allRegions[i].building = []
            for (let j = 0; j < resp.data.length; j++) {
              this.allRegions[i].building.push(resp.data[j])
              this.buildIdObjDict[resp.data[j].building_id.toString()] = resp.data[j]
            }
          }).catch(e => {
            console.log(e)
          })
        }

      }).catch(e => {
        console.log(e)
      })
    },

    handleSelectionChange(pa) {
      console.log(pa)
      this.selectedStu = pa
    },

    handleUpdate() {
      if (this.selectedStu.length > 0) {
        if (this.selectedStu.length !== 1) {
          ElMessage({
            type: 'info',
            message: '请仅选择一项',
          })
          return
        } else {
          this.form_u = JSON.parse(JSON.stringify(this.selectedStu[0]))

          this.form_u.gender = this.form_u.gender === 1 ? '男' : '女'

          this.dialog_u = true
        }
      }
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

        let formCreate = []
        for (let i = 1; i < matrix.length; i++) {
          formCreate.push({})
            formCreate[i-1].student_id = matrix[i][0]
            formCreate[i-1].name = matrix[i][2]
            formCreate[i-1].gender = matrix[i][3] === '女' ? 0 : 1
            formCreate[i-1].qq = matrix[i][4]
            formCreate[i-1].email = matrix[i][5]
            formCreate[i-1].wechat = matrix[i][6]
        }
        console.log(formCreate)
        axios.post('api/admin/studentAccount/create',
            formCreate
        ).then(resp => {
          if (resp.data['success_count'] === 1) {
            alert('成功创建！')
            this.getAllMaster()
            this.getAllDoctor()
          }
        }).catch(e => {
          console.log(e)
          console.log(formCreate)
        })

      };
      reader.readAsBinaryString(file);
    },

    handleImport() {
      console.log(parsedData.value);

    },

    handleExpo() {
      let expo = []

      // this.selectedStu = [
      //   {
      //     "student_id": "12039876",
      //     "name": "李三",
      //     "gender": 0,
      //     "photo_url": "string",
      //     "air_conditioner_temperature": 0,
      //     "snore": true,
      //     "qq": "123",
      //     "email": "www",
      //     "wechat": "333",
      //     "team_id": 5,
      //     "team": {
      //       "team_id": 20,
      //       "owner_id": "12021925",
      //       "dormitory_id": 3,
      //       "dormitory": {
      //
      //         "dormitory_id": 3,
      //
      //         "floor": 3,
      //         "room_number": "308",
      //         "bed_count": 0,
      //         "is_empty": false,
      //         "gender": 0,
      //         "degree": 0,
      //         "building_id": 2,
      //
      //         "building": {
      //           "building_id": 2,
      //           "building_name": "13栋宿舍楼",
      //           "region_id": 1,
      //           "region": {
      //             "region_id": 1,
      //             "region_name": "学生宿舍区"
      //           }
      //         }
      //
      //
      //       }
      //     }
      //   },
      //
      //
      //
      //
      //
      //
      //   {
      //     "student_id": "12039881",
      //     "name": "王",
      //     "gender": 0,
      //     "photo_url": "string",
      //     "air_conditioner_temperature": 0,
      //     "snore": true,
      //     "qq": "123",
      //     "email": "www",
      //     "wechat": "333",
      //     "team_id": 5,
      //     "team": {
      //       "team_id": 20,
      //       "owner_id": "12021925",
      //       "dormitory_id": 3,
      //       "dormitory": {
      //
      //         "dormitory_id": 3,
      //
      //         "floor": 3,
      //         "room_number": "308",
      //         "bed_count": 0,
      //         "is_empty": false,
      //         "gender": 0,
      //         "degree": 0,
      //         "building_id": 2,
      //
      //         "building": {
      //           "building_id": 2,
      //           "building_name": "13栋宿舍楼",
      //           "region_id": 1,
      //           "region": {
      //             "region_id": 1,
      //             "region_name": "学生宿舍区"
      //           }
      //         }
      //
      //
      //       }
      //     }
      //   },
      //
      //
      //   {
      //     "student_id": "12039882",
      //     "name": "张",
      //     "gender": 0,
      //     "photo_url": "string",
      //     "air_conditioner_temperature": 0,
      //     "snore": true,
      //     "qq": "123",
      //     "email": "www",
      //     "wechat": "333",
      //     "team_id": 5,
      //     "team": {
      //       "team_id": 20,
      //       "owner_id": "12021925",
      //       "dormitory_id": 3,
      //       "dormitory": {
      //
      //         "dormitory_id": 3,
      //
      //         "floor": 3,
      //         "room_number": "319",
      //         "bed_count": 0,
      //         "is_empty": false,
      //         "gender": 0,
      //         "degree": 0,
      //         "building_id": 1,
      //
      //         "building": {
      //           "building_id": 1,
      //           "building_name": "11栋宿舍楼",
      //           "region_id": 1,
      //           "region": {
      //             "region_id": 1,
      //             "region_name": "学生宿舍区"
      //           }
      //         }
      //       }
      //     }
      //   },
      //
      //
      //
      //   {
      //     "student_id": "12039883",
      //     "name": "赵",
      //     "gender": 0,
      //     "photo_url": "string",
      //     "air_conditioner_temperature": 0,
      //     "snore": true,
      //     "qq": "123",
      //     "email": "www",
      //     "wechat": "333",
      //     "team_id": 5,
      //     "team": {
      //       "team_id": 20,
      //       "owner_id": "12021925",
      //       "dormitory_id": 3,
      //       "dormitory": {
      //
      //         "dormitory_id": 3,
      //
      //         "floor": 5,
      //         "room_number": "510",
      //         "bed_count": 0,
      //         "is_empty": false,
      //         "gender": 0,
      //         "degree": 0,
      //         "building_id": 1,
      //
      //         "building": {
      //           "building_id": 1,
      //           "building_name": "11栋宿舍楼",
      //           "region_id": 1,
      //           "region": {
      //             "region_id": 1,
      //             "region_name": "学生宿舍区"
      //           }
      //         }
      //       }
      //     }
      //   }
      // ]

      console.log('***********')
      console.log(this.selectedStu)

      for (let i = 0; i < this.selectedStu.length; i++) {
        let stu = {}
        stu['学号'] = this.selectedStu[i].student_id
        stu['姓名'] = this.selectedStu[i].name
        stu['性别'] = this.selectedStu[i].gender === 0? '女' : '男'
        stu.QQ = this.selectedStu[i].qq
        stu.Email = this.selectedStu[i].email
        stu['微信'] = this.selectedStu[i].wechat

        let build = this.buildIdObjDict[this.selectedStu[i].team.dormitory.building_id.toString()]

        stu['楼层'] = this.selectedStu[i].team.dormitory.floor
        stu['房号'] = this.selectedStu[i].team.dormitory.room_number

        stu['宿舍楼'] = build.building_name
        stu['区域'] = this.regionIdNameDict[build.region_id.toString()]
        expo.push(stu)
      }
      console.log(expo)

      const jsonData = ref(expo)
      const ws = XLSX.utils.json_to_sheet(jsonData.value);
      const wb = XLSX.utils.book_new();
      XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');

      // 导出 Excel 文件
      XLSX.writeFile(wb, 'exported_data.xlsx');

    },

    handleDelete() {
      if (this.selectedStu.length > 0) {

        ElMessageBox.confirm(
            '是否确认删除？', '警告',
            {
              confirmButtonText: '确认',
              cancelButtonText: '取消',
              type: 'warning',
            }
        )
            .then(() => {
              let deleSID = []
              for (let i = 0; i < this.selectedStu.length; i++) {
                deleSID.push(this.selectedStu[i].student_id)
              }
              console.log('?!?!')
              console.log(deleSID)
              axios.delete('/api/admin/studentAccount/delete', {
                data: deleSID
              }
              ).then(resp=>{
                console.log(resp.data)
                if (resp.status === 200) {
                  this.getAllDoctor()
                  this.getAllMaster()
                  ElMessage({
                    type: 'success',
                    message: '成功删除'
                  })
                }
              }).catch(e => {
                console.log(e)
              })

            })
            .catch(() => {
              ElMessage({
                type: 'info',
                message: '取消删除',
              })
            })

      }
    },

    cancelFormUpdate() {
      this.dialog_u = false
    },

    cancelFormCreate() {
      this.dialog = false
    },

    submitUpdate(){
      let student = JSON.parse(JSON.stringify(this.form_u))
      student['student_id'] = this.selectedStu[0].student_id
      student['gender'] = (student['gender'] === '男' || student['gender'] === 1) ? 1 : 0

      console.log('0000')
      console.log(student)

      // this.form_u.student_id = this.selectedStu[0].student_id
      // console.log(this.form_u)

      axios.put('/api/admin/studentAccount/update', [student]
      ).then(resp=> {
        this.form_u = {
          gender: '',
          name: '',
          student_id: '',
          qq: '',
          wechat: '',
        }

        this.getAllMaster()
        this.getAllDoctor()
        alert('成功')
      }).catch(e => {
        console.log(e)
      })


    },

    submitCreate() {
      let sid = Number(this.form.student_id)
      if (isNaN(sid)) {
        alert('学号非数字')
        return
      }
      if (sid < 10000000) {
        alert('学号格式有误')
        return
      }
      axios.get('/api/admin/studentAccount/getStudent', {
        params: {
          studentId: sid
        }
      }).then(resp => {
        if (resp.data === '') {
          axios.post('api/admin/studentAccount/create',
              [this.form]
          ).then(resp => {
            if (resp.data['success_count'] === 1) {
              alert('成功')
              this.getAllMaster()
              this.getAllDoctor()
            }
          }).catch(e => {
            console.log(e)
          })
        } else {
          alert('学号已存在')
          return
        }
      }).catch(e => {
        console.log(e)
      })

    },

    getAllDoctor() {
      this.doctor = [];
      //doctor
      for (let gen = 0; gen < 2; gen++) {
        for (let i = 2018; i < 2024; i++) {
          axios.get('/api/admin/studentAccount/get', {
            params: {
              entryYear: i,
              degree: 'doctor',
              gender: gen
            }
          }).then(res => {
            // this.dcotor = this.doctor.concat(res.data)
            if (res.data[0] !== undefined) {
              for (let stui in res.data) {
                this.doctor.push(res.data[stui])
              }
            }
            if (!this.showingMaster) {
              this.tableData = this.doctor
            }
          }).catch(e => {
            console.log(e)
          })
        }
      }
    },

    getAllMaster() {
      this.master = [];
      // master
      for (let gen = 0; gen < 2; gen++) {
        for (let i = 2018; i < 2024; i++) {
          axios.get('/api/admin/studentAccount/get', {
            params: {
              entryYear: i,
              degree: 'master',
              gender: gen
            }
          }).then(resp => {
            this.master = this.master.concat(resp.data)
            if (this.showingMaster) {
              this.tableData = this.master
            }
          }).catch(e => {
            console.log(e)
          })
        }
      }
    },

    setMaster() {
      this.showingMaster = true
      this.tableData = this.master
    },

    setDoctor() {
      this.showingMaster = false
      this.tableData = this.doctor
      console.log(this.doctor)
    },

    searchSid() {
      console.log(this.inputSid)
      axios.get('/api/admin/studentAccount/getStudents', {
        params: {
          partStudentId: this.inputSid
        }
      }).then(resp => {
        console.log(resp.data)
        let dataTemp = []
        for (let i = 0; i < resp.data.length; i++) {
          if (parseInt(resp.data[i].student_id / 10000) % 10 === 2 && this.showingMaster) {
            dataTemp.push(resp.data[i])
          } else if (parseInt(resp.data[i].student_id / 10000) % 10 === 3 && (!this.showingMaster)) {
            dataTemp.push(resp.data[i])
          }
        }
        this.tableData = dataTemp
      }).catch(e => {
        console.log(e)
      })

    }


  }


}
</script>

<style scoped>
.box-item {
  width: 110px;
  margin-top: 10px;
}
</style>