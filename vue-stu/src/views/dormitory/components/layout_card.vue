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
    <el-dialog title="详细信息" v-model="dialogVisible">
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
        <span class="title">{{ layout?.layout_name }}</span>
        <div class="text-item">
            <span>区域：{{ dormitory.building.region.region_name }}</span>
            <span>楼栋：{{ dormitory.building.building_name }}</span>
            <span>房号：{{ dormitory.room_number }}</span>
            <span>床数：{{ dormitory.bed_count }}</span>
            <span>简介：{{ layout?.description }}</span>
        </div>
        <el-button type="primary" @click="bookmark"> 收藏至队伍 </el-button>
        <div class="comment-container">
            <span>评论</span>
            <el-form>
                <el-form-item>
                    <el-input
                        v-model="newComment"
                        placeholder="请输入评论"
                        type="textarea"
                        :rows="3"
                    />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="postComment">
                        发布评论
                    </el-button>
                </el-form-item>
            </el-form>
            <div class="comments-list">
                <Comment
                    v-for="comment in comments"
                    :key="comment.id"
                    :comment="comment"
                />
            </div>
        </div>
    </el-dialog>
</template>

<script setup lang="ts">
import { ref, PropType, computed } from "vue";
import { Dormitory, CommentType } from "@/types/globalTypes";
import axiosInstance from "@/axios/axiosConfig";
import { ElMessage } from "element-plus";
import Comment from "./comment.vue";

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

async function bookmark() {
    // 添加收藏的逻辑
    console.log("收藏至队伍被点击");
    axiosInstance
        .post(
            "/student/dormitory/favor2",
            {},
            {
                params: {
                    studentAccountId: localStorage.getItem("studentId"),
                    dormitoryId: props.dormitory.dormitory_id,
                },
            }
        )
        .then((res) => {
            console.log(res);
            ElMessage.success("收藏成功");
            dialogVisible.value = false;
        })
        .catch((err) => {
            console.log(err);
            ElMessage.error("收藏失败");
        });
}

const comments = ref<CommentType[]>([]);

async function fetchCommentsAndApplies(dormitory_id: number) {
    // 添加获取评论的逻辑
    console.log("获取评论");
    axiosInstance
        .get("/student/forum/getComments", {
            params: {
                dormitoryId: dormitory_id,
            },
        })
        .then((res) => {
            console.log(res);
            comments.value = res.data;
        })
        .then(() => {
            comments.value.forEach((comment) => {
                axiosInstance
                    .get("/student/forum/getReply", {
                        params: {
                            commentId: comment.id,
                        },
                    })
                    .then((res) => {
                        console.log(res);
                        comment.reply_list = res.data;
                    })
                    .catch((err) => {
                        console.log(err);
                    });
            });
        })
        .catch((err) => {
            console.error(err);
        });
}
fetchCommentsAndApplies(props.dormitory.dormitory_id);

const newComment = ref("");
async function postComment() {
    // 添加发布评论的逻辑
    console.log("发布评论");
    if (newComment.value === "") {
        ElMessage.error("评论不能为空");
        return;
    }
    console.log(localStorage.getItem("studentId"));
    axiosInstance
        .post(
            "/student/forum/launch",
            {},
            {
                params: {
                    studentAccountId: localStorage.getItem("studentId"),
                    dormitoryId: props.dormitory.dormitory_id,
                    content: newComment.value,
                },
            }
        )
        .then((res) => {
            console.log(res);
            ElMessage.success("发布成功");
            fetchCommentsAndApplies(props.dormitory.dormitory_id);
            newComment.value = "";
        })
        .catch((err) => {
            console.error(err);
            ElMessage.error("发布失败");
        });
}
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
    display: flex;
    flex-direction: column;
    align-items: flex-start; // 确保子元素从容器的左边开始

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

    .title {
        text-align: center; // 文本居中
        color: @text-color4; // 文本颜色
        font-size: x-large; // 字体大小
        font-weight: bold; // 字体加粗
    }

    span {
        display: block;
        margin-bottom: 10px; // 标题下的间距
        font-size: large;
        font-weight: bold;
    }

    .text-item {
        padding: 0 10%; // 左右留白
        font-size: large;
    }

    .el-button {
        border: None;
        background-color: @button-color;
        &:hover {
            background-color: darken(@button-color, 5%);
        }

        display: block;

        margin-top: 20px;
        padding: 10px 30px;
        margin-left: auto;
    }
}
</style>
