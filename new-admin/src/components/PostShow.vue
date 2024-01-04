<template>
  <div>
    <head-menu class="header"></head-menu>
  </div>



  <el-container  style="font-family: SansSerif,sans-serif;font-size: 20px; display: grid">
<!--    <el-main class="main_part"-->
<!--             style="position: absolute; left: 160px; right: 0; top: 60px; bottom: 0; overflow-y: scroll">-->

<!--      <el-row><br/></el-row>-->
<!--    </el-main>-->
    <div>
      <el-card class="card">
        <div style="display: flex">
          <span class="author-span">
            <a ref="authorLink" href="">{{ cardauthor }}</a>
            </span>
        </div>
        <div style="display: flex">
          <span class="uid-span">{{ carduid }}</span>
        </div>
        <div style="display: flex">
          <span class="mail-span">{{ cardmail }}</span>
        </div>
      </el-card>

      <el-popconfirm title="确认删除此帖子?" @confirm="handleDelete">
        <template #reference>
<!--          <el-button>Delete</el-button>-->
          <el-button ref="edit" type="info" plain v-if="showE" style="position: absolute; left: 60px">
            <!--        <el-icon :class="this.isLiked ? 'el-icon-thumb-up' : 'el-icon-thumb-up-outline'"></el-icon>-->
            删除帖子
          </el-button>
        </template>
      </el-popconfirm>



      <el-button ref="edit" type="primary" plain v-if="showE" @click="handleEdit" style="position: absolute; left: 150px">
        <!--        <el-icon :class="this.isLiked ? 'el-icon-thumb-up' : 'el-icon-thumb-up-outline'"></el-icon>-->
        编辑帖子
      </el-button>

      <el-button ref="like" type="danger" plain @click="handleLike" style="position: absolute; left: 240px">
        <!--        <el-icon :class="this.isLiked ? 'el-icon-thumb-up' : 'el-icon-thumb-up-outline'"></el-icon>-->
        赞{{ this.likeCount }}
      </el-button>

    </div>


    <el-card class="post">
      <div class="clearfix" >
        <span class="card-title" style="font-size: 40px">{{ postTitle }}</span>
      </div>
      <div class="card-content">
              <span>
                <div class="ql-container ql-snow" style="margin: 10px; width: 100%; box-sizing: border-box">
            <div class="ql-editor" v-html="content" style="margin: 10px; width: 100%; box-sizing: border-box"></div>
          </div>
              </span>
      </div>
    </el-card>
  </el-container>


</template>

<script type="text/javascript">

import {Search} from '@element-plus/icons'
import HeadMenu from './util/HeadMenu'
import axios from "axios";
import "echarts-wordcloud/dist/echarts-wordcloud";
import "echarts-wordcloud/dist/echarts-wordcloud.min";
import elTableInfiniteScroll from 'el-table-infinite-scroll';
import {useRouter} from 'vue-router'
import {ElMessageBox} from "element-plus";


let image = new Image();
let nowTime = null;
// eslint-disable-next-line no-unused-vars
let windowH = 0;
// eslint-disable-next-line no-unused-vars
let tableH = 0;
let nextPageI = 0;
// eslint-disable-next-line no-unused-vars
let allSections = [];
let pid = -1;

let postContent = '';

export default {
  name: "PostShow",
  directives: {
    'el-table-infinite-scroll': elTableInfiniteScroll
  },

  components: {
    HeadMenu
  },

  created() {
    const router = useRouter()
    this.pid = parseInt(router.currentRoute.value.params['postId'])
    console.log("hello, post ", this.pid)
    this.postContent = this.getPostDetail(this.pid)

    this.allSections = []
    this.getAllSections()
    nowTime = new Date(Date.now()).toISOString()
    this.autoHeight()
    this.tableData = []
    window.onresize = this.autoHeight
    // this.getInfo();

  },

  data() {
    return {
      showE: false,
      tableData: [],
      windowH: 800,
      tableH: 600,
      nextPageI: 0,
      allSections: [],
      active_side: 'g',
      search_suggestions: [],
      pid: -1,
      postContent: '',
      aut: 'primary',
      cardauthor: 'Author',
      carduid: 'uid',
      cardmail: 'mail',
      content: 'NULL',
      postTitle: 'no title',
      likeCount: 0,
      isLiked: false,
      autUid: 0
    }
  },

  methods: {
    handleDelete() {



      axios.post('/api/delPost', {}, {
        params: {
          pid: this.pid
        }
      }).then(resp=>{
        console.log(resp.data)
        this.$router.push('/userinfo/' + localStorage.getItem('uid'))
      }).catch(e=>{
        console.log(e)
      })
    },
    handleEdit() {
      if (localStorage.getItem('uid') === null) {
        ElMessageBox.alert('请登录后再编辑', {
          confirmButtonText: 'OK',
          type: 'error'
        })
        return
      }
      if (parseInt(localStorage.getItem('uid')) !== parseInt(this.autUid)) {
        console.log(localStorage.getItem('uid'), this.autUid)
        ElMessageBox.alert('非作者账号无法编辑', {
          confirmButtonText: 'OK',
          type: 'error'
        })
        return
      }
      this.$router.push('/postedit/' + this.pid)
    },

    handleLike() {
      if (localStorage.getItem('uid') == null) {
        ElMessageBox.alert('请登录后再点赞', {
          confirmButtonText: 'OK',
          type: 'error'
        })
        return
      }
      if (this.isLiked) {
        ElMessageBox.alert('已点赞，请勿重复点赞', {
          confirmButtonText: 'OK',
          type: 'error'
        })
      } else {
        axios.get('/api/like', {
          params: {
            pid: this.pid
          }
        }).then(resp=>{
          console.log(resp.data)
          if (resp.data) {
            this.$refs.like.plain = false
            this.likeCount += 1
          }
          else {
            ElMessageBox.alert('已点赞，请勿重复点赞', {
              confirmButtonText: 'OK',
              type: 'error'
            })
          }
        })
      }
    },
    getAllSections() {
      console.log('run : getAllSections')
      axios.get('/api/sections').then(resp => {
        this.allSections = resp.data
      }).catch(e => {
        console.log(e)
      })
    },

    async getPostDetail(pid) {
      console.log('run : getPostDetail, pid', pid)
      axios.get('/api/post', {
        params: {
          pid: pid
        }
      }).then(resp => {
        let resData = resp.data
        console.log('resData-> ', resData)
        this.postContent = resData
        this.aut = this.postContent['author']
        this.content = this.postContent['content']
        this.postTitle = this.postContent['title']

        this.likeCount = this.postContent['likeCount']
        this.getIsLiked()

        this.getInfo()
        console.log(this.postContent['likeCount'] + '1!!')
        return resData
      }).catch(e => {
        console.log(e)
      })
      return ''
    },

    getIsLiked() {
      axios.get('/api/isLike', {
        params: {
          pid: this.pid
        }
      }).then(resp => {
        console.log(resp.data)
        this.isLiked = resp.data
        if (this.isLiked) {
          this.$refs.like.type = "danger"
          this.$refs.like.plain = false
        } else {
          this.$refs.like.type = "danger"
          this.$refs.like.plain = true
        }
      }).catch(e => {
        console.log(e)
      })
    },

    getInfo() {
      axios.get('/api/user/info', {
        params: {
          uid: this.postContent['uid']
        }
      }).then(
          resp => {
            console.log(resp);
            this.cardauthor = resp.data['userName'];
            this.carduid = 'uid: ' + resp.data['uid'];
            this.autUid = resp.data['uid'];
            this.cardmail = 'mail: ' + resp.data['mail'];


            this.$refs.authorLink.href = "/userinfo/" + this.autUid

            if (parseInt(resp.data['uid']) === parseInt(localStorage.getItem('uid'))) {
              this.showE = true
            }
          }
      )
    },

    async toTop() {
      this.$refs.main_table.scrollTo(0, 0)
      // for (let cur = 1000; cur > 0; cur-=10) {
      //   await this.sleep(1)
      //   this.$refs.main_table.scrollTo(0, cur)
      // }
    },

    autoHeight() {
      console.log(
          'window.innerHeight ', window.innerHeight,
          'document.body.clientHeight', document.body.clientHeight,
          'document.documentElement.clientHeight', document.documentElement.clientHeight
      )
      // if (window.innerHeight)
      windowH = Math.max(window.innerHeight, document.body.clientHeight, document.documentElement.clientHeight)
      // this.setTableH()
      this.tableH = (windowH - 200) + 'px'
    },

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

.card-title {
  font-size: 18px;
  font-weight: bold;
}

.card-content {
  font-size: 14px;
  margin: 20px;
}

.post {
  border-radius: 20px;
  margin: 20px;
  padding-right: 20px;
  display: flex;
  position: absolute;
  right: 60px;
  left: 340px;
}
</style>