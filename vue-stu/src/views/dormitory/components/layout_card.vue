<template>
    <el-card :body-style="{ padding: '0' }">
        <img :src="image" class="image" alt="Card image" />
        <div
            style="
                display: flex;
                justify-content: center;
                align-items: center;
                padding: 30px 30px;
            "
        >
            <span>{{ title }}</span>
            
            <el-button type="primary" @click="viewDetails">
                查看详情
            </el-button>
        </div>
    </el-card>

    <!-- 模态框内容 -->
    <el-dialog title="详细信息" v-model="dialogVisible" width=40%;>
        <div class="image-container">
            <el-image
                :src="image"
                :zoom-rate="1.5"
                :max-scale="7"
                :min-scale="0.2"
                :preview-src-list="[image]"
                :initial-index="1"
                fit="cover"
                alt="Card image"
            >
            </el-image>
        </div>
        <span>{{ title }}</span>
        <div class="text item">
            {{ description }}
        </div>
    </el-dialog>
</template>

<script setup lang="ts">
import { ref, PropType } from "vue";
import { Dormitory } from "@/types/globalTypes";

const props = defineProps({
    id: Number,
    image: String,
    title: String,
    description: String,
    dormitory: Object as PropType<Dormitory>,
});

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
        margin-bottom: 10px; // 标题下的间距
        font-size: large;
        font-weight: bold;
    }

    .text.item {
        font-size: large;
    }
}
</style>
