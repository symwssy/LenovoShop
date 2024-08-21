import { createRouter, createWebHistory } from 'vue-router';
import Index from './components/Index.vue';
import Login from './components/Login.vue';
import Register from './components/Register.vue';
import Loading from './components/Loading.vue';
import { checkLoginStatus } from './auth.js'; // 引入 checkLoginStatus 函数
import axios from 'axios';
import { useRouter } from 'vue-router';

const routes = [
  { path: '/', redirect: '/index' },
  { path: '/index', component: Index },
  { path: '/login', component: Login },
  { path: '/register', component: Register },
  { path: '/loading', component: Loading },
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

router.beforeEach(async (to, from, next) => {
  if (to.meta.requireAuth) {
    try {
      const isLoggedIn = await checkLoginStatus();
      if (isLoggedIn) {
        next();
      } else {
        next('/login');
      }
    } catch (error) {
      console.error('检查登录状态失败', error);
      next('/login');
    }
  } else {
    next();
  }
});

export default router;
