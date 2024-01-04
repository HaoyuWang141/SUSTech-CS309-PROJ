<template>
    <div class="comment-container">
        <div class="comment">
            <p>{{ props.comment.content }}</p>
            <span>{{ comment.publish_time }}</span>
            <!-- 显示评论发布者等信息 -->
        </div>
        <div class="replies">
            <Reply
                v-for="reply in comment.reply_list"
                :key="reply.id"
                :comment="reply"
            />
        </div>
    </div>
</template>

<script setup lang="ts">
import { defineComponent, PropType } from "vue";
import { CommentType, ReplyType } from "@/types/globalTypes";


const props = defineProps({
    comment: {
        type: Object as PropType<CommentType>,
        required: true,
    },
});

</script>

<style scoped>
.comment {
    margin-bottom: 10px;
}

.replies {
    margin-left: 20px; /* 回复的缩进 */
}
</style>
