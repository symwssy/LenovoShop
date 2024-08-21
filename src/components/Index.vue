<template>
  <div class="app-container">
    <!-- Carousel Section -->
    <div class="carousel-container">
      <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
        <div class="carousel-indicators">
          <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
          <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
          <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
        </div>
        <div class="carousel-inner">
          <div class="carousel-item active">
            <img src="../assets/Background.jpg" class="d-block w-100" alt="Background Image 1">
          </div>
          <div class="carousel-item">
            <img src="../assets/Background2.jpg" class="d-block w-100" alt="Background Image 2">
          </div>
          <div class="carousel-item">
            <img src="../assets/Background3.jpg" class="d-block w-100" alt="Background Image 3">
          </div>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
          <span class="carousel-control-prev-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
          <span class="carousel-control-next-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Next</span>
        </button>
      </div>
    </div>

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
