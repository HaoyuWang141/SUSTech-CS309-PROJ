<template>
    <el-form class="form-horizontal">
        <el-form-item>
            <el-select
                v-model="selectedRegion"
                value-key="region_id"
                placeholder="Select region"
                @change="fetchBuildings"
                class="select"
            >
                <el-option
                    v-for="region in regions"
                    :key="region.region_id"
                    :label="region.region_name"
                    :value="region"
                >
                </el-option>
            </el-select>
        </el-form-item>

        <el-form-item>
            <el-select
                v-model="selectedBuilding"
                value-key="building_id"
                placeholder="Select building"
                class="select"
            >
                <el-option
                    v-for="building in buildings"
                    :key="building.building_id"
                    :label="building.building_name"
                    :value="building"
                >
                </el-option>
            </el-select>
        </el-form-item>

        <el-form-item>
            <el-input v-model="floor" placeholder="floor" clearable />
        </el-form-item>

        <el-form-item>
            <el-input
                v-model="room_number"
                placeholder="room number"
                clearable
            />
        </el-form-item>

        <el-button type="primary" @click="fetchDorms">View</el-button>
    </el-form>

    <div class="dormitroy_description">
        <h1>简介</h1>
        <h2>
            {{ selectedRegion?.region_name }}
            {{ selectedBuilding?.building_name }}
        </h2>
        <p>{{ selectedBuilding?.description }}</p>
    </div>

    <div class="dormitroy_description">
        <h1>布局</h1>
        <p>
            每栋楼虽然宿舍很多，但是宿舍类型大多大同小异，看看这栋楼都有哪些类型的房间吧！
        </p>
    </div>

    <div class="cards">
        <el-row :gutter="40">
            <el-col
                :span="7"
                v-for="dorm in dormitoryList"
                :key="dorm.dormitory_id"
            >
                <LayoutCard :dormitory="dorm" />
            </el-col>
        </el-row>
    </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import LayoutCard from "./components/layout_card.vue";
import axiosInstance from "@/axios/axiosConfig";
import type { Region, Building, Layout, Dormitory } from "@/types/globalTypes";

const regions = ref<Region[]>([]);
const selectedRegion = ref<Region>();

const buildings = ref<Building[]>([]);
const selectedBuilding = ref<Building>();

const floor = ref(null);

const room_number = ref(null);

const dormitoryList = ref<Dormitory[]>([]);

async function fetchRegions() {
    try {
        const response = await axiosInstance.get(
            "/student/dormitory/getRegions"
        );
        regions.value = response.data;
    } catch (error) {
        console.error(error);
    }
}

async function fetchBuildings() {
    selectedBuilding.value = undefined;
    try {
        const response = await axiosInstance.get(
            "/student/dormitory/getBuildings",
            {
                params: { regionId: selectedRegion.value?.region_id },
            }
        );
        buildings.value = response.data;
    } catch (error) {
        console.error(error);
    }
}

async function fetchDorms() {
    try {
        const response = await axiosInstance.get(
            "/student/dormitory/getDormitories",
            {
                params: {
                    buildingId: selectedBuilding.value?.building_id,
                    floor: floor.value,
                    roomNumber: room_number.value,
                },
            }
        );
        dormitoryList.value = response.data;
    } catch (error) {
        console.error(error);
    }
}

fetchRegions();
</script>

<style lang="less">
@import "../../assets/colors.less";

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
    padding: 30px 5%;
    gap: 10%;
    margin: 50px 10%;

    background-color: rgba(0, 0, 0, 0.7);
    box-shadow: 2px 2px 4px rgba(34, 204, 228, 0.8);
    border-radius: 12px;

    .el-form-item {
        flex: 1;
        min-width: 200px;
        max-width: 20%;
        // margin: 0;

        .el-select {
            background-color: @mainColor;
        }
    }

    .el-button {
        border: None;
        background-color: @button-color-light;

        &:hover {
            background-color: darken(@button-color-light, 15%);
        }
    }
}

.dormitroy_description {
    margin: 50px 10%;
    padding: 20px 5%;
    background-color: rgba(0, 0, 0, 0.7);
    box-shadow: 2px 2px 4px rgba(34, 204, 228, 0.8);
    border-radius: 12px;

    h1 {
        color: @text-color2;
        font-size: 2rem;
        font-weight: bold;
        margin-bottom: 10px;
    }

    h2 {
        color: @text-color2;
        font-size: 1.5rem;
        font-weight: bold;
        margin-bottom: 10px;
    }

    p {
        color: @text-color2;
        font-size: 16px;
    }
}

.cards {
    padding: 0 10%;

    .el-row {
        justify-content: center;
    }
}
</style>
