<script setup>
import {ref} from "vue";

const teammates = ref([
    {
        name: "wanghaoyu",
        sid: 11911612,
    },
    {
        name: "liuyiyu",
        sid: 12116666,
    }
])

function deleteRow(index) {
    teammates.value.splice(index, 1)
    //TODO
}
</script>

<template>
  <el-card class="team-view-card">
    <template #header>
      <div class="team-view-header">
        <span>组队信息</span>
      </div>
    </template>
    <div class="team-view-body">
      <div style="display: flex; flex-direction: row">
        <el-button type="primary">
          邀请
        </el-button>
        <el-button type="primary">
          查看收藏
        </el-button>
      </div>
      <div>
        <!--        this div is for displaying teammates-->
        <el-table :data="teammates" style="width: 100%">
          <el-table-column prop="name" label="姓名"/>
          <el-table-column prop="sid" label="学号"/>
          <el-table-column fixed="right" label="操作">
            <template #default="scope">
              <el-button
                  type="danger"
                  size="small"
                  @click.prevent="deleteRow(scope.$index)">
                移除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </el-card>
</template>

<style scoped>
.team-view-header {
    text-align: center;
    font-size: 18px;
    font-weight: bold;
}

.team-view-card {
    width: 30%;
    height: 30%;
    margin: 2%;
}
</style>