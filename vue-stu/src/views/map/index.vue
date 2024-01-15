<template>
  <div id="map-container">
    <!-- 设置地图图片作为背景图 -->
    <div id="map-image"></div>

    <!-- 放置按钮 -->
    <el-button id="xue" @click="dormWithRegion(1)">学生宿舍</el-button>
    <el-button id="hu" @click="dormWithRegion(2)">湖畔宿舍</el-button>
    <el-button id="li" @click="dormWithRegion(3)">荔园宿舍</el-button>
    <el-button id="xin" @click="dormWithRegion(4)">欣园宿舍</el-button>
  </div>

</template>

<script setup lang="ts">
import router from "@/router";

function dormWithRegion(region: number) {router.push({
  name: "Dormitory",
  query: {
    region: region,
  },
});

}
</script>

<style>
#map-container {
  width: 100%;
  background-color: #c3bfd5;
}

#map-image {
  width: 100%;
  height: 100%;
  background: url('src/assets/map.png') no-repeat center fixed;
  background-size: contain;
  position: absolute;
  top: 0;
  left: 0;
  z-index: 1;
}


#xue {
  z-index: 2;
  /* 按钮样式 */
  position: absolute;
  top: 170px;
  left: 720px;
  background-color: cornflowerblue;
  color: white;
  border: None;
  padding: 5px;
}

#hu {
  z-index: 2;
  /* 按钮样式 */
  position: absolute;
  top: 250px;
  left: 620px;
  color: white;
  border: None;
  padding: 5px;
  background-color: green;
}

#xin {
  z-index: 2;
  /* 按钮样式 */
  position: absolute;
  top: 60px;
  left: 1060px;
  color: white;
  border: None;
  padding: 5px;
  background-color: saddlebrown;
}

#li {
  z-index: 2;
  /* 按钮样式 */
  position: absolute;
  top: 120px;
  left: 830px;
  color: white;
  border: None;
  padding: 5px;
  background-color: purple;
}
</style>
