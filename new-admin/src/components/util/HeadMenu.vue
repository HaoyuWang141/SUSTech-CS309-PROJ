<template>
  <el-menu
      :default-active="activeIndex"
      router
      class="el-menu1"
      mode="horizontal"
      unique-opened
      background-color="rgb(16,86,126)"
      text-color="white"
      active-text-color="rgb(149,213,243)"
      style="position: relative; width: 100%; height: 60px;"
  >
<!--    <el-menu-item index="/mainpage" @click="this.$router.push('/mainpage')">-->
<!--      <b style="font-size: 18px; font-family: SansSerif,sans-serif">首页</b>-->
<!--    </el-menu-item>-->

    <span
        style="position: absolute;text-align: center;padding-top: 10px;right: 40%;
        font-size: 30px;font-weight: bold; color: white; font-family: SansSerif,sans-serif">
      南科大选宿舍系统 - 管理端
    </span>
    <el-button type="primary" style="position: absolute; right: 1%; top: 25%" @click="doLogout">登出</el-button>

  </el-menu>
</template>

<script>
import axios from "axios";
import {ElMessageBox} from "element-plus";

export default {
  name: 'HeadMenu',
  data() {
    console.log(this.$route.path.substring(0, this.$route.path.length))
    return {
      activeIndex: this.$route.path.substring(0, this.$route.path.length)
    }
  },
    methods:{
    goUserInfo() {
      if (localStorage.getItem('uid') == null) {
        ElMessageBox.alert('请先登录', {
          confirmButtonText: 'OK',
          type: 'error'
        })
        return
      }
      this.$router.push('/userinfo/' + localStorage.getItem('uid'))
    },

      doLogout:function(){
        let account = localStorage.getItem('act')
        localStorage.removeItem('act')
        localStorage.removeItem('pw')
        this.$router.push('/');
        ElMessageBox.alert('管理员账号：' +account+ ' 登出成功', {
          confirmButtonText: 'OK',
          type: 'success'
        })
      }
    }
}
</script>

<style scoped>

title {
  font-family: SansSerif,sans-serif;
}


el-menu, el-sub-menu, el-menu-item {
  font-family: SansSerif,sans-serif;
}

/*a {*/
/*  text-decoration: none;*/
/*}*/

span {
  pointer-events: none;
}
</style>
