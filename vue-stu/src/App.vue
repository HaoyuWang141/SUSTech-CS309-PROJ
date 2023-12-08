<script>
export default {
    data() {
        return {
            activeMenu: 'home'
        };
    },
    methods: {
        handleMenuClick(index) {
            this.activeMenu = index;
            this.$router.push({name: index});
        }
    }
};
</script>

<template>
  <div class="layout-container">
    <RouterView></RouterView>
  </div>
</template>

<style scoped>
.layout-container {
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    display: flex;
    box-sizing: border-box;
}
</style>
