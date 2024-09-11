import { createRouter, createWebHistory } from 'vue-router';
import Index from './components/Index.vue';
import Manage from './components/Manage.vue';
import Login from './components/Login.vue';
import Register from './components/Register.vue';
import Loading from './components/Loading.vue';
import AddressList from './components/AddressList.vue';
import GoodsDetail from './components/GoodsDetail.vue';
import OrderList from './components/OrderList.vue';
import ProductSearch from './components/ProductSearch.vue';
import PurchasePage from './components/PurchasePage.vue';
import UserDetail from './components/UserDetail.vue';

const routes = [
  { path: '/', redirect: '/index' },
  { path: '/index', component: Index },
  { path: '/manage', component: Manage, meta: { requireAuth: true } }, // 需要登录
  { path: '/login', component: Login },
  { path: '/register', component: Register },
  { path: '/loading', component: Loading },
  { path: '/address-list', component: AddressList, meta: { requireAuth: true } }, // 需要登录
  { path: '/goods-detail/:id', component: GoodsDetail },
  { path: '/order-list', component: OrderList, meta: { requireAuth: true } }, // 需要登录
  { path: '/product-search', component: ProductSearch },
  { path: '/purchase-page/:id', component: PurchasePage, meta: { requireAuth: true } }, // 需要登录
  { path: '/user-detail', component: UserDetail, meta: { requireAuth: true } }, // 需要登录
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

// 检查登录状态的函数，假设它是一个异步函数
async function checkLoginStatus() {
  // 假设你可以从 Vuex store 或 localStorage 中获取登录状态
  // 这里仅为示例，具体实现可能会有所不同
  const token = localStorage.getItem('token');
  return !!token;
}

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
