<template>
  <header class="navbar">
    <div class="container">
      <div class="left-nav">
        <img src="../assets/LenovoLogo.png" alt="Logo" class="logo">
        <nav>
          <router-link to="/">主页</router-link>
          <router-link to="/manage">管理</router-link>
        </nav>
      </div>
      <div class="right-nav">
        <img src="../assets/search.svg" alt="Search" class="icon">
        <router-link to="/login">登录</router-link>
        <router-link to="/register">注册</router-link>
      </div>
    </div>
  </header>
</template>

<script setup>
</script>

<style scoped>
.navbar {
  position: fixed; /* 固定在视口顶部 */
  top: 0;
  left: 0;
  width: 100%; /* 宽度100%确保覆盖整个视口宽度 */
  background-color: #333;
  color: #fff;
  padding: 1rem;
  z-index: 1000; /* 确保Header在其他内容之上 */
}

.container {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.left-nav {
  display: flex;
  align-items: center;
}

.logo {
  height: 5vh; /* 设置高度为视口高度的 50% */
}

nav a {
  color: #fff;
  margin: 0 1rem;
  text-decoration: none;
}

nav a.router-link-active {
  border-bottom: 2px solid #fff;
}

.right-nav {
  display: flex;
  align-items: center;
}

.icon {
  height: 2rem; /* 设置放大镜图标的高度 */
  margin-right: 1rem;
}

.right-nav a {
  color: #fff;
  margin-left: 1rem;
  text-decoration: none;
}
</style>
