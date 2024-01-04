<template>
  <div>
    <head-menu class="header"></head-menu>
  </div>
  <el-container style="font-family: SansSerif,sans-serif;font-size: 20px">
    <aside-menu></aside-menu>

    <el-main>



    </el-main>
    <!--    <el-main class="main_part"-->
    <!--             style="position: absolute; left: 160px; right: 0; top: 60px; bottom: 0; overflow-y: scroll">-->
    <!--      <el-row class="row_search">-->
    <!--        <div style="display: flex">-->
    <!--          <el-input v-model="keyword" :placeholder="placeholder" @keydown.enter="handleSearch"></el-input>-->
    <!--          <el-button type="primary" @click="handleSearch" style="margin-left: 10px">搜索</el-button>-->
    <!--        </div>-->
    <!--      </el-row>-->

    <!--      <el-row><br/></el-row>-->

    <!--      <el-table class="posts_table" ref="main_table"-->
    <!--                :data="this.tableData" :height="tableH" style="width: 100%"-->
    <!--                @row-click="this.try"-->
    <!--                v-el-table-infinite-scroll="this.loadTableMore"-->
    <!--      >-->
    <!--        <el-table-column width="120" label="时间" prop="time"/>-->
    <!--        <el-table-column width="100" label="分区" prop="section"/>-->
    <!--        &lt;!&ndash;        <el-table-column width="70" label="" prop="avatar">&ndash;&gt;-->
    <!--        &lt;!&ndash;          <slot>&ndash;&gt;-->
    <!--        &lt;!&ndash;            <el-avatar shape="square" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"/>&ndash;&gt;-->
    <!--        &lt;!&ndash;          </slot>&ndash;&gt;-->
    <!--        &lt;!&ndash;        </el-table-column>&ndash;&gt;-->
    <!--        <el-table-column width="110" label="作者" prop="author"/>-->

    <!--        <el-table-column min-width="120" label="标题" prop="title"/>-->
    <!--        <el-table-column width="60" label="赞" prop="likeCount"/>-->
    <!--        <el-table-column type="expand">-->
    <!--          <template #default="props">-->
    <!--            <div>-->
    <!--              <p style="position: relative; left: 5%">{{ props.row.contentHead }}</p>-->
    <!--            </div>-->
    <!--          </template>-->
    <!--        </el-table-column>-->
    <!--      </el-table>-->

    <!--      <el-button id="ToTop" type="plain" @click="toTop">-->
    <!--        Top-->
    <!--      </el-button>-->
    <!--    </el-main>-->

  </el-container>

</template>

<script type="text/javascript">


import HeadMenu from './util/HeadMenu'
import AsideMenu from "@/components/util/AsideMenu";
import axios from "axios";
import "echarts-wordcloud/dist/echarts-wordcloud";
import "echarts-wordcloud/dist/echarts-wordcloud.min";
// import AsideMenu from "@/components/util/AsideMenu";
import elTableInfiniteScroll from 'el-table-infinite-scroll';
import router from "@/router";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "MainPage",
  directives: {
    'el-table-infinite-scroll': elTableInfiniteScroll
  },

  components: {
    AsideMenu,
    // Edit,
    // AsideMenu,
    HeadMenu
  },

  created() {
    // this.check()

  },

  data() {
    return {
      tableData: [],
      windowH: 800,
      tableH: 600,
      nextPageI: 0,
      allSections: [],
      // active_side: '/mainpage',
      search_suggestions: [],
      keyword: '',
      placeholder: '请输入关键字',
      sectionList: {},
      nowSectionId: -1
    }
  },

  methods: {
    check() {
      if (localStorage.getItem('uid') === null) {
        this.$router.push('/');
      }
    }



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

</style>