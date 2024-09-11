<template>
  <header class="navbar">
    <div class="container">
      <img href="/index" src="../assets/LenovoLogo.png" alt="Logo" class="logo">
      <div v-if="!showSearch" class="left-nav">
        <div class="nav-links">
          <router-link to="/index">主页</router-link>
          <!-- 仅管理员显示 "管理" 按钮 -->
          <router-link v-if="isManager" to="/manage">管理</router-link>
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

        <!-- 显示用户名和下拉菜单 -->
        <div v-if="isLoggedIn" class="btn-group user-dropdown">
          <button
              type="button"
              class="btn dropdown-toggle"
              data-bs-toggle="dropdown"
              aria-expanded="false"
              style="background-color: transparent; border: none; color: #ffffff;">
            {{ username }}
          </button>
          <ul class="dropdown-menu dropdown-menu-end">
            <li><router-link class="dropdown-item" :to="'/user-detail'">用户信息</router-link></li>
            <li><router-link class="dropdown-item" :to="'/order-list'">用户订单</router-link></li>
            <li><hr class="dropdown-divider"></li>
            <li><a class="dropdown-item" @click="logout">退出登录</a></li>
          </ul>
        </div>

        <!-- 未登录时显示登录/注册按钮 -->
        <div v-else>
          <router-link to="/login">登录</router-link>
          <router-link to="/register">注册</router-link>
        </div>

      </div>
    </div>
  </header>
</template>


<script setup>
import { ref, computed, watch } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

const store = useStore();
const router = useRouter();

const showSearch = ref(false);
const searchQuery = ref('');

// 计算是否登录
const isLoggedIn = computed(() => store.state.isLoggedIn);

// 计算管理员状态
const isManager = computed(() => store.getters.isManager); // 从 Vuex 获取管理员状态

// 计算用户名
const username = computed(() => store.getters.getUsername);  // 从 Vuex 获取用户名


// 监控 token 变化
watch(
    () => localStorage.getItem('token'),
    (newToken) => {
      if (newToken) {
        try {
          const base64Url = newToken.split('.')[1];
          const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
          const decoded = JSON.parse(atob(base64));
          username.value = decoded.claims?.username || ''; // 更新用户名
          store.commit('setLoginState', !!username.value); // 更新 Vuex 状态
        } catch (e) {
          console.error('Token 解码错误', e);
          username.value = ''; // 解码错误时设置为空
          store.commit('setLoginState', false); // 更新 Vuex 状态
        }
      } else {
        username.value = ''; // Token 不存在时设置为空
        store.commit('setLoginState', false); // 更新 Vuex 状态
      }
    },
    { immediate: true } // 立即执行一次
);

// 切换搜索框显示
const toggleSearch = () => {
  showSearch.value = !showSearch.value;
};

// 获取用户 ID
const userId = computed(() => store.state.userId);

// 清除搜索框内容并隐藏搜索框
const clearSearch = () => {
  searchQuery.value = '';
  showSearch.value = false;
};

// 执行搜索
const performSearch = () => {
  if (searchQuery.value.trim()) {
    console.log(`搜索内容: ${searchQuery.value}`);
    // 调用搜索逻辑
  }
};

// 退出登录
const logout = () => {
  localStorage.removeItem('token');
  store.dispatch('logout'); // 更新 Vuex 状态
  router.push('/login');
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
  justify-content: center;
}

.container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}

.left-nav {
  display: flex;
  align-items: center;
  position: relative;
  flex: 1;
}

.logo {
  height: 5vh;
}

.nav-links {
  display: flex;
  align-items: center;
  margin-left: 1rem;
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
  width: 50%;
  position: absolute;
  left: 55%;
  transform: translateX(-50%);
}

.search-input {
  flex-grow: 1;
  padding: 0.5rem;
  font-size: 1rem;
  border-radius: 5px;
  border: none;
  outline: none;
}

.clear-icon {
  position: absolute;
  right: 4rem;
  height: 1.5rem;
  cursor: pointer;
}

.search-icon {
  height: 2rem;
  margin-left: 1.5rem;
  cursor: pointer;
}

/* 去掉下拉菜单 hover 效果 */
.dropdown-menu a.dropdown-item:hover {
  background: none;
}

/* 设置下拉菜单项字体颜色为黑色 */
.dropdown-menu a.dropdown-item {
  background: none;
  color: black;
}

/* 禁止选中文字 */
.dropdown-menu a.dropdown-item {
  user-select: none;
}
</style>
