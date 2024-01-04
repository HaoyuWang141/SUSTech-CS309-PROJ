<template>
    <div>
        <head-menu class="header"></head-menu>
    </div>

  <el-container  style="font-family: SansSerif,sans-serif;font-size: 20px; display: grid">

<!--      <el-main style="display: flex; justify-content: center;">-->
<!--        <el-container style="font-family: SansSerif,sans-serif;">-->
<!--          <div class="card-container">-->
            <el-card class="card">
              <div style="display: flex">
                <span class = "author-span">{{ cardauthor }}</span>
              </div>
              <div style="display: flex">
                <span class = "uid-span">{{ carduid }}</span>
              </div>
              <div style="display: flex">
                <span class = "mail-span">{{ cardmail }}</span>
              </div>
            </el-card>
<!--          </div>-->
<!--        </el-container>-->
<!--      </el-main>-->

    <el-row><br/></el-row>

    <el-table class="posts" ref="main_table"
              :data="this.tableData" :height="tableH" style="width: 100%"
              @row-click="this.go"
              v-el-table-infinite-scroll="this.loadTableMore"
    >
      <el-table-column min-width="40px" label="时间" prop="time"/>
<!--      <el-table-column  label="分区" prop="section"/>-->
<!--      <el-table-column  label="" prop="avatar">-->
<!--        <slot>-->
<!--          <el-avatar shape="square" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"/>-->
<!--        </slot>-->
<!--      </el-table-column>-->
      <el-table-column min-width="140px" label="标题" prop="title"/>
      <el-table-column  label="赞" prop="likeCount"/>
      <el-table-column type="expand">
        <template #default="props">
          <div>
            <p style="position: relative; left: 5%">{{ props.row.contentHead }}</p>
          </div>
        </template>
      </el-table-column>
    </el-table>

  </el-container>
</template>

<style>
.container {
    //height: ; /* 让容器高度等于屏幕高度 */
    display: grid; /* 使用 flex 布局 */
    justify-content: center; /* 水平居中 */
    align-items: center; /* 垂直居中 */
}

.card-container {
    width: 800px; /* 设置卡片容器的固定宽度 */
    display: flex; /* 使用 flex 布局 */
    justify-content: center; /* 水平居中 */
    //border: 1px solid #ccc;
    //box-shadow: 0 1px 3px rgba(0, 0, 0, 0.12), 0 1px 2px rgba(0, 0, 0, 0.24); /* 添加阴影*/
}

.posts {
  border-radius: 20px;
  margin: 20px;
  padding-right: 20px;
  display: flex;
  position: absolute;
  right: 60px;
  left: 340px;
}

.card {
  border-radius: 20px;
  width: 300px;
  height: 400px;
  margin: 20px;
  box-shadow: #33383f;
  display: flex;
  align-items: flex-start;
  text-align: left;
  text-overflow: ellipsis;
}

.author-span {
    font-size: 24px;
    font-weight: bold;
}

.uid-span {

}
</style>

<script>
import axios from "axios";
import HeadMenu from "@/components/util/HeadMenu.vue";
import router from "@/router";
import {useRouter} from 'vue-router'

export default {
    components: {HeadMenu},
    data() {
        return {
            cardauthor: '',
            carduid: 'uid',
            cardmail: 'mail',
          tableData: [],
          uid: 1
        }
    },
    created() {
      const router = useRouter()
      this.uid = parseInt(router.currentRoute.value.params['uid'])

      this.getInfo();
    },
    methods: {
      go(row) {
        console.log(row['pid'], ' index oo')
        router.push("/postshow/" + row['pid'])
      },
        getInfo() {
            axios.get('/api/user/info', {
                params: {
                    uid: this.uid
                }
            }).then(
                resp=>{
                    console.log(resp);
                    this.cardauthor = resp.data['userName'];
                    this.carduid = 'uid: ' + resp.data['uid'];
                    this.cardmail = 'mail: ' + resp.data['mail'];
                }
            )

          axios.get('/api/uidFind', {
            params: {
              uid: this.uid,
              page: 0
            }
          }).then(
              resp=>{
                console.log(resp.data)
                this.tableData = [];
                for (let post of resp.data) {
                  post['time'] = this.simplifyTime(post['time'])
                }
                this.tableData = this.tableData.concat(resp.data)
              }
          )
        },
      simplifyTime(t) {
        return t.split('T')[0]
      }
    }
}
</script>

