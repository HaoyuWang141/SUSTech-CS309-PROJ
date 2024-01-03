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
            <!-- <span>{{ title }}</span>
            <div class="text item">
                {{ description }}
            </div> -->
            <el-button type="primary" @click="viewDetails">查看详情</el-button>
        </div>
    </el-card>
</template>

<script setup lang="ts">
const props = defineProps({
    id: Number,
    image: String,
    title: String,
    description: String,
});

const viewDetails = () => {
    // 添加查看详情的逻辑
    console.log("查看详情被点击");
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
</style>
