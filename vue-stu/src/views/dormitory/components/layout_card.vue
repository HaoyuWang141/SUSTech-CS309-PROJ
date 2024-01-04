<template>
    <el-card :body-style="{ padding: '0' }">
        <img :src="layout?.image_url" class="image" alt="Card image" />
        <div
            style="
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
                padding: 30px;
            "
        >
            <span style="margin-bottom: 5px">{{ dormitory.room_number }}</span>
            <span style="margin-bottom: 20px">{{ layout?.layout_name }}</span>
            <el-button type="primary" @click="viewDetails">
                查看详情
            </el-button>
        </div>
    </el-card>

    <!-- 模态框内容 -->
    <el-dialog title="详细信息" v-model="dialogVisible" width="40%;">
        <div class="image-container">
            <el-image
                :src="layout?.image_url"
                :zoom-rate="1.5"
                :max-scale="7"
                :min-scale="0.2"
                :preview-src-list="[layout?.image_url]"
                :initial-index="1"
                fit="cover"
                alt="Card image"
            >
            </el-image>
        </div>
        <span>{{ layout?.layout_name }}</span>
        <div class="text-item">
            <span>区域：{{ dormitory.building.region.region_name }}</span>
            <span>楼栋：{{ dormitory.building.building_name }}</span>
            <span>房号：{{ dormitory.room_number }}</span>
            <span>床数：{{ dormitory.bed_count }}</span>
            <span>简介：{{ layout?.description }}</span>
        </div>
    </el-dialog>
</template>

<script setup lang="ts">
import { ref, PropType, computed } from "vue";
import { Dormitory, Layout } from "@/types/globalTypes";

const props = defineProps({
    dormitory: {
        type: Object as PropType<Dormitory>,
        required: true,
    },
});

const layout = computed(() => props.dormitory.layout);
const dialogVisible = ref(false);

const viewDetails = () => {
    // 添加查看详情的逻辑
    console.log("查看详情被点击");
    dialogVisible.value = true;
};
</script>

<style scoped lang="less">
@import "@/assets/colors.less";

.el-card {
    min-width: 100px;
    margin-bottom: 30px;
    background-color: @gray;
    color: @text-color4;
    border: none;
    border-radius: 12px;
    font-size: large;
    box-shadow: 4px 4px 10px rgba(34, 204, 228, 0.8);
}

.image {
    width: 100%;
    height: 200px;
    display: block;
}

.el-button {
    border: None;
    background-color: @button-color;
    &:hover {
        background-color: darken(@button-color, 5%);
    }
}

.el-dialog {
    .image-container {
        display: flex;
        justify-content: center; // 水平居中
        align-items: center; // 垂直居中
        margin-bottom: 15px; // 提供一些间距

        .el-image {
            max-width: 100%; // 限制最大宽度，以适应容器
            // 高度将自动调整以保持纵横比
        }
    }

    span {
        display: block;
        margin: auto;
        margin-bottom: 10px; // 标题下的间距
        font-size: large;
        font-weight: bold;
    }

    .text-item {
        padding: 0 10%; // 左右留白
        font-size: large;
    }
}
</style>
