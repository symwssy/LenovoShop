<template>
  <div class="app-container">
    <!-- Shop Page Section -->
    <ShopPage />
  </div>
</template>

<script setup>
import { onMounted } from 'vue';
import ShopPage from '../components/ShopPage.vue'; // 导入 ShopPage 组件

onMounted(() => {
  const carouselElement = document.querySelector('#carouselExampleIndicators');
  if (carouselElement) {
    new bootstrap.Carousel(carouselElement, {
      interval: 5000, // 5秒切换一次
      wrap: true
    });
  }
});
</script>

<style scoped>
.app-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
}

.carousel-container {
  flex-shrink: 0;
}

.carousel-item img {
  width: 100%;
  height: auto;
}

.shop-page-container {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
}
</style>
