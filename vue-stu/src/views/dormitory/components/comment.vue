<template>
    <div class="comment">
        <div class="comment-content">
            <p>{{ comment.content }}</p>
            <span>{{ comment.publish_time }}</span>
            <!-- 显示评论发布者等信息 -->
        </div>
        <div class="replies">
            <Comment
                v-for="reply in comment.reply_list"
                :key="reply.id"
                :comment="reply"
            />
        </div>
    </div>
</template>

<script setup lang="ts">
import { defineComponent } from "vue";

const props = {
    comment: {
        type: Object,
        required: true,
    },
};
const comment = defineComponent({
    props,
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
