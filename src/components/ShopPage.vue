<template>
  <div class="shop-page">
    <!-- 侧边导航 -->
    <nav class="side-nav" :class="{ 'show': isSidebarVisible }">
      <ul>
        <!-- 强制循环11次 -->
        <li v-for="index in 11" :key="index - 1">
          <a href="#" @click.prevent="scrollToSection(index - 1)" :class="{ active: activeSection === index - 1 }">
            <span class="section-layer">{{ index }}F</span>
            <span class="section-title">{{ sections[index - 1]?.title || '' }}</span>
          </a>
        </li>
      </ul>
    </nav>

    <!-- 页面内容 -->
    <div class="content">
      <div v-for="index in 11" :key="index" class="section" ref="sections" :data-index="index - 1">
        <h2>{{ sections[index - 1]?.title }}</h2>
        <div class="section-content">
          <img :src="sections[index - 1]?.image" alt="Section Image" class="section-image">
          <div class="products">
            <div v-for="product in sections[index - 1]?.products" :key="product.id" class="product-card">
              <img :src="product.goodsPic" class="product-image" :alt="product.goodsName">
              <div class="product-details">
                <h3 class="product-name">{{ product.goodsName }}</h3>
                <p class="product-description">{{ product.briefIntro }}</p>
                <p class="product-price">{{ product.goodsPrice }} 元</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import axios from 'axios';

// 默认的sections数据
const sections = ref([
  { title: 'Lenovo 电脑', image: '../src/assets/sections/section1.png', products: [] },
  { title: 'Lenovo 台式机', image: '../src/assets/sections/section2.png', products: [] },
  { title: 'ThinkPad 电脑', image: '../src/assets/sections/section3.png', products: [] },
  { title: '手机&配件', image: '../src/assets/sections/section4.png', products: [] },
  { title: '平板电脑', image: '../src/assets/sections/section5.png', products: [] },
  { title: '选件', image: '../src/assets/sections/section6.png', products: [] },
  { title: '服务/配件', image: '../src/assets/sections/section7.png', products: [] },
  { title: '智能', image: '../src/assets/sections/section8.png', products: [] },
  { title: '显示器', image: '../src/assets/sections/section9.png', products: [] },
  { title: 'IP周边', image: '../src/assets/sections/section10.png', products: [] },
  { title: 'thinkplus', image: '../src/assets/sections/section11.png', products: [] },
]);

const activeSection = ref(0);
const isSidebarVisible = ref(false);

async function fetchProducts(goodsType) {
  try {
    const response = await axios.get('/api/index/', {
      params: {
        goodsType: goodsType,
      }
    });

    if (response.data.code === 0) {
      const products = response.data.data;
      sections.value[goodsType].products = products;
    } else {
      console.error('API 返回错误:', response.data.message);
    }
  } catch (error) {
    console.error('请求失败:', error);
  }
}

async function loadAllProducts() {
  for (let i = 0; i < 11; i++) {
    await fetchProducts(i);
  }
}

function scrollToSection(index) {
  const sectionElement = document.querySelector(`.section[data-index="${index}"]`);
  if (sectionElement) {
    sectionElement.scrollIntoView({ behavior: 'smooth' });
    activeSection.value = index;
  }
}

function handleScroll() {
  const sectionsEl = document.querySelectorAll('.section');
  let shouldShowSidebar = false;
  let currentActiveSection = -1;

  for (let i = 0; i < sectionsEl.length; i++) {
    const section = sectionsEl[i];
    const rect = section.getBoundingClientRect();

    if (rect.top < window.innerHeight && rect.bottom > 0) {
      shouldShowSidebar = true;
      currentActiveSection = i;
      break;
    }
  }

  if (sectionsEl[0].getBoundingClientRect().bottom > 900) {
    shouldShowSidebar = false;
  }

  isSidebarVisible.value = shouldShowSidebar;
  activeSection.value = currentActiveSection;
}

onMounted(() => {
  loadAllProducts();
  window.addEventListener('scroll', handleScroll);
});

onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll);
});
</script>
<style scoped>
.shop-page {
  display: flex;
}

.side-nav {
  width: 15%;
  position: fixed;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  opacity: 0;
  transition: opacity 0.3s;
}

.side-nav.show {
  opacity: 1;
}

.side-nav ul {
  list-style-type: none;
  padding: 0;
}

.side-nav li {
  margin: 10px 0;
}

.side-nav a {
  display: flex;
  align-items: center;
  justify-content: flex-end;
  text-decoration: none;
  color: black;
  padding: 5px 10px;
  position: relative;
}

.side-nav .section-layer,
.side-nav .section-title {
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1rem;
  padding: 5px;
  border-radius: 4px;
  margin-right: 5px;
}

.side-nav .section-layer {
  background: none;
}

.side-nav .section-title {
  display: none;
}

.side-nav a:hover .section-title,
.side-nav a.active .section-title {
  display: flex;
  color: red;
}

.side-nav a:hover .section-layer,
.side-nav a.active .section-layer {
  display: none;
}

.content {
  margin-top: -5%;
  margin-left: 220px;
  padding: 5%;
}

.content h2 {
  margin-top: 6%;
}

.section {
  margin-bottom: 50px;
  padding: 10px;
}

.section-content {
  display: flex;
  gap: 10px;
}

.section-image {
  width: 20%;
  height: auto;
}

.products {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 10px;
  width: 75%;
}

.product-card {
  border: 1px solid #ddd;
  border-radius: 8px;
  overflow: hidden;
  background: #fff;
  transition: transform 0.3s ease, box-shadow 0.3s ease; /* 添加过渡效果 */
  text-align: center; /* 使子元素水平居中 */
}

.product-card:hover {
  transform: translateY(-5px); /* 鼠标悬浮时向上浮动5px */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* 添加阴影效果 */
}

.product-card:active {
  transform: translateY(-2px); /* 点击时稍微浮动，效果更加明显 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2); /* 点击时的阴影效果 */
}

.product-image {
  width: 100%;
  height: auto;
  object-fit: cover;
}

.product-details {
  padding: 10px;
}

.product-name {
  font-size: 1rem;
  margin-bottom: 0.5rem;
}

.product-description {
  font-size: 0.875rem;
  color: #555;
}

.product-price {
  font-size: 2vh;
  font-weight: bold;
  color: #ff0000;
}
</style>