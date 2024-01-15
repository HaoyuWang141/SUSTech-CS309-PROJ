<template>
  <el-aside width="160px" style="background-color: rgb(25,49,82); position: absolute; left: 0; bottom: 0; top: 60px">
    <el-menu :default-active="active_side"
             unique-opened
             router
             background-color="rgb(25,49,82)"
             text-color="white"
             active-text-color="wheat">
      <el-menu-item index="/mainpage">
        <el-icon :size="20">
          <house/>
        </el-icon>
        <span>房型管理</span>
      </el-menu-item>

      <el-menu-item index="/dormitory">
      <el-icon :size="20">
        <edit/>
      </el-icon>
      <span>管理宿舍</span>
<!--        增改删区域（包括building）   弹窗表单？？ -->
<!--        增改删一间宿舍-->
    </el-menu-item>


<!--      <el-menu-item index="/dormitory_show">-->
<!--        <el-icon :size="20">-->
<!--          <edit/>-->
<!--        </el-icon>-->
<!--        <span>查看宿舍</span>-->
<!--&lt;!&ndash;        ?是否需要&ndash;&gt;-->
<!--      </el-menu-item>-->


      <el-menu-item index="/student">
        <el-icon :size="20">
          <user/>
        </el-icon>
        <span>管理学生</span>
      </el-menu-item>
      <el-menu-item index="/allocate">
        <el-icon :size="20">
          <bell/>
        </el-icon>
        <span>安排选宿舍</span>
      </el-menu-item>
      <el-menu-item index="/notification">
        <el-icon :size="20">
          <message/>
        </el-icon>
        <span>发布通知</span>
      </el-menu-item>
    </el-menu>

  </el-aside>

</template>


<script>
import {Edit, Message, Bell, User, House} from '@element-plus/icons-vue'

export default {
  name: 'AsideMenu',
  components: {
    Edit, Message, Bell, User, House
  },
  data() {
    console.log(this.$route.path.substring(0, this.$route.path.length))
    return {
      // active_side: "/dormitory"
      active_side: this.$route.path.substring(0, this.$route.path.length),
      // direction: 'right',
      // style: 'position: fixed; bottom: 0; left: 0; zIndex: 1',
      // width: 100,
      // height: 100,
      // size: 200,
    }
  },
}
</script>
