<template>
  <div>
    <head-menu class="header"></head-menu>
  </div>
  <el-container style="font-family: SansSerif,sans-serif;font-size: 20px">
    <aside-menu></aside-menu>
  </el-container>
</template>

<script>
import HeadMenu from "@/components/util/HeadMenu";
import AsideMenu from "@/components/util/AsideMenu";

export default {
  name: "ManageNote",
  components: {
    HeadMenu, AsideMenu
  },
}
</script>

<style scoped>

</style>