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
    <el-table-column label="开始时间" prop="start_time"/>
    <el-table-column label="结束时间" prop="end_time"/>
    <el-table-column type="expand" prop="dorms">
      <template v-slot="scope">
        <span v-if="Object.keys(scope.row.dorms).length !== 0" ><b>限定宿舍列表： </b></span>
        <p style="position: relative; left: 100px" v-for="buili of Object.keys(scope.row.dorms)" :key="buili">
          {{ this.buildIdDict[buili].toString() +': '+ scope.row.dorms[buili] + '\n' }}
        </p>
      </template>
    </el-table-column>

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

        <el-form-item>限定宿舍范围：</el-form-item>
        <el-form-item label=" " :label-width="formLabelWidth">

          <el-cascader :props="props"
                       @change="atSelectDorms" v-model="seleList"
                       collapse-tags clearable/>
        </el-form-item>

<!--        <el-form-item>楼栋：</el-form-item>-->


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
      props: {
        multiple: true,
        lazy: true,
        lazyLoad: (node, resolve) => {
          console.log(node.level)

          if (node.level === 0) {
            axios.get('/api/admin/dormitory/get/region').then(resp => {
              this.allRegions = resp.data
              this.aboveDormOps = []
              for (let i = 0; i < this.allRegions.length; i++) {
                this.aboveDormOps.push({
                  value: this.allRegions[i].region_id.toString(),
                  label: this.allRegions[i].region_name
                })
                axios.get('/api/admin/dormitory/get/building', {
                  params: {
                    regionId: this.allRegions[i].region_id
                  }
                }).then(resp => {
                  this.aboveDormOps[i].children = []
                  for (let j = 0; j < resp.data.length; j++) {
                    this.aboveDormOps[i].children.push({
                      value: resp.data[j].building_id,
                      label: resp.data[j].building_name,
                    })
                  }
                  if (this.aboveDormOps[i].children.length === 0)
                    this.aboveDormOps[i].leaf = true
                  resolve(this.aboveDormOps)
                  return
                }).catch(e => {
                  console.log(e)
                })
              }
            }).catch(e => {
              console.log(e)
            })
          }

          if (node.level === 3) {
            resolve(this.allFloorsDict[node.value])
          }

          if (node.level === 2) {

            axios.get('/api/admin/dormitory/get/dormitory', {
              params: {
                type: 'building',
                id: node.value
              }
            }).then(resp => {
              if (resp.data.length === 0) {
                resolve([])
                return
              }
              let flIndexDict = {}
              let retLis = [] // [11, 15, 17, 18]
              let i = 0
              for (let di of resp.data) {

                if (!('f' + di['floor'] in flIndexDict)) {
                  this.allFloorsDict[node.value + 'f' + di['floor']] = []
                  flIndexDict['f' + di['floor']] = i
                  retLis.push({
                    value: node.value + 'f' + di['floor'],
                    label: di['floor'] + '层'
                  })
                  i++
                }
              }

              for (let di of resp.data) {
                // let atIndex = flIndexDict['f' + di['floor']]
                this.allFloorsDict[node.value + 'f' + di['floor']].push({
                  value: di.dormitory_id,
                  label: di.room_number,
                  leaf: true
                })
              }
              console.log(retLis)
              resolve(retLis)
              return;
            }).catch(e => {
              console.log(e)
            })

          }
        }

      },

      aboveDormOps: [],

      buildIdDict: {},

      dialog_pub_state: false,
      formState: {},
      dormList: [],

      seleRegions: [],
      seleBuilds: [],

      states: [],
      allAdm: [],
      timeRange: '',
      allRegions: [],
      dormTree: {},

      seleList: [],

      allFloorsDict: {},

    }
  },

  created() {
    // this.check()
    this.getAllRegions()
    this.getAllState()
  },

  methods: {

    atSelectDorms(e) {

      // console.log(e)
      // console.log(this.seleList)
    },

    getDormsForBuild(build_id) {
      axios.get('/api/admin/dormitory/get/dormitory', {
        params: {
          type: 'building',
          id: build_id
        }
      }).then(resp => {


      }).catch(e => {
        console.log(e)
      })
    },

    getAllRegions() {
      axios.get('/api/admin/dormitory/get/region').then(resp => {
        this.allRegions = resp.data
        this.aboveDormOps = []
        for (let i = 0; i < this.allRegions.length; i++) {
          // this.dormTree[this.allRegions[i].region_id.toString()] = this.allRegions[i]
          this.aboveDormOps.push({
            value: this.allRegions[i].region_id.toString(),
            label: this.allRegions[i].region_name
          })
          axios.get('/api/admin/dormitory/get/building', {
            params: {
              regionId: this.allRegions[i].region_id
            }
          }).then(resp => {
            this.aboveDormOps[i].children = []
            for (let j = 0; j < resp.data.length; j++) {

              this.buildIdDict[resp.data[j].building_id.toString()] =
                  this.allRegions[i].region_name + ' ' + resp.data[j].building_name

              this.aboveDormOps[i].children.push({
                value: resp.data[j].building_id,
                label: resp.data[j].building_name
              })
            }
            if (this.aboveDormOps[i].children.length === 0)
              this.aboveDormOps[i].leaf = true

          }).catch(e => {
            console.log(e)
          })
        }
      }).catch(e => {
        console.log(e)
      })
    },

    check() {
      if (localStorage.getItem('act') === null) {
        this.$router.push('/');
      }
    },
    submitNote() {
      console.log(this.formState)
      let didList = []
      for (let di of this.seleList) {
        didList.push(di[3])
      }
      console.log(this.seleList)

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

      axios.post('api/admin/allocation/relate', didList, {
        params: {
          entryYear: Number(submitForm.entry_year ),
          degree: Number(submitForm.degree ),
          gender: Number(submitForm.gender ),
          // dormitoryIdList: didList
        }
      }).then(resp => {
        if (resp.status === 200) {
          alert('成功！')
          console.log('?!?!?!?')
        }
      }).catch(e => {
        console.log(e)
      })

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

      console.log(this.states)

      this.dialog_pub_state = false
    },
    pubState() {

      this.dialog_pub_state = true
    },

    getAllState() {
      this.states = []
      axios.get('api/admin/allocation/getState').then(res => {
        console.log(res.data)
        for (let j = 0; j < res.data.length; j++) {
          res.data[j].start_time = new Date(res.data[j].start_time).toLocaleString()
          res.data[j].end_time = new Date(res.data[j].end_time).toLocaleString()


          let statei = res.data[j]

          axios.get('api/admin/allocation/getRelation', {
            params: {
              entryYear: res.data[j].entry_year,
              degree: res.data[j].degree,
              gender: res.data[j].gender
            }
          }).then(resp=>{

            let doIdList = resp.data
            let dorms = {} // {buid1:[501, 502, 503]}

            for (let doid of doIdList) {
              axios.get('api/admin/dormitory/get/dormitory', {
                params:{
                  type: 'dormitory',
                  id: doid
                }
              }).then(resDorm=>{
                let buid = resDorm.data[0].building_id.toString()
                if (buid in dorms) {
                  dorms[buid].push(resDorm.data[0].room_number)
                } else {
                  dorms[buid] = []
                  dorms[buid].push(resDorm.data[0].room_number)
                }
              }).catch(e => {
                console.log(e)
              })
            }

            statei.dorms = dorms
            this.states.push(statei)

          }).catch(e => {
            console.log(e)
          })
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