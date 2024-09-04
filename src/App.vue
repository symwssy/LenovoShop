<template>
  <div class="app">
    <Header />
    <main class="content">
      <router-view :key="$route.fullPath"></router-view>
    </main>
  </div>
</template>

<script setup>
import Header from './components/Header.vue';
</script>

<style>
body {
  overflow-x: hidden;
  overflow-y: auto;
  font-family: 'Arial', sans-serif;
}

.app {
  background-color: #F0F2F4;
  display: flex;
  flex-direction: column;
  min-height: 100vh; /* 确保应用占满整个视口高度 */
}

.content {
  margin-top: 4rem; /* 根据Header的实际高度设置内边距 */
}
</style>
