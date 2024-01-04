<template>
    <div class="comment-container">
        <div class="comment">
            <span>[{{ publish_time }}] {{ comment.publisher_id }}</span>
            <p>{{ comment.content }}</p>
        </div>
        <div class="replies">
            <Reply
                v-for="reply in comment.reply_list"
                :key="reply.id"
                :reply="reply"
            />
        </div>
        <el-form>
            <el-form-item>
                <el-input
                    v-model="newReply"
                    placeholder="请输入回复"
                    clearable
                />
            </el-form-item>
            <el-form-item>
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

const publish_time = computed(() => new Date(props.comment.publish_time).toLocaleString());

const newReply = ref("");
async function postReply() {
    // 添加发布回复的逻辑
    console.log("发布回复被点击");
    axiosInstance
        .post("/student/forum/reply", {
            studentAccountId: localStorage.getItem("studentId"),
            commentId: props.comment.id,
            content: newReply.value,
        })
        .then((res) => {
            console.log(res);
            newReply.value = "";
            ElMessage.success("回复成功");
        })
        .catch((err) => {
            console.log(err);
            ElMessage.error("回复失败");
        });
}

</script>

<style scoped>
.comment {
    margin-bottom: 10px;
}

.replies {
    margin-left: 20px; /* 回复的缩进 */
}
</style>
