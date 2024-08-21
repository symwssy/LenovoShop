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
  background-color: #333;
  color: #fff;
  padding: 1rem;
}

.container {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.left-nav {
  display: flex;
  align-items: center;
  margin-left: -10rem; /* 确保右边距不为负值 */
}

.logo {
  height: 5vh; /* 设置高度为视口高度的 50% */
  margin-right: 1rem;
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
  margin-right: -10rem; /* 确保右边距不为负值 */
}

.icon {
  height: 2rem; /* 设置放大镜图标的高度 */
  margin-right: 1rem; /* 确保右边距不为负值 */
}

.right-nav a {
  color: #fff;
  margin-left: 1rem;
  text-decoration: none;
}
</style>

