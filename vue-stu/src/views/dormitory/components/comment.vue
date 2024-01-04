<template>
    <div class="comment-container">
        <div class="comment">
            <span>[{{ publish_time }}] {{ comment.publisher_id }}</span>
            <p>{{ comment.content }}</p>
        </div>
        <div class="replies" v-for="reply in comment.reply_list">
            <span>{{ reply.publisher_id }} 回复：{{ reply.content }}</span>
        </div>
        <el-form>
            <el-form-item>
                <el-input
                    v-model="newReply"
                    placeholder="请输入回复"
                    clearable
                    style="margin-top: 10px;"
                />
            </el-form-item>
            <el-form-item style="margin-bottom: 30px;">
                <el-button type="primary" @click="postReply">
                    发布回复
                </el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script setup lang="ts">
import { computed, PropType, ref } from "vue";
import { CommentType, ReplyType } from "@/types/globalTypes";
import axiosInstance from "@/axios/axiosConfig";
import { ElMessage } from "element-plus";

const props = defineProps({
    comment: {
        type: Object as PropType<CommentType>,
        required: true,
    },
});

const publish_time = computed(() =>
    new Date(props.comment.publish_time).toLocaleString()
);

async function fetchApplies(comment_id: number) {
    // 添加获取回复的逻辑
    console.log("获取回复");
    axiosInstance
        .get("/student/forum/getReply", {
            params: {
                commentId: comment_id,
            },
        })
        .then((res) => {
            console.log(res);
            props.comment.reply_list = res.data;
        })
        .catch((err) => {
            console.log(err);
        });
}
const newReply = ref("");
async function postReply() {
    // 添加发布回复的逻辑
    console.log("发布回复被点击");
    if (newReply.value === "") {
        ElMessage.warning("回复不能为空");
        return;
    }
    axiosInstance
        .post(
            "/student/forum/reply",
            {},
            {
                params: {
                    studentAccountId: localStorage.getItem("studentId"),
                    commentId: props.comment.id,
                    content: newReply.value,
                },
            }
        )
        .then((res) => {
            console.log(res);
            ElMessage.success("回复成功");
            fetchApplies(props.comment.id);
            newReply.value = "";
        })
        .catch((err) => {
            console.log(err);
            ElMessage.error("回复失败");
        });
}
</script>

<style scoped>
.comment {
    margin-bottom: 20px;
}

.replies {
}

.el-button {
    margin-left: auto;
}
</style>
