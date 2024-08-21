<template>
  <div class="container mt-5">
    <div class="row">
      <!-- 商品卡片 -->
      <div class="col-md-4 mb-4" v-for="product in products" :key="product.id">
        <div class="card">
          <img :src="product.image" class="card-img-top" :alt="product.name">
          <div class="card-body text-center">
            <h5 class="card-title">{{ product.name }}</h5>
            <p class="card-text">{{ product.description }}</p>
            <h6 class="card-price">{{ product.price }}</h6>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const products = ref([
  {
    id: 1,
    name: '商品 1',
    description: '这是商品 1 的介绍。',
    price: '¥99.00',
    image: '../src/assets/product1.jpg',
  },
  {
    id: 2,
    name: '商品 2',
    description: '这是商品 2 的介绍。',
    price: '¥129.00',
    image: '../src/assets/product2.jpg',
  },
  {
    id: 3,
    name: '商品 3',
    description: '这是商品 3 的介绍。',
    price: '¥159.00',
    image: '../src/assets/product3.jpg',
  },
  {
    id: 4,
    name: '商品 4',
    description: '这是商品 4 的介绍。',
    price: '¥189.00',
    image: '../src/assets/product4.jpg',
  },
  {
    id: 5,
    name: '商品 5',
    description: '这是商品 5 的介绍。',
    price: '¥219.00',
    image: '../src/assets/product5.jpg',
  },
  {
    id: 6,
    name: '商品 6',
    description: '这是商品 6 的介绍。',
    price: '¥249.00',
    image: '../src/assets/product6.jpg',
  },
  {
    id: 7,
    name: '商品 7',
    description: '这是商品 7 的介绍。',
    price: '¥279.00',
    image: '../src/assets/product7.jpg',
  },
  {
    id: 8,
    name: '商品 8',
    description: '这是商品 8 的介绍。',
    price: '¥299.00',
    image: '../src/assets/product8.jpg',
  },
  {
    id: 9,
    name: '商品 9',
    description: '这是商品 9 的介绍。',
    price: '¥329.00',
    image: '../src/assets/product9.jpg',
  },
]);

</script>

<style scoped>
.card {
  border: 1px solid #ddd; /* 添加边框 */
  border-radius: 8px; /* 圆角边框 */
  overflow: hidden; /* 防止图片溢出 */
}

.card-img-top {
  height: 200px;
  object-fit: cover; /* 图片覆盖 */
}

.card-body {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.card-title {
  font-size: 1.25rem;
  margin-bottom: 0.5rem;
}

.card-text {
  font-size: 1rem;
  color: #555;
}

.card-price {
  font-size: 1.25rem;
  font-weight: bold;
  color: #333;
}
</style>
