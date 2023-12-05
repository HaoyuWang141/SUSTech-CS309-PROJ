<script setup lang="ts">
defineProps({
    title: String,
    description: String
});
</script>

<template>
    <div class="feature">
        <h3 class="title">{{ title }}</h3>
        <p class="description">{{ description }}</p>
    </div>
</template>

<style scoped lang="less">
@background-color: #09233E;
@hover-background-color: #1C2B4C;
@primary-font-size: 1.2em;
@primary-font-color: #fff;
@primary-font-weight: bold;
@secondary-font-size: 1em;
@secondary-font-color: #fff;
@secondary-font-weight: normal;

.feature {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 8% 8%;
    background-color: @background-color;
    border-radius: 2rem;
    transition: background-color 0.5s;
    box-shadow: 4px 4px 10px rgba(34, 204, 228, 0.2);

    &:hover {
        background-color: @hover-background-color;
    }
}

.title {
    color: @primary-font-color;
    font-size: @primary-font-size;
    font-weight: @primary-font-weight;
    margin-bottom: 1rem;
}

.description {
    margin: 0;
    color: @secondary-font-color;
    font-size: @secondary-font-size;
    align-items: @secondary-font-weight;
}
</style>
