<template>
    <div class="reply-container">
        <div class="reply">
            <p>{{ reply.content }}</p>
            <span>{{ reply.publish_time }}</span>
            <!-- 显示回复发布者等信息 -->
        </div>
    </div>
</template>

<script setup lang="ts">
import { PropType } from "vue";
import { ReplyType } from "@/types/globalTypes";

const props = defineProps({
    reply: {
        type: Object as PropType<ReplyType>,
        required: true,
    },
});


</script>