<script setup>
import HomePagePersonCard from "@/components/HomePagePersonCard.vue";
import NoticeBoard from "@/components/NoticeBoard.vue";
import TeamView from "@/components/TeamView.vue";
import FunctionBoard from "@/components/FunctionBoard.vue";
</script>

<template>
  <div class="grid-container">
    <div class="upper-left">
      <HomePagePersonCard />
    </div>
    <div class="upper-right">
      <NoticeBoard />
    </div>
    <div class="lower-left">
      <TeamView />
    </div>
    <div class="lower-right">
      <FunctionBoard />
    </div>
  </div>
</template>

<style scoped>
.grid-container {
    display: grid;
    grid-gap: 20px;
    grid-template-columns: repeat(10, 1fr);
    grid-template-rows: repeat(10, 1fr);
}

.upper-left {
    grid-column: 1 / 6;
    grid-row: 1 / 6;
}

.upper-right {
    grid-column: 6 / 11;
    grid-row: 1 / 6;
}

.lower-left {
    grid-column: 1 / 6;
    grid-row: 6 / 11;
}

.lower-right {
    grid-column: 6 / 11;
    grid-row: 6 / 11;
}
</style>