<template>
    <el-form class="form-horizontal">
        <el-form-item label="Region">
            <el-select v-model="selectedRegion" placeholder="Select region">
                <el-option
                    v-for="region in regions"
                    :key="region"
                    :label="region"
                    :value="region"
                >
                </el-option>
            </el-select>
        </el-form-item>

        <el-form-item label="Building">
            <el-select v-model="selectedRegion" placeholder="Select building">
                <el-option
                    v-for="region in regions"
                    :key="region"
                    :label="region"
                    :value="region"
                >
                </el-option>
            </el-select>
        </el-form-item>

        <el-form-item label="Floor">
            <el-select v-model="selectedRegion" placeholder="Select floor">
                <el-option
                    v-for="region in regions"
                    :key="region"
                    :label="region"
                    :value="region"
                >
                </el-option>
            </el-select>
        </el-form-item>

        <el-form-item label="Room">
              <el-input v-model="room_number" placeholder="Room Number" clearable />
        </el-form-item>

        <el-form-item>
            <el-button type="primary" @click="viewDorm">View</el-button>
        </el-form-item>
    </el-form>

    <el-row :gutter="40">
        <el-col :span="5" v-for="(card, index) in cards" :key="index">
            <LayoutCard
                :image="card.image"
                :title="card.title"
                :description="card.description"
            />
        </el-col>
    </el-row>
</template>

<script setup lang="ts">
import { defineAsyncComponent, ref } from "vue";
import LayoutCard from "./components/layout_card.vue";
import axiosInstance from "@/axios/axiosConfig"

const regions = ref(null); // 示例数据
const selectedRegion = ref<string>("");

const room_number = ref<string>("");

const selectedLayout = ref<string>("");

const viewDorm = () => {
    // 实现查看宿舍的逻辑
    console.log(
        `Selected Dorm: ${selectedRegion.value}, Layout: ${selectedLayout.value}`
    );
};

const cards = ref([
    {
        image: "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png",
        title: "Title 1",
        description: "Description 1",
    },
    {
        image: "src/assets/bg.png",
        title: "Title 1",
        description: "Description 1",
    },
    {
        image: "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png",
        title: "Title 1",
        description: "Description 1",
    },
    {
        image: "src/assets/bg.png",
        title: "Title 1",
        description: "Description 1",
    },
    {
        image: "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png",
        title: "Title 1",
        description: "Description 1",
    },
    {
        image: "src/assets/bg.png",
        title: "Title 1",
        description: "Description 1",
    },
    {
        image: "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png",
        title: "Title 1",
        description: "Description 1",
    },
    {
        image: "src/assets/bg.png",
        title: "Title 1",
        description: "Description 1",
    },
]);

async function fetchData() {
  try {
    const response = await axiosInstance.get('/student/dormitory/getRegions', );
    regions.value = response.data;
  } catch (error) {
    console.error(error);
  }
}

fetchData();

</script>

<style scoped lang="less">
main {
    border-radius: 8rem;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);

    .container {
        margin: auto 0;
        padding: 0 10%;
    }
}

.form-horizontal {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    padding: 0 10%;
    gap: 30px;
    margin: 50px 0;

    .el-form-item {
        flex: 1;
        min-width: 200px;
        max-width: 30%;
    }
}

.el-row {
    padding: 0 20px;
    justify-content: center;
}
</style>
@/axios/axiosConfigs