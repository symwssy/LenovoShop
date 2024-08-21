<template>
  <div class="app">
    <Header />
    <main class="content">
      <router-view></router-view>
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
  min-height: 100vh; /* Ensure the app takes up the full height of the viewport */
}

</style>
