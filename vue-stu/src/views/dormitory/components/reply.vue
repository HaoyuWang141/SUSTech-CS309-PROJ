<template>
    
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