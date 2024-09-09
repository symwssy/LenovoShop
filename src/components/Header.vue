<template>
  <header class="navbar">
    <div class="container">
      <img src="../assets/LenovoLogo.png" alt="Logo" class="logo">
      <div v-if="!showSearch" class="left-nav">

        <div class="nav-links">
          <router-link to="/home">主页</router-link>
          <router-link to="/manage">管理</router-link>
        </div>
      </div>

      <!-- 搜索框显示 -->
      <div v-if="showSearch" class="search-box">
        <input
            type="text"
            v-model="searchQuery"
            placeholder="输入搜索内容..."
            class="search-input"
        />
        <img src="../assets/cross.svg" alt="Clear" class="clear-icon" @click="clearSearch">
        <img src="../assets/search.svg" alt="Search" class="search-icon" @click="performSearch">
      </div>

      <!-- 正常导航显示 -->
      <div v-if="!showSearch" class="right-nav">
        <img src="../assets/search.svg" alt="Search" class="icon" @click="toggleSearch">
        <router-link to="/login">登录</router-link>
        <router-link to="/register">注册</router-link>
      </div>
    </div>
  </header>

</template>


<script setup>
import {ref} from 'vue';

const showSearch = ref(false); // 控制搜索框的显示状态
const searchQuery = ref(''); // 搜索框输入的内容

// 切换搜索框显示
const toggleSearch = () => {
  showSearch.value = !showSearch.value;
};

// 清除搜索框内容并隐藏搜索框
const clearSearch = () => {
  searchQuery.value = '';
  showSearch.value = false;
};

// 执行搜索（后续可以根据需求传递内容给搜索页面）
const performSearch = () => {
  if (searchQuery.value.trim()) {
    console.log(`搜索内容: ${searchQuery.value}`);
    // 后续可以将 searchQuery.value 传递给其他页面进行搜索
  }
};
</script>


<style scoped>
.navbar {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  background-color: #333;
  color: #fff;
  padding: 1rem;
  z-index: 1000;
  display: flex;
  justify-content: center; /* 居中容器内容 */
}

.container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%; /* 宽度100%以确保覆盖整个header */
}

.left-nav {
  display: flex;
  align-items: center;
  position: relative;
  flex: 1; /* 使left-nav占据可用空间 */
}

.logo {
  height: 5vh;
}

.nav-links {
  display: flex;
  align-items: center;
  margin-left: 1rem; /* 调整与logo之间的距离 */
}

.nav-links a {
  color: #fff;
  margin-right: 1rem;
  text-decoration: none;
}

.right-nav {
  display: flex;
  align-items: center;
}

.icon {
  height: 2rem;
  margin-right: 1rem;
  cursor: pointer;
}

.right-nav a {
  color: #fff;
  margin-left: 1rem;
  text-decoration: none;
}

.search-box {
  display: flex;
  align-items: center;
  width: 50%; /* 搜索框宽度为header宽度的50% */
  position: absolute;
  left: 55%;
  transform: translateX(-50%); /* 居中搜索框 */
}

.search-input {
  flex-grow: 1; /* 使输入框占满剩余空间 */
  padding: 0.5rem;
  font-size: 1rem;
  border-radius: 5px;
  border: none;
  outline: none;
}

.clear-icon {
  position: absolute;
  right: 4rem; /* 位置调整到输入框内的右侧 */
  height: 1.5rem;
  cursor: pointer;
}

.search-icon {
  height: 2rem;
  margin-left: 1.5rem;
  cursor: pointer;
}
</style>
