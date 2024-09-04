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
        <h2>{{ sections[index - 1].title }}</h2>
        <div class="section-content">
          <img :src="sections[index - 1].image" alt="Section Image" class="section-image">
          <div class="products">
            <div v-for="product in sections[index - 1].products" :key="product.id" class="product-card">
              <img :src="product.image" class="product-image" :alt="product.name">
              <div class="product-details">
                <h3 class="product-name">{{ product.name }}</h3>
                <p class="product-description">{{ product.description }}</p>
                <p class="product-price">{{ product.price }}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted, onUnmounted} from 'vue';

// 默认的商品数据
const defaultProduct = {
  id: 0,
  name: '商品',
  description: '这是商品的介绍。',
  price: '¥9999.99',
  image: '../src/assets/default-product.jpg',
};

// 默认的sections数据
const sections = ref([
  {title: 'Lenovo 电脑', image: '../src/assets/sections/section1.png', products: Array(8).fill(defaultProduct)},
  {title: 'Lenovo 台式机', image: '../src/assets/sections/section2.png', products: Array(8).fill(defaultProduct)},
  {title: 'ThinkPad 电脑', image: '../src/assets/sections/section3.png', products: Array(8).fill(defaultProduct)},
  {title: '手机&配件', image: '../src/assets/sections/section4.png', products: Array(8).fill(defaultProduct)},
  {title: '平板电脑', image: '../src/assets/sections/section5.png', products: Array(8).fill(defaultProduct)},
  {title: '选件', image: '../src/assets/sections/section6.png', products: Array(8).fill(defaultProduct)},
  {title: '服务/配件', image: '../src/assets/sections/section7.png', products: Array(8).fill(defaultProduct)},
  {title: '智能', image: '../src/assets/sections/section8.png', products: Array(8).fill(defaultProduct)},
  {title: '显示器', image: '../src/assets/sections/section9.png', products: Array(8).fill(defaultProduct)},
  {title: 'IP周边', image: '../src/assets/sections/section10.png', products: Array(8).fill(defaultProduct)},
  {title: 'thinkplus', image: '../src/assets/sections/section11.png', products: Array(8).fill(defaultProduct)},
]);

const activeSection = ref(0);
const isSidebarVisible = ref(false);

function scrollToSection(index) {
  const sectionElement = document.querySelector(`.section[data-index="${index}"]`);
  if (sectionElement) {
    sectionElement.scrollIntoView({behavior: 'smooth'});
    activeSection.value = index;
  }
}

function handleScroll() {
  const sections = document.querySelectorAll('.section');
  let shouldShowSidebar = false;
  let currentActiveSection = -1;

  // 遍历第1到第11层
  for (let i = 0; i < sections.length; i++) {
    const section = sections[i];
    const rect = section.getBoundingClientRect();

    // 检查部分是否在视口内
    if (rect.top < window.innerHeight && rect.bottom > 0) {
      shouldShowSidebar = true; // 只要有一个部分在视口内，就显示侧边栏
      currentActiveSection = i; // 记录当前视口内的部分索引
      break; // 一旦找到在视口内的部分就停止循环
    }
  }

  // 只要第一个部分的底部超过了900px，就隐藏侧边栏
  if (sections[0].getBoundingClientRect().bottom > 900) {
    shouldShowSidebar = false;
  }

  // 更新侧边栏的显示状态
  isSidebarVisible.value = shouldShowSidebar;
  // 更新选中的部分
  activeSection.value = currentActiveSection;
}

onMounted(() => {
  // 监听滚动事件
  window.addEventListener('scroll', handleScroll);
  // 初始化检查
  handleScroll();
});

onUnmounted(() => {
  // 移除滚动事件监听器
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