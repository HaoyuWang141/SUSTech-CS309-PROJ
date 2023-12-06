<script setup>
import HomePagePersonCard from "@/components/HomePagePersonCard.vue";
import NoticeBoard from "@/components/NoticeBoard.vue";
import TeamView from "@/components/TeamView.vue";
</script>

<template>
    <div class="grid-container">
        <div class="upper-left">
            <HomePagePersonCard></HomePagePersonCard>
        </div>
        <div class="upper-right">
            <NoticeBoard></NoticeBoard>
        </div>
        <div class="lower-full">
            <TeamView></TeamView>
            <!-- 其他组件可以在这里添加 -->
        </div>
    </div>
</template>

<style scoped>
.grid-container {
    display: grid;
    grid-template-columns: repeat(10, 1fr);
    grid-template-rows: repeat(10, 1fr);
    background-color: #f2f2f2;
    height: 100vh;
    width: 100vw;
    margin: 0;
    padding: 0;
    border: 0;
}

.upper-left {
    grid-column: 1 / 6;
    grid-row: 1 / 6;
}

.upper-right {
    grid-column: 6 / 11;
    grid-row: 1 / 6;
}

.lower-full {
    grid-column: 1 / 6;
    grid-row: 6 / 11;
}
</style>