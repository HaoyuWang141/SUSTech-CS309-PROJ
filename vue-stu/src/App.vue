<script>
export default {
    data() {
        return {
            activeMenu: 'home'
        };
    },
    methods: {
        handleMenuClick(index) {
            this.activeMenu = index;
            this.$router.push({name: index});
        }
    }
};
</script>

<template>
  <div class="layout-container">
    <el-container>
      <el-aside style="width: 15%; height: 100%; background-color: lightpink;">
        <el-menu :default-active="activeMenu" class="el-menu-vertical">
          <el-menu-item index="home" @click="handleMenuClick('home')">HOME</el-menu-item>
          <el-menu-item index="profile" @click="handleMenuClick('profile')">个人信息</el-menu-item>
          <el-menu-item index="查看"></el-menu-item>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header style="background-color: #f2f2f2">
          Header
        </el-header>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<style scoped>
.layout-container {
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    display: flex;
    //background-color: var(--el-color-primary-light-7);
    box-sizing: border-box;
}
</style>
